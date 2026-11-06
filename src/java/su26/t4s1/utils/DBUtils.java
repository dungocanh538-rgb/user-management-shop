
package su26.t4s1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtils {
    private static final String DB_NAME="UserManagement";
    private static final String USER_NAME="SA";
    private static final String PASSWORD="YOUR_DB_PASSWORD";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn= null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url="jdbc:sqlserver://localhost:1433;databaseName="+ DB_NAME + ";trustServerCertificate=true";
        conn= DriverManager.getConnection(url, USER_NAME, PASSWORD);
        return conn;
    }
}
