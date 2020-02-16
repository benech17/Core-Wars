package traduction;
import instruction.*;
import java.util.ArrayList;

public class Traducteur94 {

	// les opérandes doivent etre entier et compris entre -8191 et 8192
	/* Passage d'une instruction à un long et réciproquement. */
	public static Instruction traduireLongToInstruction(long ins) {
		System.out.println("Traduction Long To Instruction : "+ins);
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

		ins/=(int)1<<5;
		int e=(int)ins;

		try {
			switch(tmp) {
			case 1:return new Mov94(a,b,c,d,e);
			case 2:return new Add94(a,b,c,d,e);
			case 3:return new Dat94(a,b,c,d,e);
			case 4:return new Sub94(a,b,c,d,e);
			case 5:return new Mul94(a,b,c,d,e);
			case 6:return new Div94(a,b,c,d,e);
			case 7:return new Mod94(a,b,c,d,e);
			case 8:return new Jmp94(a,b,c,d,e);
			case 9:return new Jmz94(a,b,c,d,e);
			case 10:return new Jmn94(a,b,c,d,e);
			case 11:return new Djn94(a,b,c,d,e);
			case 12:return new Spl94(a,b,c,d,e);
			case 13:return new Cmp94(a,b,c,d,e);
			case 14:return new Seq94(a,b,c,d,e);
			case 15:return new Sne94(a,b,c,d,e);
			case 16:return new Slt94(a,b,c,d,e);
			case 17:return new Stp94(a,b,c,d,e);
			case 18:return new Ldp94(a,b,c,d,e);
			case 0:return new Nop94(a,b,c,d,e);
			default: throw new RedCodeException(bug+" n'est pas une instruction valide");
			}
		}catch(RedCodeException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		return null;

	}

	// problème de traduction pour # et @
	public static long traduireInstructionToLong(Instruction instru) {


		System.out.println("Traduire Instruction To Long : "+instru);
		int d=instru.getArg2();
		int b=instru.getArg1();
		if (b<0){b=(1<<13) -b;System.out.println("change b");}
		if (d<0){d=(1<<13)-d;System.out.println("change d");}









		int a=0;
		if(instru instanceof Nop94) a=0;
		if(instru instanceof Mov94) a=1;
		if(instru instanceof Add94) a=2;
		if(instru instanceof Dat94) a=3;
		if(instru instanceof Sub94) a=4;
		if(instru instanceof Mul94) a=5;
		if(instru instanceof Div94) a=6;
		if(instru instanceof Mod94) a=7;
		if(instru instanceof Jmp94) a=8;
		if(instru instanceof Jmz94) a=9;
		if(instru instanceof Jmn94) a=10;
		if(instru instanceof Djn94) a=11;
		if(instru instanceof Spl94) a=12;
		if(instru instanceof Cmp94) a=13;
		if(instru instanceof Seq94) a=14;
		if(instru instanceof Sne94) a=15;
		if(instru instanceof Slt94) a=16;
		if(instru instanceof Stp94) a=17;
		if(instru instanceof Ldp94) a=18;





		long res=d;
		res+=instru.getType()*((long)1<<43);
		res+=a*(long)1<<38;
		res+=instru.getAdMode2()*(long)1<<15;
		res+=b*(long)(1<<19);
    res+=instru.getAdMode1()*((long)1<<34);




		System.out.println("\t resultat = "+res);

		return res;
	}

	//Traduction d'un programme complet originel                            */
	// D'un programme textuel à un tableau d'instruction
	public static ArrayList<Instruction> traduireCodeToInstructions (String programme) {
		System.out.println("Traduire Code To Instructions: "+programme);
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

		for(int i=0; i<instructions.size(); i++) {
			System.out.println("\t"+instructions.get(i));
		}
		return instructions;
	}

	// transforme ligne de redcode en Instruction
	//utilisé uniquement par appel de traductionProgramme
	public static Instruction traduireLigne(String ligne, int i) {
		String[] tab=ligne.split(" ");
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;

		System.out.println(ligne);

		int[] tmp=operande(tab[1],i,1);
		a=tmp[0];
		b=tmp[1];



		if (tab.length>2) {
			tmp=operande(tab[2],i,2);
			c=tmp[0];
			d=tmp[1];
		}
		int e=0;
		try{
			switch(tab[0].substring(3)){
				case ".A":e=1;break;
				case ".B":e=2;break;
				case ".AB":e=3;break;
				case ".BA":e=4;break;
				case ".F":e=5;break;
				case ".X":e=6;break;
				case ".I":e=7;break;
				case "":e=0;break;
				default: throw new RedCodeException("Ligne "+i+" non valide");
			}
		}catch(RedCodeException ex) {
				System.out.println("Erreur De Traduction De Ligne");
				ex.printStackTrace();
				System.exit(0);
		}
		b%=((int)1<<13);
		d%=((int)1<<13);

		try {
			System.out.println("Traduire Ligne To Instruction : "+ligne);
			String temp=Character.toString(tab[0].charAt(0));
			temp=temp+Character.toString(tab[0].charAt(1));
			temp=temp+Character.toString(tab[0].charAt(2));
			switch(temp) {
			case "MOV":return new Mov94(a,b,c,d,e);
			case "ADD":return new Add94(a,b,c,d,e);
			case "DAT":return new Dat94(a,b,c,d,e);
			case "SUB":return new Sub94(a,b,c,d,e);
			case "MUL":return new Mul94(a,b,c,d,e);
			case "DIV":return new Div94(a,b,c,d,e);
			case "MOD":return new Mod94(a,b,c,d,e);
			case "JMP":return new Jmp94(a,b,c,d,e);
			case "JMZ":return new Jmz94(a,b,c,d,e);
			case "JMN":return new Jmn94(a,b,c,d,e);
			case "DJN":return new Djn94(a,b,c,d,e);
			case "SPL":return new Spl94(a,b,c,d,e);
			case "CMP":return new Cmp94(a,b,c,d,e);
			case "SEQ":return new Seq94(a,b,c,d,e);
			case "SNE":return new Sne94(a,b,c,d,e);
			case "SLT":return new Slt94(a,b,c,d,e);
			case "STP":return new Stp94(a,b,c,d,e);
			case "LDP":return new Ldp94(a,b,c,d,e);
			case "NOP":return new Nop94(a,b,c,d,e);
			default: throw new RedCodeException("Ligne "+i+" non valide");

			}
		}catch(RedCodeException ex ){
			System.out.println("Erreur De Traduction De Ligne");
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	//sous fonction
	private static int[] operande( String operande, int n, int m) {
		int[]res= new int[2];
		String[]op=operande.split(",");
		try {
			System.out.println(op[0]);
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

	//test
	public static void main(String [] args) {
	    //	System.out.println("Début");
	    //	System.out.println(traduireLigne("STP 2, @2",0));
		System.out.println(traduireLigne("MOV.A @0, 4",0));

	 	System.out.println(traduireLongToInstruction(traduireInstructionToLong(traduireLigne("JMZ.F @-2, 5",45))));

		//System.out.println(traduireLong(traduireInstruction(traduireLigne("SUB @20, 4",0))));

		//Instruction [] tab=traductionProgramme("MOV 0, 4");
		//System.out.println(traduireLong(traduireInstruction(tab[0])));
	   System.out.println("Success");
	}

}
