package fr.ensiie.itic.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import fr.ensiie.itic.mediatheque.Mediatheque;
import fr.ensiie.itic.mediatheque.client.CategorieClient;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;

/* 
*
* Author : Guillaume Descroix
* Last Update : 05/12/21
*
*/
public class GenerationMediatheque {
	
	public static void MediathequeTestEmprunter() throws IOException {
		String nomMediatheque = "MediathequeTestEmprunter";
		// Supprime l'ancien fichier .data
		String nomFichier = nomMediatheque.concat(".data");
		Files.deleteIfExists(Paths.get(nomFichier));
		
		// Création de  la médiatheque de test
		Mediatheque M = new Mediatheque(nomMediatheque);
		try {
			//Initialisation des salles et rayons
			String presidents[] = {"Charles de Gaulle", "Georges Pompidou", "Valéry Giscard d'Estaing", 
					  "François Mitterrand", "Jacques Chirac", "Nicolas Sarkozy", 
					  "François Hollande", "Emmanuel Macron"};
			for(String president : presidents) {
				for(char lettre='A'; lettre <= 'Z'; lettre++) {
					M.ajouterLocalisation(president,Character.toString(lettre));
				}
			}
			
			//M.listerLocalisations();
			
			//Initialisation des genres
			String genres[] = {"Roman", "Article", "Biographie",
					"Fantaisie", "Fiction", "Littérature",
					"Policier", "Horreur", "Science-Fiction",
					"Aventure", "Documentaire", "Poésie",
					"Bande-Dessinée", "Manga", "Enseignement",
					"Divertissement", "Classique", "Variétés Françaises",
					"Variétés Internationales ", "Compilation", "Comédie"};
			for(String genre : genres) {
				M.ajouterGenre(genre);
			}
			//M.listerGenres();
			
			//Initialisation des documents
			Random rand = new Random();
			M.ajouterAudio("1", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterAudio("1_2", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterAudio("1_3", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterAudio("1_4", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterAudio("2", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterLivre("3", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterLivre("3_2", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterLivre("3_3", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterLivre("3_4", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterLivre("4", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterVideo("5", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			M.ajouterVideo("5_2", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			M.ajouterVideo("5_3", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			M.ajouterVideo("5_4", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			M.ajouterVideo("6", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			
			//Rend empruntable des documents
			M.metEmpruntable("1");
			M.metEmpruntable("1_2");
			M.metEmpruntable("1_3");
			M.metEmpruntable("1_4");
			M.metEmpruntable("3");
			M.metEmpruntable("3_2");
			M.metEmpruntable("3_3");
			M.metEmpruntable("3_4");
			M.metEmpruntable("5");
			M.metEmpruntable("5_2");
			M.metEmpruntable("5_3");
			M.metEmpruntable("5_4");
			
			//M.listerDocuments();
			
			// Ajout d'une catégorie de client à la médiatheque
			CategorieClient catEtudiant = M.ajouterCatClient("Etudiant", 3, 10.0, 1.0, 1.0, false);
			M.ajouterCatClient("Classic", 3, 30.0, 2.5, 2.5, false);
			
			//M.listerCatsClient();
			
			// Ajout de client
			M.inscrire("Descroix", "Guillaume", "91000 Evry", catEtudiant.getNom());
			M.inscrire("Doz", "Louka", "91000 Evry", catEtudiant.getNom());
			M.inscrire("Forget", "Nicolas", "91000 Evry", catEtudiant.getNom());
			M.inscrire("Delamotte", "Guillaume", "91000 Evry", catEtudiant.getNom());
			M.inscrire("Castel", "Aurélien", "91000 Evry", catEtudiant.getNom());
			
			//M.listerClients();
			
			// Sauvegarder la médiatheque
			M.saveToFile();
			
		} catch (OperationImpossible oi){
			System.out.println("Echec lors de la création de la médiathèque (" + nomMediatheque + ") : " + oi);
		}
	}
	
	public static void MediathequeTestRestituer() throws IOException {
		String nomMediatheque = "MediathequeTestRestituer";
		// Supprime l'ancien fichier .data
		String nomFichier = nomMediatheque.concat(".data");
		Files.deleteIfExists(Paths.get(nomFichier));
		
		// Création de  la médiatheque de test
		Mediatheque M = new Mediatheque(nomMediatheque);
		//TODO
		try {
			//Initialisation des salles et rayons
			String presidents[] = {"Charles de Gaulle", "Georges Pompidou", "Valéry Giscard d'Estaing", 
					  "François Mitterrand", "Jacques Chirac", "Nicolas Sarkozy", 
					  "François Hollande", "Emmanuel Macron"};
			for(String president : presidents) {
				for(char lettre='A'; lettre <= 'Z'; lettre++) {
					M.ajouterLocalisation(president,Character.toString(lettre));
				}
			}
			
			//M.listerLocalisations();
			
			//Initialisation des genres
			String genres[] = {"Roman", "Article", "Biographie",
					"Fantaisie", "Fiction", "Littérature",
					"Policier", "Horreur", "Science-Fiction",
					"Aventure", "Documentaire", "Poésie",
					"Bande-Dessinée", "Manga", "Enseignement",
					"Divertissement", "Classique", "Variétés Françaises",
					"Variétés Internationales ", "Compilation", "Comédie"};
			for(String genre : genres) {
				M.ajouterGenre(genre);
			}
			//M.listerGenres();
			
			//Initialisation des documents
			Random rand = new Random();
			M.ajouterAudio("1", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterAudio("1_2", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterAudio("1_3", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterAudio("1_4", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterAudio("2", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Homework", "Daft Punk", "1997", "Variétés Françaises", "Electro");
			M.ajouterLivre("3", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterLivre("3_2", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterLivre("3_3", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterLivre("3_4", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterLivre("4", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'), 
					"Les Fleurs du mal", "Charles Baudelaire", "1857", "Poésie", 288);
			M.ajouterVideo("5", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			M.ajouterVideo("5_2", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			M.ajouterVideo("5_3", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			M.ajouterVideo("5_4", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			M.ajouterVideo("6", presidents[rand.nextInt(presidents.length)], Character.toString(rand.nextInt(26) + 'A'),
					"C'est pas sorcier -Les secrets du chocolat", "France 3", "2013", "Documentaire", 19, "None");
			
			//Rend empruntable des documents
			M.metEmpruntable("1");
			M.metEmpruntable("1_2");
			M.metEmpruntable("1_3");
			M.metEmpruntable("1_4");
			M.metEmpruntable("3");
			M.metEmpruntable("3_2");
			M.metEmpruntable("3_3");
			M.metEmpruntable("3_4");
			M.metEmpruntable("5");
			M.metEmpruntable("5_2");
			M.metEmpruntable("5_3");
			M.metEmpruntable("5_4");
			
			//M.listerDocuments();
			
			// Ajout d'une catégorie de client à la médiatheque
			CategorieClient catEtudiant = M.ajouterCatClient("Etudiant", 3, 10.0, 1.0, 1.0, false);
			M.ajouterCatClient("Classic", 5, 30.0, 2.5, 2.5, false);
			
			//M.listerCatsClient();
			
			// Ajout de client
			M.inscrire("Descroix", "Guillaume", "91000 Evry", catEtudiant.getNom());
			M.inscrire("Doz", "Louka", "91000 Evry", catEtudiant.getNom());
			M.inscrire("Forget", "Nicolas", "91000 Evry", catEtudiant.getNom());
			M.inscrire("Delamotte", "Guillaume", "91000 Evry", catEtudiant.getNom());
			M.inscrire("Castel", "Aurélien", "91000 Evry", catEtudiant.getNom());
			
			//M.listerClients();
			
			// Emprunt Client (Testé en amont avec TestEmprunt)
			M.emprunter("Descroix", "Guillaume","1");
			M.emprunter("Descroix", "Guillaume","3");
			M.emprunter("Descroix", "Guillaume","5");
			// Sauvegarder la médiatheque
			M.saveToFile();
			
		} catch (OperationImpossible oi){
			System.out.println("Echec lors de la création de la médiathèque (" + nomMediatheque + ") : " + oi);
		}
	}
	
	public static void main(String[] args) throws IOException {
		//Génération des médiathèques
		MediathequeTestEmprunter();
		MediathequeTestRestituer();
	}

}
