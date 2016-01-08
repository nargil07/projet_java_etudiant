/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_projet.DAO;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mini_projet.Entity.Etudiant;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Cette class vas chercher les infos dans un fichier xml. Et contiendra la
 * liste des etudiants
 *
 * @author antony
 */
public class EtudiantDAO {

    private final HashMap<String, Etudiant> listEtudiants;
    private final File f;

    public EtudiantDAO(String pathFichier) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        listEtudiants = new HashMap<>();
        f = new File(pathFichier);
        if (f.exists()) {
            Document document = db.parse(f);
            NodeList listNode = document.getDocumentElement().getChildNodes();
            for (int i = 0; listNode.getLength() > i; ++i) {
                Node node_id = listNode.item(i);
                if (node_id.getNodeType() == Node.ELEMENT_NODE) {
                    Etudiant etudiant = new Etudiant();
                    etudiant.setIdentifiant(node_id.getAttributes().getNamedItem("identifiant").getNodeValue());
                    NodeList listAttributes = node_id.getChildNodes();
                    for (int j = 0; listAttributes.getLength() > j; ++j) {
                        Node value = listAttributes.item(j);
                        if (value.getNodeType() == Node.ELEMENT_NODE) {
                            switch (value.getNodeName()) {
                                case "nom":
                                    etudiant.setNom(value.getTextContent());
                                    break;
                                case "prenom":
                                    etudiant.setPrenom(value.getTextContent());
                                    break;
                                case "groupe":
                                    etudiant.setGroupe(value.getTextContent());
                                    break;
                            }
                        }
                    }
                    listEtudiants.put(etudiant.getIdentifiant(), etudiant);
                }
            }
        } else {
            f.createNewFile();
        }
    }

    private String generate(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
        String pass = "";
        for (int x = 0; x < length; x++) {
            int i = (int) Math.floor(Math.random() * 62); // Si tu supprimes des lettres tu diminues ce nb
            pass += chars.charAt(i);
        }
        System.out.println(pass);
        return pass;
    }

    public void addEtudiant(String nom, String prenom, String groupe){
        Etudiant etudiant = new Etudiant();
        String identifiant = null;
        do{
            identifiant = generate(10);
        }while(listEtudiants.get(identifiant) != null);
        etudiant.setIdentifiant(identifiant);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setGroupe(groupe);
        listEtudiants.put(etudiant.getIdentifiant(), etudiant);
    }

    public void save() throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element elem = doc.createElement("list_etudiant");
        for (Map.Entry<String, Etudiant> entrySet : listEtudiants.entrySet()) {
            Etudiant value = entrySet.getValue();
            Element etudiant = doc.createElement("etudiant");
            etudiant.setAttribute("identifiant", value.getIdentifiant());

            Element elem_nom = doc.createElement("nom");
            elem_nom.setTextContent(value.getNom());
            etudiant.appendChild(elem_nom);

            Element elem_prenom = doc.createElement("prenom");
            elem_prenom.setTextContent(value.getPrenom());
            etudiant.appendChild(elem_prenom);

            Element elem_groupe = doc.createElement("groupe");
            elem_groupe.setTextContent(value.getGroupe());
            etudiant.appendChild(elem_groupe);

            elem.appendChild(etudiant);
        }
        doc.appendChild(elem);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(f);
        transformer.transform(new DOMSource(doc), output);
    }

}
