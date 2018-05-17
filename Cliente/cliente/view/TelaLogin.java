package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JPasswordField;

public class TelaLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public void create(ClientConexao conecta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin(conecta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaLogin(ClientConexao conecta) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {

				String email = textFieldEmail.getText();
				String senha = passwordField.getText();

				if (email.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campos obrigatorios nao preenchidos.");
				}
				if (senha.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campos obrigatorios nao preenchidos.");
				} else {
					String mensagem = "LOG/" + email + "/" + " " + "/" + senha;
					conecta.Envia(mensagem);
					
					TelaInicial telaInicial = new TelaInicial(conecta);
					telaInicial.show();
					dispose();
				}

			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEntrar.setBounds(307, 168, 105, 23);
		contentPane.add(btnEntrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(178, 168, 105, 23);
		contentPane.add(btnCancelar);

		JButton btnCadastrar = new JButton("Cadastrar-se");
		btnCadastrar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				TelaCadastro cadastro = new TelaCadastro(conecta);
				cadastro.show();
				dispose();
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCadastrar.setBounds(36, 168, 117, 23);
		contentPane.add(btnCadastrar);

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(127, 53, 285, 20);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmail.setBounds(36, 56, 61, 14);
		contentPane.add(lblEmail);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSenha.setBounds(36, 115, 61, 14);
		contentPane.add(lblSenha);

		passwordField = new JPasswordField();
		passwordField.setBounds(127, 114, 146, 20);
		contentPane.add(passwordField);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passwordField == null) ? 0 : passwordField.hashCode());
		result = prime * result + ((textFieldEmail == null) ? 0 : textFieldEmail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TelaLogin other = (TelaLogin) obj;
		if (passwordField == null) {
			if (other.passwordField != null)
				return false;
		} else if (!passwordField.equals(other.passwordField))
			return false;
		if (textFieldEmail == null) {
			if (other.textFieldEmail != null)
				return false;
		} else if (!textFieldEmail.equals(other.textFieldEmail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TelaLogin [textFieldEmail=" + textFieldEmail + ", passwordField=" + passwordField + "]";
	}
}
