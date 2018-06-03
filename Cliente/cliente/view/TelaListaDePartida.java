package cliente.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.conection.ClientConexao;
import server.main.ServerManager;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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

public class TelaListaDePartida extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList listPartidasEmEspera;

	/**
	 * Launch the application.
	 */
	
	public void create(ClientConexao conecta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaListaDePartida frame = new TelaListaDePartida(conecta);
					frame.setVisible(true);
					
					consultarPartidas();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(11, 219, 105, 30);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JButton btnIniciarPartida = new JButton("Iniciar Partida");
		btnIniciarPartida.setBounds(287, 219, 131, 30);
		btnIniciarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomePartida = listPartidasEmEspera.getSelectedValue().toString();
				//ENTRAR EM UM PARTIDA - (ENT/NOME/NULL/NULL) - RESPOSTA (SUC/SALDO/NULL/NULL) ou (ERR)
	    		String entrarPartida = "ENT/" + nomePartida + "/" + null + "/" + null ;
				conecta.Envia(entrarPartida);
	    		
	    		TelaAguardaPartidaIniciar telaAguardaPartidaIniciar = new TelaAguardaPartidaIniciar(conecta);
	    		telaAguardaPartidaIniciar.show();
				dispose();
			}
		});
		
		JButton btnCriarPartida = new JButton("Criar Partida");
		btnCriarPartida.setBounds(139, 219, 125, 30);
		btnCriarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomePartida = JOptionPane.showInputDialog((Component) e.getSource(), "Qual sera o nome da partida?");
		        if (nomePartida != null && !nomePartida.equals("")) {
		        	try {
		        		//CRIAR PARTIDA - (CRI/NOME/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
		        		
		        		String criarPartida = "CRI/" + nomePartida + "/" + null + "/" + null ;
						conecta.Envia(criarPartida);
						
		        		JOptionPane.showMessageDialog((Component) e.getSource(), "Partida " + nomePartida + " criada com sucesso.");
		        		
		        		//ENTRAR EM UM PARTIDA - (ENT/NOME/NULL/NULL) - RESPOSTA (SUC/SALDO/NULL/NULL) ou (ERR)
		        		String entrarPartida = "ENT/" + nomePartida + "/" + null + "/" + null ;
						conecta.Envia(entrarPartida);
		        		
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
		listPartidasIniciadas.setBounds(11, 40, 180, 160);
		listPartidasIniciadas.setBackground(Color.WHITE);
		listPartidasIniciadas.setVisibleRowCount(10);
		listPartidasIniciadas.setModel(new AbstractListModel() {
			String[] values = new String[] {"Lista 1", "Lista 2", "Lista 1", "Lista 2", "Lista 1", "Lista 2", "Lista 1", "Lista 8", "Lista 9", "Lista 10"};
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
		listPartidasEmEspera.setBounds(206, 40, 180, 160);
		contentPane.add(listPartidasEmEspera);
	}
	
	private boolean consultarPartidas () {
		try {
			ServerManager partida = new ServerManager();
			//CONSULTAR PARTIDA - (PAR/NOME/STATUS) - RESPOSTA (PAR/NOME/STATUS/NULL) ou (EOP)
			String partidasIniciadas = "PAR/" + " " + "/" + " "+ "/" + null ;
			System.out.println(partida + "Consultando partidas iniciadas.");
			
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
}
