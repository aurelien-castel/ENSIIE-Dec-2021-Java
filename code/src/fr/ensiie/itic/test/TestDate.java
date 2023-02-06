package fr.ensiie.itic.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;

import fr.ensiie.itic.mediatheque.util.Datutil;

/* 
*
* Author : Guillaume Descroix
* Last Update : 05/12/21
*
* Cette classe n'a aucun intéret pour les tests fonctionnels
* => Permet de mettre en lumière un disfonctionnement 
* de la fonction getDureeEmprunt() dans le cas ou on change d'heurs.
*/
public class TestDate {
	
	private Date dateEmprunt = Calendar.getInstance().getTime();
	private Date dateLimite;
	
	public void addDate(int nbjour) {
		GregorianCalendar greg = new GregorianCalendar();
		greg.setTime(dateEmprunt);
		greg.add(Calendar.DATE, nbjour);
		dateLimite = greg.getTime();
	}
	
	public int getDureeEmprunt() {
		return (int) ((dateLimite.getTime() - dateEmprunt.getTime())
				/ (Datutil.MILLISINSEC * Datutil.SECSINMIN * Datutil.MINSINHOUR
						* Datutil.HOURSINDAY));
	}
	
	public static void main(String[] args) {
		TestDate d = new TestDate();
		
		System.out.println("TEST : " + d.dateEmprunt.toString());
		for(int i=1; i<=10000; i++) {
			d.addDate(i);
			System.out.println("A : "+d.getDureeEmprunt()+" : "+ d.dateLimite.toString());
			Assertions.assertEquals(i,d.getDureeEmprunt());
		}
		
	}
}
