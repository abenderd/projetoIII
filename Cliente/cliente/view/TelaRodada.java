package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JTextPane;

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
	@SuppressWarnings("unchecked")
	public TelaRodada(ClientConexao conecta) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnFinalizarPartida = new JButton("Finalizar Partida");
		btnFinalizarPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFinalizarPartida.setBounds(24, 201, 123, 23);
		contentPane.add(btnFinalizarPartida);
		
		JButton btnComprarCartas = new JButton("Comprar Cartas");
		btnComprarCartas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//COMPRAR CARTAS - (COM/NULL/NULL/NULL) - (CAR/NAIPE/VALOR/NULL) +? OU (EOC)
				String apostar = "COM/" + null + "/" + null + "/" + null;
				conecta.Envia(apostar);
			}
		});
		btnComprarCartas.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnComprarCartas.setBounds(274, 201, 123, 23);
		contentPane.add(btnComprarCartas);
		contentPane.setLayout(null);
		
		JTextPane txtpnA = new JTextPane();
		txtpnA.setText("a1. ");
		txtpnA.setEnabled(false);
		txtpnA.setEditable(false);
		txtpnA.setBounds(99, 49, 234, 112);
		contentPane.add(txtpnA);
		
		JLabel lblCartas = new JLabel("Cartas:");
		lblCartas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCartas.setBounds(47, 11, 46, 14);
		contentPane.add(lblCartas);
	}
}
