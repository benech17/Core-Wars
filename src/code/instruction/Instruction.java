package instruction;

import traduction.RedCodeException;

public abstract class Instruction {
	private int op1ad;
	private int op1;
	private int op2ad;
	private int op2;
	private int type;


	public int getAdMode1() {
		return op1ad;
	}

	public int getArg1() {
		return op1;
	}
	public  void setArg1(int a){
		op1=a;
	}
	public  void setArg2(int a){
		op2=a;
	}
	public int getAdMode2() {
		return op2ad;
	}

	public int getArg2() {
		return op2;
	}
	public int getType() {
		return type;
	}

	public Instruction(int a, int b, int c, int d,int e) {
		op1ad=a;
		op1=b;
		op2ad=c;
		op2=d;
		type=e;
		estValide();
	}

	public Instruction(int a, int b, int c, int d) {
		op1ad=a;
		op1=b;
		op2ad=c;
		op2=d;
		type=0;
		estValide();
	}

	public abstract boolean estValide() throws RedCodeException;// vérifie la cohérence des arguments

	public String toString() {
		String a="";
		switch(getType()){
			case 0:break;
			case 1:a+=".A";break;
			case 2:a+=".B";break;
			case 3:a+=".AB";break;
			case 4:a+=".BA";break;
			case 5:a+=".F";break;
			case 6:a+=".X";break;
			case 7:a+=".I";break;
			default: throw new RedCodeException("Type invalide "+getType());
		}

		switch (op1ad) {
		case 1:a+=" ";break;
		case 2:a+=" #";break;
		case 3:a+=" @";break;
        case 4:a+=", <";break;
		case 5:a+=", >";break;
		case 6:a+=", {";break;
		case 7:a+=", }";break;
		default: System.exit(op1ad);//  Instruction non valide
		}
		a+=Integer.toString(op1);
		if (op2ad!=0 ) {
			switch(op2ad) {
			case 1:a+=", ";break;
			case 2:a+=", #";break;
			case 3:a+=", @";break;
      case 4:a+=", <";break;
			case 5:a+=", >";break;
			case 6:a+=", {";break;
			case 7:a+=", }";break;
			default:throw new RedCodeException("pas le bon mode d'adressage en 2");
			}
			a+=Integer.toString(op2);
		}
		return a;
	}



	public int execute(long[] memory, int read){return -1;}

	protected int aType(long[] memory, int read){return -1;}
	protected int bType(long[] memory, int read){return -1;}
	protected int abType(long[] memory, int read){return -1;}
	protected int baType(long[] memory, int read){return -1;}
	protected int xType(long[] memory, int read){return -1;}
	protected int fType(long[] memory, int read){return -1;}
	protected int iType(long[] memory, int read){return -1;}


}
