package ch.windmill.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NavigationFilter;
import javax.swing.text.Position.Bias;
/**
 * <p>The <code>ConsolePane</code> class is a specific <code>JTextPanel</code>. It needs an input and output stream. For example
 * the <code>System.in</code> and <code>System.out</code> stream.</p>
 * 
 * <p>It's possible to scan the input stream and return the user input. The code generated output text is not editable 
 * for the user. If the user press ENTER on his keyboard, the typed text will be stored in the {@code ConsolePane}. Every text command will also be
 * cached in the {@code queue}. It has his own KeyAdapter to listen for some user typed text.</p>
 * 
 * The console has some features:
 * <ul>
 * 		<li>Press Up and Down to navigate the command queue</li>
 * </ul>
 * 
 * @author Cyrill Jauner
 * @version 0.2
 */
@SuppressWarnings("serial")
public class ConsolePane extends JTextPane{
	private boolean userInput;
	private int lastConsolePos;
	private String runningCommand;
	private LinkedList<String> queue;
	
	/**
	 * Constructs an <code>ConsolePane</code> object with handlers for the given <code>InputStream</code> and <code>PrintWriter</code>.
	 * @param out  the code generated output will be written to this stream.
	 * @param in  the typed text in the ConsolePane will be written to this stream.
	 */
	public ConsolePane(final InputStream out, PrintWriter in) {
		super();
		lastConsolePos = 0;
		runningCommand = "";
		userInput = false;
		queue = new LinkedList<>();
		
		/**
		 *  handle "System.out"
		 */
	    new SwingWorker<Void, String>() {
	        @SuppressWarnings("resource")
			@Override
	        protected Void doInBackground() throws Exception {
	            Scanner s = new Scanner(out);
	            
	            while (s.hasNextLine()) {
	            	publish(s.nextLine());
	            }
	            return null;
	        }
	        
	        @Override
	        protected void process(List<String> chunks) {
	            for (String line : chunks) {
					try {
						getDocument().insertString(getDocument().getLength(), line+"\n", null);
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
	            
	            try {
	            	if(userInput) {
	            		getDocument().insertString(getDocument().getLength(),runningCommand+">", null);
	            	}
				} catch (Exception e) {
					e.printStackTrace();
				}
	            
	            setLastConsolePos(getDocument().getLength()); // Update the position for the current code generated text
	        }
	    }.execute();
	    
	    /**
	     * Handle "System.in"
	     * Initialize a new {@code KeyAdapter} who listen for KeyTyped and KeyPressed events.
	     */
	    addKeyListener(new KeyAdapter() {
	        private StringBuffer line = new StringBuffer();
	        private ListIterator<String> i;
	        
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();
	            
	            if (c == KeyEvent.VK_ENTER) {
	            	queue.add(new String(line)); // add the user typed text to the command queue
	            	i = queue.listIterator(queue.size()); // initialize a new ListIterator
	                in.println(line); // write the user typed text to the input stream
	                line.setLength(0); // set the StringBuffer empty
	            } else if (c == KeyEvent.VK_BACK_SPACE) {
	            	if(line.length() > 0) {
	            		line.setLength(line.length() - 1); // remove the last char
	            	}
	            } else if (!Character.isISOControl(c)) {
	                line.append(e.getKeyChar()); // append the typed char to the StringBuffer
	            }
	        }
	        
	        @Override
	        public void keyPressed(KeyEvent e) {
	        	String s = "";
	        	if(e.getKeyCode() == KeyEvent.VK_UP) {
	        		if(i.hasPrevious()) {
	        			line.setLength(0);
	        			s = i.previous();
	        			select(lastConsolePos, getText().length()); // select the current user typed text
	        			replaceSelection(s); // replace the user typed text with the current String of the queue
	        			line.append(s); // append the String of the queue to the StringBuffer
	        		}
	        	} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
	        		if(i.hasNext()) {
	        			line.setLength(0);
	        			s = i.next();
	        			select(lastConsolePos, getText().length());
	        			replaceSelection(s);
	        			line.append(s);
	        		}
	        	} else {
	        		super.keyPressed(e);
	        	}
	        	
	        }
	    });
	    
	    /**
	     * Add the specific <code>filter</code> to the document
	     */
	    ((AbstractDocument) getDocument()).setDocumentFilter(new ConsoleFilter());
	    setNavigationFilter(new NavFilter());
	}
	
	/**
	 * Private class for document filtering.
	 * @author Cyrill Jauner
	 * 
	 */
	private class ConsoleFilter extends DocumentFilter {
    	
    	/**
    	 * Handles insert string event for the document
    	 * @param fb - FilterBypass that can be used to mutate Document
		 * @param offset - the offset into the document to insert the content >= 0. All positions that track change at or after the given location will move.
		 * @param string - the string to insert
		 * @param attr - the attributes to associate with the inserted content. This may be null if there are no attributes.
    	 * @throws BadLocationException - the given insert position is not a valid position within the document
    	 */
		@Override
		public void insertString(FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException {
            
            super.insertString(fb, offset, string, attr);
            
    	}
    	
    	/**
    	 * Handles replace event for the document.
    	 * @param fb - FilterBypass that can be used to mutate Document
		 * @param offset - Location in Document
		 * @param length - Length of text to delete
		 * @param text - Text to insert, null indicates no text to insert
		 * @param attrs - AttributeSet indicating attributes of inserted text, null is legal.
    	 * @throws BadLocationException - the given insert position is not a valid position within the document
    	 */
		@Override
		public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
            
            if(getText().length() != 0) {
            	if(getCaretPosition() >= lastConsolePos) {
            		super.replace(fb, offset, length, text, attrs);
            	}
            }
    	}
    	
    	/**
    	 * Handles remove event for the document
    	 * @param fb - FilterBypass that can be used to mutate Document
		 * @param offset - the offset from the beginning >= 0
		 * @param length - the number of characters to remove >= 0
		 * @throws BadLocationException - some portion of the removal range was not a valid part of the document. The location in the exception is the first bad position encountered
    	 */
		@Override
		public void remove(FilterBypass fb, int offset, int length)
                        throws BadLocationException {
    		if(getText().length() != 0) {
            	if(getCaretPosition() > lastConsolePos) {
            		super.remove(fb, offset, length);
            	}
            }
    	}
    }
	
	/**
	 * Private class for cursor navigation.
	 * @author Cyrill Jauner
	 *
	 */
	private class NavFilter extends NavigationFilter {
		
		/**
		 * Invoked prior to the Caret setting the dot. This Override method allows only
		 * positions after the ">"-sign (the value of {@code lastConsolePos} -1).
		 * @param fb - FilterBypass that can be used to mutate caret position
		 * @param dot - the position >= 0
		 * @param bias - Bias to place the dot at
		 */
		@Override
		public void setDot(FilterBypass fb, int dot, Bias bias) {
			if(dot > (lastConsolePos-1)) {
				super.setDot(fb, dot, bias);
			}
		}
	}
	
	/**
	 * Set the last position value of the code generated 
	 * @param pos - the text position
	 */
	private void setLastConsolePos(final int pos) {
		this.lastConsolePos = pos;
	}
	
	/**
	 * set the active/running command. This String will be displayed in the console
	 * @param commName - the active command
	 */
	public void setActiveCommand(final String commName) {
		runningCommand = commName;
	}
	
	public void setInputSource(boolean b) {
		userInput = b;
	}
	
	/**
	 * Returns the last code generated text position
	 * @return - last code generated text position
	 */
	public int getLastConsolePos() {
		return lastConsolePos;
	}
	
	/**
	 * Returns the name of the running command
	 * @return - command name
	 */
	public String getActiveCommand() {
		return runningCommand;
	}
	
	/**
	 * Returns true if the current text is user generated. Otherwise return false.
	 * @return - the text source
	 */
	public boolean isUserInput() {
		return userInput;
	}
	
	/**
	 * Scan the given input stream for some user input.
	 * @return - the typed text as a String line
	 */
	@SuppressWarnings("resource")
	public String scanning() {
		return (new Scanner(System.in)).nextLine();
	}
}
