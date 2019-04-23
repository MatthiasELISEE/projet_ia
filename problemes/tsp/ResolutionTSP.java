/**
 *
 */
package problemes.tsp;

import iia.espacesEtats.algorithmes.AEtoile;
import iia.espacesEtats.algorithmes.AlgorithmeHeuristiqueRechercheEE;
import iia.espacesEtats.modeles.Etat;
import iia.espacesEtats.modeles.Heuristique;
import iia.espacesEtats.modeles.Probleme;
import iia.espacesEtats.modeles.Solution;
import java.util.LinkedList;


/**
 * Main générique pour la résolution de problème.
 * @author chatalic
 */
public class ResolutionTSP {

    public static void main(String[] args) {


        /* Fonction heuristique de base : prend la route la plus courte sur
         *  la carte, et multiplie par le nombre de villes qui reste à
         *  parcourir.
         * Cette heuristique est minorante, mais peu efficace !
         *
         * Quelle critique peut-on lui faire ?
         *
         * */

        final float minDistance = EtatTSP.CARTE.distanceMinimum();

        Heuristique h1 = new Heuristique() {

            @Override
            public float eval(Etat e) {
                if (e instanceof EtatTSP) {
                    return ((EtatTSP) e).getaParcourir().size() * minDistance;
                } else {
                    throw new Error("Cette heursitique ne peut s'appliquer que sur des EtatTSP");
                }
            }
        };

        //Pour chaque ville restante à parcourir,
        //prendre la longueur moyenne des routes quittant cette ville.
        //Sommer ces distances

        Heuristique h2 = new Heuristique() {
            @Override
            public float eval(Etat e) {
                if (e instanceof EtatTSP) {
                    return eval2((EtatTSP) e);
                } else {
                    throw new Error("Cette heursitique ne peut s'appliquer que sur des EtatTSP");
                }
            }

            public float eval2(EtatTSP e) {
                float somme = 0;
                float resultat = 0;
                int nb = 0;

                ProblemeTSP p = new ProblemeTSP(e, "");

                for (Etat e1 : p.successeurs(e)) {
                    nb ++;
                    for (Etat e2 : p.successeurs(e1)) {
                        somme += p.cout(e1, e2);
                    }
                    resultat += somme;
                    somme = 0;
				}

				/*
				 * for (String v : e.getaParcourir()) { if (e.allerA(v)) { nb
				 * ++; for (String v2: e.getaParcourir()) { if
				 * (e.CARTE.connexion(v, v2)!=-1) { somme +=
				 * e.CARTE.connexion(v, v2); } } resultat += somme; somme =
				 * 0;//successeur(e.CARTE.connexion(e.getVilleCourante(), v)); }
				 * }
				 */
                return resultat/nb;
            }

        };

        Heuristique h3 = new Heuristique() {

            @Override
            public float eval(Etat e) {
                if (e instanceof EtatTSP) {
                    return eval3((EtatTSP) e);
                } else {
                    throw new Error("Cette heursitique ne peut s'appliquer que sur des EtatTSP");
                }
            }
            public float eval3(EtatTSP e){
                float somme = 0;
                CarteRoutiereRoumanie route = new CarteRoutiereRoumanie();

                for (String v : e.getaParcourir()) {
                    somme+=route.routeLaPlusCourte(v);
                }   
                return somme;
            }
        };
    



        /* Définition d'un problème : choix de l'état initial
         * et construction du problème*/
        LinkedList<String> villesAVisiter = EtatTSP.CARTE.getListeVilles();
        /* n'importe quelle ville peut être un point de départ */
        String depart = villesAVisiter.getFirst();

        System.out.println("Depart : " + depart);
        System.out.println("Parcours : " + villesAVisiter);

        Etat initial = new EtatTSP(villesAVisiter, depart);
        Probleme pb = new ProblemeTSP(initial, "Voyage en Roumanie");

        /* Choix de l'algorithe de ésolution */
        AlgorithmeHeuristiqueRechercheEE algo = new AEtoile(h1);

        /* La solution, si elle est trouvée est retournée sous forme d'une
         *  liste d'états allant de l'état initial à  l'état final
         */
        Solution sol = algo.chercheSolution(pb);
        if (sol != null) {
            System.out.println("La solution est la suite : ");
            sol.affiche();
        } else {
            System.out.println("Echec !");
        }
    }
}