package mini_projet.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MDXInterpreter extends DefaultHandler {
	
	public static String html;
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if(localName=="sheet")
		{
			MDXInterpreter.html = ""
					+ "<html>"
					+ "<head>"
					+ "</head>"
					+ "<body>"
					+ "Ta gueule";
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName=="sheet") {
			MDXInterpreter.html += "</body>";
		}
	}
	
	public static String getHtml(){
		return new String(html);
	}
	
}
