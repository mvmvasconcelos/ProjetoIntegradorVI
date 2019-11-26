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
    /**
     * Creates new form TelaEquipamento
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
    }
    
    public TelaEquipamento(java.awt.Frame parent, boolean modal, int codigo, Controlador contr) {
        super(parent, modal);
        ctl = contr;
        initComponents();
        preencheFormulario(codigo);
    }
    
    private void cadastraEquipamento(){
        boolean camposValidados = false;
        int id = Integer.parseInt(txtIdEquipamento.getText());
        int codigo = Integer.parseInt(txtCodigo.getText()); 
        String tipo = txtTipo.getText();
        String descricao = txtDescricao.getText();
        String situacao = pegaRadioSelecionado();
        if (validaCampo(txtCodigo)) {
            txtCodigo.setBackground(Color.WHITE);
            if (validaCampo(txtTipo)) {
                txtTipo.setBackground(Color.WHITE);
                if (validaCampo(txtDescricao)) {
                    txtDescricao.setBackground(Color.WHITE);
                    camposValidados = true;
                }
            }
        }
        if (camposValidados) {
            if (ctl.existeObjeto(id, "IDEQP")) {
                JOptionPane.showMessageDialog(null, "Já existe equipamento com esse ID");
                //this.dispose();
            } else {
                if (ctl.existeObjeto(codigo, "CODEQP")){
                    JOptionPane.showMessageDialog(null, "Já existe equipamento com esse CÓDIGO");
                    txtCodigo.requestFocus();
                } else {
                    ctl.cadastraEquipamento(id, codigo, tipo, descricao, situacao);
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                    this.dispose();
                }
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
    
    
    /**
     * Preenche os dados do formulário de acordo com o id do equipamento
     * @param idEquipamento 
     */
    private void preencheFormulario(int idEquipamento){
        //Armazena o objeto pego da lista do controlador em um objeto local para facilitar
        equipamento = ctl.getEquipamentoPeloID(idEquipamento);
        //Altera os textos no formulário
        txtIdEquipamento.setText(String.valueOf(idEquipamento));
        txtCodigo.setText(String.valueOf(equipamento.getCodigo()));
        txtTipo.setText(equipamento.getTipo());
        txtDescricao.setText(equipamento.getDescricao());
        marcaRadioButton(equipamento.getSituacao());
        btnCadastrar.setText("Salvar Modificações");
    }
    private void preencheFormulario(){
        txtIdEquipamento.setText(String.valueOf(equipamento.getIdEquipamentoBD()));
        marcaRadioButton("D");
        //limpaFormulario();
        btnCadastrar.setText("Cadastrar Equipamento");
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
        lblNumeroID = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        lblSituacao = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        txtIdEquipamento = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDescricao = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        radioDisponivel = new javax.swing.JRadioButton();
        radioEmprestado = new javax.swing.JRadioButton();
        radioIndisponivel = new javax.swing.JRadioButton();
        txtTipo = new javax.swing.JTextField();
        btnLimpar = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Cadastrar Equipamento");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblNumeroID.setText("Número ID:");

        lblTipo.setText("Tipo:");

        lblDescricao.setText("Descrição:");

        lblSituacao.setText("Situação:");

        lblCodigo.setText("Código:");

        txtIdEquipamento.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        txtIdEquipamento.setText("1234");

        txtCodigo.setText("123456");
        txtCodigo.setToolTipText("CÓDIGO");

        txtDescricao.setText("Notebook HP");
        txtDescricao.setToolTipText("DESCRIÇÃO");

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

        btnLimpar.setText("Limpar Campos");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnCadastrar.setText("Cadastrar Equipamento");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
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
                            .addComponent(lblNumeroID)
                            .addComponent(lblTipo)
                            .addComponent(lblDescricao)
                            .addComponent(lblCodigo))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtIdEquipamento)
                                        .addGap(195, 195, 195)
                                        .addComponent(lblSituacao))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDescricao)
                                .addGap(93, 93, 93)))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCadastrar)
                        .addGap(133, 133, 133)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVoltar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNumeroID)
                            .addComponent(txtIdEquipamento)
                            .addComponent(lblSituacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(btnCadastrar)
                    .addComponent(btnVoltar))
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

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastraEquipamento();
    }//GEN-LAST:event_btnCadastrarActionPerformed

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
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblNumeroID;
    private javax.swing.JLabel lblSituacao;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.ButtonGroup radGrupo;
    private javax.swing.JRadioButton radioDisponivel;
    private javax.swing.JRadioButton radioEmprestado;
    private javax.swing.JRadioButton radioIndisponivel;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JLabel txtIdEquipamento;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
