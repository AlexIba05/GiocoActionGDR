package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel {
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

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLUE);
        this.setDoubleBuffered(true);

        //Questo componente serve a rendere dinamico la massimizzazione dello schermo
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Aggiorna le dimensioni del pannello quando la finestra viene ridimensionata
                System.out.println("Finestra ridimensionata: " + getWidth() + "x" + getHeight());
            }
        });
    }
}
