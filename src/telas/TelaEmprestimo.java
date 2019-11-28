/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import controle.Controlador;
import controle.DataHora;
import java.awt.Color;
import javax.swing.JOptionPane;
import negocio.Emprestimo;
import negocio.Equipamento;

/**
 *
 * @author Vinicius
 */
public class TelaEmprestimo extends javax.swing.JDialog {
    Controlador ctl;
    Emprestimo emprestimo;
    Equipamento equipamento;
    TelaBuscaResponsavel telaBuscarResponsavel;
    private boolean estaEmprestado;
    private long dataHoraAgora;
    private int idResponsavel;
    private int idResponsavelOriginal;
    private int idEmprestimo;
    private int idEquipamento;
    boolean clicouAgora;
    String dataDevolucao;

    
    public TelaEmprestimo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();    
    }

    /**
     * Creates new form TelaEmprestimo
     */
    public TelaEmprestimo(java.awt.Frame parent, boolean modal, int codigo, Controlador cont) {
        super(parent, modal);
        initComponents();
        ctl = cont;
        estaEmprestado = ctl.estaEmprestado(codigo);
        System.out.println("emprestado " + estaEmprestado);
        if (estaEmprestado) {
            preencheFormSeEmprestado(codigo);
        } else {
            preencheFormSeDisponivel(codigo);
        }
    }
    
    /**
     * Se o código for de um equipamento já emprestado, atualiza os campos
     * com as informações correspondentes
     * 
     * @param codigo 
     */
    private void preencheFormSeEmprestado(int codigo){
        emprestimo = ctl.getEmprestimoPeloCodigoDoEquipamento(codigo);
        txtRetirada.setText(emprestimo.getDataRetirada());
        idResponsavelOriginal = emprestimo.getIdResponsavel();
        preencheDadosResponsavel(idResponsavelOriginal);
        preencheDadosEquipamento(codigo);
        txtRetirada.setEnabled(false);
        btnAgoraRet.setEnabled(false);
        btnAcao.setText("Devolver");
        System.out.println("DEVOLUÇÂO " + txtDevolucao.getText());
    }
    
    private void preencheFormSeDisponivel(int codigo){
        dataHoraAgora = System.currentTimeMillis();
        txtRetirada.setText(DataHora.dataFormatada(dataHoraAgora));
        txtDevolucao.setEnabled(false);
        btnAgoraDev.setEnabled(false);
        preencheDadosEquipamento(codigo);
        btnAcao.setText("Emprestar");
    }
    
    private void preencheDadosEquipamento(int codigo){
        equipamento = ctl.getEquipamentoPeloCodigo(codigo);
        idEquipamento = equipamento.getIdEquipamento();
        txtCodigo.setText(String.valueOf(equipamento.getCodigo()));
        txtTipo.setText(equipamento.getTipo());
        txtDescricao.setText(equipamento.getDescricao());
        switch(equipamento.getSituacao()){
            case "E":
                lblStatus.setText("Emprestado");
                lblStatus.setForeground(new Color(190, 20, 0));
                break;
            case "D":
                lblStatus.setText("Disponível");
                lblStatus.setForeground(new Color(10, 150, 50));
                break;
            case "I":
                lblStatus.setText("Indisponível");
                lblStatus.setForeground(new Color(180, 190, 0));
                break;
        }
    }
    
    /**
     * Preenche os dados do responsável de acordo com o ID pego da busca
     * @param id 
     */
    private void preencheDadosResponsavel(int id){
        txtNome.setText(ctl.getNomeResponsavel(id));
        txtTelefone.setText(ctl.getTelefoneResponsavel(id));
    }
    
    private void abreBusca(){
        this.telaBuscarResponsavel = new TelaBuscaResponsavel(this, true);
        //Abre a janela de busca e fica aguardando
        telaBuscarResponsavel.setVisible(true);
        
        //Se o usuário tiver escolhido um responsável, atualiza
        if (this.telaBuscarResponsavel.isSelecionado()) {
            idResponsavel = this.telaBuscarResponsavel.getIdResponsavel();
            
            if (JOptionPane.showConfirmDialog(null, "Você alterou o responsável.\nTem certeza que deseja continuar?", "ATENÇÃO", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                preencheDadosResponsavel(idResponsavel);
            } else {
                preencheDadosResponsavel(idResponsavelOriginal);
            }            
        }           
    }
    
    private void devolverEquipamento(){
        boolean validado = true;
        if (txtDevolucao.getText().isBlank() && validado) {
            JOptionPane.showMessageDialog(null, "A data não pode estar em branco");
            validado = false;
        }
        if (DataHora.converteDataParaLong(txtDevolucao.getText()) < 100000 && txtDevolucao.getText().length() != 10 && validado) {
            JOptionPane.showMessageDialog(null, "A data precisa ter o formato dd/mm/aaaa.");
            validado = false;
        }
        
        if (validado) {
            java.sql.Timestamp devolucao = DataHora.converteParaTimestamp(DataHora.converteDataParaLong(dataDevolucao));
            ctl.devolveEmprestimo(idEmprestimo, devolucao, idResponsavel);
            JOptionPane.showMessageDialog(null, "Equipamento devolvido com sucesso!");
        }
        /*emprestimo.setDevolucao(DataHora.converteParaTimestamp(dataHoraAgora));
        emprestimo.setSituacaoEmprestimo("C");
        equipamento.setSituacao("D");
        this.dispose();*/
    }
    
    private void retirarEquipamento(){
        ctl.cadastraEmprestimo(DataHora.converteParaTimestamp(dataHoraAgora), idResponsavel, idEquipamento);
        equipamento.setSituacao("E");
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrupoSituacao = new javax.swing.ButtonGroup();
        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblDataRetirada = new javax.swing.JLabel();
        lblDataDevolucao = new javax.swing.JLabel();
        txtDevolucao = new javax.swing.JFormattedTextField();
        txtRetirada = new javax.swing.JFormattedTextField();
        lblResponsavel = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblInformacoes = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        lblSituacao = new javax.swing.JLabel();
        btnBuscarEquipamento = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAcao = new javax.swing.JButton();
        btnBuscarResponsavel = new javax.swing.JButton();
        btnAgoraDev = new javax.swing.JButton();
        btnAgoraRet = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empréstimos");

        lblTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Empréstimo de Equipamento");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDataRetirada.setText("Data de Retirada:");

        lblDataDevolucao.setText("Data de Devolução:");

        txtDevolucao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDevolucaoFocusLost(evt);
            }
        });

        txtRetirada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        lblResponsavel.setText("Responsável:");

        txtNome.setEditable(false);

        lblTelefone.setText("Telefone:");

        txtTelefone.setEditable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblInformacoes.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblInformacoes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes.setText("Informações sobre o equipamento");

        lblCodigo.setText("Código:");

        txtCodigo.setText("00000");

        lblTipo.setText("Tipo:");

        txtTipo.setEditable(false);
        txtTipo.setText("XXXXXX");

        lblDescricao.setText("Descrição:");

        txtDescricao.setEditable(false);
        txtDescricao.setText("XXXXX");

        lblSituacao.setText("Situação:");

        btnBuscarEquipamento.setText("Buscar");

        lblStatus.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblStatus.setText("Disponível");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(lblInformacoes))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(lblTipo)
                            .addComponent(lblDescricao)
                            .addComponent(lblSituacao))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigo)
                                    .addComponent(txtTipo, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscarEquipamento)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInformacoes)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarEquipamento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSituacao)
                    .addComponent(lblStatus))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAcao.setText("Devolver");
        btnAcao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcaoActionPerformed(evt);
            }
        });

        btnBuscarResponsavel.setText("Buscar");
        btnBuscarResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarResponsavelActionPerformed(evt);
            }
        });

        btnAgoraDev.setText("Agora");
        btnAgoraDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgoraDevActionPerformed(evt);
            }
        });

        btnAgoraRet.setText("Agora");
        btnAgoraRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgoraRetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDataDevolucao)
                            .addComponent(lblDataRetirada)
                            .addComponent(lblResponsavel)
                            .addComponent(lblTelefone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDevolucao)
                            .addComponent(txtRetirada)
                            .addComponent(txtNome)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBuscarResponsavel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgoraDev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgoraRet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAcao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataRetirada)
                            .addComponent(txtRetirada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgoraRet))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataDevolucao)
                            .addComponent(txtDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgoraDev))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblResponsavel)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarResponsavel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefone)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAcao))
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
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAcaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcaoActionPerformed
        if (estaEmprestado) {
            devolverEquipamento();
        } else {
            retirarEquipamento();
        }
    }//GEN-LAST:event_btnAcaoActionPerformed

    private void btnBuscarResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarResponsavelActionPerformed
        //TelaBuscaResponsavel novaTela = new FrameBuscaResponsavel();
        abreBusca();
    }//GEN-LAST:event_btnBuscarResponsavelActionPerformed

    private void btnAgoraDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgoraDevActionPerformed
        dataHoraAgora = System.currentTimeMillis();
        txtDevolucao.setText(DataHora.dataFormatada(dataHoraAgora));
    }//GEN-LAST:event_btnAgoraDevActionPerformed

    private void btnAgoraRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgoraRetActionPerformed
        dataHoraAgora = System.currentTimeMillis();
        clicouAgora = true;
        txtRetirada.setText(DataHora.dataFormatada(dataHoraAgora));
    }//GEN-LAST:event_btnAgoraRetActionPerformed

    private void txtDevolucaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDevolucaoFocusLost
        if (clicouAgora) {
            
        }
    }//GEN-LAST:event_txtDevolucaoFocusLost

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaEmprestimo dialog = new TelaEmprestimo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAcao;
    private javax.swing.JButton btnAgoraDev;
    private javax.swing.JButton btnAgoraRet;
    private javax.swing.JButton btnBuscarEquipamento;
    private javax.swing.JButton btnBuscarResponsavel;
    private javax.swing.JButton btnCancelar;
    private javax.swing.ButtonGroup btnGrupoSituacao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDataDevolucao;
    private javax.swing.JLabel lblDataRetirada;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblInformacoes;
    private javax.swing.JLabel lblResponsavel;
    private javax.swing.JLabel lblSituacao;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JFormattedTextField txtDevolucao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JFormattedTextField txtRetirada;
    private javax.swing.JTextField txtTelefone;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
