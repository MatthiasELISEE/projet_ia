package escampe;

import iia.espacesEtats.modeles.*;
import iia.jeux.alg.*;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class HeuristiqueEscampe implements iia.jeux.alg.Heuristique {

	/*
	 * Theorie des jeux
	 * 
	 * @Override public int eval(PlateauJeu p, Joueur j) { if (p.finDePartie()) {
	 * PlateauEscampe p2 = (PlateauEscampe) p; if ((p2.NoirGagne && j.NOIR) ||
	 * (p2.BlancGagne && !j.NOIR)) { return Integer.MAX_VALUE; } else if
	 * ((p2.NoirGagne && !j.NOIR) || (p2.BlancGagne && j.NOIR)) { return
	 * Integer.MIN_VALUE; } else { return 0; } } else {
	 * 
	 * }
	 * 
	 * return 0; } }
	 */

	@Override
	public int eval(PlateauJeu p, Joueur j) {
		int points = 0;
		PlateauEscampe p2 = (PlateauEscampe) p;
		if (p2.BlancGagne && !j.bool) {
			return Integer.MAX_VALUE;
		}
		if (p2.NoirGagne && j.bool) {
			return Integer.MIN_VALUE;
		}
		if (j.bool) {

			// si je suis mis en echec par une pièce
			if (!j.bool) {
				for (Piece piece : p2.pieces) {
					if (piece.player == false) {
						for (Coup coup : piece.possibleMoves()) {
							if (coup != null && piece.possibleMoves().length != 0 && coup.toX == piece.x
									&& coup.toY == piece.y && piece.licorne) {
								return Integer.MIN_VALUE + 10;
							}
						}
					}
				}
			}
			// si je met la licorne adverse en echec
			if (!j.bool) {
				for (Piece piece : p2.pieces) {
					if (piece.player == true) {
						for (Coup coup : piece.possibleMoves()) {
							if (coup != null && piece.possibleMoves().length != 0 && coup.toX == piece.x
									&& coup.toY == piece.y && piece.licorne) {
								points = points + 60;
							}
						}
					}
				}
			} else {
				if (j.bool) {
					// chaque coup possible rapporte des points
					for (Piece piece : p2.pieces) {
						if (j.bool && piece.licorne == false && piece.player == true) {
							for (Coup coup : piece.possibleMoves()) {
								points = points + 10;
							}
						}
					}
					// si je controle les cases du milieu
					for (int i = 0; i < 6; i++) {
						for (int v = 0; v < 6; v++) {
							if (p2.array[2][2].getPiece() != null && p2.array[2][2].getPiece().licorne
									|| p2.array[2][3].getPiece() != null && !p2.array[2][3].getPiece().licorne
									|| p2.array[3][2].getPiece() != null && !p2.array[3][2].getPiece().licorne
									|| p2.array[3][3].getPiece() != null && !p2.array[3][3].getPiece().licorne) {
								points = points + 30;
							}
						}
					}
				}
			}
			return points;
		} else {
			// si je suis mis en echec par une pièce
			if (!j.bool) {
				for (Piece piece : p2.pieces) {
					if (piece.player == true) {
						for (Coup coup : piece.possibleMoves()) {
							if (coup != null && piece.possibleMoves().length != 0 && coup.toX == piece.x
									&& coup.toY == piece.y && piece.licorne) {
								return Integer.MIN_VALUE + 10;
							}
						}
					}
				}
			}
			// si je met la licorne adverse en echec
			if (!j.bool) {
				for (Piece piece : p2.pieces) {
					if (piece.player == false) {
						for (Coup coup : piece.possibleMoves()) {
							if (coup != null && piece.possibleMoves().length != 0 && coup.toX == piece.x
									&& coup.toY == piece.y && piece.licorne) {
								points = points + 60;
							}
						}
					}
				}
			} else {
				if (!j.bool) {
					// chaque coup possible rapporte des points
					for (Piece piece : p2.pieces) {
						if (!j.bool && piece.licorne == false && piece.player == false) {
							for (Coup coup : piece.possibleMoves()) {
								points = points + 10;
							}
						}
					}

					// si je controle les cases du milieu
					for (int i = 0; i < 6; i++) {
						for (int v = 0; v < 6; v++) {

							if (p2.array[v][i].getPiece() != null) {
								if (p2.array[2][2].getPiece() != null && p2.array[2][2].getPiece().licorne
										|| p2.array[2][3].getPiece() != null && !p2.array[2][3].getPiece().licorne
										|| p2.array[3][2].getPiece() != null && !p2.array[3][2].getPiece().licorne
										|| p2.array[3][3].getPiece() != null && !p2.array[3][3].getPiece().licorne) {
									points = points + 30;
								}

							}
						}
					}

				}

			}
			return points;
		}
	}



}
