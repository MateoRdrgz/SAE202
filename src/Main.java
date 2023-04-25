import Programme.Images;

public class Main {
    public static void main(String[] args) {
        Images test = new Images();
        double[][] MatriceDistance = test.calculerDistanceImage();
        for (int i = 0; i < MatriceDistance.length; i++) {
            for (int j = 0; j < MatriceDistance[i].length; j++) {
                System.out.print(MatriceDistance[i][j] + " ");
            }
            System.out.println(); // passer à la ligne suivante après chaque ligne de la matrice
        }
    }
}