package modele;

public class Coup {
	int fromX;
	int fromY;
	int toX;
	int toY;
	
	public Coup(int fromX, int fromY, int toX, int toY) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}
	
	public Coup(String coup) {
		char[] chars = coup.toCharArray();
		this.fromX = chars[0]-64-1;
		this.fromY = Character.getNumericValue(chars[1]);
		this.toX = chars[3]-64-1;
		this.toY = Character.getNumericValue(chars[4]);
	}
	
	
	public String toString() {
		String coup = "";
		coup += (char)(this.fromX+64+1);
		coup += Integer.toString(this.fromY);
		coup += "-";
		coup += (char)(this.toX+64+1);
		coup += Integer.toString(this.toY);
		return coup;
	}
	
	public static void main(String a[]) {
		Coup c = new Coup(1,1,2,2);
		Coup c2 = new Coup("B1-C2");
		System.out.println(c);
		System.out.println(c2);
	}
}
