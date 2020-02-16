package tournois;

public interface TournoisListener {
	/** Méthode appelée pour lancer un match dans le core.
	* @param m le match à lancer.
	* @see Match
	* @see Tournois
	* @see Controller
	*/
	public int runMatch(Match m);
	/** Méthode appelée pour lancer un match en mode fast.
	* @param m le match à lancer.
	* @see Match
	* @see Tournois
	* @see Controller
	*/
	public int runMatchFast(Match m);
}