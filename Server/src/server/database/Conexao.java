package server.database;

import javax.swing.JOptionPane;

import server.dao.CadastroDAO;

public class Conexao {

	public static final MeuPreparedStatement conexao;
	public static final CadastroDAO cadastroDao;

	static {
		MeuPreparedStatement _conexao = null;
		CadastroDAO _cadastroDao = null;

		try {
			_conexao = new MeuPreparedStatement("com.mysql.jdbc.Drive", "jdbc:mysql://127.0.0.1:3306/ProjetoII?useTimezone=true&serverTimezone=UTC",
					"root", "");

			_cadastroDao = new CadastroDAO();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro de conexao com o banco de dados." + e);
			System.err.println(e);
		}

		conexao = _conexao;
		cadastroDao = _cadastroDao;
	}

	public static MeuPreparedStatement getConexao() {
		return conexao;
	}

	public static CadastroDAO getCadastrodao() {
		return cadastroDao;
	}
}