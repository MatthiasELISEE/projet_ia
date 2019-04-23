package escampe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import iia.espacesEtats.modeles.*;

public class ProblemeEscampeSimple extends ProblemeAvecVictoire {

	public ProblemeEscampeSimple(Etat eInit, String nom) {
		super(eInit, nom);
	}

	@Override
	public boolean isGagne(Etat e) {
		PlateauEscampe board = (PlateauEscampe) e;
		if (((super.getNom().equals("noir")) && board.NoirGagne)
				|| ((super.getNom().equals("blanc")) && board.BlancGagne)) {
			System.err.println(board.toString());
			System.err.println(board.dernierCoup);
			System.err.println("goooooood");
		}
		return ((super.getNom().equals("noir")) && board.NoirGagne)
				|| ((super.getNom().equals("blanc")) && board.BlancGagne);
	}

	@Override
	public Collection<Etat> successeurs(Etat e) {
		PlateauEscampe board = (PlateauEscampe) e;
		String nomJoueurActuel = board.joueurActuel ? "noir" : "blanc";
		ArrayList<Etat> l = new ArrayList<>(10000);
		for (String coupString : board.possibleMoves(nomJoueurActuel)) {
			PlateauEscampe successeur = board.copy();
			successeur.play(coupString, nomJoueurActuel);
			l.add(successeur.copy());
		}
		return l;
	}

	@Override
	public boolean isPerdu(Etat e) {
		PlateauEscampe board = (PlateauEscampe) e;
		if (((super.getNom().equals("noir")) && board.BlancGagne)
				|| ((super.getNom().equals("blanc")) && board.NoirGagne)) {
			System.err.println("baaaaaaad");
		}
		return ((super.getNom().equals("noir")) && board.BlancGagne)
				|| ((super.getNom().equals("blanc")) && board.NoirGagne);
	}
}
