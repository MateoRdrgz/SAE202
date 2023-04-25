package Vue;

import java.awt.Color;

import javax.swing.*;

public class Fenetre extends JFrame {
    private Menu monMenu; // le composant à afficher dans la fenêtre

    /**
     * Constructeur
     */
    public Fenetre() {
        this.setTitle("Visionneuse de Clusters"); // titre de la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // option de fermeture

        // initialisation des composants dans la fenêtre
        monMenu = new Menu(this);
        initialisation(monMenu);
    }

    /**
     * Initialisation de la fenêtre
     * 
     * @param g la grille à associer à la vue
     */
    private void initialisation(JPanel g) {
        // Ajouter G à la fenêtre
        this.setBackground(Color.WHITE);
        this.add(monMenu);
        this.pack(); // redimensionnement de la fenêtre
    }
}
