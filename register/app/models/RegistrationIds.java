package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
	
	
	public static Finder<String, RegistrationIds> find = new Finder<String, RegistrationIds>(
			String.class, RegistrationIds.class);
	
	public void create(RegistrationIds registrationId){
		registrationId.save();
	}
	
	public static List<RegistrationIds> getRegistrationIdsFromEmail(String email) {
		List<RegistrationIds> registrationIds = find.where().eq("email", email)
				.findList();
		return registrationIds;
	}
	
	public static boolean emailAndDeviceToken(String email, String deviceToken){
		RegistrationIds regId = find.where().eq("email", email).eq("device_token", deviceToken).findUnique();
		if(regId != null)
			return true;
		else
			return false;
	}

}
