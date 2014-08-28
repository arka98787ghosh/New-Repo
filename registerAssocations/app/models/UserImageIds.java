package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class UserImageIds extends Model {

	@Id
	public Long id;
	//@Column(nullable = false)
	//public Long user_id;
	//@Column(nullable = false)
	public String imageId;
	//@Column(nullable = false)
	public String fullImagePath;
	//@Column(nullable = false)
	public String thumbnailImagePath;
	//@Column(nullable = false)
	public String createdAt;
	@ManyToOne
	public User user;

	public static Finder<String, UserImageIds> find = new Finder<String, UserImageIds>(
			String.class, UserImageIds.class);

	public void create(UserImageIds userImageId, Long id) {
		userImageId.user = User.find.ref(id);
		userImageId.save();
	}
	
	/*
	public List<UserImageIds> getImageIdsFromUserId(Long id) {
		List<UserImageIds> userImageIds = find.where().eq("user_id", id)
				.findList();
		return userImageIds;
	}
	*/
	
	public List<UserImageIds> findUserIdsInvolving(Long id){
		return find.where().eq("user.id", id).findList();
	}
	
}
