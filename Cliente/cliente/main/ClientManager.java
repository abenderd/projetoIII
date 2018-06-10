package cliente.main;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cliente.conection.ClientConexao;

public class ClientManager {

	private ClientConexao conecta;

	public ClientManager(ClientConexao conecta) {
		this.conecta = conecta;
	}

	public List<String> consultarPartidasIniciadas() {
		try {
			// CONSULTAR PARTIDA - (PAR/NOME/STATUS) - RESPOSTA (PAR/NOME/STATUS/NULL) ou
			// (EOP)
			String partidasIniciadas = "PAR/" + null + "/" + true + "/" + null;
			conecta.Envia(partidasIniciadas);
			System.out.println(partidasIniciadas + " Consultando partidas iniciadas.");
			return conecta.recebeNMsg("EOP/ / / ").stream().map(s -> s.split("/"))
					.filter(p -> p[2].equals("Em execucao")).map(p -> p[1]).collect(Collectors.toList());

		} catch (Exception e) {
			System.err.println(e);
		}
		return new LinkedList<>();
	}

	public List<String> consultarPartidasAguardando() {
		try {
			// CONSULTAR PARTIDA - (PAR/NOME/STATUS) - RESPOSTA (PAR/NOME/STATUS/NULL) ou
			// (EOP)
			String partidasIniciadas = "PAR/" + null + "/" + true + "/" + null;
			conecta.Envia(partidasIniciadas);
			System.out.println(partidasIniciadas + " Consultando partidas iniciadas.");
			return conecta.recebeNMsg("EOP/ / / ").stream().map(s -> s.split("/"))
					.filter(p -> p[2].equals("Aguardando")).map(p -> p[1]).collect(Collectors.toList());

		} catch (Exception e) {
			System.err.println(e);
		}
		return new LinkedList<>();
	}

	public ClientConexao getConecta() {
		return conecta;
	}

	public void setConecta(ClientConexao conecta) {
		this.conecta = conecta;
	}

	@Override
	public String toString() {
		return "ClientManager [conecta=" + conecta + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conecta == null) ? 0 : conecta.hashCode());
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
		ClientManager other = (ClientManager) obj;
		if (conecta == null) {
			if (other.conecta != null)
				return false;
		} else if (!conecta.equals(other.conecta))
			return false;
		return true;
	}
}
