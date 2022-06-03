package com.javarush.task.task27.task2712.ad;

import java.util.*;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {}


    //список активных рекламных роликов
    public Map<String, Integer> listOfActiveAdvertisement() {
        Map<String, Integer> activeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Advertisement advertisement : advertisementStorage.list()) {
            if (advertisement.isActive())
                activeMap.put(advertisement.getName(), advertisement.getHits());
        }

        return activeMap;
    }


    //список неактивных рекламных роликов
    public Map<String, Integer> listOfInactiveAdvertisement() {
        Map<String, Integer> inactiveMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Advertisement advertisement : advertisementStorage.list()) {
            if (!advertisement.isActive())
                inactiveMap.put(advertisement.getName(), advertisement.getHits());
        }

        return inactiveMap;
    }


    public static StatisticAdvertisementManager getInstance() {
        if(instance == null) {
            instance = new StatisticAdvertisementManager();
        }

        return instance;
    }
}
