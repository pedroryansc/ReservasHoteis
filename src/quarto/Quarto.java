package quarto;

import enumerado.Categoria;
import enumerado.Cor;

public class Quarto {

	private int numero;
	private Categoria categoria;
	private Cor cor;
	private Quarto esquerdo, direito, pai;
	
	public Quarto(int numero, int opcaoCategoria) {
		this.numero = numero;
		this.categoria = Categoria.getCategoria(opcaoCategoria);
		this.cor = Cor.VERMELHO;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Quarto getEsquerdo() {
		return esquerdo;
	}

	public void setEsquerdo(Quarto esquerdo) {
		this.esquerdo = esquerdo;
	}

	public Quarto getDireito() {
		return direito;
	}

	public void setDireito(Quarto direito) {
		this.direito = direito;
	}

	public Quarto getPai() {
		return pai;
	}

	public void setPai(Quarto pai) {
		this.pai = pai;
	}
	
}