package instruction;
import traduction.Traducteur94;
import traduction.RedCodeException;

public class Nop94 extends Instruction {

	public Nop94(int a, int b, int c, int d, int e) {
		super(a, b, c, d, e);
	}

	@Override
	public String toString() {
		return "NOP";
	}

	public int execute(long[] memory, int read){
		return -1;
	}

	protected int noneType(long[] memory, int read){
		return iType(memory, read);
	}

	protected int aType(long[] memory, int read){
		return read+1;
	}

	protected int bType(long[] memory, int read){
		return read+1;
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
		return read+1;
	}

	@Override
	public boolean estValide() {
		if (getAdMode1()!=0 || getArg1()!=0 || getAdMode2()!=0 || getArg2()!=0) throw new RedCodeException("NOP ne prends pas d'arguments");
		return true;
	}


}
