package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread {
        Socket socket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            String userName = null;
            System.out.println("Установлено соединение с " + socket.getRemoteSocketAddress());
            try {
                Connection connection = new Connection(socket);
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Sorry! Произошла ошибка при обмене данными");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            Message messageUser = null;
            do {
                connection.send(new Message(MessageType.NAME_REQUEST));
                messageUser = connection.receive();
                if (messageUser.getType() == MessageType.USER_NAME
                        && !messageUser.getData().isEmpty()
                        && !connectionMap.containsKey(messageUser.getData())) {
                    connectionMap.put(messageUser.getData(), connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));
                    break;
                }
            } while (true);

            return messageUser.getData();
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> entry : connectionMap.entrySet()) {
                String name = entry.getKey();
                if (!name.equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                    System.out.println("В чат добавлен пользователь" + name);
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            do {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + message.getData()));
                } else ConsoleHelper.writeMessage("Error");
            } while (true);
        }
    }

    public static void sendBroadcastMessage(Message message) {
        try {
            for (Map.Entry<String, Connection> entry : connectionMap.entrySet()) {
                entry.getValue().send(message);
            }
        } catch (IOException e) {
            System.out.println("Упс! Вы не смогли отправить сообщение. Пожалуйста, повторите!");
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
            ConsoleHelper.writeMessage("Ура, сервер запущен.");
            while (true) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
