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

    @Override
    public void run() {

        while (gameThread != null) {
            // System.out.println("Il loop del gioco sta funzionando");

            // 1 AGGIORNARE: Aggiornare le informazioni (es. la posizione del giocatore)
            update();

            // 2 DISEGNARE: Ri-disegnare la schermata con le informazioni aggiornate
            repaint();
        }
    }

    public void update() {

        if (keyH.upPressed) {
            playerY -= playerSpeed;
        } else if (keyH.downPressed) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed) {
            playerY -= playerSpeed;
        } else if (keyH.rightPressed) {
            playerY += playerSpeed;
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
