package instruction;

import traduction.RedCodeException;

public class Djn extends Instruction {

    public Djn(int a, int b, int c, int d) {
	super(a, b, c, d);
    }
	
    @Override
    public String toString() {
	return "DJN"+super.toString();
    }

    @Override
    public boolean estValide() {   //DJN @_#A  @_#B
	if(getAdMode1()==0 || getAdMode2()==0)
	    throw new RedCodeException("DJN prends 2 arguments");
	
	return true;
    }

}
