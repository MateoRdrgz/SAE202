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

    public Images(int algorithme) {
        File[] files = this.loadFile();
        list_images = new ArrayList<>();
        if (files != null) {
            this.filesToImages(files);
        }
    }

    // Get list_images
    public ArrayList<ArrayList<ArrayList<Integer>>> getList_images() {
        return this.list_images;
    }

    public void test() {
        double[][] MatriceDistance = this.calculerDistanceImage();
        for (int i = 0; i < MatriceDistance.length; i++) {
            for (int j = 0; j < MatriceDistance[i].length; j++) {
                System.out.print(MatriceDistance[i][j] + " ");
            }
            System.out.println(); // passer à la ligne suivante après chaque ligne de la matrice
        }
    }

    private File[] loadFile() {
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

    private ArrayList<ArrayList<Integer>> fileToImage(File file, int compteur1)
            throws HauteurException, LargeurException {
        try {
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
                                try {
                                    testPixel = Integer.parseInt(str);

                                    if (testPixel < 0 || testPixel > this.palette)
                                        throw new NumberFormatException("Erreur de nombre, pas dans la palette");
                                    ligne.add(testPixel);
                                } catch (NumberFormatException e) {
                                    throw new NumberFormatException("Erreur de nombre");
                                }
                            }
                            System.out.println(ligne);
                            image.add(ligne);
                        } else {
                            throw new LargeurException("La photo ne fait pas la bonne largeur");

                        }
                    }

                    compteur2++;
                    // System.out.println(line);
                    line = reader.readLine();
                }
                if (compteur2 - 1 != this.hauteur) {
                    throw new HauteurException("La photo ne fais pas la bonne hauteur");
                }

                reader.close();
                System.out.println("");
                return image;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Erreur de nombre");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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

}
