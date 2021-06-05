package aps.unip.front;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import aps.unip.models.Cliente;
import aps.unip.models.Conversa;
import aps.unip.models.Conversas;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;

/*
 * Essa é a classe que monta o Frame principal.
 */
public class Front extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	static JPanel panelAdicionarContatoList;
	static Front front;
	static JScrollPane sPanelAdicionarContotoList;
	public static JDesktopPane desktopPaneConversa;

	/*
	 * Monta a lista de conversas no front.
	 */
	private static void montarListaDeConvercas() {

		ArrayList<Conversa> conversas = Conversas.getConversas();
		if (panelAdicionarContatoList != null) {
			panelAdicionarContatoList.removeAll();
		} else {
			return;
		}
		int y = 5;
		for (Conversa conversa : conversas) {
			JPanel jPanel = conversa.getPanelConversa();
			jPanel.setLocation(0, y);
			panelAdicionarContatoList.add(jPanel);
			panelAdicionarContatoList.validate();
			panelAdicionarContatoList.setPreferredSize(new Dimension(380, y + 65));
			y = y + 65;
		}
		sPanelAdicionarContotoList.setVisible(false);
		sPanelAdicionarContotoList.setVisible(true);
	}

	/*
	 * Inicia o frame Front.
	 */
	public static void initFront() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			new Front().setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Atualiza as conversas no frame Front.
	 */
	public static void atualizarListaConversas() {
		Conversas.atualizarConversas();
		montarListaDeConvercas();
	}
	
	/*
	 * Construtor do frame Front.
	 */
	@SuppressWarnings("static-access")
	public Front() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 695);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panelConversas = new JPanel();
		panelConversas.setBackground(Color.YELLOW);
		panelConversas.setBounds(10, 11, 357, 624);
		panel.add(panelConversas);
		panelConversas.setLayout(null);

		JPanel panelCabecalhoUsuario = new JPanel();
		panelCabecalhoUsuario.setBackground(Color.GRAY);
		panelCabecalhoUsuario.setBounds(0, 0, 357, 56);
		panelConversas.add(panelCabecalhoUsuario);
		panelCabecalhoUsuario.setLayout(null);

		JLabel lblFoto = new JLabel();
		lblFoto.setLocation(5, 5);
		lblFoto.setSize(45, 45);
		lblFoto.setIcon(FrameUltils.redimensionarIcon(45, 45, Cliente.getFoto()));
		panelCabecalhoUsuario.add(lblFoto);

		JLabel lblNome = new JLabel(Cliente.getNome());
		lblNome.setLocation(60, 15);
		lblNome.setSize(95, 20);
		panelCabecalhoUsuario.add(lblNome);

		JLabel btnAdicionarContato = new JLabel();
		btnAdicionarContato.setLocation(312, 11);
		btnAdicionarContato.setSize(35, 35);
		btnAdicionarContato.setBackground(new Color(0, 0, 0, 0));
		btnAdicionarContato.setBorder(null);
		btnAdicionarContato.setFocusable(false);
		btnAdicionarContato.setIcon(FrameUltils.redimensionarIcon(35, 35, "imgs/IconeAdicionarContato.png"));
		panelCabecalhoUsuario.add(btnAdicionarContato);
		btnAdicionarContato.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FrameAdicionarContato.initFrameAdicionarContato();
			}

			public void mouseEntered(MouseEvent e) {
				btnAdicionarContato
						.setIcon(FrameUltils.redimensionarIcon(35, 35, "imgs/IconeAdicionarContatoFocus.png"));
			}

			public void mouseExited(MouseEvent e) {
				btnAdicionarContato.setIcon(FrameUltils.redimensionarIcon(35, 35, "imgs/IconeAdicionarContato.png"));

			}
		});

		JLabel lblNovaConversa = new JLabel();
		lblNovaConversa.setSize(35, 35);
		lblNovaConversa.setLocation(270, 11);
		lblNovaConversa.setIcon(FrameUltils.redimensionarIcon(35, 35, "imgs/iconeNovaConversa.png"));
		panelCabecalhoUsuario.add(lblNovaConversa);
		lblNovaConversa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FrameNovaConverca.initFrameNovaConverca();
			}

			public void mouseEntered(MouseEvent e) {
				lblNovaConversa.setIcon(FrameUltils.redimensionarIcon(35, 35, "imgs/iconeNovaConversaFocus.png"));
			}

			public void mouseExited(MouseEvent e) {
				lblNovaConversa.setIcon(FrameUltils.redimensionarIcon(35, 35, "imgs/iconeNovaConversa.png"));
			}
		});

		JPanel panelBarraDePesquisa = new JPanel();
		panelBarraDePesquisa.setBackground(Color.darkGray);
		panelBarraDePesquisa.setLayout(null);
		panelBarraDePesquisa.setLocation(0, 55);
		panelBarraDePesquisa.setSize(357, 45);
		panelConversas.add(panelBarraDePesquisa);
		
		JLabel lblBarraDePesquisa = new JLabel();
		lblBarraDePesquisa.setLocation(3,5);
		lblBarraDePesquisa.setSize(350,40);
		lblBarraDePesquisa.setIcon(new ImageIcon("imgs/BarraDePesquisaContato.png"));
		lblBarraDePesquisa.setBackground(new Color(0,0,0,0));
		lblBarraDePesquisa.setOpaque(true);
		panelBarraDePesquisa.add(lblBarraDePesquisa);
		
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
			}
		});

		panelAdicionarContatoList = new JPanel();
		panelAdicionarContatoList.setLayout(null);
		panelAdicionarContatoList.setBackground(Color.darkGray);

		sPanelAdicionarContotoList = new JScrollPane(panelAdicionarContatoList);
		sPanelAdicionarContotoList.setHorizontalScrollBarPolicy(sPanelAdicionarContotoList.HORIZONTAL_SCROLLBAR_NEVER);
		sPanelAdicionarContotoList.setLocation(0, 100);
		sPanelAdicionarContotoList.setSize(357, 524);

		panelConversas.add(sPanelAdicionarContotoList);

		desktopPaneConversa = new JDesktopPane();
		desktopPaneConversa.setBounds(377, 11, 487, 624);
		panel.add(desktopPaneConversa);

		montarListaDeConvercas();
	}
}
