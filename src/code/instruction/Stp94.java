package instruction;
import traduction.Traducteur94;
import traduction.RedCodeException;

public class Stp94 extends Instruction {

	public Stp94(int a, int b, int c, int d, int e) {
		super(a, b, c, d, e);
	}

	@Override
	public String toString() {
		return "STP"+super.toString();
	}

	public int execute(long[] memory, int read){
		switch (getType()){
			case 0:return noneType(memory,read);
			case 1:return aType(memory,read);
			case 2:return bType(memory,read);
			case 3:return abType(memory,read);
			case 4:return baType(memory,read);
			case 6:return xType(memory,read);
			case 5:return fType(memory,read);
			case 7:return iType(memory,read);
			default:return -1;
		}
	}

	public int noneType(long[] memory, int read){
		return aType(memory, read);
	}

	protected int aType(long[] memory, int read){
		return read++;
	}

	protected int bType(long[] memory, int read){
		return read++;
	}


	//
	protected int abType(long[] memory, int read){
		return read;
	}
	protected int baType(long[] memory, int read){
		return read;
	}
	protected int fType(long[] memory, int read){
		return read;
	}
	protected int xType(long[] memory, int read){
		return read;
	}
	protected int iType(long[] memory, int read){
		return read++;
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
