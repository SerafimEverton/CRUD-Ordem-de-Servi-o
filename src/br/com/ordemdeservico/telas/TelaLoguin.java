package br.com.ordemdeservico.telas;

import java.sql.*;
import br.com.ordemdeservico.dal.ModuloConexao;
import java.awt.Color;
import javax.swing.JOptionPane;

public class TelaLoguin extends javax.swing.JFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
      
    public void logar(){
    
     String sql = "select * from tbusuario where loguin = ? and senha =?";
    
        try {
            
            // As linhas abaixo preparam a consulta ao banco em função do que 
            // foi digitado nas caixas de texto .O "?" é substituido pelo conte-
            // udo das variaveis.
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtUsuario.getText());
            pst.setString(2, txtSenha.getText());
            // A linha abaixo executa a Query.
            rs = pst.executeQuery();
            
            // Se existir usuário e senha correspondente.
            if (rs.next()){
            // A linha abaixo captura o conteudo do campo Perfil da tabela tbusuario
            String perfil = rs.getString(6);
                System.out.println(perfil);
                // A estrutura abaixo faz o tratamento do perfil do usuario
                if(perfil.equals("Administrador")){
                   TelaPrincipal principal = new TelaPrincipal();
                   principal.setVisible(true);
                   TelaPrincipal.MenRel.setEnabled(true);
                   TelaPrincipal.MenCadUsu.setEnabled(true);
                   TelaPrincipal.lblUsuario.setText(rs.getString(2));  //ou lblUsuario.setText("Administrador");
                   TelaPrincipal.lblUsuario.setForeground(Color.RED);
                   this.dispose();
                   conexao.close();
                }else{
                
                    TelaPrincipal principal = new TelaPrincipal();
                   principal.setVisible(true);
                   TelaPrincipal.lblUsuario.setText(rs.getString(2));  //ou lblUsuario.setText("Usuário");
                   TelaPrincipal.lblUsuario.setForeground(Color.DARK_GRAY);
                   this.dispose();
                   conexao.close();
                   
                }              
            }else{ 
            JOptionPane.showMessageDialog(null, "Usuário ou senha invalida");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            
        }
    }  
    public TelaLoguin() {
                initComponents();
        conexao =ModuloConexao.Conector();
        System.out.println(conexao);
        if (conexao != null){
        lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/DBon.png")));    
        lblStatus.setText("Conectado");
        }else{
        lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Off.png")));    
        lblStatus.setText("Desconectado");
        }        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        btnLoguin = new javax.swing.JButton();
        txtSenha = new javax.swing.JPasswordField();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ordem de  Serviço");
        setPreferredSize(new java.awt.Dimension(425, 155));
        setResizable(false);

        jLabel1.setText("Usuário");

        jLabel2.setText("Senha");

        btnLoguin.setText("Login");
        btnLoguin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLoguin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoguinActionPerformed(evt);
            }
        });
        btnLoguin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLoguinKeyPressed(evt);
            }
        });

        lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ordemdeservico/icones/Off.png"))); // NOI18N
        lblStatus.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStatus)
                        .addGap(109, 109, 109)
                        .addComponent(btnLoguin))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsuario)
                            .addComponent(txtSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoguin)
                    .addComponent(lblStatus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(468, 157));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoguinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLoguinKeyPressed
        // TODO add your handling code here:
        logar();
    }//GEN-LAST:event_btnLoguinKeyPressed

    private void btnLoguinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoguinActionPerformed
        logar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoguinActionPerformed

    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLoguin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLoguin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLoguin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLoguin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
               TelaLoguin telinha = new TelaLoguin();
               telinha.setVisible(true);
               
               }
        });
    }
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoguin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
