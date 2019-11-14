/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import controle.Controlador;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import negocio.Responsavel;

/**
 *
 * @author Vinicius
 */
public class TelaBuscaResponsavel extends javax.swing.JDialog {
    private boolean selecionado = false;
    //Cria modelo de tabela, será usado na manipulação da mesma
    DefaultTableModel tabela;
    private int idResponsavel;
    
    /**
     * Creates new form TelaBuscaResponsavel
     */
    public TelaBuscaResponsavel(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        consultarResponsaveis();
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public boolean isSelecionado() {
        return selecionado;
    }
    
    /**
     * Ao selecionar um responsavel, atualiza idSelecionado, o verificador e
     * fecha a janela
     */
    private void selecionaResponsavel(){
        selecionado = true;
        this.dispose();
    }
    private void consultarResponsaveis(){
        ArrayList<Responsavel> listaDeResponsaveis = Controlador.getListaDeResponsaveis();
        if (listaDeResponsaveis.size() > 0) {
            //Recria a tabela caso ela tenha sido modificada            
            tabelaResponsavel.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                     "Nome", "Código", "Telefone", "E-Mail", "idResponsavel"
                })
                {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }}
            );
            //define a tabela com o mesmo modelo da tabelaResponsavel
            tabela = (DefaultTableModel) tabelaResponsavel.getModel();

            //Cria um objeto dadosDaLinhaNaColuna, onde cada posição é será coluna
            Object dadosDaLinhaNaColuna[] = new Object[5];
            //percorre o lista e popula as colunas de acordo com a posicao
            for (int i = 0; i < listaDeResponsaveis.size(); i++) {               
                
                //Busca na lista de equipamentos o idEquipamento do emprestimo atual
                dadosDaLinhaNaColuna[0] = listaDeResponsaveis.get(i).getNome();
                dadosDaLinhaNaColuna[1] = listaDeResponsaveis.get(i).getCodigo();
                dadosDaLinhaNaColuna[2] = listaDeResponsaveis.get(i).getTelefone();
                dadosDaLinhaNaColuna[3] = listaDeResponsaveis.get(i).getEmail();
                dadosDaLinhaNaColuna[4] = listaDeResponsaveis.get(i).getIdResponsavel();
                
                //Adiciona os dados à linha na tabela
                tabela.addRow(dadosDaLinhaNaColuna);                

            }
            //Define o autoresize pra 3, não lembro qual o motivo.
            tabelaResponsavel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            //Oculta última coluna que é apenas para passar referência
            alteraTamanhoColuna(0, -1);
            alteraTamanhoColuna(1, 100);
            alteraTamanhoColuna(2, 150);
            alteraTamanhoColuna(3, 200);
            alteraTamanhoColuna(4, 0);
            alinhaColunas(new int[]{1}, "C");
            jScrollPane1.getVerticalScrollBar().setValue(0);
        } else { //Se estiver vazia, avisa o usuário
            //exibirMensagem();
            JOptionPane.showMessageDialog(this, "Nenhum responsável cadastrado.");
        }        
    }
    
    private void alteraTamanhoColuna(int coluna, int largura){
        tabelaResponsavel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        if (largura > -1) {
            tabelaResponsavel.getColumnModel().getColumn(coluna).setMinWidth(largura);
            tabelaResponsavel.getColumnModel().getColumn(coluna).setPreferredWidth(largura);
            tabelaResponsavel.getColumnModel().getColumn(coluna).setMaxWidth(largura);
        }        
    }
    private void alinhaColunas(int[] coluna, String alinha){
        DefaultTableCellRenderer alinhamento = new DefaultTableCellRenderer();
        for (int i = 0; i < coluna.length; i++) {
            switch (alinha){
                case "C":
                    alinhamento.setHorizontalAlignment( JLabel.CENTER );
                    break;
                case "E":
                    alinhamento.setHorizontalAlignment( JLabel.LEFT );
                    break;
                case "D":
                    alinhamento.setHorizontalAlignment( JLabel.RIGHT );
                    break;
            }
            tabelaResponsavel.getColumnModel().getColumn(coluna[i]).setCellRenderer(alinhamento);
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaResponsavel = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Procura de Responsável");

        lblTitulo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Pesquisa de Responsável");

        tabelaResponsavel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Nome", "Código", "Telefone", "E-Mail", "idResponsavel"
            }
        ));
        tabelaResponsavel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaResponsavelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaResponsavel);
        if (tabelaResponsavel.getColumnModel().getColumnCount() > 0) {
            tabelaResponsavel.getColumnModel().getColumn(4).setMinWidth(0);
            tabelaResponsavel.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabelaResponsavel.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblBuscar.setText("Digite o nome do responsável");

        txtBuscar.setText("Fulano");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblBuscar)
                    .addContainerGap(527, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnCancelar))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(lblBuscar)
                    .addContainerGap(46, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tabelaResponsavelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaResponsavelMouseClicked
        //Armazena o id presente na coluna 4 da linha clicada em idResponsavel
        idResponsavel = (int) tabelaResponsavel.getValueAt(tabelaResponsavel.getSelectedRow(), 4);
        if (evt.getClickCount() == 2) {
            System.out.println("2 cliques no ID:" + idResponsavel);
            selecionaResponsavel();
        } else {
            //selecionaTermo(idTermoSelecionado);
            System.out.println("1 clique no ID " + idResponsavel);
        }
    }//GEN-LAST:event_tabelaResponsavelMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaBuscaResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaBuscaResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaBuscaResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaBuscaResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaBuscaResponsavel dialog = new TelaBuscaResponsavel(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tabelaResponsavel;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
