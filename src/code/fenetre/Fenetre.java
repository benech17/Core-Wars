package fenetre;

import infoDisplay.InfoDisplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import memoryDisplay.MemoryDisplayer;
import memoryDisplay.Modification;
import core.Core;
import fenetre.FenetreListener;
import controller.Controller;

public class Fenetre extends JFrame{
	
	/** Affiche la mémoire du core.
	* @see MemoryDisplayer
	*/
	private MemoryDisplayer memoryDisplayer;
	/** Affiche les informations sur les programmes en cours d'execution dans le core.
	* @see InfoDisplayer
	*/
	private InfoDisplayer infoDisplayer;
	
	/** Utilié pour mettre le core en mode Normal.*/
	private JRadioButton normalModeButton;
	/** Utilisé pour lancer le run.*/
	private JButton runButton;

	/** Utilisé pour mettre le run en pause.*/
	private JButton pauseButton;
	/** Itilisé pour arrêter le run en cours.*/
	private JButton stopButton;

	/** Utilisé pour mettre le run en mode analyz.*/
	private JRadioButton startAnalyzModeButton;
	/** Utilisé pour passer à l'instruction suivante de chaque programme pendant le run en mode Analyz.*/
	private JButton analizFollowButton;
	/** Utilisé pour revenir à l'instruction précédente de chaque programme pendant le run en mode Analyz.*/
	private JButton analizPreviousButton;
	
	/** Utilisé pour mettre le run en mode Fast*/
	private JRadioButton fastModeButton;
	
	/** La barre d'outil affichant les boutons de mode de run.
	* @see Fenetre#startAnalyzModeButton
	* @see Fenetre#normalModeButton
	* @see Fenetre#fastModeButton
	*/
	private JToolBar modeToolBar;
	/** La barre d'outil affichant les boutons d'actions possible pour chaque mode de run.*/
	private JToolBar actionToolBar;

	/** La barre de menu.*/
	private JMenuBar menuBar;
	/** Le menu core. Contenant les options pour modifier les caractéristiques du core.*/
	private JMenu coreMenu;
	/** L'item pour changer le taille du core.
	*@see Fenetre#coreMenu
	*/
	private JMenuItem coreSizeItem;
	/** L'item pour changer le nombre de gagnant souhaité à la fin d'un run.
	*@see Fenetre#coreMenu
	*/
	private JMenuItem nbrGagnantsItem;
	/**
	*/
	private JMenuItem redCoreVersionItem;

	/** Possede les méthodes pour communiquer avec le core.
	* @see Core
	*/
	private FenetreListener fenetreListener;

	/** La taille de la mémoire du core.
	* @see Core
	*/
	private static final int SIZE = Core.SIZE;
	/** La largeur de {@link Fenetre}.*/
	private int WIDTH;
	/** La hauteur de {@link Fenetre}.*/
	private int HEIGHT;

	/** Appelle les méthode d'initialisation des composants graphiques de {@link Fenetre}.*/
	public Fenetre() {
		super();

		this.memoryDisplayer = new MemoryDisplayer(SIZE);
		
		this.initMenuBar();
		this.initToolBar();
		this.initInfoDisplayer();

		this.initUI();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/** Retourne l'afficheur de mémoire.
	* @return Le champ {@link Fenetre#memoryDisplayer}.
	*/
	public MemoryDisplayer getMemoryDisplayer() { return this.memoryDisplayer; }
	/** Fixe le champ {@link Fenetre#fenetreListener}.
	* @param listener Un {@link FenetreListener}
	*/
	public void setFenetreListener(FenetreListener listener) { this.fenetreListener = listener; }
	/** Retourne l'afficheur d'information sur les programmes.
	* @return Le champ {@link Fenetre#infoDisplayer}
	*/
	public InfoDisplayer getInfoDisplayer() {
		return this.infoDisplayer;
	}

	/** Ajoute un programme à afficher : Couleur, nom. Lance une {@link javax.swing.JColorChooser}.*/
	public void addProgram(String programName) {
		Color color = JColorChooser.showDialog(this, "Choisissez la couleur de votre programme", null);
		if(color!=null) {
			this.infoDisplayer.addProgramsNamesLabel(programName, color);
			this.memoryDisplayer.addColorOwner(color);
		}
		else {
			this.infoDisplayer.addProgramsNamesLabel(programName, Color.RED);
			this.memoryDisplayer.addColorOwner(Color.RED);	
		}
	}

	/** Initialise toute l'interface graphique.*/
	private void initUI() {

		this.setLayout(new BorderLayout());
		this.getContentPane().add(this.memoryDisplayer, BorderLayout.CENTER);
		this.getContentPane().add(this.infoDisplayer, BorderLayout.EAST);
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(2, 1));
		aux.add(this.modeToolBar);
		aux.add(actionToolBar);
		this.getContentPane().add(aux, BorderLayout.PAGE_START);

		this.updateFenetreSize();
		this.setTitle("Core War");
		this.setLocation(500, 100);
		this.setVisible(true);
		//this.setResizable(false);
	}
	/** Initialise la barre de menu.*/ 
	private void initMenuBar() {

		this.coreMenu = new JMenu("Core");
		this.coreSizeItem = new JMenuItem("Taille de la mémoire");
		this.coreSizeItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				boolean bonneSaisie = false;
				int size = 2048; // 2048 valeur par défault.
				while(!bonneSaisie) {
					String sizeStr = JOptionPane.showInputDialog("Entrez La nouvelle taille de la mémoire");
					if(sizeStr.length()>0) {
						try {
							size = Integer.parseInt(sizeStr);
							bonneSaisie = true;
						} catch(Exception exception) {
							System.out.println("Erreur de saisie : \ncoreSize");
							bonneSaisie = false;
						}
					}
				}
				fenetreListener.onCoreSizeModification(size);
				Fenetre.this.updateFenetreSize();
			}
		});
		this.nbrGagnantsItem = new JMenuItem("Nombre de gagnants");
		this.nbrGagnantsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nbr = 1;
				String nbrStr = JOptionPane.showInputDialog("Entrez La nouvelle taille de la mémoire");
				boolean bonneSaisie = false;
				while(!bonneSaisie) {
					if(nbrStr.length()>0) {
						try {
							nbr = Integer.parseInt(nbrStr);
							bonneSaisie = true;
						} catch(Exception exception) {
							System.out.println("Erreur de saisie : \ncoreSize");
							bonneSaisie = false;
						}
					}
				}
				Fenetre.this.fenetreListener.onNbrGagnantModification(nbr);
			}
		});
		this.redCoreVersionItem = new JMenuItem("Version de redcode");
		this.redCoreVersionItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String[] versions = {"RedCode 84", "RedCode 94"};
				String version = (String) JOptionPane.showInputDialog(null, "Choisissez une version de redcode", "Version de redcode", JOptionPane.INFORMATION_MESSAGE, null, versions, versions[0]);
				try {
					if(version.equals(versions[0]))
						Fenetre.this.fenetreListener.onRedCodeVersionModification(Controller.RED_CODE_84);
					else if(version.equals(versions[1]))
						Fenetre.this.fenetreListener.onRedCodeVersionModification(Controller.RED_CODE_94);
				} catch (Exception ex) {}
			}
		});
		this.coreMenu.add(this.coreSizeItem);
		this.coreMenu.add(this.nbrGagnantsItem);
		this.coreMenu.add(this.redCoreVersionItem);

		this.menuBar = new JMenuBar();
		this.menuBar.add(this.coreMenu);

		this.setJMenuBar(this.menuBar);
	}
	/** Initialise le {@link Fenetre#infoDisplayer}.*/
	private void initInfoDisplayer() {
		this.infoDisplayer = new InfoDisplayer();
	}
	private void initToolBar() {

		this.normalModeButton = new JRadioButton("MODE NORMAL");
		this.normalModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fenetre.this.fenetreListener.onNormalModeButtonClicked();
				Fenetre.this.setActionToolBarAsNormal();
			}
		});

		this.runButton = new JButton("RUN");
		this.runButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Fenetre.this.fenetreListener.onRunButtonClicked();
			}
		});

		this.pauseButton = new JButton("PAUSE");
		this.pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fenetre.this.fenetreListener.onPauseButtonClicked();
			}
		});

		this.stopButton = new JButton("STOP");
		this.stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fenetre.this.fenetreListener.onStopButtonClicked();
			}
		});

		this.startAnalyzModeButton = new JRadioButton("MODE ANALYSE");
		this.startAnalyzModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fenetre.this.fenetreListener.onAnalyzButtonClicked();
				Fenetre.this.setActionToolBarAsAnalyz();
			}
		});

		this.analizFollowButton = new JButton("SUIVANT");
		this.analizFollowButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Fenetre.this.fenetreListener.onAnalyzFollowButtonClicked();
			}
		});

		this.analizPreviousButton = new JButton("PRECEDENT");
		this.analizPreviousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fenetre.this.fenetreListener.onAnalyzPreviousButtonClicked();
			}
		});

		this.fastModeButton = new JRadioButton("MODE FAST");
		this.fastModeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Fenetre.this.fenetreListener.onFastModeButtonClicked();
				Fenetre.this.setActionToolBarAsFast();
			}
		});

		ButtonGroup bg = new ButtonGroup();
		bg.add(this.normalModeButton);
		bg.add(this.startAnalyzModeButton);
		bg.add(this.fastModeButton);
		this.normalModeButton.setSelected(true);

		this.modeToolBar = new JToolBar();
		this.modeToolBar.add("MODE NORMAL", this.normalModeButton);
		this.modeToolBar.add("MODE ANALYSE ", this.startAnalyzModeButton);
		this.modeToolBar.add("MODE FAST", this.fastModeButton);

		this.actionToolBar = new JToolBar();
		this.setActionToolBarAsNormal();
	}
	private void setActionToolBarAsNormal() {
		this.actionToolBar.removeAll();
		this.actionToolBar.add("RUN", this.runButton);
		this.actionToolBar.add("PAUSE", this.pauseButton);
		this.actionToolBar.add("STOP", this.stopButton);
	}
	private void setActionToolBarAsAnalyz() {
		this.actionToolBar.removeAll();
		this.actionToolBar.add("RUN", this.runButton);
		this.actionToolBar.add("PRECEDENT", this.analizPreviousButton);
		this.actionToolBar.add("SUIVANT", this.analizFollowButton);
		this.actionToolBar.add("STOP", this.stopButton);
	}
	private void setActionToolBarAsFast() {
		this.actionToolBar.removeAll();
		this.actionToolBar.add("RUN", this.runButton);
		this.actionToolBar.add("STOP", this.stopButton);
	}


	private void updateFenetreSize() {
		this.WIDTH = infoDisplayer.getSize().width + memoryDisplayer.getDimension().width + 150;
		this.HEIGHT = memoryDisplayer.getDimension().height + 100 + 100;

		this.setSize(new Dimension(WIDTH, HEIGHT));
	}

	//////////////////////////////////////////////////////

	public void updateMemoryDisplayer(ArrayList<Modification> memoryModifications) { // Méthode appelé par le controlleur lorsque le core lui envoit une modification de la mémoire. Cette méthode met à jour les modifications sur le memoryDisplayer et réaffiche le memoryDisplayer
		try {
			this.memoryDisplayer.setModifications(memoryModifications);
		} catch (Exception e) {
			System.out.println("Impossible d'afficher ces modifications : " + memoryModifications);
			System.out.println(e.getMessage());
		}
		Fenetre.this.memoryDisplayer.repaint();
	}
	public void updateInfoDisplayerStates(LinkedHashMap<Integer, String> states) {
		try {
			this.infoDisplayer.setProgramsStates(states);
		} catch(Exception e) {
			System.err.println("Impossible de mettre à jours le infoDisplayer avec ces données : "+ states);
			System.err.println(e.getMessage());
		}
	}

	//////////////////////////////////////////////////////

	public void setEnabledRunButton(boolean b) {
		this.runButton.setEnabled(b);
	}
	public void setEnabledPauseButton(boolean b) {
		this.pauseButton.setEnabled(b);
	}
	public void setEnabledStopButton(boolean b) {
		this.stopButton.setEnabled(b);
	}
	public void setEnabledstartAnalyzModeButton(boolean b) {
		this.startAnalyzModeButton.setEnabled(b);
	}
	public void setEnabledAnalizFollowButton(boolean b) {
		this.analizFollowButton.setEnabled(b);
	}
	public void setEnabledAnalizPreviousButton(boolean b) {
		this.analizPreviousButton.setEnabled(b);
	}

	public void changePauseToContinue() {
		this.pauseButton.setText("CONTINUER");
	}
	public void changeAnalyzModeOnToOff() {
		this.startAnalyzModeButton.setText("ARRETER MODE ANALYSE");
	}
	public void changeContinueToPause() {
		this.pauseButton.setText("PAUSE");
	}
	public void changeAnalyzModeOffToOn() {
		this.startAnalyzModeButton.setText("LANCER MODE ANALYSE");
	}
}
