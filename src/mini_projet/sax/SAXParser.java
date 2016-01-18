package mini_projet.sax;

import java.io.File;
import java.io.FileInputStream;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SAXParser {

	private XMLReader parser;
	private MDXInterpreter interpreter;
	
	public SAXParser(File file) {
		this.parser = null;
		try {
			parser = XMLReaderFactory.createXMLReader();
		} catch (SAXException e) {
			System.out.println(e);
		}
		try {
			InputSource is = new InputSource(new FileInputStream(file));
			this.interpreter = new MDXInterpreter();
			parser.setContentHandler(interpreter);
			parser.parse(is);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public MDXInterpreter getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(MDXInterpreter interpreter) {
		this.interpreter = interpreter;
	}
	
	

}
