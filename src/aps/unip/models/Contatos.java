package aps.unip.models;

import java.util.ArrayList;

import aps.unip.DAOs.DAOContatos;
/*
 * Essa classe armazena todos os contatos do cliente dentro de um ArrayList
 */
public class Contatos {
	
	private static ArrayList<Contato> contatos = new ArrayList<Contato>();
	
	/*
	 * Seta uma lista de contatos
	 * @param contatos_, Lista de Contatos.
	 */
	public static void setContatos(ArrayList<Contato> contatos_) {
		contatos = contatos_;
	}
	
	/*
	 * Retorna todos os contatos da lista.
	 */
	public static ArrayList<Contato> getContatos() {
		return contatos;
	}
	
	/*
	 * Retorna um contato com base no ID do contato.
	 * @param idContato, Id do contato que o cliente procura.
	 * @return
	 * 		Contato, Retorna um contato, caso seja encontrado.
	 * 		null, Retorna null quando nenhum contato, com o id passado, é encontrado.
	 */
	public static Contato getContato(int idContato) {
		if(contatos != null) {	
			for (Contato contato : contatos) {
				if (contato.getId() == idContato) {
					return contato;
				}
			}
		}
		return null;
	}
	
	/*
	 * Atualiza todos os contatos do Cliente.
	 */
	public static void atualizarContatos() {
		DAOContatos daoContatos = new DAOContatos();
		Contatos.setContatos(daoContatos.getContatos(Cliente.getId()));
	}
}

















