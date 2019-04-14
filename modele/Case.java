package modele;

public class Case {
	final int lisere;
	Piece piece;
	private EscampeBoard board;

	public Case(int lisere, EscampeBoard board) throws IllegalArgumentException {
		this.lisere = lisere;
		if (lisere < 1 || lisere > 3) {
			throw new IllegalArgumentException("lisere trop grand : " + lisere);
		}
		piece = null;
		this.board = board;
	}

	public int getLisere() {
		return this.lisere;
	}

	public Piece getPiece() {
		return piece;
	}

	boolean retirerPiece() {
		if (piece != null) {
			piece = null;
			return true;
		}
		return false;
	}

	boolean mettrePiece(Piece p) {
		if (piece != null) {
			return false;
		}
		piece = p;
		return true;
	}

	// Attention ! Cette fonction ne vérifie pas si le coup est faisable. Par contre
	// elle fait le coup seulement sur sa propre pièce.
	boolean bougerPiece(Coup c) {
		if (c.fromX == this.piece.x && c.fromY == this.piece.y) {
			this.piece.x = c.toX;
			this.piece.y = c.toY;
			return this.board.array[c.toX][c.toY].mettrePiece(piece) && this.retirerPiece();
		}
		return false;
	}

	public String toString() {
		return this.lisere + ":" + this.piece.toString();
	}
}
