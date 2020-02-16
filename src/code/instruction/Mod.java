package instruction;

import traduction.RedCodeException;

public class Mod extends Instruction {

	public Mod(int a, int b, int c, int d) {
		super(a, b, c, d);
	}
	
	@Override
	public String toString() {
		return "MOD"+super.toString();
	}

	@Override
	public boolean estValide() {     // MOD  #A  @_B
	if( getAdMode1()!=2 || ( getAdMode2()!=1 && getAdMode2()!=3 ) )		
	    throw new RedCodeException("MOD prend une valeur en premier arg et une adresse en 2 eme argument");
        
		return true;
	}

}
