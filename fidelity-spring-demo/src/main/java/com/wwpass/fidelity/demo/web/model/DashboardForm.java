package com.wwpass.fidelity.demo.web.model;

import org.hibernate.validator.constraints.NotEmpty;


public class DashboardForm {

    @NotEmpty(message="Password is required")
    private String password;
    @NotEmpty(message="Hidden field \"ticket\" is required")
    private String ticket;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
}
