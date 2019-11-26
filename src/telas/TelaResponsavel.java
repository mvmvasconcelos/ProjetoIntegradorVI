/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import controle.Controlador;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import negocio.Responsavel;

/**
 *
 * @author Vinicius
 */
public class TelaResponsavel extends javax.swing.JDialog {
    Controlador ctl;
    Responsavel responsavel;
    boolean novoResp;
    int idResponsavel;
    int codResp;
    
    
    /**
     * Creates new form TelaResponsavel
     */
    public TelaResponsavel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    public TelaResponsavel(java.awt.Frame parent, boolean modal, Controlador cont) {
        super(parent, modal);
        ctl = cont;
        initComponents();
        //limpaFormulario();
        preencheFormulario();
        novoResp = true;
        System.out.println("ID ao abrir cadastro respon " + Responsavel.getIdDB());
        
    }
    public TelaResponsavel(java.awt.Frame parent, boolean modal, int id, Controlador cont) {
        super(parent, modal);
        ctl = cont;
        initComponents();
        novoResp = false;
        idResponsavel = id;
        preencheFormulario(id);
    }
    
    
    private void executaResponsavel(){
        boolean camposValidados = false;
        boolean continua = true;
        int id = 0;
        int codigo = 0;
        String nome = null;
        String telefone = null;
        String email = null;
        if (validaCampo(txtNome)) {
            txtNome.setBackground(Color.WHITE);
            if (validaCampo(txtCodigo)) {
                txtCodigo.setBackground(Color.WHITE);
                if (validaCampo(txtTelefone)) {
                    txtTelefone.setBackground(Color.WHITE);
                    if (validaCampo(txtEmail)) {
                        txtTelefone.setBackground(Color.WHITE);
                        camposValidados = true;                        
                        id = Responsavel.getIdDB();
                        codigo = Integer.parseInt(txtCodigo.getText()); 
                        nome = txtNome.getText();
                        telefone = txtTelefone.getText();
                        email = txtEmail.getText();
                    }
                }
            }
        }      
        
            System.out.println("ID NO CADASTRO " + id);
        //Se passou na validação de dados e o responsavel for novo
        if (camposValidados && novoResp) {
            //Se já existe a id deste responsavel, algo está muito errado!
            if (ctl.existeObjeto(id, "IDRESP")) {
                JOptionPane.showMessageDialog(null, "Já existe responsável com esse ID");
                this.dispose();
            } else {
                //Se já existir o código, avisa o usuario
                if (ctl.existeObjeto(codigo, "CODRESP")){
                    JOptionPane.showMessageDialog(null, "Já existe responsável com esse CÓDIGO");
                    txtCodigo.requestFocus();
                } else {
                    //Se não existir, faz o cadastro
                    ctl.cadastraResponsavel(id, nome, email, telefone, codigo);
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                    this.dispose();
                }
            }
          //Se passou na validação de dados e não é um responsavel novo  
        } else if(camposValidados && !novoResp) {
            //Se o usuario digitou um novo código
            if (codigo != codResp) {
                //Pergunta se foi de propósito, se não for, volta pro formulario
                if (JOptionPane.showConfirmDialog(null, "Você alterou o CÓDIGO do responsavel.\nTem certeza que deseja continuar?", "ATENÇÃO", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    txtCodigo.requestFocus();
                    continua = false;
                //Se foi, verifica se o código já existe ou não
                } else  if (ctl.existeObjeto(codigo, "CODRESP")){
                    JOptionPane.showMessageDialog(null, "Já existe responsavel com esse CÓDIGO");
                    txtCodigo.requestFocus();
                    continua = false;
                }
            }  
            //Se continua ainda é true, quer dizer que ele passou pelas outras validações
            if (continua) {
                ctl.modificaResponsavel(id, nome, email, telefone, codigo);
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
    
    private void preencheFormulario(){
        //Desabilita o botão de excluir
        limpaFormulario();
        btnExcluir.setEnabled(false);
        btnExecutar.setText("Cadastrar Responsável");
    }
    
    private void preencheFormulario(int idResp){
        //Armazena o objeto pego da lista do controlador em um objeto local para facilitar
        responsavel = ctl.getResponsavelPeloID(idResp);
        //Altera os textos no formulário
        codResp = responsavel.getCodigo();
        txtCodigo.setText(String.valueOf(codResp));
        txtNome.setText(responsavel.getNome());
        txtTelefone.setText(responsavel.getTelefone());
        txtEmail.setText(responsavel.getEmail());        
        btnExecutar.setText("Salvar Modificações");
        lblTitulo.setText("Editar Responsavel " + String.valueOf(responsavel.getCodigo()));
    }
    
    private void limpaFormulario(){
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtCodigo.setText("");
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblTelefone = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        btnExecutar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Cadastrar Responsável");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCodigo.setText("123456");
        txtCodigo.setToolTipText("CÓDIGO");

        txtNome.setText("Nome completo da pessoa");
        txtNome.setToolTipText("NOME COMPLETO");

        txtTelefone.setText("51-90909090");
        txtTelefone.setToolTipText("TELEFONE");

        txtEmail.setText("email@email.com.br");
        txtEmail.setToolTipText("E-MAIL");

        lblNome.setText("Nome Completo:");

        lblCodigo.setText("Código:");

        lblTelefone.setText("Telefone:");

        lblEmail.setText("E-Mail");

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnExecutar.setText("Cadastrar Responsável");
        btnExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecutarActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar Campos");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir Responsável");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnExecutar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVoltar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addComponent(lblCodigo)
                            .addComponent(lblTelefone)
                            .addComponent(lblEmail))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtEmail)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtCodigo))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExecutar)
                    .addComponent(btnVoltar)
                    .addComponent(btnExcluir)
                    .addComponent(btnLimpar))
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
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        executaResponsavel();
    }//GEN-LAST:event_btnExecutarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaResponsavel dialog = new TelaResponsavel(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
