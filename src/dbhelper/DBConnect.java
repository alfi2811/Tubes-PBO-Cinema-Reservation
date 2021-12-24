package dbhelper;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    public static Connection conn;
    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            } catch (ClassNotFoundException | SQLException sqle) {
                Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            }
        }
        return conn;
    }
}
