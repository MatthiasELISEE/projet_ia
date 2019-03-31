package modele;

import java.util.ArrayList;

public class Piece {
	final boolean player;
	int x;
	int y;

	final boolean licorne;

	EscampeBoard board;

	public Piece(boolean player, boolean licorne, EscampeBoard board) {
		this.player = player;
		this.board = board;
		this.licorne = licorne;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public Coup[] possibleMoves() {
		Case caseMere = this.board.array[x][y];

		ArrayList<Coup> returned = new ArrayList<>();

		if (caseMere.lisere == 1) {
			for (Coup c1 : this.board.horizon1(x, y, player)) {
				try {
					if (this.board.lisereActuel!=-1 && this.board.array[c1.toX][c1.toY].getLisere() == this.board.lisereActuel) {
						returned.add(new Coup(x, y, c1.toX, c1.toY));
					}
				} catch (NullPointerException e) {
					// les coups nulls sont des coups impossibles.
				}
			}
		}

		if (caseMere.lisere == 2) {
			for (Coup c1 : this.board.horizon1(x, y, player)) {
				try {
					for (Coup c2 : this.board.horizon1(c1.toX, c1.toY, player)) {
						if (this.board.lisereActuel!=-1 && this.board.array[c2.toX][c2.toY].getLisere() == this.board.lisereActuel) {
							returned.add(new Coup(x, y, c2.toX, c2.toY));
						}
					}
				} catch (NullPointerException e) {
					// les coups nulls sont des coups impossibles.
				}
			}
		}

		if (caseMere.lisere == 3) {
			for (Coup c1 : this.board.horizon1(x, y, player)) {
				try {
					for (Coup c2 : this.board.horizon1(c1.toX, c1.toY, player)) {
						for (Coup c3 : this.board.horizon1(c2.toX, c2.toY, player)) {
							if (this.board.lisereActuel!=-1 && this.board.array[c3.toX][c3.toY].getLisere() == this.board.lisereActuel) {
								returned.add(new Coup(x, y, c3.toX, c3.toY));
							}
						}
					}
				} catch (NullPointerException e) {
					// les coups nulls sont des coups impossibles.
				}
			}
		}

		System.out.println(returned);

		Coup[] arg0 = new Coup[returned.size()];
		return returned.toArray(arg0);
	}
}
