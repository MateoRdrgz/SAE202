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
    protected int total;
    protected int algorithme;
    protected ArrayList<ArrayList<ArrayList<Integer>>> list_images;

    public Images(int algorithme) {
        File[] files = this.loadFile();
        list_images = new ArrayList<>();
        if (files != null) {
            this.filesToImages(files);
        }
        this.algorithme = algorithme;
    }

    /**
     * Set ALgorithme
     * 
     * @param algorithme L'algorithme à utiliser
     */

    public void setAlgorithme(int algorithme) {
        this.algorithme = algorithme;
    }

    // Get largeur
    public int getLargeur() {
        return this.largeur;
    }

    // Get hauteur
    public int getHauteur() {
        return this.hauteur;
    }

    // Get list_images
    public ArrayList<ArrayList<ArrayList<Integer>>> getList_images() {
        return this.list_images;
    }

    // Get palette
    public int getPalette() {
        return this.palette;
    }

    // Get total
    public int getTotal() {
        return this.total;
    }

    // Get algorithme
    public int getAlgorithme() {
        return this.algorithme;
    }

    /**
     * calcule la matrice, initialise une instance Ensemble, et lance les
     * algorithmes
     * 
     * @return la liste d'ensembles
     */
    public ArrayList<Ensemble> get_Ensembles() {
        double[][] MatriceDistance = this.calculerDistanceImage();
        Ensembles en = new Ensembles(MatriceDistance);

        ArrayList<Double> distance = en.algoSaut(algorithme);
        Double[] heuristique = en.calculerHeuristique(distance);
        en.resetEnsemble();
        en.algoSaut(heuristique, algorithme);

        return en.List_Ensemble;
    }

    /**
     * Charge les fichiers avec JFileChooser afin des les convertir en images
     * utilisables par la suite
     * 
     * @return les fichiers chargés
     */
    private File[] loadFile() {
        // Vérifie si le fichier last_path.txt est vide
        File last_path = new File("last_path.txt");
        if (last_path.length() != 0) {
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(last_path))) {
                    String path = br.readLine();
                    File directory = new File(path);
                    if (directory.isDirectory()) {
                        this.total = directory.listFiles().length;
                        return directory.listFiles();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) { // Vérifier si l'utilisateur a sélectionné un fichier
                File selectedDirectory = fileChooser.getSelectedFile(); // Obtenir le fichier sélectionné
                System.out.println("Le dossier sélectionné est : " + selectedDirectory.getName());
                this.total = selectedDirectory.listFiles().length;

                // Ecrit le chemin du dossier dans le fichier last_path.txt
                try {
                    FileWriter fw = new FileWriter(last_path);
                    fw.write(selectedDirectory.getAbsolutePath());
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return selectedDirectory.listFiles();
            }
        }
        return null;
    }

    public void resetText() {
        File last_path = new File("last_path.txt");
        try {
            FileWriter fw = new FileWriter(last_path);
            fw.write("");
            fw.close();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }

    /**
     * Convertit un fichier CSV en image
     * 
     * @param file      le fichier CSV
     * @param compteur1 L'indice du fichier
     * @return La matrice de l'image
     * @throws HauteurException      Si la hauteur de l'image n'est pas bonne : soit
     *                               supérieur ou inférieur au header
     * @throws LargeurException      Si la largeur de l'image n'est pas bonne : soit
     *                               supérieur ou inférieur au header
     * @throws NumberFormatException Si un caractère n'est pas un nombre
     */
    private ArrayList<ArrayList<Integer>> fileToImage(File file, int compteur1)
            throws HauteurException, LargeurException, NumberFormatException {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            int compteur2 = 0;
            int testPixel = 0;
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
                            testPixel = Integer.parseInt(str);

                            if (testPixel < 0 || testPixel > this.palette)
                                throw new NumberFormatException("Erreur de nombre, pas dans la palette");
                            ligne.add(testPixel);
                        }
                        image.add(ligne);
                    } else {
                        throw new LargeurException("La photo ne fait pas la bonne largeur");

                    }
                }

                compteur2++;
                line = reader.readLine();
            }
            if (compteur2 - 1 != this.hauteur) {
                throw new HauteurException("La photo ne fais pas la bonne hauteur");
            }

            reader.close();
            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convertit les fichiers CSV en images, fait une boucle sur les fichiers
     * 
     * @param files Les fichiers CSV
     */
    private void filesToImages(File[] files) {
        // int taille = files.length;
        int compteur1 = 0;
        for (File file : files) {
            // Vérifier si c'est un fichier CSV et qu'il ne s'appelle pas correspondance.csv
            if (file.isFile() && file.getName().endsWith(".csv") && !file.getName().equals("correspondance.csv")) {
                try {
                    ArrayList<ArrayList<Integer>> image = fileToImage(file, compteur1);

                    list_images.add(image);
                    compteur1++;
                } catch (HauteurException | LargeurException | NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    /**
     * Calcul la distance entre chaque image
     * 
     * @return la matrice de distance
     */
    public double[][] calculerDistanceImage() {
        double[][] MatriceDistance = new double[list_images.size()][list_images.size()];
        for (int i = 0; i < list_images.size(); i++) {
            for (int j = i + 1; j < list_images.size(); j++) {
                ArrayList<ArrayList<Integer>> image1 = list_images.get(i);
                ArrayList<ArrayList<Integer>> image2 = list_images.get(j);
                double[] image1Array = new double[hauteur * largeur];
                double[] image2Array = new double[hauteur * largeur];
                int index = 0;
                for (ArrayList<Integer> ligne : image1) {
                    for (Integer pixel : ligne) {
                        image1Array[index] = pixel;
                        index++;
                    }
                }
                index = 0;
                for (ArrayList<Integer> ligne : image2) {
                    for (Integer pixel : ligne) {
                        image2Array[index] = pixel;
                        index++;
                    }
                }
                double distance = 0;
                for (int k = 0; k < image1Array.length; k++) {
                    distance += Math.pow((image1Array[k] - image2Array[k]), 2);
                }
                distance = Math.sqrt(distance);
                MatriceDistance[i][j] = distance;
                MatriceDistance[j][i] = distance;
            }
        }
        return MatriceDistance;
    }

    public void traiterImages() {
        ArrayList<ArrayList<ArrayList<Integer>>> list_images8x8 = new ArrayList<>();
        // Convertir toutes les matrices de pixels en 8x8 en faisant la moyenne par
        // groupe de pixels adjacents
        for (ArrayList<ArrayList<Integer>> image : list_images) {
            ArrayList<ArrayList<Integer>> image8x8 = new ArrayList<>();
            for (int i = 0; i < image.size(); i += 8) {
                ArrayList<Integer> ligne8x8 = new ArrayList<>();
                for (int j = 0; j < image.get(i).size(); j += 8) {
                    int somme = 0;
                    for (int k = i; k < i + 8; k++) {
                        for (int l = j; l < j + 8; l++) {
                            somme += image.get(k).get(l);
                        }
                    }
                    ligne8x8.add(somme / 64);
                }
                image8x8.add(ligne8x8);
            }
            list_images8x8.add(image8x8);
        }

        this.list_images = list_images8x8;
    }

}
