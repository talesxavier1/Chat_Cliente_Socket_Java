package aps.unip.front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import aps.unip.DAOs.DAOContatos;
import aps.unip.enums.Requisicao;
import aps.unip.models.Cliente;
import aps.unip.protocolo.Mensagem;
import aps.unip.server.conexao.ClienteConnection;

/*
 * Essa classe monta o Frame Para adicionar um contato.
 */
public class FrameAdicionarContato extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JScrollPane sPanelAdicionarContotoList = null;
	private static JPanel panelAdicionarContatoList;
	
	/*
	 * Inicia o frame adicionar contatos.
	 */
	public static void initFrameAdicionarContato() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			new FrameAdicionarContato();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Monta a lista de contatos.
	 * @param contatos
	 * 			contatos[linha][0], consta o id do contato.
	 * 			contatos[linha][1], consta o nome do contato.
	 *			contatos[linha][2], consta a foto do usuario.
	 */
	public static void montarListaDeContatos(Object[][] contatos) {

			panelAdicionarContatoList.removeAll();
			int y = 5;
			for (int i = 0; i < contatos.length; i++) {
				JLabel lblFoto = new JLabel();
				JLabel lblNomeValue = new JLabel(""+contatos[i][1]);
				JLabel lblId = new JLabel(""+contatos[i][0]);
				JLabel btnAdicionar = new JLabel();
				JPanel panelContato = new JPanel();
				
				panelContato.setSize(380,60);
				panelContato.setLocation(0,y);
				panelContato.setLayout(null);
				panelContato.setBackground(new Color(19,28,33));
				panelContato.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent e) {
						panelContato.setBackground(new Color(50,55,57));
					}
					public void mouseExited(MouseEvent e) {
						panelContato.setBackground(new Color(19,28,33));
					}
				});
				
				lblFoto.setSize(40,40);
				lblFoto.setLocation(10,10);
				lblFoto.setOpaque(true);
				lblFoto.setBackground(Color.yellow);
				byte[] foto = (byte[]) contatos[i][2];
				if (foto != null) {
					lblFoto.setIcon(FrameUltils.redimensionarIcon(40, 40, foto));
				}
				else {
					lblFoto.setIcon(FrameUltils.redimensionarIcon(40, 40, "imgs/SemFoto.png"));
				}
				panelContato.add(lblFoto);
	
				JLabel lblnome = new JLabel("Nome:");
				lblnome.setSize(40,20);
				lblnome.setLocation(70,20);
				panelContato.add(lblnome);
				
				lblNomeValue.setSize(200,20);
				lblNomeValue.setLocation(110,20);
				panelContato.add(lblNomeValue);
				panelContato.add(lblId);
				
				btnAdicionar.setSize(30,30);
				btnAdicionar.setLocation(345,15);
				btnAdicionar.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/GifAdicionarContato.png"));
				btnAdicionar.setBorder(null);
				btnAdicionar.setFocusable(false);
				btnAdicionar.addMouseListener(new MouseAdapter() {
				/*
				 * Esse evento chama o metodo de verificar contato da classe DAOContatos e adiciona o contato, caso ele nao
				 * esteja na tabela contatos.
				 */
				public void mouseClicked(MouseEvent e) {
					for (int j = 0; j < contatos.length; j++) {
						if(Integer.parseInt((String) contatos[j][0]) == Integer.parseInt(lblId.getText())) {
							DAOContatos daocontatos = new DAOContatos();
							if(!daocontatos.verificarContato(Integer.parseInt(lblId.getText()),Cliente.getId())) {
								daocontatos.AdicionarContato(
										Integer.parseInt(lblId.getText()), 
										lblNomeValue.getText(), 
										(byte[]) contatos[j][2], 
										Cliente.getId());
							}
							break;
						}
					}
				}
				public void mouseEntered(MouseEvent e) {
					panelContato.setBackground(new Color(50,55,57));
					btnAdicionar.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/GifAdicionarContatoFocus.png"));
				}
				public void mouseExited(MouseEvent e) {
					panelContato.setBackground(new Color(19,28,33));
					btnAdicionar.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/GifAdicionarContato.png"));
				}
				});
				panelContato.add(btnAdicionar);
				

				panelAdicionarContatoList.add(panelContato);
				panelAdicionarContatoList.validate();
				panelAdicionarContatoList.setPreferredSize(new Dimension(380,y+65));

				y = y + 65;
			}
			sPanelAdicionarContotoList.setVisible(false);
			sPanelAdicionarContotoList.setVisible(true);
	}
	
	/*
	 * Construtor do frame.
	 */
	public FrameAdicionarContato() {
		
		JFrame frameBuscarContatos = new JFrame();
		frameBuscarContatos.setSize(410,300);
		frameBuscarContatos.setDefaultCloseOperation(FrameAdicionarContato.DISPOSE_ON_CLOSE);
		frameBuscarContatos.setVisible(true);
		frameBuscarContatos.setLayout(null);
		frameBuscarContatos.setLocationRelativeTo(null);
		
		JPanel panelBarraDeBusca = new JPanel();
		panelBarraDeBusca.setLocation(0,0);
		panelBarraDeBusca.setSize(400,50);
		panelBarraDeBusca.setBackground(Color.darkGray);
		frameBuscarContatos.add(panelBarraDeBusca);
		panelBarraDeBusca.setLayout(null);
		
		JTextField txtBuscaDeUsuario = new JTextField();
		txtBuscaDeUsuario.setSize(300,20);
		txtBuscaDeUsuario.setLocation(60,15);
		txtBuscaDeUsuario.setEditable(true);
		txtBuscaDeUsuario.setBorder(null);
		panelBarraDeBusca.add(txtBuscaDeUsuario);
		
		JLabel lblBarraDePesquisa = new JLabel();
		lblBarraDePesquisa.setLocation(20,5);
		lblBarraDePesquisa.setSize(350,40);
		lblBarraDePesquisa.setIcon(new ImageIcon("imgs/BarraDePesquisaContato.png"));
		lblBarraDePesquisa.setBackground(new Color(0,0,0,0));
		lblBarraDePesquisa.setOpaque(true);
		panelBarraDeBusca.add(lblBarraDePesquisa);
		
		JButton lblLupa = new JButton();
		lblLupa.setFocusPainted(false);
		lblLupa.setSize(20,20);
		lblLupa.setLocation(10,10);
		lblLupa.setOpaque(true);
		lblLupa.setBorder(null);
		lblLupa.setFocusable(false);
		lblLupa.setIcon(FrameUltils.redimensionarIcon(20, 20, "imgs/lupa.png"));
		lblBarraDePesquisa.add(lblLupa);
		lblLupa.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent f) {
        	lblLupa.setIcon(FrameUltils.redimensionarIcon(20, 20, "imgs/lupaFocus.png"));
        }
		public void mouseExited(MouseEvent f) {
			lblLupa.setIcon(FrameUltils.redimensionarIcon(20, 20, "imgs/lupa.png"));
		}});
		lblLupa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mensagem mensagem = new Mensagem();
				mensagem.setRequisicao(Requisicao.BUSCAR_USUARIO);
				mensagem.setParametros("nome", txtBuscaDeUsuario.getText());
				ClienteConnection.dispararRequisicao(mensagem);
			}
		});
		
		panelAdicionarContatoList = new JPanel();
		panelAdicionarContatoList.setLayout(null);
		panelAdicionarContatoList.setBackground(Color.darkGray);
		
		sPanelAdicionarContotoList = new JScrollPane(panelAdicionarContatoList);
		sPanelAdicionarContotoList.setLocation(0,50);
		sPanelAdicionarContotoList.setSize(400,220);

		frameBuscarContatos.add(sPanelAdicionarContotoList);
	
	}
}
