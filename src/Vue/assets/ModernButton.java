package Vue.assets;

import javax.swing.*;
import java.awt.*;
/**
 * Cette classe permet de créer un bouton moderne
 */
public class ModernButton extends JButton {
    /**
     * Constructeur de la classe ModernButton
     * @param text Texte du bouton
     */
    public ModernButton(String text) {
        super(text);
        setForeground(new Color(129, 90, 242));
        setBackground(Color.white);
        setFocusPainted(false);
        
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

        // Mettre des bordures de 3px et arrondie
        setBorder(new RoundedBorder(new Color(129, 90, 242), 3, 10));

        // Mettre un padding
        setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        // Changer la police d'écriture à Inter
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Vue/assets/Inter.ttf"));
            setFont(font.deriveFont(Font.PLAIN, 16));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
