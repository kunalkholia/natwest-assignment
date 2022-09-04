package com.natwest.assignment.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kunalkho on 9/3/2022.
 */

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
      throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Strict-Transport-Security", "max-age=31536000");
        throw authEx;
    }

	@Override
    public void afterPropertiesSet() {
        setRealmName("assignment");
        super.afterPropertiesSet();
    }

}