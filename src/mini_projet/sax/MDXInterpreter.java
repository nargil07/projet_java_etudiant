package mini_projet.sax;

import java.util.List;
import mini_projet.DAO.EtudiantDAO;
import mini_projet.Entity.Etudiant;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MDXInterpreter extends DefaultHandler {

    public static String html;
    public List<Etudiant> list_etudiant = null;

    public MDXInterpreter(List<Etudiant> list_etudiant) {
        this.list_etudiant = list_etudiant;
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
                MDXInterpreter.html += "<form>";
                MDXInterpreter.html += "<input type=\"text\" name=\"research\" />";
                MDXInterpreter.html += "<input type=\"submit\" name=\"action\" value=\"rechercher\"/>";
                MDXInterpreter.html += "</form>";
                MDXInterpreter.html += "<table>";
                MDXInterpreter.html += "<thead>";
                MDXInterpreter.html += "<tr>";
                MDXInterpreter.html += "<th>prenom</th>";
                MDXInterpreter.html += "<th>nom</th>";
                MDXInterpreter.html += "<th>groupe</th>";
                MDXInterpreter.html += "<th></th>";
                MDXInterpreter.html += "</tr>";
                MDXInterpreter.html += "</thead>";
                MDXInterpreter.html += "<tbody>";
                for(Etudiant etudiant : list_etudiant){
                    MDXInterpreter.html += "<tr>";
                    MDXInterpreter.html += "<form>";
                    MDXInterpreter.html += "<td><a href='detail.html?id="+etudiant.getIdentifiant()+"'>"+etudiant.getNom()+"</a></td>";
                    MDXInterpreter.html += "<td><a href='detail.html?id="+etudiant.getIdentifiant()+"'>"+etudiant.getPrenom()+"</a></td>";
                    MDXInterpreter.html += "<td><a href='detail.html?id="+etudiant.getIdentifiant()+"'>"+etudiant.getGroupe()+"</a></td>";
                    MDXInterpreter.html += "<td>";
                    MDXInterpreter.html += "<input type=\"hidden\" name=\"identifiant\" value=\""+etudiant.getIdentifiant()+"\"/>";
                    MDXInterpreter.html += "<input type=\"submit\" name=\"action\" value=\"supprimer\""+etudiant.getIdentifiant()+"/>";
                    MDXInterpreter.html += "</td>";
                    MDXInterpreter.html += "</form>";
                    MDXInterpreter.html += "</tr>";
                }
               MDXInterpreter.html += "<form>"
                       + "<input type=\"text\" name=\"nom\" placeholder=\"nom\">"
                       + "<input type=\"text\" name=\"prenom\" placeholder=\"prenom\">"
                       + "<input type=\"text\" name=\"groupe\" placeholder=\"groupe\">"
                       + "<input type=\"submit\" name=\"action\" value=\"ajouter\"/>"
                       + "</form>";
                break;
            case "card":
                MDXInterpreter.html += "<div>";
                MDXInterpreter.html += "<form action=\"detail.html\">";
                Etudiant etudiant = list_etudiant.get(0);
                MDXInterpreter.html += "<p><input type=\"hidden\" name=\"identifiant\" value=\"" + etudiant.getIdentifiant()+"\"/></p>";
                MDXInterpreter.html += "<p><input type=\"text\" name=\"nom\" value=\"" + etudiant.getNom() +"\"/></p>";
                MDXInterpreter.html += "<p><input type=\"text\" name=\"prenom\" value=\""+etudiant.getPrenom()+"\"/></p>";
                MDXInterpreter.html += "<p><input type=\"text\" name=\"groupe\" value=\""+etudiant.getGroupe()+"\"/></p>";
                MDXInterpreter.html += "<p><input type=\"submit\" name=\"action\" value=\"modifierEtudiant\" ></input></p>";
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
                MDXInterpreter.html += "</tbody>";
                MDXInterpreter.html += "</table>";
                break;
            case "card":
                MDXInterpreter.html += "</form>";
                MDXInterpreter.html += "</div>";
                break;
        }
    }
    
    public static String getHtml() {
        return new String(html);
    }

}
