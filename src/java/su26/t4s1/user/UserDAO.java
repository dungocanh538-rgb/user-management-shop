package su26.t4s1.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import su26.t4s1.utils.DBUtils;

public class UserDAO {

    public UserDTO get(String email) throws SQLException {
        String stmt = "SELECT * FROM tblUsers WHERE email = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDTO dto = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new UserDTO(
                        rs.getString("uID"),
                        rs.getString("password"),
                        rs.getString("roleID"),
                        rs.getString("name"),
                        rs.getBoolean("status"),
                        rs.getString("email") 
                );
            }
        } catch (Exception e) {
            System.out.println("Error at UserDAO.get: " + e.toString());
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
        return dto;
    }

    public boolean update(UserDTO user) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement("UPDATE tblUsers SET name=?, roleID=? WHERE uID=?");
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getRoleID());
                pstmt.setString(3, user.getuID());
                pstmt.execute();
                check = true;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return check;
    }

    public boolean updateStatus(String uID, boolean status) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement("UPDATE tblUsers SET status=? WHERE uID=?");
                pstmt.setBoolean(1, status);
                pstmt.setString(2, uID);
                check = pstmt.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return check;
    }

    public boolean delete(String uID) throws SQLException {
        String stmt = "DELETE FROM tblUsers WHERE uID=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean check = false;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, uID);
            check = pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error occur at delete: " + e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return check;
    }

    public UserDTO checkLogin(String username, String password) throws SQLException {
        String stmt = "SELECT * FROM tblUsers WHERE uID = ? AND password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDTO dto = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new UserDTO(rs.getString("uID"), rs.getString("password"), rs.getString("roleID"), rs.getString("name"), rs.getBoolean("status"));
            }
        } catch (Exception e) {
            System.out.println("Error occur at checkLogin: " + e.toString());
            dto = null;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return dto;
    }

    public List<UserDTO> getListUser(String name) throws SQLException {
        List<UserDTO> list = new ArrayList<>();
        String stmt = "SELECT * FROM tblUsers WHERE name LIKE ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, "%" + name + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new UserDTO(rs.getString("uID"), "****", rs.getString("roleID"), rs.getString("name"), rs.getBoolean("status")));
            }
        } catch (Exception e) {
            System.out.println("Error occur at getListUser: " + e.toString());
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return list;
    }

    public boolean insert(UserDTO dto) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement("INSERT INTO tblUsers(uID, name, roleID, password, status, email) VALUES(?,?,?,?,?,?)");
                pstmt.setString(1, dto.getuID());
                pstmt.setString(2, dto.getName());
                pstmt.setString(3, dto.getRoleID());
                pstmt.setString(4, dto.getPassword());
                pstmt.setBoolean(5, dto.getStatus());
                pstmt.setString(6, dto.getEmail());
                check = pstmt.executeUpdate() > 0;
            }
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

    public boolean checkDuplicate(String uID) throws SQLException {
        String stmt = "SELECT uID FROM tblUsers WHERE uID=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, uID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (Exception e) {
            System.out.println("Error occur at checkLogin: " + e.toString());

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

    public boolean checkDuplicateEmail(String email) throws SQLException {
        String stmt = "SELECT uID FROM tblUsers WHERE email=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (Exception e) {
            System.out.println("Error occur at checkLogin: " + e.toString());

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
}
