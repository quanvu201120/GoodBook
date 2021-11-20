package com.quanvu201120.goodbooksellers.model;

import java.io.Serializable;

public class Address implements Serializable {

    String name, phone, address;

    public Address() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Address(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
