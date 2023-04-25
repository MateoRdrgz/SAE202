package Vue;

import javax.swing.*;

public class Menu extends JFrame {

    JButton loadfFile = new JButton();

    /**
     * Constructeur
     * @param g la grille à associer à l'attribut vue
     */
    public Menu(){
        this.setTitle("Jeu 2048"); //titre de la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //option de fermeture

        //initialisation des composants dans la fenêtre
        initialisation(g);

        //ajout des listeners
        this.addMouseListener(vue); //la fenêtre (this) est écoutée, et c'est vue qui écoute et recevra les appels de fonctions liées à KeyEvent
    }

    /**
     * Initialisation de la fenêtre
     * @param g la grille à associer à la vue
     */
    private void initialisation(){
        //this.vue=new VueGrille(g); //on crée la vue

        this.add(vue); //ajoute 'vue' au JPanel par défaut dans la JFrame
        this.pack(); //redimensionnement de la fenêtre
    }
}
