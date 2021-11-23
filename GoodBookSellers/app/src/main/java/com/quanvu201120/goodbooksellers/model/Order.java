package com.quanvu201120.goodbooksellers.model;

import java.io.Serializable;

public class Order implements Serializable {
    private String id;
    private String tenSach;
    private String giaSach;
    private String soLuong;
    private String tenKH;
    private String soDienThoai;
    private String diaChi;
    private String ngayDatHang;
    private String trangThai;

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", giaSach='" + giaSach + '\'' +
                ", soLuong='" + soLuong + '\'' +
                ", tenKH='" + tenKH + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", ngayDatHang='" + ngayDatHang + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Order(String id, String tenSach, String giaSach, String soLuong, String tenKH, String soDienThoai, String diaChi, String ngayDatHang, String trangThai) {
        this.id = id;
        this.tenSach = tenSach;
        this.giaSach = giaSach;
        this.soLuong = soLuong;
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ngayDatHang = ngayDatHang;
        this.trangThai = trangThai;
    }

    public Order() {
    }
}
