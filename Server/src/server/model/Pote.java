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
}
