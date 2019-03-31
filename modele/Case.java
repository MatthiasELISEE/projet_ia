package modele;

public class Case {
	final int lisere;
	Piece piece;
	
	public Case(int lisere) throws IllegalArgumentException{
		this.lisere = lisere;
		if (lisere < 1 || lisere > 3) {
			throw new IllegalArgumentException("lisere trop grand : "+lisere);
		}
		piece = null;
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
}
