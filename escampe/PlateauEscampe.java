package escampe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.nio.file.Files;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import iia.espacesEtats.modeles.*;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class PlateauEscampe implements PlateauJeu, Etat{

	public int lisereActuel = -1;

	boolean joueurActuel; // true si noir, false si blanc
	String dernierCoup;
	boolean BlancGagne = false;
	boolean NoirGagne = false;
	private Timer timer = new Timer();
	public Case[][] array;
	public int secondsPassed = 0;
	public HashSet<Piece> pieces;
	boolean partieNull = false;
	private boolean TimerLance = false;
	
	public PlateauEscampe copy() {
		PlateauEscampe returned = new PlateauEscampe();

		for (int i = 0; i < this.array.length; i++) {
			for (int j = 0; j < this.array.length; j++) {
				returned.array[i][j] = this.array[i][j].copy(returned);
			}
		}
		returned.secondsPassed= this.secondsPassed;
		returned.lisereActuel = this.lisereActuel;
		returned.joueurActuel = this.joueurActuel;
		returned.dernierCoup = this.dernierCoup;
		returned.BlancGagne= this.BlancGagne;
		returned.NoirGagne= this.NoirGagne;
		returned.partieNull=this.partieNull;
		returned.BlancGagne = this.BlancGagne;
		returned.NoirGagne = this.NoirGagne;
		returned.TimerLance=this.TimerLance;
		returned.secondsPassed = this.secondsPassed;
		returned.timer = this.timer;

		returned.pieces = new HashSet<>();
		for (Piece piece : this.pieces) {
			returned.pieces.add(piece.copy());
		}

		returned.partieNull = this.partieNull;

		return returned;
	}

	public PlateauEscampe() {

		array = new Case[6][6];
		array[0][0] = new Case(1, this);
		array[1][0] = new Case(2, this);
		array[2][0] = new Case(2, this);
		array[3][0] = new Case(3, this);
		array[4][0] = new Case(1, this);
		array[5][0] = new Case(2, this);

		array[0][1] = new Case(3, this);
		array[1][1] = new Case(1, this);
		array[2][1] = new Case(3, this);
		array[3][1] = new Case(1, this);
		array[4][1] = new Case(3, this);
		array[5][1] = new Case(2, this);

		array[0][2] = new Case(2, this);
		array[1][2] = new Case(3, this);
		array[2][2] = new Case(1, this);
		array[3][2] = new Case(2, this);
		array[4][2] = new Case(1, this);
		array[5][2] = new Case(3, this);

		array[0][3] = new Case(2, this);
		array[1][3] = new Case(1, this);
		array[2][3] = new Case(3, this);
		array[3][3] = new Case(2, this);
		array[4][3] = new Case(3, this);
		array[5][3] = new Case(1, this);

		array[0][4] = new Case(1, this);
		array[1][4] = new Case(3, this);
		array[2][4] = new Case(1, this);
		array[3][4] = new Case(3, this);
		array[4][4] = new Case(1, this);
		array[5][4] = new Case(2, this);

		array[0][5] = new Case(3, this);
		array[1][5] = new Case(2, this);
		array[2][5] = new Case(2, this);
		array[3][5] = new Case(1, this);
		array[4][5] = new Case(3, this);
		array[5][5] = new Case(2, this);

		pieces = new HashSet<>();
	}

	public boolean isValidMove(String move, String player) {
		return isValidMoveBool(move, player == "noir");
	}

	private boolean isValidMoveBool(String move, boolean player) {
		Coup c = new Coup(move);
		if (array[c.fromX][c.fromY].getPiece() != null && array[c.fromX][c.fromY].getPiece().player == player) {
			if (array[c.toX][c.toY].getPiece() == null || c.estFatal(this)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Vérifie que le joueur du booléen peut passer par la case (x,y)
	 */
	public boolean traversable(int x, int y, boolean player, boolean fatal) {
		if ((x < 6 && x >= 0) && (y < 6 && y >= 0) && (array[x][y].getPiece() == null || (fatal && (this.array[x][y].getPiece()!= null && this.array[x][y].getPiece().licorne)))) {
			return true;
		}
		return false;
	}

	/*
	 * Retourne sous forme de coups les adresses des cases qui sont autour de la
	 * case x, y
	 */
	public Coup[] horizon1(int x, int y, boolean player, boolean fatal) {
		Coup[] returned = new Coup[4];
		if (traversable(x, y - 1, player, fatal)) {
			returned[3] = new Coup(x, y, x, y - 1);
		}
		if (traversable(x - 1, y, player, fatal)) {
			returned[2] = new Coup(x, y, x - 1, y);
		}
		if (traversable(x, y + 1, player, fatal)) {
			returned[1] = new Coup(x, y, x, y + 1);
		}
		if (traversable(x + 1, y, player, fatal)) {
			returned[0] = new Coup(x, y, x + 1, y);
		}
		return returned;
	}

	public String[] possibleMoves(String player) {
		boolean p = (player == "noir");
		ArrayList<String> listOfMoves = new ArrayList<>();

		for (Piece piece : pieces) {
			if (piece.player == p) {
				for (Coup c : piece.possibleMoves()) {
					try {
						if (this.isValidMove(c.toString(), player)) {
							listOfMoves.add(c.toString());
						}
					} catch (NullPointerException e) {
						// les coups nulls sont des coups impossibles.
					}
				}
			}
		}

		String[] returned = new String[listOfMoves.size()];
		listOfMoves.toArray(returned);
		return returned;
	}

	public void play(String move, String player) {
		if(this.secondsPassed==0 && TimerLance==false) {
			TimerLance=true;
			startTimer();
		}
		this.dernierCoup = move;
		if (move != "E") {
			if (move.length() == 5) {
				Coup c = new Coup(move);
				if (!this.array[c.fromX][c.fromY].piece.licorne
						&& this.array[c.toX][c.toY].getPiece() != null
						&& (player == "noir") != this.array[c.toX][c.toY].piece.player
						&& this.array[c.toX][c.toY].piece.licorne) {
					if (player == "noir") {
						this.NoirGagne = true;
					}
					if (player == "blanc") {
						this.BlancGagne = true;
					}
				}
				if (this.secondsPassed == 500) {
					this.partieNull = true;
				}
				this.array[c.fromX][c.fromY].bougerPiece(c);
				this.lisereActuel = this.array[c.toX][c.toY].lisere;
				this.joueurActuel = (player == "noir") ? false : true;
			} else {
				Coup.debutPartie(move, this, (player == "noir"));
			}
		}
	}

	public boolean mettrePiece(Piece p) {
		if (this.traversable(p.getX(), p.getY(), p.player, true)) {
			this.pieces.add(p);
			return array[p.getX()][p.getY()].mettrePiece(p);
		}
		return false;
	}

	public String toString() {
		String texte = "";
		for (int i = 0; i < 6; i++) {

			for (int j = 0; j < 6; j++) {
				if (this.array[j][i].getPiece() != null && this.array[j][i].getPiece().licorne
						&& this.array[j][i].getPiece().player) {
					texte = texte + "N";
				}
				if (this.array[j][i].getPiece() != null && this.array[j][i].getPiece().licorne
						&& !this.array[j][i].getPiece().player) {
					texte = texte + "B";
				}

				if (this.array[j][i].getPiece() != null && !this.array[j][i].getPiece().licorne
						&& this.array[j][i].getPiece().player) {
					texte = texte + "n";
				}
				if (this.array[j][i].getPiece() != null && !this.array[j][i].getPiece().licorne
						&& !this.array[j][i].getPiece().player) {
					texte = texte + "b";
				}
				if (this.array[j][i].getPiece() == null) {
					texte = texte + "-";
				}
				texte += this.array[j][i].getLisere();
				texte += " ";

			}
			texte = texte + "\n";
		}
		return texte;
	}

	//
	// Initialise un fichier de sauvegarde depuis un board
	//
	public void saveToFile(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		String texte = "%  ABCDEF" + "\n";
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (j == 0) {
					texte = texte + "0" + (i + 1) + " ";
				}
				if (this.array[j][i].getPiece() != null && this.array[j][i].getPiece().licorne
						&& this.array[j][i].getPiece().player) {
					texte = texte + "N";
				}
				if (this.array[j][i].getPiece() != null && this.array[j][i].getPiece().licorne
						&& !this.array[j][i].getPiece().player) {
					texte = texte + "B";
				}

				if (this.array[j][i].getPiece() != null && !this.array[j][i].getPiece().licorne
						&& this.array[j][i].getPiece().player) {
					texte = texte + "n";
				}
				if (this.array[j][i].getPiece() != null && !this.array[j][i].getPiece().licorne
						&& !this.array[j][i].getPiece().player) {
					texte = texte + "b";
				}
				if (this.array[j][i].getPiece() == null) {
					texte = texte + "-";
				}
				if (j == 5) {
					texte = texte + " 0" + (i + 1);
				}
			}
			texte = texte + "\n";
		}
		texte = texte + "%  ABCDEF";
		Files.write(path, texte.getBytes());
	}

	//
	// Initialise un plateau depuis un fichier sauvegarde
	//
	public void setFromFile(String fileName) throws IOException {

		File file = new File(fileName);
		int numeroLigne = 0;
		int numeroColonne = 0;
		ArrayList<Character> tableauDeSauvegarde = new ArrayList<Character>();

		try {
			List<String> lignes = Files.readAllLines(Paths.get(fileName));
			for (String ligne : lignes) {
				if (ligne.charAt(0) != '%') {
					for (int i = 0; i < ligne.length(); i++) {
						tableauDeSauvegarde.add(ligne.charAt(i));
					}
				}
			}
			System.out.println(tableauDeSauvegarde);
			for (char c : tableauDeSauvegarde) {
				if (c == 'N') {
					System.out
							.println(" numero de la ligne : " + numeroLigne + " numero de la colonne " + numeroColonne);

					Piece p = new Piece(true, true, this);
					this.array[numeroColonne][numeroLigne].mettrePiece(p);
					numeroColonne = (numeroColonne + 1) % 6;
					if (numeroColonne == 0) {
						numeroLigne = numeroLigne + 1;
					}
				}
				if (c == 'n') {
					Piece p = new Piece(true, false, this);
					this.array[numeroColonne][numeroLigne].mettrePiece(p);
					numeroColonne = (numeroColonne + 1) % 6;
					if (numeroColonne == 0) {
						numeroLigne = numeroLigne + 1;
					}
				}
				if (c == 'B') {
					Piece p = new Piece(false, true, this);
					this.array[numeroColonne][numeroLigne].mettrePiece(p);
					numeroColonne = (numeroColonne + 1) % 6;
					if (numeroColonne == 0) {
						numeroLigne = numeroLigne + 1;
					}
				}
				if (c == 'b') {
					Piece p = new Piece(false, false, this);
					this.array[numeroColonne][numeroLigne].mettrePiece(p);
					numeroColonne = (numeroColonne + 1) % 6;
					if (numeroColonne == 0) {
						numeroLigne = numeroLigne + 1;
					}
				}
				if (c == '-') {
					System.out
							.println(" numero de la ligne : " + numeroLigne + " numero de la colonne " + numeroColonne);

					this.array[numeroColonne][numeroLigne].piece = null;
					numeroColonne = (numeroColonne + 1) % 6;
					if (numeroColonne == 0) {
						numeroLigne = numeroLigne + 1;
					}

				}

			}
		} catch (IOException e) {
			System.err.println("impossible d'ouvrir le fichier " + file.toString());
		}
	}

	TimerTask task = new TimerTask() {
		public void run() {
			secondsPassed++;
			System.out.println("Seconds passed: " + secondsPassed);
		}
	};

	public void startTimer() {
		this.timer.scheduleAtFixedRate(task, 1000, 1000);
	}

	public boolean gameOver() {
		if (this.partieNull) {
			System.out.println("La partie est null");
			return true;
		}
		if (this.BlancGagne) {
			System.out.println("La partie est gagné par le blanc");
			return true;
		}
		if (this.NoirGagne) {
			System.out.println("La partie est gagné par le noir");
			return true;
		}
		return false;
	}
	/*
	 * public static void main(String[] args) throws IOException {
	 * 
	 * EtatEscampe b = new EtatEscampe(); b.startTimer();
	 * 
	 * b.setFromFile("Test"); b.saveToFile("test.txt");
	 * 
	 * }
	 */

	@Override
	public String[] coupsPossibles(Joueur j) {
		return this.possibleMoves(j.toString());
	}

	@Override
	public void joue(Joueur j, String c) {
		this.play(c, j.toString());
	}

	@Override
	public boolean finDePartie() {
		return this.gameOver();
	}

	@Override
	public boolean coupValide(Joueur j, CoupJeu c) {
		Coup coupEscampe = (Coup)c;
		return this.isValidMove(coupEscampe.toString(), j.toString());
	}

	@Override
	public void printPoints() {
		System.out.println("tour");
	}

}
