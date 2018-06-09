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

			List<String> pegarNaipeStream = cartasIniciais.stream().map(s -> s.split("/")).map(p -> p[1])
					.collect(Collectors.toList());

			List<String> pegarValorCartas = cartasIniciais.stream().map(s -> s.split("/")).map(p -> p[2])
					.collect(Collectors.toList());

			System.out.println(pegarNaipeStream + " Pegar naipe cartas");

			System.out.println(pegarValorCartas + " Pegar valor cartas");

			// paus (1), ouros (2), copas (3) e espadas (4)
			for (int i = 0, j = 0; i < pegarNaipeStream.size(); i++, j++) {
				System.out.println(pegarNaipeStream.get(i) + " Pegar posição");
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

				// As (0), 1 (1), 2 (2), 3 (3), 4 (4), 5 (5), 6 (6), 7 (7), 8 (8), 9 (9), 10 (10), 11 (J - Valete), 12 (Q - Dama), 13 (K - Rei)
				System.out.println(pegarValorCartas.get(j) + " Pegar cartas");
				cartasStream = pegarValorCartas.get(j);
				d = Integer.parseInt(cartasStream);
				if (d == 0) {
					System.out.println("Carta Ás " + 0);
					naipeString = "A";
				} else if (d > 0 && d < 11) {
					System.out.println("Carta " + d);
					naipeString = Integer.toString(d);
				} else if (d == 11) {
					System.out.println("Carta Valete " + 11);
					naipeString = "J";
				} else if (d == 12) {
					System.out.println("Carta Dama " + 12);
					naipeString = "Q";
				} else if (d == 13) {
					System.out.println("Carta Rei " + 13);
					naipeString = "R";
				}
				cartaENaipe.add(naipeStream + "/" + cartasStream);
				cartaENaipeString.add(naipeString + " " + cartaString);
				System.out.println(cartaENaipe + "Carta e naipe");


				listaCartasEmEspera.addElement(naipeString + " " + cartaString);
			}
			System.out.println(cartaENaipeString + "Carta e naipe to String" + '\n');
			System.out.println(listaCartasEmEspera + "Lista de espera");

		} catch (Exception e) {
			System.err.println(e);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
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
		btnFinalizarPartida.setBounds(10, 327, 123, 23);
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
					e1.printStackTrace();
				}				

				List<String> pegarNaipeStream = cartasAdicionais.stream().map(s -> s.split("/")).map(p -> p[1])
						.collect(Collectors.toList());

				List<String> pegarValorCartas = cartasAdicionais.stream().map(s -> s.split("/")).map(p -> p[2])
						.collect(Collectors.toList());

				System.out.println(pegarNaipeStream + " Pegar naipe cartas adicionais");

				System.out.println(pegarValorCartas + " Pegar valor cartas adicionais ");

				// paus (1), ouros (2), copas (3) e espadas (4)
				for (int i = 0, j = 0; i < pegarNaipeStream.size(); i++, j++) {
					System.out.println(pegarNaipeStream.get(i) + " Pegar posição");
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

					// As (0), 1 (1), 2 (2), 3 (3), 4 (4), 5 (5), 6 (6), 7 (7), 8 (8), 9 (9), 10 (10), 11 (J - Valete), 12 (Q - Dama), 13 (K - Rei) 
					System.out.println(pegarValorCartas.get(j) + " Pegar cartas");
					cartasStream = pegarValorCartas.get(j);
					d = Integer.parseInt(cartasStream);
					if (d == 0) {
						System.out.println("Carta Ás " + 0);
						naipeString = "A";
					} else if (d > 0 && d < 11) {
						System.out.println("Carta " + d);
						naipeString = Integer.toString(d);
					} else if (d == 11) {
						System.out.println("Carta Valete " + 11);
						naipeString = "J";
					} else if (d == 12) {
						System.out.println("Carta Dama " + 12);
						naipeString = "Q";
					} else if (d == 13) {
						System.out.println("Carta Rei " + 13);
						naipeString = "R";
					}
					cartaENaipe.add(naipeStream + "/" + cartasStream);
					cartaENaipeString.add(naipeString + " " + cartaString);
					System.out.println(cartaENaipe + "Carta e naipe adicionais");


					listaCartasEmEspera.addElement(naipeString + " " + cartaString);
				}
				System.out.println(cartaENaipeString + "Carta e naipe adicionais to String" + '\n');
				System.out.println(listaCartasEmEspera + "Lista com cartas adicionais");
			}
		});
		btnComprarCartas.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnComprarCartas.setBounds(351, 327, 123, 23);
		contentPane.add(btnComprarCartas);
		contentPane.setLayout(null);

		JLabel lblCartas = new JLabel("Cartas:");
		lblCartas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCartas.setBounds(161, 11, 46, 14);
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
		button.setBounds(168, 327, 151, 23);
		contentPane.add(button);

		listCartasEmEspera = new JList();
		listCartasEmEspera.setModel(listaCartasEmEspera);
		listCartasEmEspera.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listCartasEmEspera.setEnabled(false);
		listCartasEmEspera.setVisibleRowCount(10);
		listCartasEmEspera.setBackground(Color.WHITE);
		listCartasEmEspera.setBounds(162, 25, 179, 291);
		contentPane.add(listCartasEmEspera);
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

}
