package Vue;

import Vue.assets.ModernButton;
import Vue.assets.ModernLabel;

import javax.swing.*;

import Programme.Images;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Cluster extends JPanel implements ActionListener {

    JButton exit = new ModernButton("Quitter");
    JLabel titre = new ModernLabel("Visionner le Cluster");
    JLabel sousJLabel = new ModernLabel("");
    JFrame parent;

    public Cluster(Images images, JFrame fenetre) {
        this.parent = fenetre;
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new java.awt.Insets(10, 10, 10, 10);

        // Mettre un padding entre chaque élement avec un BorderLayout
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gc.gridx = 0;
        gc.gridy = 0;

        // Augmenter la taille du titre
        titre.setFont(titre.getFont().deriveFont(32.0f));

        add(titre, gc);

        gc.gridx = 0;
        gc.gridy = 1;

        sousJLabel.setText("Nombre de clusters: " + images.getList_images().size());
        add(sousJLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints panelGC = new GridBagConstraints();
        panelGC.fill = GridBagConstraints.BOTH;

        // Calculer le scale factor la taille de l'image réelle et la largeur voulue
        int scaleFactor = 1;

        if (images.getLargeur() >= 16) {
            scaleFactor = 3;
        } else if (images.getLargeur() >= 8) {
            scaleFactor = 5;
        }

        int i = 0;
        // Ajouter les images
        for (ArrayList<ArrayList<Integer>> image : images.getList_images()) {
            panelGC.gridx = (int) (i % Math.ceil((images.getList_images().size() / 5)));
            panelGC.gridy = (int) (i / Math.ceil((images.getList_images().size() / 5)));
            Image imagePanel = new Image(image, scaleFactor, images.getPalette());
            panelGC.insets = new java.awt.Insets(10, 10, 10, 10);
            panel.add(imagePanel, panelGC);
            i++;
        }
        add(panel, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        exit.addActionListener(this);
        add(exit, gc);

        setBackground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            Menu menu = new Menu(parent);
            this.parent.setContentPane(menu);
            this.parent.pack();
        }
    }
}
