
package su26.t4s1.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import su26.t4s1.utils.DBUtils;


public class OrderDAO {

    public String insert(String uID, double total) throws SQLException {
        boolean check = false;
        String stmt = "INSERT INTO Orders (uID, total) VALUES (?,?)";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;

        String orderID = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, uID);
                pstmt.setDouble(2, total);
                check = pstmt.executeUpdate() > 0;
                if (check) {
                    rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        orderID = rs.getString(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error occur at Order.insert: " + e.toString());
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
        return orderID;
    }
}
