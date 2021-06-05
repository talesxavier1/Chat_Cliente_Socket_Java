package aps.unip.models;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import aps.unip.DAOs.DAOContatos;
import aps.unip.DAOs.DAOConversa;
import aps.unip.front.FrameUltils;
import aps.unip.front.Front;
import aps.unip.front.InternalFrameChat;

/*
 * Essa classe representa as conversas do Cliente.
 */
public class Conversa {

	private Contato contato;
	private int idConversa;
	private ArrayList<String> mensagens = new ArrayList<String>();
	private JPanel panelConversa;

	public Conversa(Contato contato, int idConversa) {
		this.contato = contato;
		this.setIdConversa(idConversa);
		construitJPanel();
	}
	/*
	 * as mensagens da conversa.
	 */
	public void atualizarMensagens() {
		DAOConversa conversa = new DAOConversa();
		mensagens = conversa.getMensagens(Cliente.getId(), contato.getId());
	}

	private void construitJPanel() {
		JLabel lblFoto = new JLabel();
		JLabel lblNomeValue = new JLabel(contato.getNome());
		JPanel panelContato = new JPanel();
		JLabel lblBtnExcluirConversa = new JLabel();
		JLabel lblIconeNovaMensagem = new JLabel();

		panelContato.setSize(355, 60);
		panelContato.setLayout(null);
		panelContato.setBackground(new Color(19, 28, 33));
		panelContato.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				panelContato.setBackground(new Color(50, 55, 57));
				lblBtnExcluirConversa.setBackground(new Color(50, 55, 57));
				lblIconeNovaMensagem.setBackground(new Color(50, 55, 57));
			}

			public void mouseExited(MouseEvent e) {
				panelContato.setBackground(new Color(19, 28, 33));
				lblBtnExcluirConversa.setBackground(new Color(19, 28, 33));
				lblIconeNovaMensagem.setBackground(new Color(19, 28, 33));
			}

			/*
			 * Essa action inicia o JinternalFrame da conversa.
			 */
			public void mouseClicked(MouseEvent e) {
				Front.desktopPaneConversa.removeAll();
				InternalFrameChat.idConversa = null;
				Front.desktopPaneConversa.add(new InternalFrameChat(contato.getNome(), contato.getId(),
						contato.getFoto(), idConversa).internalFrame);
				lblIconeNovaMensagem.setVisible(false);
			}
		});

		lblFoto.setSize(40, 40);
		lblFoto.setLocation(10, 10);
		lblFoto.setName("lblFoto");
		lblFoto.setOpaque(true);
		lblFoto.setBackground(Color.yellow);
		byte[] foto = contato.getFoto();
		if (foto != null) {
			lblFoto.setIcon(FrameUltils.redimensionarIcon(40, 40, foto));
		} else {
			lblFoto.setIcon(FrameUltils.redimensionarIcon(40, 40, "imgs/SemFoto.png"));
		}
		panelContato.add(lblFoto);

		JLabel lblnome = new JLabel("Nome:");
		lblnome.setSize(40, 20);
		lblnome.setName("lblnome");
		lblnome.setLocation(70, 20);
		panelContato.add(lblnome);

		lblNomeValue.setSize(200, 20);
		lblNomeValue.setName("lblNomeValue");
		lblNomeValue.setLocation(110, 20);
		panelContato.add(lblNomeValue);

		lblIconeNovaMensagem.setSize(18, 12);
		lblIconeNovaMensagem.setLocation(280, 25);
		lblIconeNovaMensagem.setName("lblIconeNovaMensagem");
		lblIconeNovaMensagem.setOpaque(true);
		lblIconeNovaMensagem.setVisible(false);
		lblIconeNovaMensagem.setBackground(new Color(19, 28, 33));
		lblIconeNovaMensagem.setIcon(new ImageIcon("imgs/GifNovaMensagem.gif"));
		panelContato.add(lblIconeNovaMensagem);

		lblBtnExcluirConversa.setSize(30, 30);
		lblBtnExcluirConversa.setName("lblBtnExcluirConversa");
		lblBtnExcluirConversa.setLocation(315, 15);
		lblBtnExcluirConversa.setOpaque(true);
		lblBtnExcluirConversa.setBackground(new Color(0, 0, 0, 0));
		lblBtnExcluirConversa.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/iconDeletarFoto.png"));
		panelContato.add(lblBtnExcluirConversa);
		lblBtnExcluirConversa.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				lblBtnExcluirConversa.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/iconDeletarFotoFocus.png"));
			}

			public void mouseExited(MouseEvent e) {
				lblBtnExcluirConversa.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/iconDeletarFoto.png"));
			}
			/*
			 * Essa action deleta a conversa com o contato.
			 */
			public void mouseClicked(MouseEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "Deletar A conversa com " + lblNomeValue.getText());
				if (confirm == 0) {
					if (contato.getNome().equals("Desconhecido")) {
						
						DAOConversa conversa = new DAOConversa();
						DAOContatos contatos = new DAOContatos();
						conversa.deletarConversa(contato.getId(), Cliente.getId());
						contatos.deletarNaoContato(Cliente.getId(), contato.getId(), true);
						Contatos.atualizarContatos();
						Conversas.atualizarConversas();
						Front.atualizarListaConversas();
						if (InternalFrameChat.idConversa != null && InternalFrameChat.idConversa == idConversa) {
							Front.desktopPaneConversa.removeAll();
							Front.desktopPaneConversa.setVisible(false);
							Front.desktopPaneConversa.setVisible(true);
							InternalFrameChat.idConversa = null;
							InternalFrameChat.idContato = null;
						}
					} else {
						DAOConversa conversa = new DAOConversa();
						conversa.deletarConversa(contato.getId(), Cliente.getId());
						Front.atualizarListaConversas();
						if (InternalFrameChat.idConversa != null && InternalFrameChat.idConversa == idConversa) {
							Front.desktopPaneConversa.removeAll();
							Front.desktopPaneConversa.setVisible(false);
							Front.desktopPaneConversa.setVisible(true);
							InternalFrameChat.idConversa = null;
							Front.desktopPaneConversa.validate();
							InternalFrameChat.idContato = null;
						}

					}
				}
			}
		});
		panelConversa = panelContato;
	}

	public int getIdContato() {
		return contato.getId();
	}

	public void addMensagem(String mensagem) {
		mensagens.add(mensagem);
	}

	public ArrayList<String> getMensagens() {
		return mensagens;
	}

	public void setMensagens(ArrayList<String> mensagens) {
		this.mensagens = mensagens;
	}

	public int getIdConversa() {
		return idConversa;
	}

	public void setIdConversa(int idConversa) {
		this.idConversa = idConversa;
	}

	public JPanel getPanelConversa() {
		return panelConversa;
	}

	public void setPanelConversa(JPanel panelConversa) {
		this.panelConversa = panelConversa;
	}

	public void putMensagem(String mensagem) {
		mensagens.add(mensagem);
	}
	
	/*
	 * Esse metodo deixa o lblIconeNovaMensagem visivel.
	 * Esse label é um gif que aparece quando uma nova mensagem chega.  
	 */
	public void setNewMessageIcon() {
		Component[] comp = panelConversa.getComponents();
		for (int i = 0; i < comp.length; i++) {
			if (comp[i].getName().equals("lblIconeNovaMensagem")) {
				panelConversa.getComponent(i).setVisible(true);
			}
		}
	}
}
