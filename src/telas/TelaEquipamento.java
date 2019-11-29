/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import controle.Controlador;
import java.awt.Color;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import negocio.Equipamento;

/**
 *
 * @author vinicius
 */
public class TelaEquipamento extends javax.swing.JDialog {
    Controlador ctl;
    Equipamento equipamento;
    Boolean novoEqp; //true se for novo, false se for existente
    int codEqp;
    int idEquipamento;
    String tipoEqp;
    /**
     * Creates new form TelaEquipamento
     * @param parent
     * @param modal
     */
    public TelaEquipamento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        preencheFormulario();
    }
    public TelaEquipamento(java.awt.Frame parent, boolean modal, Controlador contr) {
        super(parent, modal);
        ctl = contr;
        initComponents();
        preencheFormulario();
        novoEqp = true;
    }
    
    public TelaEquipamento(java.awt.Frame parent, boolean modal, int id, Controlador contr) {
        super(parent, modal);
        ctl = contr;
        idEquipamento = id;
        initComponents();
        preencheFormulario(id);
        novoEqp = false;
    }
    
    private void executaEquipamento(){
        boolean camposValidados = false;
        boolean continua = true;
        int codigo = 0;
        String tipo = null;
        String descricao = null;
        String situacao = null;
        if (validaCampo(txtCodigo)) {
            txtCodigo.setBackground(Color.WHITE);
            if (validaCampo(txtTipo)) {
                txtTipo.setBackground(Color.WHITE);
                if (validaCampo(txtDescricao)) {
                    txtDescricao.setBackground(Color.WHITE);
                    camposValidados = true;
                    codigo = Integer.parseInt(txtCodigo.getText()); 
                    tipo = txtTipo.getText();
                    descricao = txtDescricao.getText();
                    situacao = pegaRadioSelecionado();
                }
            }
        }      
        
        //Se passou na validação de dados e o equipamento for novo
        if (camposValidados && novoEqp) {
            //Se já existir o código, avisa o usuario
            if (ctl.existeObjeto(codigo, "CODEQP")){
                JOptionPane.showMessageDialog(null, "Já existe equipamento com esse CÓDIGO");
                txtCodigo.requestFocus();
            } else {
                //Se não existir, faz o cadastro
                ctl.cadastraEquipamento(codigo, tipo, descricao, situacao);
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                this.dispose();
            }            
          //Se passou na validação de dados e não é um equipamento novo  
        } else if(camposValidados && !novoEqp) {
            //Se o usuario digitou um novo código
            if (codigo != codEqp) {
                //Pergunta se foi de propósito, se não for, volta pro formulario
                if (JOptionPane.showConfirmDialog(null, "Você alterou o CÓDIGO do equipamento.\nTem certeza que deseja continuar?", "ATENÇÃO", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    txtCodigo.requestFocus();
                    continua = false;
                //Se foi, verifica se o código já existe ou não
                } else  if (ctl.existeObjeto(codigo, "CODEQP")){
                    JOptionPane.showMessageDialog(null, "Já existe equipamento com esse CÓDIGO");
                    txtCodigo.requestFocus();
                    continua = false;
                }
            }
            //Se o usuario alterou o tipo e não precisou passar pela validação anterior
            //Isso evita que apareça o JOPane antes de resolver o anterior
            if (!tipo.equals(tipoEqp) && continua){
                if (JOptionPane.showConfirmDialog(null, "Você alterou o TIPO do equipamento.\nTem certeza que deseja continuar?", "ATENÇÃO", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    txtTipo.requestFocus();
                    continua = false;
                }
            }      
            //Se continua ainda é true, quer dizer que ele passou pelas outras validações
            if (continua) {
                ctl.modificaEquipamento(idEquipamento, codigo, tipo, descricao, situacao);
                JOptionPane.showMessageDialog(null, "Modificado com sucesso!");
                this.dispose();
            }
        }
    }
    
    private boolean validaCampo(JTextField jTF){
        if (jTF.getText().equals("")) {
            jTF.setBackground(new Color (255,172,166));
            JOptionPane.showMessageDialog(null, "O campo " + jTF.getToolTipText() + " precisa ser válido");
            jTF.requestFocus();
            return false;
        }
        return true;
    }
    
    private void excluiEquipamento(){
        if (!novoEqp) {
            if (equipamento.getSituacao().equals("E")) {
                JOptionPane.showMessageDialog(null, "Não é possível excluir um equipamento atualmente emprestado.");
                this.dispose();
            } else {
                if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o equipamento?", "ATENÇÃO", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    txtCodigo.requestFocus();
                } else {
                    ctl.deletaEquipamento(idEquipamento);
                    JOptionPane.showMessageDialog(null, "Equipamento removido com sucesso!");
                    this.dispose();
                }
            }
        }
    }
    
    
    /**
     * Preenche os dados do formulário de acordo com o id do equipamento
     * @param idEquipamento 
     */
    private void preencheFormulario(int idEquipamento){
        //Armazena o objeto pego da lista do controlador em um objeto local para facilitar
        equipamento = ctl.getEquipamentoPeloID(idEquipamento);
        //Altera os textos no formulário
        codEqp = equipamento.getCodigo();
        txtCodigo.setText(String.valueOf(codEqp));
        tipoEqp = equipamento.getTipo();
        txtTipo.setText(tipoEqp);
        txtDescricao.setText(equipamento.getDescricao());
        marcaRadioButton(equipamento.getSituacao());
        if (equipamento.getSituacao().equals("E")) {
            Enumeration<AbstractButton> enumeration = radGrupo.getElements();
            while (enumeration.hasMoreElements()) {
                enumeration.nextElement().setEnabled(false);
            }
        }
        radioEmprestado.setEnabled(false);
        btnExecutar.setText("Salvar Modificações");
        lblTitulo.setText("Editar Equipamento " + String.valueOf(equipamento.getCodigo()));
    }
    private void preencheFormulario(){
        marcaRadioButton("D");
        //Desativa opção de situação emprestado para o usuario não criar um equipamento já cadastrado
        radioEmprestado.setEnabled(false);
        //Desabilita o botão de excluir
        btnExcluir.setEnabled(false);
        limpaFormulario();
        btnExecutar.setText("Cadastrar Equipamento");
    }
    private void limpaFormulario(){
        txtCodigo.setText("");
        txtTipo.setText("");
        txtDescricao.setText("");
    }    
    
    /**
     * Define quais dos três radios buttons estão selecionados
     * @param situacao 
     */
    private void marcaRadioButton(String situacao){
        switch (situacao){
            case "D":
                radioDisponivel.setSelected(true);
                break;
            case "E":
                radioEmprestado.setSelected(true);
                break;
            case "I":
                radioIndisponivel.setSelected(true);
                break;
        }
    }
    
    private String pegaRadioSelecionado(){
        for (Enumeration<AbstractButton> buttons = radGrupo.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return String.valueOf(button.getText().charAt(0));
            }
        }
        return null;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radGrupo = new javax.swing.ButtonGroup();
        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblTipo = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        lblSituacao = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDescricao = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        radioDisponivel = new javax.swing.JRadioButton();
        radioEmprestado = new javax.swing.JRadioButton();
        radioIndisponivel = new javax.swing.JRadioButton();
        txtTipo = new javax.swing.JTextField();
        btnLimpar = new javax.swing.JButton();
        btnExecutar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informações do Equipamento");

        lblTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Cadastrar Equipamento");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTipo.setText("Tipo:");

        lblDescricao.setText("Descrição:");

        lblSituacao.setText("Situação:");

        lblCodigo.setText("Código:");

        txtCodigo.setText("123456");
        txtCodigo.setToolTipText("CÓDIGO");
        txtCodigo.setNextFocusableComponent(txtTipo);

        txtDescricao.setText("Notebook HP");
        txtDescricao.setToolTipText("DESCRIÇÃO");
        txtDescricao.setNextFocusableComponent(btnExecutar);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        radGrupo.add(radioDisponivel);
        radioDisponivel.setSelected(true);
        radioDisponivel.setText("Disponível");
        jPanel2.add(radioDisponivel);

        radGrupo.add(radioEmprestado);
        radioEmprestado.setText("Emprestado");
        jPanel2.add(radioEmprestado);

        radGrupo.add(radioIndisponivel);
        radioIndisponivel.setText("Indisponível");
        jPanel2.add(radioIndisponivel);

        txtTipo.setText("Notebook");
        txtTipo.setToolTipText("TIPO");
        txtTipo.setNextFocusableComponent(txtDescricao);

        btnLimpar.setText("Limpar Campos");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnExecutar.setText("Cadastrar Equipamento");
        btnExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecutarActionPerformed(evt);
            }
        });

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir Equipamento");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipo)
                            .addComponent(lblDescricao)
                            .addComponent(lblCodigo))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDescricao))
                        .addGap(24, 24, 24)
                        .addComponent(lblSituacao)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnExecutar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVoltar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSituacao)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTipo)
                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDescricao)
                            .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpar)
                    .addComponent(btnExecutar)
                    .addComponent(btnVoltar)
                    .addComponent(btnExcluir))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpaFormulario();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecutarActionPerformed
        executaEquipamento();
    }//GEN-LAST:event_btnExecutarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluiEquipamento();
    }//GEN-LAST:event_btnExcluirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaEquipamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEquipamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEquipamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEquipamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaEquipamento dialog = new TelaEquipamento(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblSituacao;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.ButtonGroup radGrupo;
    private javax.swing.JRadioButton radioDisponivel;
    private javax.swing.JRadioButton radioEmprestado;
    private javax.swing.JRadioButton radioIndisponivel;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
