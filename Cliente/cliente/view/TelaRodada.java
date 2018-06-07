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
		try {
			// DISTRIBUIR 2 CARTAS AO JOGADOR - (CAR/NAIPE/VALOR/NULL)
			String recebeCartasIniciais = "CAR" + null + "/" + null + "/" + null;
			conecta.Envia(recebeCartasIniciais);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnFinalizarPartida = new JButton("Desistir");
		btnFinalizarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SAIR DA PARTIDA - (SAI/NULL/NULL/NULL)
				String apostar = "SAI/" + null + "/" + null + "/" + null;
				conecta.Envia(apostar);
			}
		});
		btnFinalizarPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFinalizarPartida.setBounds(10, 201, 123, 23);
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
		btnComprarCartas.setBounds(351, 201, 123, 23);
		contentPane.add(btnComprarCartas);
		contentPane.setLayout(null);
		
		JTextPane txtpnA = new JTextPane();
		txtpnA.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnA.setText("a1. ");
		txtpnA.setEnabled(false);
		txtpnA.setEditable(false);
		txtpnA.setBounds(62, 49, 352, 114);
		contentPane.add(txtpnA);
		
		JLabel lblCartas = new JLabel("Cartas:");
		lblCartas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCartas.setBounds(47, 11, 46, 14);
		contentPane.add(lblCartas);
		
		JButton button = new JButton("Finalizar Partida");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DEFINIR VENCEDORES - (WIN/NOME/EMAIL/NULL) + xWIN? + (EOW/SALDO/NULL/NULL)
				String apostar = "WIN/" + null + "/" + null + "/" + null;
				conecta.Envia(apostar);
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.setBounds(168, 201, 151, 23);
		contentPane.add(button);
	}
}
