package aps.unip.server.conexao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import aps.unip.enums.Requisicao;
import aps.unip.protocolo.Mensagem;
import aps.unip.tratamento.Tratamento;

/*
 * Essa classe faz a conexao entre o cliente e o servidor.
 */
public class ClienteConnection {
	
	private static Socket socket = null;
	private static ObjectOutputStream outputStream = null;
	private static ObjectInputStream inputStream = null;

	/*
	 * Conecta o cliente com o servidor.
	 */
	public static void conectar() {
		if (socket == null || !socket.isConnected()) {
			try {
				socket = new Socket("localhost", 80);
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				inputStream = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Fecha a conexao entre o cliente e o servidor.
	 */
	public static void fecharSocket() {
		try {
			socket.close();
			socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Metodo que faz o pedido de login para o servidor.
	 */
	public static void login(String usuario, String senha) {
		Mensagem mensagemLogin = new Mensagem();
		mensagemLogin.setRequisicao(Requisicao.LOGIN);
		mensagemLogin.setParametros("email", usuario);
		mensagemLogin.setParametros("senha", senha);
		dispararRequisicao(mensagemLogin);
		
		try {
			System.out.println("Esperando resposta de login");
			Mensagem mensagemInput = (Mensagem) inputStream.readObject();
			Tratamento tratamento = new Tratamento();
			tratamento.tratarMensagem(mensagemInput);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * Metodo que faz o pedido de cadastro para o servidor.
	 */
	public static void Cadastrar(String nome,String email,String senha, byte[] foto) {
		Mensagem mensagemCadastro = new Mensagem();
		mensagemCadastro.setRequisicao(Requisicao.CADASTRO);
		mensagemCadastro.setParametros("nome", nome);
		mensagemCadastro.setParametros("email", email);
		mensagemCadastro.setParametros("senha", senha);
		mensagemCadastro.setParametros("foto", foto);
		dispararRequisicao(mensagemCadastro);
		
		try {
			System.out.println("Esperando resposta de Cadastro");
			Mensagem mensagemInput = (Mensagem) inputStream.readObject();
			Tratamento tratamento = new Tratamento();
			tratamento.tratarMensagem(mensagemInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo que dispara uma mensagem para o servidor.
	 * @param mensagemOutput, Mensagem que vai ser enviada para o servidor.
	 */
	public static void dispararRequisicao(Mensagem mensagemOutput) {
		try {
			outputStream.writeObject(mensagemOutput);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Essa Thread monitora o inputListener.
	 * Todas as mensagens que partem do servidor para o cliente sao capturadas por essa Thread.
	 */
	private static Thread inputListener = new Thread() {
		public void run() {
			for (;;) {
				try {
					System.out.println("inputListener rodando");
					Mensagem mensagemInput = (Mensagem) inputStream.readObject();
					Tratamento tratamento = new Tratamento();
					tratamento.tratarMensagem(mensagemInput);
				} catch (Exception e) {
					
					e.printStackTrace();
					break;
				}
			}
		};
	};

	/*
	 * Para o inputListener.
	 */
	public static void stopInputListener() {
		if (inputListener.isAlive()) {
			inputListener.stop();
		}
	}
	
	/*
	 * Inicia o inputListener.
	 */
	public static void startInputListener() {
		if (!inputListener.isAlive()) {
			inputListener.start();
		}
	}
}
