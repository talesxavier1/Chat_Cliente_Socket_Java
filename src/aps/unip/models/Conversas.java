package aps.unip.models;

import java.util.ArrayList;

import aps.unip.DAOs.DAOConversa;

/*
 * Essa classe armazena todas as conversas do cliente.
 */
public class Conversas {
	private static ArrayList<Conversa> conversas = new ArrayList<Conversa>();

	/*
	 * Retorna a lista de conversas.
	 */
	public static ArrayList<Conversa> getConversas() {
		return conversas;
	}
	
	/*
	 * seta as conversas na lista.
	 */
	public static void setConversas(ArrayList<Conversa> conversas_) {
		conversas = conversas_;
	}
	
	/*
	 * Retorna uma conversa.
	 * @param idContato, id do contato que pertence à conversa.
	 * @return
	 * 		Conversa, Retorna uma conversa com o contato passado.
	 * 		null, quando nenhuma conversa é encontrada.
	 */
	public static Conversa getConversa(int idContato) {
		if (conversas != null) {
			for (Conversa conversa : conversas) {
				if (conversa.getIdContato() == idContato) {
					return conversa;
				}
			}
		}
		return null;
	}
	
	/*
	 * Atualiza todas as conversas do Cliente.
	 */
	public static void atualizarConversas() {
		DAOConversa conversa = new DAOConversa();
		Conversas.setConversas(conversa.buscarListaConversas(Cliente.getId()));
	}
}
