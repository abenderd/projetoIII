package server.main;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import server.conection.ServerConexao;

public class ServerMain {

	

	public static void main(String[] args) {
		// PEGA O IP DO SERVIDOR
		try {
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServerManager srv = new ServerManager();
	}
}
