package hotel;

import enumerado.Cor;
import quarto.Quarto;
import reserva.Reserva;

public class Hotel {

	private int id;
	private String nome;
	private Cor cor;
	private Hotel esquerdo, direito, pai;
	private Quarto raizQuarto;
	private Reserva raizReserva;
	
	public Hotel(int id, String nome) {
		this.id = id;
		this.nome = nome;
		this.cor = Cor.VERMELHO;
	}
	
	public void inserirQuarto(String numero, int categoria) {
		Quarto novoQuarto = new Quarto(numero, categoria);
		
		raizQuarto = inserirQuartoRecursivo(getRaizQuarto(), novoQuarto);
		
		balancearArvore(novoQuarto);
	}
	
	private Quarto inserirQuartoRecursivo(Quarto atual, Quarto novoQuarto) {
		return atual;
	}
	
	private <T> void balancearArvore(T nodo) {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Hotel getEsquerdo() {
		return esquerdo;
	}

	public void setEsquerdo(Hotel esquerdo) {
		this.esquerdo = esquerdo;
	}

	public Hotel getDireito() {
		return direito;
	}

	public void setDireito(Hotel direito) {
		this.direito = direito;
	}

	public Hotel getPai() {
		return pai;
	}

	public void setPai(Hotel pai) {
		this.pai = pai;
	}

	public Quarto getRaizQuarto() {
		return raizQuarto;
	}

	public void setRaizQuarto(Quarto raizQuarto) {
		this.raizQuarto = raizQuarto;
	}

	public Reserva getRaizReserva() {
		return raizReserva;
	}

	public void setRaizReserva(Reserva raizReserva) {
		this.raizReserva = raizReserva;
	}
	
}