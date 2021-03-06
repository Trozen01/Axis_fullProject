package com.MainFrameWork.support;  

import java.io.File;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlDataManipulator {
	/**
	 * Returns a double dimentional array with element values of an XML file
	 * passed as String Parameter. The sample file assumed to be in the below
	 * format. <?xml version="1.0"?> <Class> <Student id="1">
	 * <firstname>Kiran</firstname> <lastname>Kumar</lastname>
	 * <salary>10000</salary> </Student> <Student id="2">
	 * <firstname>Ram</firstname> <lastname>Rahim</lastname>
	 * <salary>20000</salary> </Student> </Class>
	 * 
	 * @return String[][] is a 2D array with tabular representation of data.
	 */
	public String[][] getXMLDataIn2DArray(String filePath) {
		String[][] data = null;
		int rows = 0, cols = 0, p = 0, q = 0;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File file = new File(filePath);
			Document doc = builder.parse(file);

			doc.getDocumentElement().normalize();

			// System.out.println("Root element is :" +
			// doc.getDocumentElement().getNodeName());

			NodeList allParentNodes = doc.getDocumentElement().getChildNodes();
			NodeList allChildNodes = allParentNodes.item(1).getChildNodes(); // item(0)
																				// is
																				// a
																				// text
																				// node.
																				// and
																				// item(1)
																				// is
																				// an
																				// element
																				// node

			// To find number of records which are strictly element nodes.
			for (int i = 0; i < allParentNodes.getLength(); i++) {
				if (allParentNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					rows++;
				}
			}
			// To find number of columns which are strictly element nodes.
			for (int j = 0; j < allChildNodes.getLength(); j++) {
				if (allChildNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					cols++;
				}
			}

			data = new String[rows][cols];

			for (int i = 0; i < allParentNodes.getLength(); i++) {
				if (allParentNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					// System.out.println(allNodes.item(i).getNodeName());
					allChildNodes = allParentNodes.item(i).getChildNodes();

					for (int j = 0; j < allChildNodes.getLength(); j++) {
						if (allChildNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
							String item = allChildNodes.item(j)
									.getTextContent();
							// System.out.println(item);
							data[p][q] = item;
							// System.out.println("\t"+allChildNodes.item(j).getTextContent());
							q++;
						}
					}
					p++;
					q = 0;
					// We use p,q instead of i,j to store skip the non-element
					// node indexes in the array.
				}
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * Creates an XML file with the Root element, and records name (parent node
	 * of all records) and the data from a 2D array.
	 * 
	 * @param data
	 *            : a 2D array with all tabular data to fill in text contents.
	 * @param filePath
	 *            : file name (with path)
	 * @param rootEle
	 *            : Root Element name.
	 * @param recordNodeName
	 *            : A record node name such as "Student"
	 * @param columnNames
	 *            : Elements inside recordNodeName such as "Name", "Roll Num",
	 *            etc..
	 */
	public void createXML(String[][] data, String filePath, String rootEle,
			String recordNodeName, String[] columnNames) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement(rootEle);
			doc.appendChild(rootElement); // Create a root element.

			for (int i = 0; i < data.length; i++) {
				Element record = doc.createElement(recordNodeName);
				rootElement.appendChild(record); // Creates record node element
													// such as "Student"

				for (int j = 0; j < columnNames.length; j++) {

					Element ele = doc.createElement(columnNames[j]);
					ele.appendChild(doc.createTextNode(data[i][j]));
					record.appendChild(ele); // Creates Elements inside
												// recordNodeName such as
												// "Name", "Roll Num", etc..
				}

			}

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));

			transformer.transform(source, result);
		}

		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String argv[]) {
	 * 
	 * String[] columns = {"first","last","Nick","Sal"};
	 * 
	 * String[][] items = new XMLFileIO().getXMLDataIn2DArray("testdata.xml");
	 * 
	 * System.out.println(items.length);
	 * 
	 * System.out.println(items[0][0] + items[0][1] + items[0][2] + items[0][3]
	 * ); System.out.println(items[1][0] + items[1][1] + items[1][2] +
	 * items[1][3] ); System.out.println(items[2][0] + items[2][1] + items[2][2]
	 * + items[2][3] ); System.out.println(items[3][0] + items[3][1] +
	 * items[3][2] + items[3][3] );
	 * 
	 * new XMLFileIO().createXML(items, "file.xml", "class", "student",
	 * columns); }
	 */

}
