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
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;

public class TelaInicial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldSaldoDisponivel;

	/**
	 * Launch the application.
	 */
	
	public void create(ClientConexao conecta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial(conecta);
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
	public TelaInicial(ClientConexao conecta) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIniciarPartida = new JButton("Iniciar Partida");
		btnIniciarPartida.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				TelaAposta telaAposta = new TelaAposta(conecta);
				telaAposta.show();
				dispose();
			}
		});
		btnIniciarPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIniciarPartida.setBounds(266, 190, 127, 23);
		contentPane.add(btnIniciarPartida);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSair.setBounds(49, 190, 127, 23);
		contentPane.add(btnSair);
		
		JLabel lblSejaBemVindo = new JLabel("Seja Bem-Vindo(a)");
		lblSejaBemVindo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSejaBemVindo.setBounds(52, 55, 149, 14);
		contentPane.add(lblSejaBemVindo);
		
		JLabel lblSaldoDisponivel = new JLabel("Saldo Disponível:");
		lblSaldoDisponivel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaldoDisponivel.setBounds(49, 115, 127, 14);
		contentPane.add(lblSaldoDisponivel);
		
		JLabel lblNomeDaPessoa = new JLabel("Nome da pessoa");
		lblNomeDaPessoa.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNomeDaPessoa.setBounds(187, 55, 206, 14);
		contentPane.add(lblNomeDaPessoa);
		
		textFieldSaldoDisponivel = new JTextField();
		textFieldSaldoDisponivel.setEnabled(false);
		textFieldSaldoDisponivel.setEditable(false);
		textFieldSaldoDisponivel.setFont(new Font("Tahoma", Font.BOLD, 14));
		textFieldSaldoDisponivel.setBounds(186, 112, 86, 20);
		contentPane.add(textFieldSaldoDisponivel);
		textFieldSaldoDisponivel.setColumns(10);
	}
}
