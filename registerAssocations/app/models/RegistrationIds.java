package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import controllers.Application;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class RegistrationIds extends Model {
	
	@Id
	public Long id;
	//@Column(nullable = false)
	public String device_token;
	//@Column(nullable = false)
	public String email;
	//@Column(nullable=false)
	public String created_at;
	//@Column(nullable=false)
	public String updated_at;
	@ManyToOne
	public User user;
	
	public RegistrationIds(String device_token, String email, String createdAt, String updatedAt){
		this.device_token = device_token;
		this.email = email;
		this.created_at = createdAt;
		this.updated_at = updatedAt;
		this.user = User.find.ref(Application.loggedInUserId);
		this.save();
	}
	
	public static Finder<String, RegistrationIds> find = new Finder<String, RegistrationIds>(
			String.class, RegistrationIds.class);
	/*
	public void create(RegistrationIds registrationId, Long id){
		registrationId.user = User.find.ref(id);
		registrationId.save();
	}
	*/
	/*
	public static List<RegistrationIds> getRegistrationIdsFromEmail(String email) {
		List<RegistrationIds> registrationIds = find.where().eq("email", email)
				.findList();
		return registrationIds;
	}
	*/
	
	public static List<RegistrationIds> getRegistrationIds(Long id){
		return find.where().eq("user.id", id).findList();
	}
	
	public static boolean emailAndDeviceToken(String email, String deviceToken){
		RegistrationIds regId = find.where().eq("email", email).eq("device_token", deviceToken).findUnique();
		if(regId != null)
			return true;
		else
			return false;
	}

}
