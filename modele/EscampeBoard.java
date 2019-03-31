package modele;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileReader;
import java.io.FileNotFoundException;


public class EscampeBoard {

    int lisereActuel;

    boolean joueurActuel; // true si noir, false si blanc

    Case[][] array;
    EscampeBoard board;



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
        for(int i = 0; i<6;i++) {
            for(int j =0; j<6;j++) {
                array[j][i].piece=null;
            }
        }

    }
    public EscampeBoard(EscampeBoard board) {
        this.board = board;
        array = new Case[6][6];
        array=board.array;

    }

    public boolean isValidMove(String move, boolean player) {
        Coup c = new Coup(move);
        if (array[c.fromX][c.fromY].getPiece() != null && array[c.fromX][c.fromY].getPiece().player==player) {
            if (array[c.toX][c.toY].getPiece() == null) {
                return true;
            }
        }
        return false;
    }
    public void setFromFile(String fileName) throws IOException {

        Path path = Paths.get(fileName);
        String texte = "";
        for(int i = 0; i<6;i++) {
            for(int j =0; j<6;j++) {
                if(board.array[i][j].piece!=null && board.array[i][j].piece.licorne && board.array[i][j].piece.player) {
                    texte = texte+"N";
                }
                if(board.array[i][j].piece!=null && board.array[i][j].piece.licorne && board.array[i][j].piece.player==false) {
                    texte = texte+"B";
                }
                if(board.array[i][j].piece!=null && board.array[i][j].piece.licorne==false && board.array[i][j].piece.player) {
                    texte = texte+"n";
                }
                if(board.array[i][j].piece!=null && board.array[i][j].piece.licorne==false && board.array[i][j].piece.player==false) {
                    texte = texte+"b";
                }
                if(board.array[i][j].piece==null) {
                    texte = texte+"-";
                }
                if(j==5) {
                    texte= texte+"\n";
                }

            }
        }

        Files.write(path, texte.getBytes());
    }
    
    public EscampeBoard InitGameFromFile() throws IOException{
        /*    Path path = Paths.get("/home/truffe/Eclipse_JAVA/Test.txt");
        List<String> lignes = Files.readAllLines(path);
        for(String ligne : lignes) {
            System.out.println(ligne);

        }
         */

        File file = new File("FichierDeSauvegarde.txt");
        FileReader fileReader = null;
        int numeroLigne = 0;
        int numeroColonne = 0;
        EscampeBoard b = new EscampeBoard();
        try {
            fileReader = new FileReader(file);


            char [] TableauDeSauvegarde = new char[36];

            fileReader.read(TableauDeSauvegarde);
            System.out.println("voici la sauvegarde : ");
            for(char c : TableauDeSauvegarde) {
                System.out.print(c);
            }
            for(char c : TableauDeSauvegarde) {
                System.out.println(" numero de la ligne : "+ numeroLigne+ " numero de la colonne "+ numeroColonne);
                if(c =='N') {
                    Piece p = new Piece(true,true,b);
                    b.array[numeroColonne][numeroLigne].mettrePiece(p);
                }
                if(c == 'n') {
                    Piece p = new Piece(true,false,b);
                    b.array[numeroColonne][numeroLigne].mettrePiece(p);
                }
                if(c =='B') {
                    Piece p = new Piece(false,true,b);
                    b.array[numeroColonne][numeroLigne].mettrePiece(p);
                }
                if(c =='b') {
                    Piece p = new Piece(false,false,b);
                    b.array[numeroColonne][numeroLigne].mettrePiece(p);
                }
                if(c == '-') {
                    b.array[numeroColonne][numeroLigne].piece=null;
                }
                numeroColonne = (numeroColonne+1)%6;
                if(numeroColonne==0) {
                    numeroLigne = numeroLigne+1;
                }


            }
            System.out.println(!b.array[0][0].piece.licorne);
        }catch(FileNotFoundException e) {
            System.err.printf("le fichier %s n'a pas ete trouve,", file.toString());
        }catch(IOException e) {
            System.err.println("impossible de lire le contenue "+ file.toString());
        }
        try {
            fileReader.close();
        } catch (IOException e) {
            System.err.println("impossible de fermer le fichier "+ file.toString());
        } catch(NullPointerException e) {
            System.err.print("impossible d'ouvrir le fichier");
        }
        return b;
    }

    public static void main(String[] args) throws IOException{
        EscampeBoard board = new EscampeBoard();
        EscampeBoard board2 = new EscampeBoard(board.InitGameFromFile());
        System.out.println(!board2.array[0][0].piece.licorne);
        board2.setFromFile("FichierStockage");
    }
}