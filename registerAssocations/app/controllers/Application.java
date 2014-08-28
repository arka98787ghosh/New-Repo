package controllers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import models.IdGen;
import models.RegistrationIds;
import models.User;
import models.UserImageIds;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.F.Function;
import play.libs.Json;
import play.libs.WS;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;

public class Application extends Controller {
	public static int whatError = 0;
	public static Long loggedInUserId;
	public static String fullImagePath = "register/public/images/FullImages";
	public static String thumbnailImagePath = "register/public/images/ThumbnailImages";
	public static int thumbnailSize = 100;
	public static String setToken;
	static String registration_id;

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
		registration_id = json.findPath("registrationId").getTextValue();
		if (email == null) {
			result.put("status", "KO");
			result.put("message", "Missing parameter [name]");
			return badRequest(result);
		} else {
			//User user = new User();
			//user.email = email;
			MD5Password.passwordToHash = password;
			//user.password = MD5Password.hashPassword();
			String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			if (email.matches(EMAIL_REGEX) && User.find.where().eq("email", email).findUnique() == null) {
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				// get current date time with Date()
				Date date = new Date();
				// System.out.println(dateFormat.format(date));
				//user.created_at = dateFormat.format(date);
				//user.updated_at = dateFormat.format(date);
				//User.create(user);
				User user = new User(email, MD5Password.hashPassword(), dateFormat.format(date), dateFormat.format(date));
				result.put("state", true);
				result.put("status", "OK");
				result.put("message", "Hello " + email + " your password is "
						+ password);
				sendRegistrationNotification();
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
		String deviceRegistrationId = json.findPath("registrationId")
				.getTextValue();

		if (email == null) {
			result.put("status", "KO");
			result.put("message", "Missing parameter [name]");
			return badRequest(result);
		} else {
			
			//User user = new User();
			//user.email = email;
			MD5Password.passwordToHash = password;
			//user.password = MD5Password.hashPassword();
			
			if (User.loginValidate(email, MD5Password.hashPassword())) {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				// get current date time with Date()
				Date date = new Date();

				 /*
				registrationId.device_token = deviceRegistrationId;
				registrationId.email = email;
				registrationId.created_at = dateFormat.format(date);
				registrationId.updated_at = dateFormat.format(date);
				
				//User user = getUser();
				
				 */
				result.put("state", true);
				result.put("authToken", setToken);
				if (!RegistrationIds.emailAndDeviceToken(email,deviceRegistrationId)){
					RegistrationIds registrationId = new RegistrationIds(deviceRegistrationId, email, dateFormat.format(date), dateFormat.format(date));
				}
					//registrationId.create(registrationId, loggedInUserId);
				return ok(result);
			} else {
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
			// uII.user_id = loggedInUserId;
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
			uII.create(uII, loggedInUserId);
			// sendRegistrationNotification();
			sendUploadNotification(loggedInUserId);
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
	public static Result getImageUrls() throws UnknownHostException,
			SocketException {
		UserImageIds uII = new UserImageIds();
		String receivedHeader = request().getHeader("authToken");
		Long id = User.getIdFromToken(receivedHeader);
		// List<UserImageIds> lUII = uII.getImageIdsFromUserId(id);
		// List<UserImageIds> lUII = UserImageIds.find.where().eq("user_id",
		// id).findList();
		List<UserImageIds> lUII = uII.findUserIdsInvolving(id);
		ObjectNode listImageIds = Json.newObject();
		int i = 0;
		for (UserImageIds element : lUII) {
			// check if you can get port programmatically
			listImageIds.put("" + i++, "" + "http://" + IPTest.getIp()
					+ ":9000/assets/images/ThumbnailImages/" + element.imageId
					+ ".png");

		}
		return ok(listImageIds);
	}

	@BodyParser.Of(BodyParser.Json.class)
	@Security.Authenticated(Secured.class)
	public static Result getFullImageUrl() throws UnknownHostException,
			SocketException {
		JsonNode json = request().body().asJson();
		String receivedUrl = json.findPath("imageUrl").getTextValue();
		String imageId = modifyUrl(receivedUrl);
		ObjectNode fullImageUrl = Json.newObject();
		fullImageUrl.put("fullImageUrl", "http://" + IPTest.getIp()
				+ ":9000/assets/images/FullImages/" + imageId);
		return ok(fullImageUrl);
	}

	public static String modifyUrl(String imageUrl) {
		int start = imageUrl.lastIndexOf("/");
		return imageUrl.substring(start + 1);
	}

	public static Result sendRegistrationNotification() {
		return async(WS
				.url("https://android.googleapis.com/gcm/send")
				.setHeader("Authorization",
						"key=AIzaSyCR5pveDNWNdApiEBsumJJzsJRti9eNyQM")
				.setHeader("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8")
				.post("collapse_key=score_update&time_to_live=108&delay_while_idle=1&data=you have been succesfully registered&registration_id="
						+ registration_id)
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						return null;
					}
				}));
	}

	public static Result sendUploadNotification(Long id) {

		String registration_ids = null;
		// String email = User.getEmailIdFromUserId(id);
		// List<RegistrationIds> registrationIds =
		// RegistrationIds.getRegistrationIdsFromEmail(email);
		List<RegistrationIds> registrationIds = RegistrationIds
				.getRegistrationIds(loggedInUserId);

		for (RegistrationIds e : registrationIds) {
			registration_ids += "\"" + e.device_token + "\",";
		}

		registration_ids = registration_ids.substring(0,
				registration_ids.length() - 1);

		return async(WS
				.url("https://android.googleapis.com/gcm/send")
				.setHeader("Authorization",
						"key=AIzaSyCR5pveDNWNdApiEBsumJJzsJRti9eNyQM")
				.setHeader("Content-Type", "application/json")
				.post("{\"registration_ids\" : [" + registration_ids + "]}")
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						return null;
					}
				}));
	}
	
	public static User getUser(){
		return User.find.where().eq("id", loggedInUserId).findUnique();
	}
	
}
