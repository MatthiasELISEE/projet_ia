package iia.espacesEtats.algorithmes;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import escampe.PlateauEscampe;
import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Probleme;
import iia.espacesEtats.modeles.ProblemeAvecVictoire;
import iia.espacesEtats.modeles.Solution;

/**
 * RechercheEnProfondeurDetectionDeCycles une version de l'algorithme de
 * recherche en profondeur d'abord capable de dï¿½tecter les cycles pour ne pas
 * redï¿½velopper des ï¿½tats dï¿½jï¿½ vus prï¿½cï¿½demments sur le chemin en
 * cours d'exploration
 *
 * @author <Vous Mï¿½me>
 */
public class RechercheEnProfondeurDetectionDeCycles implements AlgorithmeRechercheEE {
	static int PROFONDEUR_MAX = 4;
	boolean perdu = false;
	int iterations = 0;
	static int WIN = 1;
	static int LOST = -1;
	static int UNKNOWN = 0;
	
	// ----------------------------------------------------
	// Constructeurs
	// ----------------------------------------------------

	public RechercheEnProfondeurDetectionDeCycles() {
		super();
	}

	// ----------------------------------------------------
	// Methode(s) requise par la classe abstraite AlgorithmeRechercheEE
	// ----------------------------------------------------

	@Override
	public Solution chercheSolution(Probleme p) {
		iterations = 0;
		Etat e = p.getEtatInitial();
		System.out.println(e);
		HashSet<Object> s = new HashSet<>();
		try {
			System.out.println("plus loin que ça");
			return solution(p, e, s, 0);
		} catch (Exception e1) {
			System.out.println("error");
			return null;
		}
	}

	private Solution solution(Probleme p, Etat s, HashSet<Object> dejavu, int profondeur) {
		Solution sol;
		if (p.isTerminal(s)) {
			return null;
		}
		if (!dejavu.contains((Object) s)) {
			dejavu.add((Object) s);
			for (Etat sp : p.successeurs(s)) {
				sol = solution(p, sp, dejavu, profondeur + 1);
				if (sol != null) {
					sol.add(p.getEtatInitial());
					return sol;
				}
			}
		}
		return null;
	}

}
