package escampe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import iia.espacesEtats.algorithmes.*;
import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Solution;
import iia.jeux.alg.*;
import iia.jeux.modele.*;
import iia.jeux.modele.joueur.*;

public class NoLoophole2 implements IJoueur {

	private int numJoueur;
	private int nbTours = 0;
	public String nomJoueur;
	private PlateauEscampe board;
	private ProblemeEscampeSimple probleme;
	private AlgoJeu algorithm;
	private Joueur joueur;

	@Override
	public void initJoueur(int mycolour) {
		this.numJoueur = mycolour;
		this.nomJoueur = (mycolour == 1) ? "noir" : "blanc";
		this.joueur = new Joueur(this.nomJoueur);
		this.board = new PlateauEscampe();
		this.algorithm = new Minimax(new HeuristiqueEscampe(), joueur,
				new Joueur((mycolour == 1) ? "blanc" : "noir"));
	}

	@Override
	public int getNumJoueur() {
		return numJoueur;
	}

	@Override
	public String choixMouvement() {
		// probleme = new ProblemeEscampeSimple(board, nomJoueur);
		System.out.println("test3");
		String coup = null;
		System.out.println(board.pieces);
		System.out.println(nbTours);
		nbTours++;
		if (board.pieces.isEmpty()) {
			if (board.hautLibre()) {
				coup = "A6/B5/C6/D5/E6/F5";
			} else {
				coup = "A1/B2/C1/D2/E1/F2";
			}
		} else if (board.pieces.size() == 6) {
			if (board.hautLibre()) {
				coup = "A6/B5/C6/D5/E6/F5";
			} else {
				coup = "A1/B2/C1/D2/E1/F2";
			}
		} else {
			if (board.gameOver()) {
				return "GameOver";
			}
			// Version graphe d'Etat
			// Solution solution = new
			// Solution();//algorithm.chercheSolution(this.probleme);
			// if (solution == null) {
			// return "E";
			// }
			// Iterator<Etat> itr = solution.descendingIterator();
			// System.out.println(solution);
			// System.out.println(((PlateauEscampe)solution.getLast()).dernierCoup);
			// System.out.println(((PlateauEscampe)solution.getFirst()).dernierCoup);
			//
			// if (itr.hasNext()) {
			// coup = ((PlateauEscampe) itr.next()).dernierCoup;
			// }
			// coup = ((PlateauEscampe) solution.get(0)).dernierCoup;

			// Version théorie des jeux
			// System.out.println(Arrays.toString(this.board.copy().coupsPossibles(this.joueur)));

			coup = this.algorithm.meilleurCoup(this.board.copy(), Math.min(2* nbTours, 50));
			// coup = this.algorithm.meilleurCoup(this.board.copy(), 50);
			if (coup == null) {
				this.board.lisereActuel = -1;
				return "E";
			}
			System.out.println(coup);
		}
		System.out.println(coup);
		this.board.joue(this.joueur, coup);
		return coup;
	}

	@Override
	public void declareLeVainqueur(int colour) {
		System.out.println("De toute manière, on aurait du gagner.");
		if (colour == this.numJoueur) {
			System.out.println("La victoire c'est simple. On nait avec.");
		}
	}

	@Override
	public void mouvementEnnemi(String coup) {
		System.out.println(coup);
		this.board.play(coup, (this.nomJoueur == "noir") ? "blanc" : "noir");
	}

	@Override
	public String binoName() {
		return "Baptiste et Matthias";
	}

}
