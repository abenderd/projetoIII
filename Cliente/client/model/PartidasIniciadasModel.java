package client.model;

import java.util.List;

import javax.swing.AbstractListModel;

import cliente.conection.ClientConexao;
import cliente.main.ClientManager;

public class PartidasIniciadasModel extends AbstractListModel {

	List<String> arrayPartidasIniciadas;

	public PartidasIniciadasModel(ClientConexao conecta) {
		ClientManager cm = new ClientManager(conecta);
		arrayPartidasIniciadas = cm.consultarPartidasIniciadas();
	}

	public int getSize() {
		return arrayPartidasIniciadas.size();
	}

	public Object getElementAt(int index) {
		return arrayPartidasIniciadas.get(index);
	}

	public List<String> getArrayPartidasIniciadas() {
		return arrayPartidasIniciadas;
	}

	public void setArrayPartidasIniciadas(List<String> arrayPartidasIniciadas) {
		this.arrayPartidasIniciadas = arrayPartidasIniciadas;
	}

	@Override
	public String toString() {
		return "PartidasIniciadasModel [arrayPartidasIniciadas=" + arrayPartidasIniciadas + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrayPartidasIniciadas == null) ? 0 : arrayPartidasIniciadas.hashCode());
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
		PartidasIniciadasModel other = (PartidasIniciadasModel) obj;
		if (arrayPartidasIniciadas == null) {
			if (other.arrayPartidasIniciadas != null)
				return false;
		} else if (!arrayPartidasIniciadas.equals(other.arrayPartidasIniciadas))
			return false;
		return true;
	}
}
