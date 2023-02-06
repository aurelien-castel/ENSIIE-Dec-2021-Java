package fr.ensiie.itic.mediatheque;

import java.io.Serializable;
import java.util.Date;

import fr.ensiie.itic.mediatheque.client.Client;
import fr.ensiie.itic.mediatheque.document.Document;
import fr.ensiie.itic.mediatheque.util.Datutil;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;

/**
 * La classe FicheEmprunt gere les fiches d'emprunts de la mediatheque. Il y a
 * une fiche par document emprunte et par client emprunteur.
 */
public final class FicheEmprunt implements Serializable {
	/** serial version. */
	private static final long serialVersionUID = 3L;
	/** Emprunteur. */
	private Client client;
	/** Document emprunte. */
	private Document document;

	// Attributs
	/** Date de l'emprunt. */
	private Date dateEmprunt;
	/** Date limite de restitution. */
	private Date dateLimite;
	/** Indicateur d'emprunt depasse. */
	private boolean depasse;
	/** date de rappel si emprunt depasse. */
	private Date dateRappel;

	/**
	 * Nombre d'emprunts total de documents de la mediatheque.
	 */
	private static int nbEmpruntsTotal = 0;

	// Les methodes
	/**
	 * Constructeur.
	 * 
	 * @param d
	 *            document associe
	 * @param c
	 *            client associe
	 * @throws OperationImpossible
	 *             en relai de emprunter sur document et client
	 */
	public FicheEmprunt(final Client c, final Document d)
			throws OperationImpossible {
		client = c;
		document = d;
		dateEmprunt = Datutil.dateDuJour();
		int duree = document.dureeEmprunt();
		dateLimite = client.dateRetour(dateEmprunt, duree);
		dateRappel = null;
		depasse = false;
		document.emprunter();
		client.emprunter(this);
		nbEmpruntsTotal++;
		System.out.println("\tTarif = " + getTarifEmprunt() + " euros");
		assert invariant();
	}

	/**
	 * invariant de la classe.
	 * 
	 * @return vrai si l'invariant est respecte.
	 */
	public boolean invariant() {
		return (depasse == (Datutil.dateDuJour().after(dateLimite)))
				&& ((dateRappel == null) || (dateRappel.after(dateLimite)))
				&& (nbEmpruntsTotal >= 0);
	}

	/**
	 * <TT>verifier</TT> teste si la date de fin de prêt est depassée.
	 * 
	 * @return booleen si depasse pour la première fois.
	 */
	public boolean verifier() {
		if (depasse) {
			return false;
		} else {
			Date dateActuelle = Datutil.dateDuJour();
			if (dateLimite.before(dateActuelle)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Le client est marque ; la lettre de rappel est cree.
	 * 
	 * @throws OperationImpossible
	 *             si deja depasse.
	 */
	public void premierRappel() throws OperationImpossible {
		if (depasse) {
			throw new OperationImpossible("Fiche Emprunt deja en retard");
		}
		depasse = true;
		client.marquer();
		dateRappel = Datutil.dateDuJour();
		assert invariant();
	}

	/**
	 * <TT>relancer</TT> verifie si l'emprunt est depasse, auquel cas il faudra
	 * relancer le client retardataire.
	 * 
	 * @throws OperationImpossible
	 *             si emprunt non en retard
	 */
	public void relancer() throws OperationImpossible {
		Date dateActuelle = Datutil.dateDuJour();
		if (!depasse) {
			throw new OperationImpossible(
					"Relancer sur FicheEmprunt non en retard");
		}
		Date dateRelance = Datutil.addDate(dateRappel, Datutil.DAYSINWEEK);
		if (dateRelance.before(dateActuelle)) {
			dateRappel = dateActuelle;
		}
		assert invariant();
	}

	/**
	 * modifie le client associe a l'emprunt pour permettre les modifications de
	 * nom et prenom dans la table de hachage.
	 * 
	 * @param newClient le nouveau client
	 */
	void modifierClient(final Client newClient) {
		client = newClient;
	}

	/**
	 * <TT>correspond</TT> verifie que l'emprunt correspond au document et au
	 * client en retournant vrai.
	 * 
	 * @param cli
	 *            Emprunteur
	 * @param doc
	 *            Document emprunte
	 * @return true si l'emprunt correspond
	 */
	public boolean correspond(final Client cli, final Document doc) {
		return (client.equals(cli) && document.equals(doc));
	}

	/**
	 * <TT>restituer</TT> est lancee lors de la restitution d'un document. Elle
	 * appelle les methodes de restitution sur le document et le client.
	 * 
	 * @throws OperationImpossible
	 *             en relai de restituer sur document et client.
	 */
	public void restituer() throws OperationImpossible {
		client.restituer(this);
		document.restituer();
		assert invariant();
	}

	/**
	 * retourne le client associe a la fiche.
	 * 
	 * @return client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * retourne le document associe à la fiche.
	 * 
	 * @return document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * retourne la date d'emprunt.
	 * 
	 * @return date d'emprunt
	 */
	public Date getDateEmprunt() {
		return dateEmprunt;
	}

	/**
	 * retourne la date limite.
	 * 
	 * @return date limite
	 */
	public Date getDateLimite() {
		return dateLimite;
	}

	/**
	 * retourne la valeur du booleen depasse.
	 * 
	 * @return depasse
	 */
	public boolean getDepasse() {
		return depasse;
	}

	/**
	 * retourne la duree d'emprunt en jour.
	 * 
	 * @return duree d'emprunt
	 */
	public int getDureeEmprunt() {
		return (int) ((dateLimite.getTime() - dateEmprunt.getTime())
				/ (Datutil.MILLISINSEC * Datutil.SECSINMIN * Datutil.MINSINHOUR
						* Datutil.HOURSINDAY));
	}

	/**
	 * retourne le tarif d'emprunt calcule à partir du tarifnominal du document
	 * et du type de client.
	 * 
	 * @return tarif
	 */
	public double getTarifEmprunt() {
		double tarifNominal = document.tarifEmprunt();
		return client.sommeDue(tarifNominal);
	}

	/**
	 * changementCategorie est appele apres un changement de categorie du client
	 * calcule si l'emprunt est en retard ou non.
	 * 
	 * @return boolean true si l'emprunt etait depasse
	 * @throws OperationImpossible
	 *             en relai de verifier
	 */
	public boolean changementCategorie() throws OperationImpossible {
		boolean oldDepasse = depasse;
		if (depasse) {
			depasse = false;
		}
		int duree = document.dureeEmprunt();
		dateLimite = client.dateRetour(dateEmprunt, duree);
		verifier();
		return oldDepasse;
	}

	/**
	 * <TT>toString</TT> affiche les caracteristiques de l'emprunt.
	 * 
	 * @return Caracteristiques de l'emprunt
	 */
	@Override
	public String toString() {
		String s = "\"" + document.getCode() + "\" par \"" + client.getNom()
				+ "\" le " + Datutil.dateToString(dateEmprunt) + " pour le "
				+ Datutil.dateToString(dateLimite);
		if (depasse) {
			s = s + " (depasse)";
		}
		return s;
	}

	/**
	 * <TT>afficherStatistiques</TT> affiche le nombre total d'emprunts.
	 */
	public static void afficherStatistiques() {
		System.out.println("Nombre total d'emprunts = " + nbEmpruntsTotal);
	}
}
