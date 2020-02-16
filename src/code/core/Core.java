package core;

import instruction.Add;
import instruction.Cmp;
import instruction.Dat;
import instruction.Div;
import instruction.Djn;
import instruction.Instruction;
import instruction.Jmn;
import instruction.Jmp;
import instruction.Jmz;
import instruction.Mod;
import instruction.Mov;
import instruction.Mul;
import instruction.Nop;
import instruction.Seq;
import instruction.Slt;
import instruction.Sne;
import instruction.Sub;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import memoryDisplay.MemoryListener;
import memoryDisplay.Modification;
import traduction.RedCodeException;
import traduction.Traducteur;


/**
* Le classe core représente une machine virtuelle.
* elle contient : 
* <ul>
* <li>une mémoire</li>
* <li>une liste de programmes chargés en mémoire</li>
*</ul>
* elle permet de faire s'executer ces programmes chargés en mémoire.
*/
public class Core {

	/**
	* La mémoire du core, c'est un tableau unidimensionnelle de 
	* nombres entier représentant les contenu de chaque case mémoire.
	*/
	private long[] memory;
	/**
	* Le nombre de case mémoire.
	* @see setCoreSize(int)
	*/
	public static int SIZE;

	/**
	* La vitesse d'execution du core en mode d'execution normal.
	* Sa valeur est comprise entre 1 et {@link Core#MAX_SPEED}.
	* @see Core#mode
	* @see Core#MODE_NORMAL
	* @see Core#setCoreSpeed()
	* @see Core#MAX_SPEED
	*/
	public int CORESPEED = 1;
	/** La vitesse maximum de l'execution du core
	* @see Core#CORESPEED
	*/
	public static int MAX_SPEED = 1000;

	// LES ETATS DE L'EXECUTION D'UN RU  DU CORE
	/**
	* Le mode d'execution du core, il peut prendre les valeurs :
	* <p>
	* <ul>
	*	<li>{@link Core#MODE_NORMAL} : l'execution s'effectue avec une vitesse modifiable</li>
	*	<li>{@link Core#MODEANALYZ} : l'execution s'effectue instruction par instruction</li>
	*	<li>{@link Core#MODE_FAST} : l'execution s'effectue à pleine vitesse de la machine physique</li>
	* </ul>
	* </p>
	*/
	private int mode;
	/** l'execution s'effectue avec une vitesse modifiable
	*/
	public static final int MODE_NORMAL = 0;
	/** l'execution s'effectue instruction par instruction
	*/
	public static final int MODE_ANALYZ = 1;
	/** l'execution s'effectue à pleine vitesse de la machine physique
	*/
	public static final int MODE_FAST = 2;


	/** true si la méthode run() du core est en cours d'execution, false sinon
	*/
	private boolean isRunning;
	/** true quand l'execution du la méthode run() se met en pause, et false quand l'execution continue.
	* Changer sa valeur change l'état de l'execution de run().
	* @see Core#pauseOn()
	* @see Core#pauseOff()
	* @see Core#isOnPause()
	*/
	private boolean isPause;
	/** Si true, l'execution de {@link Core#run()} s'arrete, si false, il ne se passe rien.
	* @see Core#stop()
	*/
	private boolean isStop;

	/** En mode analyz, si cette variable est à true, l'execution se met en pause. Si elle est à false, l'execution continue.
	* en faisant passer sa valeur de true à false, puis après l'execution d'une seule instruction par programme, on fait repasser sa vvaleur à false,
	* on peut executer une seule instruction à chaque signal.
	* C'est là le principe de fonctionnement du mode analyz.
	* @see Core#instrAnalyzModeFollow()
	*/
	private boolean iswaitingFollow;

	/** Ecoute les modifications de la mémoire, sa méthode {@link MemoryListener#onMemoryModification(ArrayList<Modification>)} est appellée à chaque modification de la mémoire.
	* @see MemoryListener
	* @see Core#memory
	*/
	private MemoryListener memoryListener;

	/** Liste des programmes chargés dans la mémoire du core
	* @see Program
	*/
	private ArrayList<Program> programs;

	/** Type d'execution avec envois de modifications au {@link Core#memoryListener} du core.
	* @see Program.execute(int)
	* @see MemoryListener
	*/
	private static final int EXECUTION_TYPE_NORMAL = 0;
	/** Type d'execution sans envois de modifications au {@link Core#memoryListener} du core.
	* @see Program.execute(int)
	* @see MemoryListener
	*/
	private static final int EXECUTION_TYPE_FAST = 1;

	/** Le nombre de programme restant dans la mémoire avant pour finir un run()
	*/
	private int NBR_GAGNANTS;

	/** @param size Le nombre de case mémoire
	* @see Core#memory
	*/
	public Core(int size) {
		// INITIALISATION DE LA MEMOIRE
		this.memory = new long[size];
		for(int i=0; i<this.memory.length; i++) {
			this.memory[i] = 0;
		}
		SIZE = size;
		// INITIALISATION DES PROGRAMMES

		this.programs = new ArrayList<Program>();

		// INITIALISATION DES ETATS
		this.mode = this.MODE_NORMAL;
		this.isRunning = false;
		this.isPause = false;
		this.isStop = false;
		this.iswaitingFollow = false;

		try {
			this.setNbrGagnants(1);
		} catch (CoreException e) {}
	}

	/** @return La mémoire du core
	* @see Core#memory
	*/
	public long[] getMemory() { return this.memory; }
	
	/** @return Le nombre de gagnants de core.
	* @see Core#NBR_GAGNANTS
	* @see Core#setNbrGagnants(int)
	*/
	public int getNbrGagnants() {
		return this.NBR_GAGNANTS;
	}

	/** Fixe le membre {@link memoryListener} du core.
	* @param memoryListener Un memoryListener.
	* @see MemoryListener
	*/
	public void setMemoryListener(MemoryListener memoryListener) {
		this.memoryListener = memoryListener;
	}
	
	/** Fixe la vitesse du core
	* @exception CoreException Si la vitesse fournie n'est pas valide.
	* @param coreSpeed La nouvelle vitesse.
	* @see Core#CORESPEED
	* @see Core#MAX_SPEED
 	*/
	public void setCoreSpeed(int coreSpeed) throws CoreException{
		if(coreSpeed > 0 && coreSpeed <= this.MAX_SPEED) {
			this.CORESPEED = coreSpeed;
		}
		else
			throw new CoreException(CoreException.EXCEPTION_INVALID_SPEED);
	}
	
	/** Modifie le {@link Core#NBR_GAGNANTS} du core.
	* @param nbrGagnants le nouveau nombre de gagnants.
	* @see Core#NBR_GAGNANTS
	* @see getNbrGagnants()
	* @exception CoreException
	*/
	public void setNbrGagnants(int nbrGagnants) throws CoreException{
		if(!this.isRunning && nbrGagnants > 0) {
			this.NBR_GAGNANTS = nbrGagnants;
		}
		else
			throw new CoreException(CoreException.EXCEPTION_INVALID_NBRGAGNANTS);
	}
	///////////////////////////
	///////////////////////////

	/** Change la taille du core.
	* @param size nouvelle taille de {@link Core#memory}.
	* @return 0 s'il la modification ne s'effectue pas pendant un run().
	* -1 si la modification n'est pas exécuté.
	* 1 si la modification est exécuter pendant un run(). Dans ce cas, cette méthode supprime les programmes déjà dans le core.
	* @see Core#SIZE
	*/
	public int setCoreSize(int size) {
		if(this.isRunning)
			return -1; // ne fait rien
		else{
			if(size > this.SIZE) { // Dans ce cas, on peux conserver les programmes déjà présent dans le core.
				long[] memoryAux = new long[size]; // creation d'une nouvelle mémoire plus grande.

				for(int i=0; i<this.memory.length; i++) { // Copie de l'ancienne mémoire dans la nouvelle.
					memoryAux[i] = this.memory[i];
				}
				for(int i=this.memory.length; i<memoryAux.length; i++) { // On complete le reste de la nouvelle mémoire avec des zéros.
					memoryAux[i] = 0;
				}
				this.memory = memoryAux;
				this.SIZE = size;
				return 0; // taille plus grande.
			}
			else if(size < this.SIZE) {
				int option = JOptionPane.showConfirmDialog(null, 
				"La taille du Core vas être réduite. Cette opération supprimera tout les programmes déjà chargés.\nVoulez-vous continier",
				"Réduction de la taille du Core", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE);

				if(option == JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION) { // Si l'utilisateur refuse l'opération
					return -1; 
				}
				else if(option == JOptionPane.YES_OPTION) { // Si l'utilisateur acepte l'opération.
					long[] memoryAux = new long[size]; // creation d'une nouvelle mémoire plus petite.

					for(int i=0; i<memoryAux.length; i++) { // On complete la nouvelle mémoire avec des zéros.
						memoryAux[i] = 0;
					}
					this.SIZE = size;
					this.memory = memoryAux;
					return 1;
				}
				else
					return -1;
			} else {
				return -1;
			}
		}
	}


	////////////////////////////////////////////////////////////////////////
	//METHODE POUR METTRE A JOUR UN PROGRAM DEJA EN MEMOIRE (AVANT LE RUN)//
	////////////////////////////////////////////////////////////////////////
	/** Met le code du programme d'index indexProgram dans {@link Core#programs} et met à jour son code dans la mémoire
	* avec comme nouveau code code.
	* @param code les code du programme.
	* @param indexProgram l'index du programme dans la liste de programme.
	* @see Core#programs
	* @see Program
	* @see Program#update(string)
	* @see Core#loadProgram(int, ArrayList<Instruction>, int)
	* @exception CoreException si indexProgram n'est pas valide.
	*/
	public void updateProgram(String code, int indexProgram) throws CoreException {
		if(indexProgram < this.programs.size()) {
			Program p = this.programs.get(indexProgram);
			p.update(code);
		}
		else
			throw new CoreException(CoreException.EXCEPTION_INVALID_PROGRAM_INDEX);
	}
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////
	//METHODES POUR L'AJOUT D'UN NOUVEAU PROGRAMME EN MEMOIRE//
	///////////////////////////////////////////////////////////

	/** charge les instructions d'un program dans la memoire (sous forme d'entiers) du core à partir de la position fournie.
	* @param position la position de la première instruction du programme en mémoire.
	* @param program une liste des instructions du programme.
	* @param programID l'index du programme dans {@link Core#programs}.
	* @see Instruction
	* @exception CoreException si programID n'est pas valide.
	*/
	private void loadProgram(int position, ArrayList<Instruction> program, int programID) throws CoreException { // Lance la méthode du memoryListener

		if(programID < this.programs.size()) {
			ArrayList<Modification> memoryModifications = new ArrayList<>(); // contient les modifications de memory 
			
			for(int i=0, j=position; i<program.size(); i++, j++) {
				try {
					this.memory[j] = Traducteur.traduireInstructionToLong(program.get(i));
				} catch(Exception e) {
					System.out.println("Instruction "+i+" non traduite !");
					System.out.println(e.getMessage());
					return;
				}

				memoryModifications.add(new Modification(j, this.memory[j], programID, program.get(i))); // Ajout de la modification a la liste de modifications
			}

			this.memoryListener.onMemoryModification(memoryModifications); // Appel de la méthode du MemoryListener avec les modifications apportées à la mémoire
		}
		else
			throw new CoreException(CoreException.EXCEPTION_INVALID_PROGRAM_INDEX);
	}
	/** Créer et charge un nouveau programme au Core à partir du code passé en paramètre.
	* @param code le code Redcode du programme à ajouter.
	* @return l'index dans {@link Core#programs} du programme ajouté.
	* @see Core#loadProgram(int , ArrayList<Instruction>, int)
	* @see Core#clearPrograms()
	* @see Core#programs
	*/
	public int addProgram(String code) { // Retourne l'index du Program dans la liste des programmes
		Program program = new Program(code);
		this.programs.add(program);
		int programIndex = this.programs.indexOf(program);
		try {
			loadProgram(program.getStart(), program.getInstructions(), programIndex);
		} catch (CoreException e) {
			System.out.println(e.getMessage());
			this.programs.remove(programIndex);
			programIndex = -1;
		}
		return programIndex;
	}
	/** Retire tous les programmes du core.
	* @see Core#addProgram(String)
	* @see Core#programs
	* @see Core#loadProgram(int , ArrayList<Instruction>, int)
	*/
	public void clearPrograms() {
		this.programs.clear();
	}

	///////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////

	///////////////////////////////////////////////////
	///AFFICHAGE DE LA MEMOIRE EN LIGNE DE COMMANDES///
	///////////////////////////////////////////////////
	/** Affiche en console le contenu de la mémoire.
	* @see Core#memory
	*/
	public void displayMemory() {
		for(int i=0; i<this.memory.length; i++) {
			Instruction inst = Traducteur.traduireLongToInstruction(this.memory[i]);
			System.out.println(i+": "+inst+"     "+this.memory[i]);
		}
	}
	////////////////////////////////////
	////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////
	//METHODES POUR OBTENIR UN ESPACE LIBRE D'UNE CERTAINE TAILLE DANS LA MEMOIRE//
	///////////////////////////////////////////////////////////////////////////////
	/** @param size La taille de la mémoire du core que l'on veut alouer.
	* @return un index aléatoire de case mémoire du core, le premier de size cases à alouer.
	*/
	private int getRandomFreePlace(int size){
		int res = randomInt(this.memory.length-1, 0);
		while(!isFreePlace(res, size)) {
			res = randomInt(this.memory.length-1, 0);
		}
		return res;
	}
	/** @param Max La valeur maximum du nombre retourné.
	* @param Min La valeur minimum du nombre retourné.
	* @return Un nombre aléatoire compris entre Max et Min.
	*/
	private static int randomInt(int Max, int Min) {
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	/** Vérifie si size cases à partir de la position place de {@link Core#memory} sont vide.
	* @param place Le début de l'espace mémoire à vérifier.
	* @param size la taille de l'espace mémoire à vérifier.
	*/
	private boolean isFreePlace(int place, int size) {
		if(this.memory[place] == 0) {
			for (int i=place; i<=(place+size)%this.SIZE; i++) {
				if(memory[i] != 0)
					return false;
			}
		}
		else
			return false;

		return true;
	}
	/////////////////////////
	/////////////////////////

	/** Remplit une zone de le mémoire avec des zéro.
	* @param start La première case de la zone mémoire.
	* @param end La case après la dernière case de la zone mémoire.
	*/
	private void fillWithZero(int start, int end) {
		ArrayList<Modification> memoryModifications = new ArrayList<>();
		for (int i=start; i<end; i++) {
			this.memory[i] = 0;
			memoryModifications.add(new Modification(i, this.memory[i], -1, Traducteur.traduireLongToInstruction(0)));
		}

		this.memoryListener.onMemoryModification(memoryModifications);
	}
	
	//////////////////////////////////////////////
	//////METHODE MANIPULANT LES ETATS////////////
	//////////////////////////////////////////////

	/**
	* Permet de fixer le mode d'execution du core.
	* @param mode Le nouveau mode d'execution.
	* @exception CoreException le mode fourni n'est pas valide.
	* @see Core#mode
	* @see Core#getMode()
	*/
	public void setMode(int mode) throws CoreException {
		if(mode == MODE_NORMAL || mode == MODE_ANALYZ || mode == MODE_FAST) {
			this.mode = mode;
			if(this.mode!=Core.MODE_ANALYZ)
				this.iswaitingFollow = false;
		}
		else
			throw new CoreException(CoreException.EXCEPTION_MODE_EXECUTION);
	}
	/**
	* @return Le mode d'execution actuel du core. 
	* @see Core#mode
	* @see Core#setMode()
	*/
	public int getMode() {
		return this.mode;
	}

	/** Met l'exécution de la méthode {@link Core#run()} en pause
	* @exception CoreException le core ne peut être mit en pause.
	* @see Core#isPause
	* @see Core#pauseOff()
	*/
	public void pauseOn() throws CoreException {
		if(this.isRunning)
			this.isPause = true;
		else
			throw new CoreException(CoreException.EXCEPTION_PAUSE_UNABLE);
	}
	/** Fait la méthode {@link Core#run()} de la pause.
	* @exception CoreException le core ne peut pas quitter la pause.
	* @see Core#isPause
	* @see Core#pauseOn()
	*/
	public void pauseOff() throws CoreException{
		if(this.isRunning)
			this.isPause = false;
		else
			throw new CoreException(CoreException.EXCEPTION_PAUSE_UNABLE);
	}

	/** Arrete une pause eventuelle et met {@link Core#isStop} à true.
	* L'execution de la methode {@link Core#run()} s'arrete.
	* @exception CoreException l'execution de {@link Core#run()} ne peut pas être stoppé.
	* @see Core#isStop
	*/
	public void stop() throws CoreException{
		if(this.isRunning){
			this.pauseOff();
			this.isStop = true;
		}
		else new CoreException(CoreException.EXCEPTION_STOP_UNABLE);
	}
	/** Fait passer la valeur de {@link Core#iswaitingFollow} à false.
	* @see Core#iswaitingFollow
	*/
	public void instrAnalyzModeFollow() throws CoreException{
		if(this.isRunning && this.mode == this.MODE_ANALYZ)
			iswaitingFollow = false;
		else
			throw new CoreException(CoreException.EXCEPTION_ANAMLYS_FOLLOW_UNABLE);
	}

	/** @return true si la méthode {@link Core#run()} est en cours et false sinon.
	* @see Core#isRunning
	*/
	public boolean isOnRun() {
		return this.isRunning;
	}
	/** @return true si la méthode {@link Core#run()} est en pause et false sinon.
	* @see Core#isPause
	* @see Core#pauseOff()
	* @see Core#pauseOn()
	*/
	public boolean isOnPause() {
		return this.isPause;
	}
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	///////////////////////////
	/////////////METHODES RUN///
	///////////////////////////

	/** Execute le programme contenu dans le core instruction après insrtruction. Cela avec le type d'execution fast.
	* @see Core#EXECUTION_TYPE_FAST
	* @return La liste des indexes des programmes gagnant le run.
	*/
	public ArrayList<Integer> runFastFast() {
		ArrayList<Integer> vainqueurs = new ArrayList<>();

		isRunning = true;
		int[] executionResults = new int[this.programs.size()];
		int[] eliminatedPrograms = new int[this.programs.size()]; // -1 si le program éliminé et 0 sinon
		for(int i=0; i<eliminatedPrograms.length; i++) {
			eliminatedPrograms[i] = 0;
		}

		boolean arret = false;
		while(!fin(eliminatedPrograms)/* && !arret*/) {
			// initialisation du tableau de resultat de l'execution de chaque programme
			for(int i=0; i<executionResults.length; i++) {
				executionResults[i] = 0;
			}
			// Un tour
			for(int warrior=0; warrior<this.programs.size(); warrior++) {
				if(eliminatedPrograms[warrior] != -1) {
					executionResults[warrior] = this.programs.get(warrior).execute(this.EXECUTION_TYPE_FAST);
				}
			}
			// vérification de défaites de programmes
			for(int i=0; i<executionResults.length; i++) {
				if(executionResults[i] != -2) {
					if(executionResults[i] == -1) {
						// On elimine le programme
						eliminatedPrograms[i] = -1;
						executionResults[i] = -2;
					}
				}
			}
		}

		isRunning = false;
		for(int j=0; j<eliminatedPrograms.length; j++) {
			if(eliminatedPrograms[j]==0) {
				vainqueurs.add(j);
			}
		}
		this.isStop = false;
		this.fillWithZero(0, this.SIZE); // remplissage de la mémoire avec des zéros

		return vainqueurs;
	}

	/** Execute le programme contenu dans le core instruction après insrtruction. Cela avec le type d'execution normal.
	* @see Core#EXECUTION_TYPE_NORMAL
	* @return La liste des indexes des programmes gagnant le run.
	*/
	public ArrayList<Integer> run() {

		ArrayList<Integer> vainqueurs = new ArrayList<>();

		isRunning = true;
		int[] executionResults = new int[this.programs.size()];
		int[] eliminatedPrograms = new int[this.programs.size()]; // -1 si le program éliminé et 0 sinon
		for(int i=0; i<eliminatedPrograms.length; i++) {
			eliminatedPrograms[i] = 0;
		}

		boolean arret = false;
		while(!fin(eliminatedPrograms)/* && !arret*/) {
			// initialisation du tableau de resultat de l'execution de chaque programme
			for(int i=0; i<executionResults.length; i++) {
				executionResults[i] = 0;
			}
			// Un tour
			for(int warrior=0; warrior<this.programs.size(); warrior++) {
				if(eliminatedPrograms[warrior] != -1) {
					executionResults[warrior] = this.programs.get(warrior).execute(this.EXECUTION_TYPE_NORMAL);
				}
			}
			// vérification de défaites de programmes
			for(int i=0; i<executionResults.length; i++) {
				if(executionResults[i] != -2) {
					if(executionResults[i] == -1) {
						// On elimine le programme
						eliminatedPrograms[i] = -1;
						executionResults[i] = -2;
					}
				}
			}

			// SI LE MODE ANALYZ EST ACTIF
			if(this.mode==this.MODE_ANALYZ) {

				iswaitingFollow = true;
				try{
					while(iswaitingFollow) {
						Thread.sleep(10);
						// SI ON STOPE LE RUN
						if(this.isStop) {
							this.isStop = false;
							this.fillWithZero(0, this.SIZE); // remplissage de la mémoire avec des zéros
							arret = true;
							break;
						}
					}
				}catch(Exception e) {}
			}
			else if(this.mode==this.MODE_NORMAL) {
				// SI LA PAUSE EST ACTIVE
				try{
					Thread.sleep(1500 - CORESPEED);
					while(isPause) {
						Thread.sleep(100);

						// SI ON STOPPE LE RUN PENDANT LA PAUSE
						if(this.isStop) {
							this.isStop = false;
							this.fillWithZero(0, this.SIZE); // remplissage de la mémoire avec des zéros
							arret = true;
							break;
						}
					}
				}catch(Exception e){}

				// SI ON STOPE LE RUN
				if(this.isStop) {
					this.isStop = false;
					this.fillWithZero(0, this.SIZE); // remplissage de la mémoire avec des zéros
					arret = true;
					break;
				}
			}
			else if(this.mode==this.MODE_FAST) {
				// SI ON STOPE LE RUN
				if(this.isStop) {
					this.isStop = false;
					this.fillWithZero(0, this.SIZE); // remplissage de la mémoire avec des zéros
					arret = true;
					break;
				}
			}
		}

		isRunning = false;
		for(int j=0; j<eliminatedPrograms.length; j++) {
			if(eliminatedPrograms[j]==0) {
				vainqueurs.add(j);
			}
		}
		this.isStop = false;
		this.fillWithZero(0, this.SIZE); // remplissage de la mémoire avec des zéros

		System.out.println("Liste de programme : " + this.programs);

		return vainqueurs;
	}

	/** Vérifie si le nombre eliminatedPrograms à atteint le nombre de gagnants.
	* @param eliminatedPrograms Les indexes de programmes éliminés du run. Si eliminatedPrograms[i] contient -1 alors le programme i est éliminé.
	* @return true si c'est la fin du run, false sinon.
	* @see Core#NBR_GAGNANTS
	* @see Core#run()
	* @see Core#runFastFast()
	*/
	private boolean fin(int[] eliminatedPrograms) {
		int nbrElimines = 0;
		for (int i=0; i<eliminatedPrograms.length; i++) {
			if(eliminatedPrograms[i]==-1) {
				nbrElimines += 1;
			}
		}

		if(nbrElimines >= (eliminatedPrograms.length -NBR_GAGNANTS))
			return true;
		else
			return false;
	}
	////////////////////////////
	////////////////////////////


	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	/////////////////LA CLASSE PROGRAM/////////////////////////////////
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////

	/** Représente un programme chargé dans le core par une tête de lecture, un point de départ, une liste d'instructions
	*/
	public class Program {

		/** La tête de lecture du programme.
		* @see Program#getReadHead()
		*/
		private int readHead;
		/** Le point de départ de la tête de lecture.
		* @see Program#getStart()
		*/
		private int start;
		/** La liste des instructions de départ.
		* @see Program#getInstructions()
		*/
		private ArrayList<Instruction> instructions;

		/** @param code Le code Redcode du programme.
		*/
		public Program(String code) {			
			this.instructions =  Traducteur.traduireCodeToInstructions(code);
			int positionStart = getRandomFreePlace(this.instructions.size());

			this.start = positionStart;
			this.readHead = positionStart;
		}

		public String toString() {
			return "Le Program : " + this.readHead;
		}
		
		/** Met à jour le code du Program
		* @param code les codes sous forme de string.
		*/
		public void update(String code) { // Cette méthode doit soulever une exception si la simulation est déjà lancer.
			int previousEnd = (this.start + this.instructions.size())%SIZE;
			this.instructions = Traducteur.traduireCodeToInstructions(code);
			int programIndex = Core.this.programs.indexOf(this);
			try{
				Core.this.loadProgram(this.start, this.instructions, programIndex);
			} catch(CoreException e) {
				System.out.println(e.getMessage());
			}
			int newEnd = (this.start + this.instructions.size())%SIZE;
			if(newEnd < previousEnd)
				Core.this.fillWithZero(newEnd, previousEnd);
		}
		
		/** @return Le point de départ de la tête de lecture. 
		* @see Program#start
		*/
		public int getStart() { return this.start; }
		/** La position en mémoire de la dernière instruction de départ du programme. */
		public int getEnd() { return this.start + this.instructions.size(); }
		/** @return La liste d'instruction de départ du programme.
		* @see Program#instructions
		*/
		public ArrayList<Instruction> getInstructions() { return this.instructions; }
		/** @return La position de la tête de lecture.
		* @see Program#readhead
		*/
		public int getReadHead() { return this.readHead; }

		/** Execute l'instruction courante du programme, celle à la position de sa tête de lecture dans la mémoire du core. Puis incrémente la tête de lecture.
		* @return 0 si l'instruction est valide, -1 sinon.
		* @param executionType le type de d'execution.
		* @see Program#readhead
		* @see Core#EXECUTION_TYPE_NORMAL
		* @see Core#EXECUTION_TYPE_FAST
		* @see Instruction
		*/
		public int execute(int executionType) {  // Lance la méthode du memoryListener de Core
			try {
				Instruction inst = Traducteur.traduireLongToInstruction(Core.this.memory[readHead%SIZE]);

				if (inst instanceof Mov) {
					Mov mov = (Mov) inst;
					long arg1 =getArgument(mov,1);
					long arg2 =getArgument(mov,2);
					int adres1= getAddressingMode(mov, 1);
					int adres2= getAddressingMode(mov, 2);
					memory[(int)(arg2)%SIZE] = memory[(int)(arg1)%SIZE];

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification((int)(arg2)%SIZE, memory[(int)(arg2)%SIZE], programIndex, mov));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++ ;
					return 0;
				}

				else if(inst instanceof Jmp) {
					Jmp jmp = (Jmp) inst;
					long arg = getArgument(jmp, 1);
					int adres= getAddressingMode(jmp, 1);
					readHead =(int) arg;
					//readHead++ ;

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, jmp));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					return 0;
				}
				
				else if(inst instanceof Dat) {
					Dat dat = (Dat) inst;
					long arg = getArgument(dat, 1);
					int adres= getAddressingMode(dat, 1);
					memory[readHead%SIZE] = memory[readHead%SIZE];   //ne fais rien justement , seulement une constante 
					
					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, memory[readHead%SIZE], programIndex, dat));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++ ;
					return 0;
				} 

				else if(inst instanceof Nop) {
					Nop nop = (Nop) inst;
					readHead++ ;

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, nop));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					return -1; // Instruction qui fait s'arrêter le programme
				}

				else if(inst instanceof Add) {
					Add add = (Add) inst;
					long arg1 = getArgument(add, 1);
					long arg2 = getArgument(add, 2);

					int adres1= getAddressingMode(add, 1);
					int adres2= getAddressingMode(add, 2);
					
					Instruction instru2 = Traducteur.traduireLongToInstruction(memory[(int)arg2%SIZE]);
					instru2.setArg1((int)arg1+instru2.getArg1());
					memory[(int)arg2%SIZE] = Traducteur.traduireInstructionToLong(instru2);
					
					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification( (int)arg2%SIZE, memory[(int)arg2%SIZE], programIndex, add) );
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}
					
					readHead++ ;
					return 0;
				}

				else if(inst instanceof Sub) {
					Sub sub = (Sub) inst;
					long arg1 = getArgument(sub, 1);
					long arg2 = getArgument(sub, 2);
					int adres1= getAddressingMode(sub, 1);
					int adres2= getAddressingMode(sub, 2);

					Instruction instru2 = Traducteur.traduireLongToInstruction(memory[(int)arg2%SIZE]);
					instru2.setArg1((int)arg1-instru2.getArg1());
					//	System.out.println("Traducteur.traduireInstructionToLong(instru2)    "+Traducteur.traduireInstructionToLong(instru2));
					memory[(int)arg2%SIZE] = Traducteur.traduireInstructionToLong(instru2);
					
					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification( (int)arg2%SIZE, memory[(int)arg2%SIZE], programIndex, sub));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}
					
					readHead++ ;
					return 0;
				}

				else if(inst instanceof Mul) {
					Mul mul = (Mul) inst;
					long arg1 = getArgument(mul, 1);
					long arg2 = getArgument(mul, 2);
					int adres1= getAddressingMode(mul, 1);
					int adres2= getAddressingMode(mul, 2);

					Instruction instru2 = Traducteur.traduireLongToInstruction(memory[(int)arg2%SIZE]);
					instru2.setArg1((int)arg1*instru2.getArg1());
					memory[(int)arg2%SIZE] = Traducteur.traduireInstructionToLong(instru2);
					
					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification( (int)arg2%SIZE, memory[(int)arg2%SIZE], programIndex, mul));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++ ;
					return 0;
				}

				else if(inst instanceof Div) {
					Div div = (Div) inst;
					long arg1 = getArgument(div, 1);
					long arg2 = getArgument(div, 2);
					int adres1= getAddressingMode(div, 1);
					int adres2= getAddressingMode(div, 2);

					Instruction instru2 = Traducteur.traduireLongToInstruction(memory[(int)arg2%SIZE]);
					instru2.setArg1((int)arg1/instru2.getArg1());
					//	System.out.println("Traducteur.traduireInstructionToLong(instru2)    "+Traducteur.traduireInstructionToLong(instru2));
					memory[(int)arg2%SIZE] = Traducteur.traduireInstructionToLong(instru2);
					
					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification( (int)arg2%SIZE, memory[(int)arg2%SIZE], programIndex, div));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++ ;
					return 0;
				}

				else if(inst instanceof Mod) {
					Mod mod = (Mod) inst;
					long arg1 = getArgument(mod, 1);
					long arg2 = getArgument(mod, 2);

					int adres1= getAddressingMode(mod, 1);
					int adres2= getAddressingMode(mod, 2);

					Instruction instru2 = Traducteur.traduireLongToInstruction(memory[(int)arg2%SIZE]);
					instru2.setArg1((int)arg1%instru2.getArg1());
					//	System.out.println("Traducteur.traduireInstructionToLong(instru2)    "+Traducteur.traduireInstructionToLong(instru2));
					memory[(int)arg2%SIZE] = Traducteur.traduireInstructionToLong(instru2);
					
					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification( (int)arg2%SIZE, memory[(int)arg2%SIZE], programIndex, mod));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++ ;
					return 0;
				}
				else if (inst instanceof Jmz){
				    Jmz jmz = (Jmz) inst;
					long arg1 = getArgument(jmz, 1);
					long arg2 = getArgument(jmz, 2);
			  
					int adres1= getAddressingMode(jmz, 1);
					int adres2= getAddressingMode(jmz, 2);
					if(arg2==0){
					    readHead =(int) arg1;
					}

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, jmz));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++ ;
					return 0;

				}
				else if (inst instanceof Jmn){
				    Jmn jmn = (Jmn) inst;
					long arg1 = getArgument(jmn, 1);
					long arg2 = getArgument(jmn, 2);
			  
					int adres1= getAddressingMode(jmn, 1);
					int adres2= getAddressingMode(jmn, 2);
					if(arg2!=0){
					    readHead =(int) arg1;
					}

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, jmn));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++ ;
					return 0;

				}
				else if (inst instanceof Djn){
				      Djn djn = (Djn) inst;
					long arg1 = getArgument(djn, 1);
					long arg2 = getArgument(djn, 2);
			  
					int adres1= getAddressingMode(djn, 1);
					int adres2= getAddressingMode(djn, 2);
					if(arg2!=0){
					    readHead =(int) arg1-1;    //decremente
					}

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, djn));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++ ;
					return 0;
				}
				else if (inst instanceof Cmp){
				    Cmp cmp = (Cmp) inst;
					long arg1 = getArgument(cmp, 1);
					long arg2 = getArgument(cmp, 2);
			  
					int adres1= getAddressingMode(cmp, 1);
					int adres2= getAddressingMode(cmp, 2);
					if(arg1==arg2){
					    readHead++;  //skip the next one 
					}

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, cmp));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++;
					return 0;
				}
				else if (inst instanceof Seq){
				      Seq seq = (Seq) inst;
					long arg1 = getArgument(seq, 1);
					long arg2 = getArgument(seq, 2);
			  
					int adres1= getAddressingMode(seq, 1);
					int adres2= getAddressingMode(seq, 2);
					if(arg1==arg2){
					    readHead++;  //skip the next one 
					}

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, seq));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++;
					return 0;
				}
				else if (inst instanceof Sne){
				      Sne sne = (Sne) inst;
					long arg1 = getArgument(sne, 1);
					long arg2 = getArgument(sne, 2);
			  
					int adres1= getAddressingMode(sne, 1);
					int adres2= getAddressingMode(sne, 2);
					if(arg1!=arg2){
					    readHead++;  //skip the next one 
					}

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, sne));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++;
					return 0;
				}
				else if (inst instanceof Slt){
				      Slt slt = (Slt) inst;
					long arg1 = getArgument(slt, 1);
					long arg2 = getArgument(slt, 2);
			  
					int adres1= getAddressingMode(slt, 1);
					int adres2= getAddressingMode(slt, 2);
					if(arg1<arg2){
					    readHead++;  //skip the next one 
					}

					if(executionType==0) {
						ArrayList<Modification> memoryModifications = new ArrayList<>();
						int programIndex = Core.this.programs.indexOf(this);
						memoryModifications.add(new Modification(readHead%SIZE, -1, programIndex, slt));
						Core.this.memoryListener.onMemoryModification(memoryModifications);
					}

					readHead++;
					return 0;
				}
				else {
					return -1;
				}

			} catch (RedCodeException e) {
				System.out.println("Instruction invalide");
				return -1;
			}
		}

		/** @return L'entier contenu dans la case mémoire en paramètre arg (Considérant son mode d'adressage) de l'instruction inst.  -1 si arg n'est pas égale à 2 ou 1.
		* @param inst L'instruction.
		* @param arg L'argument de l'instruction inst : 1 pour le premier argument, 2 pour le second. 
		* @see Instruction
		* @see Program#getAddressingMode(Instruction, int)
		* @see Program#getSimpleArgument(Instruction, int)
		* @see Instruction
		*/
		private long getArgument(Instruction inst, int arg) {
			switch(getAddressingMode(inst, arg)) {
				case 2: // # adressage directe ( mode 2 )
				        return getSimpleArgument(inst, arg);
				case 1: // _ adressage relatif (mode 1)
					return getSimpleArgument(inst, arg)+readHead;
				case 3: // @ adressage indirecte (mode 3)
					Instruction inst2 = Traducteur.traduireLongToInstruction(memory[(int)readHead+getSimpleArgument(inst, arg)%SIZE]);
					return readHead + getSimpleArgument(inst, arg) + getSimpleArgument(inst2, 1);
				default:
					return -1;
			}
		}

		/** @return Le mode d'adressage,  -1 si arg n'est pas égale à 2 ou 1.
		* @param inst L'instruction.
		* @param arg L'argument de l'instruction inst : 1 pour le premier argument, 2 pour le second.
		* @see Program#getSimpleArgument(Instruction, int)
		* @see Program#getArgument(Instruction, int)
		* @see Instruction
		*/
		private int getAddressingMode(Instruction inst, int arg) {
			switch(arg) {
				case 1:
					return inst.getAdMode1();
				case 2:
					return inst.getAdMode2();
				default:
					return -1;
			}
		}
		/** @return return la valeur de l'argument arg (sans considérer son mode d'adressage). -1 si arg n'est pas égale à 2 ou 1.
		* @param inst L'instruction.
		* @param arg L'argument de l'instruction inst : 1 pour le premier argument, 2 pour le second.
		* @see Program#getArgument(Instruction, int)
		* @see Program#getAddressingMode(Instruction, int)
		* @see Instruction
		*/ 
		private int getSimpleArgument(Instruction inst, int arg) {
			switch(arg) {
				case 1:
					return inst.getArg1();
				case 2:
					return inst.getArg2();
				default :
					return -1;
			}
		}
	}
}