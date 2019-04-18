package cs.locationfinder.main;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cs.locationfinder.controller.LocationFinderController;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestPropertySource(properties = {"solr.user = test","solr.password = test","solr.collection = locations"})
public class LocationFinderApiApplicationTests {

	
	
	@Autowired 
	private LocationFinderController controller;
	 
	
	//static String baseURL;
	
	/*
	 
	@BeforeClass
	public static void init() {
		//baseURL = "https://location-finder-231303.appspot.com";
		baseURL = "http://localhost:8080";
	}*/
	
	
	  @Test
	  @Ignore
	  public void contextLoads() throws Exception{
		  assertThat(controller).isNotNull(); 
	  }
	 
	
	/*
	@Test
	public void testLocationTypeListSuccess() throws URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();
		
		final String url = baseURL + "/api/v1.0/locations?locationType=school";
		URI uri = new URI(url);
		
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void testLocationTypeListFails() throws URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();
		
		final String url = baseURL + "/api/v1.0/locations?locationType=office";
		URI uri = new URI(url);
		
		try {
			
			restTemplate.getForEntity(uri, String.class);
			Assert.fail();
		}catch(HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
		
				
		
	}*/

}

