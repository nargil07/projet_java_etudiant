/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_projet;

import mini_projet.DAO.EtudiantDAO;

/**
 *
 * @author antony
 */
public class Mini_projet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EtudiantDAO et = new EtudiantDAO("test.xml");
        et.addEtudiant("gueffier", "Athénaïs", "1");
    }
}
