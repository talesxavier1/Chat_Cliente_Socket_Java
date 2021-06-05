package aps.unip.models;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aps.unip.DAOs.DAOContatos;
import aps.unip.DAOs.DAOConversa;
import aps.unip.front.FrameNovaConverca;
import aps.unip.front.FrameUltils;
import aps.unip.front.Front;
import aps.unip.front.InternalFrameChat;

/*
 * Classe que representa o contato.
 */
public class Contato {

	private String nome = "Desconhecido";
	private int id;
	private byte[] foto;
	private JPanel panelContato;

	public Contato(String nome, int id, byte[] foto) {
		this.nome = nome;
		this.id = id;
		this.foto = foto;
		construirJpanel();
	}

	/*
	 * Esse metodo constroi o Jpanel que vai aparecer no frame FrameNovaConverca.java
	 */
	private void construirJpanel() {
		JLabel lblNomeValue;
		if (nome != null) {
			lblNomeValue = new JLabel(nome);
		} else {
			lblNomeValue = new JLabel("Desconhecido");
		}
		JLabel btnIniciarConversa = new JLabel();
		JPanel panelContato = new JPanel();

		panelContato.setSize(380, 60);
		panelContato.setLayout(null);
		panelContato.setBackground(new Color(19, 28, 33));
		panelContato.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				panelContato.setBackground(new Color(50, 55, 57));
			}

			public void mouseExited(MouseEvent e) {
				panelContato.setBackground(new Color(19, 28, 33));
			}
		});

		JLabel lblFoto = new JLabel();
		lblFoto.setSize(40, 40);
		lblFoto.setLocation(10, 10);
		lblFoto.setOpaque(true);
		lblFoto.setBackground(Color.yellow);
		if (foto != null) {
			lblFoto.setIcon(FrameUltils.redimensionarIcon(40, 40, foto));
		} else {
			lblFoto.setIcon(FrameUltils.redimensionarIcon(40, 40, "imgs/SemFoto.png"));
		}
		panelContato.add(lblFoto);

		JLabel lblnome = new JLabel("Nome:");
		lblnome.setSize(40, 20);
		lblnome.setLocation(70, 20);
		panelContato.add(lblnome);

		lblNomeValue.setSize(200, 20);
		lblNomeValue.setLocation(110, 20);
		panelContato.add(lblNomeValue);

		btnIniciarConversa.setSize(30, 30);
		btnIniciarConversa.setLocation(300, 15);
		btnIniciarConversa.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/IconeIniciarConversa.png"));
		btnIniciarConversa.setBorder(null);
		btnIniciarConversa.setFocusable(false);
		btnIniciarConversa.addMouseListener(new MouseAdapter() {
			/*
			 * Essa action cria uma nova conversa com o contato.
			 */
			public void mouseClicked(MouseEvent e) {
				DAOConversa conversa = new DAOConversa();
				if (!conversa.incluirNovaConverca(Cliente.getId(), id)) {
					JOptionPane.showMessageDialog(null, "Você ja tem uma conversa com esse contato!");
				} else {
					Front.atualizarListaConversas();
				}
			}

			public void mouseEntered(MouseEvent e) {
				panelContato.setBackground(new Color(50, 55, 57));
				btnIniciarConversa.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/IconeIniciarConversaFocus.png"));
			}

			public void mouseExited(MouseEvent e) {
				panelContato.setBackground(new Color(19, 28, 33));
				btnIniciarConversa.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/IconeIniciarConversa.png"));
			}
		});
		panelContato.add(btnIniciarConversa);

		JLabel btnDeletarContato = new JLabel();
		btnDeletarContato.setSize(30, 30);
		btnDeletarContato.setLocation(345, 15);
		btnDeletarContato.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/iconDeletarFoto.png"));
		btnDeletarContato.setBorder(null);
		btnDeletarContato.setFocusable(false);
		panelContato.add(btnDeletarContato);
		btnDeletarContato.addMouseListener(new MouseAdapter() {
			/*
			 * Essa action deleta o contato e as conversas com esse contato.
			 */
			public void mouseClicked(MouseEvent e) {
				int resp = JOptionPane.showConfirmDialog(null, "Voce que excluir esse contato?");
				if (resp == 0) {
					DAOContatos DAOcontatos = new DAOContatos();
					DAOcontatos.deletarContato(Cliente.getId(), id);
					Contatos.setContatos(DAOcontatos.getContatos(Cliente.getId()));
					//
					Front.atualizarListaConversas();
					Contatos.atualizarContatos();
					if (InternalFrameChat.idContato != null && InternalFrameChat.idContato == id) {
						Front.desktopPaneConversa.removeAll();
						InternalFrameChat.idConversa = null;
						InternalFrameChat.idContato = null;
					}
					FrameNovaConverca.montarListaDeContatos();
					//
				}
			}

			public void mouseEntered(MouseEvent e) {
				panelContato.setBackground(new Color(50, 55, 57));
				btnDeletarContato.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/iconDeletarFotoFocus.png"));
			}

			public void mouseExited(MouseEvent e) {
				panelContato.setBackground(new Color(19, 28, 33));
				btnDeletarContato.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/iconDeletarFoto.png"));
			}
		});
		this.setPanelContato(panelContato);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public JPanel getPanelContato() {
		return panelContato;
	}

	public void setPanelContato(JPanel panelContato) {
		this.panelContato = panelContato;
	}
}
