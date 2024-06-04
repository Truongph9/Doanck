/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.HangHoaController;
import model.HangHoa;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class XoaHangHoa {
    ArrayList<HangHoa> hh;
    DefaultTableModel dataModel;
    int getRow;
    HangHoaController controller = new HangHoaController();

    public XoaHangHoa(ArrayList<HangHoa> h, DefaultTableModel model, int row) {
        getRow = row;
        hh = h;
        dataModel = model;
        HangHoa hangHoaToRemove = hh.get(row);

        if (controller.xoaHangHoa(hangHoaToRemove)) {
            hh.remove(row);
            dataModel.removeRow(row);
            capNhatLaiHangHoa();
        }
    }

    private void capNhatLaiHangHoa() {
        for (int i = 0; i < hh.size(); i++) {
            HangHoa hangHoa = hh.get(i);
            String oldId = hangHoa.getId();
            String newId = String.valueOf(i + 1);
            hangHoa.setId(newId);
            controller.chinhSuaID(oldId, newId);

            dataModel.insertRow(i, new Object[]{
                    hangHoa.getId(), hangHoa.getTen(),
                    hangHoa.getGia(), hangHoa.getSoLuong(),
                    hangHoa.getDaNhap(), hangHoa.getNgayNhap(),
                    hangHoa.getDaXuat(), hangHoa.getNgayXuat()
            });
            dataModel.removeRow(i + 1);
        }
        dataModel.fireTableDataChanged();
    }
}

