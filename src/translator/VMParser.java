package translator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VMParser {
	private InputStreamReader inStream = null;
	private boolean hasMoreCommands = true;
	private String command = "";
	private CommandType commandType;
	private String arg1;
	private int arg2;

	public enum CommandType {
		C_ARITHMETIC, C_PUSH, C_POP, C_LABEL, C_GOTO, C_IF, C_FUNCTION, C_RETURN, C_CALL, C_ERROR;

		public static CommandType getCommandType(String command) {
			if (command.equals("add") || command.equals("sub")
					|| command.equals("neg") || command.equals("eq")
					|| command.equals("gt") || command.equals("lt")
					|| command.equals("and") || command.equals("or")
					|| command.equals("not")) {
				return C_ARITHMETIC;
			} else if (command.equals("push")) {
				return C_PUSH;
			} else if (command.equals("pop")) {
				return C_POP;
			} else if (command.equals("label")) {
				return C_LABEL;
			} else if (command.equals("goto")) {
				return C_GOTO;
			} else if (command.equals("ig-goto")) {
				return C_IF;
			} else if (command.equals("function")) {
				return C_FUNCTION;
			} else if (command.equals("return")) {
				return C_RETURN;
			} else if (command.equals("call")) {
				return C_CALL;
			} else {
				return C_ERROR;
			}
		}
	}

	public VMParser(String inFilePath) throws FileNotFoundException {
		inStream = new FileReader(inFilePath);
		advance();
	}

	public boolean hasMoreCommands() {
		return hasMoreCommands;
	}

	private int advance() {
		if (hasMoreCommands) {
			StringBuilder stringBuf = new StringBuilder();
			int ch;
			try {
				hasMoreCommands = false;
				while ((ch = inStream.read()) > -1) {
					if (ch != '\n') {
						stringBuf.append((char) ch);
					} else {
						hasMoreCommands = true;
					}
				}
				if (!hasMoreCommands) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return ReturnStatus.INSTREAM_READ_ERROR;
			}
			command = stringBuf.toString();
			System.out.println("Current command:" + command);

			return analyseCurrentCommand();
		} else {
			return ReturnStatus.HAS_NO_MORE_COMMANDS;
		}
	}

	private int analyseCurrentCommand() {
		String[] commandParts = command.split("\\s+");
		System.out.println("Splittet command:\n");
		for (String string : commandParts) {
			System.out.println(string);
		}
		System.out.println("--------------");

		if (commandParts.length > 0) {
			commandType = CommandType.getCommandType(commandParts[0]);
			if (commandType == CommandType.C_ERROR) {
				return ReturnStatus.UNKNOWN_COMMAND;
			}

			if (commandType == CommandType.C_ARITHMETIC) {
				arg1 = commandParts[0];
				arg2 = -1;
				return ReturnStatus.SUCCESS;
			} else if (commandType == CommandType.C_FUNCTION
					|| commandType == CommandType.C_CALL) {
				arg1 = commandParts[1];
				try {
					arg2 = Integer.parseInt(commandParts[2]);
				} catch (NumberFormatException nfe) {
					return ReturnStatus.SECOND_ARGUMENT_NO_NUMBER;
				}
				return ReturnStatus.SUCCESS;
			} else if (commandType == CommandType.C_POP
					|| commandType == CommandType.C_PUSH) {
				arg1 = commandParts[1];
				if(commandParts.length > 2){
					try {
						arg2 = Integer.parseInt(commandParts[2]);
					} catch (NumberFormatException nfe) {
						return ReturnStatus.SECOND_ARGUMENT_NO_NUMBER;
					}
					if( commandParts.length > 3) {
						return ReturnStatus.TOO_MANY_COMMAND_PARTS;
					}
				}
			}

		} else {
			return ReturnStatus.EMPTY_COMMAND;
		}

		return ReturnStatus.SUCCESS;
	}
}
