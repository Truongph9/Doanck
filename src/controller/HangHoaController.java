/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.HangHoa;
import model.JDBCConnection;

import java.util.ArrayList;

public class HangHoaController {
    JDBCConnection jdbcConnection = new JDBCConnection();

    public boolean nhapKho(HangHoa hanghoa) {
        return jdbcConnection.addHangHoa(hanghoa);
    }

    public boolean xuatKho(HangHoa hanghoa, int soLuong, String ngayXuat) {
        return jdbcConnection.xuatHangHoa(hanghoa, hanghoa.getId(), soLuong, ngayXuat);
    }

    public boolean chinhSuaHangHoa(HangHoa hanghoa) {
        return jdbcConnection.chinhSuaHangHoa(hanghoa, hanghoa.getId());
    }

    public boolean xoaHangHoa(HangHoa hanghoa) {
        return jdbcConnection.xoaHangHoa(hanghoa, hanghoa.getId());
    }

    public boolean chinhSuaID(String oldId, String newId) {
        return jdbcConnection.chinhSuaID(oldId, newId);
    }

    public ArrayList<HangHoa> getListHangHoa() {
        return jdbcConnection.getListHangHoa();
    }
}
