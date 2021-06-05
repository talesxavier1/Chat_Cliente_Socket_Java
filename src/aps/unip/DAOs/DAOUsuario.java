package aps.unip.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import aps.unip.BD.coexao.SingletonConnection;

/*
 * Essa classe manipula os dados dos usuários.
 */
public class DAOUsuario {

	Connection connection = null;
	/*
	 * Verifica se o usuário ja está cadastrado no Banco de Dados.
	 * @param id, ID do usuário.
	 * @return 
	 * 		*true, quando o usuario está cadastrado.
	 * 		*false, caso não esteja cadastrado. 
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
	 * Cria o registro do usuário.
	 * @param id, ID do usuário.
	 * @param nome, Nome do usuário.
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

