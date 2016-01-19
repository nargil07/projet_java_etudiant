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
	
	public SAXParser(String identifiant) {
		this.parser = null;
		this.interpreter = new MDXInterpreter(identifiant);
	}
        
        public void parse(File file){
            try {
			parser = XMLReaderFactory.createXMLReader();
		} catch (SAXException e) {
			System.out.println(e);
		}
		try {
			InputSource is = new InputSource(new FileInputStream(file));
			
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
