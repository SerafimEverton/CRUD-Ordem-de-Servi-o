package br.com.ordemdeservico.telas;

import java.sql.*;
import br.com.ordemdeservico.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaUsuario extends javax.swing.JInternalFrame {
  
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public TelaUsuario() {
        initComponents();
        conexao =ModuloConexao.Conector();
      } 
    
        private void consultarUsu(){
        String sql = "select * from tbusuario where iduser = ?";
          try {
             pst = conexao.prepareStatement(sql);
             pst.setString(1, txtUsuId.getText());
             rs = pst.executeQuery();
             
              if (rs.next()) {
                  txtUsuNome.setText(rs.getString(2));
                  txtUsuFone.setText(rs.getString(3));
                  txtUsuLogin.setText(rs.getString(4));
                  txtUsuSenha.setText(rs.getString(5));
                  // A linha abaixo se refere ao Combo Box.
                  cboUsuPerfil.setSelectedItem(rs.getString(6));
              } else {
                  JOptionPane.showMessageDialog(null, "Usuario não Cadastrado");
                  // As linhas abaixo limpa os campos
                  txtUsuNome.setText(null);
                  txtUsuFone.setText(null);
                  txtUsuLogin.setText(null);
                  txtUsuSenha.setText(null);
                  // A linha abaixo se refere ao Combo Box.
                  //cboUsuPerfil.setSelectedItem(null);
              }
             
           } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
           }
        }
    
        // Método para adicionar usuário.
        private void adicionarUsu(){
            String sql = "insert into tbusuario ( iduser, usuario, fone, loguin, senha, Perfil) values(?,?,?,?,?,?)";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText()); // Set o campo 1 da tabela com o conteúdo txtUsuId
                pst.setString(2, txtUsuNome.getText());
                pst.setString(3, txtUsuFone.getText());
                pst.setString(4, txtUsuLogin.getText());
                pst.setString(5, txtUsuSenha.getText());
                pst.setString(6, cboUsuPerfil.getSelectedItem().toString());
                
                  // Validação dos campos obrigatorios.
                
                  if((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())){
                   JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
                  
                  
                  }else{
                      
                   // A linha abaixo atualiza a tabela do usuario com os dados do formulario.
                   // A estrutura abaixo é usada para confirmar a inserção de dados na tabela
                  int adicionado = pst.executeUpdate();
               
                    if(adicionado>0){
                          // Alinha "sout" serve para ajudar a identificar se adiconado=1
                          System.out.println(adicionado);
                          JOptionPane.showMessageDialog(null, "Cadastro realizado com Sucesso");
                          
                           txtUsuId.setText(null);
                           txtUsuNome.setText(null);
                           txtUsuFone.setText(null);
                           txtUsuLogin.setText(null);
                           txtUsuSenha.setText(null);
                            // A linha abaixo se refere ao Combo Box.
                            //cboUsuPerfil.setSelectedItem(null);
                          
                          }   
                          }
                          }catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                                                }
             
    }  
        
        // Metodo UpDate
        
        private void alterarUsu(){
        
        String sql = "update tbusuario set usuario=?, fone=?, loguin=?, senha=?, Perfil=? where iduser=?";
        
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuNome.getText());
                pst.setString(2, txtUsuFone.getText());
                pst.setString(3, txtUsuLogin.getText());
                pst.setString(4, txtUsuSenha.getText());
                pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
                pst.setString(6, txtUsuId.getText());
                
                if((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())){
                   JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
                  
                  
                  }else{
                      
                   // A linha abaixo atualiza a tabela do usuario com os dados do formulario.
                   // A estrutura abaixo é usada para confirmar a inserção de dados na tabela
                  int adicionado = pst.executeUpdate();
               
                    if(adicionado>0){
                          // Alinha "sout" serve para ajudar a identificar se adiconado=1
                          System.out.println(adicionado);
                          JOptionPane.showMessageDialog(null, "Cadastro alterado com Sucesso");
                          
                           txtUsuId.setText(null);
                           txtUsuNome.setText(null);
                           txtUsuFone.setText(null);
                           txtUsuLogin.setText(null);
                           txtUsuSenha.setText(null);
                            // A linha abaixo se refere ao Combo Box.
                            //cboUsuPerfil.setSelectedItem(null);
                          
                          }   
                          }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        
        }
        
        //Metodo responsavel pela remoção de usuarios.
        private void deletarUsu(){
        // A instrução abaixo confirma a remoção do usuario.
        int confirma =JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuario?", "Atenção", JOptionPane.YES_NO_OPTION);
        if(confirma == JOptionPane.YES_OPTION){
        String sql = "delete from tbusuario where iduser=?";
        
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                pst.executeUpdate();  
                
                int deletado = pst.executeUpdate();
               
                    if(deletado<1){
                          // Alinha "sout" serve para ajudar a identificar se adiconado=1
                          System.out.println(deletado);
                          JOptionPane.showMessageDialog(null, "Cadastro deletado com Sucesso");
                          
                           txtUsuId.setText(null);
                           txtUsuNome.setText(null);
                           txtUsuFone.setText(null);
                           txtUsuLogin.setText(null);
                           txtUsuSenha.setText(null);
                            // A linha abaixo se refere ao Combo Box.
                            //cboUsuPerfil.setSelectedItem(null);
                          
                          }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        
        }
        }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuId = new javax.swing.JTextField();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuLogin = new javax.swing.JTextField();
        txtUsuSenha = new javax.swing.JTextField();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtUsuFone = new javax.swing.JTextField();
        btnUsuCreate = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnListUsu = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(548, 453));

        jLabel1.setText("* id");

        jLabel2.setText("*Nome");

        jLabel3.setText("*Login");

        jLabel4.setText("*Senha");

        jLabel5.setText("*Perfil");

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Usuario", "Administrador" }));

        jLabel6.setText("Fone");

        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Create_1.png"))); // NOI18N
        btnUsuCreate.setText("Create");
        btnUsuCreate.setToolTipText("Adicionar");
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Read_1.png"))); // NOI18N
        btnUsuRead.setText("Read");
        btnUsuRead.setToolTipText("Consultar");
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });

        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/UpDate_1.png"))); // NOI18N
        btnUsuUpdate.setText("UpDate");
        btnUsuUpdate.setToolTipText("Alterar dados");
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Delete_1.png"))); // NOI18N
        btnUsuDelete.setText("Delete");
        btnUsuDelete.setToolTipText("Remover");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(108, 80));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });

        jLabel7.setText("* Campos obrigatórios");

        btnListUsu.setText("Visualizar Lista\n de Usuários");
        btnListUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListUsuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuNome)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(63, 63, 63))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsuSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(txtUsuFone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(81, 81, 81))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUsuCreate, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnUsuRead, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnUsuUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnListUsu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnListUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );

        setBounds(0, 0, 548, 453);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
      consultarUsu();
    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void btnUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreateActionPerformed
      // Chamando o metodo adicionar.
      adicionarUsu();
    }//GEN-LAST:event_btnUsuCreateActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        // Chamando o metodo Alterar.
        alterarUsu();
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        // Chamando o metodo Deletar dados.
        deletarUsu();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed

    private void btnListUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListUsuActionPerformed
        try {
                            
                
           //JasperPrint para preparar a impressão de relatório Ordem de Serviço
                JasperPrint print = JasperFillManager.fillReport("C:\\Users\\EvertonAngela\\Desktop\\imagens java\\Pastas Java\\Trabalhos salvos pelo IReport\\Lista de Usiários.jasper", null, conexao);
                
              // A linha abaixo exibe o relatório através da classe JasperViewer
                JasperViewer.viewReport(print, false);
                        
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lista não encontrada.");
            }
                
               
    }//GEN-LAST:event_btnListUsuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnListUsu;
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
