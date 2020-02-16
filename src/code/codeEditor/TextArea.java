package codeEditor;

import java.awt.Color;
import java.util.Scanner;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/** Composant graphique d'édition de code redCode avec Couleur.
*/
public class TextArea extends JTextPane {

	/** Le style pour les instructions.*/
	private Style instructionStyle;
	/** Le style pour les arguments.*/
	private Style argumentStyle;
	/** Le style pour les modes d'addressage.*/
	private Style addressingModeStyle;
	/** Le style standart.*/
	private Style standardStyle;

	/** Le document traité.*/
	private StyledDocument doc;

	/** Les styles associés à chaque mots clé : Le i-ième mot clé est associé au i-ième style.*/
	private Style[] styles;
	/** Les mots clé*/
	private String[] keyWords;

	/** si les tableaux colors et keyWords n'ont pas la même taille. 
	* @param text Le texte de départ.
	* @param colors Les couleurs associées à chaque mots clé : Le i-ième mot clé est associé à la i-ième couleur.
	* @param keyWords Les mots clés.
	* @exception TextAreaException  si les tableaux colors et keyWords n'ont pas la même taille.
	*/
	public TextArea(String text, Color[] colors, String[] keyWords) throws TextAreaException {
		super();

		if(colors.length != keyWords.length)
			throw new TextAreaException();

		// On fixe les attributs passes en parametre.
		this.setText(text);
		this.keyWords = keyWords;
		this.styles = new Style[colors.length];
		for(int l=0; l<colors.length; l++) { // On creer des Styles à partir des couleurs fournies.
			this.styles[l] = this.addStyle("style "+l, null);
			StyleConstants.setForeground(this.styles[l], colors[l]);
		}
		this.standardStyle = this.addStyle("standard style", null);
		StyleConstants.setForeground(this.standardStyle, Color.BLACK);

		// On recupere le Document du TextArea.
		doc = this.getStyledDocument();

		// On fixe les couleurs pour :
		this.setSelectedTextColor(Color.YELLOW); // le texte selectionne.
		this.setSelectionColor(Color.GREEN); // la zone selectionne.
		this.setForeground(Color.BLACK); // coleur du texte.
		this.getCaret().setBlinkRate(3); // le scintillement du caret.
		this.setCaretColor(Color.RED); // le caret.

		this.doc = this.getStyledDocument();
		doc.addDocumentListener(new DocumentListener() {
			
			public void changedUpdate(DocumentEvent event) { }
			
			public void insertUpdate(DocumentEvent event) {
				TextArea.this.setForeground(Color.BLACK);
				SwingUtilities.invokeLater(new Runnable(){
	                public void run(){
	                        doc.setCharacterAttributes(0, doc.getLength(), TextArea.this.standardStyle, true);
	                }
	            });

				String text = "";
				try {
					text = doc.getText(0, doc.getLength());
					final Scanner scan = new Scanner(text);
					final int[] indexCurrentWord = new int[1];
					indexCurrentWord[0] = 0;
					SwingUtilities.invokeLater(new Runnable(){
		                public void run(){
					        while(scan.hasNext()) {
								String word = scan.next();
								boolean notKeyWord = true;
								for(int j=0; j<TextArea.this.keyWords.length; j++) {
									int[] j_Aux = new int[1];
									j_Aux[0] = j;
									if(word.equals(TextArea.this.keyWords[j_Aux[0]])) {										
				                        doc.setCharacterAttributes(
				                        	indexCurrentWord[0],
				                        	TextArea.this.keyWords[j_Aux[0]].length(),
				                        	TextArea.this.styles[j_Aux[0]],
				                        	true
				                        );
				               		}
						            
						            notKeyWord = false;
								}
								indexCurrentWord[0] += word.length()+1;
							}
						}
					});
				} catch (Exception e){}
			}
			
			public void removeUpdate(DocumentEvent event) { }

		});
	}
	
	public class TextAreaException extends Exception {
		@Override
		public String getMessage() {
			return "Le nombre de mots clés et de couleur ne sont pas identiques.";
		}
	}
}