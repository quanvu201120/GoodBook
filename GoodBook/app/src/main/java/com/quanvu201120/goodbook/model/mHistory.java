package com.quanvu201120.goodbook.model;

import java.io.Serializable;
import java.util.ArrayList;

public class mHistory implements Serializable {
    private String Id_history;
    private ArrayList<Sach_History> arrSach;
    private String TongTien;
    private String Ten, SoDienThoai, DiaChi;
    private String NgayDatHang;

    @Override
    public String toString() {
        return "mHistory{" +
                "Id_history='" + Id_history + '\'' +
                ", arrSach=" + arrSach +
                ", TongTien='" + TongTien + '\'' +
                ", Ten='" + Ten + '\'' +
                ", SoDienThoai='" + SoDienThoai + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", NgayDatHang='" + NgayDatHang + '\'' +
                '}';
    }

    public mHistory() {
    }

    public String getId_history() {
        return Id_history;
    }

    public void setId_history(String id_history) {
        Id_history = id_history;
    }

    public ArrayList<Sach_History> getArrSach() {
        return arrSach;
    }

    public void setArrSach(ArrayList<Sach_History> arrSach) {
        this.arrSach = arrSach;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNgayDatHang() {
        return NgayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        NgayDatHang = ngayDatHang;
    }

    public mHistory(String id_history, ArrayList<Sach_History> arrSach, String tongTien, String ten, String soDienThoai, String diaChi, String ngayDatHang) {
        Id_history = id_history;
        this.arrSach = arrSach;
        TongTien = tongTien;
        Ten = ten;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
        NgayDatHang = ngayDatHang;
    }
}
