package iia.jeux.alg;

import java.util.Arrays;

import escampe.PlateauEscampe;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class AlphaBeta_marchepas implements AlgoJeu {
	/**
	 * La profondeur de recherche par défaut
	 */
	private final static int PROFMAXDEFAUT = 3;

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
	private int alpha;
	private int beta;
	/**
	 * Le joueur Min (l'adversaire)
	 */
	private Joueur joueurMin;

	/**
	 * Le joueur Max (celui dont l'algorithme de recherche adopte le point de
	 * vue)
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

	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public AlphaBeta_marchepas(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public AlphaBeta_marchepas(Heuristique h, Joueur joueurMax, Joueur joueurMin, int alpha, int beta) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT, alpha, beta);
	}

	public AlphaBeta_marchepas(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi, int alpha, int beta) {
		this.h = h;
	
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		profMax = profMaxi;
		System.out.println("Initialisation d'un AlphaBeta de profondeur " + profMax + " beta vaut = " + this.beta
				+ " joueurMax est : " + this.joueurMax);
	}

	
	
	
	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	public String meilleurCoup(PlateauJeu p, int profMax) {
		this.profMax = profMax;
		System.out.println("voici le joueurMax: "+joueurMax);
		System.out.println(p);
		this.nbfeuilles = 0;
		this.nbnoeuds = 0;
		String meilleur_coup = null;
		int meilleur_score = Integer.MIN_VALUE;
		String[] coupsPossibles = p.coupsPossibles(joueurMax);
		int[] scores = new int[100];
		System.out.println("Voici le lisereActuel: "+((PlateauEscampe)p).lisereActuel);
		for (int i = 0; i < coupsPossibles.length; i++) {
			//System.out.println(p);
			//System.out.println(coupsPossibles[i]);
			//PlateauJeu mlp = p.copy();
			//mlp.joue(joueurMax, coup);
			int score = this.alphabeta(p.copy(), 0, this.alpha, this.beta);
			scores[i] = score;
			if (meilleur_score <= score) {
				meilleur_score = score;
				meilleur_coup = coupsPossibles[i];
			}
		}
		System.out.println("coups possibles avec AlphaBeta: "+Arrays.toString(coupsPossibles));
		System.out.println("score avec AlphaBeta: "+Arrays.toString(scores));
		System.out.println("meilleur coup avec AlphaBeta: "+meilleur_coup);
		System.out.println("profondeur avec AlphaBeta: "+this.profMax);
		System.out.println("noeuds analysés : " + this.nbnoeuds + " ; feuilles analysées : "+ this.nbfeuilles);
		
		return meilleur_coup;
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "AlphaBeta(ProfMax=" + profMax + ")";
	}

	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------



	public int alphabeta(PlateauJeu p, int depth, int alpha, int beta) {
		return maxMin(p, depth, alpha, beta);
	}


	public int maxMin(PlateauJeu p, int depth, int alpha, int beta) {
		this.nbnoeuds++;
		if (depth >= this.profMax || p.finDePartie()) {
			this.nbfeuilles++;
			return h.eval(p, this.joueurMax, depth);
		} else {
			for (String kiddo : p.coupsPossibles(joueurMax)) {
				p.joue(joueurMax, kiddo);
				alpha = Math.max(alpha, minMax(p.copy(), depth + 1, alpha, beta));
				if (alpha >= beta) {
					return beta;
				}
			}
		}
		return alpha;
	}

	public int minMax(PlateauJeu p, int depth, int alpha, int beta) {
		this.nbnoeuds++;
		if (depth >= this.profMax || p.finDePartie()) {
			this.nbfeuilles++;
			return h.eval(p, this.joueurMin, depth);
		} else {
			for (String kiddo : p.coupsPossibles(joueurMax)) {
				p.joue(joueurMin, kiddo);
				beta = Math.min(beta, maxMin(p.copy(), depth + 1, alpha, beta));
				if (alpha >= beta) {
					return alpha;
				}
			}
		}
		return beta;
	}
}
