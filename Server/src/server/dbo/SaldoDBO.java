package server.dbo;

import server.dao.SaldoDAO;

public class SaldoDBO {

	
	SaldoDAO saldoDAO = new SaldoDAO();

	private String email, nome, data;
	
	private Float saldo;
	
	public SaldoDBO(String email, String nome, String data, Float saldo) {
		this.email = email;
		this.data = data;
		this.nome = nome;
		this.saldo = saldo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
		result = prime * result + ((saldoDAO == null) ? 0 : saldoDAO.hashCode());
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
		SaldoDBO other = (SaldoDBO) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (saldo == null) {
			if (other.saldo != null)
				return false;
		} else if (!saldo.equals(other.saldo))
			return false;
		if (saldoDAO == null) {
			if (other.saldoDAO != null)
				return false;
		} else if (!saldoDAO.equals(other.saldoDAO))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SaldoDBO [saldoDAO=" + saldoDAO + ", email=" + email + ", nome=" + nome + ", data=" + data + ", saldo="
				+ saldo + "]";
	}
	
	

}
