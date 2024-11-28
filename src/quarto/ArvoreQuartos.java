package quarto;

import java.util.ArrayList;
import java.util.List;

import enumerado.Cor;

public class ArvoreQuartos {

	private Quarto raiz;

	// Cadastro de um novo quarto
	
	public void inserir(int numero, int categoria) {
		// Possível atualização: Adicionar um método que busca por um quarto
		// para verificar se ele já foi inserido
		
		Quarto novoQuarto = new Quarto(numero, categoria);
		
		setRaiz(inserirRecursivo(getRaiz(), novoQuarto));
		
		balancearArvore(novoQuarto);
	}
	
	private Quarto inserirRecursivo(Quarto atual, Quarto novoQuarto) {
		// Caso base: se a posição for nula, insere o novo quarto
		if(atual == null)
			return novoQuarto;
		
		// Se o número do novo quarto for menor, insere à esquerda
		if(novoQuarto.getNumero() < atual.getNumero()) {
			atual.setEsquerdo(inserirRecursivo(atual.getEsquerdo(), novoQuarto));
			atual.getEsquerdo().setPai(atual);
		}
		// Se o número do novo quarto for maior, insere à direita
		else if(novoQuarto.getNumero() > atual.getNumero()) {
			atual.setDireito(inserirRecursivo(atual.getDireito(), novoQuarto));
			atual.getDireito().setPai(atual);
		}
		
		return atual;
	}
	
	private void balancearArvore(Quarto quarto) {
		Quarto pai, avo;
		
		// Enquanto o pai do nó for vermelho (e, portanto, houver violação)
		while(quarto != getRaiz() && quarto.getPai().getCor() == Cor.VERMELHO) {
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
						rotacaoEsquerda(pai);
						quarto = pai;
						pai = quarto.getPai();
					}
					// Caso 1.2.2: O nó é filho esquerdo (rotação à direita)
					rotacaoDireita(avo);
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
						rotacaoDireita(pai);
						quarto = pai;
						pai = quarto.getPai();
					}
					// Caso 2.2.2: O nó é filho direito (rotação à esquerda)
					rotacaoEsquerda(avo);
					Cor auxCor = pai.getCor();
					pai.setCor(avo.getCor());
					avo.setCor(auxCor);
					quarto = pai;
				}
			}
		}
		
		raiz.setCor(Cor.PRETO);
	}
	
	private void rotacaoEsquerda(Quarto quarto) {
		Quarto novoQuarto = quarto.getDireito();
		
		quarto.setDireito(novoQuarto.getEsquerdo());
		
		if(novoQuarto.getEsquerdo() != null)
			novoQuarto.getEsquerdo().setPai(quarto);
		
		novoQuarto.setPai(quarto.getPai());
		
		if(quarto.getPai() == null)
			setRaiz(novoQuarto);
		else if(quarto == quarto.getPai().getEsquerdo())
			quarto.getPai().setEsquerdo(novoQuarto);
		else
			quarto.getPai().setDireito(novoQuarto);
		
		novoQuarto.setEsquerdo(quarto);
		quarto.setPai(novoQuarto);
	}
	
	private void rotacaoDireita(Quarto quarto) {
		Quarto novoQuarto = quarto.getEsquerdo();
		
		quarto.setEsquerdo(novoQuarto.getDireito());
		
		if(novoQuarto.getDireito() != null)
			novoQuarto.getDireito().setPai(quarto);
		
		novoQuarto.setPai(quarto.getPai());
		
		if(quarto.getPai() == null)
			setRaiz(novoQuarto);
		else if(quarto == quarto.getPai().getDireito())
			quarto.getPai().setDireito(novoQuarto);
		else
			quarto.getPai().setEsquerdo(novoQuarto);
		
		novoQuarto.setDireito(quarto);
		quarto.setPai(novoQuarto);
	}
	
	// Listagem de quartos de um hotel
	
	public List<Quarto> listar(){
		List<Quarto> quartos = new ArrayList<Quarto>();
		
		return listarQuartosRecursivo(getRaiz(), quartos);
	}
	
	private List<Quarto> listarQuartosRecursivo(Quarto atual, List<Quarto> quartos){
		if(atual != null) {
			listarQuartosRecursivo(atual.getEsquerdo(), quartos);
			quartos.add(atual);
			listarQuartosRecursivo(atual.getDireito(), quartos);
		}
		
		return quartos;
	}
	
	// Método que busca o quarto pelo seu número e o retorna se for encontrado
	
	public Quarto procurarQuarto(int numero) {
		if(getRaiz() == null)
			return null;
		
		return procurarQuartoRecursivo(getRaiz(), numero);
	}
	
	private Quarto procurarQuartoRecursivo(Quarto atual, int numero) {
		if(atual != null) {
			if(numero == atual.getNumero())
				return atual;
			else if(numero < atual.getNumero())
				return procurarQuartoRecursivo(atual.getEsquerdo(), numero);
			else
				return procurarQuartoRecursivo(atual.getDireito(), numero);
		}
		
		return null;
	}
	
	public Quarto getRaiz() {
		return raiz;
	}

	public void setRaiz(Quarto raiz) {
		this.raiz = raiz;
	}
	
}