/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.Timestamp;
import java.util.ArrayList;
import negocio.Emprestimo;
import negocio.Equipamento;
import negocio.Responsavel;


/**
 *
 * @author vinicius
 */
public class Controlador {
    /* Listas temporária para armazenar os objetos criados    
       posteriormente serão substituídas pelo banco de dados */    
    public static ArrayList<Equipamento> listaDeEquipamentos = new ArrayList<>();
    public static ArrayList<Responsavel> listaDeResponsaveis = new ArrayList<>();
    public static ArrayList<Emprestimo> listaDeEmprestimos = new ArrayList<>();
    
    public ArrayList getListaEmprestimos(){
        return listaDeEmprestimos;
    }
    
    /** Cria um novo equipamento e adiciona ele à lista
     *
     * @param codigo
     * @param tipo
     * @param descricao
     */
    public void cadastraEquipamento(int codigo, String tipo, String descricao){
        Equipamento novo = new Equipamento(codigo, tipo, descricao);
        salvaObjeto(novo);
    }
    
    /** Cria um novo responsavel e adiciona ele à lista
     *
     * @param nome
     * @param codigo
     * @param telefone
     * @param email
     */
    public void cadastraResponsavel ( String nome, int codigo, String telefone, String email){
        Responsavel novo = new Responsavel(nome, codigo, telefone, email);
        salvaObjeto(novo);
    }
    
    public void cadastraEmprestimo(int idResponsavel, Timestamp retirada, int idEquipamento){
        Emprestimo novo = new Emprestimo(idResponsavel, retirada, idEquipamento);
        salvaObjeto(novo);
    }
    
    /**
     * Recebe o id e percorre a lista de responsáveis para pegar o nome
     * correspondente ao id
     * @param id
     * @return String
     */
    public String getNomeResponsavel(int id){
        for (int i = 0; i < listaDeResponsaveis.size(); i++) {
            if (listaDeResponsaveis.get(i).getId() == id) {
                return listaDeResponsaveis.get(i).getNome();
            }            
        }
        return null;
    }
    
    //Adiciona o objeto à lista correspondente
    public void salvaObjeto(Equipamento equipamento){
        listaDeEquipamentos.add(equipamento);
    }
    public void salvaObjeto(Responsavel responsavel){
        listaDeResponsaveis.add(responsavel);
    }
    public void salvaObjeto(Emprestimo emprestimo){
        listaDeEmprestimos.add(emprestimo);
    }
}
