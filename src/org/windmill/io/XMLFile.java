package org.windmill.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFile extends File{
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document xmlDoc;
	private TransformerFactory transformFactory;
	private Transformer transformer;
	
	private String rootText;
	
	public XMLFile(String filePath, String rootText) throws ParserConfigurationException, TransformerConfigurationException, SAXException, IOException {
		super(filePath);
		this.rootText = rootText;
		
		// Instance factories
		docFactory = DocumentBuilderFactory.newInstance();
		transformFactory = TransformerFactory.newInstance();
		
		//try {
			// Instance builders
			docBuilder = docFactory.newDocumentBuilder();
			transformer = transformFactory.newTransformer();
			
			xmlDoc = docBuilder.parse(this);
			
		//} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	}
	
	public NodeList getStaffList(String staffText) {
		NodeList staffList = null;
		
		try {
			// Get all nodes with the default staff text
			staffList = xmlDoc.getElementsByTagName(staffText);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return staffList;
	}
	
	public Node getChild(String staffText, String childText) {
		Node child = null;
		
		NodeList staffList = xmlDoc.getElementsByTagName(staffText);
		Node staff = staffList.item(0);
		NodeList childList = staff.getChildNodes();
		
		for(int i = 0; i < childList.getLength(); i++) {
			if(childList.item(i).getNodeName().equals(childText)) {
				child = childList.item(i);
			}
		}
		
		return child;
	}
	
	public void addNewStaff(ArrayList<String>childs, String staffText, String[] childValues) throws Exception {
		
		if(exists()) {
			if(childs.size() == childValues.length) {
				try {
					
					Document xmlDoc = docBuilder.parse(this);
					
					// Save the root node
					Node rootNode = xmlDoc.getFirstChild();
					// Create a new staff element
					Element staffElement = xmlDoc.createElement(staffText);
					// Append the staff element to the xml document
					xmlDoc.appendChild(staffElement);
					
					// Fill the values into the childs
					for(int i = 0; i < childs.size(); i++) {
						Element child = xmlDoc.createElement(childs.get(i));
						child.appendChild(xmlDoc.createTextNode(childValues[i]));
						
						staffElement.appendChild(child);
					}
					
					// Updates the xml file
					DOMSource domSource = new DOMSource(xmlDoc);
					StreamResult result = new StreamResult(this);
					
					transformer.transform(domSource, result);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				throw new Exception("The length of the parameter childValues isn't equal to the number of childs");
			}
		} else {
			throw new Exception("xml does not exist");
			
		}
	}
	
	public void createNewXMLFile() {
		if(!exists()) {
			Document xmlDoc = docBuilder.newDocument();
			Element rootElement = xmlDoc.createElement(rootText);
			xmlDoc.appendChild(rootElement);
			
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(this);
			
			try {
				transformer.transform(source, result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Exception e = new Exception("This xml file already exist at location: "+getAbsolutePath());
		}
	}
}
