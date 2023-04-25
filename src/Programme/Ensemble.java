package Programme;

import java.util.ArrayList;

public class Ensemble {
    protected ArrayList<Integer> Images;
    public Ensemble(){
        Images = new ArrayList<>();
    }
    public void add_Image(Integer nvImage){
        Images.add(nvImage);
    }

    public ArrayList<Integer> getImages() {
        return Images;
    }
}
