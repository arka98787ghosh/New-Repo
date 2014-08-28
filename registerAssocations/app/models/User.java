package models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import play.db.ebean.Model;
import controllers.Application;

@SuppressWarnings("serial")
@Entity
public class User extends Model {
	@Id
	public Long id;
	@Column(nullable = false)
	public String email;
	@Column(nullable = false)
	public String password;
	@Column(nullable = false)
	public String created_at;
	@Column(nullable = false)
	public String updated_at;
	@Column
	public String auth_token;
	@Column
	public Long token_created_at;

	public static Finder<Long, User> find = new Finder<Long, User>(
			Long.class, User.class);
	
	public User(String email, String password, String createdAt, String updatedAt){
		this.email = email;
		this.password = password;
		this.created_at = createdAt;
		this.updated_at = updatedAt;
		this.save();
	}
	
	/*
	public static void create(User user) {
		user.save();
	}
	
	
	public static boolean registerValidate(User user) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (user.email.matches(EMAIL_REGEX)) {
			User foundUser = emailAuthenticate(user.email);
			if (foundUser == null) {
				return true;
			} else {
				Application.whatError = 1;
				return false;
			}
		} else {
			Application.whatError = 2;
			return false;
		}

	}
	*/

	public static boolean loginValidate(String email, String password) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		String genToken = UUID.randomUUID().toString();
		if (email.matches(EMAIL_REGEX)) {
			//User foundUser = emailAuthenticate(user.email);
			User foundUser = find.where().eq("email", email).findUnique();
			if (foundUser != null) {
				//User passwordCheck = passwordAuthenticate(user.email, user.password);
				User passwordCheck = find.where().eq("email", email).eq("password", password).findUnique();
				if (passwordCheck != null) {
					Application.loggedInUserId = foundUser.id;
					//updateToken(foundUser.id, genToken);
					foundUser.auth_token = genToken;
					foundUser.token_created_at = new Date().getTime();
					//foundUser.update(genToken);
					foundUser.save();
					Application.setToken = genToken;
					return true;
				} else {
					Application.whatError = 1;
					return false;
				}
			} else {
				Application.whatError = 3;
				return false;
			}
		} else {
			Application.whatError = 2;
			return false;
		}

	}

	public static User emailAuthenticate(String email) {
		return find.where().eq("email", email).findUnique();
	}

	public static User passwordAuthenticate(String email, String password) {
		return find.where().eq("email", email).eq("password", password)
				.findUnique();
	}

	public static void updateUpdatedAt(String token, String date) {
		User foundUser = find.where().eq("auth_token", token).findUnique();
		foundUser.updated_at = date;
		foundUser.save();
	}
	
	/*
	public static void updateToken(Long id, String token) {
		User foundUser = find.where().eq("id", id).findUnique();
		foundUser.auth_token = token;
		foundUser.token_created_at = new Date().getTime();
		foundUser.save();
	}
	*/
	
	public static boolean checkToken(String token) {
		User foundUser = find.where().eq("auth_token", token).findUnique();
		if (foundUser != null)
			return true;
		else
			return false;
	}

	public static boolean authenticateToken(String token) {
		User foundUser = find.where().eq("auth_token", token).findUnique();
		if (foundUser != null)
			return true;
		else
			return false;
	}

	public static Long getAccessInstant(String token) {
		User foundUser = find.where().eq("auth_token", token).findUnique();
		if (foundUser != null)
			return foundUser.token_created_at;
		else
			return new Date().getTime();
	}
	
	public static Long getIdFromToken(String token){
		User foundUser = find.where().eq("auth_token", token).findUnique();
		return foundUser.id;
	}
	
	public static String getEmailIdFromUserId(Long id){
		User foundUser = find.where().eq("id", id).findUnique();
		return foundUser.email;
	}
}
