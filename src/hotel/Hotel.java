package hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import cliente.Cliente;
import enumerado.Cor;
import ordenacao.ShellSort;
import quarto.Quarto;
import reserva.Reserva;

public class Hotel {

	private int id;
	private String nome;
	private Cor cor;
	private Hotel esquerdo, direito, pai;
	private Quarto raizQuarto;
	private Cliente raizCliente;
	
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
	
	public boolean estaOcupado(int numQuarto, String dataCheckIn, String dataCheckOut) {
		if(getRaizCliente() == null)
			return false;
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate checkIn = LocalDate.parse(dataCheckIn, formato);
		LocalDate checkOut = LocalDate.parse(dataCheckOut, formato);
		
		boolean estaOcupado = estaOcupadoRecursivo(getRaizCliente(), numQuarto, checkIn, checkOut);
		
		if(estaOcupado)
			System.out.println("\nO quarto " + numQuarto + " está reservado durante o período especificado. \n");
		
		return estaOcupado;
	}
	
	private boolean estaOcupadoRecursivo(Cliente atual, int numQuarto, LocalDate checkIn, LocalDate checkOut) {
		if(atual != null) {
			if(atual.estaOcupadoRecursivo(atual.getRaiz(), numQuarto, checkIn, checkOut)
				|| (atual.getEsquerdo() != null && atual.estaOcupadoRecursivo(atual.getEsquerdo().getRaiz(), numQuarto, checkIn, checkOut))
				|| (atual.getDireito() != null && atual.estaOcupadoRecursivo(atual.getDireito().getRaiz(), numQuarto, checkIn, checkOut)))
				return true;
		}
		
		return false;
	}
	
	public List<Quarto> listarQuartosDisponiveis(String dataCheckIn, String dataCheckOut, int opcaoCategoria){
		List<Quarto> quartosCategoria = new ArrayList<Quarto>();
		
		for(Quarto quarto : listarQuartos()) {
			if(quarto.getCategoria().getNumOpcao() == opcaoCategoria)
				quartosCategoria.add(quarto);
		}
		
		List<Quarto> quartosDisponiveis = new ArrayList<Quarto>();
		
		if(!quartosCategoria.isEmpty()) {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			LocalDate checkIn = LocalDate.parse(dataCheckIn, formato);
			LocalDate checkOut = LocalDate.parse(dataCheckOut, formato);
			
			boolean estaDisponivel;
			
			for(Quarto quarto : quartosCategoria) {
				estaDisponivel = !estaOcupadoRecursivo(getRaizCliente(), quarto.getNumero(), checkIn, checkOut);
				
				if(estaDisponivel)
					quartosDisponiveis.add(quarto);
			}
		}
		
		return quartosDisponiveis;
	}
	
	public void inserirReserva(String cpf, String nome, Quarto quarto, String dataCheckIn, String dataCheckOut) {
		Reserva novaReserva = new Reserva(quarto.getNumero(), dataCheckIn, dataCheckOut, nome, quarto.getCategoria());
		
		Cliente cliente = procurarCliente(cpf);
		
		boolean novoCliente = false;
		
		if(cliente == null) {
			cliente = new Cliente(cpf, nome);
			novoCliente = true;
		}
			
		raizCliente = inserirReservaRecursivo(getRaizCliente(), cliente, novaReserva);
		
		if(novoCliente)
			balancearArvoreClientes(cliente);
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
	
	private void balancearArvoreClientes(Cliente cliente) {
		Cliente pai, avo;
		
		// Enquanto o pai do nó for vermelho (e, portanto, houver violação)
		while(cliente != getRaizCliente() && cliente.getPai().getCor() == Cor.VERMELHO) {
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
						rotacaoEsquerdaCliente(pai);
						cliente = pai;
						pai = cliente.getPai();
					}
					// Caso 1.2.2: O nó é filho esquerdo (rotação à direita)
					rotacaoDireitaCliente(avo);
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
						rotacaoDireitaCliente(pai);
						cliente = pai;
						pai = cliente.getPai();
					}
					// Caso 2.2.2: O nó é filho direito (rotação à esquerda)
					rotacaoEsquerdaCliente(avo);
					Cor auxCor = pai.getCor();
					pai.setCor(avo.getCor());
					avo.setCor(auxCor);
					cliente = pai;
				}
			}
		}
		
		raizCliente.setCor(Cor.PRETO);
	}
	
	private void rotacaoEsquerdaCliente(Cliente cliente) {
		Cliente novoCliente = cliente.getDireito();
		
		cliente.setDireito(novoCliente.getEsquerdo());
		
		if(novoCliente.getEsquerdo() != null)
			novoCliente.getEsquerdo().setPai(cliente);
		
		novoCliente.setPai(cliente.getPai());
		
		if(cliente.getPai() == null)
			setRaizCliente(novoCliente);
		else if(cliente == cliente.getPai().getEsquerdo())
			cliente.getPai().setEsquerdo(novoCliente);
		else
			cliente.getPai().setDireito(novoCliente);
		
		novoCliente.setEsquerdo(cliente);
		cliente.setPai(novoCliente);
	}
	
	private void rotacaoDireitaCliente(Cliente cliente) {
		Cliente novoCliente = cliente.getEsquerdo();
		
		cliente.setEsquerdo(novoCliente.getDireito());
		
		if(novoCliente.getDireito() != null)
			novoCliente.getDireito().setPai(cliente);
		
		novoCliente.setPai(cliente.getPai());
		
		if(cliente.getPai() == null)
			setRaizCliente(novoCliente);
		else if(cliente == cliente.getPai().getDireito())
			cliente.getPai().setDireito(novoCliente);
		else
			cliente.getPai().setEsquerdo(novoCliente);
		
		novoCliente.setDireito(cliente);
		cliente.setPai(novoCliente);
	}
	
	public List<Reserva> listarReservas() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		listarReservasRecursivo(getRaizCliente(), reservas);
		
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
	
	public void cancelarReserva(String cpf, LocalDate checkIn) {
		Cliente cliente = procurarCliente(cpf);
		
		cliente.setRaiz(cliente.cancelarReservaRecursivo(cliente.getRaiz(), checkIn));
	}
	
	public List<Cliente> listarClientes(){
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		return listarClientesRecursivo(getRaizCliente(), clientes);
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
		if(getRaizCliente() == null)
			return null;
		
		return procurarClienteRecursivo(getRaizCliente(), cpf);
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

	public Cliente getRaizCliente() {
		return raizCliente;
	}

	public void setRaizCliente(Cliente raizCliente) {
		this.raizCliente = raizCliente;
	}
	
}