// CHECKSTYLE:OFF
package fr.ensiie.itic.mediatheque.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Classe utilitaire pour la gestion des dates ; afin de tester les operations
 * de la mediatheque, la date du jour est simulee par une variable de classe
 * (<TT>dateDeTest</TT>) initialisee a la date du jour reelle lors du chargement
 * de la classe et une operation de classe permet de changer cette date
 * (<TT>addAuJour</TT>).
 * 
 * Les operations sont toutes statiques.
 */
public class Datutil {
	/**
	 * Constantes associees a la gestion des dates.
	 */
	public static final int DAYSINYEAR = 365;
	public static final int DAYSINWEEK = 7;
	public static final int HOURSINDAY = 24;
	public static final int MINSINHOUR = 60;
	public static final int SECSINMIN = 60;
	public static final int MILLISINSEC = 1000;
	/**
	 * Date du jour (pour les tests)
	 */
	private static Date dateDeTest = Calendar.getInstance().getTime();
	private static Locale dateLocale = Locale.FRANCE;
	private static Locale dbLocale = Locale.FRANCE;

	/**
	 * retourne la date de test.
	 * 
	 * @return Date du jour
	 */
	public static Date dateDuJour() {
		return dateDeTest;
		// return Calendar.getInstance().getTime();
	}

	/**
	 * change la date du jour (pour les tests) en additionnant le nombre de
	 * jours indique.
	 * 
	 * @param nbjour
	 *            Nombre de jours a ajouter
	 */
	public static void addAuJour(int nbjour) {
		GregorianCalendar greg = new GregorianCalendar();
		greg.setTime(dateDeTest);
		greg.add(Calendar.DATE, nbjour);
		dateDeTest = greg.getTime();
	}

	/**
	 * calcule une nouvelle date a partir d'une date et d'un nombre de jours.
	 * 
	 * @param date
	 *            Date initiale
	 * @param nbjour
	 *            Nombre de jours a ajouter
	 * @return Nouvelle date calculee
	 */
	public static Date addDate(Date date, int nbjour) {
		GregorianCalendar greg = new GregorianCalendar();
		greg.setTime(date);
		greg.add(Calendar.DATE, nbjour);
		return greg.getTime();
	}

	/**
	 * convertit une date en chaine de caracteres au format francais.
	 * 
	 * @param date
	 *            Date a convertir au format francais (jj/mm/aa)
	 * @return Date convertie en chaine de caracteres
	 */
	public static String dateToString(Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
				dateLocale);
		return df.format(date);
	}

	/**
	 * convertit une date en chaine de caracteres au format francais.
	 * 
	 * @param date
	 *            Date a convertir au format JDBC correspondant a la locale
	 * @return Date convertie en chaine de caracteres
	 */
	public static String dateToSqlValues(Date date) {
		DateFormat df;
		if (dbLocale == Locale.FRANCE) {
			df = new SimpleDateFormat("dd-MM-yyyy");
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd");
		}
		return df.format(date);
	}

	/**
	 * fixe {@code locale}.
	 * 
	 * @param dLocale
	 *            la nouvelle valeur.
	 */
	public static void setDbLocale(Locale dLocale) {
		dbLocale = dLocale;
	}
}
