package aps.unip.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import aps.unip.BD.coexao.SingletonConnection;

/*
 * Essa classe manipula os dados dos usu�rios.
 */
public class DAOUsuario {

	Connection connection = null;
	/*
	 * Verifica se o usu�rio ja est� cadastrado no Banco de Dados.
	 * @param id, ID do usu�rio.
	 * @return 
	 * 		*true, quando o usuario est� cadastrado.
	 * 		*false, caso n�o esteja cadastrado. 
	 */
	public boolean verificarCadastro(int id) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String SQL = String.format("SELECT * FROM USUARIO WHERE id_usuario = %s", id);
			connection = SingletonConnection.getConnection();
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			SingletonConnection.close(statement, resultSet);
		}
	}
	
	/*
	 * Cria o registro do usu�rio.
	 * @param id, ID do usu�rio.
	 * @param nome, Nome do usu�rio.
	 */
	public void criarRegistro(int id, String nome) {
		PreparedStatement statement = null;
		try {
			String SQL = String.format("INSERT INTO usuario (id_usuario,nome_usuario)VALUES (%s,'%s');", id,nome);
			connection = SingletonConnection.getConnection();
			statement = connection.prepareStatement(SQL);
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			SingletonConnection.close(statement);
		}

	}

}

