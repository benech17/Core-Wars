package instruction;
import traduction.Traducteur94;
import traduction.RedCodeException;

public class Seq94 extends Instruction {

	public Seq94(int a, int b, int c, int d, int e) {
		super(a, b, c, d, e);
	}

	@Override
	public String toString() {
		return "SEQ"+super.toString();
	}



	@Override
	public boolean estValide() {
		if(getAdMode1()==0 || getAdMode2()==0)throw new RedCodeException("SEQ prend deux arguments");
  	if (getType()>4){
      System.out.println(this);
      throw new RedCodeException("SEQ ne peut etre de type F, X ou I");
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

	public int noneType(long[] memory, int read){
		return aType(memory, read);
	}

	protected int aType(long[] memory, int read){
		int a =0;
		int b=0;
		switch (getAdMode2()){
			case 1:b=getArg2();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			case 2:b=getArg2();break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+a)%memory.length]);b=instru.getArg1(); break;
			case 4:setArg2(getArg2()-1);b=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			case 5:setArg2(getArg2()+1);b=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			case 6:b=getArg2();setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			case 7:b=getArg2();setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			default: return -1;
		}
		switch (getAdMode1()){
			case 1:a=getArg1();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			case 2:a=getArg1();break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1()+a)%memory.length]);a=instru.getArg1(); break;
			case 4:setArg1(getArg1()-1);a=getArg1();instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			case 5:setArg1(getArg1()+1);a=getArg1();instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			case 6:a=getArg1();setArg1(getArg1()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			case 7:a=getArg1();setArg1(getArg1()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			default: return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		if (a==b)	return read+2;
		return read+1;
	}

	protected int bType(long[] memory, int read){
		int a =0;
		int b=0;
		switch (getAdMode2()){
			case 1:b=getArg2();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 2:b=getArg2();break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg2();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+a)%memory.length]);b=instru.getArg2(); break;
			case 4:setArg2(getArg2()-1);b=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 5:setArg2(getArg2()+1);b=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 6:b=getArg2();setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 7:b=getArg2();setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			default: return -1;
		}
		switch (getAdMode1()){
			case 1:b=getArg1();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 2:b=getArg1();break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg2();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+a)%memory.length]);b=instru.getArg2(); break;
			case 4:setArg1(getArg1()-1);b=getArg1();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 5:setArg1(getArg1()+1);b=getArg1();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 6:b=getArg1();setArg1(getArg1()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 7:b=getArg1();setArg1(getArg1()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			default: return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		if (a==b)	return read+2;
		return read+1;
	}


	//
	protected int abType(long[] memory, int read){
		int a =0;
		int b=0;
		switch (getAdMode2()){
			case 1:b=getArg2();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 2:b=getArg2();break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg2();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+a)%memory.length]);b=instru.getArg2(); break;
			case 4:setArg2(getArg2()-1);b=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 5:setArg2(getArg2()+1);b=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 6:b=getArg2();setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 7:b=getArg2();setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			default: return -1;
		}
		switch (getAdMode1()){
			case 1:a=getArg1();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			case 2:a=getArg1();break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1()+a)%memory.length]);a=instru.getArg1(); break;
			case 4:setArg1(getArg1()-1);a=getArg1();instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			case 5:setArg1(getArg1()+1);a=getArg1();instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			case 6:a=getArg1();setArg1(getArg1()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			case 7:a=getArg1();setArg1(getArg1()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+a)%memory.length]);a=instru.getArg1();break;
			default: return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		if (a==b)	return read+2;
		return read+1;
	}
	protected int baType(long[] memory, int read){
		int a =0;
		int b=0;
		switch (getAdMode2()){
			case 1:b=getArg2();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			case 2:b=getArg2();break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+a)%memory.length]);b=instru.getArg1(); break;
			case 4:setArg2(getArg2()-1);b=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			case 5:setArg2(getArg2()+1);b=getArg2();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			case 6:b=getArg2();setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			case 7:b=getArg2();setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg1();break;
			default: return -1;
		}
		switch (getAdMode1()){
			case 1:b=getArg1();Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 2:b=getArg1();break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]); if(sbl instanceof Dat94){a=sbl.getArg2();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+a)%memory.length]);b=instru.getArg2(); break;
			case 4:setArg1(getArg1()-1);b=getArg1();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 5:setArg1(getArg1()+1);b=getArg1();instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 6:b=getArg1();setArg1(getArg1()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			case 7:b=getArg1();setArg1(getArg1()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+b)%memory.length]);b=instru.getArg2();break;
			default: return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		if (a==b)	return read+2;
		return read+1;

	}



	protected int fType(long[] memory, int read){
		if (getAdMode1()==4)setArg1(getArg1()-1);
	if (getAdMode2()==4)setArg2(getArg2()-1);
	if (getAdMode1()==5)setArg1(getArg1()+1);
	if (getAdMode2()==5)setArg2(getArg2()+1);
		int a;int b;int c=0;
		Instruction instru;
		switch(getAdMode1()){
			case 1:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 4:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 6:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 5:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 7:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 2:a=getArg1();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);if(!(instru instanceof Dat94)){return -1;}a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1()+instru.getArg1())%memory.length]).getArg1();break;
			default:return -1;
		}
		switch(getAdMode2()){
			case 1:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 4:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 6:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 5:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 7:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 2:b=getArg2();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);if(!(instru instanceof Dat94)){return -1;}b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+instru.getArg1())%memory.length]).getArg1();break;
			default:return -1;
		}
		if (a==b)c=read+2;


		switch(getAdMode1()){
			case 1:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 4:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 6:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 5:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 7:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 2:a=getArg1();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);if(!(instru instanceof Dat94)){return -1;}a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1()+instru.getArg1())%memory.length]).getArg2();break;
			default:return -1;
		}
		switch(getAdMode2()){
			case 1:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 4:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 6:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 5:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 7:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 2:b=getArg2();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);if(!(instru instanceof Dat94)){return -1;}b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+instru.getArg2())%memory.length]).getArg1();break;
			default:return -1;
		}
		if (getAdMode1()==6)setArg1(getArg1()-1);
		if (getAdMode1()==7)setArg1(getArg1()+1);
		if (getAdMode2()==6)setArg2(getArg2()-1);
		if (getAdMode2()==7)setArg2(getArg2()+1);
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		if (a==b & c==read+2) return c;
		return read+1;
	}










	protected int xType(long[] memory, int read){
		if (getAdMode1()==4)setArg1(getArg1()-1);
		if (getAdMode2()==4)setArg2(getArg2()-1);
		if (getAdMode1()==5)setArg1(getArg1()+1);
		if (getAdMode2()==5)setArg2(getArg2()+1);
		int a;int b;int c=0;
		Instruction instru;
		switch(getAdMode1()){
			case 1:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 4:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 6:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 5:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 7:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 2:a=getArg1();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);if(!(instru instanceof Dat94)){return -1;}a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1()+instru.getArg1())%memory.length]).getArg2();break;
			default:return -1;
		}
		switch(getAdMode2()){
			case 1:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 4:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 6:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 5:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 7:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg1();break;
			case 2:b=getArg2();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);if(!(instru instanceof Dat94)){return -1;}b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+instru.getArg1())%memory.length]).getArg1();break;
			default:return -1;
		}
		if (a==b)c=read+2;

		switch(getAdMode1()){
			case 1:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 4:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 6:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 5:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 7:a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 2:a=getArg1();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);if(!(instru instanceof Dat94)){return -1;}a=Traducteur94.traduireLongToInstruction(memory[(read+getArg1()+instru.getArg1())%memory.length]).getArg1();break;
			default:return -1;
		}
		switch(getAdMode2()){
			case 1:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 4:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 6:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 5:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 7:b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]).getArg2();break;
			case 2:b=getArg2();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);if(!(instru instanceof Dat94)){return -1;}b=Traducteur94.traduireLongToInstruction(memory[(read+getArg2()+instru.getArg2())%memory.length]).getArg1();break;
			default:return -1;
		}
		if (getAdMode1()==6)setArg1(getArg1()-1);
		if (getAdMode1()==7)setArg1(getArg1()+1);
		if (getAdMode2()==6)setArg2(getArg2()-1);
		if (getAdMode2()==7)setArg2(getArg2()+1);
 		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		if (a==b & c==read+2) return c;
		return read+1;
	}





	protected int iType(long[] memory, int read){
		long a;long b;int ab;
		switch (getAdMode2()){
			case 1:b=memory[(read+getArg2())%memory.length];break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]); if(sbl instanceof Dat94){ab=sbl.getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}b=memory[(read+getArg2()+ab)%memory.length]; break;
			case 4:setArg2(getArg2()-1);b=memory[(read+getArg2())%memory.length];break;
			case 5:setArg2(getArg2()+1);b=memory[(read+getArg2())%memory.length];break;
			case 6:b=memory[(read+getArg2())%memory.length];setArg2(getArg2()-1);break;
			case 7:b=memory[(read+getArg2())%memory.length];setArg2(getArg2()+1);break;
			default: return -1;
		}
		switch (getAdMode1()){
			case 1:a=memory[(read+getArg1())%memory.length];break;
			case 3:Instruction sbl=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]); if(sbl instanceof Dat94){ab=sbl.getArg1();}else{System.out.println("Adressage indirect doit rediriger vers un Dat94");return -1;}a=memory[(read+getArg1()+ab)%memory.length]; break;
			case 4:setArg1(getArg1()-1);a=memory[(read+getArg1())%memory.length];break;
			case 5:setArg1(getArg1()+1);a=memory[(read+getArg1())%memory.length];break;
			case 6:a=memory[(read+getArg1())%memory.length];setArg1(getArg1()-1);break;
			case 7:a=memory[(read+getArg1())%memory.length];setArg1(getArg1()+1);break;
			default: return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		if (a==b) return read+2;
		return read+1;
	}
}
