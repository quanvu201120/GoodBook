package com.quanvu201120.goodbook.model;

import java.io.Serializable;

public class Sach_History implements Serializable {
    private String TenSach, SoLuong, GiaSach;

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getGiaSach() {
        return GiaSach;
    }

    public void setGiaSach(String giaSach) {
        GiaSach = giaSach;
    }

    public Sach_History(String tenSach, String soLuong, String giaSach) {
        TenSach = tenSach;
        SoLuong = soLuong;
        GiaSach = giaSach;
    }

    public Sach_History() {
    }

    @Override
    public String toString() {
        return "Sach_History{" +
                "TenSach='" + TenSach + '\'' +
                ", SoLuong='" + SoLuong + '\'' +
                ", GiaSach='" + GiaSach + '\'' +
                '}';
    }
}
