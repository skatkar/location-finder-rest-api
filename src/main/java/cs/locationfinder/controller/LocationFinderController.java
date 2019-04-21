/**
 * 
 */
package cs.locationfinder.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cs.locationfinder.bo.LocationFinderBO;
import cs.locationfinder.exception.LocationNotFoundException;
import cs.locationfinder.vo.LocationVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



/**
 * @author CampusUser
 *
 */

@RestController
public class LocationFinderController {

	private static final Logger logger = LoggerFactory.getLogger(LocationFinderController.class);
	
	@Autowired
	LocationFinderBO locationFinderBo;
	
		
	/* Other approach
	 * Use URL like this- "/locations?id={id}"
	 * For parameter us- @RequestParam String id
	*/
	
	@GetMapping("/api/v1.0/status")
	public String checkHealth() {
		logger.info("REST service is accessible");
		return LocalDateTime.now().toString();
	}
	
	
	
	@CrossOrigin
	@ApiOperation(value = "View a list of locations by id", response = ResponseEntity.class)
	@ApiResponses(value = {
			 @ApiResponse(code = 200, message = "Successfully retrieved list"),
			 @ApiResponse(code = 404, message = "Requested location not found at Solr"),
			 @ApiResponse(code = 500, message = "Server side error")
	})
	@GetMapping("/api/v1.0/location/{id}")
	public ResponseEntity<List<LocationVO>> getLocationsById(@PathVariable String id) throws LocationNotFoundException,Exception{
		
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		logger.debug(METHOD_NAME + " Starting with search by id");
		
		
		
		try {
			List<LocationVO> responseVO = locationFinderBo.getLocationsById(id);
			HttpHeaders headers = new HttpHeaders();
			
			if(responseVO.size() != 0) {
				logger.info(METHOD_NAME + " Successfully retrieved data from Solr");
				URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
					      .build()
					      .toUri();
				
					headers.setLocation(locationUri);
					return new ResponseEntity<List<LocationVO>>(responseVO, headers, HttpStatus.OK);
			}else {
				logger.error(METHOD_NAME + " Requested location not found at Solr");
				throw new LocationNotFoundException("Requested location not found at Solr");
			}
		}catch(Exception ex) {
			throw ex;
		}
		
		
	}
	
	@CrossOrigin
	@ApiOperation(value = "Delete a location by id", response = ResponseEntity.class)
	@ApiResponses(value = {
			 @ApiResponse(code = 200, message = "Successfully deleted location"),
			 @ApiResponse(code = 404, message = "Requested location id not found at Solr"),
			 @ApiResponse(code = 500, message = "Server side error")
	})
	@DeleteMapping("/api/v1.0/location/{id}")
	public ResponseEntity<Map<String,String>> deleteLocationById(@PathVariable String id) throws LocationNotFoundException,Exception{
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		logger.debug(METHOD_NAME + " Starting with delete by id");
		
		try {
			
			 locationFinderBo.deleteLocationById(id);
			
			//if(numberOfDocuments != 0) {
				logger.info(METHOD_NAME + " Deleted data from Solr");
				Map<String,String> response = new HashMap<String, String>();
				response.put("message", "success deleting data");
				return ResponseEntity.accepted().body(response);
			//}
			/*else {
				logger.error(METHOD_NAME + " Requested location not found at Solr");
				throw new LocationNotFoundException("Requested location not found at Solr");
			}*/
		}catch(Exception ex) {
			throw ex;
		}
		
		
	}
	
	
	@CrossOrigin
	@ApiOperation(value = "View a list of locations by type", response = ResponseEntity.class)
	@ApiResponses(value = {
			 @ApiResponse(code = 200, message = "Successfully retrieved list"),
			 @ApiResponse(code = 400, message = "Required String parameter 'locationType' is not present"),
			 @ApiResponse(code = 404, message = "Requested location type not found at Solr"),
			 @ApiResponse(code = 500, message = "Server side error")
	})
	@GetMapping("/api/v1.0/locations")
	public ResponseEntity<List<LocationVO>> getLocationsByType(
			@RequestParam(value="locationType", required=true) String type, 
			@RequestParam(value="limit", defaultValue="10") String limit) throws Exception{
		
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		logger.debug(METHOD_NAME + " Starting with search by type");
		
		try {
			List<LocationVO> responseVO = locationFinderBo.getLocationsByType(type,limit);
			HttpHeaders headers = new HttpHeaders();
			
			if(responseVO.size() != 0) {
				logger.info(METHOD_NAME + " Successfully retrieved data from Solr");
				URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
					      .build()
					      .toUri();
				
					headers.setLocation(locationUri);
					return new ResponseEntity<List<LocationVO>>(responseVO, headers, HttpStatus.OK);
			}else {
				logger.error(METHOD_NAME + " Requested location type not found at Solr");
				throw new LocationNotFoundException("Requested location not found at Solr");
			}
		}catch(Exception ex) {
			throw ex;
		}
			
	}
	
	
	
	@CrossOrigin
	@ApiOperation(value = "General search by using the search string", response = List.class)
	@ApiResponses(value = {
			 @ApiResponse(code = 200, message = "Successfully performed search operation"),
			 @ApiResponse(code = 404, message = "Requested location not found at Solr"),
			 @ApiResponse(code = 500, message = "Server side error"),
	})
	@GetMapping("/api/v1.0/search")
	public ResponseEntity<List<LocationVO>> searchDocuments(
			@RequestParam(value="query", required = true) String query,
			@RequestParam(value="limit", defaultValue="10") String limit) throws Exception{
		
		// For this functionality the query should perform like this:
		// locationType:searchText OR description:searchText OR reviews:searchText
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		
		logger.debug(METHOD_NAME + " Starting with search by user provided search string");
		
		try {
			List<LocationVO> responseVO = locationFinderBo.searchDocuments(query,limit);
			HttpHeaders headers = new HttpHeaders();
			
			if(responseVO.size() != 0) {
				logger.info(METHOD_NAME + " Successfully retrieved data from Solr");
				URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
					      .build()
					      .toUri();
				
					headers.setLocation(locationUri);
					return new ResponseEntity<List<LocationVO>>(responseVO, headers, HttpStatus.OK);
			}else {
				logger.error(METHOD_NAME + " Requested location not found at Solr");
				throw new LocationNotFoundException("Requested location not found at Solr");
			}
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	
	
	@CrossOrigin
	@ApiOperation(value = "Adds a new location to the repository", response = ResponseEntity.class)
	@ApiResponses(value = {
			 @ApiResponse(code = 201, message = "Successfully added new location"),
			 @ApiResponse(code = 500, message = "Server side error while adding to the repository"),
	})
	@PostMapping("/api/v1.0/location")
	public ResponseEntity<LocationVO> addNewLocation(@RequestBody LocationVO locationVo) throws Exception {
		
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		logger.info(METHOD_NAME + " A new location will be added to the collection");
		/* Approach 1
		boolean status = locationFinderBo.addNewLocation(locationVo);
		
		if(status) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(locationVo.getId()).toUri();
			return ResponseEntity.created(location).build();
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}*/
		
		
		// Approach 2
		
		
		try {
			LocationVO responseVo = locationFinderBo.save(locationVo);
			HttpHeaders headers = new HttpHeaders();
			
			logger.info(METHOD_NAME + " Successfully added data to Solr collection");
			URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
				      .path(String.valueOf(responseVo.getId()))
				      .build()
				      .toUri();
			
			headers.setLocation(locationUri);
			return new ResponseEntity<LocationVO>(responseVo, headers, HttpStatus.CREATED);
			
		}catch(Exception ex) {
			throw ex;
		}
	}
}
