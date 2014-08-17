/**
 * This class is generated by jOOQ
 */
package com.cellarhq.generated.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.1" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
@javax.persistence.Entity
@javax.persistence.Table(name = "cellar", schema = "public")
public class Cellar implements java.io.Serializable {

	private static final long serialVersionUID = 470741520;

	private java.lang.Long     id;
	private java.lang.Integer  version;
	private java.lang.Long     photoId;
	private java.lang.String   screenName;
	private java.lang.String   displayName;
	private java.lang.String   location;
	private java.lang.String   website;
	private java.lang.String   bio;
	private java.lang.Boolean  updateFromNetwork;
	private java.lang.String   contactEmail;
	private java.lang.Boolean  private_;
	private java.sql.Timestamp lastLogin;
	private java.lang.Object   lastLoginIp;
	private java.sql.Timestamp createdDate;
	private java.sql.Timestamp modifiedDate;

	public Cellar() {}

	public Cellar(
		java.lang.Long     id,
		java.lang.Integer  version,
		java.lang.Long     photoId,
		java.lang.String   screenName,
		java.lang.String   displayName,
		java.lang.String   location,
		java.lang.String   website,
		java.lang.String   bio,
		java.lang.Boolean  updateFromNetwork,
		java.lang.String   contactEmail,
		java.lang.Boolean  private_,
		java.sql.Timestamp lastLogin,
		java.lang.Object   lastLoginIp,
		java.sql.Timestamp createdDate,
		java.sql.Timestamp modifiedDate
	) {
		this.id = id;
		this.version = version;
		this.photoId = photoId;
		this.screenName = screenName;
		this.displayName = displayName;
		this.location = location;
		this.website = website;
		this.bio = bio;
		this.updateFromNetwork = updateFromNetwork;
		this.contactEmail = contactEmail;
		this.private_ = private_;
		this.lastLogin = lastLogin;
		this.lastLoginIp = lastLoginIp;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	@javax.persistence.Id
	@javax.persistence.Column(name = "id", unique = true, nullable = false, precision = 64)
	@javax.validation.constraints.NotNull
	public java.lang.Long getId() {
		return this.id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	@javax.persistence.Column(name = "version", nullable = false, precision = 32)
	@javax.validation.constraints.NotNull
	public java.lang.Integer getVersion() {
		return this.version;
	}

	public void setVersion(java.lang.Integer version) {
		this.version = version;
	}

	@javax.persistence.Column(name = "photo_id", precision = 64)
	public java.lang.Long getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(java.lang.Long photoId) {
		this.photoId = photoId;
	}

	@javax.persistence.Column(name = "screen_name", unique = true, nullable = false, length = 64)
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 64)
	public java.lang.String getScreenName() {
		return this.screenName;
	}

	public void setScreenName(java.lang.String screenName) {
		this.screenName = screenName;
	}

	@javax.persistence.Column(name = "display_name", length = 60)
	@javax.validation.constraints.Size(max = 60)
	public java.lang.String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(java.lang.String displayName) {
		this.displayName = displayName;
	}

	@javax.persistence.Column(name = "location", length = 100)
	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getLocation() {
		return this.location;
	}

	public void setLocation(java.lang.String location) {
		this.location = location;
	}

	@javax.persistence.Column(name = "website", length = 100)
	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getWebsite() {
		return this.website;
	}

	public void setWebsite(java.lang.String website) {
		this.website = website;
	}

	@javax.persistence.Column(name = "bio")
	public java.lang.String getBio() {
		return this.bio;
	}

	public void setBio(java.lang.String bio) {
		this.bio = bio;
	}

	@javax.persistence.Column(name = "update_from_network", nullable = false)
	@javax.validation.constraints.NotNull
	public java.lang.Boolean getUpdateFromNetwork() {
		return this.updateFromNetwork;
	}

	public void setUpdateFromNetwork(java.lang.Boolean updateFromNetwork) {
		this.updateFromNetwork = updateFromNetwork;
	}

	@javax.persistence.Column(name = "contact_email", length = 255)
	@javax.validation.constraints.Size(max = 255)
	public java.lang.String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(java.lang.String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@javax.persistence.Column(name = "private", nullable = false)
	@javax.validation.constraints.NotNull
	public java.lang.Boolean getPrivate() {
		return this.private_;
	}

	public void setPrivate(java.lang.Boolean private_) {
		this.private_ = private_;
	}

	@javax.persistence.Column(name = "last_login")
	public java.sql.Timestamp getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(java.sql.Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	@javax.persistence.Column(name = "last_login_ip")
	public java.lang.Object getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(java.lang.Object lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@javax.persistence.Column(name = "created_date", nullable = false)
	@javax.validation.constraints.NotNull
	public java.sql.Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(java.sql.Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@javax.persistence.Column(name = "modified_date", nullable = false)
	@javax.validation.constraints.NotNull
	public java.sql.Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(java.sql.Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}