package server.model;

public class Pote {
	private Usuario usuario;
	private float valor;

	public Pote(Usuario usuario, float valor) {
		super();
		this.usuario = usuario;
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public float getValor() {
		return valor;
	}
}
