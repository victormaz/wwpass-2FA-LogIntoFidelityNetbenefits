package com.wwpass.fidelity.demo.service;

import com.wwpass.fidelity.demo.domain.DemoUser;


public interface DemoService  {
    DemoUser getUser(int id);
    DemoUser getUserByUsername(String username);
    int createUser(DemoUser user);
}
