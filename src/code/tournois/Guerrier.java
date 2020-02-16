package tournois;

/**
 * Guerrier est notre classe qui va nous permettre d'avoir les informations sur notre guerrier
 */
public class Guerrier{

    private String nom;
    private String code;
    private int id=0;
    private static int nb_guerrier;
    private int nbr_victoire_matchs=0;
    private int nbr_victoire_tournois=0;
    private int nbr_matchs=0;

    public Guerrier(String nom, String code){
        this.nom=nom;
        this.code=code;
        this.id=nb_guerrier;
        this.nb_guerrier++;
        
    }
        
    public String getNom(){
        return this.nom;
    }

    public String getCode(){
        return this.code;
    }
        
    int getNbr_victoire_matchs(){
        return this.nbr_victoire_matchs;
    }
        
    int getNbr_victoire_tournois(){
        return this.nbr_victoire_tournois;
    }

    int getNbr_matchs(){
        return this.nbr_matchs;
    }

    int getId(){
        return this.id;
    }

    void inc_match(){
        nbr_matchs++;
    }

    void inc_victoire_match(){
        nbr_victoire_matchs++;
    }

    void inc_victoire_tournois(){
        nbr_victoire_tournois++;
    }

    void reinitiliser(){
        nbr_matchs=0;
        nbr_victoire_matchs=0;
        nbr_victoire_tournois=0;
    }
    public String toString() {
        return this.nom;
    }

    public static void main(String[] args){
        System.out.println("TEST" );
        Guerrier gr1= new Guerrier("Imp","MOV 0 1");
        Guerrier gr2= new Guerrier("Dwarf","MOV 0 1");
        Guerrier gr3= new Guerrier("Fdp","MOV 0 1");
        
        System.out.println(gr1.getNom());
        System.out.println(gr1.getId());
        
        System.out.println(gr2.getNom());
        System.out.println(gr2.getId());
        
        System.out.println(gr3.getNom());
        System.out.println(gr3.getId());
        
    }

}








