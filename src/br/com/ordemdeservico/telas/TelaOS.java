package br.com.ordemdeservico.telas;

import java.sql.*;
import br.com.ordemdeservico.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // A linha abaixo cria uma variavel para armazenar um texto de acordo com o radio button selecionado.
    private String tipo;

    public TelaOS() {

        initComponents();
        conexao = ModuloConexao.Conector();

    }

    private void pesquisar_cliente() {

         btnOsCriar.setEnabled(false);
        
        String sql = "select idClientes as id, nomeCliente as Nome, foneCliente as Fone from tbclientes where nomeCliente like ?";

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOsPesquisa.getText() + "%");
            rs = pst.executeQuery();
            tblOsClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void setar_campos() {

        int setar = tblOsClientes.getSelectedRow();

        if (txtOsId.getText().isEmpty() && (txtOsPesquisa.getText().isEmpty())) {

            JOptionPane.showMessageDialog(null, "Preencha o campo de Pesquisar", "Busca de Cadastro do Cliente", DISPOSE_ON_CLOSE); 

        } else {
            txtOsId.setText(tblOsClientes.getModel().getValueAt(setar, 0).toString());
            txtOsPesquisa.setText(tblOsClientes.getModel().getValueAt(setar, 1).toString());

             btnOsCriar.setEnabled(true);
        }

    }
    
    /*private void clicar_Tabela(){
        
     int setar = tblOsClientes.getSelectedRow();    
    
    txtOsPesquisa.setText(tblOsClientes.getValueAt(setar, 1).toString());
    
    }*/

    // Metodo de cadastro de OS
    private void emitir_OS() {

        String sql = "insert into tbOS (tipo, situacao, equipamento, defeito, servico, tecnico,valor, idCli) values (?,?,?,?,?,?,?,?)";

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEquip.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            pst.setString(7, txtOsvalor.getText().replace(",", "."));
            pst.setString(8, txtOsId.getText());

            // Validação dos campos Obrigatorio
            if (txtOsId.getText().isEmpty() ||(txtOsPesquisa.getText().isEmpty()) || (txtOsEquip.getText().isEmpty()) || (txtOsDefeito.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatorios");

            } else {

                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso.");

                    txtOsEquip.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsvalor.setText(null);
                    txtOsId.setText(null);

                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Metodo de ´pesquisa de OS
    private void pesquisar_OS() {

        String num_OS = JOptionPane.showInputDialog("Número da OS");

        String sql = " select * from tbOS where os = " + num_OS;

        try {

            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                                                
                txtNumOs.setText(rs.getString(1));
                txtDataOs.setText(rs.getString(2));

                // Setando os Radio Button
                String rbtTipo = rs.getString(3);

                if (rbtTipo.equals("Ordem de Serviço")) {
                    rbtOrdemServico.setSelected(true);
                    tipo = "Ordem de Serviço";
                } else {
                    rbtOrcamento.setSelected(true);
                    tipo = "Orçamento";
                }

                cboOsSit.setSelectedItem(rs.getString(4));
                txtOsEquip.setText(rs.getString(5));
                txtOsDefeito.setText(rs.getString(6));
                txtOsServico.setText(rs.getString(7));
                txtOsTecnico.setText(rs.getString(8));
                txtOsvalor.setText(rs.getString(9));
                txtOsId.setText(rs.getString(10));
                            
                // Desabilitando o botão criar
                btnOsCriar.setEnabled(false);
                txtOsPesquisa.setEditable(false);
                tblOsClientes.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "Ordem de Serviço não cadastrada ou número da ordem não digitado");
            }
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor, digite valores numéricos");
        }
 
        
    }

    //Alterando OS
    private void alterar_OS() {

        String sql = "update tbOS set tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where os=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEquip.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            pst.setString(7, txtOsvalor.getText().replace(",", "."));
            pst.setString(8, txtNumOs.getText());

            // Validação dos campos Obrigatorio
            if (txtOsId.getText().isEmpty() || (txtOsEquip.getText().isEmpty()) || (txtOsDefeito.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatorios");

            } else {

                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com sucesso.");

                    txtNumOs.setText(null);
                    txtDataOs.setText(null);
                    txtOsId.setText(null);
                    txtOsEquip.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsvalor.setText(null);

                    //Habilitando os Componentes 
                    btnOsCriar.setEnabled(true);
                    txtOsPesquisa.setEditable(true);
                    tblOsClientes.setVisible(true);
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    // Excluir OS
    private void excluir_OS() {
        
        if(txtNumOs.getText().isEmpty()){
           JOptionPane.showMessageDialog(null, "Numero da Ordem de Serviço não preenchido"); 
        
        }else{

        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja deletar esta Ordem de serviço", "Atenção", JOptionPane.YES_NO_OPTION);

        if ((confirma ==JOptionPane.YES_OPTION) && ! (txtNumOs.getText().isEmpty())) {

            String sql = "delete from tbOS where os=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNumOs.getText());
                int apagado = pst.executeUpdate();

                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço excuida com sucesso.");

                    txtNumOs.setText(null);
                    txtDataOs.setText(null);
                    txtOsId.setText(null);
                    txtOsEquip.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsvalor.setText(null);

                    //Habilitando os Componentes 
                    btnOsCriar.setEnabled(true);
                    txtOsPesquisa.setEditable(true);
                    tblOsClientes.setVisible(true);

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Numero da Ordem de Serviço não preenchido pelo sistema.");
        }

    }
    }
    
    public void  limpar_campos(){
    
                    txtOsPesquisa.setText(null);
                    txtNumOs.setText(null);
                    txtDataOs.setText(null);
                    txtOsId.setText(null);
                    txtOsEquip.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsvalor.setText(null);
                    
                    btnOsCriar.setEnabled(true);
                    txtOsPesquisa.setEditable(true);
                    tblOsClientes.setVisible(true);
    
    
    }
    
    // Imprimir Os  
    private void imprimir_OS(){
        
        if(txtNumOs.getText().isEmpty()){
           JOptionPane.showMessageDialog(null, "Numero da Ordem de Serviço não preenchido");
        }else{
        
      // Gerando um relatório de Ordem de Serviço
        
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desta Ordem de Serviço", "Atenção", JOptionPane.YES_NO_OPTION);
        
        if( confirma == JOptionPane.YES_OPTION ){
        
        //Chamando Framework  para imprimir o relatório.
            try {
                
                // Usando a Classe HashMap para criar um filtro
                HashMap filtro = new HashMap();
                filtro.put("os", Integer.parseInt(txtNumOs.getText()));
                
                
           //JasperPrint para preparar a impressão de relatório Ordem de Serviço
                JasperPrint print = JasperFillManager.fillReport("C:\\Users\\EvertonAngela\\Desktop\\imagens java\\Pastas Java\\Trabalhos salvos pelo IReport\\OrdemServiço.jasper", filtro, conexao);
                
              // A linha abaixo exibe o relatório através da classe JasperViewer
                JasperViewer.viewReport(print, false);
                        
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não há numero de Ordem de Serviço preenchida");
            }
        
        }
    }
    }  
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNumOs = new javax.swing.JTextField();
        txtDataOs = new javax.swing.JTextField();
        rbtOrcamento = new javax.swing.JRadioButton();
        rbtOrdemServico = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtOsPesquisa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOsClientes = new javax.swing.JTable();
        txtOsId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtOsEquip = new javax.swing.JTextField();
        txtOsDefeito = new javax.swing.JTextField();
        txtOsServico = new javax.swing.JTextField();
        txtOsTecnico = new javax.swing.JTextField();
        txtOsvalor = new javax.swing.JTextField();
        btnOsCriar = new javax.swing.JButton();
        btnOsPesquisar = new javax.swing.JButton();
        btnOsAlterar = new javax.swing.JButton();
        btnOSDelete = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnOsImprimir = new javax.swing.JButton();
        btnOsVassoura = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Tela OS");
        setAlignmentX(0.2F);
        setPreferredSize(new java.awt.Dimension(548, 451));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtNumOs.setEditable(false);

        txtDataOs.setEditable(false);

        buttonGroup1.add(rbtOrcamento);
        rbtOrcamento.setText("Orçamento");
        rbtOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOrdemServico);
        rbtOrdemServico.setText("Ordem de Serviço");
        rbtOrdemServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrdemServicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtNumOs, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbtOrcamento)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(rbtOrdemServico))
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtDataOs, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrcamento)
                    .addComponent(rbtOrdemServico))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação");

        cboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancada", "Aguardando Peças ", "Em analise ", "Realizando Conserto e Testes", "Abandonado pelo Cliente", "Equipamento desatualizado, peças indisponiveis no mercado", "Não encontrado o problema. ", "Equipamento para descarte", "Aguardando Analilse Técnica", "Equipamento devolvido", "Equipamento OK", "Equipamento Ok e Devolvido para o cliente" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));
        jPanel2.setPreferredSize(new java.awt.Dimension(242, 148));

        txtOsPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOsPesquisaKeyReleased(evt);
            }
        });

        jLabel4.setText("*id");

        tblOsClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "id", "Nome", "Fone"
            }
        ));
        tblOsClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOsClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOsClientes);

        txtOsId.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtOsPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOsId)
                .addGap(11, 11, 11))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOsPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtOsId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel5.setText("Equipamento");

        jLabel6.setText("Serviço");

        jLabel7.setText("Defeito");

        jLabel8.setText("Técnico");

        jLabel9.setText("Valor Total");

        txtOsEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsEquipActionPerformed(evt);
            }
        });

        txtOsvalor.setText("0");

        btnOsCriar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Create_1.png"))); // NOI18N
        btnOsCriar.setText("Criar");
        btnOsCriar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsCriarActionPerformed(evt);
            }
        });

        btnOsPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Read_1.png"))); // NOI18N
        btnOsPesquisar.setText("Pesquisar");
        btnOsPesquisar.setToolTipText("");
        btnOsPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesquisarActionPerformed(evt);
            }
        });

        btnOsAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/UpDate_1.png"))); // NOI18N
        btnOsAlterar.setText("Alterar");
        btnOsAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAlterarActionPerformed(evt);
            }
        });

        btnOSDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Delete_1.png"))); // NOI18N
        btnOSDelete.setText("Deletar");
        btnOSDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSDeleteActionPerformed(evt);
            }
        });

        jButton5.setText("jButton5");

        btnOsImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/print_2.png"))); // NOI18N
        btnOsImprimir.setText("Imprimir");
        btnOsImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsImprimirActionPerformed(evt);
            }
        });

        btnOsVassoura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/vassoura.png"))); // NOI18N
        btnOsVassoura.setToolTipText("Realiza a limpeza de todos os campos");
        btnOsVassoura.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 0)));
        btnOsVassoura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsVassoura.setPreferredSize(new java.awt.Dimension(65, 36));
        btnOsVassoura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsVassouraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel8)))
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtOsDefeito)
                                            .addComponent(txtOsEquip)))
                                    .addComponent(cboOsSit, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtOsServico, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtOsvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnOsVassoura, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOsCriar)
                        .addGap(3, 3, 3)
                        .addComponent(btnOsPesquisar)
                        .addGap(3, 3, 3)
                        .addComponent(btnOsAlterar)
                        .addGap(3, 3, 3)
                        .addComponent(btnOSDelete)
                        .addGap(3, 3, 3)
                        .addComponent(btnOsImprimir)))
                .addGap(40, 40, 40)
                .addComponent(jButton5)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))
                            .addComponent(btnOsVassoura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOsDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtOsvalor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOsCriar)
                    .addComponent(btnOsPesquisar)
                    .addComponent(btnOsAlterar)
                    .addComponent(btnOSDelete)
                    .addComponent(jButton5)
                    .addComponent(btnOsImprimir))
                .addGap(29, 29, 29))
        );

        setBounds(0, 0, 548, 451);
    }// </editor-fold>//GEN-END:initComponents

    private void rbtOrdemServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrdemServicoActionPerformed
        // Atribui um texto a variavel Ordem de Serviço.
        tipo = "Orçamento de Serviço";
    }//GEN-LAST:event_rbtOrdemServicoActionPerformed

    private void rbtOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcamentoActionPerformed
        // Atribui um texto a variavel tipo se selecionado.
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcamentoActionPerformed

    private void txtOsEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsEquipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsEquipActionPerformed

    private void txtOsPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOsPesquisaKeyReleased
        //Chamando o metodo pesquisar cliente

        pesquisar_cliente();
    }//GEN-LAST:event_txtOsPesquisaKeyReleased

    private void tblOsClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOsClientesMouseClicked
        // Chamando o metodo setar campos
        setar_campos();
    }//GEN-LAST:event_tblOsClientesMouseClicked

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Marca o interno Orçamento
        rbtOrcamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsCriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsCriarActionPerformed
        // Cria OS no sistema.
        emitir_OS();
    }//GEN-LAST:event_btnOsCriarActionPerformed

    private void btnOsPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesquisarActionPerformed
        //Chama o metodo pesquisar OS
        pesquisar_OS();
    }//GEN-LAST:event_btnOsPesquisarActionPerformed

    private void btnOsAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAlterarActionPerformed
        //Chama o metodo Alterar
        alterar_OS();
    }//GEN-LAST:event_btnOsAlterarActionPerformed

    private void btnOSDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSDeleteActionPerformed
        // Excuindo uma ordem de Serviço
        excluir_OS();
    }//GEN-LAST:event_btnOSDeleteActionPerformed

    private void btnOsVassouraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsVassouraActionPerformed
        // Limpa todos os campos
        limpar_campos();
    }//GEN-LAST:event_btnOsVassouraActionPerformed

    private void btnOsImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsImprimirActionPerformed
        // Imprimindo Ordem de Serviço
        
        imprimir_OS();
    }//GEN-LAST:event_btnOsImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOSDelete;
    private javax.swing.JButton btnOsAlterar;
    private javax.swing.JButton btnOsCriar;
    private javax.swing.JButton btnOsImprimir;
    private javax.swing.JButton btnOsPesquisar;
    private javax.swing.JButton btnOsVassoura;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSit;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOrcamento;
    private javax.swing.JRadioButton rbtOrdemServico;
    private javax.swing.JTable tblOsClientes;
    private javax.swing.JTextField txtDataOs;
    private javax.swing.JTextField txtNumOs;
    private javax.swing.JTextField txtOsDefeito;
    private javax.swing.JTextField txtOsEquip;
    private javax.swing.JTextField txtOsId;
    private javax.swing.JTextField txtOsPesquisa;
    private javax.swing.JTextField txtOsServico;
    private javax.swing.JTextField txtOsTecnico;
    private javax.swing.JTextField txtOsvalor;
    // End of variables declaration//GEN-END:variables
}
