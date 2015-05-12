package ch.windmill.consolePrompt;

import java.util.Arrays;

import ch.windmill.net.SimpleCommunicator;

/**
 * The <code>SocketCommand</code> can establish a tcp connection with a remote host.
 * This class has simple methods to communicate over the network. The <code>SimpleCommunicator</code> class is a client 
 * application it works with the <code>Socket</code> class.
 * @author Cyrill Jauner
 *
 */
public class SocketCommand extends Command {
	private SimpleCommunicator sc;
	
	/**
	 * Constructs an <code>SocketCommand</code> object.
	 */
	public SocketCommand() {
		super("connect", "A simple client to communicate");
	}
	
	/**
	 * This class needs parameters. This method throws an exception.
	 */
	@Override
	public void execute() throws Exception {
		throw new Exception("this command needs an ip adress and a port.");
	}
	
	/**
	 * Checks if the socket <code>SimpleCommunicator</code> is still connected. If not, it will try to connect to the given
	 * host address.
	 * 
	 * If the connection is established, it's possible to write to and read from the streams.
	 * @param args - all <code>String</code> parameters
	 */
	@Override
	public void execute(String[] args) throws Exception {
		if(sc == null) {
			sc = new SimpleCommunicator(args[0], Integer.parseInt(args[1]), 5);
			sc.connect();
			
			System.out.println("You are now connected to "+sc.getSocket().getInetAddress().getHostName()+" on the port "+sc.getSocket().getPort());
			//System.out.println("runn state "+getRunningState());
			changeRunningState();
		}
	}
	
	/**
	 * Terminates the connection to the remote host.
	 */
	@Override
	public void terminate() throws Exception {
		sc.shutdown();
		sc = null;
		System.out.println("The communicator was terminated");
	}

	@Override
	public void process(String arg) throws Exception {
		sc.writeToStream(arg.getBytes());
		System.out.println(new String(sc.readFromStream()));
	}
}
