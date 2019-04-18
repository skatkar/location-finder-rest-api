package cs.locationfinder.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cs.locationfinder.vo.LocationVO;

@Component
@Qualifier("locationMapper")
public class LocationSolrObjMapper implements SolrObjMapper {

	@Override
	public List<LocationVO> solrObjMapper(SolrDocumentList documents) {
		List<LocationVO> locations = new ArrayList<LocationVO>();
		
		for (SolrDocument document : documents) {
			locations.add(new LocationVO((String) document.getFirstValue("id"),
					(Double) document.getFirstValue("latitude"), (Double) document.getFirstValue("longitude"),
					(String) document.getFirstValue("description"), (String) document.getFirstValue("locationType"),
					null));
		
		}
		
		return locations;
	}

}
