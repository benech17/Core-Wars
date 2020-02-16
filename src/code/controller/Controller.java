package controller;

import fenetre.Fenetre;
import fenetre.FenetreListener;
import infoDisplay.InfoDisplayer;
import infoDisplay.InfoDisplayerListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import memoryDisplay.MemoryDisplayer;
import memoryDisplay.MemoryListener;
import memoryDisplay.Modification;
import tournois.CheckBox;
import tournois.Match;
import tournois.MatchListener;
import tournois.TournoisListener;
import codeEditor.CodeEditorListener;
import codeEditor.CodeEditorUI;
import codeEditor.CodeEditorUIListener;
import core.Core;
import core.Core94;

/** Fait le lien entre les objets {@link Fenetre}, {@link MemoryDisplayer}, {@link InfoDisplayer}, {@link Core}, {@link CodeEditorUI} et {@link TournoisListener} via des interfaces.
*/
public class Controller implements MemoryListener, CodeEditorListener, FenetreListener, InfoDisplayerListener,CodeEditorUIListener, TournoisListener {

	/** Constante indiquant la version 84 de redcode.
	*/
	public static final int RED_CODE_84 = 1;
	/** Constante indiquant la version 94 de redcode.
	*/
	public static final int RED_CODE_94 = 2;

	/** La fenetre abritant le MemoryDisplayer et le InfoDisplayer.*/
	private Fenetre fenetre;
	/** Le core executant les programme dans sa mémoire.*/
	private Core core;
	/** Composant grapĥique permetant la saisie de code redcode.*/
	private CodeEditorUI codeEditorUI;
	
	/** l'association de programmes du {@link Controller#codeEditorUI} et du {@link Controller#core}*/
	private LinkedHashMap<Integer, Integer> programIndexing; 
	/** La liste des noms de programmes.*/
	private ArrayList<String> names;

	/** Le lanceur de tournois.*/
	private CheckBox tournoisCheckBox;

	/** Sa méthode est executer à la fin des matchs de tournois.*/
	private MatchListener matchListener;

	/** Initialise {@link Controller#core}, {@link Controller#fenetre} et {@link Controller#codeEditorUI}.*/
	public Controller() {
		this.initCodeEditorUI();
		this.core = new Core(2048);
		this.core.setMemoryListener(this);

		this.fenetre = new Fenetre();
		this.fenetre.setFenetreListener(this);
		this.fenetre.getInfoDisplayer().setListener(this);
		this.programIndexing = new LinkedHashMap<Integer, Integer>();

		this.fenetre.setEnabledStopButton(false);
		this.fenetre.setEnabledPauseButton(false);
		this.fenetre.setEnabledAnalizFollowButton(false);
	}

	/** Fixe le {@link Controller#matchListener}.
	* @param listener le MatchListener
	* @see MatchListener
	*/
	public void setMatchListener(MatchListener listener) {
		this.matchListener = listener;
	}

	/** Initialise le {@link Controller#codeEditorUI}.
	* @see CodeEditorUI
	*/
	private void initCodeEditorUI() {
		this.codeEditorUI = new CodeEditorUI();
		codeEditorUI.setCodeEditorListener(this);
		this.codeEditorUI.setCodeEditorUIListener(this);
	}

	@Override
	public void onMemoryModification(final ArrayList<Modification> memoryModifications) {
		Thread t = new Thread(new Runnable() { // Thread executant affichant le memory displayer toute les 500 millisecondes
			public void run() {
				////////MISE A JOUR DE L'AFFICHAGE DE LA MEMOIRE/////////
				Controller.this.fenetre.updateMemoryDisplayer(memoryModifications);

				///////MISE A JOUR DE L'AFFICHAGE DES ETATS//////////////
				LinkedHashMap<Integer, String> states = new LinkedHashMap<>();
				for(int l = 0; l<memoryModifications.size(); l++) {
					Modification m = memoryModifications.get(l);
					if(m.getOwner() > -1) {
						states.put(m.getOwner(), ""+m.getPosition()+" | "+m.getInstruction());
					}
				}
				Controller.this.fenetre.updateInfoDisplayerStates(states);
			}
		});
		t.start();
		try {
			t.join(); // Attendre que le thread t soit terminé pour continuer le processus.
		} catch(Exception e) {}
	}
	/////////////////////////
	/////////////////////////

	@Override
	public void onChargerButtonClicked(String code, int indexCodeEditor, boolean updateNotCharge, String programName) {
		if (!core.isOnRun()) {
			if(updateNotCharge) {
				try {
					this.core.updateProgram(code, programIndexing.get(indexCodeEditor));
				} catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}
			else {
				this.fenetre.addProgram(programName);
				int indexInCore = this.core.addProgram(code);
				programIndexing.put(indexCodeEditor, indexInCore); // conservons l'index du CodeEditor et du Program pour pouvoir modifier avant l'execution de Run du Core
			}
		}
		else {
			System.out.println("CORE IS ALREADY RUNNING");
		}
	}

	@Override
	public void onRunButtonClicked() {
		if (!core.isOnRun()) {
			this.fenetre.setEnabledRunButton(false);
			this.fenetre.setEnabledStopButton(true);
			this.fenetre.setEnabledPauseButton(true);
			this.fenetre.setEnabledAnalizFollowButton(true);
			Thread t = new Thread(new Runnable(){ // Thread executant la méhode run() du core
				public void run() {
					ArrayList<Integer> winners = core.run();
					this.setLoserWinnerState(winners);
					Controller.this.fenetre.setEnabledRunButton(true);
					System.out.println("RUN END");
				}
				private void setLoserWinnerState(ArrayList<Integer> winners) {
					LinkedHashMap<Integer, String> states = new LinkedHashMap<>();
					for(int l = 0; l<Controller.this.fenetre.getInfoDisplayer().getNbrPrograms(); l++) {
						if(winners.indexOf(l) == -1) {
							states.put(l, "LOSER");
						}
						else {
							states.put(l, "WINNER");
						}
					}
					Controller.this.fenetre.updateInfoDisplayerStates(states);
				}
			});
			t.start();
		}
		else {
			System.out.println("CORE IS ALREADY RUNNING");
		}
	}

	@Override
	public void onTournoisLancer() {
		this.tournoisCheckBox = new CheckBox(Controller.this);
	}

	@Override
	public int runMatch(final Match m) {

        if (!core.isOnRun()) {
			onChargerButtonClicked(m.getGuerrier1().getCode(), 0, false, m.getGuerrier1().getNom());
			onChargerButtonClicked(m.getGuerrier2().getCode(), 1, false, m.getGuerrier2().getNom());

			Thread t = new Thread(new Runnable(){ // Thread executant la méhode run() du core
				public void run() {
					ArrayList<Integer> winners = core.run();
					//this.setLoserWinnerState(winners);
					Controller.this.fenetre.setEnabledRunButton(true);
					
					if(winners.get(0) == 0) {
			            m.setGuerrier1AsVainqueur();
			        }
			        else {
			            m.setGuerrier2AsVainqueur();
			        }
			        Controller.this.onStopButtonClicked();
			        Controller.this.matchListener.onMatchEnd(winners.get(0));
				}
			});
			t.start();
		}

        return 0;
	}
	
	@Override
	public int runMatchFast(Match m) {

        if (!core.isOnRun()) {
			onChargerButtonClicked(m.getGuerrier1().getCode(), 0, false, m.getGuerrier1().getNom());
			onChargerButtonClicked(m.getGuerrier2().getCode(), 1, false, m.getGuerrier2().getNom());

			ArrayList<Integer> winners = core.runFastFast();
			//this.setLoserWinnerState(winners);
			Controller.this.fenetre.setEnabledRunButton(true);
			
	        Controller.this.onStopButtonClicked();
	        
	        return winners.get(0);
		}

        return -1;
	}

	@Override
	public void onPauseButtonClicked() {
		if(!this.core.isOnPause()) {
			try {
				this.core.pauseOn();
				this.fenetre.setEnabledStopButton(false);
				this.fenetre.changePauseToContinue();
				this.fenetre.getMemoryDisplayer().takeShot();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this.fenetre, "Pause non effectué", "Erreur", JOptionPane.ERROR_MESSAGE);
				System.err.println(e.getMessage());
			}
		}
		else {
			try {
				this.core.pauseOff();
				this.fenetre.setEnabledStopButton(true);
				this.fenetre.changeContinueToPause();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this.fenetre, "Impossible de quitter la pause", "Erreur", JOptionPane.ERROR_MESSAGE);
				System.err.println(e.getMessage());
			}
		}
	}

	@Override
	public void onStopButtonClicked() {
		try {
			this.core.stop();
			this.codeEditorUI.setAllCodeEditorAsNotAlreadyLoad();
			this.programIndexing = new LinkedHashMap<Integer, Integer>();
			this.core.clearPrograms();
			this.fenetre.getMemoryDisplayer().clearModification();
			this.fenetre.getInfoDisplayer().clearProgramsNamesLabel();
			this.fenetre.getMemoryDisplayer().clearColorsOwner();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this.fenetre, "Impossible de stopper le run ", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void onCoreSpeedSlideModification(int coreSpeed) {
		try {
			System.err.println(" la nouvelle vitesse : "+ coreSpeed);
			this.core.setCoreSpeed(coreSpeed);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.fenetre, "Impossible de modifier la vitesse du core", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void onAnalyzButtonClicked() {
		if(!(this.core.getMode()==Core.MODE_ANALYZ)) {
			try {
				this.core.setMode(Core.MODE_ANALYZ);
				this.fenetre.changeAnalyzModeOnToOff();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this.fenetre, "Impossible de mettre le core en mode analyz", "Erreur", JOptionPane.ERROR_MESSAGE);
				System.err.println(e.getMessage());
			}
		}
		else {
			try {
				this.core.setMode(Core.MODE_NORMAL);
				this.fenetre.changeAnalyzModeOffToOn();
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this.fenetre, "Impossible de quitter le mode analyz", "Erreur", JOptionPane.ERROR_MESSAGE);
				System.err.println(e.getMessage());
			}
		}
	}

	@Override
	public void onAnalyzFollowButtonClicked() {
		try {
			this.core.instrAnalyzModeFollow();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this.fenetre, "Impossible de passer à l'instruction suivante ", "Erreur Mode analyse", JOptionPane.ERROR_MESSAGE);
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void onAnalyzPreviousButtonClicked() {
		this.fenetre.getMemoryDisplayer().undoDisplay();
	}

	@Override
	public void onFastModeButtonClicked() {
		try {
			this.core.setMode(Core.MODE_FAST);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.fenetre, "Impossible de mettre le core en mode fast", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void onNormalModeButtonClicked() {
		try{
			this.core.setMode(Core.MODE_NORMAL);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this.fenetre, "Impossible de mettre le core en mode normal", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void onCoreSizeModification(int size) {
		int result = this.core.setCoreSize(size);
		if(result != -1) {
			this.fenetre.getMemoryDisplayer().setMemoryDisplayerSize(size);
			if(result==1) {
				this.codeEditorUI.setAllCodeEditorAsNotAlreadyLoad();
				this.programIndexing = new LinkedHashMap<Integer, Integer>();
				this.core.clearPrograms();
				this.fenetre.getInfoDisplayer().clearProgramsNamesLabel();
			}
		}
	}

	@Override
	public void onNbrGagnantModification(int nbrGagants) {
		try {
			this.core.setNbrGagnants(/*nbrGagants*/1);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.fenetre, "Impossible de changer le nombre de gagnant", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void onRedCodeVersionModification(int version) {
		if(version == RED_CODE_94) {
			this.core = new Core94(this.core.SIZE);
			this.core.setMemoryListener(this);
		}
		else if(version == RED_CODE_84) {
			this.core = new Core(this.core.SIZE);
			this.core.setMemoryListener(this);
		}
	}

}
