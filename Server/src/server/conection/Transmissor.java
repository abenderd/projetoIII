package server.conection;

import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Transmissor {

	public void transmite(Socket connection, String mensagem) {
		try {
			System.out.println("Transmitindo " + mensagem);
			OutputStreamWriter transmissor = new OutputStreamWriter(connection.getOutputStream());
			transmissor.write(mensagem + "\n");
			transmissor.flush();
			// transmissor.close();
		} catch (Exception erro) {
			System.err.println(erro.getMessage());
		}
	}
}
