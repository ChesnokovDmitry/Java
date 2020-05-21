package com.javarush.task.task36.task3608.view;

import com.javarush.task.task36.task3608.controller.Controller;
import com.javarush.task.task36.task3608.model.ModelData;

/*
отображает данные о редактировании конкретного пользователя
 */
public class EditUserView implements View {
    private Controller controller;

    @Override
    public void refresh(ModelData modelData) {                          //выбор текущего пользователя
        System.out.println("User to be edited:");
        System.out.println("\t" + modelData.getActiveUser());
        System.out.println("===================================================");
    }

    public void fireEventUserDeleted(long id) {                         //удаление пользователя
        controller.onUserDelete(id);
    }

    public void fireEventUserChanged(String name, long id, int level) { //обновить данные пользователя
        controller.onUserChange(name, id, level);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
