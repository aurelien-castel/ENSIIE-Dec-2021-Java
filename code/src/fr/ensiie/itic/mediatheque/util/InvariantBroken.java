// CHECKSTYLE:OFF
package fr.ensiie.itic.mediatheque.util;


/**
 * <TT>InvariantBroken</TT> is raised when the invariant of a class is not true in
 * the current state of the instance
 */
public class InvariantBroken extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de l'exception InvariantBroken.
     *   @param message Message d'erreur
     */
    public InvariantBroken(String message) {
        super(message);
    }
}

