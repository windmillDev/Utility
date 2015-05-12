package ch.windmill.consolePrompt;

import java.util.ArrayList;
import java.util.Arrays;

import ch.windmill.swing.ConsoleWindow;

/**
 * This <code>ConsolePrompt</code> let you work with some commands.
 * @author Cyrill Jauner
 *
 */
public class ConsolePrompt {
	private ArrayList<Command> commands;
	private ConsoleWindow console;
	
	public static void main(String[] args) {
		new ConsolePrompt();
	}
	
	/**
	 * Constructs a new <code>ConsolePrompt</code> object and starts with scanning.
	 */
	public ConsolePrompt() {
		commands = new ArrayList<>();
		
		try {
			console = new ConsoleWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		addCommands();
		startScanning();
	}
	
	/**
	 * Create <code>Command</code> references and add to the command list.
	 */
	private void addCommands() {
		commands.add(new HelpCommand(commands));
		commands.add(new SocketCommand());
		commands.add(new EchoServerCommand());
	}
	
	/**
	 * Start with scanning of the <code>ConsolePane</code>. This method checks the user input and invoke commands.
	 */
	private void startScanning() {
		boolean commandFound = false;
		boolean exit = false;
		Command runningCommand = null;
		String input = "";
		String[] inputSplit = null;
		
		do {	
			console.changeInputSource();
			input = console.scan();
			console.changeInputSource();
			
			if(input.equals("exit")) {
				if(runningCommand == null) {
					exit = true;
				} else {
					exitCommand(runningCommand);
					runningCommand = null;
				}
			} else {
				if(runningCommand != null) {
					try {
						runningCommand.process(input);
					} catch (Exception e) {
						System.out.println("Error: "+e.getMessage());
						e.printStackTrace();
					}
				} else {
					inputSplit = input.split(" ");
					commandFound = false;
					for(Command c : commands) {
						if(inputSplit[0].equals(c.getName())) {
							if(inputSplit.length == 1) {
								try {
									c.execute();
								} catch (Exception e) {
									System.out.println("Error: "+e.getMessage());
									e.printStackTrace();
								}
							} else {
								try {
									c.execute(Arrays.copyOfRange(inputSplit, 1, inputSplit.length));
								} catch (Exception e) {
									System.out.println("Error: "+e.getMessage());
									e.printStackTrace();
								}
							}
							commandFound = true;
							
							if(c.getRunningState()) {
								console.setCommandName(c.getName());
								runningCommand = c;
							}
							
							break;
						}
					}
					
					if(!commandFound) {
						System.out.println("error: command not found");
					}
				}
			}
		} while(!exit);
		
		/**
		 * close the frame and close the application.
		 */
		console.dispose();
		System.exit(0);
	}
	
	/**
	 * 
	 * @param c
	 */
	private void exitCommand(Command c) {
		try {
			c.terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.changeRunningState();
		console.setCommandName(ConsoleWindow.EMPTY);
	}
}
