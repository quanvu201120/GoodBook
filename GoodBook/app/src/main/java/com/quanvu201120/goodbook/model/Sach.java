package com.quanvu201120.goodbook.model;

import java.io.Serializable;

public class Sach implements Serializable {

    private String id;
    private String trangThai;
    private String tenSach;
    private String tacGia;
    private String nhaXuatBan;
    private String namXuatBan;
    private String theLoai;
    private String ngonNgu;
    private String hinhAnh;
    private String giaBan;
    private String soTrang;
    private String gioiThieu;
    private String id_Shop;

    public Sach() {
    }

    public Sach(String id, String trangThai, String tenSach, String tacGia, String nhaXuatBan, String namXuatBan, String theLoai, String ngonNgu, String hinhAnh, String giaBan, String soTrang, String gioiThieu, String id_Shop) {
        this.id = id;
        this.trangThai = trangThai;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
        this.theLoai = theLoai;
        this.ngonNgu = ngonNgu;
        this.hinhAnh = hinhAnh;
        this.giaBan = giaBan;
        this.soTrang = soTrang;
        this.gioiThieu = gioiThieu;
        this.id_Shop = id_Shop;
    }

    @Override
    public String toString() {
        return "Sach{" +
                "id='" + id + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", tacGia='" + tacGia + '\'' +
                ", nhaXuatBan='" + nhaXuatBan + '\'' +
                ", namXuatBan='" + namXuatBan + '\'' +
                ", theLoai='" + theLoai + '\'' +
                ", ngonNgu='" + ngonNgu + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", giaBan='" + giaBan + '\'' +
                ", soTrang='" + soTrang + '\'' +
                ", gioiThieu='" + gioiThieu + '\'' +
                ", id_Shop='" + id_Shop + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public String getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(String namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(String soTrang) {
        this.soTrang = soTrang;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public String getId_Shop() {
        return id_Shop;
    }

    public void setId_Shop(String id_Shop) {
        this.id_Shop = id_Shop;
    }
}
