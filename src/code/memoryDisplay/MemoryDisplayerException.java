package memoryDisplay;

public class MemoryDisplayerException extends Exception {
	
	private int type;
	public static final int MODIFICATION_OUT_OF_BOUND = 0;

	public MemoryDisplayerException(int type) {
		this.type = type;
	}

	@Override
	public String getMessage() {
		switch(type) {
			case MODIFICATION_OUT_OF_BOUND :
				return "Une modification n'est pas ajoutable";
			default :
				return "";
		}
	}
}