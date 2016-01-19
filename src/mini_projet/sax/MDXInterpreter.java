package mini_projet.sax;

import java.util.List;
import mini_projet.DAO.EtudiantDAO;
import mini_projet.Entity.Etudiant;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MDXInterpreter extends DefaultHandler {

    public static String html;
    public String identifiant = null;

    public MDXInterpreter(String identifiant) {
        this.identifiant = identifiant;
    }
    
    

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        EtudiantDAO dao;
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
                dao = new EtudiantDAO(attributes.getValue("src"));
                List<Etudiant> list = dao.getListEtudiants();
                for(Etudiant etudiant : list){
                    MDXInterpreter.html += "<tr>";
                    MDXInterpreter.html += "<td><a href='detail.html?id="+etudiant.getIdentifiant()+"'>"+etudiant.getNom()+"</a></td>";
                    MDXInterpreter.html += "<td><a href='detail.html?id="+etudiant.getIdentifiant()+"'>"+etudiant.getPrenom()+"</a></td>";
                    MDXInterpreter.html += "<td><a href='detail.html?id="+etudiant.getIdentifiant()+"'>"+etudiant.getGroupe()+"</a></td>";
                    MDXInterpreter.html += "</tr>";
                }
               MDXInterpreter.html += "</tbody>";
                break;
            case "card":
                MDXInterpreter.html += "<div>";
                dao = new EtudiantDAO(attributes.getValue("src"));
                Etudiant etudiant = dao.getEtudiant(identifiant);
                MDXInterpreter.html += "<p>"+etudiant.getNom()+"</p>";
                MDXInterpreter.html += "<p>"+etudiant.getPrenom()+"</p>";
                MDXInterpreter.html += "<p>"+etudiant.getGroupe()+"</p>";
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
            case "card":
                MDXInterpreter.html += "</div>";
                break;
        }
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    
    public static String getHtml() {
        return new String(html);
    }

}
