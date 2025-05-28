package dao;

import model.Manager;

public class ManagerDAO extends DAO {
    public ManagerDAO() {
        super();
    }

    public boolean checkLogin(Manager manager) {
        try {
            String sql = "SELECT * FROM Manager WHERE username = ? AND password = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, manager.getUsername());
            pstmt.setString(2, manager.getPassword());
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                manager.setPosition(rs.getString("position"));
                rs.close();
                pstmt.close();
                return true;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}