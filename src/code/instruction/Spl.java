package instruction;

import traduction.RedCodeException;

public class Spl extends Instruction {

	public Spl(int a, int b, int c, int d) {
		super(a, b, c, d);
	}

	@Override
	public String toString() {
		return "SPL"+super.toString();
	}

	@Override
	public boolean estValide() {
		if(getAdMode1()==0 || getAdMode1()==2) throw new RedCodeException("SPL prends une adresse en argument 1");
		if(getAdMode2()!=0 || getArg2()!=0) throw new RedCodeException("SPL ne prend qu'un argument");
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
