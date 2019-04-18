/**
 * 
 */
package cs.locationfinder.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author CampusUser
 *
 */
@Configuration
//@PropertySource("classpath:solr.properties")
public class SolrConfig {

	@Bean
	public SolrClient solrClient(@Value("${solr.endpoint}") String solrUrl) {
		return new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000).withSocketTimeout(60000).build();
	}
	
	@Bean
	public String solr_username(@Value("${solr.user}") String username) {
		return username;
	}
	
	@Bean
	public String solr_password(@Value("${solr.password}") String password) {
		return password;
	}
	
	@Bean
	public String solr_collection(@Value("${solr.collection}") String collection) {
		return collection;
	}
	
}
