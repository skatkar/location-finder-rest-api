/**
 * 
 */
package cs.locationfinder.eo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cs.locationfinder.utils.SolrObjMapper;
import cs.locationfinder.vo.LocationVO;



/**
 * @author CampusUser
 *
 */
@Component
public class LocationFinderEO {

	private static final Logger logger = LoggerFactory.getLogger(LocationFinderEO.class);
	
	@Autowired
	SolrClient solrClient;
	
	@Autowired
	String solr_username;
	
	@Autowired
	String solr_password;
	
	@Autowired
	String solr_collection;
	
	@Autowired
	@Qualifier("locationMapper")
	SolrObjMapper objectMapper;
	
	final String LOCATION_TYPE = "locationType:";
	final String DESCRIPTION = "description:";
	final String ID = "id:";
	
	public List<LocationVO> getLocationsById(String id) throws Exception {
		
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		final Map<String, String> queryParamMap = new HashMap<String, String>();
		queryParamMap.put("q", ID + id);
		queryParamMap.put("start","0");
		
		
		MapSolrParams queryParams = new MapSolrParams(queryParamMap);
		
		QueryResponse response;
		
		try {
			
			//response = solrClient.query("locations", queryParams);
			
			QueryRequest request = new QueryRequest(queryParams);
			request.setBasicAuthCredentials(solr_username, solr_password);
			response = request.process(solrClient, solr_collection);
			
			SolrDocumentList documents = response.getResults();
			logger.info(METHOD_NAME + " Number of documents fetched: {}", documents.getNumFound());
			return objectMapper.solrObjMapper(documents);
		} catch (SolrServerException e) {
			logger.error(METHOD_NAME + " SolrServerException while getting the location by id ", e);
			throw e;
		} catch(IOException e) {
			logger.error(METHOD_NAME + " IOException while getting the location by id ", e);
			throw e;
		}
		
				
	}

	public List<LocationVO> getLocationsByType(String type, String limit) throws Exception {

		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		final Map<String, String> queryParamMap = new HashMap<String, String>();
		queryParamMap.put("q", LOCATION_TYPE + type);
		queryParamMap.put("start","0");
		queryParamMap.put("rows",limit);
		
		MapSolrParams queryParams = new MapSolrParams(queryParamMap);

		QueryResponse response;

		try {
			
			//response = solrClient.query("locations", queryParams);
			
			QueryRequest request = new QueryRequest(queryParams);
			request.setBasicAuthCredentials(solr_username, solr_password);
			response = request.process(solrClient, solr_collection);
			
			SolrDocumentList documents = response.getResults();

			logger.info(METHOD_NAME + " Number of documents fetched: {}", documents.getNumFound());
			return objectMapper.solrObjMapper(documents);
		} catch (SolrServerException e) {
			logger.error(METHOD_NAME + " SolrServerException while fetching records by type ", e);
			throw e;
		} catch(IOException e) {
			logger.error(METHOD_NAME + " IOException while fetching records by type ", e);
			throw e;
		}
		

	}
	
	public List<LocationVO> getAllLocations() throws Exception {
		
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		final Map<String, String> queryParamMap = new HashMap<String, String>();
		queryParamMap.put("q", "*:*");
		MapSolrParams queryParams = new MapSolrParams(queryParamMap);
		
		QueryResponse response;
		
		try {
			//response = solrClient.query("locations", queryParams);
			
			QueryRequest request = new QueryRequest(queryParams);
			request.setBasicAuthCredentials(solr_username, solr_password);
			response = request.process(solrClient,solr_collection);
			
			SolrDocumentList documents = response.getResults();
			
			logger.info(METHOD_NAME + " Number of documents fetched: {}", documents.getNumFound());
			return objectMapper.solrObjMapper(documents);
		} catch (SolrServerException e) {
			logger.error(METHOD_NAME + " SolrServerException while fetching all documents ", e);
			throw e;
		} catch(IOException e) {
			logger.error(METHOD_NAME + " IOException while fetching all documents ", e);
			throw e;
		}
		
	}
	
	public LocationVO save(LocationVO locationVo) throws Exception {
				
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	      
		try {
			locationVo.setId(UUID.randomUUID().toString());
		
			//solrClient.addBean("locations", locationVo);
			//solrClient.commit("locations");
			
			UpdateRequest request = new UpdateRequest();
			
		    request.add(solrClient.getBinder().toSolrInputDocument(locationVo));
		    request.setCommitWithin(-1);
		    
		    //Code to add the bean and commit it
		    request.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		    request.setBasicAuthCredentials(solr_username, solr_password);
		    request.process(solrClient, solr_collection);
			
			logger.info(METHOD_NAME + " New document has been added to the collection");
			return locationVo;
		} catch (SolrServerException e) {
			logger.error(METHOD_NAME + " SolrServerException while saving in Solr ", e);
			throw e;
		} catch(IOException e) {
			logger.error(METHOD_NAME + " IOException while saving in Solr ", e);
			throw e;
		}
		
	}

	public List<LocationVO> searchDocuments(String query, String limit) throws Exception{
		
		final String METHOD_NAME = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
		
		final Map<String, String> queryParamMap = new HashMap<String, String>();
		queryParamMap.put("q", LOCATION_TYPE + query + " OR " + DESCRIPTION + query);
		queryParamMap.put("start","0");
		queryParamMap.put("rows",limit);
		
		MapSolrParams queryParams = new MapSolrParams(queryParamMap);
		
		QueryResponse response;
		
		try {
			
			QueryRequest request = new QueryRequest(queryParams);
			request.setBasicAuthCredentials(solr_username, solr_password);
			response = request.process(solrClient, solr_collection);
			
			SolrDocumentList documents = response.getResults();
			logger.info(METHOD_NAME + " Number of documents fetched: {}", documents.getNumFound());
			return objectMapper.solrObjMapper(documents);
		} catch (SolrServerException e) {
			logger.error(METHOD_NAME + " SolrServerException while getting locations by search string ", e);
			throw e;
		} catch(IOException e) {
			logger.error(METHOD_NAME + " IOException while getting locations by search string ", e);
			throw e;
		}
		
	}
	
	/*
	 * private List<LocationVO> solrObjMapper(SolrDocumentList documents){
	 * List<LocationVO> locations = new ArrayList<LocationVO>();
	 * 
	 * for (SolrDocument document : documents) { locations.add(new
	 * LocationVO((String) document.getFirstValue("id"), (Double)
	 * document.getFirstValue("latitude"), (Double)
	 * document.getFirstValue("longitude"), (String)
	 * document.getFirstValue("description"), (String)
	 * document.getFirstValue("locationType"), null));
	 * 
	 * }
	 * 
	 * return locations; }
	 */
	
	/*
	public List<LocationVO> getAllLocations() { 
		  final SolrClient client = getSolrClient(); 
		  SolrQuery query = new SolrQuery("*:*");
	  
	  try {
	  
		  QueryResponse response = client.query("locations", query); List<LocationVO>
		  locations = response.getBeans(LocationVO.class);
	  
	  
		  for (LocationVO employee : locations) { 
			  System.out.println(employee); 
		  }
	  
		  return locations;
	  
	  } catch (SolrServerException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); 
	  } catch (IOException e) { 
	      e.printStackTrace(); 
	      
	  } 
	  return null; 
	 }*/
	
}

