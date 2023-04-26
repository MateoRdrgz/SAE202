package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe pour créer une image à partir d'une matrice de pixels et d'un facteur
 * d'échelle
 */
public class Image extends JPanel {
    private ArrayList<ArrayList<Integer>> pixelData;
    private float scaleFactor;
    private float palette;
    private Color color;

    /**
     * Constructeur de la classe Image
     * 
     * @param pixelData   Matrice de pixels
     * @param scaleFactor Facteur d'échelle
     * @param palette     Palette de couleurs
     * @param color       Couleur
     */
    public Image(ArrayList<ArrayList<Integer>> pixelData, float scaleFactor, float palette, Color color) {
        this.pixelData = pixelData;
        this.scaleFactor = scaleFactor;
        this.palette = palette;
        int width = (int) (pixelData.get(0).size() * scaleFactor);
        int height = (int) (pixelData.size() * scaleFactor);
        this.color = color;
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Dessiner l'image à partir de la matrice de pixels
     * 
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < pixelData.size(); i++) {
            for (int j = 0; j < pixelData.get(i).size(); j++) {
                int pixel = pixelData.get(i).get(j);
                if (pixel == 0) {
                    g.setColor(color);
                    g.fillRect((int) (j * scaleFactor), (int)(i * scaleFactor), (int)scaleFactor, (int)scaleFactor);
                    continue;
                }
                int gray = (int) (255.0 * ((palette - pixel) / palette));
                g.setColor(new Color(gray, gray, gray));
                g.fillRect((int) (j * scaleFactor), (int)(i * scaleFactor), (int)scaleFactor, (int)scaleFactor);
            }
        }
    }
}
