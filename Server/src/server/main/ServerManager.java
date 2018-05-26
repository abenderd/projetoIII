package server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import server.conection.Transmissor;
import server.dao.CadastroDAO;
import server.dbo.CadastroDBO;
import server.model.*;

public class ServerManager {

	private ArrayList<Socket> clientOutputStreams;
	private ArrayList<Partida> partidas;
	private Transmissor t;
	private ServerSocket receptor;
	
	public ServerManager(){
		clientOutputStreams = new ArrayList();
		partidas = new ArrayList();
		t = new Transmissor();
		go();
	}
	
	private void go() {
		// Guarda os clientes
		try {
			receptor = new ServerSocket(11111);
			System.out.println("Servidor up");
			// Mantem o servidor escutando
			while (true) {
				Socket connection = receptor.accept();
				clientOutputStreams.add(connection);
				Thread t = new Thread(new ClientHandler(connection));
				t.start();
			}
		} catch (IOException e) {
			System.err.print("ServerManager - erro no loop: " + e);
		}
	}

	// Cuida das conexoes com novos clientes
	private class ClientHandler implements Runnable {
		private Socket clienteSocket;
		
		public ClientHandler(Socket socket){
			this.clienteSocket = socket;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("Recebido uma nova conexao (" + clienteSocket.getInetAddress() + ")");
				ObjectInputStream server = new ObjectInputStream(clienteSocket.getInputStream());
				String mensagem = "";
				Usuario usuario = null;
				Partida partida = null;
				while (true) {
					mensagem = String.valueOf(server.readObject());
					if (mensagem.toUpperCase().equals("FIM"))
						break;
					String[] temp;
					String delimiter = "/";
					temp = mensagem.split(delimiter);
					try {
						String var1 = temp[0];
						String var2 = temp[1];
						String var3 = temp[2];
						String var4 = temp[3];

						CadastroDBO cad = new CadastroDBO(var2, var4, var3);
						CadastroDAO cadDao = new CadastroDAO();

						System.out.println(var1 + "/" + var2 + "/" + var3 + "/" + var4);

						//(COMANDO/COMPLEMENTO1/COMPLEMENTO2/COMPLEMENTO3)
						//CADASTRAR JOGADOR - (CAD/EMAIL/NOME/SENHA) - RESPOSTA (SUC) ou (ERR)
						//JOGAR JOGADOR - (LOG/EMAIL/SENHA/NULL) - RESPOSTA (SUC) ou (ERR)
						//CRIAR PARTIDA - (CRI/NOME/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
						//CONSULTAR PARTIDA - (PAR/NOME/STATUS) - RESPOSTA (PAR/NOME/STATUS/NULL) ou (EOP)
						//ENTRAR EM UM PARTIDA - (ENT/NOME/NULL/NULL) - RESPOSTA (SUC/SALDO/NULL/NULL) ou (ERR)
						//APOSTA EM UMA JOGADA - (APO/VALOR/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
						//DISTRIBUIR 2 CARTAS AO JOGADOR - (CAR/NAIPE/VALOR/NULL) + (CAR/NAIPE/VALOR/NULL)
						//COMPRAR CARTAS - (COM/NULL/NULL/NULL) - (CAR/NAIPE/VALOR/NULL) +? OU (EOC)
						//DEFINIR VENCEDORES - (WIN/NOME/EMAIL/NULL) + xWIN? + (EOW/SALDO/NULL/NULL)
						//SAIR DA PARTIDA - (SAI/NULL/NULL/NULL)
						//SEM SALDO - (SAL/MENSSAGEM/NULL/NULL)
						
						switch (var1) {
						case "CAD":
							try {
								cadDao.cadastro(cad);
							} catch (Exception e) {
								t.transmite(clienteSocket, var2 + "/" + e);
							}
							t.transmite(clienteSocket, var2 + "Cadastrado com sucesso");
							break;
						case "LOG":
							try {
								cad = cadDao.getUsuario(var2, var3);
								System.out.println(cad.toString());
								usuario = new Usuario(var3, var2, clienteSocket);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								t.transmite(clienteSocket, var2 + "/" + e);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								t.transmite(clienteSocket, var2 + "/" + e);
							}
							t.transmite(clienteSocket, cad.getNome() + " Logado com sucesso");
							break;
						case "CRI":
							if(usuario.isAguardandoSaldo()){
								t.transmite(clienteSocket, "ERR/ / / ");
							}else{
								boolean repetido = false;
								for(int count = 0;count < partidas.size();count ++){
									if(var2.equals(partidas.get(count).getNomePartida())){
										t.transmite(clienteSocket, "ERR/ / / ");
										repetido = true;
										break;
									}
								}
								if(!repetido){
									Partida nova = new Partida(var2, usuario);
									partidas.add(nova);
									partida = nova;
									t.transmite(clienteSocket, "SUC/ / / ");
								}
							}
							break;
						case "PAR":
							for(int x=0;x<partidas.size();x++){
								t.transmite(clienteSocket, "PAR/"+ partidas.get(x).getNomePartida() + "/" + partidas.get(x).getStatus() + "/ ");
							}
							t.transmite(clienteSocket,"EOP/ / / ");
							break;
						case "ENT":
							if(usuario.isAguardandoSaldo()){
								t.transmite(clienteSocket, "ERR/ / / ");
							}else{
								boolean success = false;
								for(int x=0;x<partidas.size();x++){
									if(partidas.get(x).getNomePartida().equals(var2)){
										partidas.get(x).addUsuario(usuario);
										partida = partidas.get(x);
										success = true;
										break;
									}
								}
								if(success)
									t.transmite(clienteSocket, "SUC/"+ usuario.getSaldo() +"/ / ");
								else
									t.transmite(clienteSocket, "ERR/ / / ");
							}
							break;
						case "APO":
							if(partida == null){
								InputMismatchException erro = new InputMismatchException("(APO) Voce precisa estar em uma partida para apostar");
								throw erro;
							}
							if(partida.Apostar(usuario.getEmail(), Integer.parseInt(var2)))
								t.transmite(clienteSocket, "SUC/ / / ");
							t.transmite(clienteSocket, "ERR/ / / ");
							break;
						case "CAR":
							usuario.setComprandoCartas(true);
							if(partida == null){
								InputMismatchException erro = new InputMismatchException("(CAR) Voce precisa estar em uma partida para solicitar cartas");
								throw erro;
							}
							for(int x=0;x<2;x++){
								Carta c = partida.getCarta(usuario.getEmail());
								t.transmite(clienteSocket, "CAR/"+ c.getNipe() +"/"+ c.getValor() +"/ ");
							}
							break;
						case "COM":
							if(partida == null){
								InputMismatchException erro = new InputMismatchException("(COM) Voce precisa estar em uma partida para solicitar cartas");
								throw erro;
							}
							usuario.setComprandoCartas(true);
							Carta c = partida.getCarta(usuario.getEmail());
							t.transmite(clienteSocket, "CAR/"+ c.getNipe() +"/"+ c.getValor() +"/ ");
							break;
						case "EOC": //ENVIAR EOC QUANDO NAO FARA MAIS NADA NA RODADA
							if(partida == null){
								InputMismatchException erro = new InputMismatchException("(EOC) Voce precisa estar em uma partida para cancelar");
								throw erro;
							}
							if(!usuario.isComprandoCartas()){
								InputMismatchException erro = new InputMismatchException("(EOC) Voce precisa estar comprando cartas para cancelar");
								throw erro;
							}
							usuario.setComprandoCartas(false);
							if(partida.fimRodada()){ //VERIFICA SE RODADA ACABOU, SE SIM ENVIA WIN
								ArrayList<Usuario> usuarios = partida.getUsuarios();
								ArrayList<Usuario> ganhadores = new ArrayList<Usuario>();
								ArrayList<Usuario> perdedores = new ArrayList<Usuario>();
								int valorPote = partida.getValorPorte();
								//ganhadores.add(usuarios.remove(0));
								while(usuarios.size() > 0){
									Usuario atual = usuarios.remove(0);
									if(atual.getValorMao()>21)
										perdedores.add(atual);
									else if(ganhadores.size() > 0 && (atual.getValorMao() > ganhadores.get(0).getValorMao())){
										while(ganhadores.size() > 0){
											perdedores.add(ganhadores.remove(0));
										}
										ganhadores.add(atual);
									}else
										ganhadores.add(atual);
								}
								//AVISA GANHADORES
								valorPote = (valorPote / ganhadores.size());
								for(int x=0; x < ganhadores.size(); x++){
									t.transmite(ganhadores.get(x).getClienteSocket(), "WIN/"+ ganhadores.get(x).getNome() +"/"+ ganhadores.get(x).getEmail() +"/ ");
									ganhadores.get(x).setSaldo(valorPote);
									t.transmite(ganhadores.get(x).getClienteSocket(), "EOW/"+ ganhadores.get(x).getSaldo() +"/ / ");
								}
								for(int x=0; x < perdedores.size(); x++){
									t.transmite(perdedores.get(x).getClienteSocket(), "EOW/"+ perdedores.get(x).getSaldo() +"/ / ");
								}
							}
							break;
						case "SAI":
							partida.removeUsuario(usuario.getEmail());
							clienteSocket.close();
							break;
						default:
							t.transmite(clienteSocket, var1 + " - MENSSAGEM INVALIDA");
							break;				
						}
					}catch (InputMismatchException e) {
						t.transmite(clienteSocket, "ERRO - " + e);
						System.err.println("ERRO - " + clienteSocket.toString() + " - " + e);
					} catch (Exception e) {
						t.transmite(clienteSocket, "MENSSAGEM INVALIDA " + e);
						System.err.println("Menssagem invalida de: " + clienteSocket.toString() + " - " + e);
					}
				}
				//VERIFICA SE TEM MOEDAS PARA CONTINUAR JOGADONDO
				if(usuario.getSaldo()<=0){
					t.transmite(clienteSocket, "SAL/Voce esta sem saldo, aguarde para jogar/ / ");
					if(!usuario.isAguardandoSaldo()){
						usuario.setAguardandoSaldo(true);
						//PROGRAMA
					}
				}
				// connection.close();
				// receptor.close();
			} catch (NullPointerException e) {
				System.err.println("ServerManager - Null Pointer (Usuario Desconhecido?) - " + e);
			} catch (ClassNotFoundException e) {
				System.err.println("ServerManager - server.readObject (ClassNotFoundException) - " + e);
			} catch (IOException e1) {
				System.err.println("ServerManager - ObjectInputStream (Usuario " + clienteSocket.getLocalAddress() + " Saiu)");
				//IMPLEMENTAR SAIDA AQUI
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientOutputStreams == null) ? 0 : clientOutputStreams.hashCode());
		result = prime * result + ((receptor == null) ? 0 : receptor.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "ServerMain [clientOutputStreams=" + clientOutputStreams + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ServerManager))
			return false;
		ServerManager other = (ServerManager) obj;
		if (clientOutputStreams == null) {
			if (other.clientOutputStreams != null)
				return false;
		} else if (!clientOutputStreams.equals(other.clientOutputStreams))
			return false;
		if (receptor == null) {
			if (other.receptor != null)
				return false;
		} else if (!receptor.equals(other.receptor))
			return false;
		if (t == null) {
			if (other.t != null)
				return false;
		} else if (!t.equals(other.t))
			return false;
		return true;
	}
	
}