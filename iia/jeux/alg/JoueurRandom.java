/**
 * 
 */

package iia.jeux.alg;

import java.util.Random;

import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class JoueurRandom implements AlgoJeu {

	// -------------------------------------------
	// Attributs
	// ------------------------------------------

	/**
	 * Le joueur Max (celui dont l'algorithme de recherche adopte le point de vue)
	 */
	private Joueur joueurMax;

	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public JoueurRandom(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this.joueurMax = joueurMax;
	}

	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	public String meilleurCoup(PlateauJeu p, int profMax) {
		System.out.println(joueurMax);
		System.out.println(p);
		String[] coupsPossibles = p.coupsPossibles(joueurMax);
		Random r = new Random();
		if (coupsPossibles.length == 0) {
			return "E";
		}
		int rand = r.nextInt(coupsPossibles.length);
		for (int i = 0; i < coupsPossibles.length; i++) {
			if (i == rand) {
				return coupsPossibles[i];
			}
		}
		return "E";
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "Un random";
	}

}
