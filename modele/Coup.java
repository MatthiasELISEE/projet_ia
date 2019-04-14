package modele;

public class Coup {
	int fromX;
	int fromY;
	int toX;
	int toY;

	Case[] positions;

	public Coup(int fromX, int fromY, int toX, int toY) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}

	public Coup(String coup) {
		char[] chars = coup.toCharArray();

		this.fromX = chars[0] - 64 - 1;
		this.fromY = Character.getNumericValue(chars[1])-1;
		this.toX = chars[3] - 64 - 1;
		this.toY = Character.getNumericValue(chars[4])-1;
	}
	
	public static void debutPartie(String positions, EscampeBoard board, boolean player) {		
		String[] places = positions.split("/");
		char[] chars;
		
		chars = places[0].toCharArray();
		
		Piece licorne = new Piece(player, true, board);
		licorne.setX(chars[0]-64-1);
		licorne.setY(Character.getNumericValue(chars[1])-1);
		board.mettrePiece(licorne);
		
		for (int i = 1; i < 6; i++) {
			chars = places[i].toCharArray();
			Piece paladin = new Piece(player, false, board);
			paladin.setX(chars[0]-64-1);
			paladin.setY(Character.getNumericValue(chars[1])-1);
			board.mettrePiece(paladin);
		}
	}

	public String toString() {
		String coup = "";
		coup += (char) (this.fromX + 64 + 1);
		coup += Integer.toString(this.fromY+1);
		coup += "-";
		coup += (char) (this.toX + 64 + 1);
		coup += Integer.toString(this.toY+1);
		return coup;
	}

	public static void main(String a[]) {
		Coup c = new Coup(1, 1, 2, 2);
		Coup c2 = new Coup("B2-C3");
		System.out.println(c);
		System.out.println(c2);
	}
}
