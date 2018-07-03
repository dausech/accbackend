package me.dausech.accbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import me.dausech.accbackend.JpaUserDetailsService;
import me.dausech.accbackend.model.AppUser;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JpaUserDetailsService userDetailsService;

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(AppUser.PASSWORD_ENCODER);		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()		   
		    .antMatchers("/users/**", "/style.css").permitAll()
		    .antMatchers(HttpMethod.POST, "/login").permitAll()
		    .anyRequest().authenticated()
		    .and()			
		    
			//.formLogin().permitAll()
			//.and()
			//.logout().logoutSuccessUrl("/")
			//.and()
			//	.and().httpBasic()			
			//	.and().csrf().disable()

			// filtra requisições de login
			.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
				                UsernamePasswordAuthenticationFilter.class)
									
			// filtra outras requisições para verificar a presença do JWT no header
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	JWTAuthFilter jwtAuthFilter;
	
	// https://stackoverflow.com/questions/32494398/unable-to-autowire-the-service-inside-my-authentication-filter-in-spring/32495757
	@Bean
    public FilterRegistrationBean<JWTAuthFilter> authFilterRegistrationBean() {
        FilterRegistrationBean<JWTAuthFilter> regBean = new FilterRegistrationBean<JWTAuthFilter>();
        regBean.setFilter(jwtAuthFilter);
       // regBean.setOrder(1);
       // regBean.addUrlPatterns("/blabla");
        return regBean;
    }
}