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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrayCartasEmEspera == null) ? 0 : arrayCartasEmEspera.hashCode());
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
		CartasEmEsperaModel other = (CartasEmEsperaModel) obj;
		if (arrayCartasEmEspera == null) {
			if (other.arrayCartasEmEspera != null)
				return false;
		} else if (!arrayCartasEmEspera.equals(other.arrayCartasEmEspera))
			return false;
		return true;
	}
	
	

}
