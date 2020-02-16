package instruction;

import traduction.RedCodeException;

public class Sne extends Instruction {

    public Sne(int a, int b, int c, int d) {
	super(a, b, c, d);
    }

    @Override
    public String toString() {
	return "SNE"+super.toString();
    }

    @Override
    public boolean estValide() {   //SNE @_#A @_#B
	if(getAdMode1()==0 || getAdMode2()==0)
	    throw new RedCodeException("SNE prend deux arguments");
	return true;
    }

}
