package controllers;

import java.util.Date;
import org.codehaus.jackson.node.ObjectNode;
import play.Play;
import play.libs.Json;
import play.mvc.*;
import models.*;

public class Secured extends Security.Authenticator {

	@Override
	public String getUsername(Http.Context ctx) {
		String receivedToken = ctx.request().getHeader("authToken");
		Long timeDifference = new Date().getTime()
				- User.getAccessInstant(receivedToken);
		boolean expiryStatus = timeDifference > (Long.valueOf(Play
				.application().configuration().getString("sessionTimeOut"))) * 60 * 60 * 1000;
		if (User.authenticateToken(receivedToken) && !expiryStatus) {
			return receivedToken;
		} else {
			return null;
		}

	}

	@Override
	public Result onUnauthorized(Http.Context ctx) {
		ObjectNode result = Json.newObject();
		result.put("state", false);
		return unauthorized(result);
	}
}
