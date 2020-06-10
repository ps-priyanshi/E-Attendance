package com.srmbatch2.acms.e_attendance.ModelClasses;

public class User {

    public String name, regNo, email, password;

    public User() {

    }

    public User(String name, String regNo, String email, String password) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
        this.password = password;
    }
}
