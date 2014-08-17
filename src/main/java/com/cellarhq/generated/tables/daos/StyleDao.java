/**
 * This class is generated by jOOQ
 */
package com.cellarhq.generated.tables.daos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.1" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StyleDao extends org.jooq.impl.DAOImpl<com.cellarhq.generated.tables.records.StyleRecord, com.cellarhq.generated.tables.pojos.Style, java.lang.Long> {

	/**
	 * Create a new StyleDao without any configuration
	 */
	public StyleDao() {
		super(com.cellarhq.generated.tables.Style.STYLE, com.cellarhq.generated.tables.pojos.Style.class);
	}

	/**
	 * Create a new StyleDao with an attached configuration
	 */
	public StyleDao(org.jooq.Configuration configuration) {
		super(com.cellarhq.generated.tables.Style.STYLE, com.cellarhq.generated.tables.pojos.Style.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.Long getId(com.cellarhq.generated.tables.pojos.Style object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchById(java.lang.Long... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.cellarhq.generated.tables.pojos.Style fetchOneById(java.lang.Long value) {
		return fetchOne(com.cellarhq.generated.tables.Style.STYLE.ID, value);
	}

	/**
	 * Fetch records that have <code>version IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchByVersion(java.lang.Integer... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.VERSION, values);
	}

	/**
	 * Fetch records that have <code>category_id IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchByCategoryId(java.lang.Long... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.CATEGORY_ID, values);
	}

	/**
	 * Fetch records that have <code>name IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchByName(java.lang.String... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.NAME, values);
	}

	/**
	 * Fetch records that have <code>description IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchByDescription(java.lang.String... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.DESCRIPTION, values);
	}

	/**
	 * Fetch records that have <code>searchable IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchBySearchable(java.lang.Boolean... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.SEARCHABLE, values);
	}

	/**
	 * Fetch records that have <code>brewery_db_id IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchByBreweryDbId(java.lang.String... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.BREWERY_DB_ID, values);
	}

	/**
	 * Fetch records that have <code>brewery_db_last_updated IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchByBreweryDbLastUpdated(java.sql.Timestamp... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.BREWERY_DB_LAST_UPDATED, values);
	}

	/**
	 * Fetch records that have <code>created_date IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchByCreatedDate(java.sql.Timestamp... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.CREATED_DATE, values);
	}

	/**
	 * Fetch records that have <code>modified_date IN (values)</code>
	 */
	public java.util.List<com.cellarhq.generated.tables.pojos.Style> fetchByModifiedDate(java.sql.Timestamp... values) {
		return fetch(com.cellarhq.generated.tables.Style.STYLE.MODIFIED_DATE, values);
	}
}
