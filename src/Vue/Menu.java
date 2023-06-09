package Vue;

import Programme.Images;
import Vue.assets.ModernButton;
import Vue.assets.ModernComboBox;
import Vue.assets.ModernLabel;

import javax.swing.*;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener, ChoixAlgo {

    JButton loadFile = new ModernButton("Charger les images");
    JLabel titre = new ModernLabel("Menu de la visionneuse de Clusters");
    JComboBox<String> choix = new ModernComboBox();
    JFrame parent;

    /**
     * Constructeur du menu
     */
    public Menu(JFrame fenetre) {

        // Choix des algorithmes
        for (String choixAlgo : ChoixAlgo) {
            choix.addItem(choixAlgo.toString());
        }

        this.parent = fenetre;
        JPanel panel = new JPanel(); // création d'un panel
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new java.awt.Insets(10, 10, 10, 10);

        // Mettre un padding entre chaque élement avec un BorderLayout
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gc.gridx = 0;
        gc.gridy = 0;

        // Augmenter la taille du titre
        titre.setFont(titre.getFont().deriveFont(32.0f));

        panel.add(titre, gc);

        gc.gridx = 0;
        gc.gridy = 1;

        panel.add(choix, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        panel.add(loadFile, gc);

        this.add(panel);
        panel.setBackground(Color.WHITE);
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
            Images images = new Images(choix.getSelectedIndex());

            if (images.getList_images().size() == 0) {
                images.resetText();
                JOptionPane.showMessageDialog(this, "Aucune image n'a été chargée", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cluster cluster = new Cluster(images, this.parent);
            this.parent.setContentPane(cluster);
            this.parent.pack();
        }
    }
}
