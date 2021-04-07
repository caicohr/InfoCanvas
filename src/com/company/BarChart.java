package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarChart extends JPanel {
    private Graphics2D g;
    private int h;
    private int w;
    private ArrayList<String> weather;
    private ArrayList<Float> weatherTwo;
    private ArrayList<Float> relativeData;
    private float max;
    private float min;

    public BarChart() {
        super();
        weather = new ArrayList<>();
        weatherTwo = new ArrayList<>();
        relativeData = new ArrayList<>();
    }

    public void addToWeather(Float f) {
        weatherTwo.add(f);
    }

    public void addToRelativeData(Float f) {
        relativeData.add(f);
    }

    public void setMax(float f) {
        max = f;
    }
    public void setMin(float f) {
        min = f;
    }

    public void addToWeather(String s) {
        weather.add(s);
    }

    public void clearWeather() {
        weather.clear();
        weatherTwo.clear();
    }

    @Override
    public void paintComponent(Graphics g1) {
        g = (Graphics2D) g1;
        w = getWidth();
        h = getHeight();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);

        if (!weather.isEmpty()) {
            int weatherSize = weather.size();
            int buffer = w/weatherSize;
            g.setColor(Color.BLACK);
            //g.drawLine(20,20,20,h-20);
            g.drawLine(20,(h/3)* 2, w-20, (h/3)* 2);
            for (int i = 0; i < weatherSize; i ++) {
                String s = "" + weather.get(i);
                g.drawString(s, (buffer * i) + 20, h/3);
                g.drawString(""+i,(buffer * i) + 20,((h/3)* 2) + 30);
                repaint();
            }
        }

        if (!weatherTwo.isEmpty()) {
            int weatherSizeTwo = weatherTwo.size();
            int buffer = w/(weatherSizeTwo +1);
            g.setColor(Color.BLACK);
            g.drawLine(30,40, 30, h-20);
            g.drawLine(30,h-20, w-5, h-20);
            for (int i = 0; i < weatherSizeTwo; i ++) {
                g.drawString("" + i,60 + (buffer * i), h);
            }
            float third = (max/4) * 3;
            float second = (max/4) * 2;
            g.drawString("" + max, 0, 40);
            g.drawString("" + third,0, h/3);
            g.drawString("" + second, 0, ((h/3) * 2));
            g.drawString("0",0, h-20);
            g.setColor(Color.GREEN);
            for (int i = 0; i < weatherSizeTwo; i++) {
                //g.fillRect(40 + (i * buffer), 30,buffer-5,((int)(relativeData.get(i) * h)) - 30);
                g.fillRect(40 + (buffer * i), h - ((int)(relativeData.get(i) * h) - 50),buffer-5, (((int)(relativeData.get(i) * h))- 70));
            }
            repaint();
        }

    }
}