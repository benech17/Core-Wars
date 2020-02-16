package instruction;

import traduction.RedCodeException;

public class Mul extends Instruction {

	public Mul(int a, int b, int c, int d) {
		super(a, b, c, d);
	}

	@Override
	public String toString() {
		return "MUL"+super.toString();
	}

	@Override
	public boolean estValide() {    // MUL  #A  @_B
	if( getAdMode1()!=2 || ( getAdMode2()!=1 && getAdMode2()!=3 ) )		
	    throw new RedCodeException("MUL prend une valeur en premier arg et une adresse en 2 eme argument");
           
		return true;
	}

}
