package codeEditor;

/** Contient les méthodes abstraites appellées pour charger le code d'un CodeEditor dans le Core.
*/
public interface CodeEditorListener {
	/** @param code le code redCode contenu dans le CodeEditor.
	* @param indexCodeEditor L'indexe {@link CodeEditor#index} du CodeEditor dans le CodeEditorUI.
	* @param updateNotCharge le champ {@link CodeEditor#isAlreadyLoad} du CodeEditor.
	* @param programName le nom {@link CodeEditor#name} du programme 
	* @see CodeEditor
	*/
	public void onChargerButtonClicked(String code, int indexCodeEditor, boolean updateNotCharge, String programName);
}