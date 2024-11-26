package cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enumerado.Cor;
import reserva.Reserva;

public class Cliente {

	private String cpf;
	private String nome;
	private Cor cor;
	private Cliente esquerdo, direito, pai;
	private Reserva raiz;
	
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		this.cor = Cor.VERMELHO;
	}
	
	public Reserva inserirReservaRecursivo(Reserva atual, Reserva novaReserva) {
		// Caso base: se a posição for nula, insere a nova reserva
		if(atual == null)
			return novaReserva;
		
		// Se a data da nova reserva for anterior, insere à esquerda
		if(novaReserva.getDataCheckIn().isBefore(atual.getDataCheckIn())) {
			atual.setEsquerdo(inserirReservaRecursivo(atual.getEsquerdo(), novaReserva));
			atual.getEsquerdo().setPai(atual);
		}
		// Se a data da nova reserva for posterior, insere à direita
		else if(novaReserva.getDataCheckIn().isAfter(atual.getDataCheckIn())) {
			atual.setDireito(inserirReservaRecursivo(atual.getDireito(), novaReserva));
			atual.getDireito().setPai(atual);
		}
		
		return atual;
	}
	
	public Reserva balancearArvore(Reserva reserva) {
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
		
		return reserva;
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
	
	public boolean estaOcupadoRecursivo(Reserva atual, int numQuarto, LocalDate checkIn, LocalDate checkOut) {
		if(atual != null) {
			if(atual.getNumQuarto() == numQuarto && verificaSobreposicaoDatas(atual, checkIn, checkOut)
				|| estaOcupadoRecursivo(atual.getEsquerdo(), numQuarto, checkIn, checkOut) ||
				estaOcupadoRecursivo(atual.getDireito(), numQuarto, checkIn, checkOut)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean verificaSobreposicaoDatas(Reserva atual, LocalDate checkIn, LocalDate checkOut) {
		return (atual.getDataCheckIn().isBefore(checkOut) || atual.getDataCheckIn().equals(checkOut))
				&& ((atual.getDataCheckOut().isAfter(checkIn)) || atual.getDataCheckOut().equals(checkIn));
	}
	
	public List<Reserva> listarReservasCliente(){
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		return listarReservasRecursivo(getRaiz(), reservas);
	}
	
	public List<Reserva> listarReservasRecursivo(Reserva atual, List<Reserva> reservas){
		if(atual != null) {
			listarReservasRecursivo(atual.getEsquerdo(), reservas);
			reservas.add(atual);
			listarReservasRecursivo(atual.getDireito(), reservas);
		}
		
		return reservas;
	}
	
	public Reserva cancelarReservaRecursivo(Reserva atual, LocalDate checkIn) {
		if(atual == null)
			return null;
		
		if(checkIn.isBefore(atual.getDataCheckIn()))
			atual.setEsquerdo(cancelarReservaRecursivo(atual.getEsquerdo(), checkIn));
		else if(checkIn.isAfter(atual.getDataCheckIn()))
			atual.setDireito(cancelarReservaRecursivo(atual.getDireito(), checkIn));
		else {
			if(atual.getEsquerdo() == null)
				return atual.getDireito();
			else if(atual.getDireito() == null)
				return atual.getEsquerdo();
			else {
				Reserva sucessor = encontrarSucessor(atual.getDireito());
				
				atual.setDataCheckIn(sucessor.getDataCheckIn());
				atual.setDataCheckOut(sucessor.getDataCheckOut());
				atual.setNomeCliente(sucessor.getNomeCliente());
				atual.setNumQuarto(sucessor.getNumQuarto());
				atual.setCategoriaQuarto(sucessor.getCategoriaQuarto());
				
				atual.setDireito(cancelarReservaRecursivo(atual.getDireito(), sucessor.getDataCheckIn()));
			}
		}
		
		return balancearArvore(atual);
	}
	
	private Reserva encontrarSucessor(Reserva atual) {
		while(atual.getEsquerdo() != null)
			atual = atual.getEsquerdo();
		
		return atual;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public Cliente getEsquerdo() {
		return esquerdo;
	}

	public void setEsquerdo(Cliente esquerdo) {
		this.esquerdo = esquerdo;
	}

	public Cliente getDireito() {
		return direito;
	}

	public void setDireito(Cliente direito) {
		this.direito = direito;
	}

	public Cliente getPai() {
		return pai;
	}

	public void setPai(Cliente pai) {
		this.pai = pai;
	}

	public Reserva getRaiz() {
		return raiz;
	}

	public void setRaiz(Reserva raiz) {
		this.raiz = raiz;
	}
	
}