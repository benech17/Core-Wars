package instruction;

import traduction.RedCodeException;

public class Mov extends Instruction {

    public Mov(int a, int b,int c, int d ) {
	super(a,b,c,d);
    }
	
    @Override
    public String toString() {
	return "MOV"+super.toString();
    }

    @Override
    public boolean estValide() { // 0 vide  1 relatif  2 direct #   3 indirect @ 
	if(getAdMode1()==2 || getAdMode2()==2 || (getAdMode1()==3 && getAdMode2()==3) || getAdMode2()==0 ) {
	    System.out.println(this);
	    throw new RedCodeException("MOV prend une adresse en premier argument et second argument");
	}

	return true;
    }
}
	
