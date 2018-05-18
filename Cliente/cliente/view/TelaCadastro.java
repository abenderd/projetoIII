package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class TelaCadastro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirmacao;

	/**
	 * Launch the application.
	 */

	public void create() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
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
	public TelaCadastro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				String nome = textFieldNome.getText();
				String email = textFieldEmail.getText();
				String senha = passwordField.getText();
				String confirmacaoSenha = passwordFieldConfirmacao.getText();

				if (nome.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo nome deve ser preenchido.");
				} else if (email.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo email deve ser preenchido.");
				} else if (validaEmail(email) == false) {
					JOptionPane.showMessageDialog(null, "Email invalido.");
				} else if (senha.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo senha deve ser preenchido.");
				} else if (confirmacaoSenha.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo confirma��o de senha deve ser preenchido.");
				} else if (senha.equals(confirmacaoSenha)) {

					try {
						String ipServidor = new TelaConexaoCliente().conexao();

						String mensagem = "CAD/" + email + "/" + nome + "/" + senha;

						ClientConexao c = new ClientConexao(ipServidor);
						c.Envia(mensagem);
					} catch (Exception erro) {
						System.out.println(erro);
					}

					TelaLogin login = new TelaLogin();
					login.show();
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Senha e confirma��o de senha n�o conferem.");
				}
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCadastrar.setBounds(336, 159, 108, 23);
		contentPane.add(btnCadastrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				TelaLogin login = new TelaLogin();
				login.show();
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(135, 159, 108, 23);
		contentPane.add(btnCancelar);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNome.setBounds(40, 38, 66, 14);
		contentPane.add(lblNome);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEmail.setBounds(40, 76, 66, 14);
		contentPane.add(lblEmail);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSenha.setBounds(40, 116, 66, 14);
		contentPane.add(lblSenha);

		JLabel lblConfirmaSenha = new JLabel("Confirma\u00E7\u00E3o:");
		lblConfirmaSenha.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblConfirmaSenha.setBounds(253, 116, 91, 14);
		contentPane.add(lblConfirmaSenha);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(97, 37, 405, 20);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);

		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(97, 75, 405, 20);
		contentPane.add(textFieldEmail);

		passwordField = new JPasswordField();
		passwordField.setBounds(97, 114, 146, 20);
		contentPane.add(passwordField);

		passwordFieldConfirmacao = new JPasswordField();
		passwordFieldConfirmacao.setBounds(354, 114, 148, 20);
		contentPane.add(passwordFieldConfirmacao);
	}

	public boolean validaEmail(String email) {
		{
			boolean isEmailIdValid = false;
			if (email != null && email.length() > 0) {
				String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
				Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(email);
				if (matcher.matches()) {
					isEmailIdValid = true;
				}
			}
			return isEmailIdValid;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passwordField == null) ? 0 : passwordField.hashCode());
		result = prime * result + ((passwordFieldConfirmacao == null) ? 0 : passwordFieldConfirmacao.hashCode());
		result = prime * result + ((textFieldEmail == null) ? 0 : textFieldEmail.hashCode());
		result = prime * result + ((textFieldNome == null) ? 0 : textFieldNome.hashCode());
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
		TelaCadastro other = (TelaCadastro) obj;
		if (passwordField == null) {
			if (other.passwordField != null)
				return false;
		} else if (!passwordField.equals(other.passwordField))
			return false;
		if (passwordFieldConfirmacao == null) {
			if (other.passwordFieldConfirmacao != null)
				return false;
		} else if (!passwordFieldConfirmacao.equals(other.passwordFieldConfirmacao))
			return false;
		if (textFieldEmail == null) {
			if (other.textFieldEmail != null)
				return false;
		} else if (!textFieldEmail.equals(other.textFieldEmail))
			return false;
		if (textFieldNome == null) {
			if (other.textFieldNome != null)
				return false;
		} else if (!textFieldNome.equals(other.textFieldNome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TelaCadastro [textFieldNome=" + textFieldNome + ", textFieldEmail=" + textFieldEmail
				+ ", passwordField=" + passwordField + ", passwordFieldConfirmacao=" + passwordFieldConfirmacao + "]";
	}
}
