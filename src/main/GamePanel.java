package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel implements Runnable {
    // Impostazioni delle dimensioni dei tile (caselle) del gioco
    final int originalTileSize = 16; // Dimensione originale di un tile in pixel (16x16)
    final int scale = 3; // Fattore di scala per ingrandire i tile

    final int TileSize = originalTileSize * scale; // Dimensione finale di un tile scalato (es. 16x3 = 48 pixel)

    // Numero massimo di colonne e righe visibili sullo schermo
    final int maxScreenCol = 16; // Il gioco visualizza 16 colonne di tile orizzontalmente
    final int maxScreenRow = 12; // Il gioco visualizza 12 righe di tile verticalmente

    // Dimensioni totali della finestra di gioco in pixel
    final int screenWidth = TileSize * maxScreenCol; // Larghezza totale
    final int screenHeight = TileSize * maxScreenRow; // Altezza totale

    // Qui settiamo gli FPS (Frames Per Second) del gioco
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Settiamo la posizione di default del giocatore
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLUE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        //Questo componente serve a rendere dinamico la massimizzazione dello schermo
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Aggiorna le dimensioni del pannello quando la finestra viene ridimensionata
                System.out.println("Finestra ridimensionata: " + getWidth() + "x" + getHeight());
            }
        });
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    // METODO DELTA/ACCUMULATOR
    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta= 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount= 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {

                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                System.out.println("drawCount: " + drawCount);
                timer = 0;
            }
        }
    }

    public void update() {

        if (keyH.upPressed) {
            playerY -= playerSpeed;
        } else if (keyH.downPressed) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLACK);

        g2.fillRect(playerX, playerY, TileSize, TileSize);

        g2.dispose();
    }
}
