package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    public static Connection con;

    public DAO() {
        if (con == null) {
            // Đã thêm allowPublicKeyRetrieval=true để tránh lỗi xác thực
            String dbUrl = "jdbc:mysql://localhost:3307/chess?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
            String dbClass = "com.mysql.cj.jdbc.Driver";
            try {
                // Class.forName(dbClass); // Modern drivers tự động load
                con = DriverManager.getConnection(dbUrl, "root", "Cnpm@2020?");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
