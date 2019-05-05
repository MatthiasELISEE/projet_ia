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
	public String meilleurCoup(PlateauJeu p,int profMax) {
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
			int score = this.minimax(p.copy(), coupsPossibles[i], 0, true);
			scores[i] = score;
			if (meilleur_score <= score) {
				meilleur_score = score;
				meilleur_coup = coupsPossibles[i];
			}
		}
		System.out.println("Voici les coups possibles avec Minimax: "+Arrays.toString(coupsPossibles));
		System.out.println("Voici le score avec Minimax: "+Arrays.toString(scores));
		System.out.println("Voici le meilleur coup avec Minimax: "+meilleur_coup);
		System.out.println("Voici la profondeur avec Minimax: "+this.profMax);
		System.out.println("noeuds analysés : " + this.nbnoeuds + " ; feuilles analysées : "+ this.nbfeuilles);
		
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

	public int minimax(PlateauJeu p, String coup, int depth, boolean player) {
		this.nbnoeuds++;
		int value;
		//System.out.println(depth >= this.profMax );
		//System.out.println(p);
		if (this.nbnoeuds % 10000 == 0) {
			System.out.print("#");
		}
		if (depth >= this.profMax || this.terminal(p)) {
			this.nbfeuilles++;
			return h.eval(p.copy(), this.joueurMax) ;
		};
		if (player) {
			value = Integer.MIN_VALUE;
			
			p.joue(joueurMax, coup);
			for (int i = 0; i < p.coupsPossibles(joueurMin).length; i++) {
				value = Math.max(value, this.minimax(p.copy(),  p.coupsPossibles(joueurMin)[i], depth + 1, false));
			}
		} else {
			value = Integer.MAX_VALUE;
			
			p.joue(joueurMin, coup);
			for (int i = 0; i < p.coupsPossibles(joueurMax).length; i++) {
				value = Math.min(value, this.minimax(p.copy(),  p.coupsPossibles(joueurMax)[i], depth + 1, true));
			}
		}
		return value;
	}

}
