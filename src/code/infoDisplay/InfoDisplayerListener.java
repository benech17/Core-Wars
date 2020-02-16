package infoDisplay;

/** Contient la méthode appellée pour modifier la vitesse du core.
*/
public interface InfoDisplayerListener {
	/** Modifie la vitesse du core.
	* @param coreSpeed la nouvelle vitesse.
	*/
	public void onCoreSpeedSlideModification(int coreSpeed);
} 