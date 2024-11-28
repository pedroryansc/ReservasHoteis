package hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import cliente.ArvoreClientes;
import cliente.Cliente;
import enumerado.Cor;
import ordenacao.ShellSort;
import quarto.ArvoreQuartos;
import quarto.Quarto;
import reserva.Reserva;

public class Hotel {

	private int id;
	private String nome;
	private Cor cor;
	private Hotel esquerdo, direito, pai;
	private ArvoreQuartos quartos;
	private ArvoreClientes clientes;
	private List<Reserva> reservasCanceladas;
	
	public Hotel(int id, String nome) {
		this.id = id;
		this.nome = nome;
		this.cor = Cor.VERMELHO;
		
		this.quartos = new ArvoreQuartos();
		this.clientes = new ArvoreClientes();
		this.reservasCanceladas = new ArrayList<Reserva>();
	}
	
	// Cadastro de um novo quarto
	
	public void inserirQuarto(int numero, int categoria) {
		quartos.inserir(numero, categoria);
	}
	
	// Listagem dos quartos do hotel
	
	public List<Quarto> listarQuartos(){
		return quartos.listar();
	}
	
	public List<Quarto> listarQuartosPorReservas(){
		List<Quarto> quartos = listarQuartos();
		
		return ShellSort.ordenarPorQuantReservas(quartos);
	}
	
	// Método que verifica se um certo quarto está ocupado em um determinado período
	
	public boolean estaOcupado(int numQuarto, String dataCheckIn, String dataCheckOut) {
		return clientes.estaOcupado(numQuarto, dataCheckIn, dataCheckOut);
	}
	
	// Listagem dos quartos de uma certa categoria disponíveis em um determinado período
	
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
				estaDisponivel = !clientes.estaOcupadoRecursivo(clientes.getRaiz(), quarto.getNumero(), checkIn, checkOut);
				
				if(estaDisponivel)
					quartosDisponiveis.add(quarto);
			}
		}
		
		return quartosDisponiveis;
	}
	
	public Quarto procurarQuarto(int numQuarto) {
		return quartos.procurarQuarto(numQuarto);
	}
	
	// Método que calcula a taxa de ocupação do hotel em um determinado período
	
	public double calcularTaxaOcupacao(String dataInicio, String dataFim) {
		List<Quarto> quartos = listarQuartos();
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate inicio = LocalDate.parse(dataInicio, formato);
		LocalDate fim = LocalDate.parse(dataFim, formato);
		
		double quartosOcupados = 0;
		
		boolean estaOcupado;
		
		for(Quarto quarto : quartos) {
			estaOcupado = clientes.estaOcupadoRecursivo(clientes.getRaiz(), quarto.getNumero(), inicio, fim);
			
			if(estaOcupado)
				quartosOcupados++;
		}
		
		double taxaOcupacao = (quartosOcupados * 100) / quartos.size();
		
		return taxaOcupacao;
	}
	
	// Método que verifica a taxa de ocupação do hotel nos próximos 15 dias para, se for preciso, emitir um alerta
	
	public void verificarOcupacao() {
		if(!listarQuartos().isEmpty()) {
			LocalDate inicio = LocalDate.now();
			LocalDate fim = inicio.plusDays(15);
			
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			String dataInicio = inicio.format(formato);
			String dataFim = fim.format(formato);
			
			double taxaOcupacao = calcularTaxaOcupacao(dataInicio, dataFim);
			
			if(taxaOcupacao >= 90)
				System.out.println("Aviso: Nos próximos 15 dias, o hotel estará com a taxa de " + taxaOcupacao + "% de ocupação. \n");
		}
	}
	
	// Cadastro de uma nova reserva
	
	public void inserirReserva(String cpf, String nome, Quarto quarto, String dataCheckIn, String dataCheckOut) {
		quarto.maisQuantReservas();
		
		clientes.inserirReserva(cpf, nome, quarto, dataCheckIn, dataCheckOut);
	}
	
	// Listagem de reservas do hotel
	
	public List<Reserva> listarReservas() {
		return clientes.listarReservas();
	}
	
	// Cancelamento de uma reserva e sua adição no histórico de reservas canceladas
	
	public void cancelarReserva(String cpf, Reserva reserva) {
		Reserva reservaCancelada = new Reserva(
			reserva.getNumQuarto(), reserva.getDataCheckInString(), reserva.getDataCheckOutString(),
			reserva.getNomeCliente(), reserva.getCategoriaQuarto()
		);
		
		reservasCanceladas.add(reservaCancelada);
		
		reservasCanceladas = ShellSort.ordenarPorCheckIn(reservasCanceladas);
		
		Quarto quarto = procurarQuarto(reserva.getNumQuarto());
		quarto.menosQuantReservas();
		
		clientes.cancelarReserva(cpf, reserva);
	}
	
	// Listagem das reservas canceladas do hotel
	
	public List<Reserva> listarReservasCanceladas(){
		return reservasCanceladas;
	}
	
	// Listagem dos clientes do hotel
	
	public List<Cliente> listarClientes(){
		return clientes.listarClientes();
	}
	
	// Método que busca por um cliente a partir de seu CPF e o retorna se for encontrado
	
	public Cliente procurarCliente(String cpf) {
		return clientes.procurarCliente(cpf);
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

	public ArvoreQuartos getQuartos() {
		return quartos;
	}

	public void setQuartos(ArvoreQuartos quartos) {
		this.quartos = quartos;
	}

	public ArvoreClientes getClientes() {
		return clientes;
	}

	public void setClientes(ArvoreClientes clientes) {
		this.clientes = clientes;
	}
	
}