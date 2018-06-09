package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.model.CartasEmEsperaModel;
import cliente.conection.ClientConexao;
import cliente.main.ClientManager;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TelaRodada extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList listCartasEmEspera;
	private DefaultListModel listaCartasEmEspera = new DefaultListModel();
	private List<String> cartasIniciais;
	private List<String> cartasAdicionais;
	private ArrayList<String> cartaENaipe = new ArrayList<String>();
	private ArrayList<String> cartaENaipeString = new ArrayList<String>();
	private String naipeStream = null;
	private String cartasStream = null;
	private String naipeString = null;
	private String cartaString = null;
	private int c = 0;
	private int d = 0;
	private JTextField textFieldPontuacaoAtual;
	private int pontuacaoAtual = 0;
	private int contAs = 0;
	private int contValete = 0;
	private int contDama = 0;
	private int contRei = 0;
	private int contD = 0;
	private int valorValete = 0;
	private int valorDama = 0;
	private int valorRei = 0;
	private int valorAs = 0;

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
		try {
			// DISTRIBUIR 2 CARTAS AO JOGADOR - (CAR/NAIPE/VALOR/NULL) +
			// (CAR/NAIPE/VALOR/NULL)
			String ganharCartas = "CAR/" + null + "/" + null + "/" + null;
			conecta.Envia(ganharCartas);

			List<String> leCartas = conecta.recebeNMsgSemCondParada("CAR/Fim Transmissao cartas iniciais/ /");

			System.out.println(leCartas + "Cartas iniciais dadas");

			cartasIniciais = leCartas;

			exibirCartas(cartasIniciais);

		} catch (Exception e) {
			System.err.println(e);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 413, 365);
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

				System.exit(0);
			}
		});
		btnFinalizarPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFinalizarPartida.setBounds(216, 141, 151, 23);
		contentPane.add(btnFinalizarPartida);

		JButton btnComprarCartas = new JButton("Comprar Cartas");
		btnComprarCartas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// COMPRAR CARTAS - (COM/NULL/NULL/NULL) - (CAR/NAIPE/VALOR/NULL) +? OU (EOC)
				String ganharCartas = "COM/" + null + "/" + null + "/" + null;
				conecta.Envia(ganharCartas);

				List<String> leCartas;
				try {
					leCartas = conecta.recebeNMsgSemCondParada("CAR/Fim Transmissao cartas adicionais/ /");
					System.out.println(leCartas + "Cartas adicionais dadas");

					cartasAdicionais = leCartas;
				} catch (Exception e1) {
					System.err.println(e1);;
				}

				exibirCartas(cartasAdicionais);
				
			}
		});
		btnComprarCartas.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnComprarCartas.setBounds(216, 244, 151, 23);
		contentPane.add(btnComprarCartas);
		contentPane.setLayout(null);

		JLabel lblCartas = new JLabel("Carta / Naipe:");
		lblCartas.setFont(new Font("Sitka Small", Font.BOLD, 13));
		lblCartas.setBounds(10, 22, 139, 17);
		contentPane.add(lblCartas);

		JButton button = new JButton("Finalizar Partida");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// DEFINIR VENCEDORES - (WIN/NOME/EMAIL/NULL) + xWIN? + (EOW/SALDO/NULL/NULL)
				String apostar = "WIN/" + null + "/" + null + "/" + null;
				conecta.Envia(apostar);
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.setBounds(216, 192, 151, 23);
		contentPane.add(button);

		listCartasEmEspera = new JList();
		listCartasEmEspera.setModel(listaCartasEmEspera);
		listCartasEmEspera.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listCartasEmEspera.setEnabled(false);
		listCartasEmEspera.setVisibleRowCount(10);
		listCartasEmEspera.setBackground(Color.WHITE);
		listCartasEmEspera.setBounds(10, 55, 179, 261);
		contentPane.add(listCartasEmEspera);

		JButton btnDicas = new JButton("?");
		btnDicas.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDicas.setBounds(216, 293, 151, 23);
		contentPane.add(btnDicas);

		JLabel lblPontuaoAtual = new JLabel("Pontua\u00E7\u00E3o Atual:");
		lblPontuaoAtual.setFont(new Font("Sitka Small", Font.BOLD, 13));
		lblPontuaoAtual.setBounds(193, 23, 139, 17);
		contentPane.add(lblPontuaoAtual);

		textFieldPontuacaoAtual = new JTextField();
		textFieldPontuacaoAtual.setText(Integer.toString(pontuacaoAtual));
		textFieldPontuacaoAtual.setEditable(false);
		textFieldPontuacaoAtual.setBounds(333, 20, 54, 20);
		contentPane.add(textFieldPontuacaoAtual);
		textFieldPontuacaoAtual.setColumns(10);
	}

	public JList getListCartasEmEspera() {
		return listCartasEmEspera;
	}

	public void setListCartasEmEspera(JList listCartasEmEspera) {
		this.listCartasEmEspera = listCartasEmEspera;
	}

	public DefaultListModel getListaCartasEmEspera() {
		return listaCartasEmEspera;
	}

	public void setListaCartasEmEspera(DefaultListModel listaCartasEmEspera) {
		this.listaCartasEmEspera = listaCartasEmEspera;
	}

	public List<String> getCartasIniciais() {
		return cartasIniciais;
	}

	public void setCartasIniciais(List<String> cartasIniciais) {
		this.cartasIniciais = cartasIniciais;
	}

	public ArrayList<String> getCartaENaipe() {
		return cartaENaipe;
	}

	public void setCartaENaipe(ArrayList<String> cartaENaipe) {
		this.cartaENaipe = cartaENaipe;
	}

	public ArrayList<String> getCartaENaipeString() {
		return cartaENaipeString;
	}

	public void setCartaENaipeString(ArrayList<String> cartaENaipeString) {
		this.cartaENaipeString = cartaENaipeString;
	}

	public void exibirCartas(List<String> cartas) {			
		List<String> pegarNaipeStream = cartas.stream().map(s -> s.split("/")).map(p -> p[1])
				.collect(Collectors.toList());

		List<String> pegarValorCartas = cartas.stream().map(s -> s.split("/")).map(p -> p[2])
				.collect(Collectors.toList());

		System.out.println(pegarNaipeStream + " Pegar naipe cartas");

		System.out.println(pegarValorCartas + " Pegar valor cartas");

		// paus (1), ouros (2), copas (3) e espadas (4)
		for (int i = 0, j = 0; i < pegarNaipeStream.size(); i++, j++) {
			System.out.println(pegarNaipeStream.get(i) + " Pegar posi��o");
			naipeStream = pegarNaipeStream.get(i);
			c = Integer.parseInt(naipeStream);
			if (c == 1) {
				System.out.println("Naipe paus " + 1);
				cartaString = "Paus";
			} else if (c == 2) {
				System.out.println("Naipe ouros " + 2);
				cartaString = "Ouros";
			} else if (c == 3) {
				System.out.println("Naipe copas " + 3);
				cartaString = "Copas";
			} else if (c == 4) {
				System.out.println("Naipe espadas " + 4);
				cartaString = "Espadas";
			}

			// As (0), 1 (1), 2 (2), 3 (3), 4 (4), 5 (5), 6 (6), 7 (7), 8 (8), 9 (9), 10
			// (10), 11 (J - Valete), 12 (Q - Dama), 13 (K - Rei)
			System.out.println(pegarValorCartas.get(j) + " Pegar cartas");
			cartasStream = pegarValorCartas.get(j);
			d = Integer.parseInt(cartasStream);
						
			if (d == 0) {
				System.out.println("Carta �s " + 0);
				naipeString = "A";
				contAs++;
			} else if (d > 0 && d < 11) {
				System.out.println("Carta " + d);
				naipeString = Integer.toString(d);
				contD = contD + d;
			} else if (d == 11) {
				System.out.println("Carta Valete " + 11);
				naipeString = "J";
				contValete++;
			} else if (d == 12) {
				System.out.println("Carta Dama " + 12);
				naipeString = "Q";
				contDama++;
			} else if (d == 13) {
				System.out.println("Carta Rei " + 13);
				naipeString = "R";
				contRei++;
			}
					
			if (contValete > 0 || contDama > 0 || contRei > 0) {
				valorAs = contAs * 11; 
			} else {
				valorAs = contAs * 1 ;
			}
			
			valorValete = contValete * 10;
			valorDama = contDama * 10;
			valorRei = contRei * 10;
			
			
			pontuacaoAtual = valorValete + valorDama + valorRei + valorAs + contD;
			
			cartaENaipe.add(naipeStream + "/" + cartasStream);
			cartaENaipeString.add(naipeString + " " + cartaString);
						
			listaCartasEmEspera.addElement(naipeString + " " + cartaString);
		}
		System.out.println("[INFO] Lista de string enviada para exibicao em tela" + listaCartasEmEspera);
		System.out.println("[INFO] Valor As " + valorAs);
		System.out.println("[INFO] Valor Valete " + valorValete);	
		System.out.println("[INFO] Valor Dama " + valorDama);
		System.out.println("[INFO] Valor Rei " + valorRei);
		System.out.println("[INFO] Valor D " + contD);
		System.out.println("[INFO] Contador de As " + contAs);
		System.out.println("[INFO] Contador de Valete " + contValete);
		System.out.println("[INFO] Contador de Dama " + contDama);
		System.out.println("[INFO] Contador de Rei " + contRei);
		System.out.println("[INFO] Pontuacao atual " + pontuacaoAtual);
		
		textFieldPontuacaoAtual.setText(Integer.toString(pontuacaoAtual));

	}

}
