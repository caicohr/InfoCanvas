package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarChart extends JPanel {
    private Graphics2D g;
    private int h;
    private int w;
    private ArrayList<String> weather;

    public BarChart() {
        super();
        weather = new ArrayList<>();
    }

    public void addToWeather(String s) {
        weather.add(s);
    }

    public void clearWeather() {
        weather.clear();
    }

    @Override
    public void paintComponent(Graphics g1) {
        g = (Graphics2D) g1;
        w = getWidth();
        h = getHeight();

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, w, h);

        if (!weather.isEmpty()) {
            int weatherSize = weather.size();
            int buffer = w/weatherSize;
            g.setColor(Color.BLACK);
            //g.drawLine(20,20,20,h-20);
            g.drawLine(20,((h/3)* 2) + 20, w-20, ((h/3)* 2) + 20);
            for (int i = 0; i < weatherSize; i ++) {
                String s = "" + weather.get(i);
                g.drawString(s, (buffer * i) + 20, h/3);
                g.drawString(""+i,(buffer * i) + 20,(h/3)* 2);
                repaint();
            }
        }
    }
}