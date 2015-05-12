package ch.windmill.swing;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ScrollTextArea extends JTextArea{
	private JScrollPane scrollPane;
	private boolean horizontalScroll, verticalScroll;
	
	public ScrollTextArea(boolean horizontalScroll, boolean verticalScroll) {
		super();
		this.horizontalScroll = horizontalScroll;
		this.verticalScroll = verticalScroll;
		
		scrollPane = new JScrollPane(this);
		
		prepareScrollPane();
	}

	private void prepareScrollPane() {
		if(horizontalScroll) {
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		}
		if(verticalScroll) {
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
	}
	
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	
	public void updateStatus(String message) {
		String currentText = getText();
		setText(currentText+"\n"+message);
		
		if(horizontalScroll) {
			int max = scrollPane.getHorizontalScrollBar().getMaximum();
			scrollPane.getHorizontalScrollBar().setValue(max);
			repaint();
		}
		if(verticalScroll) {
			int max = scrollPane.getVerticalScrollBar().getMaximum();
			scrollPane.getVerticalScrollBar().setValue(max);
			repaint();
		}
	}
}