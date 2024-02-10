package com.example.in28minutes.springboot.myfirstwebapp.security;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {
	//Ldap or Database for Sotring Data
	//Here we will be using Inmemory
	
//	InMemoryUserDetailsManager
//	InMemoryUserDetailsManager(UserDetails users)
	
	@Bean
	public InMemoryUserDetailsManager createuserDetailsManager()
	{
//		UserDetails userDetails = User.withDefaultPasswordEncoder() --> This Function Is deprecated and hence we will be using new method
		
		UserDetails userDetails1 = createNewUser("soumik", "1234");
		UserDetails userDetails2 = createNewUser("miko", "12345");
		
		return new InMemoryUserDetailsManager(userDetails1,userDetails2);
	}
	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoderFunc= input-> passwordEncoder().encode(input);
		UserDetails userDetails = User.builder().passwordEncoder(passwordEncoderFunc)
		.username(username)
		.password(password)
		.roles("USER","ADMIN")
		.build();
		return userDetails;
	}
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	//All urls are by protected 
	//A login form is shown for unauthorized requests.
	//So if we type any gibberish it wil redirect to login.
	//TO access the H2 Console
	//Cross Site Request Forgery must be Disabled (CSRF)
	//H2 uses Frames and Spring Security does not allow it.
	
	//SecurityFilterChain --> Whenever a Url Request comes in it is checked here. So we will configure it to allow access to H2.
	//HttpSecurity allows us to configure Security
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		//Here we are saying that we need to Authenticate all the Requests.
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
		
		//Here we are configuring the Default behavior of the Login form.
		http.formLogin(withDefaults());
		
		//Cross Site Request Forgery is being Disabled  here(CSRF)
		http.csrf().disable();
		
		//Frames weren't allowed. So we are disabling that feature.
		http.headers().frameOptions().disable();
		
		return http.build();
	}
}
