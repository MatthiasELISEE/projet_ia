package  iia.jeux.alg;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;

public interface AlgoJeu {

    /** Renvoie le meilleur
     * @param p
     * @param nbTours 
     * @return
     */
	public String meilleurCoup(PlateauJeu p, int nbTours);

}
 