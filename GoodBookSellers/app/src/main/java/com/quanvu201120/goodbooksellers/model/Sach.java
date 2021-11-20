package com.quanvu201120.goodbooksellers.model;

import java.io.Serializable;

public class Sach implements Serializable {

    private String ID;
    private String TrangThai;
    private String TenSach;
    private String TacGia;
    private String NhaXuatBan;
    private String NamXuatBan;
    private String TheLoai;
    private String NgonNgu;
    private String HinhAnh;
    private String GiaBan;
    private String SoTrang;
    private String GioiThieu;
    private String ID_Shop;

    public Sach() {
    }

    public Sach(String ID, String trangThai, String tenSach, String tacGia, String nhaXuatBan, String namXuatBan, String theLoai, String ngonNgu, String hinhAnh, String giaBan, String soTrang, String gioiThieu, String ID_Shop) {
        this.ID = ID;
        TrangThai = trangThai;
        TenSach = tenSach;
        TacGia = tacGia;
        NhaXuatBan = nhaXuatBan;
        NamXuatBan = namXuatBan;
        TheLoai = theLoai;
        NgonNgu = ngonNgu;
        HinhAnh = hinhAnh;
        GiaBan = giaBan;
        SoTrang = soTrang;
        GioiThieu = gioiThieu;
        this.ID_Shop = ID_Shop;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        TacGia = tacGia;
    }

    public String getNhaXuatBan() {
        return NhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        NhaXuatBan = nhaXuatBan;
    }

    public String getNamXuatBan() {
        return NamXuatBan;
    }

    public void setNamXuatBan(String namXuatBan) {
        NamXuatBan = namXuatBan;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public String getNgonNgu() {
        return NgonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        NgonNgu = ngonNgu;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(String giaBan) {
        GiaBan = giaBan;
    }

    public String getSoTrang() {
        return SoTrang;
    }

    public void setSoTrang(String soTrang) {
        SoTrang = soTrang;
    }

    public String getGioiThieu() {
        return GioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        GioiThieu = gioiThieu;
    }

    public String getID_Shop() {
        return ID_Shop;
    }

    public void setID_Shop(String ID_Shop) {
        this.ID_Shop = ID_Shop;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
