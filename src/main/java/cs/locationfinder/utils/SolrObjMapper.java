package cs.locationfinder.utils;

import java.util.List;

import org.apache.solr.common.SolrDocumentList;

public interface SolrObjMapper {

	public <T extends Object> List<T> solrObjMapper(SolrDocumentList documents);
}
