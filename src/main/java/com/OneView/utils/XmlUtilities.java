package com.OneView.utils; 

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.xml.SAXErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtilities {
	static String nodeValue = "";

	public static boolean validateXmlSyntax(String xmlFileName) throws ParserConfigurationException, SAXException, IOException{
		boolean result = false;
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		DocumentBuilder builder = factory.newDocumentBuilder();

		builder.setErrorHandler(new SAXErrorHandler());    
		// the "parse" method also validates XML, will throw an exception if misformatted
		/*Document document =*/ builder.parse(new InputSource(xmlFileName));
		result = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public static String getNodeValue(String fileName, String nodeName) {

		try {
			File file = new File(fileName);
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());
			if (doc.hasChildNodes()) {
				nodeValue = nodeValue(doc.getChildNodes(), nodeName);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return nodeValue;
	}

	private static String nodeValue(NodeList nodeList, String nodeName) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// get node name and value
				// System.out.println("\nNode Name =" + tempNode.getNodeName() +
				// " [OPEN]");
				// System.out.println("Node Value =" +
				// tempNode.getTextContent());
				if (tempNode.getNodeName().contains(nodeName)) {
					nodeValue = tempNode.getTextContent();
					// return nodeValue;
				}
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					nodeValue(tempNode.getChildNodes(), nodeName);
				}
				// System.out.println("Node Name =" + tempNode.getNodeName() +
				// " [CLOSE]");
			}
		}
		return nodeValue;
	}

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
		
		String abc = "";
		abc = getNodeValue("TestData/000BDBL75000021623901.xml", "Trl");
		System.out.println(abc);
		
		boolean result = false;
		result = validateXmlSyntax("TestData/000BDBL75000021623901.xml");
		System.out.println("Done " + result);
		String nodeList[] = {"nMTI","nFunCd","nRecNum","nDtTmLcTxn","nPAN","nARD","nAcqInstCd","nApprvlCd","nCrdAcptTrmId","nAmtTxn","nCcyCdTxn","nTxnOrgInstCd"};
		for(int x = 0;x<nodeList.length;x++){
			System.out.println(nodeList[x]);
		}
	}

}