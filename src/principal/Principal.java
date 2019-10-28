/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
        
        //Construa um DateFormat, com o o formato pretendido
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hf = new SimpleDateFormat("HH:mm");
        

        // Obtenha a String que representa a data nesse formato
        String formattedDate = df.format(ts);
        String formattedHora = hf.format(ts);
        
        System.out.println("Timestamp: " + ts);
        System.out.println("Dia: " + formattedDate);
        System.out.println("Hora: " + formattedHora);
        
        
    }
    
}
