/**
 * 
 */

package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class Minimax2 implements AlgoJeu {

    /** La profondeur de recherche par d�faut
     */
    private final static int PROFMAXDEFAUT = 4;

   
    // -------------------------------------------
    // Attributs
    // -------------------------------------------
 
    /**  La profondeur de recherche utilis�e pour l'algorithme
     */
    private int profMax = PROFMAXDEFAUT;

     /**  L'heuristique utilis�e par l'algorithme
      */
   private Heuristique h;

    /** Le joueur Min
     *  (l'adversaire) */
    private Joueur joueurMin;

    /** Le joueur Max
     * (celui dont l'algorithme de recherche adopte le point de vue) */
    private Joueur joueurMax;

    /**  Le nombre de noeuds d�velopp� par l'algorithme
     * (int�ressant pour se faire une id�e du nombre de noeuds d�velopp�s) */
       private int nbnoeuds;

    /** Le nombre de feuilles �valu�es par l'algorithme
     */
    private int nbfeuilles;
    
    private String meilleurCoup;


  // -------------------------------------------
  // Constructeurs
  // -------------------------------------------
    public Minimax2(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public Minimax2(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
        nbnoeuds = 0;
//		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
    }

   // -------------------------------------------
  // M�thodes de l'interface AlgoJeu
  // -------------------------------------------
   public String meilleurCoup(PlateauJeu p,int profMa) {
	   /* A vous de compl�ter le corps de ce fichier */
	   meilleurCoup = null;
       nbnoeuds = 0;
       String[] coupsPossibles = p.coupsPossibles(joueurMax);

       int best = Integer.MIN_VALUE;

       for (String c : coupsPossibles) {
           nbnoeuds++;
           PlateauJeu s = jouerBeurre(p, joueurMax, c);
           int minMax = minMax(s, profMa - 1);
           if (best < minMax) {
               best = minMax;
               meilleurCoup = c;
           }
       }
       return meilleurCoup;
       //return null;

    }
  // -------------------------------------------
  // M�thodes publiques
  // -------------------------------------------
    public String toString() {
        return "MiniMax(ProfMax="+profMax+")";
    }



  // -------------------------------------------
  // M�thodes internes
  // -------------------------------------------

    //A vous de jouer pour implanter Minimax
    private int maxMin(PlateauJeu p, int profondeur) {
        String[] coupsPossibles = p.coupsPossibles(joueurMax);

        if (profondeur == 0 || coupsPossibles.length==0) {
            return h.eval(p, joueurMax, profondeur);
        }

        int best = Integer.MIN_VALUE;

        for (String c : coupsPossibles) {
            nbnoeuds++;
            PlateauJeu s = jouerBeurre(p, joueurMax, c);
            best = Math.max(best, minMax(s, profondeur - 1));
        }
        return best;
    }

    private int minMax(PlateauJeu p, int profondeur) {
        String[] coupsPossibles = p.coupsPossibles(joueurMin);
        if (profondeur == 0 || coupsPossibles.length==0) {
            return h.eval(p, joueurMax, profondeur);
        }

        int wrost = Integer.MAX_VALUE;

        for (String c : coupsPossibles) {
            nbnoeuds++;
            PlateauJeu s = jouerBeurre(p, joueurMin, c);
            wrost = Math.min(wrost, maxMin(s, profondeur - 1));
        }

        return wrost;
    }

    private PlateauJeu jouerBeurre(PlateauJeu p, Joueur j, String c) {
        PlateauJeu s = p.copy();
        s.joue(j, c);
        return s;
    }

	
}