package com.tuesdayrefactoring.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XSLTransformUtil extends PropertyUtil {
	
	public static final Logger log = Logger.getLogger(PropertyUtil.class.getName());

	private static final ArrayList<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

	private static Map<String, String> employeeMap = null;

	public static void XMLTransform() {

		try {
			Source xmlSource = new StreamSource(new File("src/b/c/d/EmployeeRequest.xml"));
			Source source = new StreamSource(new File("src/b/c/d/Employee-modified.xsl"));
			Result xmlOutput = new StreamResult(new File("src/b/c/d/EmployeeResponse.xml"));
			TransformerFactory.newInstance().newTransformer(source).transform(xmlSource, xmlOutput);
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (TransformerException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	public static ArrayList<Map<String, String>> readXpathValues() {

		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse("src/b/c/d/EmployeeResponse.xml");
			XPath xPath = XPathFactory.newInstance().newXPath();
			int empCount = Integer.parseInt((String) xPath.compile("count(//Employees/Employee)").evaluate(doc, XPathConstants.STRING));
			for (int i = 1; i <= empCount; i++) {
				employeeMap = new HashMap<String, String>();
				employeeMap.put( Constants.XPATH_EMPLOYEE_ID_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/EmployeeID/text()")
						.evaluate(doc, XPathConstants.STRING));
				employeeMap.put( Constants.XPATH_EMPLOYEE_NAME_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/EmployeeFullName/text()")
						.evaluate(doc, XPathConstants.STRING));
				employeeMap.put( Constants.XPATH_EMPLOYEE_ADDRESS_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/EmployeeFullAddress/text()")
						.evaluate(doc, XPathConstants.STRING));
				employeeMap.put( Constants.XPATH_FACULTY_NAME_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/FacultyName/text()")
						.evaluate(doc, XPathConstants.STRING));
				employeeMap.put( Constants.XPATH_DEPARTMENT_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/Department/text()")
						.evaluate(doc, XPathConstants.STRING));
				employeeMap.put(Constants.XPATH_DESIGNATION_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/Designation/text()")
						.evaluate(doc, XPathConstants.STRING));
				employeeList.add(employeeMap);
			}
		} catch (FactoryConfigurationError e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (SAXException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (IllegalArgumentException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, e.getMessage());
		} catch (XPathExpressionException e) {
			log.log(Level.SEVERE, e.getMessage());
		}

		return employeeList;
	}
}
