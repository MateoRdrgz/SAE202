package Programme;

import erreurs.HauteurException;
import erreurs.LargeurException;

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
        list_images = new ArrayList<>();
        if(files != null) {
                this.filesToImages(files);

        }
        System.out.println(list_images);
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

    private ArrayList<ArrayList<Integer>> fileToImage(File file, int compteur1) throws HauteurException, LargeurException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int compteur2 = 0;
            ArrayList<ArrayList<Integer>> image = new ArrayList<>();
            while (line != null) {
                if (compteur2 == 0) {
                    if (compteur1 == 0) {
                        String[] data = line.split(",");
                        this.largeur = Integer.parseInt(data[0]);
                        this.hauteur = Integer.parseInt(data[1]);
                        this.palette = Integer.parseInt(data[2]);
                    }
                } else {
                    String[] data = line.split(",");
                    if (data.length == this.largeur) {
                        ArrayList<Integer> ligne = new ArrayList<>();
                        for (String str : data) {
                            try {
                                ligne.add(Integer.parseInt(str));
                            }catch (NumberFormatException e){
                                throw new NumberFormatException("Erreur de nombre");
                            }
                        }
                        image.add(ligne);
                    } else {
                        throw new LargeurException("La photo ne fait pas la bonne largeur");

                    }
                }

                compteur2++;
                //System.out.println(line);
                line = reader.readLine();
            }
            if (compteur2 - 1 != this.hauteur) {
                throw new HauteurException("La photo ne fais pas la bonne hauteur");
            }

            reader.close();
            return image;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private void filesToImages(File[] files){
        //int taille = files.length;
        int compteur1=0;
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".csv") && !file.getName().equals("correspondance.csv")) { // Vérifier si c'est un fichier CSV et qu'il ne s'appelle pas correspondance.csv
                //System.out.println("Contenu de " + file.getName() + ":");

                try {
                    ArrayList<ArrayList<Integer>> image =fileToImage(file,compteur1);

                    list_images.add(image);
                    compteur1++;
                } catch (HauteurException | LargeurException |NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }

}
