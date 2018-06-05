// Projet 1 NFA 031 2017-2018
// 
// CARRILLO,Eliseo
//
package fr.cnam.projet;

import java.io.*;
import java.util.*;

import fr.cnam.util.*;

// Classe principale d'execution du projet
//
public class Projet
{
  public static void main(String a_args[])
  {
    Terminal.ecrireStringln("Execution du projet par E. CARRILLO");

    PFJeu site = new PFJeu();
    IHMPFJeu ihm = new IHMPFJeu(site);
  }
}
