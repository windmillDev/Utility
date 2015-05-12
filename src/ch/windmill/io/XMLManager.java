package ch.windmill.io;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLManager {
	
	public static void Write(String xmlPath, ArrayList<String> childElements, ArrayList<String> values, String rootText) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer();
			
			// Root Elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(rootText);
			doc.appendChild(rootElement);
			
			// Staff Elements
			for(int i = 0; i < values.size(); i++) {
				Element type;
				
				if(childElements == null) {
					type = doc.createElement("child");
					System.out.println(values.get(i)+" ; "+i);
				} else {
					type = doc.createElement(childElements.get(i));
					System.out.println(values.get(i)+" ; "+childElements.get(i));
				}
				type.appendChild(doc.createTextNode(values.get(i)));
				rootElement.appendChild(type);
				
			}
			
			// create source file
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlPath));
			//StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException | TransformerException e) {
			String errMessage = "There's an Error occured while writing to XML-file: "+xmlPath;
	        
			e.printStackTrace();
		}
	}
}
