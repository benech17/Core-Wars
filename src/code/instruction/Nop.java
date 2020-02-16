package instruction;

import traduction.RedCodeException;

public class Nop extends Instruction {

    public Nop(int a, int b, int c, int d) {
	super(a, b, c, d);
    }

    @Override
    public String toString() {
	return "NOP";
    }

    @Override
    public boolean estValide() {
	// throw new RedCodeException("NOP ne peut être utilisé.");

	if (getAdMode1()==6 || getAdMode1()==7){
	    System.out.println(this);
	    throw new RedCodeException("} et { ne sont pas valable pour le premier argument");
        }
      	if (getAdMode2()==4 || getAdMode2()==5){
	    System.out.println(this);
	    throw new RedCodeException("< et > ne sont pas valable pour le premier argument");
        }
      	return true;
    }
	

}

