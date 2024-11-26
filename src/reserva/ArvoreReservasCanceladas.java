package reserva;

import java.util.ArrayList;
import java.util.List;

import enumerado.Cor;
import ordenacao.ShellSort;

public class ArvoreReservasCanceladas {

	private Reserva raiz;

	public void inserirReservaCancelada(Reserva reserva) {
		Reserva reservaCancelada = new Reserva(
			reserva.getNumQuarto(), reserva.getDataCheckInString(), reserva.getDataCheckOutString(), reserva.getNomeCliente(), reserva.getCategoriaQuarto()
		);
			
		setRaiz(inserirReservaCanceladaRecursivo(getRaiz(), reservaCancelada));
		
		balancearArvore(reservaCancelada);
	}
	
	private Reserva inserirReservaCanceladaRecursivo(Reserva atual, Reserva reservaCancelada) {
		// Caso base: se a posição for nula, insere a reserva cancelada
		if(atual == null)
			return reservaCancelada;
		
		// Se a data da reserva cancelada for anterior, insere à esquerda
		if(reservaCancelada.getDataCheckIn().isBefore(atual.getDataCheckIn())) {
			atual.setEsquerdo(inserirReservaCanceladaRecursivo(atual.getEsquerdo(), reservaCancelada));
			atual.getEsquerdo().setPai(atual);
		}
		// Se a data da reserva cancelada for posterior, insere à direita
		else if(reservaCancelada.getDataCheckIn().isAfter(atual.getDataCheckIn())) {
			atual.setDireito(inserirReservaCanceladaRecursivo(atual.getDireito(), reservaCancelada));
			atual.getDireito().setPai(atual);
		}
		
		return atual;
	}
	
	private void balancearArvore(Reserva reserva) {
		Reserva pai, avo;
		
		// Enquanto o pai do nó for vermelho (e, portanto, houver violação)
		while(reserva != getRaiz() && reserva.getPai().getCor() == Cor.VERMELHO) {
			pai = reserva.getPai();
			avo = pai.getPai();
			
			// Caso 1: Pai do nó é filho esquerdo do avô
			if(pai == avo.getEsquerdo()) {
				Reserva tio = avo.getDireito(); // Tio do nó
				
				// Caso 1.1: O tio é vermelho (necessário recolorir)
				if(tio != null && tio.getCor() == Cor.VERMELHO) {
					pai.setCor(Cor.PRETO); // Pai fica preto
					tio.setCor(Cor.PRETO); // Tio fica preto
					avo.setCor(Cor.VERMELHO); // Avô fica vermelho
					reserva = avo; // Continua verificando o avô
				}
				// Caso 1.2: O tio é preto ou nulo (necessário fazer rotações)
				else {
					// Caso 1.2.1: O nó é filho direito (rotação à esquerda)
					if(reserva == pai.getDireito()) {
						rotacaoEsquerda(pai);
						reserva = pai;
						pai = reserva.getPai();
					}
					// Caso 1.2.2: O nó é filho esquerdo (rotação à direita)
					rotacaoDireita(avo);
					Cor auxCor = pai.getCor();
					pai.setCor(avo.getCor());
					avo.setCor(auxCor);
					reserva = pai;
				}
			// Caso 2: Pai do nó é filho direito do avô
			} else {
				Reserva tio = avo.getEsquerdo();
				
				// Caso 2.1: O tio é vermelho (necessário recolorir)
				if(tio != null && tio.getCor() == Cor.VERMELHO) {
					pai.setCor(Cor.PRETO);
					tio.setCor(Cor.PRETO);
					avo.setCor(Cor.VERMELHO);
					reserva = avo;
				}
				// Caso 2.2: O tio é preto ou nulo (necessário fazer rotações)
				else {
					// Caso 2.2.1: O nó é filho esquerdo (rotação à direita)
					if(reserva == pai.getEsquerdo()) {
						rotacaoDireita(pai);
						reserva = pai;
						pai = reserva.getPai();
					}
					// Caso 2.2.2: O nó é filho direito (rotação à esquerda)
					rotacaoEsquerda(avo);
					Cor auxCor = pai.getCor();
					pai.setCor(avo.getCor());
					avo.setCor(auxCor);
					reserva = pai;
				}
			}
		}
		
		raiz.setCor(Cor.PRETO);
	}
	
	private void rotacaoEsquerda(Reserva reserva) {
		Reserva novaReserva = reserva.getDireito();
		
		reserva.setDireito(novaReserva.getEsquerdo());
		
		if(novaReserva.getEsquerdo() != null)
			novaReserva.getEsquerdo().setPai(reserva);
		
		novaReserva.setPai(reserva.getPai());
		
		if(reserva.getPai() == null)
			setRaiz(novaReserva);
		else if(reserva == reserva.getPai().getEsquerdo())
			reserva.getPai().setEsquerdo(novaReserva);
		else
			reserva.getPai().setDireito(novaReserva);
		
		novaReserva.setEsquerdo(reserva);
		reserva.setPai(novaReserva);
	}
	
	private void rotacaoDireita(Reserva reserva) {
		Reserva novaReserva = reserva.getEsquerdo();
		
		reserva.setEsquerdo(novaReserva.getDireito());
		
		if(novaReserva.getDireito() != null)
			novaReserva.getDireito().setPai(reserva);
		
		novaReserva.setPai(reserva.getPai());
		
		if(reserva.getPai() == null)
			setRaiz(novaReserva);
		else if(reserva == reserva.getPai().getDireito())
			reserva.getPai().setDireito(novaReserva);
		else
			reserva.getPai().setEsquerdo(novaReserva);
		
		novaReserva.setDireito(reserva);
		reserva.setPai(novaReserva);
	}
	
	public List<Reserva> listarCanceladas(){
		List<Reserva> reservasCanceladas = new ArrayList<Reserva>();
		
		listarCanceladasRecursivo(getRaiz(), reservasCanceladas);
		
		return ShellSort.ordenarPorCheckIn(reservasCanceladas);
	}
	
	private List<Reserva> listarCanceladasRecursivo(Reserva atual, List<Reserva> reservas){
		if(atual != null) {
			listarCanceladasRecursivo(atual.getEsquerdo(), reservas);
			reservas.add(atual);
			listarCanceladasRecursivo(atual.getDireito(), reservas);
		}
		
		return reservas;
	}
	
	public Reserva getRaiz() {
		return raiz;
	}

	public void setRaiz(Reserva raiz) {
		this.raiz = raiz;
	}
	
}
