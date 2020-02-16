package instruction;

import traduction.RedCodeException;

public class Jmn extends Instruction {

    public Jmn(int a, int b, int c, int d) {
	super(a, b, c, d);
    }
	
    @Override
    public String toString() {
	return "JMN"+super.toString();
    }

    @Override
    public boolean estValide() {  // JMN _A @_#B	
	if(getAdMode1()!=1 || getAdMode2()==0)
	    throw new RedCodeException("JMN prends 2 arguments dont une adresse en 1er argument");		
	
	return true;
    }

}
