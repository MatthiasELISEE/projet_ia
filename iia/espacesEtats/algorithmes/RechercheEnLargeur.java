package iia.espacesEtats.algorithmes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Probleme;
import iia.espacesEtats.modeles.Solution;

/**
 * RechercheEnLargeur codage de l'algorithme de recherche en largeur d'abord
 * 
 * @author <Vous m�me>
 */
public class RechercheEnLargeur implements AlgorithmeRechercheEE {

	// ----------------------------------------------------
	// Constructeurs
	// ----------------------------------------------------
	public RechercheEnLargeur() {
		super();
	}

	// ----------------------------------------------------
	// Methode(s) requise par la classe abstraite AlgorithmeRechercheEE
	// ----------------------------------------------------
	@Override
	public Solution chercheSolution(Probleme p) {
		HashSet<Etat> dejadev = new HashSet<>(); 
		LinkedList<Etat> frontiere = new LinkedList<>(); 
		frontiere.add(p.getEtatInitial());
		Solution sol = new Solution();
		HashMap<Etat,Etat> Pere = new HashMap<>(); 
		Pere.put(p.getEtatInitial(), p.getEtatInitial());
		
		while (!frontiere.isEmpty()){
			Etat n = frontiere.getFirst();
			System.out.println(n);
			
			if (p.isTerminal(n)) {
				return construireSolution(n,Pere);
			} else {
				frontiere.remove(n);
				dejadev.add(n);
				System.out.println("#");
				for (Etat s : p.successeurs(n)) {
					if (frontiere.contains(s) || dejadev.contains(s)) {
						continue;
					}
					Pere.put(s, n);
					frontiere.add(s);
				}
				System.out.println(sol);
				System.out.println("#");
			}
		}
		return null;
	}

	private Solution construireSolution(Etat n, HashMap<Etat, Etat> pere) {
		Solution sol = new Solution(n);
		Etat courant = n;
		while (!pere.get(courant).equals(courant)) {
			courant = pere.get(courant);
			sol.add(courant);
		}
		System.out.println("##");
		System.out.println(n);
		System.out.println(pere.get(n));
		System.out.println(sol);
		System.out.println("##");
		return sol;
	}

}
