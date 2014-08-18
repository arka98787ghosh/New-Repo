package controllers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.core.Router;
import play.libs.Json;
import scala.reflect.io.VirtualFile;
import models.IdGen;
import models.User;
import models.UserImageIds;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class Application extends Controller {
	public static int whatError = 0;
	public static Long loggedInUserId;
	public static String fullImagePath = "register/public/images/FullImages";
	public static String thumbnailImagePath = "register/public/images/ThumbnailImages";
	public static int thumbnailSize = 100;
	public static String setToken;

	/*
	 * @BodyParser.Of(BodyParser.Json.class) public static Result index() {
	 * RequestBody body = request().body(); return ok("Got json: " +
	 * body.toString()); }
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result register() {

		JsonNode json = request().body().asJson();
		ObjectNode result = Json.newObject();

		if (json == null) {
			result.put("message", "json is being null");
			return ok(result);
		}

		String email = json.findPath("email").getTextValue();
		String password = json.findPath("password").getTextValue();
		if (email == null) {
			result.put("status", "KO");
			result.put("message", "Missing parameter [name]");
			return badRequest(result);
		} else {
			User user = new User();
			user.email = email;
			MD5Password.passwordToHash = password;
			user.password = MD5Password.hashPassword();
			/*
			 * result.put("resultSetVal", User.validate(user)); return
			 * ok(result);
			 */
			if (User.registerValidate(user)) {
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				// get current date time with Date()
				Date date = new Date();
				// System.out.println(dateFormat.format(date));
				user.created_at = dateFormat.format(date);
				user.updated_at = dateFormat.format(date);
				User.create(user);
				result.put("state", true);
				result.put("status", "OK");
				result.put("message", "Hello " + email + " your password is "
						+ password);
				return ok(result);

			} else {
				result.put("state", false);
				if (whatError == 1)
					result.put("message", "This email id is already registered");
				else if (whatError == 2)
					result.put("message", "Invalid email id format");
				return badRequest(result);
			}

		}
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result login() {

		JsonNode json = request().body().asJson();
		ObjectNode result = Json.newObject();

		String email = json.findPath("email").getTextValue();
		String password = json.findPath("password").getTextValue();
		if (email == null) {
			result.put("status", "KO");
			result.put("message", "Missing parameter [name]");
			return badRequest(result);
		} else {
			User user = new User();
			user.email = email;
			MD5Password.passwordToHash = password;
			user.password = MD5Password.hashPassword();
			/*
			 * result.put("resultSetVal", User.loginValidate(user)); return
			 * ok(result);
			 */
			if (User.loginValidate(user)) {
				result.put("state", true);
				result.put("authToken", setToken);
				// result.put("id", ""+user.id);
				return ok(result);
			} else {
				// User.create(user);
				// result.put("status", "OK");
				result.put("state", false);
				if (whatError == 1)
					result.put("message", "Wrong password");
				else if (whatError == 2)
					result.put("message", "Invalid email id format");
				else if (whatError == 3)
					result.put("message", "This email id is not registered");
				return badRequest(result);
			}

		}
	}

	@Security.Authenticated(Secured.class)
	public static Result upload() throws IOException {
		MultipartFormData body = request().body().asMultipartFormData();
		String receivedHeader = request().getHeader("authToken");
		FilePart picture = body.getFile("picture");
		/*
		 * if(User.checkToken(receivedHeader)){
		 * 
		 * }else{ flash("error", "Unauthenticated User"); return ok("Error"); }
		 */
		if (picture != null) {
			File file = picture.getFile();
			BufferedImage fullImage = ImageIO.read(file);
			BufferedImage thumbnailImage = new BufferedImage(thumbnailSize,
					thumbnailSize, BufferedImage.TYPE_INT_RGB);

			Graphics g = thumbnailImage.createGraphics();
			g.drawImage(fullImage, 0, 0, thumbnailSize, thumbnailSize, null);
			g.dispose();

			IdGen generatedId = new IdGen();
			String imageFileName = generatedId.getId();
			File outputFile = new File("/home/arka/" + fullImagePath + "/"
					+ imageFileName + ".png");
			ImageIO.write(fullImage, "png", outputFile);

			File thumbnailOutputFile = new File("/home/arka/"
					+ thumbnailImagePath + "/" + imageFileName + ".png");
			ImageIO.write(thumbnailImage, "png", thumbnailOutputFile);

			UserImageIds uII = new UserImageIds();
			uII.user_id = loggedInUserId;
			uII.imageId = imageFileName;
			uII.fullImagePath = "/home/arka/" + fullImagePath + "/"
					+ imageFileName + ".png";
			uII.thumbnailImagePath = "/home/arka/" + thumbnailImagePath + "/"
					+ imageFileName + ".png";

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// get current date time with Date()
			Date date = new Date();
			// System.out.println(dateFormat.format(date));
			uII.createdAt = dateFormat.format(date);
			User.updateUpdatedAt(receivedHeader, dateFormat.format(date));
			uII.create(uII);

			return ok("File uploaded");
		} else {
			flash("error", "Missing file");
			// return redirect(routes.Application.index());
			return ok("Errorjhjh");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result tokenAuthenticate() {
		ObjectNode result = Json.newObject();
		result.put("state", true);
		return ok(result);
	}
	
	@Security.Authenticated(Secured.class)
	public static Result getImageIds(){
		UserImageIds uII = new UserImageIds();
		String receivedHeader = request().getHeader("authToken");
		Long id = User.getIdFromToken(receivedHeader);
		List<UserImageIds> lUII = uII.getImageIdsFromUserId(id);
		ObjectNode listImageIds = Json.newObject(); 
		int i = 0;
		for(UserImageIds element : lUII){
			listImageIds.put(""+ i++ , ""+element.imageId);
		}
		return ok(listImageIds);
	}
}
