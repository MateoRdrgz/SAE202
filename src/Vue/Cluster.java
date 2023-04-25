package Vue;

import Vue.assets.ModernButton;
import Vue.assets.ModernLabel;

import javax.swing.*;

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

    public Cluster(ArrayList<ArrayList<ArrayList<Integer>>> images, JFrame fenetre) {
        this.parent = fenetre;
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;

        // Mettre un padding entre chaque élement avec un BorderLayout
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gc.gridx = 0;
        gc.gridy = 0;

        // Augmenter la taille du titre
        titre.setFont(titre.getFont().deriveFont(32.0f));

        add(titre, gc);

        gc.gridx = 0;
        gc.gridy = 1;

        sousJLabel.setText("Nombre de clusters: " + images.size());
        add(sousJLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints panelGC = new GridBagConstraints();
        panelGC.fill = GridBagConstraints.BOTH;

        int i = 0;
        // Ajouter les images
        for (ArrayList<ArrayList<Integer>> image : images) {
            panelGC.gridx = i % 8;
            panelGC.gridy = i / 8;
            Image imagePanel = new Image(image, 10);
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
