package iia.jeux.modele.joueur;

public class Joueur // A
{

	private String nom;
	public boolean bool;

	public Joueur(String s) {
		nom = s;
		this.bool = (s == "noir");
	}

	public String toString() {
		return nom;
	}
}
