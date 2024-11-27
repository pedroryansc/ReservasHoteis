package hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import cliente.ArvoreClientes;
import cliente.Cliente;
import enumerado.Cor;
import quarto.ArvoreQuartos;
import quarto.Quarto;
import reserva.ArvoreReservasCanceladas;
import reserva.Reserva;

public class Hotel {

	private int id;
	private String nome;
	private Cor cor;
	private Hotel esquerdo, direito, pai;
	private ArvoreQuartos quartos;
	private ArvoreClientes clientes;
	private ArvoreReservasCanceladas reservasCanceladas;
	
	public Hotel(int id, String nome) {
		this.id = id;
		this.nome = nome;
		this.cor = Cor.VERMELHO;
		
		this.quartos = new ArvoreQuartos();
		this.clientes = new ArvoreClientes();
		this.reservasCanceladas = new ArvoreReservasCanceladas();
	}
	
	public void inserirQuarto(int numero, int categoria) {
		quartos.inserir(numero, categoria);
	}
	
	public List<Quarto> listarQuartos(){
		return quartos.listar();
	}
	
	public boolean estaOcupado(int numQuarto, String dataCheckIn, String dataCheckOut) {
		return clientes.estaOcupado(numQuarto, dataCheckIn, dataCheckOut);
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
				estaDisponivel = !clientes.estaOcupadoRecursivo(clientes.getRaiz(), quarto.getNumero(), checkIn, checkOut);
				
				if(estaDisponivel)
					quartosDisponiveis.add(quarto);
			}
		}
		
		return quartosDisponiveis;
	}
	
	public void inserirReserva(String cpf, String nome, Quarto quarto, String dataCheckIn, String dataCheckOut) {
		clientes.inserirReserva(cpf, nome, quarto, dataCheckIn, dataCheckOut);
	}
	
	public List<Reserva> listarReservas() {
		return clientes.listarReservas();
	}
	
	public void cancelarReserva(String cpf, Reserva reserva) {
		
		// Quando duas reservas canceladas com mesmo check-in
		// (o que é possível) são adicionadas à árvore de 
		// reservas canceladas, está dando erro.
		
		reservasCanceladas.inserirReservaCancelada(reserva);
		
		clientes.cancelarReserva(cpf, reserva);
	}
	
	public List<Cliente> listarClientes(){
		return clientes.listarClientes();
	}
	
	public List<Reserva> listarReservasCanceladas(){
		return reservasCanceladas.listarCanceladas();
	}
	
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

	public ArvoreReservasCanceladas getReservasCanceladas() {
		return reservasCanceladas;
	}

	public void setReservasCanceladas(ArvoreReservasCanceladas reservasCanceladas) {
		this.reservasCanceladas = reservasCanceladas;
	}
	
}