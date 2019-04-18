/**
 * 
 */
package cs.locationfinder.vo;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author CampusUser
 *
 */
public class LocationVO {

	@Field
	private String id;
	
	@Field
	private double latitude;
	
	@Field
	private double longitude;
	
	@Field
	private String description;
	
	@Field
	private String locationType;
	private List<String> reviews;
	
	@Field
	private String city;
	
	/**
	 * 
	 */
	public LocationVO() {
		super();
	}


	/**
	 * @param id
	 * @param longitude
	 * @param latitude
	 * @param description
	 * @param locationType
	 * @param reviews
	 */
	public LocationVO(String id, double latitude, double longitude,String description, String locationType,
			List<String> reviews) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.description = description;
		this.locationType = locationType;
		this.reviews = reviews;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	
	public void setId(String id) {
		this.id = id;
	}

	
	
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}


	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}


	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}


	/**
	 * @param longitude the longitude to set
	 */
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}


	/**
	 * @param latitude the latitude to set
	 */
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the locationType
	 */
	public String getLocationType() {
		return locationType;
	}


	/**
	 * @param locationType the locationType to set
	 */
	
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}


	/**
	 * @return the reviews
	 */
	public List<String> getReviews() {
		return reviews;
	}


	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}
	
	
}
