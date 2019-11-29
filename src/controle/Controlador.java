/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import negocio.Emprestimo;
import negocio.Equipamento;
import negocio.Responsavel;


/**
 *
 * @author vinicius
 */
public class Controlador {
    
    Connection conecta = null;
    PreparedStatement pst = null;
    ResultSet resultado = null;
    
    public Controlador() throws ClassNotFoundException{
        conecta = ConexaoBD.ConectaBancoDados();
    }
    
    /* Listas temporárias para armazenar os objetos criados    
       posteriormente serão substituídas pelo banco de dados */    
    public static ArrayList<Equipamento> listaDeEquipamentos = new ArrayList<>();
    public static ArrayList<Responsavel> listaDeResponsaveis = new ArrayList<>();
    public static ArrayList<Emprestimo> listaDeEmprestimos = new ArrayList<>();
    
    /**
     * Faz select na tabela responsaveis e armazena na lista
     */
    public void selectListaResponsaveis(){
        listaDeResponsaveis.clear();
        String sql = "select * from responsaveis";
        try{
            //Prepara a query
            pst = conecta.prepareStatement(sql);
            //executa a query
            resultado = pst.executeQuery();
            //pega os metadados do resultado
            ResultSetMetaData rsmd = resultado.getMetaData();
            //armazena número de colunas da tabela
            int nrDeColunas = rsmd.getColumnCount();
            //variáveis que serão gravadas com os dados
            int id = 0; String nome = null; String email = null; String telefone = null; int codigo = 0;
            //Percorre resultado enquanto houver registros
            while (resultado.next()) {
                //Para cada coluna no registro, salva o dado correspondente de acordo com a coluna
                for (int i = 1; i <= nrDeColunas; i++) {
                    switch(i){
                        case 1:                            
                            id = Integer.parseInt(resultado.getString(i));
                            break;
                        case 2:
                            nome = resultado.getString(i);
                            break;
                        case 3:
                            email = resultado.getString(i);
                            break;
                        case 4:
                            telefone = resultado.getString(i);
                            break;
                        case 5:
                            codigo = Integer.parseInt(resultado.getString(i));
                            break;
                    }
                }
                //Armazena o registro na listaDeResponsaveis
                adicionaResponsavelNaLista(id, nome, email,telefone, codigo);
            }
            pst.close();
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    
    /**
     * Faz select na tabela equipamentos e armazena na lista
     */
    public void selectListaEquipamentos(){
        //Limpa o arraylist
        listaDeEquipamentos.clear();
        String sql = "select * from equipamentos";
        try{
            //Prepara a query
            pst = conecta.prepareStatement(sql);
            //executa a query
            resultado = pst.executeQuery();
            //pega os metadados do resultado
            ResultSetMetaData rsmd = resultado.getMetaData();
            //armazena número de colunas da tabela
            int nrDeColunas = rsmd.getColumnCount();
            //variáveis que serão gravadas com os dados
            int id = 0; int codigo = 0; String tipo = null; String descricao = null; String situacao = null;
            //Percorre resultado enquanto houver registros
            while (resultado.next()) {
                //Para cada coluna no registro, salva o dado correspondente de acordo com a coluna
                for (int i = 1; i <= nrDeColunas; i++) {
                    switch(i){
                        case 1:
                            id = Integer.parseInt(resultado.getString(i));
                            break;
                        case 2:
                            codigo = Integer.parseInt(resultado.getString(i));
                            break;
                        case 3:
                            tipo = resultado.getString(i);
                            break;
                        case 4:
                            descricao = resultado.getString(i);
                            break;
                        case 5:
                            situacao = resultado.getString(i);
                            break;
                    }
                }
                //Armazena o registro na listaDeEmprestimos
                adicionaEquipamentoNaLista(id, codigo, tipo, descricao, situacao);
            }
            pst.close();
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    
    /**
     * Faz select na tabela emprestimos e armazena na lista
     */
    public void selectListaEmprestimos(){
        listaDeEmprestimos.clear();
        String sql = "select * from emprestimos";
        try{
            //Prepara a query
            pst = conecta.prepareStatement(sql);
            //executa a query
            resultado = pst.executeQuery();
            //pega os metadados do resultado
            ResultSetMetaData rsmd = resultado.getMetaData();
            //armazena número de colunas da tabela
            int nrDeColunas = rsmd.getColumnCount();
            //variáveis que serão gravadas com os dados
            int id = 0; Timestamp retirada = null; Timestamp devolucao = null; int idResponsavel = 0; int idEquipamento = 0; String situacao = null;
            //Percorre resultado enquanto houver registros
            while (resultado.next()) {
                //Para cada coluna no registro, salva o dado correspondente de acordo com a coluna
                for (int i = 1; i <= nrDeColunas; i++) {
                    switch(i){
                        case 1:
                            id = Integer.parseInt(resultado.getString(i));
                            break;
                        case 2:
                            retirada = Timestamp.valueOf(resultado.getString(i));
                            break;
                        case 3:
                            if (resultado.getString(i) != null) {
                                devolucao = Timestamp.valueOf(resultado.getString(i));
                            }
                            break;
                        case 4:
                            idResponsavel = Integer.parseInt(resultado.getString(i));
                            break;
                        case 5:
                            idEquipamento = Integer.parseInt(resultado.getString(i));
                            break;
                        case 6:
                            situacao = resultado.getString(i);
                            break;
                    }
                }
                //Armazena o registro na listaDeEmprestimos
                adicionaEmprestimoNaLista(id, retirada, devolucao, idResponsavel,idEquipamento, situacao);
                devolucao = null;
            }
            pst.close();
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    /**
     * Insere o novo equipamento no banco de dados
     */
    public void cadastraEquipamento(int codigo, String tipo, String descricao, String situacao){
        
        String sql = "insert into equipamentos (patrimonio, tipo, descricao, situacao)"
                        + "values (?, ?, ?, ?)";
        try{
            

             //Prepara a query
            pst = conecta.prepareStatement(sql);
            
            pst.setInt(1, codigo);
            pst.setString(2, tipo);
            pst.setString(3, descricao);
            pst.setString(4, situacao);
            //executa a query
            //resultado = pst.executeQuery();
            
            pst.execute();
            pst.close();
            //adicionaEquipamentoNaLista(id, codigo, tipo, descricao, situacao);
            
        }
        catch(SQLException error){
            
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);

        }
    }
    
    /**
     * Faz o update no banco
     * @param id
     * @param codigo
     * @param tipo
     * @param descricao
     * @param situacao 
     */
    public void modificaEquipamento(int id, int codigo, String tipo, String descricao, String situacao){
        
        String sql = "UPDATE equipamentos "
                   + "SET patrimonio = ?, tipo = ?, descricao = ?, situacao = ? "
                   + "WHERE idEquipamento = ?";
        try{
            

             //Prepara a query
            pst = conecta.prepareStatement(sql);
            
            pst.setInt(1, codigo);
            pst.setString(2, tipo);
            pst.setString(3, descricao);
            pst.setString(4, situacao);
            pst.setInt(5, id);
            //executa a query
            //resultado = pst.executeQuery();
            
            pst.execute();
            pst.close();
        }
        catch(SQLException error){
            
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);

        }
    }
    public void deletaEquipamento(int id){
        
        String sql = "DELETE FROM equipamentos "
                   + "WHERE idEquipamento = ?";
        try{
            

             //Prepara a query
            pst = conecta.prepareStatement(sql);
            
            pst.setInt(1, id);
            //executa a query
            //resultado = pst.executeQuery();
            
            pst.execute();
            pst.close();
        }
        catch(SQLException error){
            
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    /**
     * Insere o novo equipamento no banco de dados
     * @param nome
     * @param email
     * @param telefone
     * @param codigo
     */
    public void cadastraResponsavel(String nome, String email, String telefone, int codigo){
        String sql = "insert into responsaveis (nome, email, telefone, siape)"
                        + "values (?, ?, ?, ?)";
        try{
            //Prepara a query
            pst = conecta.prepareStatement(sql);
            
            pst.setString(1, nome);
            pst.setString(2, email);
            pst.setString(3, telefone);
            pst.setInt(4, codigo);
            //executa a query            
            pst.execute();
            pst.close();
            //adicionaResponsavelNaLista(id, nome, email, telefone, codigo);            
        }catch(SQLException error){
            
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    
    public void modificaResponsavel(int id, String nome, String email, String telefone, int codigo){
        
        String sql = "UPDATE responsaveis "
                   + "SET nome = ?, email = ?, telefone = ?, siape = ?"
                   + "WHERE idResponsavel = ?";
        try{
            

             //Prepara a query
            pst = conecta.prepareStatement(sql);
            
            pst.setString(1, nome);
            pst.setString(2, email);
            pst.setString(3, telefone);
            pst.setInt(4, codigo);
            pst.setInt(5, id);
            //executa a query
            //resultado = pst.executeQuery();
            
            pst.execute();
            pst.close();
        }
        catch(SQLException error){
            
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    
    public void cadastraEmprestimo(Timestamp retirada, int idResponsavel, int idEquipamento){
        String sql = "INSERT INTO emprestimos (retirada, idresponsavel, idequipamento, situacao) "
                   + "VALUES (?, ?, ?, 'P') ";
        try{
            //Prepara a query
            pst = conecta.prepareStatement(sql);

            pst.setTimestamp(1, retirada);
            pst.setInt(2, idResponsavel);
            pst.setInt(3, idEquipamento);
            //executa a query            
            pst.execute();
            pst.close();
        } catch (SQLException error){
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    
    public void devolveEmprestimo(int idEmprestimo, Timestamp devolucao, int idResponsavel){        
        String sql = "UPDATE emprestimos "
                   + "SET devolucao = ?, idresponsavel = ?, situacao = 'R' "
                   + "WHERE idemprestimo = ?";
        try{
            //Prepara a query
            pst = conecta.prepareStatement(sql);
            pst.setTimestamp(1, devolucao);
            pst.setInt(2, idResponsavel);
            pst.setInt(3, idEmprestimo);
            pst.execute();
            pst.close();
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    
    
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
            if (listaDeEquipamentos.get(i).getCodigo() == codigo) {
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
     * Busca na lista de empréstimos pelo ID e retorna apenas o objeto
     * @param id
     * @return emprestimo
     */
    public Emprestimo getEmprestimoPeloID(int id){
        for (int i = 0; i < listaDeEmprestimos.size(); i++) {
            if (listaDeEmprestimos.get(i).getIdEmprestimo() == id) {
                return listaDeEmprestimos.get(i);
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
    public Responsavel getResponsavelPeloCodigo(int id){
        for (int i = 0; i < listaDeResponsaveis.size(); i++) {
            if (listaDeResponsaveis.get(i).getCodigo()== id) {
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
     * Cria um novo responsavel e adiciona ele à lista
     * @param id
     * @param nome
     * @param codigo
     * @param telefone
     * @param email
     */
    public void adicionaResponsavelNaLista (int id, String nome, String email, String telefone, int codigo){
        Responsavel novo = new Responsavel(id, nome, email,telefone, codigo);
        listaDeResponsaveis.add(novo);
    }
    
    public void adicionaEmprestimoNaLista(int id, Timestamp retirada, Timestamp devolucao, int idResponsavel,int idEquipamento, String situacao){
        Emprestimo novo = new Emprestimo(id, retirada, devolucao, idResponsavel, idEquipamento, situacao);
        listaDeEmprestimos.add(novo);
    }
    
    public void adicionaEquipamentoNaLista(int id, int codigo, String tipo, String descricao, String situacao){
        Equipamento novo = new Equipamento(id, codigo, tipo, descricao, situacao);
        listaDeEquipamentos.add(novo);
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
                if (listaDeEquipamentos.contains(this.getEquipamentoPeloID(id))) {
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
            case "CODRESP":
                if (listaDeResponsaveis.contains(this.getResponsavelPeloCodigo(id))) {
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
        
        return "E".equals(getEquipamentoPeloCodigo(codigo).getSituacao());
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
}
