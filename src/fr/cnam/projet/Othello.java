package fr.cnam.projet;

import java.util.ArrayList;

import fr.cnam.ihm.*;

/* Classe de definition du jeu d'othello
 */
public class Othello
{
  final public static int NB_LIGNE = 8;
  final public static int NB_COLONNE = 8;
  // Decalage possible de la coordonnee y autour d'une case
  final public static int[] OFFSET_MOUV_LIGNE = {-1, -1, -1,  0,  0,  1,  1,  1};
  // Decalage possible de la coordonnee x autour d'une case
  final public static int[] OFFSET_MOUV_COLONNE = {-1,  0,  1, -1,  1, -1,  0,  1};
  
  private PFJeu          pfjeu;
  private CanvasIHM      grille;
  private int            couleurJoueur;
  private int            couleurAdversaire;
  private int            joueurCourant;
  private boolean        hasJeuPossible;
  private String         etatPartie;

  // Initialisation du jeu d'othello
  //
  public String initialiser(PFJeu pfjeu) {
    String res = "Initialisation du jeu d'Othello:\n";

    this.pfjeu  = pfjeu;
    this.grille = pfjeu.getIhm().getGrille();
    this.couleurJoueur = 1;                       // Noir
    this.couleurAdversaire = 5;                   // Vert
    this.joueurCourant = 1;                       // Le premier jouer qui commence est celui noir
    this.hasJeuPossible = true;                   // Le joueur courant peut jouer
    this.etatPartie = "0";                        // Etat de la partie: 0=pas fini; 1= fini

    grille.setMarque(couleurJoueur,3,3);
    grille.setMarque(couleurJoueur,4,4);
    grille.setMarque(couleurAdversaire,3,4);
    grille.setMarque(couleurAdversaire,4,3);

    res = res + "OK";

    return (res);
    }
  
    // Definition de la couleur du pion en fonction du joueur courant
    //
    public int couleurJoueurCourant(int joueurCourant) {
      int couleurJoueurCourant;
    if (joueurCourant == 1) {
      couleurJoueurCourant = couleurJoueur;
    } else {
      couleurJoueurCourant = couleurAdversaire;
    }
    return couleurJoueurCourant;
    }
  
  // Score
  //
  public int score(int couleurPion) {
    int score;
    score = 0;
    for (int i = 0; i < 8; i = i + 1) {
      for (int j = 0; j < 8; j = j + 1) {
        if (grille.getMarque(i,j) == couleurPion) {
          score = score + 1;
        }
      }
    }
    return score;
  }
  
  // Detecter le gagnant
  //
  public int couleurGagnant(int couleurJoueur, int couleurAdversaire) {
    int couleurGagnant;
    if(score(couleurJoueur) > score(couleurAdversaire)) {
      couleurGagnant = couleurJoueur;
    } else if (score(couleurJoueur) == score(couleurAdversaire)) {
      couleurGagnant = 0;
    } else {
      couleurGagnant = couleurAdversaire;
    }
    return couleurGagnant;
  }
  
  // Changer le tour de jeu
  //
  public void changerTour() {
    if (joueurCourant == 1) {
      joueurCourant = 2;
    } else {
      joueurCourant = 1;
    }
  }
  
  // Detecter des mouvements possibles
  //
  public boolean isPossibleMouv() {
    // Boolean a retourner, initialise a false
    boolean isPossible = false;
    // Definition de la couleur du pion en fonction du joueur courant
    int couleurJoueurCourant = couleurJoueurCourant(joueurCourant);
    for (int i = 0; i < 8; i = i + 1) {
      for (int j = 0; j < 8; j = j + 1) {
        if(validMouvEtChangerCouleur(i,j,true,couleurJoueurCourant)) {
          isPossible = true;
          break;
        }
      }
    }
    return isPossible;
  }
  
  /**
   * Validation de la case choisie et changement de couleur si besoin
   * @param x Colonne du mouvement
   * @param y Ligne du mouvement
   * @param onlyValid Boolean pour verifier s'il y a seulement besoin de valider un 
                      mouvement ou il faut aussi changer la couleur, si besoin
   * @param couleurJoueurCourant
   */
  public boolean validMouvEtChangerCouleur(int x,int y,boolean onlyValid,int couleurJoueurCourant) {
    // Boolean a retourner,initialise a false
    boolean isValid = false;
    // Si seulement on valide le mouvement (on ne cherche pas a changer la couleur de la case)
    // et la case n'est pas vide
    if (onlyValid && !grille.siCaseLibre(x, y)) { } 
    // Dans les autres cas
    else {
      // Verification dans les 8 directions
      for (int i = 0; i < 8; i = i + 1) {
        int curX = x + OFFSET_MOUV_COLONNE[i];
        int curY = y + OFFSET_MOUV_LIGNE[i];
        boolean hasPionsAdversaire = false;
        while (curY >=0 && curY < 8 && curX >= 0 && curX < 8) {
          // Si la case est vide, break
          if (grille.siCaseLibre(curX,curY)) {
            break;
          }
          // S'il y a un pion de l'adversaire dans une case (curX,curY) 
          // autour de la case signalee (x,y), la valeur de la variable 
          // hasPionsAdversaire change a true
          if (grille.getMarque(curX,curY) != couleurJoueurCourant) {
            hasPionsAdversaire = true;
          }
          // S'il y a une autre case dans la direction de la case initiale (x, y) et 
          // la courante actuelle (curX, curY), de la meme couleur du joueur courant
          if (grille.getMarque(curX,curY) == couleurJoueurCourant && hasPionsAdversaire) {
            isValid = true;
          }
          // Si le mouvement est correct, et on cherche a changer la couleur de pions
          if (isValid && !onlyValid) {
            // Coordonnees de la case courante precedente (case intermediaire a changer de couleur), 
            // celle avec une couleur differente a celle des cases initiale et courante actuelle
            int pionAffecteX = x + OFFSET_MOUV_COLONNE[i];
            int pionAffecteY = y + OFFSET_MOUV_LIGNE[i];
            // Tant que la ou les case/s affectee/s ne soit ou soient pas la case courante actuelle
            while (pionAffecteY != curY || pionAffecteX != curX) {
              grille.setMarque(couleurJoueurCourant,pionAffecteX,pionAffecteY);
              // Compteur i applique aux coordonnees de la case courante, afin de continuer l'analyse
              // des cases se trouvant entre la case initiale (x, y) et la courante (curX, curY) 
              // dont la couleur n'est pas celle du joueur courant
              pionAffecteX += OFFSET_MOUV_COLONNE[i];
              pionAffecteY += OFFSET_MOUV_LIGNE[i];
            }
            break;
          }
          // Compteur i applique aux variables offset, afin de continuer l'analyse
          // de toutes les cases autour de la case signalee
          curY += OFFSET_MOUV_LIGNE[i];
          curX += OFFSET_MOUV_COLONNE[i];
        }  
        // S'il y a eu un mouvement valide precedemment, et on ne cherche que valider le mouvement, 
        // pas besoin de continuer
        if (isValid && onlyValid){
          break;
        }
      }
    }
    return isValid;
  }

  /**
   * Selection de la case
   * @param x Colonne du mouvement
   * @param y Ligne du mouvement
   */
  public void selectionnerCase(int x,int y) {
    // Verification de la possibilite de faire un mouvement par le joueur courant
    if (isPossibleMouv()) {
      pfjeu.getIhm().afficherResultat("Selection case : " + x + " " + y);
      // Definition de la couleur du pion en fonction du joueur courant
      int couleurJoueurCourant = couleurJoueurCourant(joueurCourant);
      // Verification de la validite de la case choisie par le joueur courant
      if (validMouvEtChangerCouleur(x,y,true,couleurJoueurCourant)) {     
        // Affichage de la case selectionne par le joueur courant
        grille.setMarque(couleurJoueurCourant,x,y);
        // Changement de couleur de la case, si besoin
        validMouvEtChangerCouleur(x,y,false,couleurJoueurCourant);
        // Changement de tour
        changerTour();
      } else {
        pfjeu.getIhm().afficherResultat("La case choisie n'est pas correcte. Reessayez");
      }
    // Si le joueur courant ne peut pas jouer 
    } else if (hasJeuPossible && !isPossibleMouv()){
      pfjeu.getIhm().afficherResultat("Le joueur actuel ne peut pas jouer,\ndonc le tour passe a l'autre joueur");
      changerTour();
      hasJeuPossible = false;
    // Si le joueur precedent ne peut pas jouer,
    // et le joueur courant non plus
    } else if (!hasJeuPossible && !isPossibleMouv()) {
      etatPartie = "1";
      pfjeu.getIhm().afficherResultat("Aucun joueur ne peut jouer!");
      pfjeu.getIhm().afficherResultat(pfjeu.arreter());
    }
  }
  
  // Getteurs
  public String getEtatPartie() {return etatPartie;}
  public int getCouleurJoueur() {return couleurJoueur;}
  public int getCouleurAdversaire() {return couleurAdversaire;}
}