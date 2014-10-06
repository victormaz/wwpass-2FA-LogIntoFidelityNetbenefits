package com.wwpass.fidelity.demo.service;


import com.wwpass.fidelity.demo.dao.DemoUserDao;
import com.wwpass.fidelity.demo.domain.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultDemoService implements DemoService, UserDetailsService {

    private final DemoUserDao userDao;

    @Autowired
    public DefaultDemoService(DemoUserDao userDao) {
        if (userDao == null) {
            throw new IllegalArgumentException("userDao cannot be null");
        }
        this.userDao = userDao;
    }

    @Override
    public DemoUser getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public DemoUser getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public int createUser(DemoUser user) {
        return userDao.createUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DemoUser user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No such user with username: " + username + "!");
        }
        UserDetails userDetails = new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
        return userDetails;
    }
}
