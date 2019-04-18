package cs.locationfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableAutoConfiguration
@SpringBootApplication
public class LocationFinderApiApplication extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		/*
		 * return application.properties("spring.config.name:application,solr")
		 * .sources(LocationFinderApiApplication.class);
		 */
		return application.sources(LocationFinderApiApplication.class);
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(LocationFinderApiApplication.class, args);
	}

}

