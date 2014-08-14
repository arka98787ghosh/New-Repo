package models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import controllers.Application;

import play.db.ebean.Model;

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

	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);

	public static void create(User user) {
		user.save();
	}

	public static boolean registerValidate(User user) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		/*
		 * if (user.email.matches(EMAIL_REGEX)) { String url =
		 * "jdbc:mysql://localhost/CameraApp?characterEncoding=UTF-8"; String
		 * dbName = "CameraApp"; String driver = "com.mysql.jdbc.Driver"; String
		 * dbUserName = "arka"; String dbPassword = "Bibliophileno1"; final
		 * String queryCheck = "SELECT * from users WHERE email = ?"; int count
		 * = 0; try { Class.forName(driver).newInstance(); Connection conn =
		 * DriverManager .getConnection("jdbc:mysql://localhost/CameraApp?" +
		 * "user=" + dbUserName + "&password=" + dbPassword); PreparedStatement
		 * ps = conn.prepareStatement(queryCheck); ps.setString(1, user.email);
		 * ResultSet resultSet = ps.executeQuery();
		 * 
		 * if (resultSet.next()) { // System.out.println("reaching"); count =
		 * resultSet.getInt(1); }
		 * 
		 * conn.close(); Application.whatError = 1; if (count > 0) return false;
		 * else return true;
		 * 
		 * } catch (Exception e) { e.printStackTrace(); return false; } } else {
		 * Application.whatError = 2; return false; }
		 */

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

	public static boolean loginValidate(User user) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		/*
		 * if (user.email.matches(EMAIL_REGEX)) { String url =
		 * "jdbc:mysql://localhost/CameraApp?characterEncoding=UTF-8"; String
		 * dbName = "CameraApp"; String driver = "com.mysql.jdbc.Driver"; String
		 * dbUserName = "arka"; String dbPassword = "Bibliophileno1"; String
		 * queryCheckJustEmail = "SELECT * from users WHERE email = ?"; String
		 * queryCheckEmailPassword =
		 * "SELECT * from users WHERE email = ? && password = ?"; // int count =
		 * 0; try { Class.forName(driver).newInstance(); Connection conn =
		 * DriverManager .getConnection("jdbc:mysql://localhost/CameraApp?" +
		 * "user=" + dbUserName + "&password=" + dbPassword); PreparedStatement
		 * psJE = conn .prepareStatement(queryCheckJustEmail); psJE.setString(1,
		 * user.email);
		 * 
		 * PreparedStatement psEP = conn
		 * .prepareStatement(queryCheckEmailPassword); psEP.setString(1,
		 * user.email); psEP.setString(2, user.password); ResultSet resultSetJE
		 * = psJE.executeQuery(); ResultSet resultSetEP = psEP.executeQuery();
		 * if (resultSetJE.next()) { if (resultSetEP.next()) { conn.close();
		 * return true; } else { Application.whatError = 1; return false; } }
		 * else { Application.whatError = 3; return false; }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); return false; } } else {
		 * Application.whatError = 2; return false; }
		 */
		String genToken = UUID.randomUUID().toString();
		if (user.email.matches(EMAIL_REGEX)) {
			User foundUser = emailAuthenticate(user.email);
			if (foundUser != null) {
				User passwordCheck = passwordAuthenticate(user.email,
						user.password);
				if (passwordCheck != null) {
					Application.loggedInUserId = foundUser.id;
					updateToken(foundUser.id, genToken);
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

	public static void updateToken(Long id, String token) {
		User foundUser = find.where().eq("id", id).findUnique();
		foundUser.auth_token = token;
		foundUser.token_created_at = new Date().getTime();
		foundUser.save();
	}

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

}
