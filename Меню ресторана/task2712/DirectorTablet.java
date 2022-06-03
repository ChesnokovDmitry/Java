package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.*;

public class DirectorTablet {

    //какую сумму заработали на рекламе, сгруппировать по дням
    public void printAdvertisementProfit() {
        Map<String, Double> dataFromStorage = StatisticManager.getInstance().dataFromStorage();
        double totalAmount = 0;

        for (Map.Entry<String, Double> entry : dataFromStorage.entrySet()) {
            ConsoleHelper.writeMessage(String.format("%s - %.2f",entry.getKey(), entry.getValue() / 100));
            totalAmount += entry.getValue() / 100;
        }

        ConsoleHelper.writeMessage(String.format("Total - %.2f", totalAmount));
    }


    //загрузка (рабочее время) повара, сгруппировать по дням
    public void printCookWorkloading() {
        Map<String, Map<String, Integer>> workingHoursOfTheCook = StatisticManager.getInstance().workingHoursOfTheCook();

        for (Map.Entry<String, Map<String, Integer>> entry : workingHoursOfTheCook.entrySet()) {
            ConsoleHelper.writeMessage(String.format("%s", entry.getKey()));

            for (Map.Entry<String, Integer> entry1 : entry.getValue().entrySet()) {
                ConsoleHelper.writeMessage(String.format("%s - %d min", entry1.getKey(), entry1.getValue()));
            }
        }
    }


    //список активных роликов и оставшееся количество показов по каждому
    public void printActiveVideoSet() {
        Map<String, Integer> activeMap = StatisticAdvertisementManager.getInstance().listOfActiveAdvertisement();

        for (Map.Entry<String, Integer> map : activeMap.entrySet()) {
            ConsoleHelper.writeMessage(String.format("%s - %d", map.getKey(), map.getValue()));
        }
    }


    //список неактивных роликов (с оставшемся количеством показов равным нулю)
    public void printArchivedVideoSet() {
        Map<String, Integer> inactiveMap = StatisticAdvertisementManager.getInstance().listOfInactiveAdvertisement();

        for (Map.Entry<String, Integer> map : inactiveMap.entrySet()) {
            ConsoleHelper.writeMessage(String.format("%s", map.getKey()));
        }
    }
}
