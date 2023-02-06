package fr.ensiie.itic.test.mediatheque.consultable;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
//import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ensiie.itic.mediatheque.Mediatheque;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;

public class TestConsultable {
	
	private Mediatheque M;

	// Creation d'un environnement de test
	@BeforeEach
	public void creerEnvironnementTest() {
		try {
			// Récupération de la médiatheque
			// /!\ATTENTION/!\ : Penser à bien générer le fichier MediathequeTestEmprunter.data (cf GenerationMediatheque.java)
			M = new Mediatheque("MediathequeTestEmprunter");
			if(M.chercherClient("Descroix", "Guillaume") == null || M.chercherClient("Delamotte", "Guillaume") == null)
				fail();
		} catch (Exception e) {
			fail();
		}
	}
	// Code CT1
	// Objectif de test : Vérifier article inexistant ne peut être mis consultable
	// Resultat attendu : pas d'exception
	@Test
	public void mettreConsultableArticleInexistant() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
			M.metConsultable("articleInconnu");
			}
		);
	}
	
	// Code CT2
	// Objectif de test : Vérifier article null ne peut être mis consultable
	// Resultat attendu : pas d'exception
	@Test
	public void mettreConsultableArticleNull() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
			M.metConsultable(null);
			}
		);
	}
	
	
	// Code: CT3 1
	// Objectif de test : Vérifier livre empruntable peut être mis consultable
	// Resultat attendu : pas d'exception
	@Test
	public void mettreConsultableLivreEmpruntable() {
		try {
			M.metConsultable("3");
		} catch (OperationImpossible oi) {
			//si une exception de type OperationImpossible est levee, le test reussit
			oi.printStackTrace();
			fail();
		}
		Assertions.assertEquals(M.chercherDocument("3").estEmpruntable(),false);
	}
	
	// Code CT4 1
	// Objectif de test : Vérifier livre consultable ne peut pas être mis consultable
	// Resultat attendu : pas d'exception
	@Test
	public void mettreConsultableLivreConsultable() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
			M.metConsultable("4");
			}
		);
		Assertions.assertEquals(M.chercherDocument("4").estEmpruntable(),false);
	}
	
	// Code CT5 1
	// Objectif de test : Vérifier livre consultable ne peut pas être mis consultable
	// Resultat attendu : pas d'exception
	@Test
	public void mettreConsultableLivreEmprunter() {
		try {
			//Emprunter le livre (déjà testé dans une autre classe
			M.emprunter("Descroix","Guillaume","3");
		} catch(OperationImpossible oi){
			oi.printStackTrace();
			fail();
		}
		// Test CT5 1
		Assertions.assertThrows(OperationImpossible.class, () -> {
			M.metConsultable("3");
			}
		);
		Assertions.assertEquals(M.chercherDocument("3").estEmprunte(),true);
		Assertions.assertEquals(M.chercherDocument("3").estEmpruntable(),true);
	}
	
	/**********************/
	
	// Code: CT3 2
		// Objectif de test : Vérifier audio empruntable peut être mis consultable
		// Resultat attendu : pas d'exception
		@Test
		public void mettreConsultableAudioEmpruntable() {
			try {
				M.metConsultable("1");
			} catch (OperationImpossible oi) {
				//si une exception de type OperationImpossible est levee, le test reussit
				oi.printStackTrace();
				fail();
			}
			Assertions.assertEquals(M.chercherDocument("1").estEmpruntable(),false);
		}
		
		// Code CT4 2
		// Objectif de test : Vérifier audio empruntable peut être mis consultable
		// Resultat attendu : pas d'exception
		@Test
		public void mettreConsultableAudioConsultable() {
			Assertions.assertThrows(OperationImpossible.class, () -> {
				M.metConsultable("2");
				}
			);
			Assertions.assertEquals(M.chercherDocument("2").estEmpruntable(),false);
		}
		
		// Code CT5 2
		// Objectif de test : Vérifier audio consultable ne peut pas être mis consultable
		// Resultat attendu : pas d'exception
		@Test
		public void mettreConsultableAudioEmprunter() {
			try {
				//Emprunter le livre (déjà testé dans une autre classe
				M.emprunter("Descroix","Guillaume","1");
			} catch(OperationImpossible oi){
				oi.printStackTrace();
				fail();
			}
			// Test CT5 1
			Assertions.assertThrows(OperationImpossible.class, () -> {
				M.metConsultable("1");
				}
			);
			Assertions.assertEquals(M.chercherDocument("1").estEmprunte(),true);
			Assertions.assertEquals(M.chercherDocument("1").estEmpruntable(),true);
		}

	/**********************/
		
		// Code: CT3 3
		// Objectif de test : Vérifier video empruntable peut être mis consultable
		// Resultat attendu : pas d'exception
		@Test
		public void mettreConsultableVideoEmpruntable() {
			try {
				M.metConsultable("5");
			} catch (OperationImpossible oi) {
				//si une exception de type OperationImpossible est levee, le test reussit
				oi.printStackTrace();
				fail();
			}
			Assertions.assertEquals(M.chercherDocument("5").estEmpruntable(),false);
		}
		
		// Code CT4 3
		// Objectif de test : Vérifier video consultable ne peut pas être mis consultable
		// Resultat attendu : pas d'exception
		@Test
		public void mettreConsultableVideoConsultable() {
			Assertions.assertThrows(OperationImpossible.class, () -> {
				M.metConsultable("6");
				}
			);
			Assertions.assertEquals(M.chercherDocument("6").estEmpruntable(),false);
		}
		
		// Code CT5 3
		// Objectif de test : Vérifier video consultable ne peut pas être mis consultable
		// Resultat attendu : pas d'exception
		@Test
		public void mettreConsultableVideoEmprunter() {
			try {
				//Emprunter le livre (déjà testé dans une autre classe
				M.emprunter("Descroix","Guillaume","5");
			} catch(OperationImpossible oi){
				oi.printStackTrace();
				fail();
			}
			// Test CT5 1
			Assertions.assertThrows(OperationImpossible.class, () -> {
				M.metConsultable("5");
				}
			);
			Assertions.assertEquals(M.chercherDocument("5").estEmprunte(),true);
			Assertions.assertEquals(M.chercherDocument("5").estEmpruntable(),true);
		}
}


