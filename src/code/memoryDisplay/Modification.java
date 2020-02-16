package memoryDisplay;


import instruction.Instruction;

public class Modification {
	 private int position;
	 private long value; // Dans le cas d'une instruction ne modifiant pas la memoire, cette variable contient -1
	 private int owner; // LA VALEUR -1 SPECIFIE QUE LA CASE N'ETE ECRITE PAR AUCUN PROGRAMME
	 private Instruction instruction;

	 public Modification(int position, long value, int owner, Instruction inst) {
	 	this.position = position;
	 	this.value = value;
	 	this.owner = owner;
	 	this.instruction = inst;
	 }
	 public int getPosition() { return this.position; }
	 public long getValue() { return this.value; }
	 public int getOwner() { return this.owner; }
	 public Instruction getInstruction() { return this.instruction; }

	 public String toString() {
	 	return "Modification( position : "+ this.position + " | value : " + this.value + " | owner : " + this.owner + " | instruction :" +this.instruction +")";
	 }
}