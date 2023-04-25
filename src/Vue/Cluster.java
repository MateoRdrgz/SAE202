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

    public Cluster(ArrayList<ArrayList<ArrayList<Integer>>> images) {
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

        sousJLabel.setText("Nombre de clusters: " + images.size());
        panel.add(sousJLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        exit.addActionListener(this);
        panel.add(exit, gc);

        this.add(panel);
        this.setBackground(Color.WHITE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            System.exit(0);
        }
    }
}
