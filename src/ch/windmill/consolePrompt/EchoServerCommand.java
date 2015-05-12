/**
 * 
 */
package ch.windmill.consolePrompt;

import ch.windmill.net.SimpleEchoServer;

/**
 * @author Cyrill Jauner
 *
 */
public class EchoServerCommand extends Command {
	private SimpleEchoServer server;
	
	public EchoServerCommand() {
		super("echo","A simple echo ServerSocket");
	}
	
	
	@Override
	public void execute() throws Exception {
		throw new Exception("This command needs an port to listen from");
	}

	
	@Override
	public void execute(String[] args) throws Exception {
		if(server == null) {
			server = new SimpleEchoServer(Integer.parseInt(args[0]));
			System.out.println("The server listen from port "+server.getPort());
			server.Listen();
			changeRunningState();
		} else {
			
		}
	}

	
	@Override
	public void terminate() throws Exception {
		if(server != null) {
			
		}
		System.out.println("The server was terminated");
	}


	@Override
	public void process(String arg) throws Exception {
		// Nothing to do
		
	}

}
