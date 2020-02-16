package fenetre;

/** Toute classe implementant cette interface doit avoir des membres de type {@link Core} {@link Fenetre}.
* @see Fenetre
* @see Core
*/
public interface FenetreListener {
	/** Méthode appelée pour lancer un run.
	* @see Fenetre
	* @see Core
	*/
	public void onRunButtonClicked();

	/** Méthode appelée pour mettre en pause un core (s'il n'est pas en pause) ou le faire sortir de la pause.
	* @see Fenetre
	* @see Core
	*/
	public void onPauseButtonClicked();
	
	/** Méthode appelée pour stopper un run d'un core.
	* @see Fenetre
	* @see Core
	*/
	public void onStopButtonClicked();
	
	/** Méthode appelée pour mettre un run en mode analyz.
	* @see Fenetre
	* @see Core
	*/
	public void onAnalyzButtonClicked();
	
	/** Méthode appelée pour passer à l'instruction suivante lorsque le run est en mode analyz.
	* @see Fenetre
	* @see Core
	public void onAnalyzFollowButtonClicked();
	
	/** Méthode appelée pour passer le run en mode fast.
	* @see Fenetre
	* @see Core
	*/
	public void onFastModeButtonClicked();

	/** Méthode appelée pour passer le run en mode normal 
	* @see Fenetre
	* @see Core
	*/
	public void onNormalModeButtonClicked();

	/** Méthode appelée pour modifier la taille de la mémoire du core
	* @param size la nouvelle taille de mémoire du core.
	* @see Fenetre
	* @see Core
	*/
	public void onCoreSizeModification(int size);

	/** Méthode appelée pour modifier le nombre de gagnant d'un core.
	* @param nbrGagnants Le nouveau nombre de gagnants.
	* @see Core#NBR_GAGNANTS
	*/
	public void onNbrGagnantModification(int nbrGagants);

	/** Modifie la version de redcode du core.
	* @param version la nouvelle version.
	*/
	public void onRedCodeVersionModification(int version);

	/** Méthode appelée pour passer à l'instruction précédente lorsque le run est en mode analyz.
	* @see Fenetre
	* @see Core
	*/
	public void onAnalyzPreviousButtonClicked();
	
	/** Méthode appelée pour passer à l'instruction suivante lorsque le run est en mode analyz.
	* @see Fenetre
	* @see Core
	*/
	public void onAnalyzFollowButtonClicked();
}