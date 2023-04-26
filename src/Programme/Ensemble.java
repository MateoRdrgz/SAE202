package Programme;

import java.util.ArrayList;
/**
 * Ensemble d'images
 */
public class Ensemble {
    protected ArrayList<Integer> Images;
    public Ensemble(){
        Images = new ArrayList<>();
    }
    
    /** 
     * Ajoute une image à l'ensemble
     * @param nvImage L'index de l'image à ajouter
     */
    public void add_Image(Integer nvImage){
        Images.add(nvImage);
    }
    public ArrayList<Integer> getImages() {
        return Images;
    }

}
