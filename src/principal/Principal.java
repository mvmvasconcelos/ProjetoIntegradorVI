/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.sql.Timestamp;
import telas.TelaPrincipal;

/**
 *
 * @author vinicius
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
        
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println("Current Time Stamp: " + ts);
        
    }
    
}
