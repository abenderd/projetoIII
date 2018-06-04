package server.conection;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Transmissor {

	public void transmite(Socket connection, String menssagem) {
		try {
			ObjectOutputStream transmissor = new ObjectOutputStream(connection.getOutputStream());
			transmissor.writeObject(menssagem);
			transmissor.flush();
			// transmissor.close();
		} catch (Exception erro) {
			System.err.println(erro.getMessage());
		}
	}	
}
