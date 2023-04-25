package Programme;

import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

/**
 * Cette classe permet d'aller chercher les images dans un répertoire
 */
public class Images {
    protected int hauteur;
    protected int largeur;
    protected int palette;
    protected ArrayList<ArrayList<ArrayList<Integer>>> list_images;

    public Images() {
        File[] files = this.loadFile();

    }

    private File[] loadFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) { // Vérifier si l'utilisateur a sélectionné un fichier
            File selectedDirectory = fileChooser.getSelectedFile(); // Obtenir le fichier sélectionné
            System.out.println("Le dossier sélectionné est : " + selectedDirectory.getName());
            return selectedDirectory.listFiles();
        }
        return null;
    }
}
