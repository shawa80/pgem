package com.shawtonabbey.pgem.plugin.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.plugin.csv.ui.Compile;
import com.shawtonabbey.pgem.plugin.csv.ui.CsvImportWin;
import com.shawtonabbey.pgem.plugin.csv.ui.CsvReader;
import com.shawtonabbey.pgem.plugin.csv.ui.Execute;
import com.shawtonabbey.pgem.plugin.csv.ui.Logging;
import com.shawtonabbey.pgem.plugin.csv.writer.CsvModelWriter;
import com.shawtonabbey.pgem.plugin.csv.writer.SqlWriter;
import com.shawtonabbey.pgem.plugin.csv.writer.TransformWriter;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class CsvPlugin extends PluginBase {
		
	private String path;
	
	private CsvImportWin csvWin = new CsvImportWin();
	
	
	public void init() {

		csvWin.enableJava();
		
		dispatch.find(TableInstance.Added.class).listen((t, ev) -> {
			
			t.addPopup("Data", "Import", (e) -> {


				csvWin.setTitle(t.getTable().getName());
				String code = TransformWriter.write(t.getTable(), SqlWriter.write(t.getTable()));
				csvWin.setCodeText(code);
				csvWin.setModal(true);

				csvWin.getCompileObserver().listen(() -> {
					
					try {
						var msg = Compile.build(csvWin.getCsvText(), csvWin.getCodeText());
						csvWin.setCompileText(msg + "\ndone");
						
					} catch (IOException e1) {}

				});
				
				csvWin.getCsvObserver().listen(path -> {
					this.path = path;
					try (var fs = new FileInputStream(path)) {
						var r = new CsvReader(fs);
						var header = r.getHeader();
												
						var csv = CsvModelWriter.write(header);
						csvWin.setCsvText(csv);
					} catch (Exception e1) {}
					
				});
				
				csvWin.getBuilderObserver().listen(() -> {

					Logging log = (msg) -> {
						csvWin.appendExecuteText(msg);
					};
					
					var exe = new Execute();
					exe.run(path, t.findDbc(), log);
				});

				csvWin.getSaverObserver().listen((path) -> {
					try {
						var factory = DocumentBuilderFactory.newInstance();
						var docBuilder = factory.newDocumentBuilder();
						var xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><doc/>";
						var doc = docBuilder.parse(new InputSource(new StringReader(xml)));
						
						var data = doc.createElement("data");
						data.setTextContent(csvWin.getCsvText());
						
						doc.getDocumentElement().appendChild(data);
						
						var transform = doc.createElement("transform");
						transform.setTextContent(csvWin.getCodeText());
						doc.getDocumentElement().appendChild(transform);
						
						var transformer = TransformerFactory.newInstance().newTransformer();
						var output = new StreamResult(new File(path));
						var input = new DOMSource(doc);

						transformer.transform(input, output);
					} catch (ParserConfigurationException | SAXException | IOException | TransformerFactoryConfigurationError | TransformerException e1) {
						e1.printStackTrace();
					}
				      
				});
				
				csvWin.getLoaderObserver().listen((path) -> {

					try {
						var factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder;
						
						docBuilder = factory.newDocumentBuilder();
						
						var doc = docBuilder.parse(new File(path));
						
						
						var xPathfactory = XPathFactory.newInstance();
						var xpath = xPathfactory.newXPath();
						var expr = xpath.compile("/doc/data");
						
						var data = (Element)expr.evaluate(doc, XPathConstants.NODE);
						csvWin.setCsvText(data.getTextContent());
						
						expr = xpath.compile("/doc/transform");
						
						var transform = (Element)expr.evaluate(doc, XPathConstants.NODE);
						csvWin.setCodeText(transform.getTextContent());						
						
					
					} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e1) {

					}
				});
				
				csvWin.setVisible(true);

			});
			
		});
		
	}
	
		

	
	

}
