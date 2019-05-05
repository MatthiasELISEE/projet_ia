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

public class AlphaBeta implements AlgoJeu {

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
	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		profMax = profMaxi;
		System.out.println("Initialisation d'un AlphaBeta2 de profondeur " + profMax);
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
			int score = this.minimax(p2, 0, false, Integer.MAX_VALUE, Integer.MIN_VALUE);
			scores[i] = score;
			if (meilleur_score <= score) {
				meilleur_score = score;
				meilleur_coup = coupsPossibles[i];
			}
		}
		System.out.println("Voici les coups possibles avec AlphaBeta2: " + Arrays.toString(coupsPossibles));
		System.out.println("Voici le score avec AlphaBeta2: " + Arrays.toString(scores));
		System.out.println("Voici le meilleur coup avec AlphaBeta2: " + meilleur_coup);
		System.out.println("Voici la profondeur avec AlphaBeta2: " + this.profMax);
		System.out.println("noeuds analysés : " + this.nbnoeuds + " ; feuilles analysées : " + this.nbfeuilles);

		return meilleur_coup;
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "AlphaBeta2(ProfMax=" + profMax + ")";
	}

	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------

	// A vous de jouer pour implanter AlphaBeta2

	public boolean terminal(PlateauJeu p) {
		return p.finDePartie();
	}

	public int minimax(PlateauJeu p, int depth, boolean player, int alpha, int beta) {
		this.nbnoeuds++;
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

		Integer minimax_value = null;
		if (player) {
			for (int i = 0; i < p.coupsPossibles(joueurMax).length; i++) {
				PlateauJeu p2 = p.copy();
				p2.joue(joueurMax, p.coupsPossibles(joueurMax)[i]);
				
				minimax_value = this.minimax(p2, depth + 1, false, alpha, beta);
				if (alpha > minimax_value) {
					alpha = minimax_value;
				}
				if (alpha >= beta) {
					System.out.println("/b");
					return beta;
				}
			}
		} else {
			for (int i = 0; i < p.coupsPossibles(joueurMin).length; i++) {
				PlateauJeu p2 = p.copy();
				p2.joue(joueurMin, p.coupsPossibles(joueurMin)[i]);
				
				minimax_value = this.minimax(p2, depth + 1, true, alpha, beta);
				if (beta < minimax_value) {
					beta = minimax_value;
				}
				if (beta <= alpha) {
					System.out.println("/a");
					return alpha;
				}
			}
		}
		if (minimax_value == null) {
			return this.minimax(p, depth + 1, !player, alpha, beta);
		}
		return minimax_value;
	}

}