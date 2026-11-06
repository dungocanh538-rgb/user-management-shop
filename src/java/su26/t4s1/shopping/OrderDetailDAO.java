
package su26.t4s1.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import su26.t4s1.utils.DBUtils;


public class OrderDetailDAO {

    public boolean insert(String oID, String pID, int quantity) throws SQLException {
        boolean check = false;
        String stmt = "INSERT INTO OrderDetail (oID, pID, price, quantity) VALUES (?,?,?,?)";
        PreparedStatement pstmt = null;
        PreparedStatement psPrice = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                psPrice = conn.prepareStatement("SELECT price FROM Product WHERE id=?");
                psPrice.setString(1, pID);
                rs = psPrice.executeQuery();
                if (rs.next()) {
                    pstmt = conn.prepareStatement(stmt);
                    pstmt.setString(1, oID);
                    pstmt.setString(2, pID);
                    pstmt.setDouble(3, rs.getDouble("price"));
                    pstmt.setInt(4, quantity);
                    check = pstmt.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Error occur at Order.insert: " + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (psPrice != null) {
                psPrice.close();
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
}
