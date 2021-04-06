package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Vis extends JPanel implements MouseListener {
    private Graphics2D g;
    private int h;
    private int w;
    private ArrayList<Float> time;
    private ArrayList<String> weather;
    private Timer tim;

    //For showing Bar Chart
    private BarChart barChart;

    //For Time Canvas
    private BufferedImage timeImage;
    private int timePosition;

    //For Weather Canvas
    private BufferedImage weatherImage;

    //For accessing data
    private int accessIndex;

    public Vis() {
        super();
        addMouseListener(this);

        barChart = new BarChart();
        time = new ArrayList<>();
        weather = new ArrayList<>();
        timePosition = 0;

        ActionListener animateTime = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //you can only change the list's order
                int timerSize = time.size();
                timePosition += (w/timerSize);
//                for (int i = timerSize -1; i > 0; i--){
//                    Collections.swap(time, i, i - 1);
//                }
                String compareWeather = weather.get(accessIndex);
                if (compareWeather.equals("cold")) {
                    try {
                        weatherImage = ImageIO.read(new File("D:\\School\\CS490 Info Vis\\Final Project\\weathercold.jpg"));
                        //System.out.println("SAME");
                        repaint();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
                else {
                    try {
                        weatherImage = ImageIO.read(new File("D:\\School\\CS490 Info Vis\\Final Project\\weather1.png"));
                        //System.out.println("Not the same");
                        repaint();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
                accessIndex += 1;
                if (accessIndex == 23) {
                    accessIndex = 0;
                }
                if (timePosition >= w) {
                    timePosition = 0;
                }
            }
        };
        tim = new Timer(1000, animateTime);
    }

    public void stopTimer() {
        tim.stop();
        System.out.println("Timer stopped");
    }

    public void startTimer() {
        tim.start();
        System.out.println("Timer started");
    }

    public void resetTime() {
        time.clear();
        timePosition = 0;
    }

    public void resetWeather() {
        weather.clear();
    }

    public void addToTime(Float f) {
        time.add(f);
    }

    public void addToWeather(String s) {
        weather.add(s);
    }

    @Override
    public void paintComponent(Graphics g1) {
        g = (Graphics2D) g1;
        w = getWidth();
        h = getHeight();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);

        g.setColor(Color.BLACK);
        if (!time.isEmpty()){
            try {
                //Storing image
                timeImage = ImageIO.read(new File("D:\\School\\CS490 Info Vis\\Final Project\\Sun.jpg"));
                //Showing image with resize
                g.drawImage(timeImage, timePosition, 0, w/time.size(),h/10,this);
            } catch (IOException e) {
                System.out.println(e);
            }
//            g.setColor(Color.BLACK);
//            int timeSize = time.size();
//            int buffer = w/timeSize;
//            for (int i = 0; i < timeSize; i ++) {
//                String s = "" + time.get(i);
//                g.drawString(s, buffer * i, 20);
//                repaint();
//            }
        }

        if (!weather.isEmpty()){
            g.setColor(Color.GREEN);
            g.fillRect((w - (4 * w/weather.size())) - 10 ,(h/4) - 10,(w/(weather.size() / 2)) + 20,(h/8)+20);
            g.drawImage(weatherImage, w - (4 * w/weather.size()), h/4, w/(weather.size() / 2),h/8,this);
            //Showing image with resize
            g.setColor(Color.BLACK);
            int weatherSize = weather.size();
            int buffer = w/weatherSize;
//            for (int i = 0; i < weatherSize; i ++) {
//                String s = "" + weather.get(i);
//                g.drawString(s, buffer * i, 50);
//                repaint();
//            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (y <= h/10) {
            System.out.println("You just clicked the Space for Time");
        }
        g.fillRect((w - (4 * w/weather.size())) - 10 ,(h/4) - 10,(w/(weather.size() / 2)) + 20,(h/8)+20);
        if ( x <= ((w - (4 * w/weather.size()) - 10) + ((w/(weather.size() / 2)) + 20)) && x >= ((w - (4 * w/weather.size())) - 10) && y >= ((h/8)+20) && y <= (((h/4) - 10) + ((h/8)+20))){
            System.out.println("Clicked the Space for Weather");
            barChart.clearWeather();
            JFrame newFrame = new JFrame();
            JPanel newPane = new JPanel();
            JButton button = new JButton("Show Input Dialog Box");
            newPane.add(button);
            newFrame.add(newPane);
            newFrame.setSize(400,400);
            //newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setVisible(true);
            newFrame.setContentPane(barChart);
            for (var w : weather) {
                barChart.addToWeather(w);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
