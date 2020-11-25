package np.xml.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import com.sun.jarsigner.ContentSignerParameters;

public class XML {
	private static DocumentBuilder builder;
	private Document document;
	
	static {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			builder = factory.newDocumentBuilder();
		} catch (Exception ex) {
			throw new XMLException();
		}
	}
	
	XML() {
		
	}
	
	
	
	public Node CreateNode(String tag, Map<String, String> attribs, String... content) {
		Element node = document.createElement(tag);
		
		for(String key : attribs.keySet()) {
			String value = attribs.get(key);
			node.setAttribute(key, value);
		}
		
		if(content.length > 0) {
			node.setNodeValue(content[1]);
		}
		
		return node;
	}
	
	public NamedNodeMap GetAttributes() {
		if(document.hasAttributes()) 
			return document.getAttributes();
		
		throw new XMLException();
	}
	
	public static String GetNodeText(Node node) {
		return node.getTextContent();
	}
	
	public static String GetValue(Node node, String tag) {
		return node.getAttributes().getNamedItem(tag).getNodeValue();
	}
	
	public NamedNodeMap GetAttributes(Node node) {
		if(node.hasAttributes()) 
			return node.getAttributes();
		
		throw new XMLException();
	}
	
	protected XML SetDocumentDataFromDisk(String path) {
		try {
		document = builder.parse(new File(path));
		return this;
		} catch (Exception ex) {
			throw new XMLException();
		}
	} 
	
	protected XML CreateEmptyDocument() {
		try {
			document = builder.newDocument();
			return this;
		} catch (Exception ex) {
			throw new XMLException();
		}
	}
	
	public void RunThroughAllNodes(NodeConsumer consumer) {
		RunThroughChildNodes(document, consumer);
	}
	
	public void RunThroughChildNodes(Node node, NodeConsumer consumer) {
		if(node.hasChildNodes()) {
			NodeList children = node.getChildNodes();
			for(int i = 0; i < children.getLength(); i++) {
				RunThroughChildNodes(node, consumer);
			}
		} else {
			consumer.Call(node);
		}
	}
}
