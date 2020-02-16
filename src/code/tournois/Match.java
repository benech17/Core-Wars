package tournois;

/**
 * Match est notre classe qui va nous permettre de créer nos match entre deux guerriers
 * ainsi que pouvoir avoir le gagant et le perdant du match 
 * @see Guerrier
 */
public class Match{

    private Guerrier gr1;
    private Guerrier gr2;
    private Guerrier[] fin=new Guerrier[2];//on mettra le vainqueur dans la case 1 et le perdant case 2

    private Guerrier gagnant;

    public Match(Guerrier gr1,Guerrier gr2){
        this.gr1=gr1;
        this.gr2=gr2;
    }

    public Guerrier getGuerrier1(){
        return this.gr1;
    }   
    public Guerrier getGuerrier2(){
        return this.gr2;
    }
    Guerrier getVainqueur(){ //lancer joueMatch() avant d'utiliser cette méthode
        return fin[0];
    }
    Guerrier getPerdant(){
        return fin[1];
    }
    public void setGuerrier1AsVainqueur(){ //lancer joueMatch() avant d'utiliser cette méthode
        this.fin[0] = this.gr1;
        this.fin[1] = this.gr2;
    }
    public void setGuerrier2AsVainqueur(){ //lancer joueMatch() avant d'utiliser cette méthode
        this.fin[0] = this.gr2;
        this.fin[1] = this.gr1;
    }

    public String toString() {
        return "Match : " + gr1 + " vs " + gr2;
    }
}
