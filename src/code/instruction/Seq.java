package instruction;

import traduction.RedCodeException;

public class Seq extends Instruction {

    public Seq(int a, int b, int c, int d) {
	super(a, b, c, d);
    }

    @Override
    public String toString() {
	return "SEQ"+super.toString();
    }

    @Override
    public boolean estValide() {  //SEQ @_#A @_#B
	if(getAdMode1()==0 || getAdMode2()==0)
	    throw new RedCodeException("SEQ prend deux arguments");

	return true;
    }

}
