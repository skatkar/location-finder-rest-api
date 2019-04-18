package cs.locationfinder.bo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;

import cs.locationfinder.eo.LocationFinderEO;
import cs.locationfinder.vo.LocationVO;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class LocationFinderBOTest {

	@Tested
	LocationFinderBO locationFinderBO;
	
	@Injectable
	LocationFinderEO locationFinderEO;
	
	@Test
	public void testGetLocationById() throws Exception {
		
		List<LocationVO> locations = new ArrayList<LocationVO>();
		locations.add(new LocationVO("fe26287a-3938-4d70-b616-57257e383237",50.23592,49.4368,"","", null));
		
		new Expectations() {{
			locationFinderEO.getLocationsById(anyString);
			result = locations;
		}};
		
		List<LocationVO> responseVO = locationFinderBO.getLocationsById("fe26287a-3938-4d70-b616-57257e383237");
		assertEquals(1,responseVO.size());
		assertEquals("fe26287a-3938-4d70-b616-57257e383237",responseVO.get(0).getId());
	}
	
	
	@Test
	public void testGetLocationByType() throws Exception {
		List<LocationVO> locations = new ArrayList<LocationVO>();
		locations.add(new LocationVO("fe26287a-3938-4d70-b616-57257e383237",50.23592,49.4368,"","School", null));
		locations.add(new LocationVO("acd28c08-f3c7-470a-8c90-b2d772364936",50.23592,49.4368,"","School", null));
		
		new Expectations() {{
			locationFinderEO.getLocationsByType("School", anyString);
			result = locations;
		}};
		
		List<LocationVO> responseVO = locationFinderBO.getLocationsByType("School", "10");
		assertEquals(2,responseVO.size());
		assertEquals("School", responseVO.get(0).getLocationType());
		assertEquals("School", responseVO.get(1).getLocationType());
				
	}
	
	@Test
	public void testSave() throws Exception {
		LocationVO locationVO = new LocationVO("fe26287a-3938-4d70-b616-57257e383237",50.23592,49.4368,"","School", null);
		
		new Expectations() {{
			locationFinderEO.save((LocationVO) any);
			result = locationVO;
		}};
		
		LocationVO responseVO = locationFinderBO.save(locationVO);
		assertEquals(responseVO,locationVO);
	}
	
	
	@Test
	public void testSearchDocument() throws Exception {
		List<LocationVO> locations = new ArrayList<LocationVO>();
		locations.add(new LocationVO("fe26287a-3938-4d70-b616-57257e383237",50.23592,49.4368,"","School", null));
		locations.add(new LocationVO("acd28c08-f3c7-470a-8c90-b2d772364936",50.23592,49.4368,"","School", null));
		
		new Expectations() {{
			locationFinderEO.searchDocuments("School", anyString);
			result = locations;
		}};
		
		List<LocationVO> responseVO = locationFinderBO.searchDocuments("School", "10");
		assertEquals(2,responseVO.size());
		assertEquals("School", responseVO.get(0).getLocationType());
		assertEquals("School", responseVO.get(1).getLocationType());
				
	}
	
	
	
	@Test(expected = SolrException.class)
	public void testGetLocationById_shouldThrowSolrException() throws Exception {
		
		new Expectations() {{
			locationFinderEO.getLocationsById(anyString);
			result = new SolrException(ErrorCode.SERVER_ERROR, anyString);
		}};
		
		locationFinderBO.getLocationsById("fe26287a-3938-4d70-b616-57257e383237");
	}
	
	@Test(expected = IOException.class)
	public void testGetLocationById_shouldThrowIOException() throws Exception {
		
		new Expectations() {{
			locationFinderEO.getLocationsById(anyString);
			result = new IOException();
		}};
		
		locationFinderBO.getLocationsById("fe26287a-3938-4d70-b616-57257e383237");
	}
	
}
