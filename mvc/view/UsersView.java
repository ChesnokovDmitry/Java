package com.javarush.task.task36.task3608.view;

import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.controller.Controller;
import com.javarush.task.task36.task3608.model.ModelData;

/*
отображает список пользователей.
 */
public class UsersView implements View {
    private Controller controller;

    @Override
    public void refresh(ModelData modelData) {
        if (!modelData.isDisplayDeletedUserList())
            System.out.println("All users:");
        else System.out.println("All deleted users:");
        for (User user : modelData.getUsers())
            System.out.println("\t" + "User{name='" + user.getName() + "', id=" + user.getId() + ", level=" + user.getLevel() + "}");
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void fireEventShowAllUsers() {   //показать всех пользователей
        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers() {   //показать всех удаленных пользователей
        controller.onShowAllDeletedUsers();
    }

    public void fireEventOpenUserEditForm(long id) {    //отображение текущего пользователя
        controller.onOpenUserEditForm(id);
    }
}
