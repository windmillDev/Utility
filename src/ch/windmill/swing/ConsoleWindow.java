package ch.windmill.swing;

import java.awt.Font;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * The <code>ConsoleWindow</code> is a specific <code>JFrame</code> with methods for scanning of the <code>ConsolePane</code>.
 * It works with the System.in and System.out streams.
 * @author Cyrill Jauner
 * @version 0.1
 */
@SuppressWarnings("serial")
public class ConsoleWindow extends JFrame {
	public final static String EMPTY = "";
	private ConsolePane consolePane;
	
	/**
	 * Constructs an <code>ConsoleWindow</code> object. It contains a <code>ConsolePane</code> object per default.
	 * This method guide the System.in and System.out streams to the <code>ConsolePane</code>.
	 * @throws Exception - something went wrong
	 */
	public ConsoleWindow() throws Exception {
		super("Console");
		JScrollPane scroll = null;
		
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**
		 * create the pipes
		 */
		PipedInputStream inPipe = new PipedInputStream();
	    PipedInputStream outPipe = new PipedInputStream();
	    /**
	     * set the System.in and System.out streams
	     */
	    System.setIn(inPipe);
	    System.setOut(new PrintStream(new PipedOutputStream(outPipe), true));
	    consolePane = new ConsolePane(outPipe, new PrintWriter(new PipedOutputStream(inPipe), true));
	    
	    /**
	     * prepare the console view
	     */
		consolePane.setFont(new Font("Consolas", Font.PLAIN, 12));
		scroll = new JScrollPane(consolePane);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		/**
		 * add components
		 */
	    add(scroll);
		setVisible(true);
		
		/**
		 * write out the console header
		 */
		System.out.println("Windmill console\nversion: 0.1");
	}
	
	/**
	 * scan the console for user input
	 * @return the user input
	 */
	public String scan() {
		return consolePane.scanning();
	}
	
	/**
	 * <p>Set the name of the actual running {@code command}. The given {@code String} will be 
	 * displayed in the {@code ConsolePane} before the {@literal >}-sign.</p><p>This method can also invoked to 
	 * clear the running command (see the static field {@code EMPTY}).</p>
	 * @param name - of the running command.
	 */
	public void setCommandName(String name) {
		consolePane.setActiveCommand(name);
	}
	
	/**
	 * <p>Change the input source. If the {@code isUserInput} field of the {@code ConsolePane} is true, the 
	 * console will print a {@literal >}-sign after every out.println().</p>
	 */
	public void changeInputSource() {
		consolePane.setInputSource(!consolePane.isUserInput());
	}
}
