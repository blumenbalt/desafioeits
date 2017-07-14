package projeto.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import projeto.domain.service.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan("projeto.domain.service")
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private AppUserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
			.antMatchers("/**").permitAll();
		
	/*	http
		.authorizeRequests()
		.antMatchers( "/**" )
		.authenticated()
		.and()
		.httpBasic()
		.and()
		.csrf()
		.disable();*/
		
//		http.csrf().disable().sessionManagement().maximumSessions(1).expiredUrl("/login");
//		http.headers().frameOptions().disable();

        /*http
		.authorizeRequests()
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
	    .logoutUrl( "/logout" );*/
	}

}
