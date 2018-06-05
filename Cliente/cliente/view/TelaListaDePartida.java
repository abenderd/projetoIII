package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;
import server.main.ServerManager;
import server.model.Partida;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

public class TelaListaDePartida extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList listPartidasEmEspera;
	DefaultListModel listaEmEspera = new DefaultListModel();
	ServerManager serverManagaer = new ServerManager();

	/**
	 * Launch the application.
	 */

	public void create(ClientConexao conecta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					TelaListaDePartida frame = new TelaListaDePartida(conecta);
					frame.setVisible(true);
					
					consultarPartidas(conecta);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public TelaListaDePartida(ClientConexao conecta) {
		consultarPartidas(conecta);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(11, 270, 105, 30);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 11));

		JButton btnIniciarPartida = new JButton("Iniciar Partida");
		btnIniciarPartida.setBounds(287, 270, 131, 30);
		btnIniciarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomePartida = listPartidasEmEspera.getSelectedValue().toString();
				// ENTRAR EM UM PARTIDA - (ENT/NOME/NULL/NULL) - RESPOSTA (SUC/SALDO/NULL/NULL)
				// ou (ERR)
				String entrarPartida = "ENT/" + nomePartida + "/" + null + "/" + null;
				conecta.Envia(entrarPartida);

				TelaAguardaPartidaIniciar telaAguardaPartidaIniciar = new TelaAguardaPartidaIniciar(conecta);
				telaAguardaPartidaIniciar.show();
				dispose();
			}
		});

		JButton btnCriarPartida = new JButton("Criar Partida");
		btnCriarPartida.setBounds(139, 270, 125, 30);
		btnCriarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomePartida = JOptionPane.showInputDialog((Component) e.getSource(),
						"Qual sera o nome da partida?");
				
				listPartidasEmEspera.setModel(listaEmEspera);
				
				if (nomePartida != null && !nomePartida.equals("")) {
					try {
						// CRIAR PARTIDA - (CRI/NOME/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
						String criarPartida = "CRI/" + nomePartida + "/" + null + "/" + null;
						conecta.Envia(criarPartida);

						JOptionPane.showMessageDialog((Component) e.getSource(),
								"Partida " + nomePartida + " criada com sucesso.");
						
						listaEmEspera.addElement(nomePartida);

						// ENTRAR EM UM PARTIDA - (ENT/NOME/NULL/NULL) - RESPOSTA (SUC/SALDO/NULL/NULL)
						// ou (ERR)
						String entrarPartida = "ENT/" + nomePartida + "/" + null + "/" + null;
						conecta.Envia(entrarPartida);
						
						// CONSULTAR PARTIDA - (PAR/NOME/STATUS) - RESPOSTA (PAR/NOME/STATUS/NULL) ou
						// (EOP)
						String partidasIniciadas = "PAR/" + null + "/" + true + "/" + null;
						conecta.Envia(partidasIniciadas);
						ArrayList<String> bla = conecta.recebeNMsg("stop");
						System.out.println(bla);
						
						String partidasEmEspera = "PAR/" + null + "/" + false + "/" + null;
						conecta.Envia(partidasEmEspera);
						System.out.println(partidasIniciadas + " Consultando partidas em espera.");
						ArrayList<String> consultaPartidasEmEspera = conecta.recebeNMsg("stop");
						System.out.println(consultaPartidasEmEspera);
						
						TelaAguardaPartidaIniciar telaAguardaPartidaIniciar = new TelaAguardaPartidaIniciar(conecta);
						telaAguardaPartidaIniciar.show();
						dispose();

					} catch (Exception erroCriarPartida) {
						JOptionPane.showMessageDialog((Component) e.getSource(), "Erro ao criar partida.");
						System.out.println(erroCriarPartida);
					}

				}
			}
		});
		btnCriarPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIniciarPartida.setFont(new Font("Tahoma", Font.BOLD, 11));

		JLabel lblPartidasIniciadas = new JLabel("Partidas Iniciadas");
		lblPartidasIniciadas.setBounds(11, 11, 206, 35);
		lblPartidasIniciadas.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblPartidasEmEspera = new JLabel("Partidas Em Espera");
		lblPartidasEmEspera.setBounds(206, 11, 212, 35);
		lblPartidasEmEspera.setFont(new Font("Tahoma", Font.BOLD, 12));

		JList listPartidasIniciadas = new JList();
		listPartidasIniciadas.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent arg0) {
			}
		});
		listPartidasIniciadas.setBounds(11, 40, 185, 193);
		listPartidasIniciadas.setBackground(Color.WHITE);
		listPartidasIniciadas.setVisibleRowCount(10);
		listPartidasIniciadas.setModel(new AbstractListModel() {
			String[] values = new String[] { "Lista 1", "Lista 2", "Lista 1", "Lista 2", "Lista 1", "Lista 2",
					"Lista 1", "Lista 8", "Lista 9", "Lista 10" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		contentPane.setLayout(null);
		contentPane.add(lblPartidasIniciadas);
		contentPane.add(lblPartidasEmEspera);
		contentPane.add(listPartidasIniciadas);
		contentPane.add(btnCriarPartida);
		contentPane.add(btnIniciarPartida);
		contentPane.add(btnSair);

		@SuppressWarnings("rawtypes")
		JList listPartidasEmEspera = new JList();
		this.listPartidasEmEspera = listPartidasEmEspera;
		listPartidasEmEspera.setVisibleRowCount(10);
		listPartidasEmEspera.setBackground(Color.WHITE);
		listPartidasEmEspera.setBounds(206, 40, 179, 193);
		contentPane.add(listPartidasEmEspera);
	}

	public void consultarPartidas(ClientConexao conecta) {
		try {
			// CONSULTAR PARTIDA - (PAR/NOME/STATUS) - RESPOSTA (PAR/NOME/STATUS/NULL) ou
			// (EOP)
			String partidasIniciadas = "PAR/" + null + "/" + true + "/" + null;
			conecta.Envia(partidasIniciadas);
			System.out.println(partidasIniciadas + " Consultando partidas iniciadas.");
			ArrayList<Partida> arrayPartidasIniciadas = serverManagaer.getPartidas();
			System.out.println(arrayPartidasIniciadas);
			
			String partidasEmEspera = "PAR/" + null + "/" + false + "/" + null;
			conecta.Envia(partidasEmEspera);
			System.out.println(partidasIniciadas + " Consultando partidas em espera.");	
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
