package codeEditor;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

/** Une fenetre qui contient les CodeEditor et les boutons de gestion de code source de programme.
* @see CodeEditor
*/
public class CodeEditorUI extends JFrame {

	/** La liste de CodeEditor
	* @see CodeEditor
	*/
	private static ArrayList<CodeEditor> editors;
	/** Le bouton pour charger un code dans le core.*/
	private JButton chargerButton;
	/** Le bouton pour importer un code déjà écris.*/
	private JButton importButton;
	/** Le bouton pour sauvegarder un code.*/
	private JButton saveButton;

	/** La barre de menu.*/
	private JMenuBar menuBar;
	/** Le menu fichier.*/
	private JMenu fichierMenu;
	/** L'item pour ajouter un nouveau programme.*/
	private JMenuItem nouveauItem;
	/** L'item pour lancer un nouveau tournois.*/
	private JMenuItem tournoisItem;

	/** Le gestionnaire d'onglet.*/
	private JTabbedPane onglets;
	/** Ecoute tous les CodeEditor.
	* @see CodeEditorListener
	*/
	private CodeEditorListener listener;
	/** Ecoute le codeEditorUI.
	* @see CodeEditorUIListener
	*/
	private CodeEditorUIListener codeEditorUIListener;

	/**Initialise tous les membres du CodeEditorUI*/
	public CodeEditorUI() {
		this.editors = new ArrayList<CodeEditor>();

		onglets = new JTabbedPane();

		this.chargerButton = new JButton("CHARGER");
		this.chargerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int idCodeEditor = onglets.getSelectedIndex();
				CodeEditorUI.this.editors.get(idCodeEditor).charger(CodeEditorUI.this.listener);
			}
		});
		this.importButton = new JButton("IMPORTER");
		this.importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int idCodeEditor = onglets.getSelectedIndex();
				CodeEditorUI.this.editors.get(idCodeEditor).importer();
			}
		});
		this.saveButton = new JButton("SAUVEGARDER");
		this.saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int idCodeEditor = onglets.getSelectedIndex();
				CodeEditorUI.this.editors.get(idCodeEditor).save();
			}
		});

		initMenu();
		initUI();
	}

	/** Ajoute un code Editor. Lance une Boite de dialogue de saisi d'information.
	* @see CodeEditor
	*/
	private void addCodeEditor() {
		String name = JOptionPane.showInputDialog("Entrez Le nom du programme");
		CodeEditor aux = new CodeEditor(name);
		this.editors.add(aux);
		this.onglets.add(name, aux);
	}

	/** Initialise le menu du CodeEditorUI.*/
	private void initMenu() {
		this.menuBar = new JMenuBar();

		this.fichierMenu = new JMenu("Fichier");
		this.nouveauItem = new JMenuItem("Nouveau Programme");
		this.nouveauItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				CodeEditorUI.this.addCodeEditor();
			}
		});
		this.tournoisItem = new JMenuItem("Lancer un Tournois");
		this.tournoisItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CodeEditorUI.this.codeEditorUIListener.onTournoisLancer();
			}
		});

		this.fichierMenu.add(nouveauItem);
		this.fichierMenu.add(tournoisItem);
		this.menuBar.add(fichierMenu);
		
		this.setJMenuBar(this.menuBar);
	}

	/** Initialise les composants graphiques.*/
	private void initUI() {

		JToolBar toolbar = new JToolBar("Tool Bar");
		toolbar.add(this.chargerButton);
		toolbar.add(this.importButton);
		toolbar.add(this.saveButton);

		this.setLayout(new BorderLayout());
		this.getContentPane().add(onglets, BorderLayout.CENTER);
		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);

		this.setSize(1000, 500);
		this.setTitle("Editeur de code");
		this.setVisible(true);
	}

	/** fixe tous les CodeEditor comme non chargé dans le core.
	* @see CodeEditor
	*/
	public void setAllCodeEditorAsNotAlreadyLoad() {
		for(int i=0; i<this.editors.size(); i++) {
			this.editors.get(i).setAsNotAlreadyLoad();
		}
	}

	/** Fixe le {@link CodeEditorListener} de tous les CodeEditor du CodeEditorUI.
	* @param listener le CodeEditorListener.
	* @see CodeEditorListener
	*/
	public void setCodeEditorListener(CodeEditorListener listener) {
		this.listener = listener;
	}
	/** Fixe le {@link CodeEditorUIListener}.
	* @param listener le CodeEditorUIListener.
	* @see CodeEditorUIListener
	*/
	public void setCodeEditorUIListener(CodeEditorUIListener listener) {
		this.codeEditorUIListener = listener;
	}
}