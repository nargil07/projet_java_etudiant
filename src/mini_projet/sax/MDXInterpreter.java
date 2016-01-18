package mini_projet.sax;

import java.util.List;
import mini_projet.DAO.EtudiantDAO;
import mini_projet.Entity.Etudiant;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MDXInterpreter extends DefaultHandler {

    public static String html;

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        switch (localName) {
            case "sheet":
                MDXInterpreter.html = ""
                        + "<html>"
                        + "<head>"
                        + "<meta charset='utf-8'>"
                        + "</head>"
                        + "<body>";
                break;
            case "datatable":
                MDXInterpreter.html += "<table>";
                MDXInterpreter.html += "<thead>";
                MDXInterpreter.html += "<tr>";
                MDXInterpreter.html += "<th>prenom</th>";
                MDXInterpreter.html += "<th>nom</th>";
                MDXInterpreter.html += "<th>groupe</th>";
                MDXInterpreter.html += "</tr>";
                MDXInterpreter.html += "</thead>";
                MDXInterpreter.html += "<tbody>";
                EtudiantDAO dao = new EtudiantDAO(attributes.getValue("src"));
                List<Etudiant> list = dao.getListEtudiants();
                for(Etudiant etudiant : list){
                    MDXInterpreter.html += "<tr>";
                    MDXInterpreter.html += "<td>"+etudiant.getNom()+"</td>";
                    MDXInterpreter.html += "<td>"+etudiant.getPrenom()+"</td>";
                    MDXInterpreter.html += "<td>"+etudiant.getGroupe()+"</td>";
                    MDXInterpreter.html += "</tr>";
                }
               MDXInterpreter.html += "</tbody>";
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {switch (localName) {
            case "sheet":
                MDXInterpreter.html += "</body>";
                break;
            case "datatable":
                MDXInterpreter.html += "</table>";
                break;
        }
    }

    public static String getHtml() {
        return new String(html);
    }

}
