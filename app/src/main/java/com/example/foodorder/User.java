package com.example.foodorder;

public class User {

    private String id;
    private String name, email,mobile_number,address;

    public User(String id, String name, String email, String mobile_number,String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile_number = mobile_number;

        this.address=address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile_number() {
        return mobile_number;
    }


    public String getAddress() {
        return address;
    }
}
