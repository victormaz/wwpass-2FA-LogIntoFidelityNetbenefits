package com.wwpass.fidelity.demo.service;


import com.google.gson.JsonSyntaxException;
import com.wwpass.fidelity.demo.dao.DemoUserDao;
import com.wwpass.fidelity.demo.domain.DemoUser;
import com.wwpass.fidelity.demo.web.authentication.WwpassContainerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

/**
 * WwpassUserDetailsService.java
 *
 */
public class WwpassUserDetailsService implements UserDetailsService {
    
    private WwpassContainerStorage containerStorage;
    private final DemoUserDao userDao;

    @Autowired
    public WwpassUserDetailsService(DemoUserDao userDao) {
        if (userDao == null) {
            throw new IllegalArgumentException("userDao cannot be null");
        }
        this.userDao = userDao;
    }
    
    @Override
    public UserDetails loadUserByUsername(String ticket) throws UsernameNotFoundException {
        WwpassContainerStorage.UsernamePasswordPair pair = null;
        try {
            pair = containerStorage.readUsernamePasswordFromWwpass(ticket);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Exception in WWPassConnection library: \n", e);
        } catch (JsonSyntaxException e) {
            throw new UsernameNotFoundException("There is no such user.");
        }
        DemoUser demoUser = userDao.getUserByUsername(pair.username);
        if (demoUser == null) {
            throw new UsernameNotFoundException("There is no such user.");
        }
        UserDetails user = new User(
                demoUser.getUsername(),
                demoUser.getPassword(), 
                true, 
                true, 
                true, 
                true, 
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
        return user;
    }
    
    public void setContainerStorage(WwpassContainerStorage containerStorage) {
        this.containerStorage = containerStorage;
    }
}
