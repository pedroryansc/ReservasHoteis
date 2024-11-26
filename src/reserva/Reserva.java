package reserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import enumerado.Categoria;
import enumerado.Cor;

public class Reserva {

	private LocalDate dataCheckIn;
	private LocalDate dataCheckOut;
	private String nomeCliente;
	private int numQuarto;
	private Categoria categoriaQuarto;
	private Cor cor;
	private Reserva esquerdo, direito, pai;
	private DateTimeFormatter formato;
	
	public Reserva(int numQuarto, String dataCheckIn, String dataCheckOut, String nomeCliente, Categoria categoriaQuarto) {
		this.numQuarto = numQuarto;
		
		formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		this.dataCheckIn = LocalDate.parse(dataCheckIn, formato);
		this.dataCheckOut = LocalDate.parse(dataCheckOut, formato);
		
		this.nomeCliente = nomeCliente;
		this.categoriaQuarto = categoriaQuarto;
	}

	public LocalDate getDataCheckIn() {
		return dataCheckIn;
	}
	
	public String getDataCheckInString() {
		return dataCheckIn.format(getFormato());
	}

	public void setDataCheckIn(LocalDate dataCheckIn) {
		this.dataCheckIn = dataCheckIn;
	}

	public LocalDate getDataCheckOut() {
		return dataCheckOut;
	}
	
	public String getDataCheckOutString() {
		return dataCheckOut.format(getFormato());
	}

	public void setDataCheckOut(LocalDate dataCheckOut) {
		this.dataCheckOut = dataCheckOut;
	}
	
	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public int getNumQuarto() {
		return numQuarto;
	}

	public void setNumQuarto(int numQuarto) {
		this.numQuarto = numQuarto;
	}

	public Categoria getCategoriaQuarto() {
		return categoriaQuarto;
	}

	public void setCategoriaQuarto(Categoria categoriaQuarto) {
		this.categoriaQuarto = categoriaQuarto;
	}
	
	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Reserva getEsquerdo() {
		return esquerdo;
	}

	public void setEsquerdo(Reserva esquerdo) {
		this.esquerdo = esquerdo;
	}

	public Reserva getDireito() {
		return direito;
	}

	public void setDireito(Reserva direito) {
		this.direito = direito;
	}

	public Reserva getPai() {
		return pai;
	}

	public void setPai(Reserva pai) {
		this.pai = pai;
	}
	
	private DateTimeFormatter getFormato() {
		return formato;
	}
	
}