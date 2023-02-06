package fr.ensiie.itic.test.mediatheque.emprunter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ensiie.itic.mediatheque.FicheEmprunt;
import fr.ensiie.itic.mediatheque.Mediatheque;
import fr.ensiie.itic.mediatheque.client.CategorieClient;
import fr.ensiie.itic.mediatheque.client.Client;
import fr.ensiie.itic.mediatheque.util.Datutil;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;
import fr.ensiie.itic.test.GenerationMediatheque;
/* 
*
* Author : Guillaume Descroix
* Last Update : 05/12/21
*
*
* L'objectif de cette classe est de tester les fonctions d'emprunt et de restitution d'un article
* dans les cas particuliers ou l'utilisateur change de catégorie :
* 	- L'utilisateur change de catégorie et la nouvelle catégorie lui 
* 	  permet de reserver moins de livre qu'il n'en a actuellement
*   - L'utilisateur change de catégorie et cela créé un retard sur un rendu en cours 
*   
* TOUS les autres tests concernant le changement de catégorie d'un utilisateur son réaliser dans les classes suivantes :
* 	- TestEmpruntChangeCat.java
* 	- TestRestituerChangeCat.java 
*/

public class TestChangeCatSpec {
	
	private Mediatheque M;

	// Creation d'un environnement de test
	@BeforeEach
	public void creerEnvironnementTest() {
		try {
			// Génération de la médiatheque de test
			GenerationMediatheque.MediathequeTestEmprunter();
			// Récupération de la médiatheque de test
			M = new Mediatheque("MediathequeTestEmprunter");
			
			// Emprunt Client (Testé en amont avec TestEmprunt)
			M.emprunter("Descroix", "Guillaume","1");
			M.emprunter("Descroix", "Guillaume","1_2");
			M.emprunter("Descroix", "Guillaume","1_3");
			M.emprunter("Delamotte", "Guillaume","3");
			M.emprunter("Delamotte", "Guillaume","3_2");
			M.emprunter("Delamotte", "Guillaume","3_3");
			M.emprunter("Doz", "Louka","5");
			M.emprunter("Doz", "Louka","5_2");
			M.emprunter("Doz", "Louka","5_3");
			
			// Ajouter Nouvelle catégorie
			M.ajouterCatClient("EmpruntMaxInf", 2, 30.0, 1.0, 2.0, false);
			M.ajouterCatClient("EmpruntInitDepasse", 5, 5.0, 0.1, 1.5, false);
		} catch (Exception e) {
			fail();
		}
	}
	
	/* Objectif de test :
	 *					1 - (CT12.2.a) Vérifier l'impossibilité de reservé un article audio si l'utilisateur 
	 *					à trop d'article emprunté suite à un changement de catégorie
	 *					2 - (CT25.2.a) Vérifier la possibilité de restituer un article audio si l'utilisateur 
	 *					à trop d'article emprunté suite à un changement de catégorie
	 *					
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu
	 */
	@Test
	public void testEumpruntMaxDepasseAudio() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		
		Client c = M.chercherClient(nom, prenom);

		
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		try {
			//Changement de catégorie
			M.modifierClient(c, nom, prenom, c.getAdresse(),"EmpruntMaxInf", 0);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		//1er test (CT12.2.a)
		int verifNbEmprunt2 = M.chercherClient(nom, prenom).getNbEmpruntsEnCours();
		int nbEmprunt2 = M.chercherDocument("1_4").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"1_4");
			}
		);
		Assertions.assertEquals(verifNbEmprunt2,M.chercherClient(nom, prenom).getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt2,M.chercherDocument("1_4").getNbEmprunts());
		//2ème test (CT25.2.a)
		try {
			M.restituer(nom,prenom,"1");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
	
	/* Objectif de test :
	 *					1 - (CT12.1.a) Vérifier l'impossibilité de reservé un article livre si l'utilisateur 
	 *					à trop d'article emprunté suite à un changement de catégorie
	 *					2 - (CT25.1.a) Vérifier la possibilité de restituer un article livre si l'utilisateur 
	 *					à trop d'article emprunté suite à un changement de catégorie
	 *					
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu
	 */
	@Test
	public void testEumpruntMaxDepasseLivre() {
		String nom = "Delamotte";
		String prenom = "Guillaume";
		
		Client c = M.chercherClient(nom, prenom);
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		try {
			//Changement de catégorie
			M.modifierClient(c, nom, prenom, c.getAdresse(),"EmpruntMaxInf", 0);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		//1er test (CT12.1.a)
		int verifNbEmprunt2 = M.chercherClient(nom, prenom).getNbEmpruntsEnCours();
		int nbEmprunt2 = M.chercherDocument("3_4").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"3_4");
			}
		);
		Assertions.assertEquals(verifNbEmprunt2,M.chercherClient(nom, prenom).getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt2,M.chercherDocument("3_4").getNbEmprunts());
		//2ème test (CT25.1.a)
		try {
			M.restituer(nom,prenom,"3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
	
	/* Objectif de test :
	 *					1 - (CT12.3.a) Vérifier l'impossibilité de reservé un article video si l'utilisateur 
	 *					à trop d'article emprunté suite à un changement de catégorie
	 *					2 - (CT25.3.a) Vérifier la possibilité de restituer un article video si l'utilisateur 
	 *					à trop d'article emprunté suite à un changement de catégorie
	 *					
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu
	 */
	@Test
	public void testEumpruntMaxDepasseVideo() {
		String nom = "Doz";
		String prenom = "Louka";
		
		Client c = M.chercherClient(nom, prenom);

		
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		try {
			//Changement de catégorie
			M.modifierClient(c, nom, prenom, c.getAdresse(),"EmpruntMaxInf", 0);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		//1er test (CT12.3.a)
		int verifNbEmprunt2 = M.chercherClient(nom, prenom).getNbEmpruntsEnCours();
		int nbEmprunt2 = M.chercherDocument("5_4").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"5_4");
			}
		);
		Assertions.assertEquals(verifNbEmprunt2,M.chercherClient(nom, prenom).getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt2,M.chercherDocument("5_4").getNbEmprunts());
		//2ème test (CT25.3.a)
		try {
			M.restituer(nom,prenom,"5");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
	
	/* Objectif de test :
	 *					1 - (CT12.2.b) Vérifier l'impossibilité de reservé un article audio si l'utilisateur 
	 *					à un article emprunté en retard suite à un changement de catégorie
	 *					2 - (CT25.2.b) Vérifier la possibilité de restituer un article audio si l'utilisateur 
	 *					à un article emprunté en retard suite à un changement de catégorie
	 *					
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu
	 */
	@Test
	public void testEumpruntRetardAudio() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		
		//D'après l'énoncé :
		int DUREE = 4 * Datutil.DAYSINWEEK;
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = M.chercherCatClient("EmpruntInitDepasse");
		int duree = (int) ((double) DUREE*catc.getCoefDuree())+1;
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		
		Datutil.addAuJour(duree);
		try {
			//Changement de catégorie
			M.modifierClient(c, nom, prenom, c.getAdresse(),"EmpruntInitDepasse", 0);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		//1er test (CT12.2.b)
		int verifNbEmprunt2 = M.chercherClient(nom, prenom).getNbEmpruntsEnCours();
		int nbEmprunt2 = M.chercherDocument("1_4").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"1_4");
			}
		);
		Assertions.assertEquals(verifNbEmprunt2,M.chercherClient(nom, prenom).getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt2,M.chercherDocument("1_4").getNbEmprunts());
		//2ème test (CT25.2.b)
		try {
			M.restituer(nom,prenom,"1");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
	
	/* Objectif de test :
	 *					1 - (CT12.1.b) Vérifier l'impossibilité de reservé un article livre si l'utilisateur 
	 *					à un article emprunté en retard suite à un changement de catégorie
	 *					2 - (CT25.1.b) Vérifier la possibilité de restituer un article livre si l'utilisateur 
	 *					à un article emprunté en retard suite à un changement de catégorie
	 *					
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu
	 */
	@Test
	public void testEumpruntRetardLivre() {
		String nom = "Delamotte";
		String prenom = "Guillaume";
		
		//D'après l'énoncé :
		int DUREE = 6 * Datutil.DAYSINWEEK;
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = M.chercherCatClient("EmpruntInitDepasse");
		int duree = (int) ((double) DUREE*catc.getCoefDuree())+1;
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		
		Datutil.addAuJour(duree);
		try {
			//Changement de catégorie
			M.modifierClient(c, nom, prenom, c.getAdresse(),"EmpruntInitDepasse", 0);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		//1er test (CT12.1.b)
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"3_4");
			}
		);
		//2ème test (CT25.1.b)
		try {
			M.restituer(nom,prenom,"3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
	
	/* Objectif de test :
	 *					1 - (CT12.3.b) Vérifier l'impossibilité de reservé un article video si l'utilisateur 
	 *					à un article emprunté en retard suite à un changement de catégorie
	 *					2 - (CT25.3.b) Vérifier la possibilité de restituer un article video si l'utilisateur 
	 *					à un article emprunté en retard suite à un changement de catégorie
	 *					
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu
	 */
	@Test
	public void testEumpruntRetardVideo() {
		String nom = "Doz";
		String prenom = "Louka";
		
		//D'après l'énoncé :
		int DUREE = 2 * Datutil.DAYSINWEEK;
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = M.chercherCatClient("EmpruntInitDepasse");
		int duree = (int) ((double) DUREE*catc.getCoefDuree())+1;
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		
		Datutil.addAuJour(duree);
		try {
			//Changement de catégorie
			M.modifierClient(c, nom, prenom, c.getAdresse(),"EmpruntInitDepasse", 0);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		//1er test (CT12.3.b)
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"5_4");
			}
		);
		//2ème test (CT25.3.b)
		try {
			M.restituer(nom,prenom,"5");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
}
