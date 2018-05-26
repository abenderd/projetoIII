package server.model;

import java.sql.SQLException;

import server.dao.SaldoDAO;

public class Usuario {
	private String nome, email;
	private int saldo;
	
	public Usuario(String nome, String email){
		this.nome = nome;
		this.email = email;
		saldo = getSaldo();
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
}
