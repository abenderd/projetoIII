package client.model;

import java.util.List;

import javax.swing.AbstractListModel;

import cliente.conection.ClientConexao;
import cliente.main.ClientManager;

public class PartidasEmEsperaModel extends AbstractListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> arrayPartidasEmEspera;

	public PartidasEmEsperaModel(ClientConexao conecta) {
		ClientManager cm = new ClientManager(conecta);
		arrayPartidasEmEspera = cm.consultarPartidasAguardando();
	}

	public int getSize() {
		return arrayPartidasEmEspera.size();
	}

	public Object getElementAt(int index) {
		return arrayPartidasEmEspera.get(index);
	}

	public List<String> getArrayPartidasEmEspera() {
		return arrayPartidasEmEspera;
	}

	public void setArrayPartidasEmEspera(List<String> arrayPartidasEmEspera) {
		this.arrayPartidasEmEspera = arrayPartidasEmEspera;
	}

	@Override
	public String toString() {
		return "PartidasEmEsperaModel [arrayPartidasEmEspera=" + arrayPartidasEmEspera + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrayPartidasEmEspera == null) ? 0 : arrayPartidasEmEspera.hashCode());
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
		PartidasEmEsperaModel other = (PartidasEmEsperaModel) obj;
		if (arrayPartidasEmEspera == null) {
			if (other.arrayPartidasEmEspera != null)
				return false;
		} else if (!arrayPartidasEmEspera.equals(other.arrayPartidasEmEspera))
			return false;
		return true;
	}
}
