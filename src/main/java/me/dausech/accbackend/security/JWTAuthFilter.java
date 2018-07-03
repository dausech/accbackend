package me.dausech.accbackend.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import me.dausech.accbackend.JpaUserDetailsService;

@Component
public class JWTAuthFilter extends GenericFilterBean {
	
	@Autowired
	JpaUserDetailsService jpaUserDetailsService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		
		Authentication authentication = null;
		String user = TokenAuthService.getUser((HttpServletRequest) request);
		if (user != null) {
		    UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(user);
			authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities() );
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);		
		filterChain.doFilter(request, response);
	}

}
