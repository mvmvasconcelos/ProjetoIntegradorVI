/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.security.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Vinicius
 */
public class Emprestimo {
    private int idEmprestimo;
    private int idResponsavel;
    private Timestamp retirada;
    private Timestamp devolucao;
    private ArrayList equipamento;

    public Emprestimo(int idEmprestimo, int idResponsavel, Timestamp retirada, ArrayList equipamento) {
        this.idEmprestimo = idEmprestimo;
        this.idResponsavel = idResponsavel;
        this.retirada = retirada;
        this.equipamento = equipamento;
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

    public ArrayList getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(ArrayList equipamento) {
        this.equipamento = equipamento;
    }
}
