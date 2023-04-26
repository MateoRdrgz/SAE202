package Vue;

import Vue.assets.ModernButton;
import Vue.assets.ModernLabel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Programme.Images;
import Programme.Ensemble;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Cluster extends JPanel implements ActionListener, ChangeListener {

    JButton exit = new ModernButton("Quitter");
    JButton refresh = new ModernButton("Rafraichir");
    JSlider seuil = new JSlider(0, 100, 0);
    JLabel titre = new ModernLabel("Visionner le Cluster");
    JLabel sousJLabel = new ModernLabel("");
    JLabel seuilLabel = new ModernLabel("Choix de la distance utilisée: 0");
    JFrame parent;

    Images imagesRef = null;

    public Cluster(Images images, JFrame fenetre) {
        this.parent = fenetre;
        this.imagesRef = images;
        this.load();
    }

    private void load() {
        // Reset le container
        removeAll();

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

        sousJLabel.setText(
                "Nombre de clusters importés: " + imagesRef.getList_images().size() + " sur " + imagesRef.getTotal());
        add(sousJLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        add(seuilLabel, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(seuil, gc);
        seuil.setPaintTicks(true);
        seuil.setPaintLabels(true);
        seuil.addChangeListener(this);

        gc.gridx = 2;
        gc.gridy = 2;
        add(refresh, gc);
        refresh.addActionListener(this);

        gc.gridx = 0;
        gc.gridy = 3;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints panelGC = new GridBagConstraints();
        panelGC.fill = GridBagConstraints.BOTH;

        // Calculer le scale factor la taille de l'image réelle et la largeur voulue
        int scaleFactor = 1;

        if (imagesRef.getLargeur() >= 16) {
            scaleFactor = 3;
        } else if (imagesRef.getLargeur() >= 8) {
            scaleFactor = 5;
        }

        ArrayList<Ensemble> listeEnsembles = imagesRef.get_Ensembles();
        Color[] couleurs = new Color[listeEnsembles.size()];

        for (int i = 0; i < listeEnsembles.size(); i++) {
            couleurs[i] = new Color((int) (Math.random() * 0x1000000));
        }

        Color CouleurActuel = new Color(0, 0, 0);
        int i = 0;
        // Ajouter les images
        for (ArrayList<ArrayList<Integer>> image : imagesRef.getList_images()) {

            for (int y = 0; y < listeEnsembles.size(); y++) {
                if (listeEnsembles.get(y).getImages().contains(i)) {
                    CouleurActuel = couleurs[y];
                }
            }
            panelGC.gridx = (int) (i
                    % Math.ceil((imagesRef.getList_images().size() / (imagesRef.getList_images().size() > 5 ? 5 : 2))));
            panelGC.gridy = (int) (i
                    / Math.ceil((imagesRef.getList_images().size() / (imagesRef.getList_images().size() > 5 ? 5 : 2))));
            Image imagePanel = new Image(image, scaleFactor, imagesRef.getPalette(), CouleurActuel);
            panelGC.insets = new java.awt.Insets(10, 10, 10, 10);
            panel.add(imagePanel, panelGC);
            i++;
        }
        add(panel, gc);

        gc.gridx = 0;
        gc.gridy = 4;
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
        } else if (e.getSource() == refresh) {
            this.load();
            this.parent.pack();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == seuil) {
            seuilLabel.setText("Choix de la distance utilisée: " + seuil.getValue());
        }
    }
}
