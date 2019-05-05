package iia.jeux.modele;

import iia.jeux.modele.joueur.Joueur;

public interface PlateauJeu {

    /** renvoie la liste des coups possibles
     *
     * @param j	le joueur dont c'est au tour de jouer
     * @return liste des coups possibles pour le joueur j
     */
    public abstract String[] coupsPossibles(Joueur j);

    /** joue un coup sur le plateau
     *
     * @param j	Le joueur qui joue
     * @param coup Le coup joué par le joueur
     */
    public abstract void joue(Joueur j, String coup);

    /** indique si la partie est terminée
     * 
     * @return vrai si la partie est terminée
     */
    public abstract boolean finDePartie();

    //   public abstract CoupJeu parseCoup(String s);
    /** duplique le plateau courant
     * 
     */
    public abstract PlateauJeu copy();

    /** indique si un coup est possible pour un joueur sur le plateau courant
     *
     * @param j	Le joueur qui pourrait jouer ce coup
     * @param c Le coup envisagé par le joueur
     */
    public abstract boolean coupValide(Joueur j, String c);

	public abstract void printPoints();

        /** indique si la partie est terminée
     *
     * @return vrai si la partie est terminée
     */
}
