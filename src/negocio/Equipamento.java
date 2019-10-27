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
public class Equipamento {
    private int codigo;
    private String tipo;
    private String descricao;

    public Equipamento(int codigo, String tipo, String descricao) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.descricao = descricao;
    }    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }    
}
