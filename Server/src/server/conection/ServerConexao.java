package server.conection;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import server.dao.CadastroDAO;
import server.dao.SaldoDAO;
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
					SaldoDAO sadDao = new SaldoDAO();

					System.out.println(var1 + "/" + var2 + "/" + var3 + "/" + var4);

					//(COMANDO/COMPLEMENTO1/COMPLEMENTO2/COMPLEMENTO3)
					//CADASTRAR JOGADOR - (CAD/EMAIL/NOME/SENHA) - RESPOSTA (SUC) ou (ERR)
					//JOGAR JOGADOR - (LOG/EMAIL/SENHA/NULL) - RESPOSTA (SUC) ou (ERR)
					//CRIAR PARTIDA - (CRI/NOME/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
					//CONSULTAR PARTIDA - (PAR/NOME/STATUS) - RESPOSTA (PAR/NOME/STATUS/NULL) ou (EOP)
					//ENTRAR EM UM PARTIDA - (ENT/NOME/NULL/NULL) - RESPOSTA (SUC/SALDO/NULL/NULL) ou (ERR)
					//APOSTA EM UMA JOGADA - (APO/VALOR/NULL/NULL) - RESPOSTA (SUC) ou (ERR)
					//DISTRIBUIR 2 CARTAS AO JOGADOR - (CAR/NAIPE/VALOR/NULL) + (CAR/NAIPE/VALOR/NULL)
					//COMPRAR CARTAS - (COM/NULL/NULL/NULL) - (CAR/NAIPE/VALOR/NULL) +? OU (EOC)
					//DEFINIR VENCEDORES - (WIN/NOME/EMAIL/NULL) + xWIN? + (EOW/SALDO/NULL/NULL)
					//SAIR DA PARTIDA - (SAI/NULL/NULL/NULL)
					
					
					// CLIENTE
					// CAD - CADASTRO (CAD/EMAIL/NOME/SENHA)
					// LOG - LOGIN (LOG/EMAIL/NOME/SENHA)
					// SERVIDOR
					// CAD - (OK - JA EXISTE)
					// LOG - (EMAIL/LOGADO COM SUCESSO)
					
					switch (var1) {
					case "CAD":
						try {
							cadDao.cadastro(cad);
						} catch (Exception e) {
							t.transmite(connection, var2 + "/" + e);
						}
						t.transmite(connection, var2 + "Cadastrado com sucesso");
						break;
					case "LOG":
						try {
							cad = cadDao.getUsuario(var2, var3);
							System.out.println(cad.toString());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							t.transmite(connection, var2 + "/" + e);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							t.transmite(connection, var2 + "/" + e);
						}
						t.transmite(connection, cad.getNome() + " Logado com sucesso");
						break;
					case "CRI":
						
						break;
					case "PAR":
						break;
					case "ENT":
						break;
					case "APO":
						break;
					case "CAR":
						break;
					case "COM":
						break;
					case "WIN":
						break;
					case "SAI":
						break;
					default:
						t.transmite(connection, var2 + "MENSSAGEM INVALIDA");
						break;				
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
