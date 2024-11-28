package hotel;

import java.util.ArrayList;
import java.util.List;

import enumerado.Cor;

public class ArvoreHoteis {

	private Hotel raiz;
	
	// Cadastro de um novo hotel
	
	public void inserir(String nome) {
		Hotel novoHotel = new Hotel(maiorID() + 1, nome);
		
		raiz = inserirRecursivo(getRaiz(), novoHotel);
		
		balancearArvore(novoHotel); // Corrige eventuais violações das propriedades da Árvore Rubro-negra
	}
	
	private Hotel inserirRecursivo(Hotel atual, Hotel novoHotel) {
		// Caso base: se a posição for nula, insere o novo hotel
		if(atual == null)
			return novoHotel;
		
		// O ID do novo hotel sempre será maior que os outros e, por isso, ele sempre será inserido à direita
		atual.setDireito(inserirRecursivo(atual.getDireito(), novoHotel));
		atual.getDireito().setPai(atual);
				
		return atual;
	}
	
	private void balancearArvore(Hotel hotel) {
		Hotel pai, avo;
		
		// Enquanto o pai do hotel for vermelho (e, portanto, houver violação)
		while(hotel != getRaiz() && hotel.getPai().getCor() == Cor.VERMELHO) {
			pai = hotel.getPai();
			avo = pai.getPai();
			
			// Já que os hotéis sempre serão inseridos à direita, o pai do hotel sempre será filho direito do avô
			Hotel tio = avo.getEsquerdo();
			
			// Se o tio for vermelho, é necessário recolorir
			if(tio != null && tio.getCor() == Cor.VERMELHO) {
				pai.setCor(Cor.PRETO);
				tio.setCor(Cor.PRETO);
				avo.setCor(Cor.VERMELHO);
				hotel = avo;
			}
			// Se o tio for preto ou nulo e já que o hotel é filho direito, é feita uma rotação à esquerda
			else {
				rotacaoEsquerda(avo);
				Cor auxCor = pai.getCor();
				pai.setCor(avo.getCor());
				avo.setCor(auxCor);
				hotel = pai;
			}
		}
		
		raiz.setCor(Cor.PRETO);
	}
	
	private void rotacaoEsquerda(Hotel hotel) {
		Hotel novoHotel = hotel.getDireito();
		
		hotel.setDireito(novoHotel.getEsquerdo());
		
		if(novoHotel.getEsquerdo() != null)
			novoHotel.getEsquerdo().setPai(hotel);
		
		novoHotel.setPai(hotel.getPai());
		
		if(hotel.getPai() == null)
			setRaiz(novoHotel);
		else if(hotel == hotel.getPai().getEsquerdo())
			hotel.getPai().setEsquerdo(novoHotel);
		else
			hotel.getPai().setDireito(novoHotel);
		
		novoHotel.setEsquerdo(hotel);
		hotel.setPai(novoHotel);
	}
	
	// Listagem de hotéis
	
	public List<Hotel> listarHoteis(){
		List<Hotel> hoteis = new ArrayList<Hotel>();
		
		return listarHoteisRecursivo(getRaiz(), hoteis);
	}
	
	private List<Hotel> listarHoteisRecursivo(Hotel atual, List<Hotel> hoteis){
		if(atual != null) {
			listarHoteisRecursivo(atual.getEsquerdo(), hoteis);
			hoteis.add(atual);
			listarHoteisRecursivo(atual.getDireito(), hoteis);
		}
		
		return hoteis;
	}
	
	// Método que retorna o maior ID na árvore
	
	private int maiorID() {
		if(getRaiz() == null)
			return 0;
		else {
			Hotel atual = getRaiz();
			
			while(atual.getDireito() != null)
				atual = atual.getDireito();
			
			return atual.getId();
		}
	}
	
	/*
	// Método auxiliar para exibir a árvore
	public void mostrarArvore() {
		if(getRaiz() == null)
			System.out.println("A árvore está vazia.");
		else
			mostrarArvoreRecursiva(getRaiz(), "", true);
	}
		
	private void mostrarArvoreRecursiva(Hotel hotel, String prefixo, boolean isFilhoDireito) {
		if(hotel != null) {
			System.out.println(prefixo + (isFilhoDireito ? "D|--- " : "E|--- ") + hotel.getId() + " " + hotel.getNome() + " (" + hotel.getCor() + ")");
			String novoPrefixo = prefixo + (isFilhoDireito ? " " : "|");
			mostrarArvoreRecursiva(hotel.getEsquerdo(), novoPrefixo, false);
			mostrarArvoreRecursiva(hotel.getDireito(), novoPrefixo, true);
		}
	}
	*/

	public Hotel getRaiz() {
		return raiz;
	}

	public void setRaiz(Hotel raiz) {
		this.raiz = raiz;
	}
	
}