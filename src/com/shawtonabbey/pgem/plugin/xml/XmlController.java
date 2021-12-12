package com.shawtonabbey.pgem.plugin.xml;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.shawtonabbey.pgem.database.ui.HeaderCollection;
import com.shawtonabbey.pgem.database.ui.Row;

import lombok.Cleanup;

public class XmlController {
	
	private XmlQueryWin win;
	
	public XmlController(XmlQueryWin win) {
		
		this.win = win;
		win.runListener.listen(this::run);
		
	}
	
	private void run() {
		
		var xmlDoc = win.getDoc();
		var xpath = win.getXpath();
		//var ns = win;
		
		try {
			var xmlDocument = getDocument(xmlDoc);
	
			var parser = new XPathQueryParser();
			
			parser.parse(xpath);
			
					
			var nodeList = xPath(parser.getTableQuery(), xmlDocument);
			
			var table = win.getResultsModel();
			table.clear();
			
			var hc = new HeaderCollection("row");
			
			parser.getColumnQueries().stream().forEach(x->hc.add(x.getColName()));
			table.setColumns(hc);
			
			nodeList.forEach(node -> {
				
				var row = new Row();
				row.add(decode(Optional.of(node)));
				
				parser.getColumnQueries().stream()
					.map(x-> xPath(x.getXpath(), node).findFirst())
					.forEach(x-> row.add(decode(x)));

				table.addRow(row);
			});
						
		} catch (Exception e) {
			win.setResult(e.getMessage());
		}
		

	}

	private Document getDocument(String xmlDoc) throws Exception{

		@Cleanup
		var fileIS = new ByteArrayInputStream(xmlDoc.getBytes());
		var builderFactory = DocumentBuilderFactory.newInstance();
		var builder = builderFactory.newDocumentBuilder();
		var xmlDocument = builder.parse(fileIS);
		
		return xmlDocument;
	}
		
	private Stream<Node> xPath(String query, Object in) {
		
		var xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPath.compile(query).evaluate(in, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}

		Stream<Node> nodeStream = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item);
		
		return nodeStream;
	}
	
	private String decode(Optional<Node> oNode) {
		
		if (oNode.isEmpty())
			return "(null)";
		
		var node = oNode.get();
		if (node instanceof Element) return ((Element)node).getNodeName();
		if (node instanceof Attr) return ((Attr)node).getNodeValue();
		if (node instanceof Text) return ((Text)node).getTextContent();
		
		return node.toString();
	}
}
