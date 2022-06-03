package com.company.client;

import com.company.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BotClient extends Client {

    public class BotSocketThread extends SocketThread {
//        @Override
//        protected void clientMainLoop() throws IOException, ClassNotFoundException {
//            sendTextMessage("Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
//            super.clientMainLoop();
//        }

        @Override
        protected void processIncomingMessage(String message) throws IOException, ClassNotFoundException {
            ConsoleHelper.writeMessage(message);
            if (!message.contains(": ")) {
                return;
            }
            String[] arrayNameMsg = message.split(": ");
            String name = arrayNameMsg[0];
            String msg = arrayNameMsg[1];
            Calendar calendar = new GregorianCalendar();
            Date date = calendar.getTime();
            SimpleDateFormat format = null;
            switch (msg) {
                case "Привет, Илон!" -> {
                    sendTextMessage("Привет, " + name + "! Чем могу помочь?");
                    sendTextMessage("Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
                }
                case "дата" -> {
                    format = new SimpleDateFormat("d.MM.yyyy");
                    sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
                }
                case "день" -> {
                    format = new SimpleDateFormat("d");
                    sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
                }
                case "месяц" -> {
                    format = new SimpleDateFormat("MMMM");
                    sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
                }
                case "год" -> {
                    format = new SimpleDateFormat("yyyy");
                    sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
                }
                case "время" -> {
                    format = new SimpleDateFormat("H:mm:ss");
                    sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
                }
                case "час" -> {
                    format = new SimpleDateFormat("H");
                    sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
                }
                case "минуты" -> {
                    format = new SimpleDateFormat("m");
                    sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
                }
                case "секунды" -> {
                    format = new SimpleDateFormat("s");
                    sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
                }
            }
        }
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected String getUserName() {
        return "Илон";
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
