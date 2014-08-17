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
@javax.persistence.Table(name = "glassware", schema = "public")
public class Glassware implements java.io.Serializable {

	private static final long serialVersionUID = -915521645;

	private java.lang.Long     id;
	private java.lang.Integer  version;
	private java.lang.String   name;
	private java.lang.String   description;
	private java.lang.Boolean  searchable;
	private java.lang.String   breweryDbId;
	private java.sql.Timestamp breweryDbLastUpdated;
	private java.sql.Timestamp createdDate;
	private java.sql.Timestamp modifiedDate;

	public Glassware() {}

	public Glassware(
		java.lang.Long     id,
		java.lang.Integer  version,
		java.lang.String   name,
		java.lang.String   description,
		java.lang.Boolean  searchable,
		java.lang.String   breweryDbId,
		java.sql.Timestamp breweryDbLastUpdated,
		java.sql.Timestamp createdDate,
		java.sql.Timestamp modifiedDate
	) {
		this.id = id;
		this.version = version;
		this.name = name;
		this.description = description;
		this.searchable = searchable;
		this.breweryDbId = breweryDbId;
		this.breweryDbLastUpdated = breweryDbLastUpdated;
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

	@javax.persistence.Column(name = "name", nullable = false, length = 100)
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	@javax.persistence.Column(name = "description")
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	@javax.persistence.Column(name = "searchable", nullable = false)
	@javax.validation.constraints.NotNull
	public java.lang.Boolean getSearchable() {
		return this.searchable;
	}

	public void setSearchable(java.lang.Boolean searchable) {
		this.searchable = searchable;
	}

	@javax.persistence.Column(name = "brewery_db_id", length = 64)
	@javax.validation.constraints.Size(max = 64)
	public java.lang.String getBreweryDbId() {
		return this.breweryDbId;
	}

	public void setBreweryDbId(java.lang.String breweryDbId) {
		this.breweryDbId = breweryDbId;
	}

	@javax.persistence.Column(name = "brewery_db_last_updated")
	public java.sql.Timestamp getBreweryDbLastUpdated() {
		return this.breweryDbLastUpdated;
	}

	public void setBreweryDbLastUpdated(java.sql.Timestamp breweryDbLastUpdated) {
		this.breweryDbLastUpdated = breweryDbLastUpdated;
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
