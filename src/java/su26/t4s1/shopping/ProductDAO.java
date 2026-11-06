
package su26.t4s1.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import su26.t4s1.user.UserDTO;
import su26.t4s1.utils.DBUtils;


public class ProductDAO {

    public List<Product> getList() throws SQLException {
        List<Product> list = new ArrayList<>();
        String stmt = "SELECT * FROM Product";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString("id"), rs.getString("name"), rs.getFloat("price"), rs.getInt("quantity")));
            }
        } catch (Exception e) {
            System.out.println("Error occur at ProductDAO.getList: " + e.toString());
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

    public List<Product> getListPagination(int pageIndex) throws SQLException {
        List<Product> list = new ArrayList<>();
        String stmt = "SELECT * FROM Product "
                + "ORDER BY id ASC "
                + "OFFSET ? ROWS "
                + "FETCH NEXT 5 ROWS ONLY";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            pstmt.setInt(1, (pageIndex - 1) * 5);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString("id"), rs.getString("name"), rs.getFloat("price"), rs.getInt("quantity")));
            }
        } catch (Exception e) {
            System.out.println("Error occur at ProductDAO.getList: " + e.toString());
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

    public int getTotalProductCount() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM Product";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error at ProductDAO.getTotalProductCount: " + e.toString());
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
        return count;
    }

    public boolean checkQuantity(String pID, int neededQuantity) throws SQLException {
        boolean check = false;
        String stmt = "SELECT quantity FROM Product WHERE id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(stmt);
                pstmt.setString(1, pID);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    check = (rs.getInt("quantity") >= neededQuantity);
                }
            }
        } catch (Exception e) {
            System.out.println("Error occur at ProductDAO.checkQuantity: " + e.toString());
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
        return check;
    }

    public boolean reduceQuantity(String id, int quantity) throws SQLException {
        boolean check = false;
        String stmt = "UPDATE Product SET quantity = quantity - ? WHERE id=? AND quantity>=?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(stmt);
                pstmt.setInt(1, quantity);
                pstmt.setString(2, id);
                pstmt.setInt(3, quantity);
                check = pstmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error occur at ProductDAO.reduceQuantity: " + e.toString());
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
