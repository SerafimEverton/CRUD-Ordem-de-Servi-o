package br.com.ordemdeservico.telas;

// Importa recursos da Biblioteca de busca Java.(rs2xml.jar)
import net.proteanit.sql.DbUtils;

import java.sql.*;
import br.com.ordemdeservico.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.Conector();
    }

    // Pesquisando Clientes atraves da Biblioteca de Filtro.
    private void pesquisar_clientes() {

        String sql = "select * from tbClientes where nomeCliente like ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            // Passando o conteúdo da caixa de pesquisar para o interroga
            // Atenção ao % que é a continuação da String sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            
            // A Linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
                        
        } catch (Exception e) {
        }

    }
    
    // Metodo para setar o conteudo da tabela nos campos do formulario
    public void setar_campos(){
    
    int setar = tblClientes.getSelectedRow();
    
    if(txtCliPesquisar.getText().isEmpty()){
        
        JOptionPane.showMessageDialog(null, "Preencha o campo de Pesquisar", "Busca de Cadastro do Cliente", DISPOSE_ON_CLOSE); //(null, "Preencha o campo de Pesquisar","");
            
         }else{
            txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
            txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
            String nome = txtCliNome.getText();
            txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
            txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
            txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
            txtCliPesquisar.setText (nome);//(tblClientes.getModel().getValueAt(setar, 1);
//txtCliNome.getText
                 }  
            jbCliAdicionar.setEnabled(false);
    }
    

    // Método para adicionar Clientes.
    private void adicionarCli() {
        
        int confirma = JOptionPane.showConfirmDialog(null, "Tem ceteza de que os dados estão corretos?", "Atenção", JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){
        
            String sql = "insert into tbClientes ( nomeCliente, endCliente, foneCliente,emailCliente) values(?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEndereco.getText());
            pst.setString(3, txtCliFone.getText());
            pst.setString(4, txtCliEmail.getText());

            // Validação dos campos obrigatorios.
            if ((txtCliNome.getText().isEmpty()) || (txtCliEndereco.getText().isEmpty()) || (txtCliFone.getText().isEmpty()) || (txtCliEmail.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            
           
            } else {

                // A linha abaixo atualiza a tabela do usuario com os dados do formulario.
                // A estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    // Alinha "sout" serve para ajudar a identificar se adiconado=1
                    System.out.println(adicionado);
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com Sucesso");

                    txtCliNome.setText(null);
                    txtCliFone.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliEmail.setText(null);
                    txtCliPesquisar.setText(null);
                    txtCliId.setText(null);
                    
                 }
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    }

    // Metodo UpDate
    private void alterarCli() {
        
        int confirma = JOptionPane.showConfirmDialog(null, "Tem ceteza que deseja alterar?", "Atenção", JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){

        String sql = "update tbClientes set  nomeCliente=?, endCliente=?, foneCliente=?,emailCliente=? where idClientes=?";

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEndereco.getText());
            pst.setString(3, txtCliFone.getText());
            pst.setString(4, txtCliEmail.getText());
            pst.setString(5, txtCliId.getText());
            
            // Validação dos campos obrigatorios.
            if ((txtCliNome.getText().isEmpty()) || (txtCliEndereco.getText().isEmpty()) || (txtCliFone.getText().isEmpty()) || (txtCliEmail.getText().isEmpty()) || (txtCliId.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Clique no campo 'Nome do Cliente' para selecionar o Cliente mostrado na tabela");

            } else {

                // A linha abaixo atualiza a tabela do Cliente com os dados do formulario.
                // A estrutura abaixo é usada para confirmar a inserção de dados na tabela
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    // Alinha "sout" serve para ajudar a identificar se adiconado=1
                    System.out.println(adicionado);
                    JOptionPane.showMessageDialog(null, "Cadastro alterado com Sucesso");

                    txtCliNome.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);
                    txtCliPesquisar.setText(null);
                    jbCliAdicionar.setEnabled(true);
                    txtCliId.setText(null);
                    
                    // A linha abaixo se refere ao Combo Box.
                    //cboUsuPerfil.setSelectedItem(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }

    private void limpa_campos(){
        
                    txtCliNome.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);
                    txtCliPesquisar.setText(null);
                    jbCliAdicionar.setEnabled(true);    
                    txtCliId.setText(null);
                                  
                   
    }
    
    
    
    private void apagaCli() {
        
        if (txtCliId.getText().isEmpty()){
        
            JOptionPane.showMessageDialog(null, "Não existe Cliente selecionado" );
        
        }else{

        // A instrução abaixo confirma a remoção do usuario.
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este Cliente?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbClientes where idClientes=?";

            try {
                pst = conexao.prepareStatement(sql);

                pst.setString(1, txtCliId.getText());
                pst.executeUpdate();

                int deletado = pst.executeUpdate();

                if (deletado <1) {
                    // Alinha "sout" serve para ajudar a identificar se adiconado=1
                    System.out.println(deletado);
                    JOptionPane.showMessageDialog(null, "Cadastro deletado com Sucesso");

                    //txtIdCliente.setText(null);
                    txtCliNome.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);
                    txtCliPesquisar.setText(null);
                    txtCliId.setText(null);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
    }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        buttonGroup1 = new javax.swing.ButtonGroup();
        txtCliNome = new javax.swing.JTextField();
        txtCliEndereco = new javax.swing.JTextField();
        txtCliFone = new javax.swing.JTextField();
        txtCliEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jbCliAdicionar = new javax.swing.JButton();
        jbCliAlterar = new javax.swing.JButton();
        jbCliApagar = new javax.swing.JButton();
        txtCliId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnLimpCampos = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cliente");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(548, 453));

        jLabel1.setText("* Nome");

        jLabel2.setText("* Endereço");

        jLabel3.setText("* Telefone");

        jLabel4.setText("* Email");

        jLabel5.setText("* Campos Obrigatórios");

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel9.setText("Nome do Cliente");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Lupa_1.png"))); // NOI18N
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jbCliAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Create_1.png"))); // NOI18N
        jbCliAdicionar.setText("Criar");
        jbCliAdicionar.setToolTipText("Preencha os campos obrigatorios");
        jbCliAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCliAdicionarActionPerformed(evt);
            }
        });

        jbCliAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/UpDate_1.png"))); // NOI18N
        jbCliAlterar.setText("Alterar");
        jbCliAlterar.setToolTipText("Selecione um cliente da tabela para alterar.");
        jbCliAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCliAlterarActionPerformed(evt);
            }
        });

        jbCliApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Delete_1.png"))); // NOI18N
        jbCliApagar.setText("Apagar");
        jbCliApagar.setToolTipText("Selecione um cliente da tabela para apagar.");
        jbCliApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCliApagarActionPerformed(evt);
            }
        });

        txtCliId.setEnabled(false);

        jLabel6.setText("IdCliente");

        btnLimpCampos.setText("Limpar Campos");
        btnLimpCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpCamposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCliNome, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                    .addComponent(txtCliEndereco)
                                    .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(213, 213, 213))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jbCliAdicionar)
                        .addGap(79, 79, 79)
                        .addComponent(jbCliAlterar)
                        .addGap(78, 78, 78)
                        .addComponent(jbCliApagar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 41, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnLimpCampos)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10)
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(26, 26, 26)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnLimpCampos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCliApagar)
                    .addComponent(jbCliAlterar)
                    .addComponent(jbCliAdicionar))
                .addGap(58, 58, 58))
        );

        setBounds(0, 0, 548, 453);
    }// </editor-fold>//GEN-END:initComponents

    private void jbCliAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCliAdicionarActionPerformed
        // Adiciona Clientes
        adicionarCli();
    }//GEN-LAST:event_jbCliAdicionarActionPerformed

    private void jbCliAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCliAlterarActionPerformed
        // Altera o cadastro do Cliente.
        alterarCli();
    }//GEN-LAST:event_jbCliAlterarActionPerformed

    private void jbCliApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCliApagarActionPerformed
        // Apaga o cadastro do Cliente.
        apagaCli();
    }//GEN-LAST:event_jbCliApagarActionPerformed

    
    // Oevento abaixo é do tipo "enquanto for digitado"
    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // Chama o metodo pesquisar clientes.
        
        pesquisar_clientes();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // Evento que sera usado para setar os campos da tabela clicando com o mouse
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnLimpCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpCamposActionPerformed
        // Limpa todos os campos e Libera Criar cadastro
        limpa_campos();
    }//GEN-LAST:event_btnLimpCamposActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpCampos;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jbCliAdicionar;
    private javax.swing.JButton jbCliAlterar;
    private javax.swing.JButton jbCliApagar;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliEndereco;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliPesquisar;
    // End of variables declaration//GEN-END:variables

}
