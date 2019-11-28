/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import controle.Controlador;
import controle.DataHora;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import negocio.Emprestimo;
import negocio.Equipamento;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import negocio.Responsavel;


/**
 *
 * @author vinicius
 */
public class TelaPrincipal extends javax.swing.JFrame {
    Controlador controlador;
    //Cria lista de emprestimos
    ArrayList<Emprestimo> listaDeEmprestimos =  Controlador.getListaDeEmprestimos();
    
    TelaEmprestimo telaEmprestimo;
    TelaEquipamento telaEquipamento;
    TelaResponsavel telaResponsavel;
    
    /** Tipos de listagem para abrir a janela correta ao clicar duas vezes na tabela
     * 1 - Emprestimos
     * 2 - Responsáveis
     * 3 - Equipamentos
     */
    int tipoListagem;
    int idSelecionado;
    
    //Cria modelo de tabela, será usado na manipulação da mesma
    DefaultTableModel tabela; 

    //contador temporario
    int conta = 0;    
    

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal(){
        try {
            controlador = new Controlador();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        controlador.selectListaResponsaveis();
        controlador.selectListaEquipamentos();
        controlador.selectListaEmprestimos();
        cadastrosTemporarios();
        consultarTodosEmprestimos();
    }
    
    /**
     * Consulta todos os empréstimos
     */
    private void consultarTodosEmprestimos(){
        controlador.selectListaEmprestimos();
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
                      "idEmprestimo", "Tipo", "Código / Patrimônio", "Responsável", "Data", "Hora", "Data", "Hora"
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
                
                dadosDaLinhaNaColuna[1] = controlador.getEquipamentoPeloID(listaDeEmprestimos.get(i).getIdEquipamento()).getTipo();             
                dadosDaLinhaNaColuna[2] = controlador.getEquipamentoPeloID(listaDeEmprestimos.get(i).getIdEquipamento()).getCodigo();
                
                //Pega o nome do responsável de acordo com o id do empréstimo atual
                String nomeResponsavel = controlador.getNomeResponsavel(listaDeEmprestimos.get(i).getIdResponsavel());
                dadosDaLinhaNaColuna[3] = nomeResponsavel;
                
                dadosDaLinhaNaColuna[4] = listaDeEmprestimos.get(i).getDataRetirada();
                dadosDaLinhaNaColuna[5] = listaDeEmprestimos.get(i).getHoraRetirada();
                //Se já tiver sido devolvido
                if (listaDeEmprestimos.get(i).getDevolucao() != null) {
                    dadosDaLinhaNaColuna[6] = listaDeEmprestimos.get(i).getDataDevolucao();
                    dadosDaLinhaNaColuna[7] = listaDeEmprestimos.get(i).getHoraDevolucao();
                } else {
                    dadosDaLinhaNaColuna[6] = "";
                    dadosDaLinhaNaColuna[7] = "";                        
                } 
                dadosDaLinhaNaColuna[0] = listaDeEmprestimos.get(i).getIdEmprestimo();
                
                //Adiciona os dados à linha na tabela
                tabela.addRow(dadosDaLinhaNaColuna);                

            }
            
            alteraTamanhoColuna(1, 120);
            alteraTamanhoColuna(2, 150);
            alteraTamanhoColuna(3, -1);
            alteraTamanhoColuna(4, 100);
            alteraTamanhoColuna(5, 80);
            alteraTamanhoColuna(6, 100);
            alteraTamanhoColuna(7, 80);
            alteraTamanhoColuna(0, 0);
            alinhaColunas(new int[]{1,2,4,5,6,7}, "C");
            jScrollPane1.getVerticalScrollBar().setValue(0);
            
            lblListando.setText("Lista de Empréstimos");

        } else { //Se estiver vazia, avisa o usuário
            JOptionPane.showMessageDialog(this, "Nenhum empréstimo");
        }
    }
    
    private void consultarResponsaveis(){
        controlador.selectListaResponsaveis();
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
                     "idResponsavel", "Nome", "Código", "Telefone", "E-Mail"
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
                dadosDaLinhaNaColuna[1] = listaDeResponsaveis.get(i).getNome();
                dadosDaLinhaNaColuna[2] = listaDeResponsaveis.get(i).getCodigo();
                dadosDaLinhaNaColuna[3] = listaDeResponsaveis.get(i).getTelefone();
                dadosDaLinhaNaColuna[4] = listaDeResponsaveis.get(i).getEmail();
                dadosDaLinhaNaColuna[0] = listaDeResponsaveis.get(i).getIdResponsavel();
                
                //Adiciona os dados à linha na tabela
                tabela.addRow(dadosDaLinhaNaColuna);                

            }
            alteraTamanhoColuna(1, -1);
            alteraTamanhoColuna(2, 120);
            alteraTamanhoColuna(3, 100);
            alteraTamanhoColuna(4, 200);
            alteraTamanhoColuna(0, 0);
            alinhaColunas(new int[]{2}, "C");
            jScrollPane1.getVerticalScrollBar().setValue(0);
            lblListando.setText("Lista de Responsáveis");
        } else { //Se estiver vazia, avisa o usuário
            //exibirMensagem();
            JOptionPane.showMessageDialog(this, "Nenhum responsável cadastrado.");
        }        
    }
    
    private void consultarEquipamentos(){
        controlador.selectListaEquipamentos();
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
                     "idEquipamento", "Código", "Tipo", "Descrição", "Situação"
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
                dadosDaLinhaNaColuna[1] = listaDeEquipamentos.get(i).getCodigo();
                dadosDaLinhaNaColuna[2] = listaDeEquipamentos.get(i).getTipo();
                dadosDaLinhaNaColuna[3] = listaDeEquipamentos.get(i).getDescricao();
                dadosDaLinhaNaColuna[4] = controlador.situacao(listaDeEquipamentos.get(i).getSituacao());
                dadosDaLinhaNaColuna[0] = listaDeEquipamentos.get(i).getIdEquipamento();
                
                //Adiciona os dados à linha na tabela
                tabela.addRow(dadosDaLinhaNaColuna);                

            }
            alteraTamanhoColuna(1, 100);
            alteraTamanhoColuna(2, 100);
            alteraTamanhoColuna(3, -1);
            alteraTamanhoColuna(4, 120);
            alteraTamanhoColuna(0, 0);
            alinhaColunas(new int[]{1,2,4}, "C");
            lblListando.setText("Lista de Equipamentos");            
        } else { //Se estiver vazia, avisa o usuário
            //exibirMensagem();
            JOptionPane.showMessageDialog(this, "Nenhum equipamento cadastrado.");
        }    
    }
    
    private void alteraTamanhoColuna(int coluna, int largura){
        tabelaPrincipal.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        if (largura > -1) {
            tabelaPrincipal.getColumnModel().getColumn(coluna).setMinWidth(largura);
            tabelaPrincipal.getColumnModel().getColumn(coluna).setPreferredWidth(largura);
            tabelaPrincipal.getColumnModel().getColumn(coluna).setMaxWidth(largura);
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
            tabelaPrincipal.getColumnModel().getColumn(coluna[i]).setCellRenderer(alinhamento);
        }
    }
    
    private void cadastrosTemporarios(){
        /*controlador.adicionaResponsavelNaLista("Vinicius Vasconcelos", 123456, "5112345678", "email@email.com");
        controlador.adicionaResponsavelNaLista("Fulano de Tal", 853456, "5390909090", "email2@email.com.br");
        controlador.adicionaResponsavelNaLista("Bertrano de Quem", 753159, "6170708060", "email32@gemail.com.br");
        controlador.cadastraEquipamento(123456, "Projetor", "Projetor EPSON X13", "E");
        controlador.cadastraEquipamento(123457, "Projetor", "Projetor EPSON X12", "D");
        controlador.cadastraEquipamento(123458, "Notebook", "Notebook HP Probook", "D");
        controlador.cadastraEquipamento(123459, "Notebook", "Notebook Dell M179", "D");
        controlador.cadastraEquipamento(123460, "Tablet", "Tablet Motorolla Xoom", "D");*/
        
       // controlador.adicionaEmprestimoNaLista(2, DataHora.converteParaTimestamp(1572392211 ), 0);
    }
    
    void acionaEmprestimo(int cod){
        //Se existe o equipamento cadastrado
        if (controlador.existeObjeto(cod, "CODEQP")) {
            this.telaEmprestimo = new TelaEmprestimo(this, true, cod, controlador);
            this.telaEmprestimo.setVisible(true);
            consultarTodosEmprestimos();
        } else {
            JOptionPane.showMessageDialog(this, "Não existe o código digitado"); 
            consultarTodosEmprestimos();
        } 
    }
    
    void abreTelaEquipamento(int id){
        //Se existe o equipamento cadastrado
        if (controlador.existeObjeto(id, "IDEQP")) {
            this.telaEquipamento = new TelaEquipamento(this, true, id, controlador);
            this.telaEquipamento.setVisible(true);
            consultarEquipamentos();
        } else {
            JOptionPane.showMessageDialog(this, "Não existe o equipamento selecionado");
            consultarEquipamentos(); 
        } 
    }
    void abreTelaEquipamento(){
            this.telaEquipamento = new TelaEquipamento(this, true, controlador);
            this.telaEquipamento.setVisible(true);
            consultarEquipamentos();
    }
    
    void abreTelaResponsavel(int id){
        if (controlador.existeObjeto(id, "IDRESP")) {
            this.telaResponsavel = new TelaResponsavel(this, true, id, controlador);
            this.telaResponsavel.setVisible(true);
            consultarResponsaveis();
        } else {
            JOptionPane.showMessageDialog(this, "Não existe o responsável selecionado");
            consultarResponsaveis(); 
        }
    }
    
    void abreTelaResponsavel(){
            this.telaResponsavel = new TelaResponsavel(this, true, controlador);
            this.telaResponsavel.setVisible(true);
            consultarResponsaveis();
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
        jPanelItens = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        lblListando = new javax.swing.JLabel();
        btnEquipa = new javax.swing.JButton();
        btnRespon = new javax.swing.JButton();
        btnEmprest = new javax.swing.JButton();
        lblCodigo1 = new javax.swing.JLabel();
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
        jmiPendente = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Empréstimos");
        setName("framePrincipal"); // NOI18N

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
        tabelaPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaPrincipalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaPrincipal);

        lblCodigo.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigo.setText("Código / Patrimônio");

        txtCodigo.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setText("123456");

        btnRegistrar.setText("Empréstimo");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        lblListando.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblListando.setText("Listando empréstimos");

        btnEquipa.setText("Equipamentos");
        btnEquipa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEquipaActionPerformed(evt);
            }
        });

        btnRespon.setText("Responsáveis");
        btnRespon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResponActionPerformed(evt);
            }
        });

        btnEmprest.setText("Empréstimos");
        btnEmprest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmprestActionPerformed(evt);
            }
        });

        lblCodigo1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblCodigo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigo1.setText("Listar");

        javax.swing.GroupLayout jPanelItensLayout = new javax.swing.GroupLayout(jPanelItens);
        jPanelItens.setLayout(jPanelItensLayout);
        jPanelItensLayout.setHorizontalGroup(
            jPanelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItensLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelItensLayout.createSequentialGroup()
                        .addComponent(lblListando, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelItensLayout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                        .addGroup(jPanelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCodigo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelItensLayout.createSequentialGroup()
                                .addComponent(btnEmprest, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRespon, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEquipa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(jPanelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelItensLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(682, Short.MAX_VALUE)))
        );
        jPanelItensLayout.setVerticalGroup(
            jPanelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelItensLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCodigo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEquipa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRespon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmprest, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblListando, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanelItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelItensLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(85, Short.MAX_VALUE)))
        );

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

        jmiPendente.setText("Pendências");
        jmiPendente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPendenteActionPerformed(evt);
            }
        });
        jmSobre.add(jmiPendente);

        menuPrincipal.add(jmSobre);

        setJMenuBar(menuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelItens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        acionaEmprestimo(Integer.parseInt(txtCodigo.getText())); 
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void jmiSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jmiSairActionPerformed

    private void jmiNovoResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNovoResponsavelActionPerformed
        abreTelaResponsavel();
    }//GEN-LAST:event_jmiNovoResponsavelActionPerformed

    private void jmiNovoEquipamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNovoEquipamentoActionPerformed
        abreTelaEquipamento();
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

    private void btnEquipaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEquipaActionPerformed
        consultarEquipamentos();
    }//GEN-LAST:event_btnEquipaActionPerformed

    private void btnResponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResponActionPerformed
        consultarResponsaveis();
    }//GEN-LAST:event_btnResponActionPerformed

    private void btnEmprestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmprestActionPerformed
        consultarTodosEmprestimos();
    }//GEN-LAST:event_btnEmprestActionPerformed

    private void jmiPendenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPendenteActionPerformed
        JOptionPane.showMessageDialog(this, 
                                        "<html>"
                                            + "<p><b>Objetivos:</b></p>"
                                            + "<p>Interface limpa e funcional</p><br>"
                                            + "<p><b>Usabilidade:</b></p>"
                                            + "<p>O básico será o usuário escanear o equipamento com um</p>"
                                            + "<p>leitor de código de barras e automaticamente abrir a </p>"
                                            + "<p>tela de empréstimos correspondente, sem precisar digitar</br>"
                                            + "<p> ou mesmo pressionar enter.</p><br>"
                                            + "<p><b>Pendências:</b></p>"
                                            + "<ul>"
                                                + "<li>Criação BD</li>"
                                                + "<li>Integração com BD</li>"
                                                + "<li>Clique duplo item na tabela</li>"
                                                + "<li>Implementação cadastro de equipamento</li>"
                                                + "<li>Implementação cadastro de responsáveis</li>"
                                                + "<li>Listagem apenas emprestados</li>"
                                                + "<li>Ordenação por data/situção/nome/etc</li>"
                                                + "<li>Validações</li>"
                                                + "<li>Busca equipamento no empréstimo</li>"
                                            + "</ul>"
                                       +"</html>"
                                    );
    }//GEN-LAST:event_jmiPendenteActionPerformed

    private void tabelaPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaPrincipalMouseClicked
        idSelecionado = (int) tabelaPrincipal.getValueAt(tabelaPrincipal.getSelectedRow(), 0);
        if (evt.getClickCount() == 2) {
            System.out.println("idSelecionado na tabela " + idSelecionado);
            switch (tipoListagem){
                case 1: //emprestimo
                    break;
                case 2://responsavel
                    abreTelaResponsavel(idSelecionado);
                    break;
                case 3://equipamento
                    abreTelaEquipamento(idSelecionado);
                    break;
            }
            //System.out.println("2 cliques " + idTermoSelecionado);            
        } else if (evt.getClickCount() == 1) {
            System.out.println("idSelecionado na tabela " + idSelecionado);
            switch (tipoListagem){
                case 1: //emprestimo
                    txtCodigo.setText(String.valueOf(tabelaPrincipal.getValueAt(tabelaPrincipal.getSelectedRow(), 2)));
                    break;
                case 2://responsavel                    
                    break;
                case 3://equipamento
                    txtCodigo.setText(String.valueOf(tabelaPrincipal.getValueAt(tabelaPrincipal.getSelectedRow(), 1)));
                    break;
            }
            //System.out.println("2 cliques " + idTermoSelecionado);            
        }
    }//GEN-LAST:event_tabelaPrincipalMouseClicked

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
                //new TelaPrincipal().setVisible(true);
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmprest;
    private javax.swing.JButton btnEquipa;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnRespon;
    private javax.swing.JPanel jPanelItens;
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
    private javax.swing.JMenuItem jmiPendente;
    private javax.swing.JMenuItem jmiSair;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigo1;
    private javax.swing.JLabel lblListando;
    private javax.swing.JMenuBar menuPrincipal;
    private javax.swing.JTable tabelaPrincipal;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
