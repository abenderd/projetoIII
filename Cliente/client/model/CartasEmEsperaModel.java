package client.model;

import java.util.List;

import javax.swing.AbstractListModel;

public class CartasEmEsperaModel extends AbstractListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> arrayCartasEmEspera;

	public int getSize() {
		return arrayCartasEmEspera.size();
	}

	public Object getElementAt(int index) {
		return arrayCartasEmEspera.get(index);
	}

	public List<String> getArrayCartasEmEspera() {
		return arrayCartasEmEspera;
	}

	public void setArrayCartasEmEspera(List<String> arrayCartasEmEspera) {
		this.arrayCartasEmEspera = arrayCartasEmEspera;
	}

	@Override
	public String toString() {
		return "CartasEmEsperaModel [arrayCartasEmEspera=" + arrayCartasEmEspera + "]";
	}

}
