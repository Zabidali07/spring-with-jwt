package com.zabid.threadhouse.security.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

// import com.sun.org.slf4j.internal.LoggerFactory;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	//private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		//LOGGER.debug("jwt authentication failed: " +authException  );
		resolver.resolveException(request, response, null, authException);
		
	}

}
