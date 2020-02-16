package instruction;

import traduction.RedCodeException;

public class Stp extends Instruction {

	public Stp(int a, int b, int c, int d) {
		super(a, b, c, d);
	}

	@Override
	public String toString() {
		return "STP"+super.toString();
	}

	@Override
	public boolean estValide() {
		if (getAdMode1()==0|| getAdMode2()==0) throw new RedCodeException("STP prends deux arguments");
		if (getAdMode2()==2) throw new RedCodeException("STP prend une adresse en second argument");
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
