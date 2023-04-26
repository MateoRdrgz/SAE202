package Programme;

import java.util.ArrayList;

public class Ensembles {
    protected ArrayList<Ensemble> List_Ensemble;
    protected double[][] MatriceDistance;

    public Ensembles(double[][] MatriceDistance) {
        List_Ensemble = new ArrayList<>();
        this.MatriceDistance = MatriceDistance;
    }

    public void add_Ensemble(Ensemble ensemble) {
        List_Ensemble.add(ensemble);
    }

    public void del_Ensemble(int index) {
        List_Ensemble.remove(index);
    }

    public double distanceMin(Ensemble e1, Ensemble e2) {
        double distMin = Double.MAX_VALUE;
        for (int i = 0; i < e1.Images.size(); i++) {
            for (int j = 0; j < e2.Images.size(); j++) {
                double dist = this.MatriceDistance[e1.Images.get(i)][e2.Images.get(j)];
                if (dist < distMin) {
                    distMin = dist;
                }
            }
        }
        return distMin;
    }

    public double distanceMax(Ensemble e1, Ensemble e2) {
        double distMax = Double.MIN_VALUE;
        for (int i = 0; i < e1.Images.size(); i++) {
            for (int j = 0; j< e2.Images.size(); j++) {
                double dist = this.MatriceDistance[e1.Images.get(i)][e2.Images.get(j)];
                if (dist > distMax) {
                    distMax = dist;
                }
            }
        }
        return distMax;
    }

    public double distanceMoy(Ensemble e1, Ensemble e2) {
        double distMoy = 0;
        for (int i = 0; i < e1.Images.size(); i++) {
            for (int j = 0; j < e2.Images.size(); j++) {
                distMoy += this.MatriceDistance[e1.Images.get(i)][e2.Images.get(j)];
            }
        }
        return distMoy / (e1.Images.size() * e2.Images.size());
    }

    public void oneImgOneEns() {

        for (int i = 0; i < MatriceDistance.length; i++) {
            Ensemble ensemblei = new Ensemble();
            ensemblei.add_Image(i);
            List_Ensemble.add(ensemblei);
        }
    }

    public void fusionEnsemble(Ensemble e1, Ensemble e2, int index2) {
        for (int i = 0; i < e2.Images.size(); i++) {
            e1.add_Image(e2.Images.get(i));
        }
        this.del_Ensemble(index2);
    }

    public ArrayList<Double> algoSaut(int algo) {
        this.oneImgOneEns();
        ArrayList<Double> distance = new ArrayList<>();
        while (List_Ensemble.size() > 1) {
            int ei = 0;
            int ej = 0;
            Ensemble ensemble1 = null;
            Ensemble ensemble2 = null;
            double plusproche = Double.MAX_VALUE;
            for (int i = 0; i < List_Ensemble.size(); i++) {
                for (int j = i + 1; j < List_Ensemble.size(); j++) {
                    double distmin=0;
                    if(algo==0){
                        distmin = this.distanceMin(List_Ensemble.get(i), List_Ensemble.get(j));
                    } else if (algo==1) {
                        distmin = this.distanceMax(List_Ensemble.get(i), List_Ensemble.get(j));
                    } else if (algo==2) {
                        distmin = this.distanceMoy(List_Ensemble.get(i), List_Ensemble.get(j));
                    }

                    if (distmin < plusproche) {
                        plusproche = distmin;
                        ensemble1 = List_Ensemble.get(i);
                        ensemble2 = List_Ensemble.get(j);
                        ei = i;
                        ej = j;
                    }
                }
            }
            fusionEnsemble(ensemble1, ensemble2, ej);
            distance.add(plusproche);
        }
        return distance;
    }

    public ArrayList<Ensemble> getList_Ensemble() {
        return List_Ensemble;
    }

    public void algoSaut(Double[] heuristique, int algo) {
        this.oneImgOneEns();
        ArrayList<Double> distance = new ArrayList<>();
        boolean drap = true;
        while (drap) {
            int ei = 0;
            int ej = 0;
            Ensemble ensemble1 = null;
            Ensemble ensemble2 = null;
            double plusproche = Double.MAX_VALUE;
            for (int i = 0; i < List_Ensemble.size(); i++) {
                for (int j = i + 1; j < List_Ensemble.size(); j++) {
                    double distmin=0;
                    if(algo==0){
                        distmin = this.distanceMin(List_Ensemble.get(i), List_Ensemble.get(j));
                    } else if (algo==1) {
                        distmin = this.distanceMax(List_Ensemble.get(i), List_Ensemble.get(j));
                    } else if (algo==2) {
                        distmin = this.distanceMoy(List_Ensemble.get(i), List_Ensemble.get(j));
                    }

                    if (distmin < plusproche) {
                        plusproche = distmin;
                        ensemble1 = List_Ensemble.get(i);
                        ensemble2 = List_Ensemble.get(j);
                        ei = i;
                        ej = j;
                    }
                }
            }
            fusionEnsemble(ensemble1, ensemble2, ej);
            distance.add(plusproche);
            if (heuristique[0] == plusproche) {
                drap = false;
            }
        }
    }

    public Double[] calculerHeuristique(ArrayList<Double> distance) {
        double ecart = Double.MIN_VALUE;
        double index = 0;
        double index2 = 0;
        for (int i = 0; i < distance.size() - 1; i++) {
            if (distance.get(i + 1) - distance.get(i) > ecart) {
                ecart = distance.get(i + 1) - distance.get(i);
                index = distance.get(i);
                index2 = distance.get(i + 1);
            }
        }

        Double[] doubles = new Double[2];
        doubles[0] = index;
        doubles[1] = index2;
        return doubles;
    }

    public void resetEnsemble() {
        List_Ensemble.clear();
    }
}


