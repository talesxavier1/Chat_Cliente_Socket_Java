package aps.unip.BD.coexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Classe que conecta com o Banco de Dados.
 */
public class SingletonConnection {
	
	private static String URL = "jdbc:sqlite:DB/BancoDeDados - Copia.db";
	//private static String URL = "jdbc:sqlite:DB/BancoDeDados.db";
	private static Connection connection = null;
	
	static {
		connect();
	}
	
	public SingletonConnection() {
		
	}
	
	/*
	 *Abre uma conexao com o banco de dados.
	 */
	private static void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			if (connection == null) {
				connection = DriverManager.getConnection(URL);
				connection.setAutoCommit(false);
				System.out.println("[BANCO DE DADOS CONECTADO]");
			}
		} catch (SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	/*
	 * *Fecha o statement que foi aberto na consulta.
	 * @param statement O statement que foi aberto na execucao do SQL.
	 */
	public static void close(PreparedStatement statement) {
		try {
			if( statement != null) {
				statement.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * *Fecha o statement que foi aberto na consulta.
	 * @param statement O statement que foi aberto na consulta.
	 * @param resultSet O resultSet que foi aberto na consulta.
	 */
	public static void close(PreparedStatement statement, ResultSet resultSet) {
		try {
			if(resultSet != null) {
				
				resultSet.close();
			}
			if(statement != null) {				
				statement.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
