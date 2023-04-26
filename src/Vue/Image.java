package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Image extends JPanel {
    private ArrayList<ArrayList<Integer>> pixelData;
    private int scaleFactor;
    private float palette;

    public Image(ArrayList<ArrayList<Integer>> pixelData, int scaleFactor, float palette) {
        this.pixelData = pixelData;
        this.scaleFactor = scaleFactor;
        this.palette = palette;
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
                if (pixel == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(j * scaleFactor, i * scaleFactor, scaleFactor, scaleFactor);
                    continue;
                }
                int gray = (int) (255.0 * ((palette - pixel) / palette));
                g.setColor(new Color(gray, gray, gray));
                g.fillRect(j * scaleFactor, i * scaleFactor, scaleFactor, scaleFactor);
            }
        }
    }
}
