package Vue.assets;

import javax.swing.*;
import java.awt.*;

public class ModernComboBox extends JComboBox<String> {
    public ModernComboBox() {
        super();
        this.setBackground(Color.white);
        this.setFocusable(false);
        setForeground(new Color(129, 90, 242));
        this.setOpaque(true);
        this.setBorder(new RoundedBorder(new Color(129, 90, 242), 3, 10));

        /**
         * Ajouter un effet de hover sur le bouton
         * @param evt Evenement de la souris
         * @return void
         */
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(248, 249, 250));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(Color.white);
            }
        });

        // Mettre la souris en cursor pointer
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Changer la police d'écriture à Inter
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Vue/assets/Inter.ttf"));
            setFont(font.deriveFont(Font.PLAIN, 16));
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
