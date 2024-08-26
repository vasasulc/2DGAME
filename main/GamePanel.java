package main;

import java.awt.*;
import java.io.IOException;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = maxScreenCol * tileSize;
    final int screenHeight = maxScreenRow * tileSize;

    int FPS = 60;

    KeyHandler keyH =  new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double tick = (double) 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long now;
        long timer = 0;
        long tickRate = 0;

        while (gameThread != null){

            now = System.nanoTime();

            delta += (now - lastTime) / tick;
            timer += (now - lastTime);
            lastTime = now;

            if (delta >= 1){
                update();
                repaint();
                delta--;
                tickRate++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS: " + tickRate);
                timer = 0;
                tickRate = 0;
            }
        }
    }

    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);

        g2.dispose();
    }
}


