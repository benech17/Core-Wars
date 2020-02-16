package instruction;
import traduction.Traducteur94;
import traduction.RedCodeException;

public class Mov94 extends Instruction {

	public Mov94(int a, int b,int c, int d, int e) {
		super(a,b,c,d, e);
	}

	@Override
	public String toString() {
		return "MOV"+super.toString();
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

	protected int noneType(long[] memory, int read){
		return iType(memory, read);
	}







	protected int aType(long[] memory, int read){
		int tmp;
		switch (getAdMode1()){
			case 2:tmp=getArg1();break;
			case 1:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 4:this.setArg1(getArg1()-1);tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 5:this.setArg1(getArg1()+1);tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 6:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();this.setArg1(getArg1()-1);break;
			case 7:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();this.setArg1(getArg1()+1);break;
			case 3:
				Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
				if (instru instanceof Dat94){
					tmp=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg1())%memory.length]).getArg1();
					break;
				}
			default: return -1;
		}
		switch (getAdMode2()){
			case 1:Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 6:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);this.setArg2(getArg2()-1);break;
			case 7:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);this.setArg2(getArg2()-1);break;
			case 4:this.setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 5:this.setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				Instruction ins;
				if (instru instanceof Dat94){
					ins=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg2())%memory.length]);
					ins.setArg1(tmp);
					ins.estValide();
					memory[(read+getArg2()+instru.getArg1())%memory.length]=Traducteur94.traduireInstructionToLong(ins);
					break;
				}
				else return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		return read+1;
	}








	protected int bType(long[] memory, int read){
		int tmp;
		switch (getAdMode1()){
			case 2:tmp=getArg1();break;
			case 1:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 4:this.setArg1(getArg1()-1);tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 5:this.setArg1(getArg1()+1);tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 6:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();this.setArg1(getArg1()-1);break;
			case 7:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();this.setArg1(getArg1()+1);break;
			case 3:
				Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
				if (instru instanceof Dat94){
					tmp=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg1())%memory.length]).getArg2();
					break;
				}
			default: return -1;
		}
		switch (getAdMode2()){
			case 1:Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 6:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);this.setArg2(getArg2()-1);break;
			case 7:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);this.setArg2(getArg2()-1);break;
			case 4:this.setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 5:this.setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				Instruction ins;
				if (instru instanceof Dat94){
					ins=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg2())%memory.length]);
					ins.setArg1(tmp);
					ins.estValide();
					memory[(read+getArg2()+instru.getArg1())%memory.length]=Traducteur94.traduireInstructionToLong(ins);
					break;
				}
				else return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		return read+1;
	}






	protected int abType(long[] memory, int read){
		int tmp;
		switch (getAdMode1()){
			case 2:tmp=getArg1();break;
			case 1:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 4:this.setArg1(getArg1()-1);tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 5:this.setArg1(getArg1()+1);tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 6:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();this.setArg1(getArg1()-1);break;
			case 7:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();this.setArg1(getArg1()+1);break;
			case 3:
				Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
				if (instru instanceof Dat94){
					tmp=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg1())%memory.length]).getArg1();
					break;
				}
			default: return -1;
		}
		switch (getAdMode2()){
			case 1:Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 6:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);this.setArg2(getArg2()-1);break;
			case 7:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);this.setArg2(getArg2()-1);break;
			case 4:this.setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 5:this.setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				Instruction ins;
				if (instru instanceof Dat94){
					ins=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg2())%memory.length]);
					ins.setArg1(tmp);
					ins.estValide();
					memory[(read+getArg2()+instru.getArg1())%memory.length]=Traducteur94.traduireInstructionToLong(ins);
					break;
				}
				else return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		return read+1;
	}










	protected int baType(long[] memory, int read){System.out.println("mov94ab +read="+read);
		int tmp;
		switch (getAdMode1()){
			case 2:tmp=getArg1();break;
			case 1:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 4:this.setArg1(getArg1()-1);tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 5:this.setArg1(getArg1()+1);tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 6:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();this.setArg1(getArg1()-1);break;
			case 7:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();this.setArg1(getArg1()+1);break;
			case 3:
				Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
				if (instru instanceof Dat94){
					tmp=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg1())%memory.length]).getArg2();
					break;
				}
			default: return -1;
		}
		switch (getAdMode2()){
			case 1:Instruction instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 6:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);this.setArg2(getArg2()-1);break;
			case 7:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);this.setArg2(getArg2()-1);break;
			case 4:this.setArg2(getArg2()-1);instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 5:this.setArg2(getArg2()+1);instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				Instruction ins;
				if (instru instanceof Dat94){
					ins=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg2())%memory.length]);
					ins.setArg1(tmp);
					ins.estValide();
					memory[(read+getArg2()+instru.getArg1())%memory.length]=Traducteur94.traduireInstructionToLong(ins);
					break;
				}
				else return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		return read+1;
	}












	protected int fType(long[] memory, int read){
		if (getAdMode1()==4)setArg1(getArg1()-1);
		if (getAdMode2()==4)setArg2(getArg2()-1);
		if (getAdMode1()==5)setArg1(getArg1()+1);
		if (getAdMode2()==5)setArg2(getArg2()+1);
		int tmp;
		Instruction instru;
		switch(getAdMode1()){
			case 1:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 4:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 6:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 5:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 7:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 2:tmp=getArg1();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
			if (instru instanceof Dat94){
				tmp=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg1())%memory.length]).getArg2();
				break;
			}
			default:return -1;
		}
		switch (getAdMode2()){
			case 1:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 6:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 7:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 4:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 5:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				Instruction ins;
				if (instru instanceof Dat94){
					ins=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg2())%memory.length]);
					ins.setArg2(tmp);
					ins.estValide();
					memory[(read+getArg2()+instru.getArg1())%memory.length]=Traducteur94.traduireInstructionToLong(ins);
					break;
				}
			default: return -1;
		}



		switch(getAdMode1()){
			case 1:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 4:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 6:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 5:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 7:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 2:tmp=getArg1();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
			if (instru instanceof Dat94){
				tmp=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg1())%memory.length]).getArg2();
				break;
			}
			default:return -1;
		}
		switch (getAdMode2()){
			case 1:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 6:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 7:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 4:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 5:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				Instruction ins;
				if (instru instanceof Dat94){
					ins=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg2())%memory.length]);
					ins.setArg1(tmp);
					ins.estValide();
					memory[(read+getArg2()+instru.getArg1())%memory.length]=Traducteur94.traduireInstructionToLong(ins);
					break;
				}
			default: return -1;
		}


		if (getAdMode1()==6)setArg1(getArg1()-1);
		if (getAdMode1()==7)setArg1(getArg1()+1);
		if (getAdMode2()==6)setArg2(getArg2()-1);
		if (getAdMode2()==7)setArg2(getArg2()+1);
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		return read+1;
	}










	protected int xType(long[] memory, int read){
		if (getAdMode1()==4)setArg1(getArg1()-1);
		if (getAdMode2()==4)setArg2(getArg2()-1);
		if (getAdMode1()==5)setArg1(getArg1()+1);
		if (getAdMode2()==5)setArg2(getArg2()+1);
		int tmp;
		Instruction instru;
		switch(getAdMode1()){
			case 1:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 4:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 6:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 5:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 7:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg2();break;
			case 2:tmp=getArg1();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
			if (instru instanceof Dat94){
				tmp=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg1())%memory.length]).getArg2();
				break;
			}
			default:return -1;
		}
		switch (getAdMode2()){
			case 1:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 6:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 7:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 4:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 5:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg1(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				Instruction ins;
				if (instru instanceof Dat94){
					ins=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg2())%memory.length]);
					ins.setArg1(tmp);
					ins.estValide();
					memory[(read+getArg2()+instru.getArg1())%memory.length]=Traducteur94.traduireInstructionToLong(ins);
					break;
				}
			default: return -1;
		}


		switch(getAdMode1()){
			case 1:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 4:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 6:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 5:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 7:tmp=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]).getArg1();break;
			case 2:tmp=getArg1();break;
			case 3:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
			if (instru instanceof Dat94){
				tmp=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg1())%memory.length]).getArg2();
				break;
			}
			default:return -1;
		}
		switch (getAdMode2()){
			case 1:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 6:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 7:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 4:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 5:instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);instru.setArg2(tmp);instru.estValide();memory[(read+getArg2())%memory.length]=Traducteur94.traduireInstructionToLong(instru);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				Instruction ins;
				if (instru instanceof Dat94){
					ins=Traducteur94.traduireLongToInstruction(memory[(read+instru.getArg1()+getArg2())%memory.length]);
					ins.setArg2(tmp);
					ins.estValide();
					memory[(read+getArg2()+instru.getArg1())%memory.length]=Traducteur94.traduireInstructionToLong(ins);
					break;
				}
			default: return -1;
		}


		if (getAdMode1()==6)setArg1(getArg1()-1);
		if (getAdMode1()==7)setArg1(getArg1()+1);
		if (getAdMode2()==6)setArg2(getArg2()-1);
		if (getAdMode2()==7)setArg2(getArg2()+1);
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		return read+1;
	}







	protected int iType(long[] memory, int read){
		long tmp;
		Instruction instru;
		switch (getAdMode1()){
			case 1:tmp=memory[(read+getArg1())%memory.length];break;
			case 4:setArg1(getArg1()-1);tmp=memory[(read+getArg1())%memory.length];break;
			case 5:setArg1(getArg1()+1);tmp=memory[(read+getArg1())%memory.length];break;
			case 6:tmp=memory[(read+getArg1())%memory.length];setArg1(getArg1()-1);break;
			case 7:tmp=memory[(read+getArg1())%memory.length];setArg1(getArg1()+1);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg1())%memory.length]);
				if (instru instanceof Dat94){
					tmp=memory[(read+getArg1()+instru.getArg1())%memory.length];
					break;
				}
			default: return -1;
		}
		switch (getAdMode2()){
			case 1:memory[(read+getArg2())%memory.length]=tmp;break;
			case 4:setArg2(getArg2()-1);memory[(read+getArg2())%memory.length]=tmp;break;
			case 5:setArg2(getArg2()+1);memory[(read+getArg2())%memory.length]=tmp;break;
			case 6:memory[(read+getArg2())%memory.length]=tmp;setArg2(getArg2()-1);break;
			case 7:memory[(read+getArg2())%memory.length]=tmp;setArg2(getArg2()+1);break;
			case 3:
				instru=Traducteur94.traduireLongToInstruction(memory[(read+getArg2())%memory.length]);
				if (instru instanceof Dat94){
					memory[(read+instru.getArg2()+getArg2())%memory.length]=tmp;
					break;
				}
			default: return -1;
		}
		memory[read%memory.length]=Traducteur94.traduireInstructionToLong(this);
		return read+1;
	}




	@Override
	public boolean estValide() {
		if(getAdMode2()==0 || getAdMode2()==2) {
			System.out.println(this);
			throw new RedCodeException("MOV prend une adresses en second argument");
		}
    if (getAdMode1()==0){
      System.out.println(this);
      throw new RedCodeException("MOV prend deux arguments");
    }
		return true;
	}

}
