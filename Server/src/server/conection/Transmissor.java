package server.conection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Transmissor {

	public void transmite(Socket connection, String menssagem) {
		try {
			ObjectOutputStream transmissor = new ObjectOutputStream(connection.getOutputStream());

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			transmissor.writeObject(menssagem);
			transmissor.flush();
			// transmissor.close();
		} catch (Exception erro) {
			System.err.println(erro.getMessage());
		}
	}	
}
