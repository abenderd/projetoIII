package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class TelaRodada extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public void create(ClientConexao conecta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaRodada frame = new TelaRodada(conecta);
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
	public TelaRodada(ClientConexao conecta) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJogadores = new JLabel("Jogadores:");
		lblJogadores.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblJogadores.setBounds(10, 11, 67, 14);
		contentPane.add(lblJogadores);
		
		JButton btnFinalizarPartida = new JButton("Finalizar Partida");
		btnFinalizarPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFinalizarPartida.setBounds(77, 201, 159, 23);
		contentPane.add(btnFinalizarPartida);
	}
}
