package server.model;

import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.SaldoDAO;

public class Usuario {
	private String nome, email;
	private float saldo;
	private boolean comprandoCartas;
	private boolean aguardandoSaldo;
	private ArrayList<Carta> mao;
	private Socket clienteSocket;

	public Usuario(String nome, String email, Socket socket) {
		this.clienteSocket = socket;
		this.nome = nome;
		this.email = email;
		this.mao = new ArrayList<Carta>();
		saldo = getSaldo();
		comprandoCartas = false;
	}

	public int getSaldo() {
		SaldoDAO s = new SaldoDAO();
		try {
			return s.getSaldo(email);
		} catch (SQLException e) {
			System.err.println("ERRO - Usuario getSaldo (SQL?) - " + e);
		} catch (Exception e) {
			System.err.println("ERRO - Usuario getSaldo (Unknow) - " + e);
		}
		return -1;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public int getValorMao() {
		int valor = 0;
		for (int x = 0; x < mao.size(); x++) {
			valor = valor + mao.get(x).getValor();
		}
		return valor;
	}

	public Socket getClienteSocket() {
		return clienteSocket;
	}

	public boolean isComprandoCartas() {
		return comprandoCartas;
	}

	public void setComprandoCartas(boolean comprandoCartas) {
		this.comprandoCartas = comprandoCartas;
	}

	public void setCartaMao(Carta mao) {
		this.mao.add(mao);
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public boolean isAguardandoSaldo() {
		return aguardandoSaldo;
	}

	public void setAguardandoSaldo(boolean aguardandoSaldo) {
		this.aguardandoSaldo = aguardandoSaldo;
	}

	public ArrayList<Carta> getMao() {
		return mao;
	}

	public void setMao(ArrayList<Carta> mao) {
		this.mao = mao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setClienteSocket(Socket clienteSocket) {
		this.clienteSocket = clienteSocket;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", email=" + email + ", saldo=" + saldo + ", comprandoCartas="
				+ comprandoCartas + ", aguardandoSaldo=" + aguardandoSaldo + ", mao=" + mao + ", clienteSocket="
				+ clienteSocket + "]";
	}

}
