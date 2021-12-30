package com.zabid.threadhouse.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.zabid.threadhouse.security.utils.JwtTokenUtils;

@Component
public class JwtTokenAuthenticationFilter extends GenericFilterBean {

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String token = jwtTokenUtils.resolveToken(httpServletRequest);

		try {
			// understand the function validate token
			if(token != null && jwtTokenUtils.validateToken(token)) {
				Authentication auth = jwtTokenUtils.getAuthentication(token);
				
				if(auth != null) {
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			resolver.resolveException(httpServletRequest, httpServletResponse, null, e);
			
		}

	}

}
