package models;

import play.db.ebean.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class UserImageIds extends Model {
	
	@Id
	public Long id;
	@Column(nullable = false)
	public Long user_id;
	@Column(nullable = false)
	public String imageId;
	@Column(nullable = false)
	public String fullImagePath;
	@Column(nullable = false)
	public String thumbnailImagePath;
	@Column(nullable = false)
	public String createdAt;

	public void create(UserImageIds userImageId) {
		userImageId.save();
	}
}
