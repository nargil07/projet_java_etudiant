/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_projet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import mini_projet.DAO.EtudiantDAO;
import org.xml.sax.SAXException;

/**
 *
 * @author antony
 */
public class Mini_projet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            EtudiantDAO etudiantDAO = new EtudiantDAO("list_etudiant.xml");
            etudiantDAO.addEtudiant("jojo", "bernard", "3");
            etudiantDAO.save();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Mini_projet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Mini_projet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Mini_projet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Mini_projet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
