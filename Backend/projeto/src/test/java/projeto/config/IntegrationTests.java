package projeto.config;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import projeto.application.configuration.JPAConfig;
import projeto.application.configuration.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class, JPAConfig.class, DataSourceConfigTest.class})
@WebAppConfiguration
public class IntegrationTests 
{

}
