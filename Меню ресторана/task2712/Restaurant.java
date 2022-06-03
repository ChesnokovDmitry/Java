package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        Tablet tablet1 = new Tablet(1);
        Tablet tablet2 = new Tablet(2);
        Tablet tablet3 = new Tablet(3);
        Tablet tablet4 = new Tablet(4);
        Tablet tablet5 = new Tablet(5);
        tablet1.setQueue(ORDER_QUEUE);
        tablet2.setQueue(ORDER_QUEUE);
        tablet3.setQueue(ORDER_QUEUE);
        tablet4.setQueue(ORDER_QUEUE);
        tablet5.setQueue(ORDER_QUEUE);

        List<Tablet> tabletList = new ArrayList<>();
        tabletList.add(tablet1);
        tabletList.add(tablet2);
        tabletList.add(tablet3);
        tabletList.add(tablet4);
        tabletList.add(tablet5);

        Cook cook1 = new Cook("Amigo");
        Cook cook2 = new Cook("Bingo");
        cook1.setQueue(ORDER_QUEUE);
        cook2.setQueue(ORDER_QUEUE);

        Thread thread1 = new Thread(cook1);
        Thread thread2 = new Thread(cook2);
        thread1.start();
        thread2.start();

        RandomOrderGeneratorTask randomOrderGeneratorTask = new RandomOrderGeneratorTask(tabletList, ORDER_CREATING_INTERVAL);
        Thread thread = new Thread(randomOrderGeneratorTask);
        thread.start();

        try {
            Thread.sleep(1000);
            thread.interrupt();
            thread.join();
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}