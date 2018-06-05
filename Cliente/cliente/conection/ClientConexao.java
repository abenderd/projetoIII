package cliente.conection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ClientConexao {
	private Socket connection;
	private String ip;
	private ObjectInputStream server;
	private ObjectOutputStream transmissor;
	// (COMANDO/COMPLEMENTO1/COMPLEMENTO2/COMPLEMENTO3)
	// CADASTRAR JOGADOR - (CAD/EMAIL/NOME/SENHA) - RESPOSTA (SUC) ou (ERR)
	// JOGAR JOGADOR - (LOG/EMAIL/SENHA/NULL) - RESPOSTA (SUC) ou (ERR)
	// CRIAR PARTIDA - (CRI/NOME/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
	// CONSULTAR PARTIDA - (PAR/NOME/STATUS) - RESPOSTA (PAR/NOME/STATUS/NULL) ou
	// (EOP)
	// ENTRAR EM UM PARTIDA - (ENT/NOME/NULL/NULL) - RESPOSTA (SUC/SALDO/NULL/NULL)
	// ou (ERR)
	// APOSTA EM UMA JOGADA - (APO/VALOR/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
	// DISTRIBUIR 2 CARTAS AO JOGADOR - (CAR/NAIPE/VALOR/NULL) +
	// (CAR/NAIPE/VALOR/NULL)
	// COMPRAR CARTAS - (COM/NULL/NULL/NULL) - (CAR/NAIPE/VALOR/NULL) +? OU (EOC)
	// DEFINIR VENCEDORES - (WIN/NOME/EMAIL/NULL) + xWIN? + (EOW/SALDO/NULL/NULL)
	// SAIR DA PARTIDA - (SAI/NULL/NULL/NULL)

	public ClientConexao(String ip) throws UnknownHostException, IOException {
		this.ip = ip;
		abreConexao();
		// Thread t = new Thread(new ClientRecebe(connection));
		// t.start();
	}

	public void abreConexao() throws UnknownHostException, IOException {
		this.connection = new Socket(ip, 11111);
		server = null;
		transmissor = null;
	}

	public void Envia(String mensagem) {
		try {
			if (transmissor == null)
				transmissor = new ObjectOutputStream(connection.getOutputStream());
			transmissor.writeObject(mensagem);
			transmissor.flush();
			server = null;
			System.out.println("Enviado - " + mensagem);
		} catch (Exception erro) {
			System.err.println("ClientConexao - Envia - " + erro.getMessage());
		}
	}

	public String recebe1Msg() throws Exception {
		try {
			String mensagem;
			if (server == null)
				server = new ObjectInputStream(connection.getInputStream());
			mensagem = String.valueOf(server.readObject());
			return mensagem;
		} catch (Exception erro) {
			throw new Exception("ClientConexao - recebe1Msg - " + erro.getMessage());
		}
	}

	public ArrayList<String> recebeNMsg(String CondParada) throws Exception { // recebe msg ate parar
		try {
			ArrayList<String> msgs = new ArrayList<String>();
			String mensagem = null;
			if (server == null)
				server = new ObjectInputStream(connection.getInputStream());
			while (true) {
				mensagem = String.valueOf(server.readObject());
				if (!mensagem.equals(CondParada))
					break;
				System.out.println("Recebido mensagem - " + mensagem);
				msgs.add(mensagem);
			}
			return msgs;
		} catch (Exception erro) {
			throw new Exception("ClientConexao - recebeNMsg - " + erro.getMessage());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connection == null) ? 0 : connection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientConexao other = (ClientConexao) obj;
		if (connection == null) {
			if (other.connection != null)
				return false;
		} else if (!connection.equals(other.connection))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientConexao [connection= " + connection + "]";
	}

}