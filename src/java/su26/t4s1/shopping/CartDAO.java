package su26.t4s1.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import su26.t4s1.user.UserDTO;
import su26.t4s1.utils.DBUtils;

public class CartDAO {

    public boolean insert(String uID, String pID, int quantity) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheck = null;
        PreparedStatement psUpdate = null;

        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                psCheck = conn.prepareStatement("SELECT quantity FROM Cart WHERE uID=? AND pID=?");
                psCheck.setString(1, uID);
                psCheck.setString(2, pID);
                rs = psCheck.executeQuery();
                if (rs.next()) { // exists -> update quantity
                    psUpdate = conn.prepareStatement("UPDATE Cart SET quantity=? WHERE uID=? AND pID=?");
                    psUpdate.setInt(1, rs.getInt("quantity") + quantity);
                    psUpdate.setString(2, uID);
                    psUpdate.setString(3, pID);

                    check = psUpdate.executeUpdate() > 0;
                } else {
                    psInsert = conn.prepareStatement("INSERT INTO Cart(uID, pID, quantity) VALUES(?,?,?)");
                    psInsert.setString(1, uID);
                    psInsert.setString(2, pID);
                    psInsert.setInt(3, quantity);

                    check = psInsert.executeUpdate() > 0;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (psCheck != null) {
                psCheck.close();
            }
            if (psInsert != null) {
                psInsert.close();
            }
            if (psUpdate != null) {
                psUpdate.close();
            }
            if (conn != null) {
                conn.close();
            }

        }
        return check;
    }

    public List<CartItemDTO> get(String uID) throws SQLException {
        List<CartItemDTO> list = new ArrayList<>();
        String stmt = "SELECT c.pID, p.name, c.uID, c.quantity, p.price FROM Cart c"
                + " JOIN Product p ON p.id = c.pID"
                + " WHERE c.uID=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, uID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new CartItemDTO(rs.getString("pID"), rs.getString("name"), rs.getString("uID"), rs.getInt("quantity"), rs.getDouble("price")));
                System.out.println(rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("Error occur at get: " + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public boolean update(String uID, String pID, int quantity) throws SQLException {

        boolean check = false;

        String sql
                = "UPDATE Cart SET quantity=? WHERE uID=? AND pID=?";

        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, quantity);
                pstmt.setString(2, uID);
                pstmt.setString(3, pID);
                check = pstmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error occur at CartDAO.update: " + e.toString());
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean remove(String uID, String pID) throws SQLException {
        boolean check = false;
        String stmt = "DELETE FROM Cart WHERE uID=? and pID=?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(stmt);
                pstmt.setString(1, uID);
                pstmt.setString(2, pID);
                check = pstmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error occur at CartDAO.remove: " + e.toString());
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean clear(String uID) throws SQLException {
        boolean check = false;
        String stmt = "DELETE FROM Cart WHERE uID=?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(stmt);
                pstmt.setString(1, uID);
                check = pstmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error occur at CartDAO.clear: " + e.toString());
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
}
