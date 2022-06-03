package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BotClient extends Client {
    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
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
            if (msg.equals("дата")) {
                format = new SimpleDateFormat("d.MM.YYYY");
                sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
            } else if (msg.equals("день")) {
                format = new SimpleDateFormat("d");
                sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
            } else if (msg.equals("месяц")) {
                format = new SimpleDateFormat("MMMM");
                sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
            } else if (msg.equals("год")) {
                format = new SimpleDateFormat("YYYY");
                sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
            } else if (msg.equals("время")) {
                format = new SimpleDateFormat("H:mm:ss");
                sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
            } else if (msg.equals("час")) {
                format = new SimpleDateFormat("H");
                sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
            } else if (msg.equals("минуты")) {
                format = new SimpleDateFormat("m");
                sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
            } else if (msg.equals("секунды")) {
                format = new SimpleDateFormat("s");
                sendTextMessage("Информация для " + name + ": " + format.format(date.getTime()));
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
        return "date_bot_" + (int) (Math.random() * 100);
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
