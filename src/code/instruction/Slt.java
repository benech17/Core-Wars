package instruction;

import traduction.RedCodeException;

public class Slt extends Instruction {

    public Slt(int a, int b, int c, int d) {
	super(a, b, c, d);
    }
	
    @Override
    public String toString() {
	return "SLT"+super.toString();
    }

    @Override
    public boolean estValide() {    //SLT @_#A @_#B
	if(getAdMode1()==0 || getAdMode2()==0)
	    throw new RedCodeException("SLT prend deux arguments");
	return true;
    }
	
}
