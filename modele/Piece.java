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

		if (this.board.lisereActuel != -1 && caseMere.getLisere() != this.board.lisereActuel) {
			Coup[] returned = new Coup[1];
			returned[0] = null;
			return returned;
		}

		ArrayList<Coup> returned = new ArrayList<>();

		if (caseMere.getLisere() == 1) {
			for (Coup c1 : this.board.horizon1(x, y, player)) {
				if (c1 != null) {
					returned.add(new Coup(x, y, c1.toX, c1.toY));
				}
			}
		}

		if (caseMere.getLisere() == 2) {
			for (Coup c1 : this.board.horizon1(x, y, player)) {
				if (c1 != null) {
					for (Coup c2 : this.board.horizon1(c1.toX, c1.toY, player)) {
						if (c2 != null) {
							returned.add(new Coup(x, y, c2.toX, c2.toY));
						}
					}
				}
			}
		}

		if (caseMere.getLisere() == 3) {
			// System.err.println(1);
			for (Coup c1 : this.board.horizon1(x, y, player)) {
				if (c1 != null) {
					// System.err.println(c1);
					for (Coup c2 : this.board.horizon1(c1.toX, c1.toY, player)) {
						if (c2 != null) {
							// System.err.println("#"+c2.toString());
							for (Coup c3 : this.board.horizon1(c2.toX, c2.toY, player)) {
								if (c3 != null) {
									// System.err.println("##"+c2.toString());
									// System.err.println("well");
									returned.add(new Coup(x, y, c3.toX, c3.toY));
								}
							}
						}
					}
				}
			}
		}
		Coup[] arg0 = new Coup[returned.size()];
		return returned.toArray(arg0);
	}

	public String toString() {
		String texte = "";
		if (this.licorne) {
			texte += "l";
		}
		texte += this.player;
		texte += "(" + this.x + ":" + this.y + ")";
		return texte;
	}
}
