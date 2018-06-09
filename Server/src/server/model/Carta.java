package server.model;

public class Carta {
	private int valor;
	private int nipe; // 1 -ouros 2- espada 3-copas 4-paus

	public Carta(int valor, int nipe) {
		super();
		this.valor = valor;
		this.nipe = nipe;
	}

	public int getValor() {
		return valor;
	}

	public int getNipe() {
		return nipe;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public void setNipe(int nipe) {
		this.nipe = nipe;
	}

	@Override
	public String toString() {
		return "Carta [valor=" + valor + ", nipe=" + nipe + "]";
	}

}
