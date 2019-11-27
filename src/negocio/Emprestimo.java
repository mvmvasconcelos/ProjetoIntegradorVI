/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Vinicius
 */
public class Emprestimo {
    private int idEmprestimo;
    private int idResponsavel;
    private Timestamp retirada;
    private Timestamp devolucao;
    private int idEquipamento;
    private String situacaoEmprestimo;

    public Emprestimo(int id, Timestamp retirada, int idResponsavel, int idEquipamento, String situacao) {
        this.idEmprestimo = id;
        this.idResponsavel = idResponsavel;
        this.retirada = retirada;
        this.idEquipamento = idEquipamento;
        this.situacaoEmprestimo = situacao;
    }
    
    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Timestamp getRetirada() {
        return retirada;
    }

    public void setRetirada(Timestamp retirada) {
        this.retirada = retirada;
    }

    public Timestamp getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(Timestamp devolucao) {
        this.devolucao = devolucao;
    }

    public int getIdEquipamento() {
        return idEquipamento;
    }

    public String getSituacaoEmprestimo() {
        return situacaoEmprestimo;
    }

    public void setSituacaoEmprestimo(String situacaoEmprestimo) {
        this.situacaoEmprestimo = situacaoEmprestimo;
    }
    

    //Armazena os ids dos equipamentos dentro de um arraylist
    public void setIdEquipamento(int idEquipamento) {
        this.idEquipamento = idEquipamento;
    }
    
    /**
     * Formata a hora da retirada de acordo com o timestamp
     * @return String - hora da retirada
     */
    public String getHoraRetirada(){
        String hora = formataHora(retirada);
        return hora;
    }
    /**
     * Formata a hora da devolução de acordo com o timestamp
     * @return String - hora da devolução
     */
    public String getHoraDevolucao(){
        String hora = formataHora(devolucao);
        return hora;
    }
    /**
     * Formata a data da retirada de acordo com o timestamp
     * @return String - data da retirada
     */
    public String getDataRetirada(){
        String data = formataData(retirada);
        return data;
    }
    /**
     * Formata a data da devolução de acordo com o timestamp
     * @return String - data da devolução
     */
    public String getDataDevolucao(){
        String data = formataData(devolucao);
        return data;
    }
    
    /**
     * Formata a hora a partir de um timestamp
     * @param ts
     * @return String
     */
    private String formataHora(Timestamp ts){
        SimpleDateFormat hf = new SimpleDateFormat("HH:mm");
        String horaFormatada = hf.format(ts);
        return horaFormatada;
    }
    /**
     * Formata data a partir de um timestamp
     * @param ts
     * @return String
     */
    private String formataData(Timestamp ts){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = df.format(ts);
        return dataFormatada;
    }    
}
