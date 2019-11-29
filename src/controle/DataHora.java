/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Classe para facilitar manipulação de data e hora
 * @author vinicius
 */
public class DataHora {
    
    public static String dataFormatada (Timestamp ts)    {
        //Formata a data conforme modelo
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //Atribui a formatação à String
        String dataFormatada = df.format(ts);
        return dataFormatada;
    }
    public static String dataFormatada (long ts)    {
        //Formata a data conforme modelo
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //Atribui a formatação à String
        String dataFormatada = df.format(new Timestamp(ts));
        return dataFormatada;
    }
    
    /**
     * Converte data simples em long, sem pegar hora
     * @param data
     * @return 
     */
    public static long converteDataParaLong(String data){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date d = df.parse(data);
            long milliseconds = d.getTime();
            return milliseconds;
        } catch (java.text.ParseException e) {
            return 0;
        }
    }
    
    public static String horaFormatada (Timestamp ts){
        if (ts != null) {
            //Formata a data conforme modelo
            SimpleDateFormat hf = new SimpleDateFormat("HH:mm");
            //Atribui a formatação à String
            String horaFormatada = hf.format(ts);
            return horaFormatada;            
        } else {
            return "30/12/0002";
        }
    }
    
    public static Timestamp converteParaTimestamp(long data){
        Timestamp ts = new Timestamp(data);
        return ts;
    }
}
