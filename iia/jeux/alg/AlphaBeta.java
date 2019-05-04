package iia.jeux.alg;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class AlphaBeta implements AlgoJeu {
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
	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, Integer.MIN_VALUE + 1, Integer.MAX_VALUE, PROFMAXDEFAUT);
	}

	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int alpha, int beta) {
		this(h, joueurMax, joueurMin, alpha, beta, PROFMAXDEFAUT);
	}

	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi, int alpha, int beta) {
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
		this.nbfeuilles = 0;
		this.nbnoeuds = 0;
		this.profMax = profMax;
		String meilleur_coup = null;
		int meilleur_score = Integer.MIN_VALUE;
		for (String coup : p.coupsPossibles(joueurMax)) {
			PlateauJeu mlp = p.copy();
			mlp.joue(joueurMax, coup);
			int score = alphabeta(mlp, this.profMax, this.alpha, this.beta);
			if (meilleur_score <= score) {
				meilleur_score = score;
				meilleur_coup = coup;
			}
		}
		
		System.out.println("voici la profondeur de AlphaBeta: "+this.profMax);
		System.out.println("noeuds analysés : " + this.nbnoeuds + " ; feuilles analysées : " + this.nbfeuilles);
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
		if (depth <= 0 || p.finDePartie()) {
			this.nbfeuilles++;
			return h.eval(p, this.joueurMax);
		} else {
			for (String kiddo : p.coupsPossibles(joueurMax)) {
				PlateauJeu mlp = p.copy();
				mlp.joue(joueurMax, kiddo);
				alpha = Math.max(alpha, minMax(mlp, depth - 1, alpha, beta));
				if (alpha >= beta) {
					return beta;
				}
			}
		}
		return alpha;
	}

	public int minMax(PlateauJeu p, int depth, int alpha, int beta) {
		this.nbnoeuds++;
		if (depth <= 0 || p.finDePartie()) {
			this.nbfeuilles++;
			return h.eval(p, this.joueurMin);
		} else {
			for (String kiddo : p.coupsPossibles(joueurMax)) {
				PlateauJeu mlp = p.copy();
				mlp.joue(joueurMin, kiddo);
				beta = Math.min(beta, maxMin(mlp, depth - 1, alpha, beta));
				if (alpha >= beta) {
					return alpha;
				}
			}
		}
		return beta;
	}
}
