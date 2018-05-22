package server.database;

import javax.swing.JOptionPane;

import server.dao.CadastroDAO;
import server.dao.SaldoDAO;

public class Conexao {

	public static final MeuPreparedStatement conexao;
	public static final CadastroDAO cadastroDao;
	public static final SaldoDAO saldoDao;

	static {
		MeuPreparedStatement _conexao = null;
		CadastroDAO _cadastroDao = null;
		SaldoDAO _saldoDao = null;

		try {
			_conexao = new MeuPreparedStatement("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/Projeto III?useTimezone=true&serverTimezone=UTC","root", "");

			_cadastroDao = new CadastroDAO();
			
			_saldoDao = new SaldoDAO();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro de conexao com o banco de dados." + e);
			System.err.println(e);
		}

		conexao = _conexao;
		cadastroDao = _cadastroDao;
		saldoDao = _saldoDao;
	}

	public static MeuPreparedStatement getConexao() {
		return conexao;
	}

	public static CadastroDAO getCadastrodao() {
		return cadastroDao;
	}
	
	public static SaldoDAO getSaldodao() {
		return saldoDao;
	}
}