package com.javarush.task.task27.task2712.statistic.event;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.util.Date;
import java.util.List;

public class CookedOrderEventDataRow implements EventDataRow {
    String tabletName;
    String cookName;
    int cookingTimeSeconds;
    List<Dish> cookingDishes;
    Date currentDate;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishes) {
        this.tabletName = tabletName;                           //имя планшета
        this.cookName = cookName;                               //имя повара
        this.cookingTimeSeconds = cookingTimeSeconds;           //время приготовления заказа в секундах
        this.cookingDishes = cookingDishes;                     //список блюд для приготовления
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }

    public String getCookName() {
        return cookName;
    }
}
