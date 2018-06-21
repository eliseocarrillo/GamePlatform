package fr.cnam.projet;

import java.util.ArrayList;

import fr.cnam.ihm.*;

// Classe de definition de l'IHM principale du projet
// Cette ihm gere la plate-forme de jeu.
// L'heritage de AdaptaterControlesCanvasIHM permet de gerer le clic dans une case
// de la grille de jeu
// L'interface FormulaireInt permet de gerer les actions dans les objets d'ihm
//
public class IHMPFJeu extends AdaptaterControlesCanvasIHM implements FormulaireInt
// 
{
  private PFJeu pfjeu;    // La plate forme de jeu
  CanvasIHM grille;       // La grille de jeu
  Formulaire form;        // Le formulaire de l'ihm principale
  boolean demarrage;      // Boolean pour demarrer le jeu
  boolean arret;          // Boolean pour arreter le jeu

  // Constructeur
  //
  public IHMPFJeu(PFJeu pfjeu)
  {
    // L'applicatif de l'IHM
    //
    this.pfjeu = pfjeu;
    
    // Creation du formulaire
    //
    this.form = new Formulaire("Plate-forme de jeu",this,1200,730);
        
    //  Creation des elements de l'IHM
    //
    //form.addLabel("Afficher tous les joueurs");
    form.addButton("AFF_JOUEURS","Afficher tous les joueurs");
    //form.addLabel("");
    //form.addLabel("Afficher toutes les parties");
    form.addButton("AFF_PARTIES","Afficher toutes les parties");
    //form.addLabel("");
    //form.addLabel("Introduire un nouveau joueur");
    form.addText("IDENT", "Identifiant", true, null);
    form.addText("MDP", "Mot de passe", true, null);
    form.addButton("INSCRIRE","Inscrire");
    form.addButton("IDENTIFIER","Identifier");
    form.addButton("AFF_PARTIES_JOUEES","Les parties jouees");
    form.addListScroll("LISTE_ADVERSAIRES", "Adversaires", true, null, 200, 100);
    form.addButton("VALIDER_ADVERSAIRE","Valider adversaire");
    form.addButton("DEMARRER", "Demarrer");
    form.addButton("ARRETER", "Arreter");
    form.addLabel("");

    form.addZoneText("RESULTATS","Resultats",
                     true,
                     "",
                     380,300);
    
    form.setPosition(400, 350);
    form.addText("JOUEUR","Joueur", false, null);
    form.addText("ADVERSAIRE","Adversaire", false, null);

    form.setPosition(400,0);
    grille = form.addGrilleIHM(Othello.NB_LIGNE,
                               Othello.NB_COLONNE,40,this);

    // Affichage du formulaire
    //
    form.afficher();
    
    // Initialisation de l'applicatif
    //  On affiche le resultat de l'initialisation
    //
    String res = pfjeu.initialiser(this);
    form.setValeurChamp("RESULTATS",res);
    form.setListData("LISTE_ADVERSAIRES", pfjeu.listerAdversaires());
    this.demarrage = false;
    this.arret = false;
  }


  // Methode appellee quand on clique dans un objet d'ihm
  //
  public void submit(Formulaire form,String nomSubmit)
  {

    // Affichage de tous les joueurs
    //
    if (nomSubmit.equals("AFF_JOUEURS"))
    {
      String res = pfjeu.listerTousJoueurs();
      form.setValeurChamp("RESULTATS",res);
    }

    // Affichage de toutes les parties
    //
    if (nomSubmit.equals("AFF_PARTIES"))
    {
      String res = pfjeu.listerToutesParties();
      form.setValeurChamp("RESULTATS",res);
    }
    
    // Inscription et affichage d'un nouveau joueur
    //
    if (nomSubmit.equals("INSCRIRE") && arret == false)
    {
      String res = pfjeu.inscrire(form.getValeurChamp("IDENT"),form.getValeurChamp("MDP"));
      form.setValeurChamp("RESULTATS",res);
      form.setListData("LISTE_ADVERSAIRES", pfjeu.listerAdversaires());
    }
    
    // Identification et affichage d'un joueur
    //
    if (nomSubmit.equals("IDENTIFIER") && arret == false) 
    {
      ArrayList<String> res = pfjeu.identifier(form.getValeurChamp("IDENT"),form.getValeurChamp("MDP"));
      form.setValeurChamp("JOUEUR",res.get(0));
      form.setValeurChamp("RESULTATS",res.get(1));
    }
    
    // Affichage des parties du joueur courant
    //
    if (nomSubmit.equals("AFF_PARTIES_JOUEES"))
    {
      String res = pfjeu.listerPartiesJoueurCourant(form.getValeurChamp("IDENT"));
      form.setValeurChamp("RESULTATS",res);
    }
     
    // Sélection et affichage d'un adversaire
    //
    if (nomSubmit.equals("VALIDER_ADVERSAIRE") && arret == false)
    {
      String adversaire = form.getValeurChamp("LISTE_ADVERSAIRES");
      pfjeu.setIdentificationAdversaire(true);
      form.setValeurChamp("ADVERSAIRE", adversaire);
      String res = "Vous venez de choisir " + adversaire + " comme adversaire";
      form.setValeurChamp("RESULTATS", res);
    }
    
    // Demarrage d'une partie
    //
    if (nomSubmit.equals("DEMARRER") && demarrage == false && arret == false)
    {
      demarrage = true;
      String res = pfjeu.demarrer();
      form.setValeurChamp("RESULTATS", res);
    }
    
    // Arret d'une partie
    //
    if (nomSubmit.equals("ARRETER") && demarrage == true && arret == false)
    {
      String res = pfjeu.arreter();
      form.setValeurChamp("RESULTATS", res);
    }
      
  }

  // Methode appellee quand on clique dans une case de la grille
  //
  public void pointerCaseGrille(int xCase,int yCase,CanvasIHM ihm)
  {
  if (demarrage == true && arret == false) {
    // Selection d'une case dans un jeu d'othello
    pfjeu.getOthello().selectionnerCase(xCase,yCase);  
  }
  }

  // Methode elementaire permettant d'afficher une ligne de texte
  // dans la zone de resultats de l'ihm principale
  //
  public void afficherResultat(String res)
  {
    if (res.equals("")) form.setValeurChamp("RESULTATS","");
    else form.setValeurChamp("RESULTATS",form.getValeurChamp("RESULTATS") + "\n" + res);
  }

  // Getteurs
  //
  public CanvasIHM getGrille(){return grille;}
}