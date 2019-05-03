/**
 * 
 */

package iia.jeux.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import escampe.PlateauEscampe;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class Ryanndom implements AlgoJeu {

	// -------------------------------------------
	// Attributs
	// -------------------------------------------

	/**
	 * L'heuristique utilisée par l'algorithme
	 */
	private Heuristique h;

	/**
	 * Le joueur Min (l'adversaire)
	 */
	private Joueur joueurMin;

	/**
	 * Le joueur Max (celui dont l'algorithme de recherche adopte le point de vue)
	 */
	private Joueur joueurMax;

	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public Ryanndom(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this.h = h;
		this.joueurMin = joueurMin;
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
