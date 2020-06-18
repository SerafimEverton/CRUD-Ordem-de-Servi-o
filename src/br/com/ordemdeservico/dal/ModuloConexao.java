package br.com.ordemdeservico.dal;

import java.sql.*;

public class ModuloConexao {
    
    // Metodo responsavel por estabelecer a conecção
    public static Connection Conector(){
          java.sql.Connection conexao = null;
          
          // Chamando o DRIVER
          String driver = "com.mysql.jdbc.Driver";
          
          //Armazenando informações referente ao banco
          String  url = "jdbc:mysql://localhost:3307/ordem_de_serviço_1";
          String user = "root";
          String password = "";
          
          // Estaabelecendo a conexão com o banco
            try {
                Class.forName(driver);
                conexao = DriverManager.getConnection(url, user, password);
                return conexao;
            } catch (Exception e) {
                // Mostra a falta de Conexão
                System.out.println(e);
                return null;
                }
    
    }
    
}
