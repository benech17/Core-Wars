package tournois;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 * FenetreTournois est la classe qui va nous permettre d'afficher le tableau du tournois via un arbre qui aura été créer 
 * de maniere à ressembler à un arbre de tournois selon le nombre de participant choisit 
 * @see Jframe
 * @see JTree
 */
public class FenetreTournois extends JFrame {

    private JTree arbre;
    private JButton matchButton;
   
    public FenetreTournois(int i){
       
        
        this.setSize(700, 700);        
        this.setLocationRelativeTo(null);
        this.setTitle("Tableau de Tournois");
        matchButton = new JButton("Jouer le prochain Tour");
        this.add(matchButton);
        
        //On invoque la méthode de construction de notre arbre
        buildTree(i);
        this.setLayout(new BorderLayout());
        getContentPane().add(arbre, BorderLayout.CENTER);
        getContentPane().add(matchButton, BorderLayout.SOUTH);
        this.setVisible(true);
        expandAllNodes(arbre,0,0);
    
    }

   

    JTree getArbre(){
        return this.arbre;
    }

    JButton getMatchButton() { return this.matchButton; }

    private void buildTree(int i) throws IllegalArgumentException {
        
        
        if(i==8 ){
        
            //Création d'une racine
            DefaultMutableTreeNode finale = new DefaultMutableTreeNode("Gagnant du Tournois");
            DefaultMutableTreeNode f1 = new DefaultMutableTreeNode(".................................");
            
            finale.add(f1);
      
            //Nous allons ajouter des branches et des feuilles à notre racine
            DefaultMutableTreeNode demie = new DefaultMutableTreeNode("Finale");
            //Ajout pour la finale
            DefaultMutableTreeNode d1 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode d2 = new DefaultMutableTreeNode("..................................");
            demie.add(d1);
            demie.add(d2);
            //Fin d'ajout la finale

  
            DefaultMutableTreeNode quart = new DefaultMutableTreeNode("Demi-finales");
            //Ajout pour les demi finales
            DefaultMutableTreeNode q1 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode q2 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode q3 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode q4 = new DefaultMutableTreeNode("..................................");
            quart.add(q1);
            quart.add(q2);
            quart.add(q3);
            quart.add(q4);
            //Fin d'ajout pour les demis finales

  
            DefaultMutableTreeNode huitieme = new DefaultMutableTreeNode("Quarts de finales");
            //Ajout pour les quarts finales
            DefaultMutableTreeNode h1 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode h2 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode h3 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode h4 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode h5 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode h6 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode h7 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode h8 = new DefaultMutableTreeNode("..................................");
            huitieme.add(h1);
            huitieme.add(h2);
            huitieme.add(h3);
            huitieme.add(h4);
            huitieme.add(h5);
            huitieme.add(h6);
            huitieme.add(h7);
            huitieme.add(h8);
            //Fin d'ajout pour les quarts finales

            //On ajoute la feuille ou la branche à la racine
            finale.add(demie);
            finale.add(quart);
            finale.add(huitieme);

            //Nous créons, avec notre hiérarchie, un arbre
            arbre = new JTree(finale);
   
            //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll 
            this.getContentPane().add(new JScrollPane(arbre));
            
            //Quelques ajouts purement esthétique comme le changement des images des noeuds
            DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) arbre.getCellRenderer();  
            Icon img = new ImageIcon("../Image/coupe.png");
            Icon img2 = new ImageIcon("../Image/guerrier.png");
            renderer.setClosedIcon(img);
            renderer.setOpenIcon(img);
            renderer.setLeafIcon(img2);
            JScrollPane pane = new JScrollPane(arbre);
            pane.setPreferredSize(new Dimension(200, 400));
            getContentPane().add(arbre);
  
        }

        else if(i==4){
            //Création d'une racine
            DefaultMutableTreeNode finale = new DefaultMutableTreeNode("Gagnant du Tournois");
            DefaultMutableTreeNode f1 = new DefaultMutableTreeNode(".................................");
            finale.add(f1);
      
            //Nous allons ajouter des branches et des feuilles à notre racine
            DefaultMutableTreeNode demie = new DefaultMutableTreeNode("Finale");
            //Ajout pour la finale
            DefaultMutableTreeNode d1 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode d2 = new DefaultMutableTreeNode("..................................");
            demie.add(d1);
            demie.add(d2);
            //Fin d'ajout pour la finale

  
            DefaultMutableTreeNode quart = new DefaultMutableTreeNode("Demi-finales");
            //Ajout pour les demis  finales
            DefaultMutableTreeNode q1 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode q2 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode q3 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode q4 = new DefaultMutableTreeNode("..................................");
            quart.add(q1);
            quart.add(q2);
            quart.add(q3);
            quart.add(q4);
            //Fin d'ajout pour les demis finales

            //On ajoute la feuille ou la branche à la racine
            finale.add(demie);
            finale.add(quart);
            
            //Nous créons, avec notre hiérarchie, un arbre
            arbre = new JTree(finale);
   
            //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll 
            this.getContentPane().add(new JScrollPane(arbre));
            
            //Quelques ajouts purement esthétique comme le changement des images des noeuds
            DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) arbre.getCellRenderer();
            Icon img = new ImageIcon("../Image/coupe.png");
            Icon img2 = new ImageIcon("../Image/guerrier.png");
            renderer.setClosedIcon(img);
            renderer.setOpenIcon(img);
            renderer.setLeafIcon(img2);
            JScrollPane pane = new JScrollPane(arbre);
            pane.setPreferredSize(new Dimension(200, 400));
            
                    
        }

        else if(i==2){

            //Création d'une racine
            DefaultMutableTreeNode finale = new DefaultMutableTreeNode("Gagnant du Tournois");
            DefaultMutableTreeNode f1 = new DefaultMutableTreeNode(".................................");
            
            finale.add(f1);
      
            //Nous allons ajouter des branches et des feuilles à notre racine

            DefaultMutableTreeNode demie = new DefaultMutableTreeNode("Finale");

            //Ajout pour la finale
            DefaultMutableTreeNode d1 = new DefaultMutableTreeNode("..................................");
            DefaultMutableTreeNode d2 = new DefaultMutableTreeNode("..................................");
            demie.add(d1);
            demie.add(d2);
            //Fin ajout pour la finale

            //On ajoute la feuille ou la branche à la racine
            finale.add(demie);
           
            //Nous créons, avec notre hiérarchie, un arbre
            arbre = new JTree(finale);
   
            //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll 
            this.getContentPane().add(new JScrollPane(arbre));
            
            //Quelques ajouts purement esthétique comme le changement des images des noeuds
            DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) arbre.getCellRenderer();
            Icon img = new ImageIcon("../Image/coupe.png");
            Icon img2 = new ImageIcon("../Image/guerrier.png");
            renderer.setClosedIcon(img);
            renderer.setOpenIcon(img);
            renderer.setLeafIcon(img2);
            JScrollPane pane = new JScrollPane(arbre);
            pane.setPreferredSize(new Dimension(200, 400));
            getContentPane().add(arbre);
          
        }

        else throw new IllegalArgumentException("Le nombre choisit est différent de 2,4,8 !");

    }
    
    /**
         * Méthode qui nous permet de modifier les noms des noeuds
         * 
         * @param s qui correspond au nouveau noeud du noeud 
         * @param n qui correcpond à la position du noeud par rapport à la racine
         * @see DefaultTreeModel
         * @see DefaultMutableTreeNode
         */
     public void setnameNode(String s,int n){
        DefaultTreeModel model = (DefaultTreeModel) arbre.getModel();
        DefaultMutableTreeNode arbre = (DefaultMutableTreeNode) model.getRoot();

        for(int i=0;i<n;i++){
            arbre=arbre.getNextNode();
        }
        
        arbre.setUserObject(s);
        model.nodeChanged(arbre);
    }

     /**
         * Méthode qui nous permet d'afficher les noeuds qui par défault sont fermés
         * 
         * @param tree qui ici va correspondre à notre arbre
         * @param startingIndex 
         * @param rowCount
         * @see Jtree
         */
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
        for(int i=startingIndex;i<rowCount;++i){
            tree.expandRow(i);
        }
        
        if(tree.getRowCount()!=rowCount){
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
        
}
