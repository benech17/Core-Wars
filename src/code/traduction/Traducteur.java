package traduction;

import instruction.*;

import java.util.ArrayList;
public class Traducteur {

	// les opérandes doivent etre entier et compris entre -8191 et 8192
	/* Passage d'une instruction à un long et réciproquement. */
	public static Instruction traduireLongToInstruction(long ins) {
		long bug=ins;

		int d=(int)(ins%(1<<15));
		ins/=(1<<15);
		int c=(int)(ins%(1<<4));
		ins/=(1<<4);
		int b=(int)(ins%(1<<15));
		ins/=(1<<15);
		int a=(int)(ins%(1<<4));
		ins/=(1<<4);
		int tmp=(int)(ins%(1<<5));

		if(b>(int)1<<13)b=-1*(b-((int)1<<13));
		if(d>(int)1<<13)d=-1*(d-((int)1<<13));

		

		try {
			switch(tmp) {
			case 1:return new Mov(a,b,c,d);
			case 2:return new Add(a,b,c,d);
			case 3:return new Dat(a,b,c,d);
			case 4:return new Sub(a,b,c,d);
			case 5:return new Mul(a,b,c,d);
			case 6:return new Div(a,b,c,d);
			case 7:return new Mod(a,b,c,d);
			case 8:return new Jmp(a,b,c,d);
			case 9:return new Jmz(a,b,c,d);
			case 10:return new Jmn(a,b,c,d);
			case 11:return new Djn(a,b,c,d);
			case 12:return new Spl(a,b,c,d);
			case 13:return new Cmp(a,b,c,d);
			case 14:return new Seq(a,b,c,d);
			case 15:return new Sne(a,b,c,d);
			case 16:return new Slt(a,b,c,d);
			case 17:return new Stp(a,b,c,d);
			case 18:return new Ldp(a,b,c,d);
			case 0:return new Nop(a,b,c,d);
			default: throw new RedCodeException(bug+" n'est pas une instruction valide");
			}
		}catch(RedCodeException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		return null;

	}

	public static long traduireInstructionToLong(Instruction instru) {

		int d=instru.getArg2();
		int b=instru.getArg1();
		if (b<0){b=(1<<13) -b;System.out.println("change b");}
		if (d<0){d=(1<<13)-d;System.out.println("change d");}









		int a=0;
		if(instru instanceof Mov) a=1;
		if(instru instanceof Add) a=2;
		if(instru instanceof Dat) a=3;
		if(instru instanceof Sub) a=4;
		if(instru instanceof Mul) a=5;
		if(instru instanceof Div) a=6;
		if(instru instanceof Mod) a=7;
		if(instru instanceof Jmp) a=8;
		if(instru instanceof Jmz) a=9;
		if(instru instanceof Jmn) a=10;
		if(instru instanceof Djn) a=11;
		if(instru instanceof Spl) a=12;
		if(instru instanceof Cmp) a=13;
		if(instru instanceof Seq) a=14;
		if(instru instanceof Sne) a=15;
		if(instru instanceof Slt) a=16;
		if(instru instanceof Stp) a=17;
		if(instru instanceof Ldp) a=18;




		long res=d;
		res+=a*(long)1<<38;
		res+=instru.getAdMode2()*(long)1<<15;
		res+=b*(long)(1<<19);
    res+=instru.getAdMode1()*((long)1<<34);

		return res;
	}

	//Traduction d'un programme complet originel                            */
	// D'un programme textuel à un tableau d'instruction
	public static ArrayList<Instruction> traduireCodeToInstructions (String programme) {
		String[] prog= programme.split("\n");
		ArrayList<Instruction> instructions=new ArrayList<Instruction>();
		for (int i=0; i< prog.length; i++) {
			try {
				if (prog[i]!="")instructions.add(i, traduireLigne(prog[i],i+1));
			}catch(RedCodeException e) {
				e.printStackTrace();
				System.out.println("Erreur De Traduction Code To Instructions");
			}

		}
		return instructions;
	}

	// transforme ligne de redcode en Instruction
	//utilisé uniquement par appel de traductionProgramme
	private static Instruction traduireLigne(String ligne, int i) {
		String[] tab=ligne.split(" ");
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;

		int[] tmp=operande(tab[1],i,1);
		a=tmp[0];
		b=tmp[1];



		if (tab.length>2) {
			tmp=operande(tab[2],i,2);
			c=tmp[0];
			d=tmp[1];
		}
	
		b%=((int)1<<13);
		d%=((int)1<<13);

		try {
			String temp=Character.toString(tab[0].charAt(0));
			temp=temp+Character.toString(tab[0].charAt(1));
			temp=temp+Character.toString(tab[0].charAt(2));
			switch(temp) {
			case "MOV":return new Mov(a,b,c,d);
			case "ADD":return new Add(a,b,c,d);
			case "DAT":return new Dat(a,b,c,d);
			case "SUB":return new Sub(a,b,c,d);
			case "MUL":return new Mul(a,b,c,d);
			case "DIV":return new Div(a,b,c,d);
			case "MOD":return new Mod(a,b,c,d);
			case "JMP":return new Jmp(a,b,c,d);
			case "JMZ":return new Jmz(a,b,c,d);
			case "JMN":return new Jmn(a,b,c,d);
			case "DJN":return new Djn(a,b,c,d);
			case "SPL":return new Spl(a,b,c,d);
			case "CMP":return new Cmp(a,b,c,d);
			case "SEQ":return new Seq(a,b,c,d);
			case "SNE":return new Sne(a,b,c,d);
			case "SLT":return new Slt(a,b,c,d);
			case "STP":return new Stp(a,b,c,d);
			case "LDP":return new Ldp(a,b,c,d);
			case "NOP":return new Nop(a,b,c,d);
			default: throw new RedCodeException("Ligne "+i+" non valide");

			}
		}catch(RedCodeException ex ){
			System.out.println("Erreur De Traduction De Ligne");
			System.exit(0);
		}
		return null;
	}

	//sous fonction
	private static int[] operande( String operande, int n, int m) {
		int[]res= new int[2];
		String[]op=operande.split(",");
		try {
			switch(Character.toString(op[0].charAt(0))) {
			case "#":{res[0]=2; op[0]=op[0].substring(1);break;}
			case "@":{res[0]=3; op[0]=op[0].substring(1);break;}
      case "<":{res[0]=4; op[0]=op[0].substring(1);break;}
			case ">":{res[0]=5; op[0]=op[0].substring(1);break;}
			case "{":{res[0]=6; op[0]=op[0].substring(1);break;}
			case "}":{res[0]=7; op[0]=op[0].substring(1);break;}
			default: res[0]=1;
			}

		}catch(StringIndexOutOfBoundsException e) {
			System.out.println("Ligne "+n+": opérande "+m+" à trop d'espaces");
			e.printStackTrace();
			System.exit(0);
		}
		try {
			res[1]=Integer.valueOf(op[0]);
		}catch(NumberFormatException e) {
			System.out.println("Ligne "+n+": opérande "+m+" invalide");
			e.printStackTrace();
			System.exit(0);
		}
		return res;
	}

	

}
