package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Image extends JPanel {
    private ArrayList<ArrayList<Integer>> pixelData;
    private int scaleFactor;

    public Image(ArrayList<ArrayList<Integer>> pixelData, int scaleFactor) {
        this.pixelData = pixelData;
        this.scaleFactor = scaleFactor;
        int width = pixelData.get(0).size() * scaleFactor;
        int height = pixelData.size() * scaleFactor;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < pixelData.size(); i++) {
            for (int j = 0; j < pixelData.get(i).size(); j++) {
                int pixel = pixelData.get(i).get(j);
                int gray = (int) (255.0 * ((16.0 - pixel) / 16.0));
                g.setColor(new Color(gray, gray, gray));
                g.fillRect(j * scaleFactor, i * scaleFactor, scaleFactor, scaleFactor);
            }
        }
    }
}
