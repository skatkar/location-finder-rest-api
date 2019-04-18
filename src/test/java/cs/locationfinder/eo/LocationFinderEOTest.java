package cs.locationfinder.eo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;

import cs.locationfinder.fixture.LocationFinderFixture;
import cs.locationfinder.utils.SolrObjMapper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class LocationFinderEOTest {

	@Tested
	LocationFinderEO locationFinderEO;

	@Mocked
	SolrClient solrClient;

	@Mocked
	QueryRequest queryRequest;

	@Mocked
	QueryResponse response;

	@Injectable
	SolrObjMapper objectMapper;

	/*
	 * @Before public void setUp() { solrClient = new
	 * HttpSolrClient.Builder("").withConnectionTimeout(10000).withSocketTimeout(
	 * 60000).build(); }
	 */

	@Test
	public void getLocationById_ReturnLocationList_ValidInput(@Mocked SolrDocumentList documents) throws Exception {

		new Expectations() {
			{
				queryRequest.setBasicAuthCredentials(anyString, anyString);
				queryRequest.process((SolrClient) any, anyString);
				result = response;
				response.getResults();
				result = documents;
				documents.getNumFound();
				result = 1;
				objectMapper.solrObjMapper((SolrDocumentList) any);
				result = LocationFinderFixture.getLocationList();
			}
		};

		assertEquals(1, locationFinderEO.getLocationsById("fe26287a-3938-4d70-b616-57257e383237").size());
	}
	
	@Test(expected = SolrServerException.class)
	public void getLocationById_ThrowsSolrServerException() throws Exception {

		new Expectations() {
			{
				queryRequest.setBasicAuthCredentials(anyString, anyString);
				queryRequest.process((SolrClient) any, anyString);
				result = new SolrServerException("Test Exception");
			}
		};

		locationFinderEO.getLocationsById("fe26287a-3938-4d70-b616-57257e383237");
	}
	
	@Test(expected = IOException.class)
	public void getLocationById_ThrowsIOException() throws Exception {

		new Expectations() {
			{
				queryRequest.setBasicAuthCredentials(anyString, anyString);
				queryRequest.process((SolrClient) any, anyString);
				result = new IOException("Test Exception");
			}
		};

		locationFinderEO.getLocationsById("fe26287a-3938-4d70-b616-57257e383237");
	}
}
