package com.wwpass.fidelity.demo.web.authentication;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;


public class WwpassContainerAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    
    private String credentials;
    
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String ticket = request.getParameter("ticket");
        /*if (ticket.equals("")) {
            return null;
        }*/
        
        return ticket;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        if (credentials == null) {
            return DEFAULT_ROLE;
        }
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

}
