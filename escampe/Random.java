package escampe;

import iia.jeux.alg.*;
import iia.jeux.modele.joueur.*;

public class Random implements IJoueur {

	private int numJoueur;
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
		this.algorithm = new JoueurRandom(new HeuristiqueEscampe(), joueur, new Joueur((mycolour == 1) ? "blanc" : "noir"));
	}

	@Override
	public int getNumJoueur() {
		return numJoueur;
	}

	@Override
	public String choixMouvement() {
		//probleme = new ProblemeEscampeSimple(board, nomJoueur);
		String coup = null;
		System.out.println("Voici les pièces: " + board.pieces);
		System.out.println("Voici le nombre de pièce: " + board.pieces.size());

		if (board.pieces.isEmpty()) {
			coup = "A6/B5/C6/D5/E6/F5";
		} else if (board.pieces.size() == 6) {
			coup = "A1/B2/C1/D2/E1/F2";
		} else {
			// Version graphe d'Etat
			//			Solution solution = new Solution();//algorithm.chercheSolution(this.probleme);
			//			if (solution == null) {
			//				return "E";
			//			}
			//			Iterator<Etat> itr = solution.descendingIterator();
			//			System.out.println(solution);
			//			System.out.println(((PlateauEscampe)solution.getLast()).dernierCoup);
			//			System.out.println(((PlateauEscampe)solution.getFirst()).dernierCoup);
			//			
			//			if (itr.hasNext()) {
			//				coup = ((PlateauEscampe) itr.next()).dernierCoup;
			//			}
			//			coup = ((PlateauEscampe) solution.get(0)).dernierCoup;

			// Version théorie des jeux
			coup = this.algorithm.meilleurCoup(this.board.copy(), 0);
			if (coup == null) {
				this.board.lisereActuel = -1;
				return "E";
			}
			System.out.println("Le coup est null "+coup);
		}
	
			this.board.joue(this.joueur, coup);
			if(this.board.gameOver()) {
				coup = "GameOver";
				System.out.println("Voici le coup: " +coup);
				return coup;
			}
			System.out.println("Voici le coup: " +coup);
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
		this.board.play(coup, (this.nomJoueur == "noir") ? "blanc" : "noir");
	}

	@Override
	public String binoName() {
		return "Classe Random";
	}
}
