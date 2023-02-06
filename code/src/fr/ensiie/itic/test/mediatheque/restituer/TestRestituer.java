package fr.ensiie.itic.test.mediatheque.restituer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ensiie.itic.mediatheque.Mediatheque;
import fr.ensiie.itic.mediatheque.client.Client;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;
import fr.ensiie.itic.test.GenerationMediatheque;

/* 
 *
 * Author : Guillaume Descroix
 * Last Update : 05/12/21
 *
 */
public class TestRestituer {
	private Mediatheque M;

	// Creation d'un environnement de test
	@BeforeEach
	public void creerEnvironnementTest() {
		try {
			// Génération de la médiatheque de test
			GenerationMediatheque.MediathequeTestRestituer();
			// Récupération de la médiatheque de test
			M = new Mediatheque("MediathequeTestRestituer");
		} catch (Exception e) {
			fail();
		}
	}
	
	// Objectif de test : (CT15) Vérifier l'impossibilité de rendre un article inexistant
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void restituerArcticleInexistant() {
		int nbEmpruntCli = M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues();
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Descroix","Guillaume","codeArticleInexistant");
			}
		);
		Assertions.assertEquals(nbEmpruntCli,M.chercherClient("Descroix","Guillaume").getNbEmpruntsEffectues());
	}
	
	/* Objectif de test :
	 *					1 - (CT16) Vérifier l'impossibilité de restituer un article avec un nom null
	 *					2 - (CT17) Vérifier l'impossibilité de restituer un article avec un prénom null
	 *					3 - (CT18) Vérifier l'impossibilité de restituer un article avec un numéro null
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - levée d'une exception du type OperationImpossible
	 *					3 - levée d'une exception du type OperationImpossible
	 */
	@Test
	public void restituerArcticleAvecValNull() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer(null,"Guillaume","1");
			}
		);
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Descroix",null,"1");
			}
		);
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Descroix","Guillaume",null);
			}
		);
	}
	
	/* Objectif de test :
	 *					1 - (CT19.2) Vérifier l'impossibilité de restituer un article audio non emprunter (empruntable)
	 *					2 - (CT19.1) Vérifier l'impossibilité de restituer un article livre non emprunter (empruntable)
	 *					3 - (CT19.3) Vérifier l'impossibilité de restituer un article vidéo non emprunter (empruntable)
	 *					4 - (CT20.2) Vérifier l'impossibilité de restituer un article audio non emprunter (non empruntable)
	 *					5 - (CT20.1) Vérifier l'impossibilité de restituer un article livre non emprunter (non empruntable)
	 *					6 - (CT20.3) Vérifier l'impossibilité de restituer un article vidéo non emprunter (non empruntable)
	 * Resultat attendu : 
	 * 					1 - levée d'une exception du type OperationImpossible
	 * 					2 - levée d'une exception du type OperationImpossible
	 *					3 - levée d'une exception du type OperationImpossible
	 * 					4 - levée d'une exception du type OperationImpossible
	 * 					5 - levée d'une exception du type OperationImpossible
	 *					6 - levée d'une exception du type OperationImpossible
	 */
	@Test
	public void restituerArcticleNonEmprunte() {
		String nom = "Descroix";
		String prenom = "Guillaume";
		
		Client c = M.chercherClient(nom, prenom);
		int verifNbEmprunt = c.getNbEmpruntsEnCours();
		
		//1er test (CT19.2)
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer(nom, prenom,"1_2");
			}
		);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		//2ème test (CT19.1)
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer(nom, prenom,"3_2");
			}
		);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		//3ème test (CT19.3)
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer(nom, prenom,"5_2");
			}
		);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		//4ème test (CT20.2)
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Descroix","Guillaume","2");
			}
		);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		//5ème test (CT20.1)
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Descroix","Guillaume","4");
			}
		);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
		//6ème test (CT20.3)
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Descroix","Guillaume","6");
			}
		);
		Assertions.assertEquals(verifNbEmprunt,c.getNbEmpruntsEnCours());
	}
	
	// Objectif de test : (CT21.2) Vérifier l'impossibilité de rendre un article audio avec utilisateur inconnu
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void restituerArcticleAudioClientInconnu() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("NomInconnu","PrenomInconnu","1");
			}
		);
		Assertions.assertEquals(M.chercherDocument("1").estEmprunte(),true);
	}
	
	// Objectif de test : (CT21.1) Vérifier l'impossibilité de rendre un article livre avec utilisateur inconnu
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void restituerArcticleLivreClientInconnu() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("NomInconnu","PrenomInconnu","3");
			}
		);
		Assertions.assertEquals(M.chercherDocument("3").estEmprunte(),true);
	}
	
	// Objectif de test : (CT21.3) Vérifier l'impossibilité de rendre un article vidéo avec utilisateur inconnu
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void restituerArcticleVideoClientInconnu() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("NomInconnu","PrenomInconnu","5");
			}
		);
		Assertions.assertEquals(M.chercherDocument("5").estEmprunte(),true);
	}
	
	// Objectif de test : (CT22.2) Vérifier l'impossibilité de rendre un article audio avec un mauvais client
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void restituerArcticleAudioMauvaisClient() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Doz","Louka","1");
			}
		);
		Assertions.assertEquals(M.chercherDocument("1").estEmprunte(),true);
	}
	
	// Objectif de test : (CT22.1) Vérifier l'impossibilité de rendre un article livre avec un mauvais client
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void restituerArcticleLivreMauvaisClient() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Doz","Louka","3");
			}
		);
		Assertions.assertEquals(M.chercherDocument("3").estEmprunte(),true);
	}
	
	// Objectif de test : (CT22.3) Vérifier l'impossibilité de rendre un article vidéo avec un mauvais client
	// Resultat attendu : levée d'une exception du type OperationImpossible
	@Test
	public void restituerArcticleVideoMauvaisClient() {
		Assertions.assertThrows(OperationImpossible.class, () -> {
				M.restituer("Doz","Louka","5");
			}
		);
		Assertions.assertEquals(M.chercherDocument("5").estEmprunte(),true);
	}
}
