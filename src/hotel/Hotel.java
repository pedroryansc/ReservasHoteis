package hotel;

import java.util.ArrayList;
import java.util.List;

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
	
	public void inserirQuarto(int numero, int categoria) {
		// Se der tempo, adicionar um método que busca por um quarto
		// para verificar se ele já foi inserido
		
		Quarto novoQuarto = new Quarto(numero, categoria);
		
		raizQuarto = inserirQuartoRecursivo(getRaizQuarto(), novoQuarto);
		
		balancearArvoreQuartos(novoQuarto);
	}
	
	private Quarto inserirQuartoRecursivo(Quarto atual, Quarto novoQuarto) {
		// Caso base: se a posição for nula, insere o novo nó
		if(atual == null)
			return novoQuarto;
		
		// Se o valor do novo nó for menor, insere à esquerda
		if(novoQuarto.getNumero() < atual.getNumero()) {
			atual.setEsquerdo(inserirQuartoRecursivo(atual.getEsquerdo(), novoQuarto));
			atual.getEsquerdo().setPai(atual);
		}
		// Se o valor do novo nó for maior, insere à direita
		else if(novoQuarto.getNumero() > atual.getNumero()) {
			atual.setDireito(inserirQuartoRecursivo(atual.getDireito(), novoQuarto));
			atual.getDireito().setPai(atual);
		}
		
		return atual;
	}
	
	private void balancearArvoreQuartos(Quarto quarto) {
		Quarto pai, avo;
		
		// Enquanto o pai do nó for vermelho (e, portanto, houver violação)
		while(quarto != getRaizQuarto() && quarto.getPai().getCor() == Cor.VERMELHO) {
			pai = quarto.getPai();
			avo = pai.getPai();
			
			// Caso 1: Pai do nó é filho esquerdo do avô
			if(pai == avo.getEsquerdo()) {
				Quarto tio = avo.getDireito(); // Tio do nó
				
				// Caso 1.1: O tio é vermelho (necessário recolorir)
				if(tio != null && tio.getCor() == Cor.VERMELHO) {
					pai.setCor(Cor.PRETO); // Pai fica preto
					tio.setCor(Cor.PRETO); // Tio fica preto
					avo.setCor(Cor.VERMELHO); // Avô fica vermelho
					quarto = avo; // Continua verificando o avô
				}
				// Caso 1.2: O tio é preto ou nulo (necessário fazer rotações)
				else {
					// Caso 1.2.1: O nó é filho direito (rotação à esquerda)
					if(quarto == pai.getDireito()) {
						rotacaoEsquerdaQuarto(pai);
						quarto = pai;
						pai = quarto.getPai();
					}
					// Caso 1.2.2: O nó é filho esquerdo (rotação à direita)
					rotacaoDireitaQuarto(avo);
					Cor auxCor = pai.getCor();
					pai.setCor(avo.getCor());
					avo.setCor(auxCor);
					quarto = pai;
				}
			// Caso 2: Pai do nó é filho direito do avô
			} else {
				Quarto tio = avo.getEsquerdo();
				
				// Caso 2.1: O tio é vermelho (necessário recolorir)
				if(tio != null && tio.getCor() == Cor.VERMELHO) {
					pai.setCor(Cor.PRETO);
					tio.setCor(Cor.PRETO);
					avo.setCor(Cor.VERMELHO);
					quarto = avo;
				}
				// Caso 2.2: O tio é preto ou nulo (necessário fazer rotações)
				else {
					// Caso 2.2.1: O nó é filho esquerdo (rotação à direita)
					if(quarto == pai.getEsquerdo()) {
						rotacaoDireitaQuarto(pai);
						quarto = pai;
						pai = quarto.getPai();
					}
					// Caso 2.2.2: O nó é filho direito (rotação à esquerda)
					rotacaoEsquerdaQuarto(avo);
					Cor auxCor = pai.getCor();
					pai.setCor(avo.getCor());
					avo.setCor(auxCor);
					quarto = pai;
				}
			}
		}
		
		raizQuarto.setCor(Cor.PRETO);
	}
	
	private void rotacaoEsquerdaQuarto(Quarto quarto) {
		Quarto novoQuarto = quarto.getDireito();
		
		quarto.setDireito(novoQuarto.getEsquerdo());
		
		if(novoQuarto.getEsquerdo() != null)
			novoQuarto.getEsquerdo().setPai(quarto);
		
		novoQuarto.setPai(quarto.getPai());
		
		if(quarto.getPai() == null)
			setRaizQuarto(novoQuarto);
		else if(quarto == quarto.getPai().getEsquerdo())
			quarto.getPai().setEsquerdo(novoQuarto);
		else
			quarto.getPai().setDireito(novoQuarto);
		
		novoQuarto.setEsquerdo(quarto);
		quarto.setPai(novoQuarto);
	}
	
	private void rotacaoDireitaQuarto(Quarto quarto) {
		Quarto novoQuarto = quarto.getEsquerdo();
		
		quarto.setEsquerdo(novoQuarto.getDireito());
		
		if(novoQuarto.getDireito() != null)
			novoQuarto.getDireito().setPai(quarto);
		
		novoQuarto.setPai(quarto.getPai());
		
		if(quarto.getPai() == null)
			setRaizQuarto(novoQuarto);
		else if(quarto == quarto.getPai().getDireito())
			quarto.getPai().setDireito(novoQuarto);
		else
			quarto.getPai().setEsquerdo(novoQuarto);
		
		novoQuarto.setDireito(quarto);
		quarto.setPai(novoQuarto);
	}
	
	public List<Quarto> listarQuartos(){
		List<Quarto> quartos = new ArrayList<Quarto>();
		
		return listarQuartosRecursivo(getRaizQuarto(), quartos);
	}
	
	private List<Quarto> listarQuartosRecursivo(Quarto atual, List<Quarto> quartos){
		if(atual != null) {
			listarQuartosRecursivo(atual.getEsquerdo(), quartos);
			quartos.add(atual);
			listarQuartosRecursivo(atual.getDireito(), quartos);
		}
		
		return quartos;
	}
	
	// Método auxiliar para exibir a árvore
	public void mostrarArvore() {
		if(getRaizQuarto() == null)
			System.out.println("A árvore está vazia.");
		else
			mostrarArvoreRecursiva(getRaizQuarto(), "", true);
	}
		
	private void mostrarArvoreRecursiva(Quarto quarto, String prefixo, boolean isFilhoDireito) {
		if(quarto != null) {
			System.out.println(prefixo + (isFilhoDireito ? "D|--- " : "E|--- ") + quarto.getNumero() + "- " + quarto.getCor());
			String novoPrefixo = prefixo + (isFilhoDireito ? " " : "|");
			mostrarArvoreRecursiva(quarto.getEsquerdo(), novoPrefixo, false);
			mostrarArvoreRecursiva(quarto.getDireito(), novoPrefixo, true);
		}
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