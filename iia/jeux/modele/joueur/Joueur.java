package iia.jeux.modele.joueur;

public class Joueur // A
{

	private String nom;
	public boolean bool;

	public Joueur(String s) {
		setNom(s);
		this.bool = (s == "noir");
	}

	public String toString() {
		return getNom();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
