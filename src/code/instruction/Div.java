package instruction;

import traduction.RedCodeException;

public class Div extends Instruction {

	public Div(int a, int b, int c, int d) {
		super(a, b, c, d);
	}
	
	@Override
	public String toString() {
		return "DIV"+super.toString();
	}

	@Override
	public boolean estValide() {    // DIV  #A  @_B
	if( getAdMode1()!=2 || ( getAdMode2()!=1 && getAdMode2()!=3 ) )		
	    throw new RedCodeException("DIV prend une valeur en premier arg et une adresse en 2 eme argument");

		return true;
	}

}
