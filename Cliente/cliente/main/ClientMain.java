package cliente.main;

import cliente.view.TelaConexaoCliente;

public class ClientMain {

	public static void main(String[] args) {

		TelaConexaoCliente t = new TelaConexaoCliente();
		t.create();

		/*
		// FALTA FAZER MODO GRAFICO, FEITO EM TEXTO PARA TESTE
		// AS MENSSAGENS QUE O SERVIDOR PRECISA RECEBER SAO ASSIM
		// (VAR1/VAR2/VAR3/VAR4)
		// VAR1 - LOG OU CAD
		// VAR2 - EMAIL
		// VAR3 - NOME
		// VAR4 - SENHA
		// LOG - LOGIN (LOG/EMAIL/NOME/SENHA)
		// CAD - CADASTRO (CAD/EMAIL/NOME/SENHA)
		try {
			String ip, menssagem;
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Digite o ip do servidor:\n");
			ip = reader.readLine();
			ClientConexao c = new ClientConexao(ip);
			while (true) {
				System.out.println("Digite a mensagem nos padroes VAR1/VAR2/VAR3/VAR4 :\n");
				menssagem = reader.readLine();
				c.Envia(menssagem);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}	
}
