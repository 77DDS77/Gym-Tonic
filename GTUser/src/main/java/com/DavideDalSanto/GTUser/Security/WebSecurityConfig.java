package com.DavideDalSanto.GTUser.Security;

import com.DavideDalSanto.GTUser.Security.details.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Autowired
    UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointUnauthorizedJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.cors(cors -> {
					try {
						cors.and().csrf().disable();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				})
				.exceptionHandling((exc -> exc.authenticationEntryPoint(unauthorizedHandler)))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> {
					auth.antMatchers(HttpMethod.POST,"/GT/login", "/GT/users/new-user", "/GT/p-trainers/new-pt")
							.permitAll();
					auth.antMatchers(HttpMethod.GET, "/GT/all-users")
							.permitAll();
					auth.anyRequest().authenticated();
				})
				.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}

/*
 * OLD SECURITY FILTER CHAIN
 * 	 * @ Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 http.cors()
 .and()
 .csrf()
 .disable()
 .exceptionHandling()
 .authenticationEntryPoint(unauthorizedHandler)
 .and()
 .sessionManagement()
 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
 .and()
 .authorizeRequests()
 .antMatchers(HttpMethod.POST,"/GT/login", "/GT/users/new-user", "/GT/p-trainers/new-pt")
 .permitAll()
 .antMatchers(HttpMethod.GET, "/GT/all-users")
 .permitAll()
 .anyRequest()
 .authenticated();

 http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

 return http.build();
 }
 */