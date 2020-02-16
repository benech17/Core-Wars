package infoDisplay;

public class InfoDisplayerException extends Exception {

	private int type;
	public static final int INVALID_CORE_SPEED = 0;
	public static final int NO_SUCH_PROGRAM_INDEX = 1;

	public InfoDisplayerException(int type) {
		this.type = type;
	}

	@Override
	public String getMessage() {
		switch(this.type) {
			case INVALID_CORE_SPEED:
				return "Le core ne peut pas accepter cette valeur de vitesse.";
			case NO_SUCH_PROGRAM_INDEX:
				return "L'index fourni n'est à aucun programme.";
			default :
				return "";
		}
	}
}