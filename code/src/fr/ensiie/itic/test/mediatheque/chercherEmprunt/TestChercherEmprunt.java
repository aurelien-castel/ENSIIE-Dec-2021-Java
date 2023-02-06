package fr.ensiie.itic.test.mediatheque.chercherEmprunt;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ensiie.itic.mediatheque.FicheEmprunt;
import fr.ensiie.itic.mediatheque.Mediatheque;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;
import fr.ensiie.itic.test.GenerationMediatheque;

/* 
 *
 * Author : Nicolas Forget
 * Last Update : 12/12/2021
 *
 */


public class TestChercherEmprunt
{
	
	private Mediatheque M;

	// Création d'un environnement de test
	@BeforeEach
	public void creerEnvironnementTest()
	{
		try 
		{
			GenerationMediatheque.MediathequeTestEmprunter();
			
			M = new Mediatheque("MediathequeTestEmprunter");	
		} 
		
		catch (Exception e)
		{
			fail();
		}
	}

	
	// Objectif de test : (1.1) Chercher emprunt avec client inexistant 
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void ChercherEmpruntClientNull() throws OperationImpossible 
	{
		Assertions.assertThrows(OperationImpossible.class, () -> 
		{
			M.chercherEmprunt("client", "NonInscrit", "1_2");
		});
	}
	
	// Objectif de test : (1.2) Chercher un emprunt pour un document non emprunté.
	// Resultat attendu : Levée exception du type OperationImpossible
	@Test
	public void ChercherEmpruntNonDefini() throws OperationImpossible
	{
		Assertions.assertThrows(OperationImpossible.class, () -> 
		{
			M.chercherEmprunt("Nicolas", "Forget", "1_2");
		});
	}
	
	
	// Objectif de test : (1.3) Chercher emprunt avec clients existants et documents bien empruntés.
	// Resultat attendu : Fiches bien trouvées avec les bonnes valeurs.
	@Test
	public void ChercherEmpruntDefini() throws OperationImpossible
	{
		String nom1 = "Forget";
		String prenom1 = "Nicolas";
		
		String nom2 = "Doz";
		String prenom2 = "Louka";
		
		// Définition des emprunts
		M.emprunter(nom1,prenom1, "1");
	
		M.emprunter(nom1,prenom1, "1_2");
		
		M.emprunter(nom2,prenom2, "1_3");

		// Chercher un emprunt pour le client N°1
		FicheEmprunt fiche1 = M.chercherEmprunt(nom1, prenom1, "1_2");
		
		Assertions.assertEquals(fiche1.getClient().getPrenom(), "Nicolas");
		
		Assertions.assertEquals(fiche1.getDocument().getCode(), "1_2");
		
		// Chercher un emprunt pour le client N°2
		FicheEmprunt fiche2 = M.chercherEmprunt(nom2, prenom2, "1_3");
		
		Assertions.assertEquals(fiche2.getClient().getPrenom(), "Louka");
		
		Assertions.assertEquals(fiche2.getDocument().getCode(), "1_3");
	}
	
	
	// Objectif de test : (1.4) Chercher un emprunt qui n'a pas été emprunté.
	// Resultat attendu : La méthode renvoie null.
	@Test
	public void ChercherEmpruntNonEmprunte() throws OperationImpossible
	{
		String nom1 = "Forget";
		String prenom1 = "Nicolas";
		
		String nom2 = "Doz";
		String prenom2 = "Louka";
		
		// Définition d'emprunts pour un autre client
		M.emprunter(nom2,prenom2, "1_3");
		M.emprunter(nom2,prenom2, "1_4");

		// Chercher un emprunt pour le client N°1
		Assertions.assertEquals(M.chercherEmprunt(nom1, prenom1, "1_2"), null);
	}

}
