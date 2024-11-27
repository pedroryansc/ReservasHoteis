package cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import enumerado.Cor;
import ordenacao.ShellSort;
import quarto.Quarto;
import reserva.Reserva;

public class ArvoreClientes {

	private Cliente raiz;

	public void inserirReserva(String cpf, String nome, Quarto quarto, String dataCheckIn, String dataCheckOut) {
		Reserva novaReserva = new Reserva(quarto.getNumero(), dataCheckIn, dataCheckOut, nome, quarto.getCategoria());
		
		Cliente cliente = procurarCliente(cpf);
		
		boolean novoCliente = false;
		
		if(cliente == null) {
			cliente = new Cliente(cpf, nome);
			novoCliente = true;
		}
			
		setRaiz(inserirReservaRecursivo(getRaiz(), cliente, novaReserva));
		
		if(novoCliente)
			balancearArvore(cliente);
	}
	
	private Cliente inserirReservaRecursivo(Cliente atual, Cliente cliente, Reserva novaReserva) {
		// Caso base: se a posição for nula, insere o novo cliente
		if(atual == null) {
			cliente.setRaiz(novaReserva);
			
			return cliente;
		}
		
		// Se o CPF do cliente for menor, insere à esquerda
		if(cliente.getCpf().compareTo(atual.getCpf()) < 0) {
			atual.setEsquerdo(inserirReservaRecursivo(atual.getEsquerdo(), cliente, novaReserva));
			atual.getEsquerdo().setPai(atual);
		}
		// Se o CPF do cliente for maior, insere à direita
		else if(cliente.getCpf().compareTo(atual.getCpf()) > 0) {
			atual.setDireito(inserirReservaRecursivo(atual.getDireito(), cliente, novaReserva));
			atual.getDireito().setPai(atual);
		}
		// Se o CPF do cliente for igual, o cliente já havia sido cadastrado no sistema
		else {
			atual.setRaiz(cliente.inserirReservaRecursivo(cliente.getRaiz(), novaReserva));
			
			atual.balancearArvore(novaReserva);
		}
		
		return atual;
	}
	
	private void balancearArvore(Cliente cliente) {
		Cliente pai, avo;
		
		// Enquanto o pai do nó for vermelho (e, portanto, houver violação)
		while(cliente != getRaiz() && cliente.getPai().getCor() == Cor.VERMELHO) {
			pai = cliente.getPai();
			avo = pai.getPai();
			
			// Caso 1: Pai do nó é filho esquerdo do avô
			if(pai == avo.getEsquerdo()) {
				Cliente tio = avo.getDireito(); // Tio do nó
				
				// Caso 1.1: O tio é vermelho (necessário recolorir)
				if(tio != null && tio.getCor() == Cor.VERMELHO) {
					pai.setCor(Cor.PRETO); // Pai fica preto
					tio.setCor(Cor.PRETO); // Tio fica preto
					avo.setCor(Cor.VERMELHO); // Avô fica vermelho
					cliente = avo; // Continua verificando o avô
				}
				// Caso 1.2: O tio é preto ou nulo (necessário fazer rotações)
				else {
					// Caso 1.2.1: O nó é filho direito (rotação à esquerda)
					if(cliente == pai.getDireito()) {
						rotacaoEsquerda(pai);
						cliente = pai;
						pai = cliente.getPai();
					}
					// Caso 1.2.2: O nó é filho esquerdo (rotação à direita)
					rotacaoDireita(avo);
					Cor auxCor = pai.getCor();
					pai.setCor(avo.getCor());
					avo.setCor(auxCor);
					cliente = pai;
				}
			// Caso 2: Pai do nó é filho direito do avô
			} else {
				Cliente tio = avo.getEsquerdo();
				
				// Caso 2.1: O tio é vermelho (necessário recolorir)
				if(tio != null && tio.getCor() == Cor.VERMELHO) {
					pai.setCor(Cor.PRETO);
					tio.setCor(Cor.PRETO);
					avo.setCor(Cor.VERMELHO);
					cliente = avo;
				}
				// Caso 2.2: O tio é preto ou nulo (necessário fazer rotações)
				else {
					// Caso 2.2.1: O nó é filho esquerdo (rotação à direita)
					if(cliente == pai.getEsquerdo()) {
						rotacaoDireita(pai);
						cliente = pai;
						pai = cliente.getPai();
					}
					// Caso 2.2.2: O nó é filho direito (rotação à esquerda)
					rotacaoEsquerda(avo);
					Cor auxCor = pai.getCor();
					pai.setCor(avo.getCor());
					avo.setCor(auxCor);
					cliente = pai;
				}
			}
		}
		
		raiz.setCor(Cor.PRETO);
	}
	
	private void rotacaoEsquerda(Cliente cliente) {
		Cliente novoCliente = cliente.getDireito();
		
		cliente.setDireito(novoCliente.getEsquerdo());
		
		if(novoCliente.getEsquerdo() != null)
			novoCliente.getEsquerdo().setPai(cliente);
		
		novoCliente.setPai(cliente.getPai());
		
		if(cliente.getPai() == null)
			setRaiz(novoCliente);
		else if(cliente == cliente.getPai().getEsquerdo())
			cliente.getPai().setEsquerdo(novoCliente);
		else
			cliente.getPai().setDireito(novoCliente);
		
		novoCliente.setEsquerdo(cliente);
		cliente.setPai(novoCliente);
	}
	
	private void rotacaoDireita(Cliente cliente) {
		Cliente novoCliente = cliente.getEsquerdo();
		
		cliente.setEsquerdo(novoCliente.getDireito());
		
		if(novoCliente.getDireito() != null)
			novoCliente.getDireito().setPai(cliente);
		
		novoCliente.setPai(cliente.getPai());
		
		if(cliente.getPai() == null)
			setRaiz(novoCliente);
		else if(cliente == cliente.getPai().getDireito())
			cliente.getPai().setDireito(novoCliente);
		else
			cliente.getPai().setEsquerdo(novoCliente);
		
		novoCliente.setDireito(cliente);
		cliente.setPai(novoCliente);
	}
	
	public List<Reserva> listarReservas() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		listarReservasRecursivo(getRaiz(), reservas);
		
		return ShellSort.ordenarPorCheckIn(reservas);
	}
	
	private List<Reserva> listarReservasRecursivo(Cliente atual, List<Reserva> reservas){
		if(atual != null) {
			listarReservasRecursivo(atual.getEsquerdo(), reservas);
			atual.listarReservasRecursivo(atual.getRaiz(), reservas);
			listarReservasRecursivo(atual.getDireito(), reservas);
		}
		
		return reservas;
	}
	
	public void cancelarReserva(String cpf, Reserva reserva) {
		Cliente cliente = procurarCliente(cpf);
		
		cliente.setRaiz(cliente.cancelarReservaRecursivo(cliente.getRaiz(), reserva.getDataCheckIn()));
	}
	
	public List<Cliente> listarClientes(){
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		return listarClientesRecursivo(getRaiz(), clientes);
	}
	
	private List<Cliente> listarClientesRecursivo(Cliente atual, List<Cliente> clientes){
		if(atual != null) {
			listarClientesRecursivo(atual.getEsquerdo(), clientes);
			clientes.add(atual);
			listarClientesRecursivo(atual.getDireito(), clientes);
		}
		
		return clientes;
	}
	
	public Cliente procurarCliente(String cpf) {
		if(getRaiz() == null)
			return null;
		
		return procurarClienteRecursivo(getRaiz(), cpf);
	}
	
	private Cliente procurarClienteRecursivo(Cliente atual, String cpf) {
		if(atual != null) {
			if(cpf.compareTo(atual.getCpf()) == 0)
				return atual;
			else if(cpf.compareTo(atual.getCpf()) < 0)
				return procurarClienteRecursivo(atual.getEsquerdo(), cpf);
			else
				return procurarClienteRecursivo(atual.getDireito(), cpf);
		}
		
		return null;
	}
	
	public boolean estaOcupado(int numQuarto, String dataCheckIn, String dataCheckOut) {
		if(getRaiz() == null)
			return false;
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate checkIn = LocalDate.parse(dataCheckIn, formato);
		LocalDate checkOut = LocalDate.parse(dataCheckOut, formato);
		
		boolean estaOcupado = estaOcupadoRecursivo(getRaiz(), numQuarto, checkIn, checkOut);
		
		if(estaOcupado)
			System.out.println("\nO quarto " + numQuarto + " está reservado durante o período especificado. \n");
		
		return estaOcupado;
	}
	
	public boolean estaOcupadoRecursivo(Cliente atual, int numQuarto, LocalDate checkIn, LocalDate checkOut) {
		if(atual != null) {
			if(atual.estaOcupadoRecursivo(atual.getRaiz(), numQuarto, checkIn, checkOut)
				|| (atual.getEsquerdo() != null && atual.estaOcupadoRecursivo(atual.getEsquerdo().getRaiz(), numQuarto, checkIn, checkOut))
				|| (atual.getDireito() != null && atual.estaOcupadoRecursivo(atual.getDireito().getRaiz(), numQuarto, checkIn, checkOut)))
				return true;
		}
		
		return false;
	}
	
	public Cliente getRaiz() {
		return raiz;
	}

	public void setRaiz(Cliente raiz) {
		this.raiz = raiz;
	}
	
}