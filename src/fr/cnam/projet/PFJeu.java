package fr.cnam.projet;

import java.util.*;
import java.text.SimpleDateFormat;

import fr.cnam.util.*;
import fr.cnam.ihm.*;
import fr.cnam.projet.*;

// Classe de definition d'une plate-forme de jeu
//
public class PFJeu
{
private Othello othello;                            // Le jeu d'othello
  private IHMPFJeu ihm;                             // Ihm generale
  private boolean identificationJoueur = false;     // Etat de l'identification d'un joueur
  private boolean identificationAdversaire = false; // Etat de l'identification d'un adversaire

  // Declaration des arraylists de joueurs et parties
  ArrayList<Joueur> listJoueurs;
  ArrayList<Partie> listParties;
  // Constructeur
  //
  public PFJeu()
  {
    othello = new Othello();
    
    // Creation des arraylists de joueurs et parties
    listJoueurs = new ArrayList<Joueur>();
    listParties = new ArrayList<Partie>();
  }

  // Initialisation de la plate-forme de jeu
  //
  public String initialiser(IHMPFJeu ihm)
  {
    this.ihm = ihm;

    String res="Initialisation de la plate-forme de jeu :\n";
      
    // Lecture du fichier des joueurs inscrits
    //
    res=res+"Lecture du fichier des joueurs inscrits\n";
    String[] lignesJoueurs = Terminal.lireFichierTexte("data/Joueurs.txt");
    if (lignesJoueurs == null) {
      res=res+"Impossible de lire le fichier data/Joueurs.txt\n";
      return res;
    }
      
    for(String ligne:lignesJoueurs)
      {
        String[] champs = ligne.split(";");
        String ident = champs[0];
        String mdp   = champs[1];
        Joueur joueur = new Joueur(ident,mdp);
        // Une trace pour verifier que le fichier est bien lu
        System.out.println(joueur.toString());

        // Ajout du joueur dans la liste de joueurs
        listJoueurs.add(joueur);        
      }

    // Lecture du fichier des parties jouees
    // 
    res=res+"Lecture du fichier des parties inscrits\n";
    String[] lignesParties = Terminal.lireFichierTexte("data/Parties.txt");
    if (lignesParties == null) 
      {
        res=res+"Impossible de lire le fichier data/Parties.txt\n";
        return res;
      }
      
    for(String ligne:lignesParties)
      {
        String[] champs = ligne.split(";");
        int numPartie = Integer.parseInt(champs[0]);
        String ident = champs[1];
        String date = champs[2];
        //String jeu = "Othello";
        String etatPartie = champs[4];
        int scoreJoueur = Integer.parseInt(champs[5]);
        int scoreAdversaire = Integer.parseInt(champs[6]);
        Partie partie = new Partie(numPartie,ident,date,etatPartie,scoreJoueur,scoreAdversaire);
        // Une trace pour verifier que le fichier est bien lu
        System.out.println(partie.toString());

        //Ajout de la partie dans la liste de parties
        listParties.add(partie);        
      }

    // Initialisation du jeu d'othello
    //
    res = res+othello.initialiser(this);

    return res;
  }
    
  // Methode qui retourne sous la forme d'une chaine de caractere
  // tous les joueurs
  //
  public String listerTousJoueurs() {
    String res="";
    for (Joueur joueur:listJoueurs) {
      res=res+joueur.toString()+"\n";
    }
    return res;
  }
  
  // Methode qui retourne sous la forme d'une chaine de caractere
  // toutes les parties
  //
  public String listerToutesParties() {
    String res="";
    for (Partie partie:listParties) {
      res=res+partie.toString()+"\n";
    }
    return res;
  }
  
  // Methode pour inscrire un nouveau joueur
  //
  public String inscrire(String ident, String mdp) {
    String res="";
    boolean joueurInscrit = false;
    for(Joueur joueur:listJoueurs) {
      if (joueur.getIdent().equals(ident)) {
        joueurInscrit = true;
        break;
      }
    }
    // Conditions d'acceptation de l'identifiant et le mdp
    // du joueur a inscrire
    if (joueurInscrit == true) {
      res=res+"Le joueur saisi existe deja\n";
    }
    else if(ident.contains(" ")) {
      res=res+"L'identifiant du joueur saisi contient un caractere blanc, ce qui n'est pas possible\n";
    }
    else if(ident.length()==0) {
      res=res+"Il manque l'identifiant du joueur\n";
    }
    else if(mdp.length()==0) {
      res=res+"Il manque le mot de passe du joueur\n";
    }
    else if(mdp.contains(" ")) {
      res=res+"Le mot de passe contient un caractere blanc, ce qui n'est pas possible\n";
    }
    else if(mdp.length()>15) {
      res=res+"Le mot de passe contient plus de 15 caracteres\n";
    }
    else if(ident.length()==0 && mdp.length()==0) {
      res=res+"Ils manquent l'identifiant et le mot de passe du joueur\n";
    }
    else {
      // Creation du joueur
      Joueur joueur = new Joueur(ident,mdp);
        
      // Une trace pour verifier que le joueur est bien introduit
      System.out.println(joueur.toString());

      // Ajout du joueur dans la liste de joueurs
      listJoueurs.add(joueur);
        
      // Message affiche lors de la creation du joueur
      res=res+"Le joueur "+ident+" a ete inscrit avec succes\n";
    }
    return res;
  }

  // Methode pour identifier un joueur
  //
  public ArrayList<String> identifier(String ident, String mdp) {
    ArrayList<String> res = new ArrayList<String>();  
    String arrIdent = "";
    String arrRes = "";
    for(Joueur joueur:listJoueurs) {
      if (joueur.getIdent().equals(ident) && joueur.getMdp().equals(mdp)) {
        identificationJoueur = true;
        arrIdent=ident;
        res.add(arrIdent);
        arrRes=arrRes+"Bonjour "+ident+", vous vous etes bien authentifie";
        res.add(arrRes);
      }
    }
    return res;
  }
  
  // Methode pour lister toutes les parties du joueur identifie
  //
  public String listerPartiesJoueurCourant(String ident) {
    String res="";
    boolean aucunePartie = true;    
    if (identificationJoueur == true) {
      for (Partie partie:listParties) {
        if (partie.getIdent().equals(ident)) {
          aucunePartie = false;
          res=res+partie.toString()+"\n";
        }
      }
      if (aucunePartie == true) {
        res=res+"Le joueur identifie n'a joue aucune partie\n";
      }
    }
    else {
      res=res+"Aucun joueur est identifie\n";
    }
    return res;
  }
  
  // Methode pour lister tous les joueurs
  //
  public String[] listerAdversaires() {
    String[] arrAdversaires;
    arrAdversaires = new String[listJoueurs.size()];
    for(int i=0; i<listJoueurs.size(); i++) {
      arrAdversaires[i] = listJoueurs.get(i).getIdent();
    }
    return arrAdversaires;
  }
  
  // Methode pour demarrer la partie
  //
  public String demarrer() {
    String res = "";
    if (identificationJoueur == true && identificationAdversaire == true) {
      res = res+"C'est parti!";    
    }
    else {
      res = res + "Il manque un joueur ou un adversaire a identifier";
    }
    return res;
  }
  
  // Methode pour arreter la partie
  //
  public String arreter() {
    // Boolean de l'ihm pour demarrer le jeu
    getIhm().demarrage=false;    
    // Boolean de l'ihm pour arreter le jeu
    getIhm().arret=true;
    
    // String a afficher dans l'ihm
    String res = "";
    res=res+"C'est fini! Parti terminee!\n";
    
    // Arguments des parties
    String joueur = getIhm().form.getValeurChamp("IDENT");                  // Joueur
    String adversaire = getIhm().form.getValeurChamp("LISTE_ADVERSAIRES");  // Adversaire
    int couleurJoueur = getOthello().getCouleurJoueur();                    // Couleur du joueur
    int couleurAdversaire = getOthello().getCouleurAdversaire();            //Couleur de l'adversaire
    int scoreJoueur = getOthello().score(couleurJoueur);                    // Score du joueur
    int scoreAdversaire = getOthello().score(couleurAdversaire);            // Score de l'adversaire
    String etatPartie = getOthello().getEtatPartie();                       // Etat de la partie
    int numPartie = listParties.get(listParties.size()-1).getNumPartie()+1; // Numero de partie
    Date date = new Date();                                                 // Date 
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String strDate = sdf.format(date);
    
    // Ajout de la partie correspondante au joueur courant a l'arraylist de parties
    ajouterPartie(numPartie,
                  joueur,
                  strDate,
                  etatPartie,
                  scoreJoueur,
                  scoreAdversaire);
                  
    // Ajout de la partie correspondante au joueur adversaire a l'arraylist de parties
    ajouterPartie(numPartie,
                  adversaire,
                  strDate,
                  etatPartie,
                  scoreAdversaire,
                  scoreJoueur);
                  
    // Strings avec les info des 2 parties creees
    res=res+"Voici le resultat de chaque joueur:\n";
    res=res+"- "+joueur+" : "+scoreJoueur+" pions noirs\n";
    res=res+"- "+adversaire+" : "+scoreAdversaire+" pions verts\n";
    
    // Affichage du gagnant
    int couleurGagnant = getOthello().couleurGagnant(couleurJoueur,couleurAdversaire);
    String gagnant="";        
    if (couleurGagnant==couleurJoueur) {
      gagnant = joueur;      
    } else if (couleurGagnant==0) {
      gagnant = "Aucun";
    } else if (couleurGagnant==couleurAdversaire) {
      gagnant = adversaire;
    }
    res=res+"Donc le gagnant est : "+gagnant;
    return res;
  }
  
  // Methode pour creer et ajouter une partie a l'arraylist de parties
  //
  public Partie ajouterPartie(int numPartie, 
                              String ident, 
                              String date, 
                              String etatPartie, 
                              int scoreJoueur, 
                              int scoreAdversaire) {
    // Creation d'une nouvelle partie
    Partie partie = new Partie(numPartie,ident,date,etatPartie,scoreJoueur,scoreAdversaire);
    // Une trace pour verifier que la partie est bien creee
    System.out.println(partie.toString());
    // Ajout de la partie a l'arraylist de parties
    listParties.add(partie);
    return partie;
  }
   
  // Getteurs
  public Othello getOthello(){return othello;}
  public IHMPFJeu getIhm(){return ihm;}
  public boolean getIdentificationJoueur(){return identificationJoueur;}
  public boolean getIdentificationAdversaire(){return identificationAdversaire;}
  
  // Setteurs
  public void setIdentificationJoueur(boolean identification){identificationJoueur = identification;}
  public void setIdentificationAdversaire(boolean identification){identificationAdversaire = identification;}
}