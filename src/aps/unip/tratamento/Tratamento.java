package aps.unip.tratamento;

import java.util.Date;

import javax.swing.JOptionPane;

import aps.unip.DAOs.DAOContatos;
import aps.unip.DAOs.DAOConversa;
import aps.unip.DAOs.DAOUsuario;
import aps.unip.front.FrameAdicionarContato;
import aps.unip.front.Front;
import aps.unip.front.InternalFrameChat;
import aps.unip.front.LoginCadastroFront;
import aps.unip.models.Cliente;
import aps.unip.models.Contato;
import aps.unip.models.Contatos;
import aps.unip.models.Conversa;
import aps.unip.models.Conversas;
import aps.unip.protocolo.Mensagem;
import aps.unip.server.conexao.ClienteConnection;

/*
 * Essa classe faz o tratamento das mensagens que partem do servidor para o cliente.
 */
public class Tratamento {
	/*
	 * Verifica se o usuario já está cadastrado no banco de dados.
	 * Caso o contato nao for localizado, ele será criado.
	 * @param id, ID do usuario que vai ser verificado.
	 * @param nome, nome do usuario que vai ser verificado.
	 */
	private void verificarRegistro(int id, String nome) {
		DAOUsuario daoUsuario = new DAOUsuario();
		if (!daoUsuario.verificarCadastro(id)) {
			daoUsuario.criarRegistro(id, nome);
		}
	}
	
	/*
	 * Esse metodo inicia o frame principal, monta a lista de contatos e a lista de conversa.
	 */
	private void usuarioValidado(Mensagem mensagemInput) {

		LoginCadastroFront.closeLoginFrame();

		Cliente.setId((Integer) mensagemInput.getParametro("id"));
		Cliente.setNome((String) mensagemInput.getParametro("nome"));
		Cliente.setFoto((byte[]) mensagemInput.getParametro("foto"));
		
		verificarRegistro((Integer) mensagemInput.getParametro("id"), (String) mensagemInput.getParametro("nome"));
		
		Contatos.atualizarContatos();

		Conversas.atualizarConversas();

		//* Verifica se existe mensagens nao recebidas 
			Object[][] mensagensArquivadas = (Object[][]) mensagemInput.getParametro("mensagensArquivadas");
			if (mensagensArquivadas != null) {
				for (int i = 0; i < mensagensArquivadas.length; i++) {
					int remetente = (int) mensagensArquivadas[i][0];
					String mensagem = (String) mensagensArquivadas[i][1];
					direcionarNovaMensagem(remetente, mensagem);
				}
			}
			
		Front.initFront();
		ClienteConnection.startInputListener();
	}
	
	/*
	 * Esse metodo mostra a mensagem de usuario nao cadastrado e fecha a conexao com o servidor.
	 */
	private void usuarioNaoValidado() {
		JOptionPane.showMessageDialog(null, "Usuario nao cadastrado");
		ClienteConnection.fecharSocket();
		LoginCadastroFront.setEnableCompLogin();
	}
	
	/*
	 * Esse metodo informa para o cliente que ouve o cadastro e fecha a conexao com o servidor.
	 */
	private void usuarioCadastro() {
		JOptionPane.showMessageDialog(null, "Usuario Cadastrado");
		ClienteConnection.fecharSocket();
	}
	
	/*
	 * Esse metodo informa que o usuario nao foi cadastrado e fecha a conexao com o servidor.
	 * @param mensagem, Mensagem de resposta do servidor.
	 */
	private void cadastroNaoEfetuado(String mensagem) {
		JOptionPane.showMessageDialog(null, "Cadastro nao efetuado. \n" + mensagem);
		ClienteConnection.fecharSocket();

	}
	
	/*
	 * Cria uma conversa com o remetente da mensagem.
	 * @param idRemetente, remetente da mensagem.
	 */
	private void criarConversa(int idRemetente) {
		DAOConversa daoConversa = new DAOConversa();
		daoConversa.incluirNovaConverca(Cliente.getId(), idRemetente);
		Front.atualizarListaConversas();
	}
	
	/*
	 * Esse metodo direciona a mensagem que o cliente recebeu.
	 * #[Contato com conversa aberta] A mensagem é adicionada na conversa.
	 * #[naoContato com conversa aberta] A mensagem é adicionada na conversa.
	 * #[Contato sem conversa aberta] A conversa é criada e a mensagem é adicionada.
	 * #[Remetente nao é um contato] Um naoContato e criado, uma conversa é criada e a mensagem é adicionada.
	 * @param remetente_id, ID do remetente que mandou a mensagem.
	 * @param mensagem, Mensagem que o remetente enviou. 
	 */
	private void direcionarNovaMensagem(int remetente_id, String mensagem) {

		Contato contato = Contatos.getContato(remetente_id);
		if (contato != null) {
			Conversa conversa = Conversas.getConversa(remetente_id);
			if (conversa != null) {
				if (InternalFrameChat.idConversa != null && InternalFrameChat.idConversa == conversa.getIdConversa()) {
					InternalFrameChat.appendMensagemChat(contato.getNome(), mensagem);
				} else {
					Date date = new Date();
					String data = date.getHours() + ":" + date.getMinutes();
					String mensagemString = (data + "  (" + contato.getNome() + "): " + mensagem + "\n");
					DAOConversa daoConversa = new DAOConversa();
					daoConversa.addMensagem(mensagemString, conversa.getIdConversa());
					conversa.setNewMessageIcon();
				}
			} else {
				criarConversa(remetente_id);
				direcionarNovaMensagem(remetente_id, mensagem);
			}
		} else {
			DAOContatos contatos = new DAOContatos();
			DAOConversa conversa = new DAOConversa();
			contatos.AdicionarNaoContato(remetente_id, Cliente.getId());
			conversa.incluirNovaConverca(Cliente.getId(), remetente_id);
			Contatos.atualizarContatos();
			Conversas.atualizarConversas();
			Front.atualizarListaConversas();
			direcionarNovaMensagem(remetente_id, mensagem);
		}
	}
	
	/*
	 * Adiciona um contato.
	 */
	public void adicionarContato(Mensagem mensagem) {
		DAOContatos contatos = new DAOContatos();
		
		contatos.AdicionarContato((int) mensagem.getParametro("idContato"),
				                  (String) mensagem.getParametro("Nome"),
				                  (byte[]) mensagem.getParametro("foto"),
				                  Cliente.getId());
		contatos.deletarNaoContato(Cliente.getId(),(int) mensagem.getParametro("idContato"), false);
		Contatos.atualizarContatos();
		Conversas.atualizarConversas();
		Front.atualizarListaConversas();
		Front.desktopPaneConversa.removeAll();
		Front.desktopPaneConversa.setVisible(false);
		Front.desktopPaneConversa.setVisible(true);
		InternalFrameChat.idConversa = null;

	}

	/*
	 * Faz o direcionamento de todas as mensagem que partem do servidor.
	 * @param mensagemInput, mensagem que foi recebida do servidor.
	 */
	public void tratarMensagem(Mensagem mensagemInput) {
		try {
			switch (mensagemInput.getStatus()) {
			case VALIDADO:
				break;
			case NOVA_MENSAGEM:
				direcionarNovaMensagem((int) mensagemInput.getParametro("remetente_id"), 
						               (String) mensagemInput.getParametro("mensagem"));
				break;
			case CADASTRO_EFETUADO:
				usuarioCadastro();
				break;
			case CADASTRO_NAO_EFETUADO:
				cadastroNaoEfetuado((String) mensagemInput.getParametro("mensagem"));
				break;
			case USUARIO_NAO_CADASTRADO:
				usuarioNaoValidado();
				break;
			case STATUS_OK:
				usuarioValidado(mensagemInput);
				break;
			case USUARIOS_ENCONTRADOS:
				FrameAdicionarContato.montarListaDeContatos((Object[][]) mensagemInput.getParametro("usuarios"));
				break;
			case NENHUM_USUARIO_ENCONTRADO:
				JOptionPane.showMessageDialog(null, mensagemInput.getParametro("mensagem"));
				break;
			case USUARIO_ENCONTRADO:
				adicionarContato(mensagemInput);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
