package Programme;

import java.util.ArrayList;

public class Ensembles {
    protected ArrayList<Ensemble> List_Ensemble;
    protected double[][] MatriceDistance;

    public Ensembles(double[][] MatriceDistance){
        List_Ensemble=new ArrayList<>();
        this.MatriceDistance = MatriceDistance;
    }
    public void add_Ensemble(Ensemble ensemble){
        List_Ensemble.add(ensemble);
    }
    public double distanceMin(Ensemble e1, Ensemble e2){
        double distMin = Double.MAX_VALUE;
        for(int i =0;i< e1.Images.size();i++){
            for(int j=0;i<e2.Images.size();j++){
                double dist = Math.abs(e1.getImages().get(i)-e2.getImages().get(j));
                if(dist < distMin){
                    distMin=dist;
                }
            }
        }
        return distMin;
    }

}
