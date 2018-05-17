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
import javax.swing.JTextField;
import java.awt.Color;

public class TelaAposta extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldSaldoDisponível;
	private JTextField textFieldValorAposta;

	/**
	 * Launch the application.
	 */
	
	public void create(ClientConexao conecta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaAposta frame = new TelaAposta(conecta);
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
	public TelaAposta(ClientConexao conecta) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 347, 258);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 235, 215));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelar.setBounds(57, 173, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnApostar = new JButton("Apostar");
		btnApostar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				TelaPartida telaPartida = new TelaPartida(conecta);
				telaPartida.show();
				dispose();
			}
		});
		btnApostar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnApostar.setBounds(184, 173, 89, 23);
		contentPane.add(btnApostar);
		
		JLabel lblSaldoDisponvel = new JLabel("Saldo Dispon\u00EDvel:");
		lblSaldoDisponvel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSaldoDisponvel.setBounds(57, 46, 117, 14);
		contentPane.add(lblSaldoDisponvel);
		
		JLabel lblValorAposta = new JLabel("Valor da aposta:");
		lblValorAposta.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblValorAposta.setBounds(57, 94, 117, 14);
		contentPane.add(lblValorAposta);
		
		textFieldSaldoDisponível = new JTextField();
		textFieldSaldoDisponível.setEnabled(false);
		textFieldSaldoDisponível.setEditable(false);
		textFieldSaldoDisponível.setBounds(184, 44, 86, 20);
		contentPane.add(textFieldSaldoDisponível);
		textFieldSaldoDisponível.setColumns(10);
		
		textFieldValorAposta = new JTextField();
		textFieldValorAposta.setColumns(10);
		textFieldValorAposta.setBounds(184, 92, 86, 20);
		contentPane.add(textFieldValorAposta);
	}

}
