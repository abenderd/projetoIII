package server.model;

import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.SaldoDAO;

public class Usuario {
	private String nome, email;
	private int saldo;
	private boolean comprandoCartas;
	private boolean aguardandoSaldo;
	private ArrayList<Carta> mao;
	private Socket clienteSocket;
	
	public Usuario(String nome, String email, Socket socket){
		this.clienteSocket = socket;
		this.nome = nome;
		this.email = email;
		this.mao = new ArrayList<Carta>();
		saldo = getSaldo();
		comprandoCartas = false;
	}
	
	public int getSaldo(){
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
		for(int x=0;x<mao.size();x++){
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

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public boolean isAguardandoSaldo() {
		return aguardandoSaldo;
	}

	public void setAguardandoSaldo(boolean aguardandoSaldo) {
		this.aguardandoSaldo = aguardandoSaldo;
	}
	
}
