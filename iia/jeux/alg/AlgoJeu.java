package  iia.jeux.alg;

import iia.jeux.modele.PlateauJeu;

public interface AlgoJeu {

    /** Renvoie le meilleur
     * @param p
     * @param profMax 
     * @return
     */
	public String meilleurCoup(PlateauJeu p, int profMax);

}
 