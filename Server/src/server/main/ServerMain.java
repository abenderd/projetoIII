package server.main;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import server.conection.ServerConexao;

public class ServerMain {

	ArrayList<ServerConexao> clientOutputStreams;

	public static void main(String[] args) {
		// PEGA O IP DO SERVIDOR
		try {
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ServerMain().go();
	}

	private void go() {
		// Guarda os clientes
		clientOutputStreams = new ArrayList();

		try {
			ServerSocket receptor = new ServerSocket(11111);
			System.out.println("Servidor up");

			// Mantem o servidor escutando
			while (true) {
				Socket connection = receptor.accept();
				ServerConexao conexao = new ServerConexao();
				clientOutputStreams.add(conexao);
				Thread t = new Thread(new ClientHandler(conexao, receptor, connection));
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Cuida das conexoes com novos clientes
	private class ClientHandler implements Runnable {
		ServerConexao conexao;
		ServerSocket receptor;
		Socket connection;

		public ClientHandler(ServerConexao conexao, ServerSocket receptor, Socket connection) throws IOException {
			this.conexao = conexao;
			this.receptor = receptor;
			this.connection = connection;
		}

		@Override
		public void run() {
			try {
				System.out.println("Recebido uma nova conexao.");
				conexao.conecta(receptor, connection);
			} catch (NullPointerException e) {
				System.out.println("Usuario Desconectado");
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientOutputStreams == null) ? 0 : clientOutputStreams.hashCode());
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
		ServerMain other = (ServerMain) obj;
		if (clientOutputStreams == null) {
			if (other.clientOutputStreams != null)
				return false;
		} else if (!clientOutputStreams.equals(other.clientOutputStreams))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServerMain [clientOutputStreams=" + clientOutputStreams + "]";
	}
}
