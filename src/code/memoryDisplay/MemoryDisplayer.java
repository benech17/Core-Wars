package memoryDisplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import traduction.Traducteur;
import core.Core;

/** Composant graphique qui affiche les cases mémoire du core.
*/
public class MemoryDisplayer extends JPanel { // Class d'affichage de la mémoire du Core

	/** La taille de la memoire de core.
	* @see Core
	*/
	private int SIZE;
	/** Le normbre de ligne de l'affichage.
	*/
	private int NBR_LINE;
	/** Le nombre de colone.
	*/
	private int NBR_COLUMN;
	/** La largeur d'une cellule.
	*/
	private int LARGEUR_CELLULE = 10;
	/** La marge de cellule.
	*/
	private int MARGE_CELLULE = 1;

	/** Variable opérationnelle. Spécifie si (0) on doit afficher toutes les modifications ou (1) juste les nouvelles.
	*/
	private int displayType;

	/** Liste de couleurs associées à chaque programme du core.
	*/
	private ArrayList<Color> colorsOwner;

	/** Les toutes dernières modifications.
	* @see Modification
	*/
	ArrayList<Modification> modifications;
	/** Toutes les modifications apportées au core pendant le run.
	* @see Modification
	*/
	ArrayList<Modification> modificationsBunch;
	/** Liste de la dernière modification apportée à chaque case.
	* Liste de taille {@link MemoryDisplayer#SIZE}
	*/
	ArrayList<Modification> modificationsforCases;

	/** Label d'affichage de la position de la souris sur la mémoire du core.
	*/
	private JTextArea mousePositionLabel;

	/** @param size La taille de la memoire du core.
	* @param nbrLigne Le nombre de ligne.
	*/
	
	
	
	public MemoryDisplayer(int size) {
		super();
		
		this.setMemoryDisplayerSize(size);

		this.modifications = new ArrayList<>();
		this.modificationsBunch = new ArrayList<>();
		this.setSize(10*NBR_COLUMN, 10*NBR_LINE);

		this.displayType = 0;

		this.colorsOwner = new ArrayList<>();

		this.mousePositionLabel = new JTextArea();
		this.mousePositionLabel.setForeground(Color.RED);
		this.mousePositionLabel.setEditable(false);
		this.mousePositionLabel.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(this.mousePositionLabel, BorderLayout.SOUTH);

		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved​(MouseEvent e) {
				Point p = e.getPoint();
				int x = x_axisMemory((int)p.getX());
				int y = y_axisMemory((int)p.getY());

				int memoryPosition = (x-1)+NBR_COLUMN*(y-1);

				if(memoryPosition < MemoryDisplayer.this.SIZE) {
					MemoryDisplayer.this.mousePositionLabel.setText(""+ memoryPosition +" | "+modificationsforCases.get(memoryPosition).getInstruction().toString());
				}
			}
			private int x_axisMemory(int x) {
				return (x/LARGEUR_CELLULE) + 1;
			}
			private int y_axisMemory(int y) {
				return (y/LARGEUR_CELLULE) + 1;
			}
			@Override
			public void mouseDragged​(MouseEvent e) {}
		});
	}
	
	/** @return les dimensions du memoryDisplayer.
	*/
	public Dimension getDimension() {
		return new Dimension(this.getSize().width, this.getSize().height);
	}

	/** Ajout des nouvelles modifications.
	* @param modifications Les nouvelles modifications
	* @see Modification
	* @exception MemoryDisplayerException une des modifications est pour une case de position strictement superieur à {@link MemoryDisplayer#SIZE}.
	*/
	public void setModifications(ArrayList<Modification> modifications) throws MemoryDisplayerException{ // met à jour la liste des dernières modifications
		this.modifications = modifications; // Fixons Les dernières modifications
		this.modificationsBunch.addAll(modifications); // ajoute la modification à toutes les modifications.
		for(int i=0; i<modifications.size(); i++) { // Fixon les modifications de chaque case.
			int position = modifications.get(i).getPosition();
			if(position < this.SIZE) {
				this.modificationsforCases.set(position, modifications.get(i));
			}
			else
				throw new MemoryDisplayerException(MemoryDisplayerException.MODIFICATION_OUT_OF_BOUND);
		}
	}

	/** Reviens à la modification précedente. 
	*/
	public boolean undoDisplay() {
		modificationsBunch.remove(modifications.size()-1); // Suppression de la derniere modification
		return true;
	}

	/** Affiche toutes les cases mémoires en noir.
	* @param g le Graphics du MemoryDisplayer.
	*/
	private void displayBlack(Graphics g) {
		for(int i=0; i<NBR_LINE; i++) {
			for(int j=0; j<NBR_COLUMN; j++) {
				g.setColor(new Color(255, 255, 255));
				g.fillRect(j*LARGEUR_CELLULE, i*LARGEUR_CELLULE, LARGEUR_CELLULE, LARGEUR_CELLULE);
				g.setColor(new Color(0, 0, 0));
				g.fillRect(j*LARGEUR_CELLULE+MARGE_CELLULE,
					i*LARGEUR_CELLULE+MARGE_CELLULE,
					LARGEUR_CELLULE-2*MARGE_CELLULE,
					LARGEUR_CELLULE-2*MARGE_CELLULE
				);
			}
		}
	}
	/** Fixe la taille du MemoryDisplayer, et initialise {@link MemoryDisplayer#modificationsforcase}, puis réaffiche le MemoryDisplayer.
	* @param size la taille de la memoire du core.
	* @param nbrLigne le nombre de ligne de l'affichage.
	*/
	public void setMemoryDisplayerSize(int size) {
		this.SIZE = size;
		double sq = Math.sqrt(size);
		int partie_entiere = (int) sq;
		this.NBR_COLUMN = partie_entiere; // calcul du nombre de colones.
		this.NBR_LINE = ((sq-partie_entiere)==0)?(partie_entiere):(partie_entiere+1);

		this.setSize(this.LARGEUR_CELLULE*NBR_COLUMN, this.LARGEUR_CELLULE*NBR_LINE);
		this.modificationsforCases = new ArrayList<>();
		for(int i=0; i<this.SIZE; i++) {
			this.modificationsforCases.add(new Modification(i, 0, -1, Traducteur.traduireLongToInstruction(0)));
		}

		this.repaint();
	}

	/** Supprime toutes les modifications et réaffiche le MemoryDisplayer.
	*/
	public void clearModification() {
		this.modificationsBunch.clear();
		this.modificationsforCases.clear();
		this.modifications.clear();

		this.setMemoryDisplayerSize(SIZE);

		this.repaint();
	}

	/** Affiche les modifications passées en paramètre.
	* @param g Le graphics du MemoryDisplayer.
	* @param modifs Les modifications à afficher.
	* @exception MemoryDisplayerException une des modifications est pour une case de position strictement superieur à {@link MemoryDisplayer#SIZE}.
	*/
	private void displayUpdate(Graphics g, ArrayList<Modification> modifs) throws MemoryDisplayerException{

		int k = 0;
		int i = 0;
		int j = 0;
		for(int l=0; l<modifs.size(); l++) {
			k = modifs.get(l).getPosition();
			if(k < this.SIZE) {
				j = k%NBR_COLUMN;
				i = k/NBR_COLUMN;
				g.setColor(new Color(255, 255, 255));
				g.fillRect(j*LARGEUR_CELLULE, i*LARGEUR_CELLULE, LARGEUR_CELLULE, LARGEUR_CELLULE);
				if(modifs.get(l).getOwner() == -1)
					g.setColor(new Color(0, 0, 0));
				else
					g.setColor(colorOwner(modifs.get(l).getOwner()));
				g.fillRect(j*LARGEUR_CELLULE+MARGE_CELLULE,
					i*LARGEUR_CELLULE+MARGE_CELLULE,
					LARGEUR_CELLULE-2*MARGE_CELLULE,
					LARGEUR_CELLULE-2*MARGE_CELLULE
				);
			} else
				throw new MemoryDisplayerException(MemoryDisplayerException.MODIFICATION_OUT_OF_BOUND);
		}
	}

	/** Prend un screen du MemoryDisplayer.
	* @return un BufferedImage de l'image prise.
	*/
	public BufferedImage takeShot() {
		BufferedImage stateImage = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
		this.print(stateImage.createGraphics());

		return stateImage;
	}

	/** Affiche image dans le MemoryDisplayer.
	* @param image l'image à affichée.
	*/
	public void setImage(BufferedImage image) {
		Graphics g = this.getGraphics();
		g.drawImage(image, 0, 0, this.getSize().width, this.getSize().height, null);
	}

	@Override
	public void paintComponent(Graphics g) { // Après le premier affichage, seul les modifications sont réaffichées.

		if(modifications.size()<=0) { // Premier affichage du memoryDisplayer. Que des cases noires
			displayBlack(g);
		}
		else { // Lorsqu'il y a une modification. Grâce à la liste de modification, on ne redessine pas tout le JPanel, mais juste les zones modifiées
			try {
				displayUpdate(g, this.modificationsforCases);
			} catch(Exception e) {
				System.err.println(e.getMessage());
			}
    	}
		g.setColor(Color.RED);
		g.drawRect(0, 0, this.getSize().width-1, this.getSize().height-1);

	}

	/** Ajoute la couleur color à la liste des couleurs associer à chaque programme.
	* Le programme associé est celui avec l'index égale à la précedente taille de la liste.
	* @see MemoryDisplayer#colorsOwner
	* @param color La couleur à ajouter.
	*/
	public void addColorOwner(Color color) {
		this.colorsOwner.add(color);
	}

	/** Returne la couleur associée au programme du core d'index ownerId.
	* @param ownerId l'index dans le core du programme.
	* @return La couleur du programme.
	*/
	private Color colorOwner(int ownerId) {
		return this.colorsOwner.get(ownerId);
	}
	
	public void clearColorsOwner(){
		this.colorsOwner=new ArrayList<Color>();
	}
}
