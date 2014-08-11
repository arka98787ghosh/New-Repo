package models;

import play.db.ebean.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class UserImageIds extends Model {
	
	@Id
	public Long id;
	@Column(nullable = false)
	public String imageId;
	@Column(nullable = false)
	public String fullImagePath;
	@Column(nullable = false)
	public String thumbnailImagePath;
	@Column(nullable = false)
	public String created_at;

	public void create(UserImageIds userImageId) {
		userImageId.save();
	}
}
