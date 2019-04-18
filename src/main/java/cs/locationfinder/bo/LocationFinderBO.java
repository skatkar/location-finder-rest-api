/**
 * 
 */
package cs.locationfinder.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cs.locationfinder.eo.LocationFinderEO;
import cs.locationfinder.vo.LocationVO;

/**
 * @author CampusUser
 *
 */

@Component
public class LocationFinderBO {
	
	//private static final Logger logger = LoggerFactory.getLogger(LocationFinderBO.class);
	
	
	@Autowired
	LocationFinderEO locationFinderEO;
	
	public List<LocationVO> getLocationsById(String id) throws Exception {
		return locationFinderEO.getLocationsById(id);
	}

	public List<LocationVO> getLocationsByType(String type,String limit) throws Exception {
		return locationFinderEO.getLocationsByType(type,limit);
	}

	 
	public LocationVO save(LocationVO locationVo) throws Exception {
		return locationFinderEO.save(locationVo);
	}

	public List<LocationVO> searchDocuments(String query, String limit) throws Exception {
		return locationFinderEO.searchDocuments(query,limit);
	}
	
}
