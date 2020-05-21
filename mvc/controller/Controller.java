package com.javarush.task.task36.task3608.controller;

import com.javarush.task.task36.task3608.model.Model;
import com.javarush.task.task36.task3608.model.ModelData;
import com.javarush.task.task36.task3608.view.EditUserView;
import com.javarush.task.task36.task3608.view.UsersView;

/*
класс будет получать запрос от клиента
 */
public class Controller {
    private Model model;
    private UsersView usersView;
    private EditUserView editUserView;

    public void onShowAllUsers() {                      //показать всех пользователей
        model.loadUsers();
        usersView.refresh(model.getModelData());        //обновляем вывод объекта userView
    }

    public void onShowAllDeletedUsers() {               //показать всех удаленных пользователей
        model.loadDeletedUsers();
        usersView.refresh(model.getModelData());
    }

    public void onOpenUserEditForm(long userId) {       //показать текущего пользователя
        model.loadUserById(userId);
        editUserView.refresh(model.getModelData());     //обновляем вывод объекта editUserView
    }

    public void onUserDelete(long id) {                 //удалить текущего пользователя
        model.deleteUserById(id);
        usersView.refresh(model.getModelData());
    }

    public void onUserChange(String name, long id, int level) {     //обновить данные пользователя
        model.changeUserData(name, id, level);
        usersView.refresh(model.getModelData());
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setEditUserView(EditUserView editUserView) {
        this.editUserView = editUserView;
    }

    public void setUsersView(UsersView usersView) {
        this.usersView = usersView;
    }
}
