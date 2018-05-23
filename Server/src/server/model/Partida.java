package server.model;

import java.util.ArrayList;
import java.util.Collections;

public class Partida {
	private ArrayList<Usuario> usuarios;
	private ArrayList<Carta> baralho;
	private int contadorCartas;
	private ArrayList<Pote> pote;
	
	public Partida(){
		usuarios = new ArrayList<Usuario>();
		baralho = new ArrayList<Carta>();
		pote = new ArrayList<Pote>();
		contadorCartas = 0;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public Carta getCarta() {
		if (contadorCartas >= 52){
			geraCartas();
			contadorCartas = 0;
		}
		contadorCartas++;
		return baralho.remove(0);
	}

	public ArrayList<Pote> getPote() {
		return pote;
	}
	
	public void geraCartas(){
		for(int x=0;x<52;x++){
			for(int y=1;y<5;y++){
				Carta novaCarta = new Carta(x,y);
				baralho.add(novaCarta);
			}
		}
		Collections.shuffle(baralho);
	}
}
