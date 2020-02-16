package tournois;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * DialogDebut est la classe qui permet à l'utilisateur de choisir son nombre
 * de participant au tournois (2,4,8)
 * 
 * @see JDialog
 */
public class DialogDebut extends JDialog {
        
    public int nbrJoueurs;
    public boolean closed;
    public static int nbGuerriersChoissit=0;
    

    DialogDebut(){
        //setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        initComponent();
        closed=false;
    }

    public int getNbGuerriersChoissit(){
        return this.nbGuerriersChoissit;
    }

    public void close(){
        closed=true;
        dispose();
    }

    public boolean isClosed(){ 
        return closed;
    }
    
    public int getNbrJoueurs(){
        return nbrJoueurs;
    }
    
    /**
         * Affiche à l'utilisateur une liste déroulante avec le nombre de participant
         * à choisir
         * @see Jpanel
         */      
    public void initComponent(){
        this.setTitle("Tournois");

        //Joeurs
        JPanel PnbrJoueur = new JPanel();
        PnbrJoueur.setPreferredSize(new Dimension(220, 60));
        PnbrJoueur.setBorder(BorderFactory.createTitledBorder("Nombre de Guerriers"));
        final JComboBox<Integer> CnbrJoueur = new JComboBox<>();
        for(int i=2; i<=8; i*=2) {
            CnbrJoueur.addItem(i);
        }
        PnbrJoueur.add(CnbrJoueur);
        
                
        JButton ok = new JButton("OK");
        final DialogDebut dummy=this;
        ok.addActionListener( new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e){
                nbrJoueurs = CnbrJoueur.getItemAt(CnbrJoueur.getSelectedIndex());
                dummy.close();
        	}
        });

    
        this.setLayout(new GridLayout(3, 1));
        this.getContentPane().add(PnbrJoueur);
        this.getContentPane().add(ok);

        this.setSize(200, 300);
        this.setVisible(true);
        System.out.println("ininnininininininnooooooooooooooo");
    }
}
