package core;

public class CoreException extends Exception {
	private int type;
	public static final int EXCEPTION_MODE_EXECUTION = 0;
	public static final int EXCEPTION_PAUSE_UNABLE = 1;
	public static final int EXCEPTION_STOP_UNABLE = 2;
	public static final int EXCEPTION_ANAMLYS_FOLLOW_UNABLE = 3;
	public static final int EXCEPTION_INVALID_SPEED = 4;
	public static final int EXCEPTION_INVALID_PROGRAM_INDEX = 5;
	public static final int EXCEPTION_INVALID_NBRGAGNANTS = 6;
	public static final int EXCEPTION_INVALID_MEMORY_FREE_PLACE = 7;
	public static final int EXCEPTION_INVALID_VERSION = 8;

	public CoreException(int type) {
		this.type = type;
	}

	@Override
	public String getMessage() {
		switch(type) {
			case EXCEPTION_MODE_EXECUTION:
			return "Le mode du core n'est pas valide";

			case EXCEPTION_PAUSE_UNABLE:
			return "impossible de mettre l'execution en pause ou de la reprendre. L'execution n'est peut être pas en cours";

			case EXCEPTION_STOP_UNABLE:
			return "impossible de stopper l'execution. L'execution n'est peut être pas en cours";

			case EXCEPTION_ANAMLYS_FOLLOW_UNABLE:
			return "impossible de changer la valeur de isWaitingFollow";

			case EXCEPTION_INVALID_SPEED:
			return "La vitesse fournie n'est pas valide";

			case EXCEPTION_INVALID_PROGRAM_INDEX:
			return "L'index de programme fourni n'est pas valide";

			case EXCEPTION_INVALID_NBRGAGNANTS:
			return "Le nouveau NBR_GAGNZNTS est invalide ou  ne peut pas être changer";

			case EXCEPTION_INVALID_MEMORY_FREE_PLACE:
			return "Espace mémoire non valide";

			case EXCEPTION_INVALID_VERSION:
			return "La nouvelle version de redcode est invalide";

			default:
			return "BAD TYPE OF EXCEPTION";
		}
	}
}