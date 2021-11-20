package com.quanvu201120.goodbooksellers.model;

import java.io.Serializable;

public class ItemCart implements Serializable {

    private String id_Cart, id_Sach, id_Shop, id_User, hinhAnh, tenSach, giaSach;
    private int soLuong;

    public ItemCart() {
    }

    public String getId_Cart() {
        return id_Cart;
    }

    public void setId_Cart(String id_Cart) {
        this.id_Cart = id_Cart;
    }

    public String getId_Sach() {
        return id_Sach;
    }

    public void setId_Sach(String id_Sach) {
        this.id_Sach = id_Sach;
    }

    public String getId_Shop() {
        return id_Shop;
    }

    public void setId_Shop(String id_Shop) {
        this.id_Shop = id_Shop;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getGiaSach() {
        return giaSach;
    }

    public void setGiaSach(String giaSach) {
        this.giaSach = giaSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public ItemCart(String id_Cart, String id_Sach, String id_Shop, String id_User, String hinhAnh, String tenSach, String giaSach, int soLuong) {
        this.id_Cart = id_Cart;
        this.id_Sach = id_Sach;
        this.id_Shop = id_Shop;
        this.id_User = id_User;
        this.hinhAnh = hinhAnh;
        this.tenSach = tenSach;
        this.giaSach = giaSach;
        this.soLuong = soLuong;
    }
}
