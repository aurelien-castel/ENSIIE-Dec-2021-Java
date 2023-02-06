package fr.ensiie.itic.mediatheque.document;

import fr.ensiie.itic.mediatheque.Genre;
import fr.ensiie.itic.mediatheque.Localisation;
import fr.ensiie.itic.mediatheque.util.Datutil;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;

/**
 * La classe <code>Livre</code> gere les documents de type livre. Ils possedent
 * un nombre de pages, une duree de pret de 6 semaines et un tarif de pret de
 * 0.5 Euros.
 */
public final class Livre extends Document {
	/** serial number. */
	private static final long serialVersionUID = 4L;

	/**
	 * Nombre de pages, format libre.
	 */
	private int nombrePages;

	/**
	 * Duree de pret en nombre de jours.
	 */
	public static final int DUREE = 6 * Datutil.DAYSINWEEK;

	/**
	 * Tarif du pret.
	 */
	public static final double TARIF = 0.5;

	/**
	 * Nombre d'emprunts total de Livre.
	 */
	private static int nbEmpruntsTotal = 0;

	// Les methodes

	/**
	 * Constructeur de Livre avec les attributs valorises. Par defaut, le livre
	 * n'est pas empruntable.
	 * 
	 * @param code
	 *            Code du document
	 * @param localisation
	 *            Localisation du document
	 * @param titre
	 *            Titre du document
	 * @param auteur
	 *            Auteur du document
	 * @param annee
	 *            Annee de sortie du document
	 * @param genre
	 *            Genre du document
	 * @param nbPages
	 *            Nombre de pages du livre
	 * @throws OperationImpossible
	 *             en relai du constructeur de document
	 */
	public Livre(final String code, final Localisation localisation,
			final String titre, final String auteur, final String annee,
			final Genre genre, final int nbPages) throws OperationImpossible {
		super(code, localisation, titre, auteur, annee, genre);
		if (nbPages < 0) {
			throw new OperationImpossible("Ctr Livre nombrePage = " + nbPages);
		}
		this.nombrePages = nbPages;
		assert invariantLivre();
	}

	/**
	 * Safety property - nombre de pages must be positive.
	 * 
	 * @return if document in safe state (i.e. nombrePage&gt;0)
	 */
	public boolean invariantLivre() {
		return nombrePages > 0 && super.invariant();
	}

	/**
	 * retourne le nombre d'emprunts (statistique) de la classe.
	 * 
	 * @return Nombre d'emprunts total
	 */
	public static int getNbEmpruntsTotal() {
		return nbEmpruntsTotal;
	}

	/**
	 * <TT>emprunter</TT> est appelee lors de l'emprunt d'un document. Les
	 * statistiques sont mises a jour.
	 * 
	 * @exception OperationImpossible
	 *                relai de la méthode de la classe parente
	 */
	@Override
	public void emprunter() throws OperationImpossible {
		super.emprunter();
		nbEmpruntsTotal++;
		assert invariant();
	}
	// Methodes de l'interface Empruntable

	/**
	 * <TT>dureeEmprunt</TT> retourne la duree nominale de pret du document en
	 * nombre de jours.
	 * 
	 * @return Duree de pret
	 */
	@Override
	public int dureeEmprunt() {
		return DUREE;
	}

	/**
	 * <TT>tarifEmprunt</TT> retourne le tarif nominal du pret du document.
	 * 
	 * @return Tarif du pret
	 */
	@Override
	public double tarifEmprunt() {
		return TARIF;
	}

	/**
	 * <TT>toString</TT> affiche les caracteristiques du Livre.
	 * 
	 * @return Caracteristiques d'un livre
	 */
	@Override
	public String toString() {
		return "[Livre] " + super.toString() + " " + nombrePages;
	}
}
