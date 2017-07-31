package projeto.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceConfigTest 
{
	
	/*------------------------------------------------------------------------
	 * 
	 * 							BEANS
	 * 
	 *-----------------------------------------------------------------------*/
	
	/**
	 * 
	 * @return
	 */
	@Bean
	@Profile("test")
	public DataSource dataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("eits02");
		dataSource.setPassword("eits");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/eits02");
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		return dataSource;
	}
}
