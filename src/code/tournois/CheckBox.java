package tournois;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import codeEditor.SavesManager;
import controller.Controller;


/**
 * CheckBox est la classe qui nous permet dans tournois de selectionner nos guerriers.
 * Nos guerriers sont stockés dans notre répertoire Guerrier via la classse SavesManager.
 * 
 * @see SavesManager 
 */
public class CheckBox extends JFrame implements ActionListener{

    JLabel l;  
    JButton b;  
    //Nous permettra de savoir le nombre de CheckBOX
    static int numberOfCheckBoxes=0; 
    JCheckBox[] checkBoxList;

/**
         * Cela nous permet de recuperer les guerriers.
         * 
         * @see SavesManager
         */
    SavesManager save=new SavesManager();
    HashMap<String, Guerrier> guerriers= SavesManager.getGuerriers();

    DialogDebut d;
    static int res;

    private TournoisListener tournoisListener;

    public CheckBox(TournoisListener listener){
        this.tournoisListener = listener;

        //Pour recuperer le nombre de guerrier 
        String[] nbrsJoueurs = {"2", "4", "8"};
        String nbrJoueurs = (String) JOptionPane.showInputDialog(null, "Nombre de Guerriers", "Tournois", JOptionPane.INFORMATION_MESSAGE, null, nbrsJoueurs, nbrsJoueurs[0]);
        res = Integer.parseInt(nbrJoueurs);

        
        l = new JLabel("Selection des guerriers pour le tournois :");  
        l.setBounds(50,50,300,20);  
      
        //On va recuperer et creer le tableau de checkbox avec la hashmap guerrier
        numberOfCheckBoxes = guerriers.size();
        checkBoxList = new JCheckBox[numberOfCheckBoxes];

        Guerrier[] t=new Guerrier[numberOfCheckBoxes];

        Object[] foo;
        foo=guerriers.values().toArray();

        System.out.println(foo.length); 

        //On récupere uniquement les guerriers qu'on stocke dans un tableau de guerrier
        for(int i=0; i<foo.length;i++){
        t[i]=(Guerrier)foo[i];  

        }

        this.setLayout(new GridLayout(numberOfCheckBoxes + 2, 1));
        //On remplie creer nos checkbox
        for(int i = 0; i < numberOfCheckBoxes; i++) {
            checkBoxList[i] = new JCheckBox(t[i].getNom());
            checkBoxList[i].setBounds(100,100 +(50*i),150,20);
            add(checkBoxList[i]);
        }   

        b=new JButton("Choisir ces guerriers !");  
        b.setBounds(50,500,300,50);  

        b.addActionListener(this);  

        this.add(l);
        this.add(b);  

        setSize(700,700);  
        setVisible(true);
    }

    /**
         * Permet à l'utilisateur de pouvoir cocher les guerriers qu'il souhaite 
         * 
         * @param e le choix du joueur
         * @see ActionEvent
         */
    public void actionPerformed(ActionEvent e){ 
        ArrayList<Guerrier> guerriers_tournois= new ArrayList<Guerrier>(); 
        int nb_guerriers_choisit=0;
        
        //Afin d'afficher à l'utilisateur les guerriers qu'il a choisit 
        String msg="";

        for(int i = 0; i < numberOfCheckBoxes; i++) {
            if(checkBoxList[i].isSelected()){  
                nb_guerriers_choisit++;  
                msg+=" "+checkBoxList[i].getText();
                guerriers_tournois.add(guerriers.get(checkBoxList[i].getText()));
            }
        }  

        if(nb_guerriers_choisit == res)  {
        msg+=" ont été choisit pour le tournois, préparé vous !";
        JOptionPane.showMessageDialog(this,msg); 

        Tournois t = new Tournois(guerriers_tournois, this.tournoisListener);
        //((Controller)this.tournoisListener).setMatchListener(t);

        this.dispose();         
  
        }
        else { 
            JOptionPane.showMessageDialog(this,"Le nombre de guerriers choisit n'est pas égale à "+ res +" !");
        }
    }

}
