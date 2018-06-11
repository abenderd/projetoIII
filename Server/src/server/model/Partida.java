package server.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import server.dao.SaldoDAO;

public class Partida implements Cloneable{
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
			if (user.getEmail().equals(usuarios.get(x).getEmail())) {
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
		else if (usuarios.size() < 2)
			return false;
		else {
			status = true;
			return true;
		}
	}
	
	public boolean qtdeUsuario() {
		if (usuarios.size() < 2 || usuarios.size() > 7) {
			return false;
		}
		return true;
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

	public ArrayList<Carta> getBaralho() {
		return baralho;
	}

	public void setBaralho(ArrayList<Carta> baralho) {
		this.baralho = baralho;
	}

	public int getContadorCartas() {
		return contadorCartas;
	}

	public void setContadorCartas(int contadorCartas) {
		this.contadorCartas = contadorCartas;
	}

	public SaldoDAO getSaldo() {
		return saldo;
	}

	public void setSaldo(SaldoDAO saldo) {
		this.saldo = saldo;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void setPote(ArrayList<Pote> pote) {
		this.pote = pote;
	}

	public void setNomePartida(String nomePartida) {
		this.nomePartida = nomePartida;
	}

	@Override
	public String toString() {
		return "Partida [usuarios=" + usuarios + ", baralho=" + baralho + ", contadorCartas=" + contadorCartas
				+ ", pote=" + pote + ", nomePartida=" + nomePartida + ", status=" + status + ", saldo=" + saldo + "]";
	}
	
	public Partida clonaPartida(){
		try {
			return (Partida) this.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("Erro ao clonar Partida - " + e);
			return null;
		}
	}
	
	/*public Object clone ()
    {
    	Partida p = null;
    	
    	try {
			p = new Partida(this);
		} catch (Exception e) {
			System.err.println("Erro ao clonar Partida - " + e);
		}
    	
    	return p;
    }
	
	public Partida (Partida modelo) throws Exception
    {
    	if (modelo==null)
            throw new Exception ("Modelo nao fornecido");

    	modelo.usuarios = this.usuarios;
    	modelo.baralho = this.baralho;
    	modelo.contadorCartas = this.contadorCartas;
    	modelo.pote = this.pote;
    	modelo.nomePartida = this.nomePartida;
    	modelo.status = this.status;
    	modelo.saldo = this.saldo;
    	
        // inicia a instancia recem criada com NEW,
        // de forma que seja INDEPENDENTE de this,
        // porem com dados em tudo iguais aos presentes
        // em this
    }*/
}
