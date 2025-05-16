package org.example;

public class ManagerDAO {
    public boolean checkLogin(Manager manager) {
        // Giả lập kiểm tra đăng nhập
        if ("admin".equals(manager.getUsername()) && "123456".equals(manager.getPassword())) {
            manager.setPosition("Administrator");
            return true;
        }
        return false;
    }
}