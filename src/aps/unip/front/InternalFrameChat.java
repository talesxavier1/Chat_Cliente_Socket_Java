package aps.unip.front; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import aps.unip.DAOs.DAOConversa;
import aps.unip.enums.Requisicao;
import aps.unip.models.Cliente;
import aps.unip.models.Conversa;
import aps.unip.models.Conversas;
import aps.unip.protocolo.Mensagem;
import aps.unip.server.conexao.ClienteConnection;

/*
 * Essa classe monta o frame que representa o chat com o contato.
 */
public class InternalFrameChat {
	int espassamento = -110;
	int alturaChat = 0;
	public static Integer idContato;
	static JTextArea panelChat;
	JButton btnSend;
	JTextField field;
	public static Integer idConversa;
	JScrollPane SpChatArea;
	
	public JInternalFrame internalFrame = new JInternalFrame();
	/*
	 * Construtor do internal frame Chat
	 */
	public InternalFrameChat(String nome, int idContato_, byte[] foto, int idConversa_) {
		idContato = idContato_;
		idConversa = idConversa_;
		internalFrame.setLocation(10, 10);
		internalFrame.setSize(470, 600);
		internalFrame.setVisible(true);
		internalFrame.setLayout(null);
		internalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		internalFrame.setClosable(true);
		internalFrame.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				internalFrame.setLocation(10, 10);
			}
		});
		internalFrame.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				idConversa = null;
			}
		});
		
		JPanel panelCabecalho = new JPanel();
		panelCabecalho.setLocation(0,0);
		panelCabecalho.setSize(470, 55);
		panelCabecalho.setOpaque(true);
		panelCabecalho.setLayout(null);
		panelCabecalho.setBackground(Color.DARK_GRAY);
		internalFrame.add(panelCabecalho);
		
		JLabel lblFoto = new JLabel();
		lblFoto.setSize(40,40);
		lblFoto.setLocation(10,10);
		if (foto != null) {
			lblFoto.setIcon(FrameUltils.redimensionarIcon(40, 40, foto));
		}
		else {
			lblFoto.setIcon(FrameUltils.redimensionarIcon(40, 40, "imgs/SemFoto.png"));
		}
		panelCabecalho.add(lblFoto);
		
		JLabel lblNomeValue = new JLabel(nome);
		lblNomeValue.setSize(120,15);
		lblNomeValue.setLocation(60,25);
		panelCabecalho.add(lblNomeValue);
		
		JPanel panelAdicionarContato = new JPanel();
		panelAdicionarContato.setSize(200,52);
		panelAdicionarContato.setLocation(190,2);
		panelAdicionarContato.setVisible(false);
		panelAdicionarContato.setLayout(null);
		Border border = BorderFactory.createBevelBorder(1);
		panelAdicionarContato.setBorder(border);
		panelAdicionarContato.setBackground(Color.DARK_GRAY);
		panelCabecalho.add(panelAdicionarContato);
		if (lblNomeValue.getText().equals("Desconhecido")) {
			panelAdicionarContato.setVisible(true);
		}
		
		JLabel lblFraseAdicionar = new JLabel("Adicionar esse contato?");
		lblFraseAdicionar.setSize(145,15);
		lblFraseAdicionar.setLocation(30,5);
		panelAdicionarContato.add(lblFraseAdicionar);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setSize(70,20);
		btnAdicionar.setLocation(60,25);
		btnAdicionar.setFocusable(false);
		panelAdicionarContato.add(btnAdicionar);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mensagem mensagem = new Mensagem();
				mensagem.setRequisicao(Requisicao.BUSCAR_UM_USUARIO);
				mensagem.setParametros("idusuario", idContato_);
				ClienteConnection.dispararRequisicao(mensagem);
			}
		});
		
		panelChat = new JTextArea();
		panelChat.setLayout(null);
		panelChat.setPreferredSize(new Dimension(450,430));
		montarChat(Cliente.getId(), idContato_);
		
		SpChatArea = new JScrollPane(panelChat);
		SpChatArea.setLocation(0,58);
		SpChatArea.setSize(470,458);
		internalFrame.add(SpChatArea);
		
		btnSend = new JButton();
		btnSend.setSize(24,20);
		btnSend.setLocation(425,535);
		btnSend.setVisible(false);
		btnSend.setFocusable(false);
		btnSend.setBorder(null);
		btnSend.setIcon(new ImageIcon("imgs/ImgButtonSend.png"));
		internalFrame.add(btnSend);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage(idContato_);
			}
		});
		
		field = new JTextField("");
		field.setSize(350,30);
		field.setLocation(55,530);
		field.setBorder(null);
		field.setBackground(new Color(51,56,59));
		internalFrame.add(field);
		field.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (field.getText().equals("")) {
					btnSend.setVisible(false);
				} else {
					btnSend.setVisible(true);
				}
			}
		});
		field.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!field.getText().equals("") && field.getText() != null )
					sendMessage(idContato_);
				}
			}
		});
		
		JLabel panelRodape = new JLabel();
		panelRodape.setLocation(0,518);
		panelRodape.setSize(465, 55);
		panelRodape.setVisible(true);
		panelRodape.setIcon(new ImageIcon("imgs/Rodape.png"));
		panelRodape.setBackground(Color.CYAN);
		internalFrame.add(panelRodape);
	}
	
	
	/*
	 * Metodo que encaminha a mensagem para um contato.
	 * @param idContato, Destinatário da mensagem.
	 */
	private void sendMessage(int idContato) {
		String msg = field.getText();
		
		Mensagem mensagem = new Mensagem();
		mensagem.setRequisicao(Requisicao.ENVIAR_MENSAGEM);
		mensagem.setParametros("destinatario_id",idContato);
		mensagem.setParametros("remetente_id", Cliente.getId());
		mensagem.setParametros("mensagem", msg);
		ClienteConnection.dispararRequisicao(mensagem);
		appendMensagemChat("Você", msg);
		
		SpChatArea.setVisible(false);
		SpChatArea.setVisible(true);
		field.setText("");
	}
	
	/*
	 * Busca as mensagens e monta elas no painel da conversa.
	 * @param idUsuario, ID do usuario que possui a conversa.
	 * @param idContato, ID do contato que pertence à conversa.
	 */
	private void montarChat(int idUsuario,int idContato) {
		ArrayList<Conversa> conversas = Conversas.getConversas();
		ArrayList<String> mensagens = new ArrayList<String>();
		for (Conversa conversa : conversas) {
			if (conversa.getIdContato() == idContato) {
				conversa.atualizarMensagens();
				mensagens = conversa.getMensagens();
			}
		}		
		for (int i = 0; i < mensagens.size(); i++) {			
			panelChat.append(mensagens.get(i));
		}
	}
	
	/*
	 * adiciona uma mensagem ao painel do chat e grava a mensagem no Banco de Dados.
	 * @param usuario_nome, Nome do usuario ou do remetente.
	 * @param mensagem, Mensagem que foi enviada ou recebida.
	 */
	public static void appendMensagemChat(String usuario_nome, String mensagem) {
		Date date = new Date();
		@SuppressWarnings("deprecation")
		String data = date.getHours()+":"+date.getMinutes();
		String mensagemString = (data + "  ("+usuario_nome+"): " + mensagem + "\n");
		DAOConversa conversa = new DAOConversa();
		conversa.addMensagem(mensagemString, idConversa);
		panelChat.append(mensagemString);
		
	}
}


