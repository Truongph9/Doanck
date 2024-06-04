/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
import java.util.ArrayList;

public class JDBCConnection {
    private Connection conn;

    public JDBCConnection() {
      /*  final String url = "jdbc:mysql://localhost:1433/hello?useSSL=false";
        final String user = "sa";
        final String password = "123456789";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    	
        final String url = "jdbc:mysql://localhost:3306/hello?useSSL=false";
        final String user = "root";
        final String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addHangHoa(HangHoa e) {
        String sql = "INSERT INTO qlhanghoa2(Id, Ten, Gia, SoLuong, DaNhap, NgayNhap, DaXuat, NgayXuat) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, e.getId());
            ps.setString(2, e.getTen());
            ps.setDouble(3, e.getGia());
            ps.setInt(4, e.getSoLuong());
            ps.setInt(5, e.getDaNhap());
            ps.setString(6, e.getNgayNhap());
            ps.setInt(7, e.getDaXuat());
            ps.setString(8, e.getNgayXuat());
            return ps.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public boolean xuatHangHoa(HangHoa e, String id, int soLuong, String ngayXuat) {
        String sql = "UPDATE qlhanghoa2 SET SoLuong=?, DaXuat=?, NgayXuat=? WHERE Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, (e.getSoLuong() - soLuong));
            ps.setInt(2, (e.getDaXuat() + soLuong));
            ps.setString(3, ngayXuat);
            ps.setString(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public boolean xoaHangHoa(HangHoa e, String id) {
        String sql = "DELETE FROM qlhanghoa2 WHERE Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public boolean chinhSuaID(String oldId, String newId) {
        String sql = "UPDATE qlhanghoa2 SET Id=? WHERE Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newId);
            ps.setString(2, oldId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public boolean chinhSuaHangHoa(HangHoa e, String id) {
        String sql = "UPDATE qlhanghoa2 SET Ten=?, Gia=?, SoLuong=?, DaNhap=?, NgayNhap=?, DaXuat=?, NgayXuat=? WHERE Id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, e.getTen());
            ps.setDouble(2, e.getGia());
            ps.setInt(3, e.getSoLuong());
            ps.setInt(4, e.getDaNhap());
            ps.setString(5, e.getNgayNhap());
            ps.setInt(6, e.getDaXuat());
            ps.setString(7, e.getNgayXuat());
            ps.setString(8, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public ArrayList<HangHoa> getListHangHoa() {
        ArrayList<HangHoa> list = new ArrayList<>();
        String sql = "SELECT * FROM qlhanghoa2";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HangHoa s = new HangHoa(rs.getString("Id"), rs.getString("Ten"),
                        rs.getDouble("Gia"), rs.getInt("SoLuong"),
                        rs.getInt("DaNhap"), rs.getString("NgayNhap"),
                        rs.getInt("DaXuat"), rs.getString("NgayXuat"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
    	JDBCConnection jd = new JDBCConnection();
	}
}
