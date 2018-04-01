package com.ezerka.smartaquapotent;

import javax.xml.transform.sax.SAXResult;

public class User {

    public String username;
    public String email;
    public String can_id;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String can_id, String username, String email) {
        this.can_id = can_id;
        this.username = username;
        this.email = email;
    }

}