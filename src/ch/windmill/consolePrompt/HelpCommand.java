package ch.windmill.consolePrompt;

import java.util.ArrayList;

/**
 * The <code>HelpCommand</code> write informations about the given <code>Commands</code> list.
 * @author Cyrill Jauner
 * @version 0.1
 *
 */
public class HelpCommand extends Command{
	private ArrayList<Command> commands;
	
	/**
	 * Constructs an <code>HelpCommand</code> object.
	 * @param commands - the command list
	 */
	public HelpCommand(final ArrayList<Command> commands) {
		super("help", "The help program");
		this.commands = commands;
	}

	/**
	 * Write out the console help text. It list's every command of the <code>Commands</code> list
	 * with the name and description.
	 */
	@Override public void execute() throws Exception {
		System.out.println("This is the help programm of this console.\n" 
							+ "If you need informations about a command you can type\n"
							+ "help <command name>\n\n"
							+ "There's a list of all available commands:");
		for(Command c : commands) {
			System.out.println(c.getName() + " - " + c.getDescription());
		}
		//changeRunningState();
	}
	
	/**
	 * Write out the description of the given <code>Command</code>. If the name could not found
	 * the method write an error text.
	 */
	@Override public void execute(final String[] args) throws Exception {
		boolean commandFound = false;
		
		for(Command c : commands) {
			if(args[0].equals(c.getName())) {
				System.out.println(c.getName() + " - " + c.getDescription());
				commandFound = true;
			}
		}
		
		if(!commandFound) {
			System.out.println("help error: couldn't find the command " + args[0]);
		}
	}
	
	/**
	 * This class doesn't need a termination method.
	 */
	@Override public void terminate() throws Exception {
		// Nothing to do
	}

	@Override
	public void process(String arg) throws Exception {
		// Nothing to do
	}
}
