package io.github.captivecow;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ScreenModel {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private final BufferedImage backBuffer;
    private final Graphics2D graphics2D;
    private final AtomicReference<BufferedImage> mainBuffer;
    private final ArrayList<ScreenModelObserver> observers;

    public ScreenModel() {
        backBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        graphics2D = backBuffer.createGraphics();
        mainBuffer = new AtomicReference<>(
                new BufferedImage(backBuffer.getWidth(), backBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB));
        observers = new ArrayList<>();
    }

    public void update() {
        String randomNumber = String.valueOf(Math.random() * 101);
        System.out.println(randomNumber);
        graphics2D.setColor(Color.GREEN);
        graphics2D.fillRect(0, 0, backBuffer.getWidth(), backBuffer.getHeight());
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(randomNumber, WIDTH/2, HEIGHT/2);
        mainBuffer.getAndUpdate(this::updateMainDisplayBuffer);
        notifyObservers();
    }

    public void registerObserver(ScreenModelObserver observer){
        observers.add(observer);
    }

    public BufferedImage updateMainDisplayBuffer(BufferedImage displayBuffer){
        Graphics2D g2d = displayBuffer.createGraphics();
        g2d.drawImage(backBuffer, 0, 0, null);
        g2d.dispose();
        return displayBuffer;
    }

    public BufferedImage getDisplayBuffer(){
        return mainBuffer.get();
    }

    public void notifyObservers(){
        for(ScreenModelObserver observer: observers){
            SwingUtilities.invokeLater(observer::updateView
            );
        }
    }
}
