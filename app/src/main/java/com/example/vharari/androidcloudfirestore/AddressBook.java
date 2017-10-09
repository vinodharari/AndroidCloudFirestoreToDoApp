package com.example.vharari.androidcloudfirestore;

/**
 * Created by vharari on 10/9/2017.
 */

public class AddressBook {
    private String name, email, mobile;

    public AddressBook() {
    }

    public AddressBook(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
