package server.dao;

import java.io.IOException;
import java.sql.SQLException;

import server.model.Usuario;


public class CreditaConta implements Runnable {
	private Usuario cliente;
	private SaldoDAO saldo;

	public CreditaConta(Usuario cliente) throws IOException {
		this.cliente = cliente;
		saldo = new SaldoDAO();
	}

	@Override
	public void run() {
		try {
			//AGUARDA 20 MINUTOS
			Thread.currentThread();
			Thread.sleep(1200000);
			saldo.setSaldo(cliente.getEmail(), 200);
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			System.err.println("ERRO - Credita Conta Thread Sleep - " + e);
		} catch (SQLException e) {
			System.err.println("ERRO - Credita Conta SQL - " + e);
		} catch (NullPointerException e) {
			System.out.println("ERRO - Credita Conta NullPointer - " + e);
		} catch (Exception e) {
			System.err.println("ERRO - Credita Conta Unknow - " + e);
		}
	}
}
