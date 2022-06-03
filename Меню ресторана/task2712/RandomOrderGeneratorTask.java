package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    List<Tablet> tabletList;
    int interval;

    public RandomOrderGeneratorTask(List<Tablet> tabletList, int interval) {
        this.tabletList = tabletList;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int tablet = (int) (Math.random() * tabletList.size());
                new TestOrder(tabletList.get(tablet));
                Thread.sleep(interval);
            } catch (InterruptedException | IOException e) {

            }
        }
    }
}
