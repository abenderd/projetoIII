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

}
