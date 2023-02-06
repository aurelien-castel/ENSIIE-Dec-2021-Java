// CHECKSTYLE:OFF
package fr.ensiie.itic.mediatheque.util;
import java.io.*;

/**
 * Permet de lire des nombres et des chaines de caracteres
 * au clavier.
 */
public class Console {

    /**
     * La console est un flot de caracteres bufferise.
     */
    private static BufferedReader console =
            new BufferedReader(new InputStreamReader(System.in));

    /**
     * Affiche un message a l'ecran sans fin de ligne    
     *     @param prompt Message a afficher
     */
    public static void printPrompt(final String prompt) {
        System.out.print(prompt + " ");
        System.out.flush();
    }

    /**
     * Lecture d'une chaine de caracteres au clavier. La chaine ne
     * contient pas le caractere 'fin de ligne'. En cas de fin de
     * fichier ou d'erreur, la chaine retournee vaut <code>null</code>.
     *     @return La chaine lue
     */ 
    public static String readLine() {
        String r = "";
        try {
            r = console.readLine();
        } catch(IOException e) { r = null; }
        return r;
    }

    /**
     * Lecture d'une chaine de caracteres au clavier avec affichage d'un
     * prompt.
     *     @param prompt Message a afficher
     *     @return La chaine lue
     *     @see #printPrompt(String)
     *     @see #readLine()
     */
    public static String readLine(final String prompt) {
        printPrompt(prompt);
        return readLine();
    }

    /**
     * Lecture d'un entier au clavier.
     *     @param prompt Message a afficher
     *     @return L'entier lu
     *     @exception NumberFormatException en cas d'erreur
     */
    public static int readInt(final String prompt) throws NumberFormatException {
        printPrompt(prompt);
        return Integer.valueOf(readLine().trim()).intValue();
    }

    /**
     * Lecture d'un double au clavier.
     *     @param prompt Message a afficher
     *     @return Le double lu
     *     @exception NumberFormatException en cas d'erreur
     */
    public static double readDouble(final String prompt) throws NumberFormatException {
        printPrompt(prompt);
        return Double.valueOf(readLine().trim()).doubleValue();
    }
}

