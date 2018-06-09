package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.model.PartidasEmEsperaModel;
import client.model.PartidasIniciadasModel;
import cliente.conection.ClientConexao;
import cliente.main.ClientManager;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.ListSelectionModel;

public class TelaListaDePartida extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList listPartidasEmEspera;
	private JList listPartidasIniciadas = new JList();
	DefaultListModel listaEmEspera = new DefaultListModel();
	public String nomePartidaIniciada;

	/**
	 * Launch the application.
	 */

	public void create(ClientConexao conecta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaListaDePartida frame = new TelaListaDePartida(conecta);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public TelaListaDePartida(ClientConexao conecta) {
		// le a msg do login
		try {
			conecta.recebe1Msg();
		} catch (Exception e) {
			System.err.println(e);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(11, 270, 105, 30);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// SAIR DA PARTIDA - (SAI/NULL/NULL/NULL)
				String apostar = "SAI/" + null + "/" + null + "/" + null;
				conecta.Envia(apostar);
				conecta.Envia(apostar);
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

				nomePartidaIniciada = nomePartida;

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

						TelaAguardaPartidaIniciar telaAguardaPartidaIniciar = new TelaAguardaPartidaIniciar(conecta);
						telaAguardaPartidaIniciar.show();
						dispose();

					} catch (Exception erroCriarPartida) {
						JOptionPane.showMessageDialog((Component) e.getSource(), "Erro ao criar partida.");
						System.err.println(erroCriarPartida);
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

		listPartidasIniciadas.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent arg0) {
			}
		});
		listPartidasIniciadas.setBounds(11, 40, 185, 193);
		listPartidasIniciadas.setBackground(Color.WHITE);
		listPartidasIniciadas.setVisibleRowCount(10);
		listPartidasIniciadas.setModel(new PartidasIniciadasModel(conecta));
		contentPane.setLayout(null);
		contentPane.add(lblPartidasIniciadas);
		contentPane.add(lblPartidasEmEspera);
		contentPane.add(listPartidasIniciadas);
		contentPane.add(btnCriarPartida);
		contentPane.add(btnIniciarPartida);
		contentPane.add(btnSair);

		listPartidasEmEspera = new JList();
		listPartidasEmEspera.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPartidasEmEspera.setModel(new PartidasEmEsperaModel(conecta));
		listPartidasEmEspera.setVisibleRowCount(10);
		listPartidasEmEspera.setBackground(Color.WHITE);
		listPartidasEmEspera.setBounds(206, 40, 179, 193);
		contentPane.add(listPartidasEmEspera);
	}

	public JList getListPartidasEmEspera() {
		return listPartidasEmEspera;
	}

	public void setListPartidasEmEspera(JList listPartidasEmEspera) {
		this.listPartidasEmEspera = listPartidasEmEspera;
	}

	public JList getListPartidasIniciadas() {
		return listPartidasIniciadas;
	}

	public void setListPartidasIniciadas(JList listPartidasIniciadas) {
		this.listPartidasIniciadas = listPartidasIniciadas;
	}

	public String getNomePartidaIniciada() {
		return nomePartidaIniciada;
	}

	public void setNomePartidaIniciada(String nomePartidaIniciada) {
		this.nomePartidaIniciada = nomePartidaIniciada;
	}

	public DefaultListModel getListaEmEspera() {
		return listaEmEspera;
	}

	public void setListaEmEspera(DefaultListModel listaEmEspera) {
		this.listaEmEspera = listaEmEspera;
	}

	@Override
	public String toString() {
		return "TelaListaDePartida [contentPane=" + contentPane + ", listPartidasEmEspera=" + listPartidasEmEspera
				+ ", listPartidasIniciadas=" + listPartidasIniciadas + ", listaEmEspera=" + listaEmEspera
				+ ", nomePartidaIniciada=" + nomePartidaIniciada + "]";
	}

}
