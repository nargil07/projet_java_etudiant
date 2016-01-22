package mini_projet.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import mini_projet.DAO.EtudiantDAO;
import mini_projet.Entity.Etudiant;
import mini_projet.sax.MDXInterpreter;
import mini_projet.sax.SAXParser;

public class HTTPServerThread extends Thread {

    private Socket s;

    public HTTPServerThread(Socket sock) {
        super();
        this.s = sock;
    }

    public void run() {
        try {
            EtudiantDAO etudiantDAO = new EtudiantDAO("list_etudiant.xml");
            File f = null;
            InputStream is = this.s.getInputStream();
            String[] attributes;
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    "UTF-8"));
            String rq = br.readLine();
            if ((rq != null) && (rq.startsWith("GET "))) {
                rq = rq.substring(5, rq.indexOf("HTTP"));
                String url = rq.split("\\?")[0];
                if (rq.split("\\?").length > 1) {
                    attributes = rq.split("\\?")[1].split("\\&");
                } else {
                    attributes = new String[0];
                }
                List<Etudiant> list_etudiant = new ArrayList<>();
                switch (url) {
                    case "": //caractere quand on met pas index.html
                    case " ": //caractere quand on met pas index.html
                    case "index.html":
                        switch (attributes.length) {
                            case 2:
                                String action = attributes[1].split("=")[1];
                                action = action.substring(0, action.length() - 1);
                                if (action.equals("supprimer")) {
                                    String id = attributes[0].split("=")[1];
                                    etudiantDAO.removeEtudiant(etudiantDAO.getEtudiant(id));
                                    list_etudiant = etudiantDAO.getListEtudiants();
                                } else {
                                    if (attributes[0].split("=").length > 1) {
                                        String query = attributes[0].split("=")[1];
                                        list_etudiant = etudiantDAO.recherche(query);
                                    }else{
                                        list_etudiant = etudiantDAO.getListEtudiants();
                                    }
                                }
                                break;
                            case 4:
                                String nom = attributes[0].split("=")[1];
                                String prenom = attributes[1].split("=")[1];
                                String groupe = attributes[2].split("=")[1];
                                etudiantDAO.addEtudiant(nom, prenom, groupe);
                                list_etudiant = etudiantDAO.getListEtudiants();
                                break;
                            default:
                                list_etudiant = etudiantDAO.getListEtudiants();
                                break;
                        }
                        f = new File("index.mdx ");
                        if (f.isFile()) {

                            SAXParser parser = new SAXParser(list_etudiant);
                            parser.parse(f);
                            this.s.getOutputStream().write(MDXInterpreter.getHtml().getBytes());
                        }
                        break;
                    case "detail.html":

                        switch (attributes.length) {
                            case 1:
                                String id = attributes[0].split("=")[1];
                                id = id.substring(0, id.length() - 1);
                                f = new File("detail.mdx");
                                if (f.isFile()) {
                                    list_etudiant = new ArrayList<>();
                                    list_etudiant.add(etudiantDAO.getEtudiant(id));
                                    SAXParser parser = new SAXParser(list_etudiant);
                                    parser.parse(f);
                                    this.s.getOutputStream().write(MDXInterpreter.getHtml().getBytes());
                                }
                                break;
                            case 5:
                                String identifiant = attributes[0].split("=")[1];
                                String nom = attributes[1].split("=")[1];
                                String prenom = attributes[2].split("=")[1];
                                String groupe = attributes[3].split("=")[1];
                                Etudiant etudiant = etudiantDAO.getEtudiant(identifiant);
                                etudiant.setNom(nom);
                                etudiant.setPrenom(prenom);
                                etudiant.setGroupe(groupe);
                                etudiantDAO.updateEtudiant(etudiant);
                                f = new File("detail.mdx");
                                if (f.isFile()) {
                                    list_etudiant = new ArrayList<>();
                                    list_etudiant.add(etudiantDAO.getEtudiant(identifiant));
                                    SAXParser parser = new SAXParser(list_etudiant);
                                    parser.parse(f);
                                    this.s.getOutputStream().write(MDXInterpreter.getHtml().getBytes());
                                }
                                break;
                        }
                        break;

                    default:
                        System.out.println("default");
                        break;
                }

            }
            br.close();
        } catch (IOException e) {
        }
        try {
            this.s.close();
        } catch (IOException e) {
        }
    }
}
