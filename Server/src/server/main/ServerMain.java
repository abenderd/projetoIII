package server.main;

import java.net.Inet4Address;
import java.net.UnknownHostException;


public class ServerMain {

	public static void main(String[] args) {
		// PEGA O IP DO SERVIDOR
		try {
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
			ServerManager srv = new ServerManager();
		} catch (UnknownHostException e) {
			System.err.println("Erro na main, sem ip? " + e);
		}
	}
}
