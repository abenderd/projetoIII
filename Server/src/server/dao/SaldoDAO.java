package server.dao;

import java.sql.SQLException;
import java.sql.Timestamp;

import server.database.Conexao;
import server.database.MeuResultSet;
import server.dbo.SaldoDBO;

public class SaldoDAO {

	public SaldoDBO getSaldo(String email, Timestamp dtUltimaJogada, Float saldo) throws SQLException, Exception {
		SaldoDBO saldoDBO = null;
		String sql;

		try {
			sql = "SELECT * FROM tbl_usuario WHERE eMail = '" + email + "'";

			Conexao.conexao.prepareStatement(sql);
			MeuResultSet resultado = (MeuResultSet) Conexao.conexao.executeQuery();

			saldoDBO = new SaldoDBO(resultado.getString("email"), resultado.getString("senha"),
					resultado.getString("nome"), resultado.getFloat("saldo"), resultado.getTimestamp("dtUltimaJogada"));

			if (saldoDBO.getSaldo() == 0) {
				// TODO: Implementar busca por tempo maior que vinte minutos
				try {
					String sql2 = "INSERT INTO tbl_usuario (saldo, dtUltimaJogada) VALUES (?,?)";

					java.util.Date data = new java.util.Date();
					java.sql.Timestamp timestamp = new java.sql.Timestamp(data.getTime());

					Conexao.conexao.prepareStatement(sql2);
					Conexao.conexao.setFloat(1, 200);
					Conexao.conexao.setDate(2, timestamp);
					Conexao.conexao.executeUpdate();
					Conexao.conexao.commit();
					throw new Exception("Parabens voce ganhou 200 moedas!");
				} catch (SQLException e) {
					throw new Exception("Erro ao realizar cadastro." + e);
				}
			}

		} catch (Exception e) {
			throw new Exception(e);
		}

		return saldoDBO;
	}
}
