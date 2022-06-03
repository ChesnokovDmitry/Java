package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook implements Runnable {
    private LinkedBlockingQueue<Order> queue;
    private final String name;
    boolean busy;

    public Cook(String name) {
        this.name = name;
    }

    public void startCookingOrder(Order order) throws InterruptedException {
        busy = true;
        Thread.sleep(order.getTotalCookingTime() * 10);
        ConsoleHelper.writeMessage("Start cooking - " + order);
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.toString(), name,
                order.getTotalCookingTime(), order.getDishes()));
        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!queue.isEmpty()) {
                    if (!this.isBusy()) {
                        this.startCookingOrder(queue.poll());
                    }
                }

                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
        }
    }
}