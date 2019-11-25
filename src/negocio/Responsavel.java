/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

/**
 *
 * @author vinicius
 */
public class Responsavel {
    
    private static int idBD = 0; //int global para controlar o ID autoincrementado
    private int idResponsavel; //id do objeto
    private String nome;
    private int codigo;
    private String telefone;
    private String email;
    
    public Responsavel(int id, String nome, String email, String telefone, int codigo) {
        this.idResponsavel = Responsavel.idBD;
        this.nome = nome;
        this.codigo = codigo;
        this.telefone = telefone;
        this.email = email;
        Responsavel.idBD++; //incrementa o idResponsavel que será armazenado no BD
    }
    
    public static void setidBD(int id){
        idBD = id;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    /** Retorna string com todos os dados do equipamento
     * 
     * @return String
     */
    public String getTudo(){
        return "IDBD: " + idBD 
             + "\nID:" + idResponsavel
             + "\nNOME:" + nome
             + "\nCÓDIGO:" + codigo
             + "\nTELEFONE:" + telefone
             + "\nDESCRIÇÃO:" + email;
    }
        
}
