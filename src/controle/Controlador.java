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
    
    public static ArrayList<Emprestimo> getListaDeEmprestimos(){
        return listaDeEmprestimos;
    }

    public static ArrayList<Responsavel> getListaDeResponsaveis() {
        return listaDeResponsaveis;
    }

    public static ArrayList<Equipamento> getListaDeEquipamentos() {
        return listaDeEquipamentos;
    }

    /**
     * Busca na lista de equipamentos pelo ID e retorna apenas o objeto.
     * É necessário pois nem sempre o índice do arraylist será igual ao id
     * @param id
     * @return equipamento
     */
    public Equipamento getEquipamentoPeloID(int id){
        for (int i = 0; i < listaDeEquipamentos.size(); i++) {
            if (listaDeEquipamentos.get(i).getIdEquipamento()== id) {
                return listaDeEquipamentos.get(i);
            }            
        }
        return null;
    }
    /**
     * Busca na lista de equipamentos pelo Código e retorna apenas o objeto.
     * É necessário pois nem sempre o índice do arraylist será igual ao id
     * @param codigo
     * @return equipamento
     */
    public Equipamento getEquipamentoPeloCodigo(int codigo){
        for (int i = 0; i < listaDeEquipamentos.size(); i++) {
            if (listaDeEquipamentos.get(i).getCodigo()== codigo) {
                return listaDeEquipamentos.get(i);
            }            
        }
        return null;
    }
    
    /**
     * Busca na lista de responsaveis pelo ID e retorna apenas o objeto.
     * É necessário pois nem sempre o índice do arraylist será igual ao id
     * @param id
     * @return responsavel
     */
    public Responsavel getResponsavelPeloID(int id){
        for (int i = 0; i < listaDeResponsaveis.size(); i++) {
            if (listaDeResponsaveis.get(i).getIdResponsavel()== id) {
                return listaDeResponsaveis.get(i);
            }            
        }
        return null;
    }
    
    /**
     * Recebe o id e percorre a lista de responsáveis para pegar o nome
     * correspondente ao id
     * @param id id do responsavel
     * @return String
     */
    public String getNomeResponsavel(int id){
        for (int i = 0; i < listaDeResponsaveis.size(); i++) {
            if (listaDeResponsaveis.get(i).getIdResponsavel() == id) {
                return listaDeResponsaveis.get(i).getNome();
            }            
        }
        return null;
    }
    
    /**
     * Recebe o id e percorre a lista de responsáveis para pegar o nome
     * correspondente ao id
     * @param id id do responsavel
     * @return String
     */
    public String getTelefoneResponsavel(int id){
        for (int i = 0; i < listaDeResponsaveis.size(); i++) {
            if (listaDeResponsaveis.get(i).getIdResponsavel() == id) {
                return listaDeResponsaveis.get(i).getTelefone();
            }            
        }
        return null;
    }
    
    /**
     * Cria um array com todos os empréstimos que contenham o equipamento
     * relacionado
     * @param id id do equipamento
     * @return lista de emprestimos
     */
    public ArrayList<Emprestimo> getEmprestimosPeloIdEquipamento(int id){
        ArrayList<Emprestimo> emprestimoPorEquipamento = new ArrayList<>();
        for (int i = 0; i < listaDeEmprestimos.size(); i++) {
            if (listaDeEmprestimos.get(i).getIdEquipamento() == id) {
                emprestimoPorEquipamento.add(listaDeEmprestimos.get(i));
            }
        }
        return emprestimoPorEquipamento;
    }
    
    /** 
     * Cria um novo equipamento e adiciona ele à lista
     * @param codigo
     * @param tipo
     * @param descricao
     */
    public void cadastraEquipamento(int codigo, String tipo, String descricao, String situacao){
        Equipamento novo = new Equipamento(codigo, tipo, descricao, situacao);
        salvaObjeto(novo);
    }
    
    /** 
     * Cria um novo responsavel e adiciona ele à lista
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
     * Verifica qual dos empréstimos contém aquele equipamento e está
     * com a situação pendente (P)
     * @param codigo
     * @return o objeto emprestimo
     */
    public Emprestimo getEmprestimoPeloCodigoDoEquipamento(int codigo){
        int idEqp = getEquipamentoPeloCodigo(codigo).getIdEquipamento();
        ArrayList<Emprestimo> temp = getEmprestimosPeloIdEquipamento(idEqp);
        for (int i = 0; i < temp.size(); i++) {
            if ("P".equals(temp.get(i).getSituacaoEmprestimo())) {
                return temp.get(i);
            }
        }
        return null;
    }
        
    /**
     * Checa se o objeto existe de acordo com o tipo buscado
     * IDRESP - idResponsavel
     * IDEQP - idEquipamento
     * IDEQP - idEmprestimo
     * IDEMP - codigoEquipamento
     * @param id identificador do objeto
     * @param tipo conforme acima
     * @return bool se houver ou não houver o item na lista
     */
    public boolean existeObjeto(int id, String tipo){
        switch(tipo.toUpperCase()){
            case "IDRESP":
                if (listaDeResponsaveis.contains(this.getResponsavelPeloID(id))) {
                    return true;
                }
            case "IDEQP":
                if (listaDeEquipamentos.contains(this.getEquipamentoPeloCodigo(id))) {
                    return true;
                }
            case "IDEMP":
                if (listaDeEmprestimos.contains(this.getEmprestimosPeloIdEquipamento(id))) {
                    return true;
                }
            case "CODEQP":
                if (listaDeEquipamentos.contains(this.getEquipamentoPeloCodigo(id))) {
                    return true;
                }
            default:
                return false;
        }                
    }
    
    /**
     * Verifica a situação do equipamento de acordo com o id
     * @param codigo
     * @return bool
     */
    public boolean estaEmprestado(int codigo){        
        return "E".equals(listaDeEquipamentos.get(getEquipamentoPeloCodigo(codigo).getIdEquipamento()).getSituacao());
    }
    public String situacao(String situacao){
        switch (situacao.toUpperCase()){
            case "D":
                return "Disponível";
            case "E":
                return "Emprestado";
            case "I":
                return "Indisponível";
            
        }
        return "";
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
