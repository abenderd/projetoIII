package server.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import server.dao.SaldoDAO;

public class Partida {
	private ArrayList<Usuario> usuarios;
	private ArrayList<Carta> baralho;
	private int contadorCartas;
	private ArrayList<Pote> pote;
	private String nomePartida;
	private boolean status; // false = Aguardando OU true = EmExecucao
	private SaldoDAO saldo;

	public Partida(String nomePartida, Usuario user) {
		this.saldo = new SaldoDAO();
		this.usuarios = new ArrayList<Usuario>();
		this.baralho = new ArrayList<Carta>();
		this.pote = new ArrayList<Pote>();
		this.contadorCartas = 0;
		this.nomePartida = nomePartida;
		this.status = false;
		this.usuarios.add(user);
		geraCartas();
	}

	public boolean Apostar(Usuario user, int valor) {
		for (int x = 0; x < usuarios.size(); x++) {
			if (user.getEmail().equals(usuarios.get(x).getEmail())){
				if (usuarios.get(x).getSaldo() >= valor) {
					int novoSaldo = usuarios.get(x).getSaldo() - valor;
					usuarios.get(x).setSaldo(novoSaldo);
					try {
						saldo.setSaldo(user.getEmail(), novoSaldo);
						Pote p = new Pote(usuarios.get(x), valor);
						pote.add(p);
						return true;
					} catch (SQLException e) {
						System.err.println("Erro para seta Saldo - Partida/Apostar (SQLException) - " + e);
					} catch (Exception e) {
						System.err.println("Erro para seta Saldo - Partida/Apostar (Unknow) - " + e);
					}
				}
			}
		}
		return false;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public Carta getCarta(String email) {
		Carta c = null;
		for (int x = 0; x < usuarios.size(); x++) {
			if (email.equals(usuarios.get(x).getEmail())) {
				if (contadorCartas >= 52) {
					geraCartas();
					contadorCartas = 0;
				}
				contadorCartas++;
				c = baralho.remove(0);
				usuarios.get(x).setCartaMao(c);
			}
		}
		return c;
	}

	public ArrayList<Pote> getPote() {
		return pote;
	}

	private void geraCartas() {
		for (int x = 0; x < 13; x++) {
			for (int y = 1; y < 5; y++) {
				Carta novaCarta = new Carta(x, y);
				baralho.add(novaCarta);
			}
		}
		Collections.shuffle(baralho);
	}

	public String getNomePartida() {
		return nomePartida;
	}

	public void addUsuario(Usuario user) {
		usuarios.add(user);
	}

	public void removeUsuario(String email) {
		for (int x = 0; x < usuarios.size(); x++) {
			if (email.equals(usuarios.get(x).getEmail()))
				usuarios.remove(x);
		}
	}

	public boolean iniciaPartida() {
		if (status)
			return true;
		else if (usuarios.size() < 1)
			return false;
		else {
			status = true;
			return true;
		}
	}

	public boolean fimRodada() {
		for (int x = 0; x < usuarios.size(); x++) {
			if (usuarios.get(x).isComprandoCartas())
				return false;
		}
		return true;
	}

	public int getValorPorte() { // So deve ser chamado quando a rodada terminar, pois zera o pote
		int valor = 0;
		for (int x = 0; x < pote.size(); x++) {
			valor = valor + pote.get(x).getValor();
		}
		this.pote = new ArrayList<Pote>();
		return valor;
	}

	public String getStatus() {
		if (status)
			return "Em execucao";
		return "Aguardando";
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
