package instruction;
import traduction.Traducteur94;
import traduction.RedCodeException;

public class Jmn94 extends Instruction {

	public Jmn94(int a, int b, int c, int d, int e) {
		super(a, b, c, d, e);
	}

	@Override
	public String toString() {
		return "JMN"+super.toString();
	}


	@Override
	public boolean estValide() {
		if(getAdMode1()==0 || getAdMode2()==0)throw new RedCodeException("JMN prend deux arguments");
		if(getAdMode2()==2)throw new RedCodeException("JMN prend une adresse en second argument");
		if (getType()>2){
			System.out.println(this);
			throw new RedCodeException("JMN ne peut etre de type Ab, BA, F, X ou I");
		}

		return true;
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


	protected int noneType(long [] memory, int read){
		return aType(memory, read);
	}

	protected int aType(long[] memory, int read){
		int a=0;
		switch (getAdMode2()){
			case 1:a=getArg2();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg1()==0){return read+1;}break;
			case 2:if (getArg2()==0){return read+1;}break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+a+getArg1())%memory.length]);if (instru.getArg1()==0){return read+1;} break;
			case 4:setArg2(getArg2()-1);a=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg1()==0){return read+1;}break;
			case 5:setArg2(getArg2()+1);a=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg1()==0){return read+1;}break;
			case 6:a=getArg2();setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg1()==0){return read+1;}break;
			case 7:a=getArg2();setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg1()==0){return read+1;}break;
			default: return -1;
		}
		switch (getAdMode1()){
			case 1:a=getArg1();break;
			case 3:Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]); if(instru instanceof Dat94){a=instru.getArg1()+getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}break;
			case 4:setArg1(getArg1()-1);a=getArg1();break;
			case 5:setArg1(getArg1()+1);a=getArg1();break;
			case 6:a=getArg1();setArg1(getArg1()-1);break;
			case 7:a=getArg1();setArg1(getArg1()+1);break;
			default: return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		read+=a;
		if (read<0) read=memory.length+read;
		return read;
	}

	protected int bType(long[] memory, int read){
		int a=0;
		switch (getAdMode2()){
			case 1:a=getArg2();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg2()==0){return read+1;}break;
			case 2:if (getArg2()==0){return read+1;}break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+a+getArg1())%memory.length]);if (instru.getArg2()==0){return read+1;} break;
			case 4:setArg2(getArg2()-1);a=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg2()==0){return read+1;}break;
			case 5:setArg2(getArg2()+1);a=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg2()==0){return read+1;}break;
			case 6:a=getArg2();setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg2()==0){return read+1;}break;
			case 7:a=getArg2();setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);if (instru.getArg2()==0){return read+1;}break;
			default: return -1;
		}
		switch (getAdMode1()){
			case 1:a=getArg1();break;
			case 3:Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]); if(instru instanceof Dat94){a=instru.getArg1()+getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}break;
			case 4:setArg1(getArg1()-1);a=getArg1();break;
			case 5:setArg1(getArg1()+1);a=getArg1();break;
			case 6:a=getArg1();setArg1(getArg1()-1);break;
			case 7:a=getArg1();setArg1(getArg1()+1);break;
			default: return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		read+=a;
		if (read<0) read=memory.length+read;
		return read;
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
		return read+1;
	}

}
