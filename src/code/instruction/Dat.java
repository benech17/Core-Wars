package instruction;

import traduction.RedCodeException;


public class Dat extends Instruction {

    public Dat(int a, int b, int c, int d) {
	super(a, b, c, d);
    }
	
    @Override
    public String toString() {
	return "DAT"+super.toString();
    }

    @Override
    public boolean estValide() {  //DAT #A
	if (getAdMode2()!=0 || getAdMode1()!=2){
	    System.out.println(this);
	    throw new RedCodeException("DAT prend un unique argument en adressage direct");
	}
      	
      	return true;
    }

}
