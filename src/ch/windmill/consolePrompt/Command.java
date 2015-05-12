package ch.windmill.consolePrompt;

import ch.windmill.io.XMLFile;

/**
 * This is the super <code>Command</code> class for every command of the console prompt
 * @author Cyrill Jauner
 * @version 0.1
 */
public abstract class Command {
	private String name;
	private String shortDescription;
	private XMLFile helpFile;
	private boolean running;
	
	/**
	 * Constructs a command for the console prompt. The help file is null.
	 * @param name - the name of the command. This is also the execution name
	 * @param shortDescription - a short description of this command
	 */
	public Command(String name, String shortDescription) {
		this(name, shortDescription, null);
	}
	
	/**
	 * Constructs a command for the console prompt.
	 * @param name - the name of the command. This is also the execution name
	 * @param shortDescription - a short description of this command
	 * @param helpFilePath - the path to the xml help file
	 */
	public Command(String name, String shortDescription, String helpFilePath) {
		this.name = name;
		this.shortDescription = shortDescription;
		running = false;
		
		if(helpFilePath != null) {
			try {
				helpFile = new XMLFile(helpFilePath, "command");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This is the main execution method of the <code>Command</code>.
	 */
	public abstract void execute() throws Exception;
	
	/**
	 * This is the main execution method with parameters of the <code>Command</code>.
	 */
	public abstract void execute(String[] args) throws Exception;

	/**
	 * This method is for execution while the command is in the running mode.
	 */
	public abstract void process(String arg) throws Exception;
	
	/**
	 * This is the termination method of the <code>Command</code>.
	 */
	public abstract void terminate() throws Exception;
	
	/**
	 * Change the state of the running field
	 */
	public void changeRunningState() {
		if(running) {
			running = false;
		} else {
			running = true;
		}
	}
	
	/**
	 * Returns the name of this command
	 * @return the name of this command
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the xml help file. It returns null if no help file is available.
	 * @return the xml help file
	 */
	public XMLFile getHelpFile() {
		return helpFile;
	}
	
	/**
	 * Returns the description of this command.
	 * @return the short description
	 */
	public String getDescription() {
		return shortDescription;
	}
	
	/**
	 * Returns true if the command is still running, else false
	 * @return the running state
	 */
	public boolean getRunningState() {
		return running;
	}
}
