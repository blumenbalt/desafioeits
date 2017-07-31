package projeto.application.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("projeto.domain.repository")
@PropertySource({ "classpath:env/db.properties" })
@EnableTransactionManagement
public class JPAConfig 
{

	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 
	 *-----------------------------------------------------------------------*/

	@Autowired
	Environment env;

	/*------------------------------------------------------------------------
	 * 
	 * 							BEANS
	 * 
	 *-----------------------------------------------------------------------*/
	@Bean
	@Profile("dev")
	public DriverManagerDataSource dataSource() 
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername(env.getProperty("username"));
		dataSource.setPassword(env.getProperty("password"));
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setDriverClassName(env.getProperty("drive"));
		return dataSource;
	}

	/**
	 * 
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() 
	{
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL);
		adapter.setShowSql(false);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		return adapter;
	}

	/**
	 * 
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) 
	{
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("projeto.domain.entity");
		factoryBean.setDataSource(dataSource());
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(additionalProperties());
		return factoryBean;
	}

	/**
	 * 
	 */
	@Bean
	public Properties additionalProperties() 
	{
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "false");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.ddl", "false");
		return properties;
	}

	/**
	 * 
	 */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) 
	{
		return new JpaTransactionManager(entityManagerFactory);
	}
}
