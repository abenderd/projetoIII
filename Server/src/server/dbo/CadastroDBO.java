package server.dbo;

import javax.swing.JOptionPane;

import server.dao.CadastroDAO;

public class CadastroDBO {
	CadastroDAO cadastroDao = new CadastroDAO();

	private String senha, email, nome;

	public CadastroDBO(String email, String senha, String nome) {
		this.email = email;
		this.senha = senha;
		this.nome = nome;
	}

	public void cadastroEmail(String email) {
		if (this.email == null || " ".equals(email)) {
			JOptionPane.showMessageDialog(null, "Email invalido ou nulo");
		} else {
			this.email = email;
		}
	}

	public void cadastroSenha(String senha) {
		if (this.senha == null || "".equals(senha)) {
			JOptionPane.showMessageDialog(null, "Senha invalida ou nula");
		} else {
			this.senha = senha;
		}
	}

	public void cadastroNome(String nome) {
		if (this.nome == null || "".equals(nome)) {
			JOptionPane.showMessageDialog(null, "Nome invalido ou nulo");
		} else {
			this.nome = nome;
		}
	}

	public void cadastroCsenha(String senha, String confirmacaoSenha) throws Exception {
		if (this.senha.equals(confirmacaoSenha)) {

		} else {
			JOptionPane.showMessageDialog(null, "Cadastro nao realizado senhas incompativeis");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cadastroDao == null) ? 0 : cadastroDao.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		CadastroDBO other = (CadastroDBO) obj;
		if (cadastroDao == null) {
			if (other.cadastroDao != null)
				return false;
		} else if (!cadastroDao.equals(other.cadastroDao))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CadastroDBO [cadastroDao=" + cadastroDao + ", senha=" + senha + ", email=" + email + ", nome=" + nome
				+ "]";
	}

	public String getSenha() {
		return senha;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}
}