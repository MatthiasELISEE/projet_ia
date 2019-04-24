package escampe;

import iia.espacesEtats.modeles.*;
import iia.jeux.alg.*;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class HeuristiqueEscampe implements iia.espacesEtats.modeles.Heuristique, iia.jeux.alg.Heuristique{

	// Graphe d'Etat
	@Override
	public float eval(Etat e) {
		
		return 0;
	}

	// Theorie des jeux
	@Override
	public int eval(PlateauJeu p, Joueur j) {
		if (p.finDePartie()) {
			PlateauEscampe p2 = (PlateauEscampe) p;
			if ((p2.NoirGagne && j.getNom() == "noir") || (p2.BlancGagne && j.getNom()=="blanc")) {
				return Integer.MAX_VALUE;
			}
			else if ((p2.NoirGagne && j.getNom()=="blanc") || (p2.BlancGagne && j.getNom()=="noir")) {
				return Integer.MIN_VALUE;
			} else {
				return 0;
			}
		}
		return 0;
	}
}
