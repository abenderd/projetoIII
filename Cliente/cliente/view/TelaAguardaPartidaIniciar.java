package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;
import server.model.Partida;
import server.model.Usuario;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class TelaAguardaPartidaIniciar extends JFrame {

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
					TelaAguardaPartidaIniciar frame = new TelaAguardaPartidaIniciar(conecta);
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
	public TelaAguardaPartidaIniciar(ClientConexao conecta) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 347, 258);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// SAIR DA PARTIDA - (SAI/NULL/NULL/NULL)
				String apostar = "SAI/" + null + "/" + null + "/" + null;
				conecta.Envia(apostar);

				System.exit(0);
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSair.setBounds(33, 172, 117, 23);
		contentPane.add(btnSair);

		JButton btnIniciarRodada = new JButton("Iniciar Rodada");
		btnIniciarRodada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int valorAposta = Integer.parseInt(JOptionPane.showInputDialog(null, "Valor da aposta: "));
					System.out.println("Aposta is:" + valorAposta);

					// APOSTA EM UMA JOGADA - (APO/VALOR/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
					String apostar = "APO/" + valorAposta + "/" + null + "/" + null;
					conecta.Envia(apostar);

					// Validar quantidade minima de usuarios
					List<String> mensagensErros = conecta.recebeNMsg("ERR/Numero de jogadores insuficiente/ / ")
							.stream().map(s -> s.split("/")).map(p -> p[1]).collect(Collectors.toList());

					System.out.println("Mensagem do recebe N Msg " + mensagensErros);

					if (mensagensErros.contains("Numero de jogadores insuficiente")) {
						JOptionPane.showMessageDialog(null,
								"Numero de jogares insuficiente, aguarde no minimo tres jogadores.");
						mensagensErros.clear();
						conecta.recebeNMsg("ERR/Numero de jogadores insuficiente/ / ").clear();
					}

					TelaRodada telaRodada = new TelaRodada(conecta);
					telaRodada.show();
					dispose();

				} catch (Exception quantidadeUsuarioInsuficiente) {
					System.err.println(quantidadeUsuarioInsuficiente);
				}
			}
		});
		btnIniciarRodada.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIniciarRodada.setBounds(193, 172, 117, 23);
		contentPane.add(btnIniciarRodada);

		JTextPane txtpnARodadaPoder = new JTextPane();
		txtpnARodadaPoder.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtpnARodadaPoder.setEditable(false);
		txtpnARodadaPoder.setText("A rodada podera ser iniciada quando houver no minimo 3 jogadores.");
		txtpnARodadaPoder.setBounds(33, 70, 277, 74);
		contentPane.add(txtpnARodadaPoder);

		JLabel label = new JLabel("");
		label.setBounds(33, 11, 46, 14);
		contentPane.add(label);
	}

}
