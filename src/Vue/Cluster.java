package Vue;

import Vue.assets.ModernButton;
import Vue.assets.ModernComboBox;
import Vue.assets.ModernLabel;

import javax.swing.*;

import Programme.Images;
import Programme.Ensemble;
import Programme.Ensembles;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe pour créer le menu de la visionneuse de clusters
 */
public class Cluster extends JPanel implements ActionListener, ChoixAlgo {

    JButton exit = new ModernButton("Choisir une autre image");
    JButton refresh = new ModernButton("Rafraichir");
    JButton importButton = new ModernButton("Importer une image");
    JButton traiterLesImages = new ModernButton("Traiter les images");
    JLabel titre = new ModernLabel("Visionner les Clusters");
    JLabel sousJLabel = new ModernLabel("");
    JLabel seuilLabel = new ModernLabel("Choix de la distance utilisée: 0");
    JFrame parent;
    Ensembles en = null;
    JComboBox<String> choix = new ModernComboBox();
    int valeurSeuil = 0;
    Images imagesRef = null;

    /**
     * Constructeur de la classe Cluster
     * 
     * @param images  Images importées par l'utilisateur
     * @param fenetre Fenêtre parent
     */
    public Cluster(Images images, JFrame fenetre) {
        this.parent = fenetre;
        this.imagesRef = images;
        en = imagesRef.get_Ensembles();
        this.load();
    }

    /**
     * Méthode pour charger la fenêtre de la visionneuse de clusters
     * 
     * @return void
     */
    private void load() {
        // Reset le container
        removeAll();

        // Choix des algorithmes
        choix.removeAllItems();
        for (String choixAlgo : ChoixAlgo) {
            choix.addItem(choixAlgo.toString());
        }

        // Sélectionner le bon algorythme dans la combo
        choix.setSelectedIndex(this.imagesRef.getAlgorithme());

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
                "Nombre de d'images importées: " + imagesRef.getList_images().size() + " sur " + imagesRef.getTotal());
        add(sousJLabel, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridwidth = 2;
        add(choix, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 1;

        
        ArrayList<Ensemble> listeEnsembles = en.getList_Ensemble();

        JLabel clusters = new ModernLabel("Nombre de clusters: " + listeEnsembles.size());
        add(clusters, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(refresh, gc);
        refresh.addActionListener(this);

        gc.gridx = 0;
        gc.gridy = 3;

        add(seuilLabel, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        add(importButton, gc);
        importButton.addActionListener(this);

        gc.gridx = 0;
        gc.gridy = 4;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints panelGC = new GridBagConstraints();
        panelGC.fill = GridBagConstraints.BOTH;

        // Calculer le scale factor la taille de l'image réelle et la largeur voulue
        float scaleFactor = 56.0f / imagesRef.getLargeur();

        Color[] couleurs = new Color[listeEnsembles.size()];

        for (int i = 0; i < listeEnsembles.size(); i++) {
            // Générer une couleur aléatoire
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
                    % Math.ceil(
                            (imagesRef.getList_images().size() / (imagesRef.getList_images().size() >= 4 ? 4 : 1))));
            panelGC.gridy = (int) (i
                    / Math.ceil(
                            (imagesRef.getList_images().size() / (imagesRef.getList_images().size() >= 4 ? 4 : 1))));
            Image imagePanel = new Image(image, scaleFactor, imagesRef.getPalette(), CouleurActuel);
            panelGC.insets = new java.awt.Insets(10, 10, 10, 10);
            panel.add(imagePanel, panelGC);
            i++;
        }
        add(panel, gc);

        gc.gridx = 0;
        gc.gridy = 5;
        exit.addActionListener(this);
        add(exit, gc);

        gc.gridx = 1;
        gc.gridy = 5;
        traiterLesImages.addActionListener(this);
        add(traiterLesImages, gc);

        setBackground(Color.WHITE);
    }

    /**
     * Méthode pour gérer les actions des boutons
     * 
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            // Vider le fichier last_path.txt
            this.imagesRef.resetText();
            Menu menu = new Menu(parent);
            this.parent.setContentPane(menu);
            this.parent.pack();
        } else if (e.getSource() == refresh) {
            this.imagesRef.setAlgorithme(choix.getSelectedIndex());
            en.resetEnsemble();
            en = imagesRef.get_Ensembles();
            this.load();
            this.parent.pack();
        } else if (e.getSource() == importButton) {
            this.imagesRef.ajout_image(en);
            this.load();
            this.parent.pack();
        } else if (e.getSource() == traiterLesImages) {
            this.imagesRef.traiterImages();
            en.resetEnsemble();
            en = imagesRef.get_Ensembles();
            this.load();
            this.parent.pack();
        }
    }
}
