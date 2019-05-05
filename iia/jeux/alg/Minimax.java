/**
 * 
 */

package iia.jeux.alg;

import java.util.ArrayList;
import java.util.Arrays;

import escampe.HeuristiqueEscampe;
import escampe.PlateauEscampe;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class Minimax implements AlgoJeu {

	/**
	 * La profondeur de recherche par défaut
	 */
	private final static int PROFMAXDEFAUT = 50;

	// -------------------------------------------
	// Attributs
	// -------------------------------------------

	/**
	 * La profondeur de recherche utilisée pour l'algorithme
	 */
	private int profMax = PROFMAXDEFAUT;

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

	/**
	 * Le nombre de noeuds développé par l'algorithme (intéressant pour se faire
	 * une idée du nombre de noeuds développés)
	 */
	private int nbnoeuds;

	/**
	 * Le nombre de feuilles évaluées par l'algorithme
	 */
	private int nbfeuilles;

	private String coup;

	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		profMax = profMaxi;
		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
	}

	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	public String meilleurCoup(PlateauJeu p, int profMax) {
		this.profMax = profMax;
		System.out.println("voici le joueurMax: " + joueurMax);
		System.out.println(p);
		this.nbnoeuds = 0;
		this.nbfeuilles = 0;
		String meilleur_coup = null;
		int meilleur_score = Integer.MIN_VALUE;
		String[] coupsPossibles = p.coupsPossibles(joueurMax);
		int[] scores = new int[100];
		System.out.println("Voici le lisereActuel: " + ((PlateauEscampe) p).lisereActuel);
		System.out.println("Profondeur Maximale : " + profMax);

		for (int i = 0; i < coupsPossibles.length; i++) {
			// System.out.println(p);
			// System.out.println(coupsPossibles[i]);
			// PlateauJeu mlp = p.copy();
			// mlp.joue(joueurMax, coup);
			this.coup = coupsPossibles[i];
			PlateauJeu p2 = p.copy();
			p2.joue(joueurMax, coupsPossibles[i]);
			System.out.println(coupsPossibles[i]);
			int score = this.minimax(p2, 0, false);
			scores[i] = score;
			if (meilleur_score <= score) {
				meilleur_score = score;
				meilleur_coup = coupsPossibles[i];
			}
		}
		System.out.println("Voici les coups possibles avec Minimax: " + Arrays.toString(coupsPossibles));
		System.out.println("Voici le score avec Minimax: " + Arrays.toString(scores));
		System.out.println("Voici le meilleur coup avec Minimax: " + meilleur_coup);
		System.out.println("Voici la profondeur avec Minimax: " + this.profMax);
		System.out.println("noeuds analysés : " + this.nbnoeuds + " ; feuilles analysées : " + this.nbfeuilles);

		return meilleur_coup;
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "MiniMax(ProfMax=" + profMax + ")";
	}

	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------

	// A vous de jouer pour implanter Minimax

	public boolean terminal(PlateauJeu p) {
		return p.finDePartie();
	}

	public int minimax(PlateauJeu p, int depth, boolean player) {
		this.nbnoeuds++;
		Integer value = null;
		// System.out.println(depth >= this.profMax );
		// System.out.println(p);
		if (this.nbnoeuds % 10000 == 0) {
			System.out.print("#");
		}
		if (depth >= this.profMax || p.finDePartie()) {
			this.nbfeuilles++;
//			System.out.println("~~");
			return h.eval(p.copy(), this.joueurMax, depth);
		}

		if (player) {
			for (int i = 0; i < p.coupsPossibles(joueurMax).length; i++) {
				PlateauJeu p2 = p.copy();
				p2.joue(joueurMax, p.coupsPossibles(joueurMax)[i]);
//				for (int d = 0; d < depth; d++) {
//					System.out.print(" ");
//				}
//				System.out.print(p.coupsPossibles(joueurMax)[i]);
//				System.out.println();
				
				int minimax_value = this.minimax(p2, depth + 1, false);
				if (value == null || value < minimax_value) {
					value = minimax_value;
				}
			}
		} else {
			for (int i = 0; i < p.coupsPossibles(joueurMin).length; i++) {
				PlateauJeu p2 = p.copy();
				p2.joue(joueurMin, p.coupsPossibles(joueurMin)[i]);
//				for (int d = 0; d < depth; d++) {
//					System.out.print(" ");
//				}
				int minimax_value = this.minimax(p2, depth + 1, false);
				if (value == null || value > minimax_value) {
					value = minimax_value;
				}
			}
		}
		if (value == null) {
			return this.minimax(p, depth + 1, !player);
		}
		return value;
	}

}
