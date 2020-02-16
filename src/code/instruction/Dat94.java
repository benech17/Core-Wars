package instruction;
import traduction.Traducteur94;
import traduction.RedCodeException;

public class Dat94 extends Instruction {

	public Dat94(int a, int b, int c, int d, int e) {
		super(a, b, c, d, e);
	}

	@Override
	public String toString() {
		return "DAT"+super.toString();
	}

	public int execute(long[] memory, int read){
		return -1;
	}

	protected int noneType(long[] memory, int read){
		return iType(memory, read);
	}

	protected int aType(long[] memory, int read){
		return read++;
	}

	protected int bType(long[] memory, int read){
		return read++;
	}
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

		if (getAdMode1()==0 || getAdMode2()==0){ throw new RedCodeException("DAT prend deux arguments");}
		if (getType()!=0) throw new RedCodeException("Dat n'a pas de type");
		return true;
	}

}
