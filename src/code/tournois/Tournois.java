package tournois;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Tournois est la classe qui nous permet de lancer le tournois 
 * avec 2,4 ou 8 participant de maniere rapide grace à la méthode de TournoisLister runMatchFast(m1) 
 * qui nous rend uniquement le resultat du match entre deux Guerrier(0 pour le Guerrier 1 et 1 pour le Guerrier 2)
 * et qui affiche l'arbre de tournois
 * @see FenetreTournois
 * @see TournoisListener
 */
public class Tournois{
    
   
    private TournoisListener tournoisListener;

    private Guerrier gagnant_du_tournois;
    private Guerrier second_du_tournois;

    private Guerrier f1; //finaliste 1
    private Guerrier f2; //finaliste 2

    private Guerrier d1; //demi finaliste 1
    private Guerrier d2; //demi finaliste 2
    private Guerrier d3; //demi finaliste 3
    private Guerrier d4; //demi finaliste 4

    private Guerrier q1; //quart finaliste 1
    private Guerrier q2; //quart finaliste 2
    private Guerrier q3; //quart finaliste 3
    private Guerrier q4; //quart finaliste 4
    private Guerrier q5; //quart finaliste 5
    private Guerrier q6; //quart finaliste 6
    private Guerrier q7; //quart finaliste 7
    private Guerrier q8; //quart finaliste 8
    
  
    public Tournois(List<Guerrier> guerriers, TournoisListener tournoisListener){

        this.tournoisListener = tournoisListener;
        
        int n=guerriers.size();

        /////////////////////////////////////////////////////////////////
        /////////////////Si on a choisit 8 guerriers////////////////////
        ///////////////////////////////////////////////////////////////
        if(n==8){

            //Creation de la FenetreTournois
            FenetreTournois gen = new FenetreTournois(8);

            //Quelques modifications de taille de l'arbre
            final Font currentFont = gen.getArbre().getFont();
            final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 5);
            gen.getArbre().setFont(bigFont);

            //On prend nos Guerriers participants
            Guerrier gr1=guerriers.get(0);
            Guerrier gr2=guerriers.get(1);
            Guerrier gr3=guerriers.get(2);
            Guerrier gr4=guerriers.get(3);
            Guerrier gr5=guerriers.get(4);
            Guerrier gr6=guerriers.get(5);
            Guerrier gr7=guerriers.get(6);
            Guerrier gr8=guerriers.get(7);

            q1=gr1;
            q2=gr2;
            q3=gr3;
            q4=gr4;
            q5=gr5;
            q6=gr6;
            q7=gr7;
            q8=gr8;

            //On initialise notre arbre
            gen.setnameNode(q1.getNom(),11);
            gen.setnameNode(q2.getNom(),12);
            gen.setnameNode(q3.getNom(),13);
            gen.setnameNode(q4.getNom(),14);
            gen.setnameNode(q5.getNom(),15);
            gen.setnameNode(q6.getNom(),16);
            gen.setnameNode(q7.getNom(),17);
            gen.setnameNode(q8.getNom(),18);
            
            //On creer nos matchs
            Match m1 = new Match(gr1,gr2);
            Match m2 = new Match(gr3,gr4);
            Match m3 = new Match(gr5,gr6);
            Match m4 = new Match(gr7,gr8);

            //On fait jouer nos matchs ici et on a la resultats
            int r1 = tournoisListener.runMatchFast(m1);
            int r2 = tournoisListener.runMatchFast(m2);
            int r3 = tournoisListener.runMatchFast(m3);
            int r4 = tournoisListener.runMatchFast(m4);

            //Les différents cas gagant/perdant
            if(r1==0){
                d1=q1;

            }else{
                d1=q2;

            }

            if(r2==0){
                d2=q3;

            }else{
                d2=q4;
                
            }

            if(r3==0){
                d3=q5;

            }else{
                d3=q6;
                
            }

            if(r4==0){
                d4=q7;

            }else{
                d4=q8;
                
            }

            ///////////////////A partir d'ici on va jouer les demis finales/////////////////////////

            gr1=d1;
            gr2=d2;
            gr3=d3;
            gr4=d4;

            //On met à jour notre arbre
            gen.setnameNode(d1.getNom(),6);
            gen.setnameNode(d2.getNom(),7);
            gen.setnameNode(d3.getNom(),8);
            gen.setnameNode(d4.getNom(),9);
            
            //On creer nos matchs des demis finales
            m1 = new Match(gr1,gr2);
            m2 = new Match(gr3,gr4);

            //Voici les resultats
            r1 = tournoisListener.runMatchFast(m1);
            r2 = tournoisListener.runMatchFast(m2);

            if(r1==0 && r2==0){ //Si les guerriers 1 et 3 gagnent leur match

                gen.setnameNode(gr1.getNom(),3);
                gen.setnameNode(gr3.getNom(),4);

                f1=gr1;
                f2=gr3;

                m3= new Match(f1,f2);

                r3 = tournoisListener.runMatchFast(m3);

                if(r3==0){
                    gagnant_du_tournois=f1;
                }else{
                    gagnant_du_tournois=f2;
                }

            }else if(r1==0 && r2==1) { //Si les guerriers 1 et 4 gagnent leur match

                f1=gr1;
                f2=gr4;

                gen.setnameNode(gr1.getNom(),3);
                gen.setnameNode(gr4.getNom(),4);

                m3= new Match(f1,f2);

                r3 = tournoisListener.runMatchFast(m3);

                if(r3==0){
                    gagnant_du_tournois=f1;
                }else{
                    gagnant_du_tournois=f2;
                }


            }else if(r1==1 && r2==0){ //Si les guerriers 2 et 3 gagnent leur match

                f1=gr2;
                f2=gr3;

                gen.setnameNode(gr2.getNom(),3);
                gen.setnameNode(gr3.getNom(),4);


                m3= new Match(f1,f2);

                r3 = tournoisListener.runMatchFast(m3);

                if(r3==0){
                    gagnant_du_tournois=f1;
                }else{
                    gagnant_du_tournois=f2;
                }


            }else{ //Si les guerriers 2 et 4 gagent leur match

                f1=gr2;
                f2=gr4;

                gen.setnameNode(gr2.getNom(),3);
                gen.setnameNode(gr4.getNom(),4);

                m3= new Match(f1,f2);

                r3 = tournoisListener.runMatchFast(m3);

                if(r3==0){
                    gagnant_du_tournois=f1;
                }else{
                    gagnant_du_tournois=f2;
                }


            }
            //Et on ajoute le nom du vainqueur
            gen.setnameNode(gagnant_du_tournois.getNom(),1);

              
            
        }

        /////////////////////////////////////////////////////////////////
        /////////////////Si on a choisit 4 guerriers////////////////////
        ///////////////////////////////////////////////////////////////

        if(n==4){

            //Creation de la FenetreTournois
            FenetreTournois fen = new FenetreTournois(4);

            //Quelques modifications de taille de l'arbre
            final Font currentFont = fen.getArbre().getFont();
            final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 5);
            fen.getArbre().setFont(bigFont);
            
            //On prend nos Guerriers participant
            Guerrier gr1=guerriers.get(0);
            Guerrier gr2=guerriers.get(1);
            Guerrier gr3=guerriers.get(2);
            Guerrier gr4=guerriers.get(3);

            d1=gr1;
            d2=gr2;
            d3=gr3;
            d4=gr4;

            //On met à jour notre arbre
            fen.setnameNode(d1.getNom(),6);
            fen.setnameNode(d2.getNom(),7);
            fen.setnameNode(d3.getNom(),8);
            fen.setnameNode(d4.getNom(),9);
            
            //On creer nos matchs 
            Match m1 = new Match(gr1,gr2);
            Match m2 = new Match(gr3,gr4);

            //Voici les resultats des matchs
            int r1 = tournoisListener.runMatchFast(m1);
            int r2 = tournoisListener.runMatchFast(m2);

            if(r1==0 && r2==0){ //Si les guerriers 1 et 3 gagnent leur match

                f1=gr1;
                f2=gr3;

                fen.setnameNode(gr1.getNom(),3);
                fen.setnameNode(gr3.getNom(),4);

                Match m3= new Match(f1,f2);

                int r3 = tournoisListener.runMatchFast(m3);

                if(r3==0){
                    gagnant_du_tournois=f1;
                }else{
                    gagnant_du_tournois=f2;
                }

            }else if(r1==0 && r2==1) { //Si les guerriers 1 et 4 gagnent leur match

                f1=gr1;
                f2=gr4;

                fen.setnameNode(gr1.getNom(),3);
                fen.setnameNode(gr4.getNom(),4);

                Match m3= new Match(f1,f2);

                int r3 = tournoisListener.runMatchFast(m3);

                if(r3==0){
                    gagnant_du_tournois=f1;
                }else{
                    gagnant_du_tournois=f2;
                }


            }else if(r1==1 && r2==0){ //Si les guerriers 2 et 3 gagnent leur match

                f1=gr2;
                f2=gr3;

                fen.setnameNode(gr2.getNom(),3);
                fen.setnameNode(gr3.getNom(),4);

                Match m3= new Match(f1,f2);

                int r3 = tournoisListener.runMatchFast(m3);

                if(r3==0){
                    gagnant_du_tournois=f1;
                }else{
                    gagnant_du_tournois=f2;
                }


            }else{ //Si les guerriers 2 et 4 gagnent leur match

                f1=gr2;
                f2=gr4;

                fen.setnameNode(gr2.getNom(),3);
                fen.setnameNode(gr4.getNom(),4);

                Match m3= new Match(f1,f2);

                int r3 = tournoisListener.runMatchFast(m3);

                if(r3==0){
                    gagnant_du_tournois=f1;
                }else{
                    gagnant_du_tournois=f2;
                }


            }

            fen.setnameNode(gagnant_du_tournois.getNom(),1);
                    
          
            
        }

        /////////////////////////////////////////////////////////////////
        /////////////////Si on a choisit 2 guerriers////////////////////
        ///////////////////////////////////////////////////////////////
        
        else if(n==2){

            //Creation de la FenetreTournois
            FenetreTournois fen0 = new FenetreTournois(2);

            //Quelques modifications de taille de l'arbre
            final Font currentFont = fen0.getArbre().getFont();
            final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 5);
            fen0.getArbre().setFont(bigFont);
            
            //On prend nos Guerriers participant
            Guerrier gr1=guerriers.get(0);
            Guerrier gr2=guerriers.get(1);

            f1=gr1;
            f2=gr2;

            //On actualise notre arbre 
            fen0.setnameNode(f1.getNom(),3);
            fen0.setnameNode(f2.getNom(),4);
            
            Match m1 = new Match(gr1,gr2);
            
            int cr7 = tournoisListener.runMatchFast(m1);

            if(cr7 == 0){//le gagnant sera f1
                gagnant_du_tournois=f1;


            }else{//le gagant sera f2
                gagnant_du_tournois=f2;

            }

            fen0.setnameNode(gagnant_du_tournois.getNom(),1);
            
        }

        else{
            return;
        }
    }

    public void joueTournois(){

    }

    

}
