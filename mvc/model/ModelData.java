package com.javarush.task.task36.task3608.model;

import com.javarush.task.task36.task3608.bean.User;
import java.util.List;

/*
хранит необходимые данные
для отображения на клиенте
 */
public class ModelData {
    private List<User> users;
    private User activeUser;                                    //текущий пользователь для отображения
    private boolean displayDeletedUserList;


    public boolean isDisplayDeletedUserList() {
        return displayDeletedUserList;
    }

    public void setDisplayDeletedUserList(boolean displayDeletedUserList) {
        this.displayDeletedUserList = displayDeletedUserList;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
