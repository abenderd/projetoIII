package server.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import server.database.Conexao;
import server.database.MeuResultSet;
import server.dbo.SaldoDBO;

public class SaldoDAO {

	public SaldoDBO getSaldo(String email) throws SQLException, Exception {
		SaldoDBO saldoDBO = null;
		String sql;

		try {
			sql = "SELECT * FROM tbl_usuario WHERE eMail = '" + email + "'";

			Conexao.conexao.prepareStatement(sql);
			MeuResultSet resultado = (MeuResultSet) Conexao.conexao.executeQuery();

			saldoDBO = new SaldoDBO(resultado.getString("email"), resultado.getString("nome"), resultado.getString("dtUltimaJogada"), resultado.getFloat("saldo"));

		} catch (Exception e) {
			throw new Exception(e);
		}

		return saldoDBO;
	}

	public SaldoDBO setSaldoEmergencial(String email, Float saldo) throws SQLException, Exception {

		SaldoDBO setSaldoDBO = null;
		String sql;

		try {
			sql = "SELECT * FROM tbl_usuario WHERE eMail = '" + email + "'";

			Conexao.conexao.prepareStatement(sql);
			MeuResultSet resultado = (MeuResultSet) Conexao.conexao.executeQuery();

			setSaldoDBO = new SaldoDBO(resultado.getString("email"), resultado.getString("nome"), resultado.getString("dtUltimaJogada"), resultado.getFloat("saldo"));
			
			String dataUltimaJogada = resultado.getString("dtUltimaJogada");
			String pattern = "dd/MM/yyyy hh:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			java.util.Date date = sdf.parse(dataUltimaJogada);
			java.sql.Date sqlDate = null;
			sqlDate = new java.sql.Date(date.getTime());
			System.out.println(dataUltimaJogada);
			System.out.println(sdf);
			System.out.println(date);
			System.out.println(sqlDate);
			
			
			if (setSaldoDBO.getSaldo() == 0) {
				try {
					String sql2 = "INSERT INTO tbl_usuario (saldo, dtUltimaJogada) VALUES (?,?)";

					java.util.Date data = new java.util.Date();
					java.text.SimpleDateFormat dataFormatada = 
					     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String currentTime = dataFormatada.format(data);

					Conexao.conexao.prepareStatement(sql2);
					Conexao.conexao.setFloat(1, 200);
					Conexao.conexao.setString(2, currentTime);
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
		
		return setSaldoDBO;
	}
}
