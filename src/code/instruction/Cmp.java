package instruction;

import traduction.RedCodeException;


public class Cmp extends Instruction {

    public Cmp(int a, int b, int c, int d) {
	super(a, b, c, d);
    }
	
    @Override
    public String toString() {
	return "CMP"+super.toString();
    }

    @Override
    public boolean estValide() {  //CMP @_#A @_#B
	if(getAdMode1()==0 || getAdMode2()==0)
	    throw new RedCodeException("CMP prend deux arguments");
	    
	return true;
    }

}
