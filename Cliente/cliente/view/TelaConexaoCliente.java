package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class TelaConexaoCliente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldIpServidor;
	private ClientConexao conecta;

	/**
	 * Launch the application.
	 */

	public void create() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConexaoCliente frame = new TelaConexaoCliente();
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
	public TelaConexaoCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 372, 147);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldIpServidor = new JTextField();
		textFieldIpServidor.setText("127.0.0.1");
		textFieldIpServidor.setBounds(166, 29, 147, 20);
		contentPane.add(textFieldIpServidor);
		textFieldIpServidor.setColumns(10);

		JLabel lblIpServidor = new JLabel("Informe o IP do Servidor:");
		lblIpServidor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIpServidor.setBounds(10, 32, 164, 14);
		contentPane.add(lblIpServidor);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (textFieldIpServidor.getText().isEmpty() || textFieldIpServidor.getText() == "") {

					JOptionPane.showMessageDialog(null, "Campo obrigatorio nao preenchido.");
				} else {
					try {
						conecta = new ClientConexao(textFieldIpServidor.getText());
						TelaLogin login = new TelaLogin(conecta);
						login.show();
						dispose();
					} catch (UnknownHostException erro) {
						// TODO Auto-generated catch block
						erro.printStackTrace();
						JOptionPane.showMessageDialog(null, erro);
					} catch (IOException erro) {
						// TODO Auto-generated catch block
						erro.printStackTrace();
						JOptionPane.showMessageDialog(null, erro);
					}
				}

			}
		});
		btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalvar.setBounds(178, 74, 89, 23);
		contentPane.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelar.setBounds(79, 74, 89, 23);
		contentPane.add(btnCancelar);
	}

	/*
	 * public ClientConexao conexao() throws UnknownHostException, IOException {
	 * String ipServidor = textFieldIpServidor.getText();
	 * 
	 * ClientConexao c = new ClientConexao(ipServidor);
	 * 
	 * return c; }
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conecta == null) ? 0 : conecta.hashCode());
		result = prime * result + ((contentPane == null) ? 0 : contentPane.hashCode());
		result = prime * result + ((textFieldIpServidor == null) ? 0 : textFieldIpServidor.hashCode());
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
		TelaConexaoCliente other = (TelaConexaoCliente) obj;
		if (conecta == null) {
			if (other.conecta != null)
				return false;
		} else if (!conecta.equals(other.conecta))
			return false;
		if (contentPane == null) {
			if (other.contentPane != null)
				return false;
		} else if (!contentPane.equals(other.contentPane))
			return false;
		if (textFieldIpServidor == null) {
			if (other.textFieldIpServidor != null)
				return false;
		} else if (!textFieldIpServidor.equals(other.textFieldIpServidor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TelaConexaoCliente [contentPane=" + contentPane + ", textFieldIpServidor=" + textFieldIpServidor
				+ ", conecta=" + conecta + "]";
	}
}
