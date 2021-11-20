package com.quanvu201120.goodbook.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {


    private String u_Id, u_Email, u_Name, u_Phone, u_Avatar, u_StoreName;
    private ArrayList<String> us_Address;
    private ArrayList<String> us_Product, us_Cart, us_Order, us_History;

    public User() {
    }

    public User(String u_Id, String u_Email, String u_Name, String u_Phone, String u_Avatar, String u_StoreName, ArrayList<String> us_Address, ArrayList<String> us_Product, ArrayList<String> us_Cart, ArrayList<String> us_Order, ArrayList<String> us_History) {
        this.u_Id = u_Id;
        this.u_Email = u_Email;
        this.u_Name = u_Name;
        this.u_Phone = u_Phone;
        this.u_Avatar = u_Avatar;
        this.u_StoreName = u_StoreName;
        this.us_Address = us_Address;
        this.us_Product = us_Product;
        this.us_Cart = us_Cart;
        this.us_Order = us_Order;
        this.us_History = us_History;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_Id='" + u_Id + '\'' +
                ", u_Email='" + u_Email + '\'' +
                ", u_Name='" + u_Name + '\'' +
                ", u_Phone='" + u_Phone + '\'' +
                ", u_Avatar='" + u_Avatar + '\'' +
                ", u_StoreName='" + u_StoreName + '\'' +
                ", us_Address=" + us_Address +
                ", us_Product=" + us_Product +
                ", us_Cart=" + us_Cart +
                ", us_Order=" + us_Order +
                ", us_History=" + us_History +
                '}';
    }

    public ArrayList<String> getUs_History() {
        return us_History;
    }

    public void setUs_History(ArrayList<String> us_History) {
        this.us_History = us_History;
    }

    public String getU_Id() {
        return u_Id;
    }

    public void setU_Id(String u_Id) {
        this.u_Id = u_Id;
    }

    public String getU_Email() {
        return u_Email;
    }

    public void setU_Email(String u_Email) {
        this.u_Email = u_Email;
    }

    public String getU_Name() {
        return u_Name;
    }

    public void setU_Name(String u_Name) {
        this.u_Name = u_Name;
    }

    public String getU_Phone() {
        return u_Phone;
    }

    public void setU_Phone(String u_Phone) {
        this.u_Phone = u_Phone;
    }

    public String getU_Avatar() {
        return u_Avatar;
    }

    public void setU_Avatar(String u_Avatar) {
        this.u_Avatar = u_Avatar;
    }

    public String getU_StoreName() {
        return u_StoreName;
    }

    public void setU_StoreName(String u_StoreName) {
        this.u_StoreName = u_StoreName;
    }

    public ArrayList<String> getUs_Address() {
        return us_Address;
    }

    public void setUs_Address(ArrayList<String> us_Address) {
        this.us_Address = us_Address;
    }

    public ArrayList<String> getUs_Product() {
        return us_Product;
    }

    public void setUs_Product(ArrayList<String> us_Product) {
        this.us_Product = us_Product;
    }

    public ArrayList<String> getUs_Cart() {
        return us_Cart;
    }

    public void setUs_Cart(ArrayList<String> us_Cart) {
        this.us_Cart = us_Cart;
    }

    public ArrayList<String> getUs_Order() {
        return us_Order;
    }

    public void setUs_Order(ArrayList<String> us_Order) {
        this.us_Order = us_Order;
    }
}
