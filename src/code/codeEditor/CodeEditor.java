package codeEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.Core;

/** Permet d'éditer du code redCode coloré, d'importer, sauvegarder et charger dans le core un code.
* @see CodeEditorUI
* @see TextArea
*/
public class CodeEditor extends JPanel {

	/** Le nom du programme.
	* @see CodeEditor#getName()
	*/
	private String name;

	/** true si le programme a déjà été charger dans le core, false sinon.
	*/
	private boolean[] isAlreadyLoad;

	/** l'index ou identifiant du programme.
	* @see CodeEditor#getId()
	*/
	private int index;

	/** L'éditeur de code.
	* @see TextArea
	* @see CodeEditor#getCode()
	*/
	private TextArea codeText; // le champ de texte

	/** Les mots clé du langage RedCode.
	*/
	public static final String[] keyWords = {"MOV","ADD","DAT","SUB","MUL","DIV","MOD","JMP","JMZ","JMN","DJN","SPL","CMP","SEQ","SNE","SLT","STP","LDP","NOP"};

	/** Les couleurs de chaque mots clé.
	*/
	public static final Color[] colorsKeyWords = {Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, 
		Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED,};

	/** @param name Le nom du programme.
	*/
	public CodeEditor(String name) {
		super();
		this.name = name;

		this.isAlreadyLoad = new boolean[1];
		this.isAlreadyLoad[0] = false;

		initDisplay();
	}

	/** Initialise les composants graphiques.
	*/
	private void initDisplay() {
		try {
			this.codeText = new TextArea("", this.colorsKeyWords, this.keyWords);
		} catch(Exception e) {
			System.err.println("Erreur à l'initialisation du codeEditor");
			System.err.println(e.getMessage());
		}
		this.codeText.setText("MOV 0, 1");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(this.codeText);

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
	}

	/** Appelle la méthode {@link codeEditorListener#onChargerButtonClicked(String, int, boolean, String)} de {@link CodeEditorListener}
	* pour charger ou mettre à jour le code {@link CodeEditor#textCode} du programme d'index {@link CodeEditor#index} dans un Core.
	* @see Core
	* @see CodeEditorListener
	*/
	public void charger(CodeEditorListener codeEditorListener) {
		boolean updateNotCharge = CodeEditor.this.isAlreadyLoad[0];
		codeEditorListener.onChargerButtonClicked(CodeEditor.this.getCode(), CodeEditor.this.index, updateNotCharge, this.name);
		CodeEditor.this.isAlreadyLoad[0] = true;
	}

	/** Importe un code grace à la méthode {@link SavesManager#loadCodeGUI(CodeEditor)} de {@link SavesManager}.
	*/
	public void importer() {
		try {
			CodeEditor.this.codeText.setText(SavesManager.loadCodeGUI(CodeEditor.this));
		} catch(Exception e) {
			System.out.println("Programme non importer");
			System.out.println(e.getMessage());
		}
	}

	/** Sauvegarde le code de {@link CodeEditor#codeText} grace à {@link SavesManager#saveCodeGUI(String, CodeEditor)} de {@link SavesManager} dans un fichier.
	*/
	public void save() {
		try {
			SavesManager.saveCodeGUI(CodeEditor.this.codeText.getText(), CodeEditor.this);
		}  catch(Exception e) {
			System.out.println("Programme non sauvegarder");
			System.out.println(e.getMessage());
		}
	}

	/** Met la valeur de {@link CodeEditor#isAlreadyLoad} à false.
	*/
	public void setAsNotAlreadyLoad() {
		this.isAlreadyLoad[0] = false;
	}

	/** @return L'indexe du programme.
	*/
	public int getId() { return this.index; }
	
	/** @return le code Redcode du CodeEditor.
	*/
	private String getCode() { return this.codeText.getText(); }
	
	//@Override
	public Dimension getDimension() { return this.getSize(); }
	
	/** @return Le nom du programme.
	* @see CodeEditor#name
	*/
	public String getName() {return this.name; }
	//////////////////////////////////
}