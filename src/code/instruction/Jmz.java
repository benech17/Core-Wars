package instruction;

import traduction.RedCodeException;

public class Jmz extends Instruction {

    public Jmz(int a, int b, int c, int d) {
	super(a, b, c, d);
    }

    @Override
    public String toString() {
	return "JMZ"+super.toString();
    }

    @Override
    public boolean estValide() {   //JMZ _A @_#B
	if(getAdMode1()!=1 || getAdMode2()==0)
	    throw new RedCodeException("JMZ prends 2 argument dont une adresse en 1er argument");	
    
	return true;
    }

}
