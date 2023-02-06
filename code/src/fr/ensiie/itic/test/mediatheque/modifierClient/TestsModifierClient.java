package fr.ensiie.itic.test.mediatheque.modifierClient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ensiie.itic.mediatheque.Mediatheque;
import fr.ensiie.itic.mediatheque.client.CategorieClient;
import fr.ensiie.itic.mediatheque.client.ClefClient;
import fr.ensiie.itic.mediatheque.client.Client;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;

/**
 * Tests structurels de la méthode {@see Mediatheque#modifierClient(fr.ensiie.itic.mediatheque.client.Client, String, String, String, String, int)}
 * 
 * @author Louka DOZ
 */
class TestsModifierClient {
	
	private Client client;
	private Client clientWrongFirstName;
	private Client clientWrongLastName;
	private Mediatheque mediatheque;
	private CategorieClient category;
	private CategorieClient categoryWithoutCode;
	private CategorieClient categoryWithCode;
	
	/**
	 * Initialise {@link TestsModifierClient#client} et {@link TestsModifierClient#mediatheque}
	 * @throws OperationImpossible en cas d'echec d'initialisation du client
	 */
	@BeforeEach
	void init() {
		this.category = new CategorieClient("catnom0", 1, 1, 1, 1, false);
		this.categoryWithoutCode = new CategorieClient("catnom1", 1, 1, 1, 1, false);
		this.categoryWithCode = new CategorieClient("catnom2", 1, 1, 1, 1, true);
		
		try {
			this.client = new Client("nom0", "prenom0", "adresse0", this.category);
			this.clientWrongFirstName = new Client("nom0", "prenom1", "adresse0", this.category);
			this.clientWrongLastName = new Client("nom1", "prenom0", "adresse0", this.category);
			
			this.mediatheque = new Mediatheque("mediatheque");
			this.mediatheque.ajouterCatClient(
					this.category.getNom(), 
					this.category.getNbEmpruntMax(), 
					this.category.getCotisation(), 
					this.category.getCoefDuree(), 
					this.category.getCoefTarif(), 
					this.category.getCodeReducUtilise()
			);
			this.mediatheque.ajouterCatClient(
					this.categoryWithoutCode.getNom(), 
					this.categoryWithoutCode.getNbEmpruntMax(), 
					this.categoryWithoutCode.getCotisation(), 
					this.categoryWithoutCode.getCoefDuree(), 
					this.categoryWithoutCode.getCoefTarif(), 
					this.categoryWithoutCode.getCodeReducUtilise()
			);
			this.mediatheque.ajouterCatClient(
					this.categoryWithCode.getNom(), 
					this.categoryWithCode.getNbEmpruntMax(), 
					this.categoryWithCode.getCotisation(), 
					this.categoryWithCode.getCoefDuree(), 
					this.categoryWithCode.getCoefTarif(), 
					this.categoryWithCode.getCodeReducUtilise()
			);
			this.mediatheque.inscrire(
					this.client.getNom(), 
					this.client.getPrenom(), 
					this.client.getAdresse(), 
					this.client.getCategorie().getNom()
			);
		} catch (OperationImpossible e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test 1.1 : Client vaut null
	 * Résultat attendu : Exception NullPointerException
	 */
	void ClientIsNull() {
		boolean ok = false;
		
		try {
			this.mediatheque.modifierClient(null, "nom0", "prenom0", "adresse0", "catnom0", 0);
		} catch(NullPointerException e) {
			ok = true;
		}  catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertTrue(ok);
	}

	@Test
	/**
	 * Test 1.2 : Couple nom/prénom non présents dans la médiathèque (nom incorrect)
	 * Résultat attendu : Exception OperationImpossible
	 */
	void LastNameIsWrong() {
		boolean ok = false;
		
		try {
			this.mediatheque.modifierClient(this.clientWrongLastName, "nom0", "prenom0", "adresse0", "catnom0", 0);
		} catch(OperationImpossible e) {
			ok = true;
		}  catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertTrue(ok);
	}

	@Test
	/**
	 * Test 1.3 : Couple nom/prénom non présents dans la médiathèque (prénom incorrect)
	 * Résultat attendu : Exception OperationImpossible
	 */
	void FirstNameIsWrong() {
		boolean ok = false;
		
		try {
			this.mediatheque.modifierClient(this.clientWrongFirstName, "nom0", "prenom0", "adresse0", "catnom0", 0);
		} catch(OperationImpossible e) {
			ok = true;
		}  catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertTrue(ok);
	}

	@Test
	/**
	 * Test 2.1 : Changement adresse
	 * Résultat attendu : Adresse client = « adresse1 »
	 */
	void ChangeAddress() {
		try {
			this.mediatheque.modifierClient(this.client, "nom0", "prenom0", "adresse1", "catnom0", 0);
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertEquals(this.client.getAdresse(), "adresse1");
	}

	@Test
	/**
	 * Test 3.1 : Changement nom
	 * Résultat attendu : Nom client = « nom1 » et hash changé
	 */
	void ChangeFirstName() {
		ClefClient oldHash = new ClefClient(this.client.getNom(), this.client.getPrenom());
		
		try {
			this.mediatheque.modifierClient(this.client, "nom1", "prenom0", "adresse0", "catnom0", 0);
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}

		assertEquals(this.client.getNom(), "nom1");
		assertFalse(oldHash.equals(new ClefClient(this.client.getNom(), this.client.getPrenom())));
	}

	@Test
	/**
	 * Test 4.1 : Changement prénom
	 * Résultat attendu : Prénom client = « prenom1 » et hash changé
	 */
	void ChangeLastName() {
		ClefClient oldHash = new ClefClient(this.client.getNom(), this.client.getPrenom());
		
		try {
			this.mediatheque.modifierClient(this.client, "nom0", "prenom1", "adresse0", "catnom0", 0);
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertEquals(this.client.getPrenom(), "prenom1");
		assertFalse(oldHash.equals(new ClefClient(this.client.getNom(), this.client.getPrenom())));
	}

	@Test
	/**
	 * Test 5.1 : Changement catégorie (nouvelle catégorie sans code de réduction)
	 * Résultat attendu : Catégorie client = « catnom1 » et code de réduction actif pour la catégorie client = false
	 */
	void ChangeCategoryWithoutReductionCode() {		
		try {
			this.mediatheque.modifierClient(this.client, "nom0", "prenom0", "adresse0", "catnom1", 0);
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertEquals(this.client.getCategorie(), this.categoryWithoutCode);
		assertFalse(this.client.getCategorie().getCodeReducUtilise());
	}

	@Test
	/**
	 * Test 5.2 : Changement catégorie (nouvelle catégorie avec code de réduction)
	 * Résultat attendu : Catégorie client = « catnom2 » et code de réduction actif pour la catégorie client = true
	 */
	void ChangeCategoryWithReductionCode() {
		try {
			this.mediatheque.modifierClient(this.client, "nom0", "prenom0", "adresse0", "catnom2", 0);
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertEquals(this.client.getCategorie(), this.categoryWithCode);
		assertTrue(this.client.getCategorie().getCodeReducUtilise());
	}

	@Test
	/**
	 * Test 5.3 : Changement avec le même nom de catégorie
	 * Résultat attendu : Catégorie client = « catnom0 » et code de réduction actif pour la catégorie client = false
	 */
	void SameCategory() {
		try {
			this.mediatheque.modifierClient(this.client, "nom0", "prenom0", "adresse0", "catnom0", 0);
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertEquals(this.client.getCategorie(), this.category);
		assertFalse(this.client.getCategorie().getCodeReducUtilise());
	}

	@Test
	/**
	 * Test 5.4 : Catégorie non présente dans la médiathèque 
	 * Résultat attendu : Exception NullPointerException
	 */
	void CategoryNameIsWrong() {
		boolean ok = false;
		
		try {
			this.mediatheque.modifierClient(null, "nom0", "prenom0", "adresse0", "catnom3", 0);
		} catch(NullPointerException e) {
			ok = true;
		}  catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
			fail();
		}
		
		assertTrue(ok);
	}
}
