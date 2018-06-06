package server.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import server.database.Conexao;
import server.database.MeuResultSet;
import server.dbo.SaldoDBO;

public class SaldoDAO {

	public int getSaldo(String email) throws SQLException, Exception {
		try {
			String sql = "SELECT saldo FROM tbl_usuario WHERE eMail = '" + email + "'";
			Conexao.conexao.prepareStatement(sql);
			MeuResultSet resultado = (MeuResultSet) Conexao.conexao.executeQuery();
			if(resultado.first())
				return Integer.parseInt(resultado.getString("saldo"));
			else
				return -1;
		} catch (Exception e) {
			System.err.println("Erro para pegar Saldo - SaldoDAO getSaldo - " + e);
			return -1;
		}
	}
	
	public void setSaldo(String email, float valor) throws SQLException, Exception {
		try {
			String sql = "UPDATE tbl_usuario SET saldo =" + valor + " WHERE eMail = '" + email + "'";
			Conexao.conexao.prepareStatement(sql);
			MeuResultSet resultado = (MeuResultSet) Conexao.conexao.executeQuery();
		} catch (Exception e) {
			System.err.println("Erro para setar Saldo - SaldoDAO setSaldo - " + e);
		}
	}

	public SaldoDBO setSaldoEmergencial(String email) throws SQLException, Exception {

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
