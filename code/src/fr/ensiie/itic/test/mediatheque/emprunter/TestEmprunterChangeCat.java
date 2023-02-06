package fr.ensiie.itic.test.mediatheque.emprunter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

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
 */

public class TestEmprunterChangeCat {
	
	private Mediatheque M;

	// Creation d'un environnement de test
	@BeforeEach
	public void creerEnvironnementTest() {
		try {
			// Génération de la médiatheque de test
			GenerationMediatheque.MediathequeTestEmprunter();
			// Récupération de la médiatheque de test
			M = new Mediatheque("MediathequeTestEmprunter");
			
			// Changement de catégorie
			Client c = M.chercherClient("Descroix", "Guillaume");
			M.modifierClient(c, c.getNom(), c.getPrenom(), c.getAdresse(),"Classic", 0);
			c = M.chercherClient("Doz", "Louka");
			M.modifierClient(c, c.getNom(), c.getPrenom(), c.getAdresse(),"Classic", 0);
			c = M.chercherClient("Forget", "Nicolas");
			M.modifierClient(c, c.getNom(), c.getPrenom(), c.getAdresse(),"Classic", 0);
			c = M.chercherClient("Delamotte", "Guillaume");
			M.modifierClient(c, c.getNom(), c.getPrenom(), c.getAdresse(),"Classic", 0);
			c = M.chercherClient("Castel", "Aurélien");
			M.modifierClient(c, c.getNom(), c.getPrenom(), c.getAdresse(),"Classic", 0);
			M.verifier();
		} catch (Exception e) {
			fail();
		}
	}

	// Objectif de test : (CT1) Vérifier l'impossibilité de reservé un article inexistant
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void emprunterArcticleInexistant() {
		int nbEmpruntCli = M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Descroix","Guillaume","codeArticleInexistant");
			}
		);
		Assertions.assertEquals(nbEmpruntCli,M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues());
	}
	
	/* Objectif de test :
	 *					1 - (CT2) Vérifier l'impossibilité de reservé un article avec un nom null
	 *					2 - (CT3) Vérifier l'impossibilité de reservé un article avec un prénom null
	 *					3 - (CT4) Vérifier l'impossibilité de reservé un article avec un numéro null
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - levée d'une exception du type OperationImpossible
	 *					3 - levée d'une exception du type OperationImpossible
	 */
	@Test
	public void emprunterArcticleAvecValNull() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(null,"Guillaume","1");
			}
		);
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Descroix",null,"1");
			}
		);
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Descroix","Guillaume",null);
			}
		);
	}
	
	// Objectif de test : (CT5.2) Vérifier l'impossibilité de reservé un article audio avec un client inconnu
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void emprunterArcticleAudioAvecClientInconnu() {
		int nbEmprunt = M.chercherDocument("1").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("NomInconnu","PrenomInconnu","1");
			}
		);
		Assertions.assertEquals(M.chercherDocument("1").estEmprunte(),false);
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("1").getNbEmprunts());
	}
	
	// Objectif de test : (CT5.1) Vérifier l'impossibilité de reservé un article livre avec un client inconnu
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void emprunterArcticleLivreAvecClientInconnu() {
		int nbEmprunt = M.chercherDocument("3").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("NomInconnu","PrenomInconnu","3");
			}
		);
		Assertions.assertEquals(M.chercherDocument("3").estEmprunte(),false);
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("3").getNbEmprunts());
		
	}
	
	// Objectif de test : (CT5.3) Vérifier l'impossibilité de reservé un article video avec un client inconnu
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void emprunterArcticleVideoAvecClientInconnu() {
		int nbEmprunt = M.chercherDocument("5").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("NomInconnu","PrenomInconnu","5");
			}
		);
		Assertions.assertEquals(M.chercherDocument("5").estEmprunte(),false);
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("5").getNbEmprunts());
	}
	
	// Objectif de test : (CT6.2) Vérifier l'impossibilité de reservé un article audio non empruntable
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void emprunterArcticleAudioNonEmpruntable() {
		int nbEmprunt = M.chercherDocument("2").getNbEmprunts();
		int nbEmpruntCli = M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Descroix","Guillaume","2");
			}
		);
		Assertions.assertEquals(M.chercherDocument("2").estEmprunte(),false);
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("2").getNbEmprunts());
		Assertions.assertEquals(nbEmpruntCli,M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues());
	}
	
	// Objectif de test : (CT6.1) Vérifier l'impossibilité de reservé un article livre non empruntable
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void emprunterArcticleLivreNonEmpruntable() {
		int nbEmprunt = M.chercherDocument("4").getNbEmprunts();
		int nbEmpruntCli = M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Descroix","Guillaume","4");
			}
		);
		Assertions.assertEquals(M.chercherDocument("4").estEmprunte(),false);
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("4").getNbEmprunts());
		Assertions.assertEquals(nbEmpruntCli,M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues());
	}
	
	// Objectif de test : (CT6.3) Vérifier l'impossibilité de reservé un article video non empruntable
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void emprunterArcticleVideoNonEmpruntable() {
		int nbEmprunt = M.chercherDocument("6").getNbEmprunts();
		int nbEmpruntCli = M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Descroix","Guillaume","6");
			}
		);
		Assertions.assertEquals(M.chercherDocument("6").estEmprunte(),false);
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("6").getNbEmprunts());
		Assertions.assertEquals(nbEmpruntCli,M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues());
	}
	
	/* Objectifs de test : 
	 *					1 - (CT7.2.a) Vérifier la possibilité de reservé un article audio quand 
	 *					  l'utilisateur n'a pas encore emprunter d'article (Test borne inférieur)
	 *					2 - (CT8.2) Vérifier l'impossibilité de reservé un article audio déjà emprunter
	 *					3 - (CT7.2.b) Vérifier la possibilité de reservé un article audio quand 
	 *					  l'utilisateur a déjà emprunter un article mais n'a pas dépassé la limite
	 *	 				4 - (CT7.2.c) Vérifier la possibilité de reservé un article audio quand 
	 *					  l'utilisateur va atteindre le nombre d'article maximum qu'il peut emprunter (Test borne supérieur)
	 *					5 - (CT9.2) Vérifier l'impossibilié de reservé un article audio quand
	 *					  l'utilisateur a atteind le nombre d'article maximum qu'il peut emprunter
	 *					6 - (CT23.2) Vérifier la possibilité de rendre un article audio
	 *					7 - (CT7.2.d) Vérifier la possibilité de reservé un article audio après que
	 *					  l'utilisateur est rendu un article emprunter
	 *					8 - (CT23.2.b) Vérifier la possibilité de rendre un article audio (Test borne supérieur)
	 *					9 - (CT23.2.c) Vérifier la possibilité de rendre un article audio
	 *					10 - (CT23.2.d) Vérifier la possibilité de rendre un article audio (Test borne inférieur)
	 * Resultats attendu : 
	 *					1 - Emprunt réussi / document bien emprunter
	 *					2 - levée d'une exception du type OperationImpossible
	 *					3 - Emprunt réussi / document bien emprunter
	 *					4 - Emprunt réussi / document bien emprunter
	 *					5 - levée d'une exception du type OperationImpossible
	 *					6 - Rendu réussi / document bien rendu
	 *					7 - Emprunt réussi / document bien emprunter
	 *					8 - Rendu réussi / document bien rendu
	 *					9 - Rendu réussi / document bien rendu
	 *					10 - Rendu réussi / document bien rendu
	 */
	@Test
	public void emprunterArcticleAudioDejaEmprunter() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		
		//D'après l'énoncé :
		int DUREE = 4 * Datutil.DAYSINWEEK;
		double TARIF = 1.0;
		
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = c.getCategorie();
		
		//1er test (CT7.2.a)
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		int nbEmprunt = M.chercherDocument("1").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"1");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("1").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//2ème test (CT8.2) 
		int verifNbEmprunt2 = M.chercherClient("Delamotte","Guillaume").getNbEmpruntsEnCours();
		int nbEmprunt2 = M.chercherDocument("1").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Delamotte","Guillaume","1");
			}
		);
		Assertions.assertEquals(verifNbEmprunt2,M.chercherClient("Delamotte","Guillaume").getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt2,M.chercherDocument("1").getNbEmprunts());
		//3ème test (CT7.2.b)
		nbEmprunt = M.chercherDocument("1_2").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"1_2");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1_2");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1_2").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("1_2").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//4ème test (CT7.2.c)
		nbEmprunt = M.chercherDocument("1_3").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"1_3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1_3");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1_3").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("1_3").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//5ème test (CT9.2)
		nbEmprunt = M.chercherDocument("1_4").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"1_4");
			}
		);
		Assertions.assertEquals(M.chercherDocument("1_4").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("1_4").getNbEmprunts());
		//6ème test (CT23.2)
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
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//7ème test (CT7.2.d)
		nbEmprunt = M.chercherDocument("1_4").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"1_4");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1_4");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1_4").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("1_4").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//8ème test (CT23.2.b)
		try {
			M.restituer(nom,prenom,"1_4");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1_4");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1_4").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//9ème test (CT23.2.c)
		try {
			M.restituer(nom,prenom,"1_3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1_3");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1_3").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//10ème test (CT23.2.d)
		try {
			M.restituer(nom,prenom,"1_2");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1_2");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1_2").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
	
	/* Objectifs de test : 
	 *					1 - (CT7.1.a) Vérifier la possibilité de reservé un article livre quand 
	 *					  l'utilisateur n'a pas encore emprunter d'article (Test borne inférieur)
	 *					2 - (CT8.1) Vérifier l'impossibilité de reservé un article livre déjà emprunter
	 *					3 - (CT7.1.b) Vérifier la possibilité de reservé un article livre quand 
	 *					  l'utilisateur a déjà emprunter un article mais n'a pas dépassé la limite
	 *	 				4 - (CT7.1.c) Vérifier la possibilité de reservé un article livre quand 
	 *					  l'utilisateur va atteindre le nombre d'article maximum qu'il peut emprunter (Test borne supérieur)
	 *					5 - (CT9.1) Vérifier l'impossibilié de reservé un article livre quand
	 *					  l'utilisateur a atteind le nombre d'article maximum qu'il peut emprunter
	 *					6 - (CT23.1) Vérifier la possibilité de rendre un article livre
	 *					7 - (CT7.1.d) Vérifier la possibilité de reservé un article livre après que
	 *					8 - (CT23.1.b) Vérifier la possibilité de rendre un article livre (Test borne supérieur)
	 *					9 - (CT23.1.c) Vérifier la possibilité de rendre un article livre
	 *					10 - (CT23.1.d) Vérifier la possibilité de rendre un article livre (Test borne inférieur)
	 *					  l'utilisateur est rendu un article emprunter
	 * Resultats attendu : 
	 *					1 - Emprunt réussi / document bien emprunter
	 *					2 - levée d'une exception du type OperationImpossible
	 *					3 - Emprunt réussi / document bien emprunter
	 *					4 - Emprunt réussi / document bien emprunter
	 *					5 - levée d'une exception du type OperationImpossible
	 *					6 - Rendu réussi / document bien rendu
	 *					7 - Emprunt réussi / document bien emprunter
	 *					8 - Rendu réussi / document bien rendu
	 *					9 - Rendu réussi / document bien rendu
	 *					10 - Rendu réussi / document bien rendu
	 */
	@Test
	public void emprunterArcticleLivreDejaEmprunter() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		
		//D'après l'énoncé :
		int DUREE = 6 * Datutil.DAYSINWEEK;
		double TARIF = 0.5;
		
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = c.getCategorie();
		
		//1er test (CT7.1.a)
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		int nbEmprunt = M.chercherDocument("3").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("3").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//2ème test (CT8.1) 
		int verifNbEmprunt2 = M.chercherClient("Delamotte","Guillaume").getNbEmpruntsEnCours();
		int nbEmprunt2 = M.chercherDocument("3").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Delamotte","Guillaume","3");
			}
		);
		Assertions.assertEquals(verifNbEmprunt2,M.chercherClient("Delamotte","Guillaume").getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt2,M.chercherDocument("3").getNbEmprunts());
		//3ème test (CT7.1.b)
		nbEmprunt = M.chercherDocument("3_2").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"3_2");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3_2");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3_2").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("3_2").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//4ème test (CT7.1.c)
		nbEmprunt = M.chercherDocument("3_3").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"3_3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3_3");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3_3").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("3_3").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//5ème test (CT9.1)
		nbEmprunt = M.chercherDocument("3_4").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"3_4");
			}
		);
		Assertions.assertEquals(M.chercherDocument("3_4").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("3_4").getNbEmprunts());
		//6ème test (CT23.1)
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
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//7ème test (CT7.1.d)
		nbEmprunt = M.chercherDocument("3_4").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"3_4");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3_4");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3_4").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("3_4").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//8ème test (CT23.1.b)
		try {
			M.restituer(nom,prenom,"3_4");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3_4");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3_4").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//9ème test (CT23.1.c)
		try {
			M.restituer(nom,prenom,"3_3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3_3");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3_3").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//10ème test (CT23.1.d)
		try {
			M.restituer(nom,prenom,"3_2");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3_2");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3_2").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
	
	/* Objectifs de test : 
	 *					1 - (CT7.3.a) Vérifier la possibilité de reservé un article vidéo quand 
	 *					  l'utilisateur n'a pas encore emprunter d'article (Test borne inférieur)
	 *					2 - (CT8.3) Vérifier l'impossibilité de reservé un article vidéo déjà emprunter
	 *					3 - (CT7.3.b) Vérifier la possibilité de reservé un article vidéo quand 
	 *					  l'utilisateur a déjà emprunter un article mais n'a pas dépassé la limite
	 *	 				4 - (CT7.3.c) Vérifier la possibilité de reservé un article vidéo quand 
	 *					  l'utilisateur va atteindre le nombre d'article maximum qu'il peut emprunter (Test borne supérieur)
	 *					5 - (CT9.3) Vérifier l'impossibilié de reservé un article vidéo quand
	 *					  l'utilisateur a atteind le nombre d'article maximum qu'il peut emprunter
	 *					6 - (CT23.3.a) Vérifier la possibilité de rendre un article vidéo
	 *					7 - (CT7.3.d) Vérifier la possibilité de reservé un article vidéo après que
	 *					  l'utilisateur est rendu un article emprunter
	 *					8 - (CT23.3.b) Vérifier la possibilité de rendre un article vidéo (Test borne supérieur)
	 *					9 - (CT23.3.c) Vérifier la possibilité de rendre un article vidéo
	 *					10 - (CT23.3.d) Vérifier la possibilité de rendre un article vidéo (Test borne inférieur)
	 * Resultats attendu : 
	 *					1 - Emprunt réussi / document bien emprunter
	 *					2 - levée d'une exception du type OperationImpossible
	 *					3 - Emprunt réussi / document bien emprunter
	 *					4 - Emprunt réussi / document bien emprunter
	 *					5 - levée d'une exception du type OperationImpossible
	 *					6 - Rendu réussi / document bien rendu
	 *					7 - Emprunt réussi / document bien emprunter
	 *					8 - Rendu réussi / document bien rendu
	 *					9 - Rendu réussi / document bien rendu
	 *					10 - Rendu réussi / document bien rendu
	 */
	@Test
	public void emprunterArcticleVideoDejaEmprunter() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		
		//D'après l'énoncé :
		int DUREE = 2 * Datutil.DAYSINWEEK;
		double TARIF = 1.5;
		
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = c.getCategorie();
		
		//1er test (CT7.3.a)
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		int nbEmprunt = M.chercherDocument("5").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"5");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("5").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//2ème test (CT8.3) 
		int verifNbEmprunt2 = M.chercherClient("Delamotte","Guillaume").getNbEmpruntsEnCours();
		int nbEmprunt2 = M.chercherDocument("5").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter("Delamotte","Guillaume","5");
			}
		);
		Assertions.assertEquals(verifNbEmprunt2,M.chercherClient("Delamotte","Guillaume").getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt2,M.chercherDocument("5").getNbEmprunts());
		//3ème test (CT7.3.b)
		nbEmprunt = M.chercherDocument("5_2").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"5_2");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5_2");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5_2").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("5_2").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//4ème test (CT7.3.c)
		nbEmprunt = M.chercherDocument("5_3").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"5_3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5_3");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5_3").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("5_3").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//5ème test (CT9.3)
		nbEmprunt = M.chercherDocument("5_4").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"5_4");
			}
		);
		Assertions.assertEquals(M.chercherDocument("5_4").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("5_4").getNbEmprunts());
		//6ème test (CT23.3)
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
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//7ème test (CT7.3.d)
		nbEmprunt = M.chercherDocument("5_4").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"5_4");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5_4");
			double tarif = fiche.getTarifEmprunt();
			Date dateDebut = fiche.getDateEmprunt();
			Date dateFin = fiche.getDateLimite();
			Assertions.assertEquals(Datutil.addDate(dateDebut, (int) ((double) DUREE*catc.getCoefDuree())),dateFin);
			Assertions.assertEquals(tarif,TARIF*catc.getCoefTarif());
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5_4").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("5_4").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//8ème test (CT23.3.b)
		try {
			M.restituer(nom,prenom,"5_4");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5_4");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5_4").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//9ème test (CT23.3.c)
		try {
			M.restituer(nom,prenom,"5_3");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5_3");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5_3").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//10ème test (CT23.3.d)
		try {
			M.restituer(nom,prenom,"5_2");
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5_2");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5_2").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
	}
	
	/* Objectifs de test : 
	 *					1 - (CT10.2) Vérifier l' impossibilité de reservé un article audio si l’utilisateur n’a 
	 *						pas restitué un document avant sa date limite
	 *					2 - (CT24.2) Vérifier la possibilité de restituer un article audio si l’utilisateur n’a 
	 *						pas restitué ce document avant sa date limite
	 *					3 - (CT11.2) Vérifier la possibilité de reservé un article livre si l’utilisateur a 
	 *						restitué l'article en retard
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu
	 *					3 - Emprunt réussi / document bien emprunter
	 */
	@Test
	public void emprunterArcticleAudioUtilisateurInterdit() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = c.getCategorie();
		//D'après l'énoncé :
		int DUREE = 4 * Datutil.DAYSINWEEK;
		int duree = (int) ((double) DUREE*catc.getCoefDuree())+1;
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		try {
			M.emprunter(nom,prenom,"1");
			//Change la date & Actualise la médiathèque
			Datutil.addAuJour(duree);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			System.out.println(e);
			fail();
		}
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//1er test (CT10.2)
		int nbEmprunt = M.chercherDocument("1_2").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"1_2");
			}
		);
		Assertions.assertEquals(M.chercherDocument("1_2").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("1_2").getNbEmprunts());
		//2ème test (CT24.2)
		try {
			M.restituer(nom,prenom,"1");
			M.verifier();
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "1");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//3ème test (CT11.2)
		nbEmprunt = M.chercherDocument("1_3").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"1_3");
			//Change la date & Actualise la médiathèque
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			System.out.println(e);
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("1_3").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("1_3").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
	}
	
	/* Objectifs de test : 
	 *					1 - (CT10.1) Vérifier l' impossibilité de reservé un article livre si l’utilisateur n’a 
	 *						pas restitué un document avant sa date limite
	 *					2 - (CT24.1) Vérifier la possibilité de restituer un article livre si l’utilisateur n’a 
	 *						pas restitué ce document avant sa date limite
	 *					3 - (CT11.1) Vérifier la possibilité de reservé un article livre si l’utilisateur a 
	 *						restitué l'article en retard
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu 
	 *					3 - Emprunt réussi / document bien emprunter
	 */
	@Test
	public void emprunterArcticleLivreUtilisateurInterdit() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = c.getCategorie();
		//D'après l'énoncé :
		int DUREE = 6 * Datutil.DAYSINWEEK;
		int duree = (int) ((double) DUREE*catc.getCoefDuree())+1;
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		try {
			M.emprunter(nom,prenom,"3");
			//Change la date & Actualise la médiathèque
			Datutil.addAuJour(duree);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			System.out.println(e);
			fail();
		}
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//1er test (CT10.1)
		int nbEmprunt = M.chercherDocument("3_2").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"3_2");
			}
		);
		Assertions.assertEquals(M.chercherDocument("3_2").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("3_2").getNbEmprunts());
		//2ème test (CT24.1)
		try {
			M.restituer(nom,prenom,"3");
			M.verifier();
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "3");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//3ème test (CT11.1)
		nbEmprunt = M.chercherDocument("3_3").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"3_3");
			//Change la date & Actualise la médiathèque
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			System.out.println(e);
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3_3").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("3_3").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
	}
	
	/* Objectifs de test : 
	 *					1 - (CT10.3) Vérifier l' impossibilité de reservé un article vidéo si l’utilisateur n’a 
	 *						pas restitué un document avant sa date limite
	 *					2 - (CT24.3) Vérifier la possibilité de restituer un article vidéo si l’utilisateur n’a 
	 *						pas restitué ce document avant sa date limite
	 *					3 - (CT11.3) Vérifier la possibilité de reservé un article livre si l’utilisateur a 
	 *						restitué l'article en retard
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - Rendu réussi / document bien rendu
	 *					3 - Emprunt réussi / document bien emprunter
	 */
	@Test
	public void emprunterArcticleVideoUtilisateurInterdit() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		Client c = M.chercherClient(nom, prenom);
		CategorieClient catc = c.getCategorie();
		//D'après l'énoncé :
		int DUREE = 2 * Datutil.DAYSINWEEK;
		int duree = (int) ((double) DUREE*catc.getCoefDuree())+1;
		
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		try {
			M.emprunter(nom,prenom,"5");
			//Change la date & Actualise la médiathèque
			Datutil.addAuJour(duree);
			M.verifier();
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			System.out.println(e);
			fail();
		}
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//1er test (CT10.3)
		int nbEmprunt = M.chercherDocument("5_2").getNbEmprunts();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.emprunter(nom,prenom,"5_2");
			}
		);
		Assertions.assertEquals(M.chercherDocument("5_2").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt,M.chercherDocument("5_2").getNbEmprunts());
		//2ème test (CT24.3)
		try {
			M.restituer(nom,prenom,"5");
			M.verifier();
			FicheEmprunt fiche = M.chercherEmprunt(nom, prenom, "5");
			Assertions.assertEquals(fiche, null);
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5").estEmprunte(),false);
		Assertions.assertEquals(verifNbEmprunt-1,c.getNbEmpruntsEnCours());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
		//3ème test (CT11.3)
		nbEmprunt = M.chercherDocument("5_3").getNbEmprunts();
		try {
			M.emprunter(nom,prenom,"5_3");
			//Change la date & Actualise la médiathèque
		} catch (Exception e) {
			//Si on catch une exception : echec de l'objectif
			System.out.println(e);
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("5_3").estEmprunte(),true);
		Assertions.assertEquals(verifNbEmprunt+1,c.getNbEmpruntsEnCours());
		Assertions.assertEquals(nbEmprunt+1,M.chercherDocument("5_3").getNbEmprunts());
		verifNbEmprunt = c.getNbEmpruntsEnCours();
	}
}
