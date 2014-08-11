package controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.nio.file.StandardOpenOption.*;

import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.libs.Json;

import models.User;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class Application extends Controller {
	public static int whatError = 0;

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
				result.put("message", "should login");
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
	
	
	public static Result upload() {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture");
		if (picture != null) {
			String fileName = picture.getFilename();
			String contentType = picture.getContentType();
			File file = picture.getFile();
			return ok("File uploaded");
		} else {
			flash("error", "Missing file");
			// return redirect(routes.Application.index());
			return ok("Error");
		}
	}

}
