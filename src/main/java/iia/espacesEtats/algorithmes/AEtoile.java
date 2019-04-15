package iia.espacesEtats.algorithmes;
/*
 * AEtoile.java
 */

import java.util.HashMap;
import java.util.HashSet;

import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Heuristique;
import iia.espacesEtats.modeles.Probleme;
import iia.espacesEtats.modeles.ProblemeACout;
import iia.espacesEtats.modeles.Solution;

/**
 * La classe qui implémente l'algo A*
 * 
 * A completer !
 */
public class AEtoile implements AlgorithmeHeuristiqueRechercheEE {

	private Heuristique h;
	private int noeudsDeveloppe = 0;

	/* Constructeur de base */
	public AEtoile(Heuristique h) {
		this.h = h;
	}

	public Solution chercheSolution(Probleme p) {
		if (p instanceof ProblemeACout) {
			return chercheSolution((ProblemeACout) p);
		}
		return null;
	}

	/* Lance l'exploration sur un problème */
	public Solution chercheSolution(ProblemeACout p) {
		HashSet<Etat> dejadev = new HashSet<>();
		HashSet<Etat> frontiere = new HashSet<>();

		frontiere.add(p.getEtatInitial());

		HashMap<Etat, Float> f = new HashMap<>();
		HashMap<Etat, Float> g = new HashMap<>();
		HashMap<Etat, Etat> pere = new HashMap<>();

		pere.put(p.getEtatInitial(), p.getEtatInitial());
		g.put(p.getEtatInitial(), (float) 0);
		f.put(p.getEtatInitial(), h.eval(p.getEtatInitial()));

		Etat n;
		while (!frontiere.isEmpty()) {
			n = this.choixFMin(frontiere, f, p);
			if (p.isTerminal(n)) {
				System.out.println("meme pas mal");
				return buildSolution(n, pere);
			} else {
				frontiere.remove(n);
				dejadev.add(n);
				for (Etat s : p.successeurs(n)) {
					if (!dejadev.contains(s) && !frontiere.contains(s)) {
						pere.put(s, n);
						g.put(s, g.get(n) + p.cout(n, s));
						f.put(s, g.get(s) + h.eval(s));
						frontiere.add(s);
					} else {
						if (g.get(s) > g.get(n) + p.cout(s, n)) {
							pere.put(s, n);
							g.put(s, g.get(n) + p.cout(n, s));
							f.put(s, g.get(s) + h.eval(s));
						}
					}
				}
			}
		}

		return null;
	}

	public Etat choixFMin(HashSet<Etat> frontiere, HashMap<Etat, Float> f, Probleme p) {
		Etat bestEtat = null;
		for (Etat e : frontiere) {
			if (bestEtat == null || (f.get(e) != null && f.get(bestEtat) > f.get(e))) {
				bestEtat = e;
			}
		}
		return bestEtat;
	}

	public void setHeuristique(Heuristique h) {
		this.h = h;
	}

	public Heuristique getHeuristique() {
		return h;
	}

	private Solution buildSolution(Etat n, HashMap<Etat, Etat> pere) {
		Solution sol = new Solution(n);
		Etat courant = n;
		while (!pere.get(courant).equals(courant)) {
			courant = pere.get(courant);
			sol.add(courant);
		}
		return sol;
	}
}
