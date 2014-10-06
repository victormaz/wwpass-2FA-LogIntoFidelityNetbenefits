package com.wwpass.fidelity.demo.domain;

import java.io.Serializable;


public class DemoUser implements Serializable {
    
    public static final Integer ROLE_ADMIN = new Integer(0);
    public static final Integer ROLE_USER = new Integer(1);
    
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Integer roleId;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    private static final long serialVersionUID = 8433999509932007961L;
}
