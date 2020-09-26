package wybin.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class DatabaseConfiguration {
	@Value("${database.url}")
	private String databaseUrl;

	@Value("${database.username}")
	private String databaseUsername;

	@Value("${database.password}")
	private String databasePassword;

	@Bean
	public DataSource getDataSource() {
		// Create the properties object
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

		dataSourceBuilder.url(databaseUrl);
		dataSourceBuilder.username(databaseUsername);
		dataSourceBuilder.password(databasePassword);

		return dataSourceBuilder.build();
	}
}
