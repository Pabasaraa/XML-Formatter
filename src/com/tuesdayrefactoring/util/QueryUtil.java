package com.tuesdayrefactoring.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class QueryUtil extends PropertyUtil {

	public static final Logger log = Logger.getLogger(QueryUtil.class.getName());
	
	public static String Q(String id){
		NodeList nodeList;
		Element element = null;
		try {
			nodeList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new File("src/b/c/d/EmployeeQuery.xml"))
				.getElementsByTagName( Constants.TAG_NAME );
			for (int x = 0; x < nodeList.getLength(); x++) {
				element = (Element) nodeList.item(x);
				if (element.getAttribute( Constants.ID_ATTRIBUTE ).equals(id))
					break;
			}
		} catch (TransformerFactoryConfigurationError e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (SAXException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		return element.getTextContent().trim();
	}
}

