package Vue.assets;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
/**
 * Classe pour créer un label avec la police Inter
 */
public class ModernLabel extends JLabel {
    // Faire un label avec la police Inter
    public ModernLabel(String text) {
        super(text);

        // Mettre un padding
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Changer la police d'écriture à Inter
        try {
            setFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Vue/assets/Inter.ttf"))
                    .deriveFont(Font.PLAIN, 16));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
