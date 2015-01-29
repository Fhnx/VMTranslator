package translator;

public class ReturnStatus {	
	// success
	public static final int SUCCESS = 0;
	
	// parser errors
	public static final int INSTREAM_READ_ERROR = 10;
	public static final int HAS_NO_MORE_COMMANDS = 11;
	public static final int EMPTY_COMMAND = 12;
	public static final int UNKNOWN_COMMAND = 13;
	public static final int SECOND_ARGUMENT_NO_NUMBER = 14;
	public static final int TOO_MANY_COMMAND_PARTS = 15;
	
	// public static final int OUTSTREAM_READ_ERROR = 10;
	//
	// public static final int DUPLICATE_SYMBOL = 20;
	// public static final int SYMBOL_IN_LAST_LINE = 21;
	//
	// public static final int WRONG_C_INSTRUCTION = 30;
	// public static final int WRONG_JUMP_INSTRUCTION = 31;
	// public static final int WRONG_DEST_INSTRUCTION = 32;
	// public static final int WRONG_COMP_INSTRUCTION = 33;
}

