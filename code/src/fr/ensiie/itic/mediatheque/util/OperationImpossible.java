package fr.ensiie.itic.mediatheque.util;

/**
 * Une exception <TT>OperationImpossible</TT> est levee en cas d'erreur
 * fonctionnelle : une operation n'est pas permise fonctionnellement.
 */
public class OperationImpossible extends Exception {

	/** serial number. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'exception OperationImpossible.
	 * 
	 * @param message
	 *            Message d'erreur
	 */
	public OperationImpossible(final String message) {
		super(message);
	}
}
