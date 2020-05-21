package com.javarush.task.task36.task3608.model;

import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.model.service.UserService;
import com.javarush.task.task36.task3608.model.service.UserServiceImpl;
import com.javarush.task.task36.task3608.view.EditUserView;

import java.util.ArrayList;
import java.util.List;

public class MainModel implements Model {
    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();
    private List<User> list = new ArrayList<>();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {                                           //загрузить пользователей
        list.addAll(userService.getUsersBetweenLevels(1, 100));
        modelData.setUsers(getAllUsers());
    }

    @Override
    public void loadDeletedUsers() {                                    //загрузить удаленных пользователей
        modelData.setDisplayDeletedUserList(true);
        List<User> users = userService.getAllDeletedUsers();
        modelData.setUsers(users);
    }

    @Override
    public void loadUserById(long userId) {                             //загрузить текущего пользователя
        User user = userService.getUsersById(userId);
        modelData.setActiveUser(user);
    }

    @Override
    public void deleteUserById(long id) {                               //удалить текущего пользователя
        userService.deleteUser(id);
        modelData.setUsers(getAllUsers());
    }

    @Override
    public void changeUserData(String name, long id, int level) {       //обновить данные пользователя
        userService.createOrUpdateUser(name, id, level);
        modelData.setUsers(getAllUsers());
    }

    private List<User> getAllUsers() {                                  //получаем список активных пользователей
        List<User> allActiveUsers = userService.getUsersBetweenLevels(1,100);
        return userService.filterOnlyActiveUsers(allActiveUsers);
    }
}
