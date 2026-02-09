package com.example.smokingspotfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/", "/smoking-points", "/css/**", "/js/**").permitAll()
					.requestMatchers("/smoking-points/new", "/smoking-points/{id}/edit", "/smoking-points/{id}/delete").authenticated()
					.anyRequest().permitAll()
					)
			.formLogin(login -> login
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/smoking-points", true)
					.permitAll()
					)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/smoking-points")
					);
		return http.build();
	}
	@Bean 
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
		}
	
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		UserDetails admin = User.builder()
				.username("admin")
				.password(encoder.encode("password"))
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(admin);
		
	}
}
