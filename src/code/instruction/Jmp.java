package instruction;

import traduction.RedCodeException;

public class Jmp extends Instruction {

    public Jmp(int a, int b, int c, int d) {
	super(a, b, c, d);
    }
	
    public String toString() {
	return "JMP "+super.toString();
    }

    @Override
    public boolean estValide() {   //JMP _A
	if(getAdMode2()!=0 ) throw new RedCodeException("JMP ne prends qu'un seul argument");
	if(getAdMode1()!=1) throw new RedCodeException("JMP prends une adresse en argument");
	
	return true;
    }

}
