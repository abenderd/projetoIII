package server.model;

public class Pote {
	private Usuario usuario;
	private int valor;

	public Pote(Usuario usuario, int valor) {
		super();
		this.usuario = usuario;
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public int getValor() {
		return valor;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Pote [usuario=" + usuario + ", valor=" + valor + "]";
	}

}
