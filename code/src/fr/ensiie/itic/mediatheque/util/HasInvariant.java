// CHECKSTYLE:OFF
package fr.ensiie.itic.mediatheque.util;


/**
 * L'interface <TT>HasInvariant</TT> helps with testing safety properties.
 */
public interface HasInvariant {
    /**
     * <TT>invariant</TT> specifies the object is in a safe state
     *    @return boolean 
     */
    public boolean invariant();
}
