package projeto.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import projeto.domain.service.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan("projeto.domain.service")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{

	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 	
	 *-----------------------------------------------------------------------*/
	@Autowired
	private AppUserDetailsService userDetailsService;

	/*------------------------------------------------------------------------
	 * 
	 * 							BEANS
	 * 
	 *-----------------------------------------------------------------------*/
	@Bean
	public PasswordEncoder passwordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}

	/*------------------------------------------------------------------------
	 * 
	 * 							OVERRIDES
	 * 
	 *-----------------------------------------------------------------------*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	/**
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{	
		http.csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .maximumSessions(1).expiredUrl("/login");
		
		http.headers().frameOptions().disable();

		http
		.authorizeRequests()
		.antMatchers( "/projeto/**" )
		.hasIpAddress( "127.0.0.1" )
    	.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.usernameParameter( "email" )
		.passwordParameter( "senha" )
		.loginPage( "/login" )
		.loginProcessingUrl( "/authenticate" )
		.permitAll()
		.and()
		.logout()
		.logoutUrl( "/logout" ).and();
		
		 http
         .authorizeRequests()
         .antMatchers( "/api/**" )
             .authenticated()
             .and()
                 .httpBasic();
		
	}

}
