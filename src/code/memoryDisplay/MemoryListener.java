package memoryDisplay;

import java.util.ArrayList;


public interface MemoryListener {
	/** Méthode appelée pour mettre à jour l'affichage de la mémoire du core
	* @param memoryModifications la liste des modifications apportées au core.
	* @see Fenetre
	* @see MemoryDisplayer
	* @see Controller
	* @see InfoDisplayer
	*/
	public void onMemoryModification(ArrayList<Modification> memoryModifications);
}