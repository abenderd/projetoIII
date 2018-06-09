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
	
	
}
