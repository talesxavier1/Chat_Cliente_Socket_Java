package aps.unip.front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import aps.unip.models.Contato;
import aps.unip.models.Contatos;

/*
 * Essa classe monta o frame Nova Conversa.
 */
public class FrameNovaConverca extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JScrollPane sPanelAdicionarContotoList = null;
	private static JPanel panelAdicionarContatoList;
	
	/*
	 * Inicia o frame nova conversa.
	 */
	public static void initFrameNovaConverca() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			new FrameNovaConverca();
			try {
				Contatos.atualizarContatos();
				montarListaDeContatos();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Monta a lista de contatos.
	 */
	public static void montarListaDeContatos() {
		ArrayList<Contato> contatos = Contatos.getContatos();
		panelAdicionarContatoList.removeAll();
		if(contatos != null) {
			int y = 5;
			for (Contato contato : contatos) {
				if (!contato.getNome().equals("Desconhecido")) {
					JPanel jPanel = contato.getPanelContato();
					jPanel.setLocation(10,y);
					panelAdicionarContatoList.add(jPanel);
					panelAdicionarContatoList.validate();
					panelAdicionarContatoList.setPreferredSize(new Dimension(380,y+65));
					y = y + 65;
				}
			}
		}
			sPanelAdicionarContotoList.setVisible(false);
			sPanelAdicionarContotoList.setVisible(true);
	}
	
	
	/*
	 * Construtor do frame.
	 */
	public FrameNovaConverca() {
		
		JFrame frameBuscarContatos = new JFrame();
		frameBuscarContatos.setSize(410,300);
		frameBuscarContatos.setDefaultCloseOperation(FrameNovaConverca.DISPOSE_ON_CLOSE);
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
