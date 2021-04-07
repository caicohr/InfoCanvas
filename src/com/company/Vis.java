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
    private int animationSize;
    private ArrayList<Float> time;
    private ArrayList<String> weather;
    private ArrayList<String> traffic;
    private ArrayList<Float> weatherTwo;
    private Timer tim;

    //For showing Bar Chart
    private BarChart barChart;

    //For Background Image
    private BufferedImage backgroundImage;

    //For Time Canvas
    private BufferedImage timeImage;
    private int timePosition;

    //For Weather Canvas
    private BufferedImage weatherImage;

    //For Traffic Canvas
    private BufferedImage trafficImage;
    private BufferedImage trafficImageLight;
    private BufferedImage trafficImageMedium;
    private BufferedImage trafficImageHeavy;

    //For accessing data
    private int accessIndex;

    public Vis() {
        super();
        addMouseListener(this);

        barChart = new BarChart();
        time = new ArrayList<>();
        weather = new ArrayList<>();
        traffic = new ArrayList<>();
        weatherTwo = new ArrayList<>();
        timePosition = 0;
        animationSize = 24;

        ActionListener animateTime = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //you can only change the list's order
                int timerSize = animationSize;
                timePosition += (w/timerSize);
//                for (int i = timerSize -1; i > 0; i--){
//                    Collections.swap(time, i, i - 1);
//                }
                //Weather comparison for displaying on canvas
                if (!weather.isEmpty()) {
                    String compareWeather = weather.get(accessIndex);
                    if (compareWeather.equals("cold")) {
                        try {
                            weatherImage = ImageIO.read(new File("dataset\\images\\weathercold.jpg"));
                            //System.out.println("SAME");
                            repaint();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    } else {
                        try {
                            weatherImage = ImageIO.read(new File("dataset\\images\\weather1.png"));
                            //System.out.println("Not the same");
                            repaint();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                }

                //Traffic comparison
                if (!traffic.isEmpty()){
                    String compareTraffic = traffic.get(accessIndex);
                    if (compareTraffic.equals("light")) {
                        try {
                            trafficImage = ImageIO.read(new File("dataset\\images\\trafficNone.jpg"));
                            trafficImageLight = ImageIO.read(new File("dataset\\images\\trafficLight.jpg"));
                            trafficImageMedium = null;
                            trafficImageHeavy =null;
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                        //System.out.println("Traffic is light");
                    } else if(compareTraffic.equals("medium")) {
                        try {
                            trafficImage = ImageIO.read(new File("dataset\\images\\trafficNone.jpg"));
                            trafficImageLight = ImageIO.read(new File("dataset\\images\\trafficLight.jpg"));
                            trafficImageMedium = ImageIO.read(new File("dataset\\images\\trafficMedium.jpg"));
                            trafficImageHeavy = null;
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                        //System.out.println("Traffic is medium");
                    } else if(compareTraffic.equals("heavy")) {
                        try {
                            trafficImage = ImageIO.read(new File("dataset\\images\\trafficNone.jpg"));
                            trafficImageLight = ImageIO.read(new File("dataset\\images\\trafficLight.jpg"));
                            trafficImageMedium = ImageIO.read(new File("dataset\\images\\trafficMedium.jpg"));
                            trafficImageHeavy = ImageIO.read(new File("dataset\\images\\trafficHeavy.jpg"));
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                        //System.out.println("Traffic is heavy!");
                    } else {
                        try {
                            trafficImage = ImageIO.read(new File("dataset\\images\\trafficNone.jpg"));
                            trafficImageLight = null;
                            trafficImageMedium = null;
                            trafficImageHeavy = null;
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                        //System.out.println("Traffic is none");
                    }
                }

                accessIndex += 1;
                if (accessIndex == 23) {
                    accessIndex = 0;
                }
                if (timePosition >= w) {
                    timePosition = 0;
                }

                if (!weatherTwo.isEmpty()){
                    Float compareWeatherTwo = weatherTwo.get(accessIndex);
                    if (compareWeatherTwo <= 18) {
                        try {
                            weatherImage = ImageIO.read(new File("dataset\\images\\weatherColdest.gif"));
                            System.out.println("Less than 18");
                            repaint();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    } else if (compareWeatherTwo <= 26 && compareWeatherTwo >= 19){
                        try {
                            weatherImage = ImageIO.read(new File("dataset\\images\\weatherWarm.gif"));
                            System.out.println("Between 19 to 26");
                            repaint();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    } else {
                        try {
                            weatherImage = ImageIO.read(new File("dataset\\images\\weatherHottest.jpg"));
                            System.out.println("More than 26");
                            repaint();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        };
        tim = new Timer(1000, animateTime);
    }

    public void setBackgroundImage(String s) {
        try {
            backgroundImage = ImageIO.read(new File(s));
            repaint();
        } catch (IOException ex) {
            System.out.println(ex);
        }
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

    public void resetWeatherTwo() {
        weatherTwo.clear();
    }

    public void resetTraffic() {
        traffic.clear();
    }

    public void addToTime(Float f) {
        time.add(f);
    }

    public void addToWeather(String s) {
        weather.add(s);
    }

    public void addToTraffic(String s) {
        traffic.add(s);
    }

    public void addToWeatherTwo(Float f) {
        weatherTwo.add(f);
    }

    @Override
    public void paintComponent(Graphics g1) {
        g = (Graphics2D) g1;
        w = getWidth();
        h = getHeight();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        g.drawImage(backgroundImage,0,0,w,h,this);

        g.setColor(Color.BLACK);
        if (!time.isEmpty()){
            try {
                //Storing image
                timeImage = ImageIO.read(new File("dataset\\images\\airBalloon.png"));
                //Showing image with resize
                g.drawImage(timeImage, timePosition, 0, w/time.size(),h/10,this);
                repaint();
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

        if (!traffic.isEmpty()) {
            g.drawImage(trafficImage,((w/4) * 2) + 15,((h/4) * 3) - (((h/4) * 3)/12),w/(traffic.size()/3),h/9,this);
            g.drawImage(trafficImageLight,(((w/4) * 2) - (w/(traffic.size()/3))) + 10,((h/4) * 3) - (((h/4) * 3)/12),w/(traffic.size()/3),h/9,this);
            g.drawImage(trafficImageMedium,((w/4) * 1) + 5,((h/4) * 3) - (((h/4) * 3)/12),w/(traffic.size()/3),h/9,this);
            g.drawImage(trafficImageHeavy,(w/4) - (w/(traffic.size()/3)) ,((h/4) * 3) - (((h/4) * 3)/12),w/(traffic.size()/3),h/9,this);
        }

        if (!weatherTwo.isEmpty()) {
            g.setColor(Color.CYAN);
            g.fillRect((w - (4 * w/weatherTwo.size())) - 10 ,(h/4) - 10,(w/(weatherTwo.size() / 2)) + 20,(h/8)+20);
            g.drawImage(weatherImage, w - (4 * w/weatherTwo.size()), h/4, w/(weatherTwo.size() / 2),h/8,this);
            //Showing image with resize
            g.setColor(Color.BLACK);
            int weatherSize = weatherTwo.size();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (y <= h/10) {
            System.out.println("You just clicked the Space for Time");
        }
        if (!weather.isEmpty()) {
            //g.fillRect((w - (4 * w/weather.size())) - 10 ,(h/4) - 10,(w/(weather.size() / 2)) + 20,(h/8)+20);
            if ( x <= ((w - (4 * w/weather.size()) - 10) + ((w/(weather.size() / 2)) + 20)) && x >= ((w - (4 * w/weather.size())) - 10) && y >= ((h/8)+20) && y <= (((h/4) - 10) + ((h/8)+20))){
                System.out.println("Clicked the Space for Weather");
                barChart.clearWeather();
                JFrame newFrame = new JFrame();
                JPanel newPane = new JPanel();
                JButton button = new JButton("Show Input Dialog Box");
                newPane.add(button);
                newFrame.add(newPane);
                newFrame.setSize(400,400);
                newFrame.setTitle("Bar Chart for Weather");
                //newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                newFrame.setVisible(true);
                newFrame.setContentPane(barChart);
                for (var w : weather) {
                    barChart.addToWeather(w);
                }
            }
        }
        if (!weatherTwo.isEmpty()) {
            if ( x <= ((w - (4 * w/weatherTwo.size()) - 10) + ((w/(weatherTwo.size() / 2)) + 20)) && x >= ((w - (4 * w/weatherTwo.size())) - 10) && y >= ((h/8)+20) && y <= (((h/4) - 10) + ((h/8)+20))){
                System.out.println("Clicked the Space for WeatherTwo");
                barChart.clearWeather();
                JFrame newFrame = new JFrame();
                JPanel newPane = new JPanel();
                JButton button = new JButton("Show Input Dialog Box");
                newPane.add(button);
                newFrame.add(newPane);
                newFrame.setSize(900,700);
                newFrame.setTitle("Bar Chart for Weather Two");
                //newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                newFrame.setVisible(true);
                newFrame.setContentPane(barChart);
                float min = 50;
                float max = 0;
                for (var w : weatherTwo) {
                    barChart.addToWeather(w);
                    if (w > max) {
                        max = w;
                    }
                    if (w < min) {
                        min = w;
                    }
                }
                for (var w: weatherTwo) {
                    float rel = w/max;
                    barChart.addToRelativeData(rel);
                }
                barChart.setMax(max);
                barChart.setMin(min);
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
