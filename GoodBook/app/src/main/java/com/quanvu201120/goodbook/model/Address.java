package com.quanvu201120.goodbook.model;

import java.io.Serializable;

public class Address implements Serializable {

    String id, idUser, name, phone, address;

    public Address() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public Address(String id, String idUser, String name, String phone, String address) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
