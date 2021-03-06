package projeto.application.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import projeto.domain.service.EmailService;

@ComponentScan( basePackageClasses = EmailService.class)
@Configuration
@PropertySource({"classpath:env/mail.properties"})
public class MailConfig 
{
	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 
	 *-----------------------------------------------------------------------*/
	@Autowired
	private Environment env;
	
	
	
	/*------------------------------------------------------------------------
	 * 
	 * 							BEANS
	 * 
	 *-----------------------------------------------------------------------*/
	@Bean
	public JavaMailSender mailSender()
	{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("Host"));
		mailSender.setPort(587);
		mailSender.setUsername(env.getProperty("Username"));
		mailSender.setPassword(env.getProperty("Password"));
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000); // miliseconds

		mailSender.setJavaMailProperties(props);
		
		return mailSender;
		
	}
}
