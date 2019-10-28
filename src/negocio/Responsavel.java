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
    private int id; //id do objeto
    private String nome;
    private int codigo;
    private String telefone;
    private String email;

    public Responsavel(String nome, int codigo, String telefone, String email) {
        this.id = Responsavel.idBD;
        this.nome = nome;
        this.codigo = codigo;
        this.telefone = telefone;
        this.email = email;
        Responsavel.idBD++; //Auto-incrementa o id que será armazenado no BD
    }

    public int getId() {
        return id;
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
             + "\nID:" + id
             + "\nNOME:" + nome
             + "\nCÓDIGO:" + codigo
             + "\nTELEFONE:" + telefone
             + "\nDESCRIÇÃO:" + email;
    }
        
}
