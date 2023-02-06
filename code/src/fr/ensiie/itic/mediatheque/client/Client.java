package fr.ensiie.itic.mediatheque.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ensiie.itic.mediatheque.FicheEmprunt;
import fr.ensiie.itic.mediatheque.util.Datutil;
import fr.ensiie.itic.mediatheque.util.OperationImpossible;

/**
 * La classe <code>Client</code> gere tous les clients de la mediatheque. Ils
 * peuvent etre de differentes categorie. Ils possedent tous un nom, un prenom,
 * une adresse et des informations statistiques d'emprunt.
 */
public final class Client implements Serializable {
	/** serial number. */
	private static final long serialVersionUID = 2L;
	/** Nom du client. */
	private String nom;

	/** Prenom du client. */
	private String prenom;

	/** Adresse du client, format libre. */
	private String adresse;

	/** Nombre de documents empruntes par le client. */
	private int nbEmpruntsEnCours = 0;

	/**
	 * Nombre de documents non restitues dans les delais par le client.
	 */
	private int nbEmpruntsDepasses = 0;

	/**
	 * Statistique sur le nombre d'emprunts effectues par le client.
	 */
	private int nbEmpruntsEffectues = 0;

	/**
	 * Categorie du client.
	 */
	private CategorieClient catClient = null;

	/**
	 * Nombre total d'emprunts de tous les clients.
	 */
	private static int nbEmpruntsTotal = 0;
	/** Attributs pour les abonnes date de renouvellement. */
	private Date dateRenouvellement;
	/**
	 * Date de l'inscription : la verification des droits a la reduction est
	 * annuelle.
	 */
	private Date dateInscription;

	/**
	 * Code de reduction. Ce code n'est utilise que pour certaines catégories de
	 * clients.
	 */
	private int codeReduction = 0;
	/**
	 * Liste des emprunts en cours.
	 */
	private List<FicheEmprunt> lesEmprunts;
	// les methodes

	/**
	 * Constructeur de client sans code de réduction.
	 * 
	 * @param n   Nom du client
	 * @param p   Prenom du client
	 * @param a   Adresse du client
	 * @param cat Categorie du client
	 * @exception OperationImpossible si un parameter est null.
	 */
	public Client(final String n, final String p, final String a, final CategorieClient cat)
			throws OperationImpossible {
		initAttr(n, p, a, cat);
		if (cat.getCodeReducUtilise()) {
			throw new OperationImpossible("Call with client type " + cat.getNom() + " and no reduction code");
		}
		assert invariant();
	}

	/**
	 * Constructeur de client avec code de réduction.
	 * 
	 * @param n    Nom du client
	 * @param p    Prenom du client
	 * @param a    Adresse du client
	 * @param catC Categorie du client
	 * @param code code de reduction du client
	 * @exception OperationImpossible si attribut non initialise
	 */
	public Client(final String n, final String p, final String a, final CategorieClient catC, final int code)
			throws OperationImpossible {
		initAttr(n, p, a, catC);
		if (!catC.getCodeReducUtilise()) {
			throw new OperationImpossible("Call with client type " + catC.getNom() + " and reduction code");
		}
		if (code <= 0) {
			throw new OperationImpossible("le code de reduction doit etre positif");
		}
		this.codeReduction = code;
		assert invariant();
	}

	/**
	 * un client ne peut avoir plus d'emprunt en retard qu'il n'a d'emprunts. un
	 * client ne peut avoir plus d'emprunt que sa categorie ne le lui permet.
	 * 
	 * @return true si les deux conditions ci-dessus sont bonnes.
	 */
	public boolean invariant() {
		return nom != null && !nom.equals("") && prenom != null && !prenom.equals("") && (nbEmpruntsDepasses >= 0)
				&& adresse != null && !adresse.equals("") && (nbEmpruntsDepasses <= nbEmpruntsEnCours)
				&& (catClient != null) && lesEmprunts != null && (codeReduction >= 0);
	}

	/**
	 * Internal method to initialize attributes commons to all constructors.
	 * 
	 * @param n    Nom du client
	 * @param p    Prenom du client
	 * @param a    Adresse du client
	 * @param catC Categorie du client
	 * @exception OperationImpossible si attribut non initialise
	 */
	private void initAttr(final String n, final String p, final String a, final CategorieClient catC)
			throws OperationImpossible {
		if (n == null || n.equals("") || p == null || p.equals("") || a == null || a.equals("") || catC == null
				|| catC.equals("")) {
			throw new OperationImpossible("Parametre String null ou vide  dans constructeur client :" + n + " " + p
					+ " " + " " + a + " " + catC);
		}
		this.nom = n;
		this.prenom = p;
		this.adresse = a;
		this.catClient = catC;
		dateInscription = Datutil.dateDuJour();
		dateRenouvellement = Datutil.addDate(dateInscription, Datutil.DAYSINYEAR);
		lesEmprunts = new ArrayList<FicheEmprunt>();
	}

	/**
	 * Nom du client.
	 * 
	 * @return Nom du client
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Prenom du client.
	 * 
	 * @return Prenom du client
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Adresse du client.
	 * 
	 * @return Adresse du client
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * Nombre d'emprunts en cours.
	 * 
	 * @return le nombre d'emprunts en cours
	 */
	public int getNbEmpruntsEnCours() {
		return nbEmpruntsEnCours;
	}

	/**
	 * Nombre d'emprunts effectues.
	 * 
	 * @return nombre d'emprunts effectues
	 */
	public int getNbEmpruntsEffectues() {
		return nbEmpruntsEffectues;
	}

	/**
	 * Nombre d'emprunts en retard.
	 * 
	 * @return nombre d'emprunts en retard
	 */
	public int getNbEmpruntsEnRetard() {
		return nbEmpruntsDepasses;
	}

	/**
	 * Coefficient pour le tarif. Relai de la categorie.
	 * 
	 * @return coefTarif from categorie
	 */
	public double getCoefTarif() {
		return catClient.getCoefTarif();
	}

	/**
	 * Coefficient de durée, relai de la categorie.
	 * 
	 * @return coefDuree from categorie
	 */
	public double getCoefDuree() {
		return catClient.getCoefDuree();
	}

	/**
	 * <TT>equals</TT> est une surcharge de <TT>Object.equals</TT> permettant de
	 * tester l'egalite de deux clients. Il y a egalite quand les noms et prenoms
	 * sont les memes.
	 * 
	 * @param obj Operande droit
	 * @return Vrai si les objets ont les meme noms et prenoms
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Client)) {
			return false;
		}
		Client c = (Client) obj;
		return (nom.equals(c.nom) && prenom.equals(c.prenom));
	}

	/**
	 * Rewrite hashCode.
	 * 
	 * @return int to facilitate hash
	 */
	@Override
	public int hashCode() {
		// Very simple approach:
		// Using Joshua Bloch's recipe:
		final int prime = 37, magic = 17;
		int result = magic;
		if (nom != null) {
			result += nom.hashCode();
		}
		if (prenom != null) {
			result = prime * result + prenom.hashCode();
		}
		return result;
	}

	/**
	 * <TT>aDesEmpruntsEnCours</TT> verifie si le client a des emprunts en cours,
	 * auquel cas la fonction retourne vrai, faux sinon.
	 * 
	 * @return vrai si emprunt(s) en cours
	 */
	public boolean aDesEmpruntsEnCours() {
		return nbEmpruntsEnCours > 0;
	}

	/**
	 * <TT>peutEmprunter</TT> verifie si le client peut emprunter le document. Il
	 * peut le faire s'il n'a pas de document en retard de restitution ou s'il n'a
	 * pas atteint son nombre maximal d'emprunt. Si ce n'est pas le cas, elle
	 * retourne false.
	 * <P>
	 * Afin de distinguer les deux cas lors du retour, il serait souhaitable de
	 * lever une exception : a faire dans la prochaine version.
	 * 
	 * @return vrai si l'emprunt est possible, faux sinon
	 */
	public boolean peutEmprunter() {
		return !(nbEmpruntsDepasses > 0 || nbEmpruntsEnCours >= nbMaxEmprunt());
	}

	/**
	 * Le client vient de réaliser un nouvel emprunt. Ajoute l'emprunt à la liste
	 * des emprunts en cours.
	 * 
	 * @see #emprunter()
	 * @param emprunt à ajouter.
	 * @throws OperationImpossible le client ne peut pas emprunter.
	 */
	public void emprunter(final FicheEmprunt emprunt) throws OperationImpossible {
		emprunter();
		lesEmprunts.add(emprunt);
		assert invariant();
	}

	/**
	 * Le client vient de réaliser un nouvel emprunt.
	 * 
	 * @throws OperationImpossible le client ne peut pas emprunter.
	 */
	public void emprunter() throws OperationImpossible {
		if (!peutEmprunter()) {
			throw new OperationImpossible("le client ne peut pas emprunter (" + this + ")");
		}
		nbEmpruntsEffectues++;
		nbEmpruntsEnCours++;
		assert invariant();
	}

	/**
	 * <TT>marquer</TT> interdit tout nouvel emprunt par le client. Cette fonction
	 * est appelee par <TT>verifier</TT> de la classe Emprunt.
	 * 
	 * @see FicheEmprunt#verifier()
	 */
	public void marquer() {
		nbEmpruntsDepasses++;
		assert invariant();
	}

	/**
	 * <TT>restituer</TT> est appelee lors de la restitution d'un document emprunte.
	 * S'il s'agissait d'un emprunt en retard les mises a jour sont alors
	 * effectuees.
	 * 
	 * @param emprunt fiche d'emprunt associee correspondante
	 * @exception OperationImpossible relai de restituer.
	 */
	public void restituer(final FicheEmprunt emprunt) throws OperationImpossible {
		restituer(emprunt.getDepasse());
		lesEmprunts.remove(emprunt);
		assert invariant();
	}

	/**
	 * <TT>restituer</TT> est appelee lors de la restitution d'un document emprunte.
	 * S'il s'agissait d'un emprunt en retard les mises a jour sont alors
	 * effectuees.
	 * 
	 * @param enRetard Indique si l'emprunt est marque en retard
	 * @exception OperationImpossible si plus d'emprunt.
	 */
	public void restituer(final boolean enRetard) throws OperationImpossible {
		if (nbEmpruntsEnCours == 0) {
			throw new OperationImpossible("Restituer sans emprunt " + this);
		}
		nbEmpruntsEnCours--;
		if (enRetard) {
			if (nbEmpruntsDepasses == 0) {
				throw new OperationImpossible("Restituer en retard sans retard " + this);
			}
			nbEmpruntsDepasses--;
		}
		assert invariant();
	}

	/**
	 * mise a jour des emprunts suite a un changement de categorie du client.
	 * 
	 * @exception OperationImpossible relai problème de changement de categorie.
	 */
	private void metAJourEmprunts() throws OperationImpossible {
		boolean res;
		for (FicheEmprunt emprunt : lesEmprunts) {
			res = emprunt.changementCategorie();
			if (res) {
				nbEmpruntsDepasses--;
			}
		}
		assert invariant();
	}

	/**
	 * <TT>afficherStatistiques</TT> affiche les statistiques d'emprunt par
	 * categorie de client.
	 */
	public static void afficherStatistiques() {
		System.out.println("Nombre d'emprunt total des clients :" + nbEmpruntsTotal);
	}

	/**
	 * <TT>afficherStatCli</TT> affiche les statistiques d'emprunt du client.
	 */
	public void afficherStatCli() {
		System.out.println("(stat) Nombre d'emprunts effectues par \"" + nom + "\" : " + nbEmpruntsEffectues);
	}

	/**
	 * Conversion en chaine de caracteres pour l'affichage.
	 * 
	 * @return Client converti en chaine de caracteres
	 */
	@Override
	public String toString() {
		String s = nom + " " + prenom + " " + adresse + catClient + ", " + "(nbe " + nbEmpruntsEnCours + ") " + "(nbed "
				+ nbEmpruntsDepasses + ")";
		if (codeReduction != 0) {
			s += "(reduc " + codeReduction + ")";
		}
		return s;
	}

	/**
	 * <TT>dateRetour</TT> retourne la date limite de restitution du document
	 * emprunte a partir de la date du jour et de la duree du pret.
	 * 
	 * @param jour  Date du pret (date du jour)
	 * @param duree Nombre de jours du pret
	 * @return Date limite de restitution du document
	 */
	public Date dateRetour(final Date jour, final int duree) {
		int temp = (int) ((double) duree * catClient.getCoefDuree());
		return Datutil.addDate(jour, temp);
	}

	/**
	 * <TT>sommeDue</TT> permet de connaitre le tarif d'emprunt d'un document selon
	 * le type de ce document et le type de client. Le tarif pour un client a tarif
	 * normal est le tarif nominal, mais on se reserve la possibilite d'evolution.
	 * On suppose que le reglement est forcement effectue.
	 * 
	 * @param tarif Tarif nominal de l'emprunt du document
	 * @return Tarif de l'emprunt
	 */
	public double sommeDue(final double tarif) {
		return tarif * catClient.getCoefTarif();
	}

	/**
	 * <TT>nbMaxEmprunt</TT> retourne le nombre maximal d'emprunts d'un client a
	 * tarif normal.
	 * 
	 * @return nombre d'emprunts maximal
	 */
	public int nbMaxEmprunt() {
		return catClient.getNbEmpruntMax();
	}

	/**
	 * Retourne la date de cotisation.
	 * 
	 * @return Date date de cotisation
	 */
	public Date getDateCotisation() {
		return dateRenouvellement;
	}

	/**
	 * Retourne la date d'inscription.
	 * 
	 * @return la date d'inscription.
	 */
	public Date getDateInscription() {
		return dateInscription;
	}

	/**
	 * Retourne la categorie du client.
	 * 
	 * @return categorie du client
	 */
	public CategorieClient getCategorie() {
		return catClient;
	}

	/**
	 * Modifie la categorie du client.
	 * 
	 * @param nCat nouvelle categorie du client.
	 * @exception OperationImpossible necessite un code de reduction.
	 */
	public void setCategorie(final CategorieClient nCat) throws OperationImpossible {
		if (nCat.getCodeReducUtilise()) {
			throw new OperationImpossible("Categorie necessite un code de reduction");
		}
		catClient = nCat;
		metAJourEmprunts();
		assert invariant();
	}

	/**
	 * Modifie la categorie du client.
	 * 
	 * @param nCat  nouvelle categorie du client.
	 * @param reduc code de reduction du client
	 * @exception OperationImpossible si categorie ne permet pas de code de
	 *                                reduction.
	 */
	public void setCategorie(final CategorieClient nCat, final int reduc) throws OperationImpossible {
		if (nCat.getCodeReducUtilise()) {
			this.codeReduction = reduc;
		} else {
			throw new OperationImpossible("Categorie sans code de reduction");
		}
		catClient = nCat;
		metAJourEmprunts();
		assert invariant();
	}

	/**
	 * Change le code de reduction du client.
	 * 
	 * @param val nouvelle valeur du code de réduction
	 */
	public void setReduc(final int val) {
		codeReduction = val;
		assert invariant();
	}

	/**
	 * Change le nom du client.
	 * 
	 * @param val nouveau nom.
	 */
	public void setNom(final String val) {
		nom = val;
		assert invariant();
	}

	/**
	 * Change le prenom du client.
	 * 
	 * @param val nouveau prenom.
	 */
	public void setPrenom(final String val) {
		prenom = val;
		assert invariant();
	}

	/**
	 * Change l'adresse du client.
	 * 
	 * @param val nouvelle adresse.
	 */
	public void setAddresse(final String val) {
		adresse = val;
		assert invariant();
	}

	/**
	 * Retourne le code de reduction du client.
	 * 
	 * @return code de reduction
	 */
	public int getReduc() {
		return codeReduction;
	}

	/**
	 * retourne le nombre d'emprunts (statistique) de la classe.
	 * 
	 * @return Nombre d'emprunts total
	 */
	public static int getNbEmpruntsTotal() {
		return nbEmpruntsTotal;
	}
}
