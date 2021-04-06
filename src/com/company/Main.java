package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Main extends JFrame{

    private Vis mainPanel;
    private ArrayList<Float> time;

    public Main() {

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        time = new ArrayList<Float>();

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Info Canvas");
        setVisible(true);
    }

    private void countRows() {
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:D:\\School\\CS490 Info Vis\\InfoCanvas\\dataset\\infovis");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM infovis");
            rs.next();
            int count = rs.getInt(1);
            System.out.println("There are " + count + " rows in the table.");
        } catch (SQLException e) {
            System.out.print(e);
            System.out.println("could not connect to Derby!");
        }
    }

    private void queryTime() {
        try {
            mainPanel.resetTime();
            Connection c = DriverManager.getConnection("jdbc:derby:D:\\School\\CS490 Info Vis\\InfoCanvas\\dataset\\infovis");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("Select Time From infovis");
            while (rs.next()) {
                Float f = rs.getFloat(1);
                mainPanel.addToTime(f);
                repaint();
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Could not connect to Derby");
        }
    }

    private void queryWeather() {
        try {
            mainPanel.resetWeather();
            Connection c = DriverManager.getConnection("jdbc:derby:D:\\School\\CS490 Info Vis\\InfoCanvas\\dataset\\infovis");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("Select weather From infovis");
            while (rs.next()) {
                String str = rs.getString(1);
                mainPanel.addToWeather(str);
                repaint();
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Could not connect to Derby");
        }
    }

    private void queryTraffic() {
        try {
            mainPanel.resetTraffic();
            Connection c = DriverManager.getConnection("jdbc:derby:D:\\School\\CS490 Info Vis\\InfoCanvas\\dataset\\infovis");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("Select traffic From infovis");
            while (rs.next()) {
                String str = rs.getString(1);
                mainPanel.addToTraffic(str);
                repaint();
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Could not connect to Derby");
        }
    }

    private JMenuBar setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu timerMenu = new JMenu("Timer");
        JMenu backgroundImageSubMenu = new JMenu("Background Image");
        JMenuItem park = new JMenuItem("Park");
        JMenuItem first = new JMenuItem("First");
        JMenuItem time = new JMenuItem("Add Time");
        JMenuItem weather = new JMenuItem("Add Weather");
        JMenuItem traffic = new JMenuItem("Add Traffic");
        JMenuItem startTimer = new JMenuItem("Start Timer");
        JMenuItem stopTimer = new JMenuItem("Stop Timer");

        menuBar.add(fileMenu);
        menuBar.add(timerMenu);
        fileMenu.add(first);
        fileMenu.add(time);
        fileMenu.add(weather);
        fileMenu.add(traffic);
        fileMenu.add(backgroundImageSubMenu);
        backgroundImageSubMenu.add(park);
        timerMenu.add(startTimer);
        timerMenu.add(stopTimer);

        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked query one");
                countRows();
            }
        });

        time.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryTime();
            }
        });

        weather.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryWeather();
            }
        });

        traffic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryTraffic();
            }
        });

        park.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setBackgroundImage("D:\\School\\CS490 Info Vis\\InfoCanvas\\dataset\\images\\backgroundpark.png");
            }
        });

        startTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.startTimer();
            }
        });

        stopTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.stopTimer();
            }
        });

        return menuBar;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
