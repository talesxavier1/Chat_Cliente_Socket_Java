package aps.unip.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import aps.unip.BD.coexao.SingletonConnection;
import aps.unip.models.Contatos;
import aps.unip.models.Conversa;

/*
 * Essa classe manipula as conversas do usuario.
 */
public class DAOConversa {
	
	/*
	 * Verifica se uma conversa já existe.
	 * @param idUsuario, ID do usuario que possuir a conversa.
	 * @param idContato, ID do contato que faz parte da conversa.
	 * @return
	 *   *true, Caso não exista conversa com o contato.
	 *   *false, Caso exista conversa com o contato.
	 */
	private boolean verificarExistencia(int idUsuario, int idContato) {
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String SQL = String.format("SELECT * FROM conversa WHERE fk_id_usuario = %s AND fk_id_contato = %s", idUsuario, idContato);
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				return false;
			}else {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			SingletonConnection.close(statement, resultSet);
		}
	}
	
	/*
	 * Cria uma conversa com o contato.
	 * @param idContato, ID do contato que vai pertencer a conversa.
	 * @param idUsuario, ID do usuario que vai possuir a conversa.
	 * @return
	 *   *true, Caso a conversa foi incluida.
	 *   *false, Caso ocorra algum erro.
	 */
	public boolean incluirNovaConverca(int idUsuario, int idContato) {
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			if (verificarExistencia(idUsuario, idContato)) {
				String SQL = String.format("INSERT INTO conversa (fk_id_usuario,fk_id_contato)VALUES (%s,%s);", idUsuario, idContato);
				statement = connection.prepareStatement(SQL);
				statement.execute();
				connection.commit();
				return true;
			}
			
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
		return false;
	}
	
	/*
	 * Busca todas as conversas do usuario.
	 * @param idUsuario, ID do usuario que possui as conversas.
	 * @return
	 *   *ArrayList<Conversa>, quando é encontrado alguma conversa.
	 *   *null, quando nao é encontrado nenhuma conversa.
	 */
	public ArrayList<Conversa> buscarListaConversas(int idUsuario){
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String SQL = String.format(
					"SELECT conversa.id_conversa, conversa.fk_id_contato FROM conversa where conversa.fk_id_usuario = %s;",
					idUsuario);
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			
			ArrayList<Conversa> retornoBd = new ArrayList<Conversa>();
			while (resultSet.next()) {
				
				Conversa conversa = new Conversa(Contatos.getContato(resultSet.getInt("fk_id_contato")), resultSet.getInt("id_conversa"));
				retornoBd.add(conversa);
			}

			return retornoBd;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SingletonConnection.close(statement, resultSet);
		}
		return null;
	}
	
	/*
	 * Deleta as mensagens de uma conversa.
	 * @param idContato, ID do contato pertence mandou as mensagens.
	 * @param idUsuario, ID do usuario que possui as mensagens.
	 */
	public void deletarMensagens(int idContato, int idUsuario) {
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			String SQL = String.format(
					"DELETE FROM mensagem WHERE mensagem.fk_id_conversa IN ("
					+ "SELECT mensagem.fk_id_conversa FROM mensagem "
					+ "INNER JOIN conversa ON mensagem.fk_id_conversa = conversa.id_conversa "
					+ "where conversa.fk_id_usuario = %s AND conversa.fk_id_contato = %s);"
					,idUsuario,idContato);
			
			statement = connection.prepareStatement(SQL);
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			SingletonConnection.close(statement);
		}
		
	}
	
	
	/*
	 * Deleta a conversa e as mensagens da conversa.
	 * @param idContato, ID do contato pertence á conversa.
	 * @param idUsuario, ID do usuario que possui a conversa.
	 */
	public void deletarConversa(int idContato, int idUsuario) {
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			deletarMensagens(idContato, idUsuario);
			String SQL1 = String.format(
					"DELETE FROM conversa where conversa.fk_id_contato = %s AND conversa.fk_id_usuario = %s;" 
					,idContato,idUsuario);
			
			statement = connection.prepareStatement(SQL1);
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			SingletonConnection.close(statement);
		}
	}
	
	/*
	 * Busca as mensagens das conversas.
	 * @param idContato, ID do contato que mandou as mensagens.
	 * @param idUsuario, ID do usuario que possui as mensagens.
	 * @return 
	 * 		ArrayList<String>, caso retorne alguma mensagem.
	 * 		null, caso não retorne nenhuma mensagem.
	 */
	public ArrayList<String> getMensagens(int idUsuario,int idContato){
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String SQL = String.format(
					"SELECT mensagem.mensagem FROM mensagem INNER JOIN conversa ON mensagem.fk_id_conversa = conversa.id_conversa WHERE conversa.fk_id_usuario = %s AND conversa.fk_id_contato = %s;",
					idUsuario, idContato);
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			
			ArrayList<String> retornoBd = new ArrayList<String>();
			while (resultSet.next()) {
				retornoBd.add(resultSet.getString("mensagem"));
			}
			return retornoBd;
			
		}catch (Exception e) {
			return null;
		}finally {
			SingletonConnection.close(statement, resultSet);
		}
	}
	
	/*
	 * Adiciona uma nova mensagem à tabela.
	 * @param mensagem, A mensagem que vai ser armazenada.
	 * @param idConversa, ID da conversa entre usuario e contato ou usuario e naoContato.
	 */
	public void addMensagem(String mensagem, int idConversa) {
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
				String SQL = String.format("INSERT INTO mensagem (fk_id_conversa,mensagem)VALUES (%s,'%s');", idConversa, mensagem);
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








