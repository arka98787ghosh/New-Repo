package models;

import play.db.ebean.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class UserImageIds extends Model {
	@Id
	public Long id;
	@Column(nullable = false)
	public String imageId;
}