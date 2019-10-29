/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import controle.Controlador;
import controle.DataHora;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import negocio.Emprestimo;
import negocio.Equipamento;
import java.sql.Timestamp;
import negocio.Responsavel;


/**
 *
 * @author vinicius
 */
public class TelaPrincipal extends javax.swing.JFrame {
    //Carrega o controlador
    Controlador controlador = new Controlador();
    //Cria lista de emprestimos
    ArrayList<Emprestimo> listaDeEmprestimos =  Controlador.getListaDeEmprestimos();
    
    /** Tipos de listagem para abrir a janela correta ao clicar duas vezes na tabela
     * 1 - Emprestimos
     * 2 - Responsáveis
     * 3 - Equipamentos
     */
    int tipoListagem;
    
    //Cria modelo de tabela, será usado na manipulação da mesma
    DefaultTableModel tabela; 

    //contador temporario
    int conta = 0;    
    

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        cadastrosTemporarios();
        consultarTodosEmprestimos();
    }
    
    private void consultarTodosEmprestimos(){
        if (listaDeEmprestimos.size() > 0) {
            //Muda título da tabela
            lblListando.setText("Listando todos os empréstimos");
            //Atualiza o tipo da listagem
            tipoListagem = 1;
            
            //limparLista();
            //Recria a tabela caso ela tenha sido modificada            
            tabelaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                     "Tipo", "Código / Patrimônio", "Responsável", "Data", "Hora", "Data", "Hora", "idEmprestimo"
                })
                {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }}
            );
            //define a tabela com o mesmo modelo da tabelaPrincipal
            tabela = (DefaultTableModel) tabelaPrincipal.getModel();

            //Cria um objeto dadosDaLinhaNaColuna, onde cada posição é será coluna
            Object dadosDaLinhaNaColuna[] = new Object[8];
            //percorre o lista e popula as colunas de acordo com a posicao
            for (int i = 0; i < listaDeEmprestimos.size(); i++) {               
                
                //Busca na lista de equipamentos o idEquipamento do emprestimo atual
                dadosDaLinhaNaColuna[0] = Controlador.getListaDeEquipamentos().get(listaDeEmprestimos.get(i).getIdEquipamento()).getTipo();
                
                dadosDaLinhaNaColuna[1] = Controlador.getListaDeEquipamentos().get(listaDeEmprestimos.get(i).getIdEquipamento()).getCodigo();
                
                //Pega o nome do responsável de acordo com o id do empréstimo atual
                String nomeResponsavel = controlador.getNomeResponsavel(listaDeEmprestimos.get(i).getIdResponsavel());
                dadosDaLinhaNaColuna[2] = nomeResponsavel;
                
                dadosDaLinhaNaColuna[3] = listaDeEmprestimos.get(i).getDataRetirada();
                dadosDaLinhaNaColuna[4] = listaDeEmprestimos.get(i).getHoraRetirada();
                //Se já tiver sido devolvido
                if (listaDeEmprestimos.get(i).getDevolucao() != null) {
                    dadosDaLinhaNaColuna[5] = listaDeEmprestimos.get(i).getDataDevolucao();
                    dadosDaLinhaNaColuna[6] = listaDeEmprestimos.get(i).getHoraDevolucao();
                } else {
                    dadosDaLinhaNaColuna[5] = "";
                    dadosDaLinhaNaColuna[6] = "";                        
                }           
                
                System.out.println("\n\nidEmpretimo "+ listaDeEmprestimos.get(i).getIdEmprestimo());
                dadosDaLinhaNaColuna[7] = listaDeEmprestimos.get(i).getIdEmprestimo();
                
                //Adiciona os dados à linha na tabela
                tabela.addRow(dadosDaLinhaNaColuna);                

            }
            //Define o autoresize pra 3, não lembro qual o motivo.
            tabelaPrincipal.setAutoResizeMode(3);
            //Oculta última coluna que é apenas para passar referência
            tabelaPrincipal.getColumnModel().getColumn(7).setMinWidth(0);
            tabelaPrincipal.getColumnModel().getColumn(7).setMaxWidth(0);
            jScrollPane1.getVerticalScrollBar().setValue(0);
        } else { //Se estiver vazia, avisa o usuário
            //exibirMensagem();
            JOptionPane.showMessageDialog(this, "Nenhum empréstimo");
        }
    }
    
    private void consultarResponsaveis(){
        ArrayList<Responsavel> listaDeResponsaveis = controlador.getListaDeResponsaveis();
        if (listaDeResponsaveis.size() > 0) {
            //Muda título da tabela
            lblListando.setText("Listando responsáveis");
            //Atualiza o tipo da listagem
            tipoListagem = 2;

            //limparLista();
            //Recria a tabela caso ela tenha sido modificada            
            tabelaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
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
            //define a tabela com o mesmo modelo da tabelaPrincipal
            tabela = (DefaultTableModel) tabelaPrincipal.getModel();

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
            tabelaPrincipal.setAutoResizeMode(3);
            //Oculta última coluna que é apenas para passar referência
            tabelaPrincipal.getColumnModel().getColumn(4).setMinWidth(0);
            tabelaPrincipal.getColumnModel().getColumn(4).setMaxWidth(0);
            jScrollPane1.getVerticalScrollBar().setValue(0);
        } else { //Se estiver vazia, avisa o usuário
            //exibirMensagem();
            JOptionPane.showMessageDialog(this, "Nenhum responsável cadastrado.");
        }        
    }
    
    private void consultarEquipamentos(){
        ArrayList<Equipamento> listaDeEquipamentos = Controlador.getListaDeEquipamentos();
        if (listaDeEquipamentos.size() > 0) {
            //Muda título da tabela
            lblListando.setText("Listando equipamentos");
            //Atualiza o tipo da listagem
            tipoListagem = 3;
            //limparLista();
            //Recria a tabela caso ela tenha sido modificada
            tabelaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                     "Código", "Tipo", "Descrição", "Situação", "idEquipamento"
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
            //define a tabela com o mesmo modelo da tabelaPrincipal
            tabela = (DefaultTableModel) tabelaPrincipal.getModel();

            //Cria um objeto dadosDaLinhaNaColuna, onde cada posição é será coluna
            Object dadosDaLinhaNaColuna[] = new Object[5];
            //percorre o lista e popula as colunas de acordo com a posicao
            for (int i = 0; i < listaDeEquipamentos.size(); i++) {               
                
                //Busca na lista de equipamentos o idEquipamento do emprestimo atual
                dadosDaLinhaNaColuna[0] = listaDeEquipamentos.get(i).getCodigo();
                dadosDaLinhaNaColuna[1] = listaDeEquipamentos.get(i).getTipo();
                dadosDaLinhaNaColuna[2] = listaDeEquipamentos.get(i).getDescricao();
                dadosDaLinhaNaColuna[3] = controlador.situacao(listaDeEquipamentos.get(i).getSituacao());
                dadosDaLinhaNaColuna[4] = listaDeEquipamentos.get(i).getIdEquipamento();
                
                //Adiciona os dados à linha na tabela
                tabela.addRow(dadosDaLinhaNaColuna);                

            }
            //Define o autoresize pra 3, não lembro qual o motivo.
            tabelaPrincipal.setAutoResizeMode(3);
            //Oculta última coluna que é apenas para passar referência
            tabelaPrincipal.getColumnModel().getColumn(4).setMinWidth(0);
            tabelaPrincipal.getColumnModel().getColumn(4).setMaxWidth(0);
            jScrollPane1.getVerticalScrollBar().setValue(0);
        } else { //Se estiver vazia, avisa o usuário
            //exibirMensagem();
            JOptionPane.showMessageDialog(this, "Nenhum equipamento cadastrado.");
        }    
    }
    
    private void cadastrosTemporarios(){
        controlador.cadastraResponsavel("Vinicius Vasconcelos", 123456, "5112345678", "email@email.com");
        controlador.cadastraResponsavel("Fulano de Tal", 853456, "5390909090", "email2@email.com.br");
        controlador.cadastraResponsavel("Bertrano de Quem", 753159, "6170708060", "email32@gemail.com.br");
        controlador.cadastraEquipamento(123456, "Projetor", "Projetor EPSON X13", "E");
        controlador.cadastraEquipamento(123457, "Projetor", "Projetor EPSON X12", "D");
        controlador.cadastraEquipamento(123458, "Notebook", "Notebook HP Probook", "D");
        controlador.cadastraEquipamento(123459, "Notebook", "Notebook Dell M179", "D");
        controlador.cadastraEquipamento(123460, "Tablet", "Tablet Motorolla Xoom", "D");
        
        controlador.cadastraEmprestimo(2, DataHora.converteParaTimestamp("2019-10-28 10:00:00"), 0);
    }
    
    void cadastraEmprestimo(int cod){
        //Se existe o equipamento cadastrado
        if (controlador.existeObjeto(cod, "CodEqp")) {
            TelaEmprestimo novaTela = new TelaEmprestimo(cod);
            novaTela.setVisible(true);
            //Cadastra o empréstimo, diz que existe e sai do for
            //controlador.cadastraEmprestimo(0, new Timestamp(System.currentTimeMillis()), Controlador.getListaDeEquipamentos().get(i).getIdEquipamento());
            System.out.println("cadastrando");
        } else {
            JOptionPane.showMessageDialog(this, "Não existe o código digitado"); 
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaPrincipal = new javax.swing.JTable();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnTempEmprestimo = new javax.swing.JButton();
        btnTempResponsavel = new javax.swing.JButton();
        btnTempEquipamento = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        lblListando = new javax.swing.JLabel();
        menuPrincipal = new javax.swing.JMenuBar();
        jmArquivo = new javax.swing.JMenu();
        jmiNovoResponsavel = new javax.swing.JMenuItem();
        jmiNovoEquipamento = new javax.swing.JMenuItem();
        jmiSair = new javax.swing.JMenuItem();
        jmListagens = new javax.swing.JMenu();
        jmiListarEmprestimos = new javax.swing.JMenuItem();
        jmiListarPendentes = new javax.swing.JMenuItem();
        jmiListarEquipamentos = new javax.swing.JMenuItem();
        jmiListarResponsaveis = new javax.swing.JMenuItem();
        jmSobre = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Empréstimos");
        setName("framePrincipal"); // NOI18N
        setResizable(false);

        tabelaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Descrição", "Código / Patrimônio", "Responsável", "Data", "Hora", "Data", "Hora"
            }
        ));
        jScrollPane1.setViewportView(tabelaPrincipal);

        lblCodigo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigo.setText("Código / Patrimônio");

        txtCodigo.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setText("123456");

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnTempResponsavel.setText("Responsaveis");
        btnTempResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTempResponsavelActionPerformed(evt);
            }
        });

        btnTempEquipamento.setText("Equipamentos");
        btnTempEquipamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTempEquipamentoActionPerformed(evt);
            }
        });

        btnListar.setText("Emprestimos");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        lblListando.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblListando.setText("Listando empréstimos");

        jmArquivo.setText("Arquivo");

        jmiNovoResponsavel.setText("Novo Responsável");
        jmiNovoResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNovoResponsavelActionPerformed(evt);
            }
        });
        jmArquivo.add(jmiNovoResponsavel);

        jmiNovoEquipamento.setText("Novo Equipamento");
        jmiNovoEquipamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNovoEquipamentoActionPerformed(evt);
            }
        });
        jmArquivo.add(jmiNovoEquipamento);

        jmiSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jmiSair.setText("Sair");
        jmiSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSairActionPerformed(evt);
            }
        });
        jmArquivo.add(jmiSair);

        menuPrincipal.add(jmArquivo);

        jmListagens.setText("Listagens");

        jmiListarEmprestimos.setText("Listar Todos os Empréstimos");
        jmiListarEmprestimos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiListarEmprestimosActionPerformed(evt);
            }
        });
        jmListagens.add(jmiListarEmprestimos);

        jmiListarPendentes.setText("Listar Empréstimos Pendentes");
        jmListagens.add(jmiListarPendentes);

        jmiListarEquipamentos.setText("Listar Equipamentos");
        jmiListarEquipamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiListarEquipamentosActionPerformed(evt);
            }
        });
        jmListagens.add(jmiListarEquipamentos);

        jmiListarResponsaveis.setText("Listar Responsáveis");
        jmiListarResponsaveis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiListarResponsaveisActionPerformed(evt);
            }
        });
        jmListagens.add(jmiListarResponsaveis);

        menuPrincipal.add(jmListagens);

        jmSobre.setText("Sobre");
        menuPrincipal.add(jmSobre);

        setJMenuBar(menuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblListando, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtCodigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRegistrar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTempEquipamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTempResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTempEmprestimo)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTempEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTempResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTempEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnListar, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblListando, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTempResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTempResponsavelActionPerformed
        consultarResponsaveis();
    }//GEN-LAST:event_btnTempResponsavelActionPerformed

    private void btnTempEquipamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTempEquipamentoActionPerformed
        consultarEquipamentos();
    }//GEN-LAST:event_btnTempEquipamentoActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        int cod = Integer.parseInt(txtCodigo.getText());
        cadastraEmprestimo(cod); 
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        consultarTodosEmprestimos();
    }//GEN-LAST:event_btnListarActionPerformed

    private void jmiSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jmiSairActionPerformed

    private void jmiNovoResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNovoResponsavelActionPerformed
        TelaResponsavel novaTela = new TelaResponsavel();
        novaTela.setVisible(true);
    }//GEN-LAST:event_jmiNovoResponsavelActionPerformed

    private void jmiNovoEquipamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNovoEquipamentoActionPerformed
        TelaEquipamento novaTela = new TelaEquipamento();
        novaTela.setVisible(true);
    }//GEN-LAST:event_jmiNovoEquipamentoActionPerformed

    private void jmiListarEmprestimosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiListarEmprestimosActionPerformed
        consultarTodosEmprestimos();
    }//GEN-LAST:event_jmiListarEmprestimosActionPerformed

    private void jmiListarResponsaveisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiListarResponsaveisActionPerformed
        consultarResponsaveis();
    }//GEN-LAST:event_jmiListarResponsaveisActionPerformed

    private void jmiListarEquipamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiListarEquipamentosActionPerformed
        consultarEquipamentos();
    }//GEN-LAST:event_jmiListarEquipamentosActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnTempEmprestimo;
    private javax.swing.JButton btnTempEquipamento;
    private javax.swing.JButton btnTempResponsavel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu jmArquivo;
    private javax.swing.JMenu jmListagens;
    private javax.swing.JMenu jmSobre;
    private javax.swing.JMenuItem jmiListarEmprestimos;
    private javax.swing.JMenuItem jmiListarEquipamentos;
    private javax.swing.JMenuItem jmiListarPendentes;
    private javax.swing.JMenuItem jmiListarResponsaveis;
    private javax.swing.JMenuItem jmiNovoEquipamento;
    private javax.swing.JMenuItem jmiNovoResponsavel;
    private javax.swing.JMenuItem jmiSair;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblListando;
    private javax.swing.JMenuBar menuPrincipal;
    private javax.swing.JTable tabelaPrincipal;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
