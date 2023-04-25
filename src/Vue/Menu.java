package Vue;

import Programme.Images;
import Vue.assets.ModernButton;

import javax.swing.*;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener {

    JButton loadFile = new ModernButton("Charger les images");
    JLabel titre = new JLabel("Visionneuse de Cluster");

    /**
     * Constructeur du menu
     */
    public Menu() {
        JPanel panel = new JPanel(); // création d'un panel
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;

        // Mettre un padding entre chaque élement avec un BorderLayout
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        


        gc.gridx = 0;
        gc.gridy = 0;

        // Augmenter la taille du titre
        titre.setFont(titre.getFont().deriveFont(32.0f));

        panel.add(titre, gc);

        gc.gridx = 0;
        gc.gridy = 1;

        panel.add(loadFile, gc);
        
        this.add(panel);
        this.setBackground(Color.WHITE);
        // ajout des listeners
        loadFile.addActionListener(this);
    }

    /**
     * Action à effectuer lors d'un clic sur un bouton
     * 
     * @param e évènement
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadFile) {
            Images images = new Images();
        }
    }
}
