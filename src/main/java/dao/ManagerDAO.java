package dao;

import model.Manager;

public class ManagerDAO extends DAO {
    public ManagerDAO() {
        super();
    }

    public boolean checkLogin(Manager manager) {
        if ("admin".equals(manager.getUsername()) && "123456".equals(manager.getPassword())) {
            manager.setPosition("Administrator");
            return true;
        }
        return false;
    }
}