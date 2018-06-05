package fr.cnam.projet;

import java.util.*;

// Classe de definition d'une partie
//
public class Partie
{
  private int numPartie;        // numero de la partie
  private String  ident;        // identificateur (nom ou pseudo) du joueur
  private String  date;         // date de creation
  //private String jeu;         // nom du jeu
  private String etatPartie;    // partie terminee ou non
  private int scoreJoueur;      // score obtenu par le joueur
  private int scoreAdversaire;  // score obtenu par l'adversaire

  // Constructeur
  //
  public Partie(int numPartie, 
                String ident,
                String date,
                //String jeu,
                String etatPartie,
                int scoreJoueur,
                int scoreAdversaire)
  {
    this.numPartie=numPartie;
    this.ident=ident;
    this.date=date;
    //this.jeu=jeu;
    this.etatPartie=etatPartie;
    this.scoreJoueur=scoreJoueur;
    this.scoreAdversaire=scoreAdversaire;
  }

  // Partie en chaine
  //
  public String toString()
  {
    return String.format("%3d %-15s %5s %2s %2d %2d",
                         numPartie,ident,date,etatPartie,scoreJoueur,scoreAdversaire);
  }

  // Getteur du joueur
  //
  public String getIdent() {
    return ident;
  }
  
  // Getteur du numero de partie
  //
  public int getNumPartie() {
    return numPartie;
  }
}