package com.wwpass.fidelity.demo.dao;


import com.wwpass.fidelity.demo.domain.DemoUser;

/**
 * UserDao.java
 */
public interface DemoUserDao {
    DemoUser getUser(int id);
    DemoUser getUserByUsername(String username);
    int createUser(DemoUser user);
}
