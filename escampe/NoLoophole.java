package escampe;

import iia.jeux.alg.*;
import iia.jeux.modele.joueur.*;

public class NoLoophole implements IJoueur {

	private int numJoueur;
	private int nbTours = 0;
	public String nomJoueur;
	private PlateauEscampe board;
	private AlgoJeu algorithm;
	private Joueur joueur;

	@Override
	public void initJoueur(int mycolour) {
		this.numJoueur = mycolour;
		this.nomJoueur = (mycolour == 1) ? "noir" : "blanc";
		this.joueur = new Joueur(this.nomJoueur);
		this.board = new PlateauEscampe();
		this.algorithm = new AlphaBeta(new HeuristiqueEscampe(), joueur,
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
			System.out.println("premiers à jouer");
			coup = "A1/B2/C1/D2/E1/F2";
		} else if (board.pieces.size() == 6) {
			for (Piece p : board.pieces) {
				if (p.getX() >= 1) {
					coup = "A6/B5/C6/D5/E6/F5";
				} else {
					coup = "A1/B2/C1/D2/E1/F2";
				}
			}
		} else {
			if (board.gameOver()) {
				return "GameOver";
			}

			coup = this.algorithm.meilleurCoup(this.board.copy(), Math.min(2* nbTours, 50));
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