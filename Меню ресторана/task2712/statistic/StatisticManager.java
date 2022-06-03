package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private static StatisticManager instance;
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {}

    public static StatisticManager getInstance() {
        if (instance == null) {
            instance = new StatisticManager();
        }

        return instance;
    }


    //логика подсчета общей прибыли за каждый день
    public Map<String, Double> dataFromStorage() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        List<EventDataRow> eventList = statisticStorage.storage.get(EventType.SELECTED_VIDEOS);
        Map<String, Double> totalProfitForEachDay = new TreeMap<>(Collections.reverseOrder());

        for (EventDataRow event : eventList) {
            VideoSelectedEventDataRow videoSelectedEventDataRow = (VideoSelectedEventDataRow) event;
            String date = simpleDateFormat.format(videoSelectedEventDataRow.getDate());
            double amount = (double) videoSelectedEventDataRow.getAmount();

            if (totalProfitForEachDay.containsKey(date))
                totalProfitForEachDay.put(date, totalProfitForEachDay.get(date) + amount);
            else
                totalProfitForEachDay.put(date, amount);
        }

        return totalProfitForEachDay;
    }


    //логика подсчета загрузки повара
    public Map<String, Map<String, Integer>> workingHoursOfTheCook() {
        String day = "";
        String cookName = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        List<EventDataRow> eventList = statisticStorage.storage.get(EventType.COOKED_ORDER);

        //дневная статистика загрузки поваров
        Map<String, Map<String, Integer>> map = new TreeMap<>(Collections.reverseOrder());

        //продолжительность работы повара
        Map<String, Integer> cookDuration = new TreeMap<>();

        for (EventDataRow event : eventList) {
            CookedOrderEventDataRow cookedOrderEventDataRow = (CookedOrderEventDataRow) event;
            String date = simpleDateFormat.format(cookedOrderEventDataRow.getDate());

            if (!day.equals(date)) {
                cookDuration.put(cookedOrderEventDataRow.getCookName(), cookedOrderEventDataRow.getTime());
                map.put(date, cookDuration);
                day = date;
                cookName = cookedOrderEventDataRow.getCookName();
            } else {
                if (cookName.equals(cookedOrderEventDataRow.getCookName())) {
                    cookDuration.computeIfPresent(cookedOrderEventDataRow.getCookName(),
                            (a, b) -> b += cookedOrderEventDataRow.getTime());
                    map.computeIfPresent(date, (a, b) -> b = cookDuration);
                } else {
                    cookDuration.put(cookedOrderEventDataRow.getCookName(), cookedOrderEventDataRow.getTime());
                    map.computeIfPresent(date, (a, b) -> b = cookDuration);
                }
            }
        }

        return map;
    }


    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    private static class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public StatisticStorage() {
            for (EventType type : EventType.values()) {
                storage.put(type, new ArrayList<>());
            }
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }
    }
}
