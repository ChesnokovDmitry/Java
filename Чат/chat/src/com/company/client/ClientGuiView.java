package com.company.client;

import javax.swing.*;
import java.awt.*;

public class ClientGuiView {
    private final ClientGuiController controller;

    private JFrame frame = new JFrame("Chesnogram");
    private JTextField textField = new JTextField(50);          //поле ввода сообщений
    private JTextArea messages = new JTextArea(10, 50);     //поле вывода всех сообщений
    private JTextArea users = new JTextArea(10, 10);        //поле активных пользователей чата

    private JMenuBar menu = new JMenuBar();
    private JMenu account = new JMenu("Аккаунт");
    private JMenuItem person = new JMenuItem("Профиль");
    private JMenuItem contacts = new JMenuItem("Контакты");
    private JMenuItem chats = new JMenuItem("Создать чат");
    private JMenuItem exit = new JMenuItem("Выход");

    private JMenu param = new JMenu("Настройки");
    private JMenuItem params = new JMenuItem("Параметры");

    private JMenu help = new JMenu("Помощь");
    private JMenuItem programs = new JMenuItem("О программе");
    private JMenuItem helps = new JMenuItem("Справка");

    public ClientGuiView(ClientGuiController controller) {
        this.controller = controller;
        initView();
    }

    private void initView() {
        textField.setEditable(false);
        messages.setEditable(false);
        users.setEditable(false);

        frame.getContentPane().add(new JScrollPane(users), BorderLayout.WEST);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);      //размещение поля ввода сообщений снизу (юг)
        frame.getContentPane().add(new JScrollPane(messages), BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           //завершить работу, если пользователь закрывает приложение

        account.add(person);
        account.add(contacts);
        account.add(chats);
        account.addSeparator();
        account.add(exit);
        exit.setToolTipText("Нажмите для выхода");
        menu.add(account);

        param.add(params);
        menu.add(param);

        help.add(programs);
        help.add(helps);
        menu.add(help);

        frame.setJMenuBar(menu);

        frame.setVisible(true);                                 //отобразить фрейм
                                                                //сохраняет введеное сообщение, которое выводится на экран
        textField.addActionListener(e -> {
            controller.sendTextMessage(textField.getText());
            textField.setText("");
        });

        exit.addActionListener(e -> {
            if (e.getActionCommand().equals("Выход")) {
                System.exit(0);
            }
        });
    }

    public String getServerAddress() {
        return JOptionPane.showInputDialog(
                frame,
                "Введите адрес сервера:",
                "Конфигурация клиента",
                JOptionPane.QUESTION_MESSAGE);
    }

    public int getServerPort() {
        while (true) {
            String port = JOptionPane.showInputDialog(
                    frame,
                    "Введите порт сервера:",
                    "Конфигурация клиента",
                    JOptionPane.QUESTION_MESSAGE);
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Был введен некорректный порт сервера. Попробуйте еще раз.",
                        "Конфигурация клиента",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getUserName() {
        return JOptionPane.showInputDialog(
                frame,
                "Введите ваше имя:",
                "Конфигурация клиента",
                JOptionPane.QUESTION_MESSAGE);
    }

    public void notifyConnectionStatusChanged(boolean clientConnected) {
        textField.setEditable(clientConnected);
        if (clientConnected) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Соединение с сервером установлено",
                    "Чат",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    frame,
                    "Клиент не подключен к серверу",
                    "Чат",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void refreshMessages() {                                     //выводит сохраненое сообщение на экран
        messages.append(controller.getModel().getNewMessage() + "\n");
    }

    public void refreshUsers() {
        ClientGuiModel model = controller.getModel();
        StringBuilder sb = new StringBuilder();
        for (String userName : model.getAllUserNames()) {
            sb.append(userName).append("\n");
        }
        users.setText(sb.toString());
    }
}
