package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class TelaPartida extends JFrame {

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
					TelaPartida frame = new TelaPartida(conecta);
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
	public TelaPartida(ClientConexao conecta) {
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
		
		JLabel lblJogador1 = new JLabel("1. Nome do Jogador 1");
		lblJogador1.setBounds(10, 36, 118, 14);
		contentPane.add(lblJogador1);
		
		JLabel lblJogador2 = new JLabel("2. Nome do Jogador 2");
		lblJogador2.setBounds(10, 50, 118, 14);
		contentPane.add(lblJogador2);
		
		JLabel lblJogador3 = new JLabel("3. Nome do Jogador 3");
		lblJogador3.setBounds(10, 64, 118, 14);
		contentPane.add(lblJogador3);
		
		JLabel lblJogador4 = new JLabel("4. Nome do Jogador 4");
		lblJogador4.setBounds(10, 78, 118, 14);
		contentPane.add(lblJogador4);
		
		JLabel lblJogador5 = new JLabel("5. Nome do Jogador 5");
		lblJogador5.setBounds(10, 92, 118, 14);
		contentPane.add(lblJogador5);
		
		JLabel lblJogador6 = new JLabel("6. Nome do Jogador 6");
		lblJogador6.setBounds(10, 106, 118, 14);
		contentPane.add(lblJogador6);
		
		JLabel lblJogador7 = new JLabel("7. Nome do Jogador 7");
		lblJogador7.setBounds(10, 120, 118, 14);
		contentPane.add(lblJogador7);
		
		JLabel lblJogador8 = new JLabel("8. Nome do Jogador 8");
		lblJogador8.setBounds(10, 134, 118, 14);
		contentPane.add(lblJogador8);
		
		JButton btnFinalizarPartida = new JButton("Finalizar Partida");
		btnFinalizarPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFinalizarPartida.setBounds(77, 201, 159, 23);
		contentPane.add(btnFinalizarPartida);
	}
}
