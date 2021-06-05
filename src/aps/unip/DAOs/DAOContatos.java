package aps.unip.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import aps.unip.BD.coexao.SingletonConnection;
import aps.unip.models.Contato;

/*
 * Classe que manipula os contatos do usuario.
 */
public class DAOContatos {
	
	/*
	 * Verifica a existencia de um usuario na tabela de contatos.
	 * @param idContato, ID do contato que vai ser verificado.
	 * @param idUsuario, ID do usuario que possui o contato.
	 * @return true, contato consta na tabela de Contatos.
	 * @return false, contato nao consta na tabela Contatos.
	 */
	public boolean verificarContato(int idContato, int idUsuario) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = SingletonConnection.getConnection();
			String SQL = String.format("SELECT * FROM contatos where id_contato = %s AND fk_id_usuario = %s;", idContato, idUsuario);
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				return true;
			}else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SingletonConnection.close(statement);
		}
		return true;
	}
	
	/*
	 * Busca os naoContatos, contatos que nao foram adicionados. 
	 * (Quando o cliente recebe uma mensagem de outro usuario que nao consta nos contatos, O Id desse contato é armazenado nos naoContatos).
	 * @param idUsuario, ID do usuario que possui naoContato.
	 * @return ArrayList<Contato>, caso seja encontrado algum naoContato.
	 * @return null, Quando nao é encontrado nenhum naoContato.
	 */
	private ArrayList<Contato> getNaoContatos(int idUsuario){
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String SQL = String.format("SELECT * FROM naoContatos WHERE fk_id_usuario = %s", idUsuario);
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			
			ArrayList<Contato> retorno = new ArrayList<Contato>();
			while (resultSet.next()) {
				int idContato = resultSet.getInt("id_contato");
				Contato contato = new Contato("Desconhecido", idContato, null);
				retorno.add(contato);
			}
			if (retorno.size() >= 1) {
				return retorno;
			}else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			SingletonConnection.close(statement, resultSet);
		}
		
	}
	
	/*
	 * Busca os Contatos.
	 * @param idUsuario, ID do usuario que possui os Contatos.
	 * @return ArrayList<Contato>, Caso seja encontrado algum Contato.
	 * @return null, Quando nao é encontrado nenhum Contato.
	 */
	public ArrayList<Contato> getContatos(int idUsuario){
		ArrayList<Object> RetornoBd = new ArrayList<Object>();
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String SQL = String.format("SELECT * FROM contatos WHERE fk_id_usuario = %s", idUsuario);
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				RetornoBd.add(resultSet.getInt("id_contato"));
				RetornoBd.add(resultSet.getString("nome_contato"));
				RetornoBd.add(resultSet.getBytes("foto_contato"));
			}
			
			ArrayList<Contato> retorno = new ArrayList<Contato>();
			if(RetornoBd.size() >= 3 ) {
				int linhas = RetornoBd.size()/3;
				for (int i = 0; i < linhas; i++) {
					Contato contato = new Contato((String) RetornoBd.get(1), (Integer) RetornoBd.get(0), (byte[]) RetornoBd.get(2));
					RetornoBd.remove(0);
					RetornoBd.remove(0);
					RetornoBd.remove(0);
					
					retorno.add(contato);
				}
				ArrayList<Contato> naoContatos = getNaoContatos(idUsuario);
				if (naoContatos != null) {
					for (Contato contato : naoContatos) {
						retorno.add(contato);
					}
				}
				return retorno;
			}else {
				ArrayList<Contato> naoContatos = getNaoContatos(idUsuario);
				if (naoContatos != null) {
					for (Contato contato : naoContatos) {
						retorno.add(contato);
					}
					return retorno;
				}
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			SingletonConnection.close(statement, resultSet);
		}
	}
	
	/*
	 * Adiciona um contato na tabela Contatos.
	 * @param idContato, ID do contato que vai ser gravado.
	 * @param nomeContato, Nome do contato que vai ser gravado.
	 * @param foto, Foto do contato, em array de bytes, que vai ser gravado.
	 * @param idUsuario, ID do usuario que vai possuir o contato.
	 */
	public void AdicionarContato(int idContato, String nomeContato, byte[] foto, int idUsuario) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = SingletonConnection.getConnection();
			String SQL = "INSERT INTO contatos (id_contato,nome_contato,foto_contato,fk_id_usuario)VALUES (?,?,?,?)";
			statement = connection.prepareStatement(SQL);
			statement.setInt(1, idContato);
			statement.setString(2, nomeContato);
			statement.setBytes(3, foto);
			statement.setInt(4, idUsuario);
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
	
	/*
	 * Adiciona um naoContato.
	 * @param idContato, ID do contato que vai ser adicionado.
	 * @param idUsuario, ID do usuario que possuir o naoContato.
	 */
	public void AdicionarNaoContato(int idContato, int idUsuario) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = SingletonConnection.getConnection();
			String SQL = "INSERT INTO naoContatos (id_contato, fk_id_usuario)VALUES (?,?)";
			statement = connection.prepareStatement(SQL);
			statement.setInt(1, idContato);
			statement.setInt(2, idUsuario);
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
	
	/*
	 * Deleta um naoContato.
	 * @param idContato, ID do naoContato que vai ser deletado.
	 * @param idUsuario, ID do usuario que possuir o naoContato.
	 * @param deletarConversa 
	 *   *true, deleta a conversa com o naoContato.
	 *   *false, nao deleta a conversa com o naoContato.
	 */
	public void deletarNaoContato(int idUsuario, int idContato, boolean deletarConversa) {
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			DAOConversa conversa = new DAOConversa();
			if(deletarConversa) {				
				conversa.deletarConversa(idContato, idUsuario);
			}
			String SQL = String.format("DELETE FROM naoContatos WHERE id_contato = %s AND fk_id_usuario = %s;", idContato, idUsuario);
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
	
	/*
	 * Deleta um contato.
	 * @param idContato, ID do contato que vai ser deletado.
	 * @param idUsuario, ID do usuario que possuir o naoContato.
	 */
	public void deletarContato(int idUsuario, int idContato) {
		Connection connection = SingletonConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			DAOConversa conversa = new DAOConversa();
			conversa.deletarConversa(idContato, idUsuario);
			String SQL = String.format("DELETE FROM contatos WHERE id_contato = %s AND fk_id_usuario = %s;", idContato, idUsuario);
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

