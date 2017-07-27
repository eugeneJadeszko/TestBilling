package by.intexsoft.test.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

/**
 * This class configure Couchbase Server for further usage
 */
@EnableCouchbaseRepositories(basePackages = "by.intexsoft.test.repository")
@PropertySource(value = "classpath:application.properties")
@Configuration
@ComponentScan(basePackages = "by.intexsoft.test.service")
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

	@Value("${couchbase.host}")
	private String hostName;

	@Value("${couchbase.bucket}")
	private String bucketName;

	@Value("${couchbase.password}")
	private String bucketPassword;

	/**
	 * Set hosts of Couchbase servers
	 *
	 * @return collection of Couchbase's host servers
	 */
	@Override
	protected List<String> getBootstrapHosts() {
		return Collections.singletonList(hostName);
	}

	/**
	 * Set bucket name in which we will write some information
	 *
	 * @return name of bucket
	 */
	@Override
	protected String getBucketName() {
		return bucketName;
	}

	/**
	 * Set bucket password in which we will write some information
	 *
	 * @return password of bucket
	 */
	@Override
	protected String getBucketPassword() {
		return bucketPassword;
	}
}