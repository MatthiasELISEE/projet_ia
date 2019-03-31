package modele;

public class EscampeBoard {
	
	int lisereActuel;
	
	boolean joueurActuel; // true si noir, false si blanc
	
	Case[][] array;  
	
	
	
	public EscampeBoard() {
		
		array = new Case[6][6];
		array[0][0] = new Case(1);
		array[1][0] = new Case(2);
		array[2][0] = new Case(2);
		array[3][0] = new Case(3);
		array[4][0] = new Case(1);
		array[5][0] = new Case(2);
		
		array[0][1] = new Case(3);
		array[1][1] = new Case(1);
		array[2][1] = new Case(3);
		array[3][1] = new Case(1);
		array[4][1] = new Case(3);
		array[5][1] = new Case(2);

		array[0][2] = new Case(2);
		array[1][2] = new Case(3);
		array[2][2] = new Case(1);
		array[3][2] = new Case(2);
		array[4][2] = new Case(1);
		array[5][2] = new Case(3);
		
		array[0][3] = new Case(2);
		array[1][3] = new Case(1);
		array[2][3] = new Case(3);
		array[3][3] = new Case(2);
		array[4][3] = new Case(3);
		array[5][3] = new Case(1);
		
		array[0][4] = new Case(1);
		array[1][4] = new Case(3);
		array[2][4] = new Case(1);
		array[3][4] = new Case(3);
		array[4][4] = new Case(1);
		array[5][4] = new Case(2);
		
		array[0][5] = new Case(3);
		array[1][5] = new Case(2);
		array[2][5] = new Case(2);
		array[3][5] = new Case(1);
		array[4][5] = new Case(3);
		array[5][5] = new Case(2);
		
		array[1][0] = new Case(2);
		array[2][0] = new Case(2);
		array[3][0] = new Case(1);
		array[4][0] = new Case(2);
		array[5][0] = new Case(2);	
	}
	
	public boolean isValidMove(String move, String player) {
		Coup c = new Coup(move);
		if (array[c.fromX][c.fromY].getPiece() != null && array[c.fromX][c.fromY].getPiece().player==player) {
			if (array[c.toX][c.toY].getPiece() == null) {
				return true;
			}
		}
		return false;
	}
	
	
}
