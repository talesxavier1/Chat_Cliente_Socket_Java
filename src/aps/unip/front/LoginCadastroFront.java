package aps.unip.front;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import aps.unip.server.conexao.ClienteConnection;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/*
 * Essa classe monta o frame de login e de cadastro.
 */
public class LoginCadastroFront extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JLabel lblGifLoadingLogin;
	private JPanel contentPane,
				   panelContainerCadastro,
	               panelContainerLogin;
	private static JPanel panelContainerLoginElements;
	private JTextField textLoginEmail,
	                   textLoginPassword,
	                   txtCadastroEmail,
	                   txtCadastroNome,
	                   txtCadastroApelido,
	                   txtCadastroSenha,
	                   txtCadastroSenhaConfirme;
	byte[] foto;
	static LoginCadastroFront loginCadastroFront;

	/*
	 * Inicia o Frame de login.
	 */
	public static void initLoginFrame() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			loginCadastroFront = new LoginCadastroFront();
			loginCadastroFront.setVisible(true);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Fecha o frame de Login.
	 */
	public static void closeLoginFrame() {
		loginCadastroFront.dispose();
	}
	
	/*
	 * Construtor do frame de login e de cadastro.
	 */
	public LoginCadastroFront() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 260);
		setLocation(100, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		panelContainerLogin = new JPanel();
		panelContainerLogin.setVisible(true);
		panelContainerLogin.setBackground(Color.DARK_GRAY);
		panelContainerLogin.setSize(240, 200);
		panelContainerLogin.setLocation(25,15);
		contentPane.add(panelContainerLogin);
		panelContainerLogin.setLayout(null);
		
		
		JLabel lblTitleLogin = new JLabel("Login");
		lblTitleLogin.setVisible(true);
		lblTitleLogin.setSize(220, 44);
		lblTitleLogin.setLocation(10, 10);
		lblTitleLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleLogin.setFont(new Font("Segoe UI Black", Font.PLAIN, 32));
		lblTitleLogin.setAlignmentY(0.0f);
		lblTitleLogin.setAlignmentX(1.0f);
		panelContainerLogin.add(lblTitleLogin);
		
		lblGifLoadingLogin = new JLabel();
		lblGifLoadingLogin.setSize(240,201);
		lblGifLoadingLogin.setLocation(0,0);
		lblGifLoadingLogin.setIcon(new ImageIcon("imgs/Loading.gif"));
		lblGifLoadingLogin.setVisible(false);
		panelContainerLogin.add(lblGifLoadingLogin);
		
		panelContainerLoginElements = new JPanel();
		panelContainerLoginElements.setVisible(true);
		panelContainerLoginElements.setSize(220, 120);
		panelContainerLoginElements.setLocation(10, 65);
		panelContainerLogin.add(panelContainerLoginElements);
		panelContainerLoginElements.setLayout(null);
		
		JLabel lblUserTitleEmail = new JLabel("Email:");
		lblUserTitleEmail.setVisible(true);
		lblUserTitleEmail.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
		lblUserTitleEmail.setSize(58, 22);
		lblUserTitleEmail.setLocation(10, 10);
		panelContainerLoginElements.add(lblUserTitleEmail);
		
		JLabel lblUserPasswordLogin = new JLabel("Senha:");
		lblUserPasswordLogin.setVisible(true);
		lblUserPasswordLogin.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
		lblUserPasswordLogin.setSize(58, 22);
		lblUserPasswordLogin.setLocation(10, 44);
		panelContainerLoginElements.add(lblUserPasswordLogin);
		
		textLoginEmail = new JTextField();
		textLoginEmail.setVisible(true);
		textLoginEmail.setLocation(78, 14);
		textLoginEmail.setSize(100, 20);
		panelContainerLoginElements.add(textLoginEmail);
		textLoginEmail.setColumns(10);
		textLoginEmail.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!textLoginPassword.getText().equals("") && textLoginPassword.getText() != null ) {
						if (!textLoginEmail.getText().equals("") && textLoginEmail.getText() != null) {
							logar();
						}else {
							JOptionPane.showMessageDialog(null, "Digite um email valido!");
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "Digite uma senha valida");
					}
				}
			}});
		
		textLoginPassword = new JTextField();
		textLoginPassword.setVisible(true);
		textLoginPassword.setSize(100, 20);
		textLoginPassword.setLocation(78, 47);
		panelContainerLoginElements.add(textLoginPassword);
		textLoginPassword.setColumns(10);
		textLoginPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!textLoginPassword.getText().equals("") && textLoginPassword.getText() != null ) {
						if (!textLoginEmail.getText().equals("") && textLoginEmail.getText() != null) {
							logar();
						}else {
							JOptionPane.showMessageDialog(null, "Digite um email valido!");
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "Digite uma senha valida");
					}
				}
			}});
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setVisible(true);
		btnLogin.setSize(89, 23);
		btnLogin.setLocation(121, 86);
		btnLogin.setFocusable(false);
		panelContainerLoginElements.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			/*
			 * Chama o metodo de login.
			 */
			public void actionPerformed(ActionEvent e) {
				setDisableCompLogin();
				logar();
			}});

		
		JButton btnCadastrarLogin = new JButton("Cadastrar");
		btnCadastrarLogin.setVisible(true);
		btnCadastrarLogin.setSize(89, 23);
		btnCadastrarLogin.setLocation(10, 86);
		btnCadastrarLogin.setFocusable(false);
		panelContainerLoginElements.add(btnCadastrarLogin);
		btnCadastrarLogin.addActionListener(new ActionListener() {
			/*
			 * Muda para o frame de cadastro.
			 */
			public void actionPerformed(ActionEvent arg0) {
				cadastroLayout();
			}
		});
		
		///------------------- Cadastro -----------------------------///
		
		panelContainerCadastro = new JPanel();
		panelContainerCadastro.setVisible(false);
		panelContainerCadastro.setLayout(null);
		panelContainerCadastro.setBackground(Color.DARK_GRAY);
		panelContainerCadastro.setLocation(20,20);
		panelContainerCadastro.setSize(465,255);
		getContentPane().add(panelContainerCadastro);
		
		JLabel lblTitleCadastro = new JLabel("Cadastrar");
		lblTitleCadastro.setVisible(true);
		lblTitleCadastro.setSize(300, 44);
		lblTitleCadastro.setLocation(165, 10);
		lblTitleCadastro.setFont(new Font("Segoe UI Black", Font.PLAIN, 32));
		lblTitleCadastro.setAlignmentY(0.0f);
		lblTitleCadastro.setAlignmentX(1.0f);
		panelContainerCadastro.add(lblTitleCadastro);
		
		JPanel panelContainerCadastroElements = new JPanel();
		panelContainerCadastroElements.setVisible(true);
		panelContainerCadastroElements.setSize(425, 170);
		panelContainerCadastroElements.setLocation(20, 65);
		panelContainerCadastroElements.setLayout(null);
		panelContainerCadastro.add(panelContainerCadastroElements);
		
		JLabel lblCadastroNome = new JLabel("Nome:");
		lblCadastroNome.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
		lblCadastroNome.setSize(50,15);
		lblCadastroNome.setLocation(10,15);
		panelContainerCadastroElements.add(lblCadastroNome);
		
		txtCadastroNome = new JTextField();
		txtCadastroNome.setSize(170,20);
		txtCadastroNome.setLocation(60,10);
		panelContainerCadastroElements.add(txtCadastroNome);
		

		JLabel lblCadastroEmail = new JLabel("Email:");
		lblCadastroEmail.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
		lblCadastroEmail.setSize(50,15);
		lblCadastroEmail.setLocation(10,45);
		panelContainerCadastroElements.add(lblCadastroEmail);
		
		txtCadastroEmail = new JTextField();
		txtCadastroEmail.setSize(170,20);
		txtCadastroEmail.setLocation(60,40);
		panelContainerCadastroElements.add(txtCadastroEmail);

		JLabel lblCadastroSenha = new JLabel("Senha:");
		lblCadastroSenha.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
		lblCadastroSenha.setSize(50,15);
		lblCadastroSenha.setLocation(10,75);
		panelContainerCadastroElements.add(lblCadastroSenha);
		
		txtCadastroSenha = new JTextField();
		txtCadastroSenha.setSize(165,20);
		txtCadastroSenha.setLocation(65,70);
		panelContainerCadastroElements.add(txtCadastroSenha);
		
		JLabel lblCadastroSenhaConfirme = new JLabel("Senha:");
		lblCadastroSenhaConfirme.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
		lblCadastroSenhaConfirme.setSize(50,15);
		lblCadastroSenhaConfirme.setLocation(10,105);
		panelContainerCadastroElements.add(lblCadastroSenhaConfirme);
		
		txtCadastroSenhaConfirme = new JTextField();
		txtCadastroSenhaConfirme.setSize(165,20);
		txtCadastroSenhaConfirme.setLocation(65,100);
		panelContainerCadastroElements.add(txtCadastroSenhaConfirme);
		
		setFotoPadrao();
		
		JPanel panelFotoArea = new JPanel();
		panelFotoArea.setSize(180,145);
		panelFotoArea.setLocation(245,10);
		panelFotoArea.setLayout(null);
		panelFotoArea.setBackground(new Color(0,0,0,0));
		panelContainerCadastroElements.add(panelFotoArea);
		
		JLabel lblFoto = new JLabel();
		lblFoto.setSize(125,125);
		lblFoto.setLocation(10,10);
		lblFoto.setOpaque(true);
		lblFoto.setBackground(Color.yellow);
		lblFoto.setIcon(FrameUltils.redimensionarIcon(125, 125, foto));
		panelFotoArea.add(lblFoto);
		
		JButton btnRemoverFoto = new JButton();
		btnRemoverFoto.setSize(30,30);
		btnRemoverFoto.setLocation(140,10);
		btnRemoverFoto.setFocusable(false);
		btnRemoverFoto.setIcon(FrameUltils.redimensionarIcon(30, 30, "imgs/iconDeletarFoto.png"));
		panelFotoArea.add(btnRemoverFoto);
		btnRemoverFoto.addActionListener(new ActionListener() {
			/*
			 * Remove a foto que foi selecionada e coloca uma foto padrão.
			 */
			public void actionPerformed(ActionEvent arg0) {
				lblFoto.setIcon(FrameUltils.redimensionarIcon(125, 125, "imgs/SemFoto.png"));
				setFotoPadrao();
			}
		});
		
		JButton btnAdicionarFoto = new JButton();
		btnAdicionarFoto.setSize(30,30);
		btnAdicionarFoto.setLocation(140,45);
		btnAdicionarFoto.setFocusable(false);
		btnAdicionarFoto.setIcon(FrameUltils.redimensionarIcon(25, 25, "imgs/iconProcurarFoto.png"));
		panelFotoArea.add(btnAdicionarFoto);
		btnAdicionarFoto.addActionListener(new ActionListener() {
			/*
			 * Essa action inicia o explorador de arquivos para selecionar uma foto.
			 */
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(0);
					fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "bmp", "png", "jpg"));

					if (fileChooser.showOpenDialog(loginCadastroFront) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile().getAbsoluteFile();
						byte[] fileFotoBytes = FrameUltils.imageToBytes(file, "jpg");
						foto = fileFotoBytes;
						lblFoto.setIcon(FrameUltils.redimensionarIcon(125, 125, file));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnCadastroCancelar = new JButton("Cancelar");
		btnCadastroCancelar.setLocation(10, 135);
		btnCadastroCancelar.setSize(89, 23);
		btnCadastroCancelar.setFocusable(false);
		panelContainerCadastroElements.add(btnCadastroCancelar);
		btnCadastroCancelar.addActionListener(new ActionListener() {
			/*
			 * Volta para o Frame Login.
			 */
			public void actionPerformed(ActionEvent arg0) {
				loginLayout();
			}});
		
		JButton btnCadastroCadastrar = new JButton("Cadastrar");
		btnCadastroCadastrar.setLocation(141, 135);
		btnCadastroCadastrar.setSize(89, 23);
		btnCadastroCadastrar.setFocusable(false);
		panelContainerCadastroElements.add(btnCadastroCadastrar);
		btnCadastroCadastrar.addActionListener(new ActionListener() {
			/*
			 * Essa action conecta o cliente com o servidor para fazer o cadastro.
			 */
			public void actionPerformed(ActionEvent e) {
				if (txtCadastroSenha.getText().equals(txtCadastroSenhaConfirme.getText())) {
					ClienteConnection.conectar();
					ClienteConnection.Cadastrar(txtCadastroNome.getText(),txtCadastroEmail.getText(),txtCadastroSenha.getText(),foto);
					loginLayout();
				}
				
			}});
	}
	
	/*
	 * Desabilita todos os componentes do frame login.
	 */
	private void setDisableCompLogin() {
		for (Component component : panelContainerLoginElements.getComponents()) {
			component.setEnabled(false);
		}
		lblGifLoadingLogin.setVisible(true);
		
	}
	
	/*
	 * Conecta com o servidor e manda a requisição de login.
	 */
	private void logar() {
		new Thread() {
			@Override
			public void run() {
				ClienteConnection.conectar();
				ClienteConnection.login(textLoginEmail.getText(), textLoginPassword.getText());
			}
		}.start();
	}
	
	/*
	 * Habilita os componentes do frame login.
	 */
	public static void setEnableCompLogin() {
		for (Component component : panelContainerLoginElements.getComponents()) {
			component.setEnabled(true);
		}
		lblGifLoadingLogin.setVisible(false);
	}
	
	/*
	 * Muda para o frame de cadastro.
	 */
	private void cadastroLayout() {
		this.setSize(510, 330);
		panelContainerLogin.setVisible(false);
		panelContainerCadastro.setVisible(true);
	}
	
	/*
	 * Muda para o frame de login.
	 */
	private void loginLayout() {
		this.setSize(300, 260);
		panelContainerLogin.setVisible(true);
		panelContainerCadastro.setVisible(false);
	}
	
	/*
	 * Seta a foto padrão.
	 */
	private void setFotoPadrao() {
		File file = new File("imgs/SemFoto.png");
		foto = FrameUltils.imageToBytes(file, "jpg");
	}
	
	public JTextField getTextLoginUser() {
		return textLoginEmail;
	}

	public JTextField getTextLoginPassword() {
		return textLoginPassword;
	}

	public JTextField getTxtCadastroNome() {
		return txtCadastroEmail;
	}

	public JTextField getTxtCadastroApelido() {
		return txtCadastroApelido;
	}

	public JTextField getTxtCadastroSenha() {
		return txtCadastroSenha;
	}

	public JTextField getTxtCadastroSenhaConfirme() {
		return txtCadastroSenhaConfirme;
	}
}