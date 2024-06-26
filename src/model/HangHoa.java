/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
 import java.io.Serializable;

public class HangHoa implements Serializable {
    private String id;
    private String ten;
    private double gia;
    private int soLuong;
    private int daNhap;
    private String ngayNhap;
    private int daXuat;
    private String ngayXuat;

    // Constructors
    public HangHoa() {
    }

    public HangHoa(String id, String ten, double gia, int soLuong) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public HangHoa(String id, String ten, double gia, int soLuong, int daNhap, int daXuat) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.soLuong = soLuong;
        this.daNhap = daNhap;
        this.daXuat = daXuat;
    }

    public HangHoa(String id, String ten, double gia, int soLuong, int daNhap, String ngayNhap, int daXuat, String ngayXuat) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.soLuong = soLuong;
        this.daNhap = daNhap;
        this.ngayNhap = ngayNhap;
        this.daXuat = daXuat;
        this.ngayXuat = ngayXuat;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDaNhap() {
        return daNhap;
    }

    public void setDaNhap(int daNhap) {
        this.daNhap = daNhap;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getDaXuat() {
        return daXuat;
    }

    public void setDaXuat(int daXuat) {
        this.daXuat = daXuat;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    @Override
    public String toString() {
        return "HangHoa [id=" + id + ", ten=" + ten + ", gia=" + gia + ", soLuong=" + soLuong + ", daNhap=" + daNhap
                + ", ngayNhap=" + ngayNhap + ", daXuat=" + daXuat + ", ngayXuat=" + ngayXuat + "]";
    }
}