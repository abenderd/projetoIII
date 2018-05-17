package server.conection;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import server.dao.CadastroDAO;
import server.dbo.CadastroDBO;

public class ServerConexao {
	public void conecta(ServerSocket receptor, Socket connection) {
		try {
			
			Transmissor t = new Transmissor();
			ObjectInputStream server = new ObjectInputStream(connection.getInputStream());
			String mensagem = "";
			
			while (true) {
				mensagem = String.valueOf(server.readObject());
				if (mensagem.toUpperCase().equals("FIM"))
					break;
				String[] temp;
				String delimiter = "/";
				temp = mensagem.split(delimiter);
				try {
					String var1 = temp[0];
					String var2 = temp[1];
					String var3 = temp[2];
					String var4 = temp[3];

					CadastroDBO cad = new CadastroDBO(var2, var4, var3);
					CadastroDAO cadDao = new CadastroDAO();

					System.out.println(var1 + "/" + var2 + "/" + var3 + "/" + var4);

					// (VAR1/VAR2/VAR3/VAR4)
					// CLIENTE
					// CAD - CADASTRO (CAD/EMAIL/NOME/SENHA)
					// LOG - LOGIN (LOG/EMAIL/NOME/SENHA)
					// SERVIDOR
					// CAD - (OK - JA EXISTE)
					// LOG - (EMAIL/LOGADO COM SUCESSO)

					if (var1.equals("CAD")) {
						try {
							cadDao.cadastro(cad);
						} catch (Exception e) {
							t.transmite(connection, var2 + "/" + e);
						}
						t.transmite(connection, var2 + "Cadastrado com sucesso");
					} else if (var1.equals("LOG")) {
						try {
							cad = cadDao.getUsuario(var2, var4);
							System.out.println(cad.toString());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							t.transmite(connection, var2 + "/" + e);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							t.transmite(connection, var2 + "/" + e);
						}
						t.transmite(connection, cad.getNome() + " Logado com sucesso");
					} else {
						t.transmite(connection, var2 + "MENSSAGEM INVALIDA");
					}
				} catch (Exception e) {
					t.transmite(connection, "MENSSAGEM INVALIDA");
					System.err.println("Menssagem invalida de: " + connection.toString());
				}

			}
			System.out.println("Encerrando conexao");
			server.close();
			// connection.close();
			// receptor.close();

		} catch (Exception erro) {
			System.err.println(erro.getMessage());
		}
	}

	@Override
	public String toString() {
		return "ServerConexao [hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}	
}
