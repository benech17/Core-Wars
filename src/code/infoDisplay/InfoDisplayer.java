package infoDisplay;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.Core;

/** Affiche les changements de positions des têtes de lecture de chaque programme dans le core.
*/
public class InfoDisplayer extends JPanel {

	/** Liste des ProgramLabel.
	*/
	private ArrayList<ProgramLabel> programsStatesLabels;

	/** Le JSlider de modification de la vitesse du core.
	*/
	private JSlider coreSpeedSlide;
	
	/** Le JScrollPane contenant tous les ProgramLabel du InfoDisplayer.
	*/
	private JScrollPane scrollPane;

	/** JPanel contenant tous les composants graphiques.
	*/
	private JPanel statesPanel;

	/** Le nombre de programme à afficher les informations.
	*/
	private int NBR_PROGRAM;

	/** La largeur du InfoDisplayer.
	*/
	private int width = 100;

	/** Ecoute les les changement du {@link InfoDisplayer#scrollPane}.
	*/
	private InfoDisplayerListener listener;
	
	/** Initialise les membre de la classe.
	*/
	public InfoDisplayer() {
		this.NBR_PROGRAM = 0;

		this.coreSpeedSlide = initCoreSpeedSlide();

		statesPanel = new JPanel();
		programsStatesLabels = new ArrayList<ProgramLabel>();
		scrollPane = new JScrollPane();

		this.setLayout(new GridLayout(2, 1));
		this.add(scrollPane);
		this.add(coreSpeedSlide);
	}
	/** Fixe le {@link InfoDisplayerListener} InfoDisplayer.
	* @param listener le {@link InfoDisplayerListener}
	*/
	public void setListener(InfoDisplayerListener listener) {
		this.listener = listener;
	}
	/** Retourne le nombre de programmes.
	* @return Le nombre de programmes.
	*/
	public int getNbrPrograms() { return this.NBR_PROGRAM; }

	/** Initialise li avec {@link InfoDisplayer#listener} puis retourne un JSlider, pour la modification de la vitesse du core.
	* @return Un JSlider pouvant modifier la vitesse du core.
	*/
	private JSlider initCoreSpeedSlide() {
		final JSlider res = new JSlider(SwingConstants.HORIZONTAL, 1, Core.MAX_SPEED, 1);
		res.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					listener.onCoreSpeedSlideModification(res.getValue());
				} catch(Exception x) {
					System.err.println("Impossible de mettre la vitesse du core à cette valeur");
					System.err.println(x.getMessage());
				}
			}
		});
		res.setSize(width, 50);

		return res;
	}
	
	/** Ajoute un nouveau programme (donc aussi un nouveau ProgramLabel) au InfoDisplayer.
	* @param programName Le nom du nouveau programme.
	* @param color La couleur avec laquelle seront affichées les informations sur le nouveau programme.
	* @see ProgramLabel
	* @see InfoDisplayer#programsStatesLabels
	* @see NBR_PROGRAM
	*/
	public void addProgramsNamesLabel(String programName, Color color) {
		this.NBR_PROGRAM++ ;
		ProgramLabel aux = new ProgramLabel(programName, color);
		programsStatesLabels.add(aux);

		statesPanel.setLayout(new GridLayout(NBR_PROGRAM, 1));
		statesPanel.add(aux);
		
		scrollPane.getViewport().add(statesPanel);
	}

	/** Supprime tous les programmes du InfoDisplayer.
	* c'est à dire :
	* <ul>
		<li>vide la liste {@link InfoDisplayer#programsStatesLabels} </li>
		<li>met {@link InfoDisplayer#NBR_PROGRAM} à zéro.</li>
	*</ul>
	*/
	public void clearProgramsNamesLabel() {
		this.statesPanel.removeAll();
		this.programsStatesLabels.clear();

		this.NBR_PROGRAM = 0;
		this.repaint();
	}

	/** affiche des information pour chaque programme.
	* @param states une map ( < l'indexe du programme, l'information> )
	* @exception InfoDisplayerException si l'indexe du programme n'est à  aucun programme.
	*/
	public void setProgramsStates(LinkedHashMap<Integer, String> states) throws InfoDisplayerException {
		Set<Entry<Integer, String>> set = states.entrySet();
	    Iterator<Entry<Integer, String>> it = set.iterator();
		while(it.hasNext()){
	        Entry<Integer, String> e = it.next();
	        int indexProgram = e.getKey();
	        if(indexProgram < this.NBR_PROGRAM) {
		        programsStatesLabels.get(indexProgram).setText(e.getValue());
	        }
	        else
	        	throw new InfoDisplayerException(InfoDisplayerException.NO_SUCH_PROGRAM_INDEX);
	    }
	}

	/** @return la valeur du JSlider {@link InfoDisplayer#coreSpeedSlide}.
	*/
	public int getCoreSpeedSlideValue() {
		return coreSpeedSlide.getValue();
	}

	@Override
	public Dimension getSize() {
		Dimension dim = new Dimension(Math.max(statesPanel.getSize().width, coreSpeedSlide.getSize().width),
			statesPanel.getSize().height
			);
		this.setSize(dim);
		return dim;
	}

	/** Composant graphique pour l'affichage d'informations sur un programme.
	*/
	public class ProgramLabel extends JPanel{

		/** Le nom du programme.*/
		private String name;
		/** La zone d'affichage du nom.*/
		private JTextArea nameText;
		/** LA zone d'affichage d'information.*/
		private JTextArea stateText;

		/** @param name Le nom du programme.
		* @param color La couleur d'affichage d'informations et du nom.
		*/
		public ProgramLabel(String name, Color color) {
			super();
			this.name = name;
			this.setPreferredSize(new Dimension(200, 50));

			this.nameText = new JTextArea(this.name);
			this.nameText.setForeground(color);
			this.nameText.setEditable(false);
			this.stateText = new JTextArea("");
			this.stateText.setEditable(false);

			this.setLayout(new GridLayout(1, 2));
			this.add(this.nameText);
			this.add(this.stateText);
		}

		/** Fixe les informations à afficher.
		* @param status Le texte des informations à afficher.
		*/
		public void setText(String status) {
			this.stateText.setText(status);
		}

		/** fixe le nom du programme.
		* @param name Le nom du programme.
		*/
		public void setName(String name) {this.name = name; }
		/** @return Le nom du programme.
		*/
		public String getName() {return this.name; }
	}
}