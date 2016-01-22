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
                        + "<style>"
                        + ".textfield {width: 200px;height: 65px;padding-top: 20px;display: inline-block; margin:10px;}"
                        + ".textfield > input[type=text]{display: block;width: 100%;height: 36px;border: none;border-bottom: 1px solid #9e9e9e;font-size: 18px;}"
                        + ".textfield > input[type=text]:focus{transition: 0.2s ease all;border-bottom: 3px solid #2196f3;outline: none;}"
                        + ".textfield > input[type=text]:focus ~ label{position: relative;top: -60px;color: #2196f3;font-size: 13px;}"
                        + ".textfield > input[type=text]:valid{outline: none;}"
                        + ".textfield > input[type=text]:valid ~ label{position: relative;top: -60px;font-size: 13px;}"
                        + ".textfield > input[type=text] ~ label{position: relative;top: -28px;transition: top 450ms;pointer-events: none;color: #9e9e9e;font-family: 'Roboto', sans-serif;font-size: 18px;font-weight: normal;}"
                        + "input[type=submit] {display: inline-block;min-width: 64px;text-align: center;height: 36px;margin: 8px;padding-right: 8px;padding-left: 8px;cursor: pointer;text-transform: uppercase;color: #ffffff;border: none;border-radius: 2px;background-color: #2196f3;box-shadow: 0px 2px 2px 0px rgba(0, 0, 0, 0.14), 0px 3px 1px -2px rgba(0, 0, 0, 0.2), 0px 1px 5px 0px rgba(0, 0, 0, 0.12);font-family: 'Roboto', sans-serif;font-size: 14px;font-weight: 500;line-height: 36px;}"
                        + "table {position: relative;white-space: nowrap;margin: 10px;border: 1px solid rgba(0, 0, 0, 0.12);border-collapse: collapse;background-color: #FFF;box-shadow: 0px 2px 2px 0px rgba(0, 0, 0, 0.14), 0px 3px 1px -2px rgba(0, 0, 0, 0.2), 0px 1px 5px 0px rgba(0, 0, 0, 0.12);font-size: 13px;}"
                        + "tr {border-bottom: 1px solid #e0e0e0;height: 48px;}"
                        + "td,th {padding: 8px;}"
                        + "tbody > tr {background-color: #ffffff;}"
                        + "tbody > tr:hover {background-color: #f5f5f5;}"
                        + "</style>"
                        + "</head>"
                        + "<body>";
                break;
            case "datatable":
                MDXInterpreter.html += "<form>";
                MDXInterpreter.html += "<div class=\"textfield\"><input type=\"text\" name=\"recherche\" required><label>Recherche</label></div>";
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
                for (Etudiant etudiant : list_etudiant) {
                    MDXInterpreter.html += "<tr>";
                    MDXInterpreter.html += "<form>";
                    MDXInterpreter.html += "<td><a href='detail.html?id=" + etudiant.getIdentifiant() + "'>" + etudiant.getNom() + "</a></td>";
                    MDXInterpreter.html += "<td><a href='detail.html?id=" + etudiant.getIdentifiant() + "'>" + etudiant.getPrenom() + "</a></td>";
                    MDXInterpreter.html += "<td><a href='detail.html?id=" + etudiant.getIdentifiant() + "'>" + etudiant.getGroupe() + "</a></td>";
                    MDXInterpreter.html += "<td>";
                    MDXInterpreter.html += "<input type=\"hidden\" name=\"identifiant\" value=\"" + etudiant.getIdentifiant() + "\"/>";
                    MDXInterpreter.html += "<input type=\"submit\" name=\"action\" value=\"supprimer\"" + etudiant.getIdentifiant() + "/>";
                    MDXInterpreter.html += "</td>";
                    MDXInterpreter.html += "</form>";
                    MDXInterpreter.html += "</tr>";
                }
                MDXInterpreter.html += "<form>"
                        + "<div class=\"textfield\"><input type=\"text\" name=\"nom\"  required><label>Nom</label></div>"
                        + "<div class=\"textfield\"><input type=\"text\" name=\"prenom\" required><label>Prénom</label></div>"
                        + "<div class=\"textfield\"><input type=\"text\" name=\"groupe\"  required><label>Groupe</label></div>"
                        + "<input type=\"submit\" name=\"action\" value=\"ajouter\"/>"
                        + "</form>";
                break;
            case "card":
                MDXInterpreter.html += "<div>";
                MDXInterpreter.html += "<form action=\"detail.html\">";
                Etudiant etudiant = list_etudiant.get(0);
                MDXInterpreter.html += "<input type=\"hidden\" name=\"identifiant\" value=\"" + etudiant.getIdentifiant() + "\"/>";
                MDXInterpreter.html += "<div class=\"textfield\"><input type=\"text\" name=\"nom\" value=\"" + etudiant.getNom() + "\" required /><label>Nom</label></div>";
                MDXInterpreter.html += "<div class=\"textfield\"><input type=\"text\" name=\"prenom\" value=\"" + etudiant.getPrenom() + "\" required/><label>Prénom</label></div>";
                MDXInterpreter.html += "<div class=\"textfield\"><input type=\"text\" name=\"groupe\" value=\"" + etudiant.getGroupe() + "\" required/><label>Groupe</label></div>";
                MDXInterpreter.html += "<p><input type=\"submit\" name=\"action\" value=\"modifier\" ></input></p>";
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        switch (localName) {
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
