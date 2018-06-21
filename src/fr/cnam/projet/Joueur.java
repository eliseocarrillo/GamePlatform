package fr.cnam.projet;

import java.util.*;

// Classe de definition d'un joueur
//
public class Joueur
{
  private String  ident;      // identificateur (nom ou pseudo)
  private String  mdp;        // mot de passe

  // Constructeur
  //
  public Joueur(String ident,
                String mdp)
  {
    this.ident = ident;
    this.mdp = mdp;
  }

  // Joueur en chaine
  //
  public String toString()
  {
    return String.format("%-30s %-20s ",
                         ident,mdp);
  }
  
  // Getteur de l'identificateur
  //
  public String getIdent() {
    return ident;
  }
  
  // Getteur du mot de passe
  //
  public String getMdp() {
    return mdp;
  }

}