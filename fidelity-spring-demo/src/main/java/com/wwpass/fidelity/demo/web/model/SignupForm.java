package com.wwpass.fidelity.demo.web.model;

import org.hibernate.validator.constraints.NotEmpty;


public class SignupForm {

    @NotEmpty(message="Nickname is required")
    private String nickname;
    @NotEmpty(message="Username is required")
    private String username;
    @NotEmpty(message="Password is required")
    private String password;

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
