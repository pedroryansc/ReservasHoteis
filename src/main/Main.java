package main;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import cliente.Cliente;
import enumerado.Categoria;
import hotel.ArvoreHoteis;
import hotel.Hotel;
import quarto.Quarto;
import reserva.Reserva;

public class Main {
	
	public static void verificaValidade(boolean invalido) {
		if(invalido)
			System.out.println("\nErro: Opção inválida\n");
	}
	
	public static boolean verificaData(String data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		try {
			LocalDate.parse(data, formato);
			
			return true;
		} catch(Exception e) {
			System.out.println("\nErro: Data inválida\n");
			
			return false;
		}
	}
	
	public static void main(String[] args) {
		
		Scanner entrada = new Scanner(System.in);
		
		// Criação da árvore de hotéis
		ArvoreHoteis redeHoteis = new ArvoreHoteis();
		
		redeHoteis.inserir("Hilton Hotel");
		
		List<Hotel> listaHoteis = redeHoteis.listarHoteis();
		
		listaHoteis.get(0).inserirQuarto(501, 1);
		
		List<Quarto> listaQuartos = listaHoteis.get(0).listarQuartos();
		
		listaHoteis.get(0).inserirReserva("09341570905", "Pedro Ryan Coelho Iplinski", listaQuartos.get(0), "25/11/2024", "27/11/2024");
		
		/*
		redeHoteis.inserir("Rosewood São Paulo");
		redeHoteis.inserir("Copacabana Palace");
		redeHoteis.inserir("Hilton Hotel");
		redeHoteis.inserir("Rosewood São Paulo");
		redeHoteis.inserir("Copacabana Palace");
		redeHoteis.inserir("Hilton Hotel");
		redeHoteis.inserir("Rosewood São Paulo");
		redeHoteis.inserir("Copacabana Palace");
		redeHoteis.inserir("Hilton Hotel");
		redeHoteis.inserir("Rosewood São Paulo");
		redeHoteis.inserir("Copacabana Palace");
		
		redeHoteis.mostrarArvore();
		*/
		
		boolean invalido;
		
		int opcaoSistema = 1;
		
		while(opcaoSistema > 0) { // Estrutura de repetição que mantém a execução do sistema
			
			int opcaoHotel = 1;
			
			// Menu principal
			
			System.out.println("The Data Structure Hotels \n");
			
			do {
				System.out.println("O que você gostaria de fazer?: \n"
					+ "(1) Cadastrar um novo hotel \n(2) Acessar hotéis \n(0) Encerrar Sistema");
				
				opcaoSistema = entrada.nextInt();
				
				invalido = opcaoSistema < 0 || opcaoSistema > 2;
				
				verificaValidade(invalido);
			} while(invalido);
			
			System.out.println();
			
			if(opcaoSistema == 1) {
				
				// Cadastrar novo hotel
				
				System.out.println("Cadastro de Hotel \n");
				
				entrada.nextLine();
				
				System.out.println("Nome do hotel (ou ENTER para cancelar):");
				String nome = entrada.nextLine();
				
				if(nome.equals(""))
					System.out.println("Cadastro cancelado.");
				else {
					redeHoteis.inserir(nome);
					
					System.out.println("\nHotel cadastrado com sucesso!");
				}
				
				System.out.println();
			} else if(opcaoSistema == 2) {
				
				// Acessar hotéis
				
				while(opcaoHotel > 0) {
					
					int opcao = 1;
					
					System.out.println("Hotéis \n");
					
					List<Hotel> hoteis = redeHoteis.listarHoteis();
					
					if(hoteis.isEmpty()) {
						System.out.println("Nenhum hotel foi cadastrado.");
						
						opcaoHotel = 0;
					} else {
						do {
							System.out.println("Qual hotel você quer acessar?");
							for(int i = 0; i < hoteis.size(); i++)
								System.out.println("("+ hoteis.get(i).getId() +") " + hoteis.get(i).getNome());
							System.out.println("(0) Voltar");
							
							opcaoHotel = entrada.nextInt();
							
							invalido = opcaoHotel < 0 || opcaoHotel > hoteis.size();
									
							verificaValidade(invalido);
						} while(invalido);
						
						System.out.println();
						
						if(opcaoHotel > 0) {
							
							// Menu do hotel
							
							Hotel hotel = hoteis.get(opcaoHotel - 1);
							
							while(opcao > 0) {
								int opcaoRelatorio = 1;
								
								System.out.println("Menu do " + hotel.getNome() + "\n");
								
								hotel.verificarOcupacao();
								
								do {
									System.out.println("O que você gostaria de fazer? \n"
										+ "(1) Reservar quarto \n(2) Consultar reservas \n(3) Consultar reservas por cliente \n"
										+ "(4) Cancelar reserva \n(5) Histórico de reservas canceladas \n(6) Cadastrar quarto \n"
										+ "(7) Consultar quartos \n(8) Consultar disponibilidade de quartos \n(9) Consultar relatórios \n"
										+ "(0) Voltar");
									
									opcao = entrada.nextInt();
									
									invalido = opcao < 0 || opcao > 9;
									
									verificaValidade(invalido);
								} while(invalido);
								
								System.out.println();
								
								if(opcao == 1) {
									
									// Cadastrar reserva
									
									System.out.println("Reserva de Quarto \n");
									
									entrada.nextLine();
									
									List<Quarto> quartos = hotel.listarQuartos();
									
									if(quartos.isEmpty()) {
										System.out.println("Nenhum quarto foi cadastrado. Para fazer uma reserva, primeiro cadastre um quarto.");
										
										System.out.println("\nInsira qualquer tecla para voltar:");
										
										entrada.nextLine();
									} else {
										String dataCheckIn;
											
										do {
											System.out.println("Data de Check-in (Dia/Mês/Ano - ou ENTER para cancelar):");
											dataCheckIn = entrada.nextLine();
											
											if(dataCheckIn.equals(""))
												invalido = false;
											else
												invalido = !verificaData(dataCheckIn);
										} while(invalido);
										
										if(dataCheckIn.equals(""))
											System.out.println("Cadastro cancelado.");
										else {
											System.out.println();
											
											String dataCheckOut;
											
											// Se der tempo, adicionar uma verificação
											// que garante que a data de check-out seja igual ou
											// posterior à data de check-in
											
											do {
												System.out.println("Data de Check-Out (Dia/Mês/Ano):");
												dataCheckOut = entrada.nextLine();
														
												invalido = !verificaData(dataCheckOut);
											} while(invalido);
											
											System.out.println();
											
											int numQuarto;
											
											do {
												System.out.println("Qual quarto será reservado?");
												
												int i = 1;
												for(Quarto quarto : quartos) {
													System.out.println("("+ i + ") " + quarto.getNumero() + " - " + quarto.getCategoria());
													i++;
												}
												System.out.println("(0) Cancelar");
												
												numQuarto = entrada.nextInt();
												
												invalido = numQuarto < 0 || numQuarto > quartos.size();
												
												verificaValidade(invalido);
												
												if(!invalido && numQuarto != 0)
													invalido = hotel.estaOcupado(quartos.get(numQuarto - 1).getNumero(), dataCheckIn, dataCheckOut);
											} while(invalido);
											
											if(numQuarto == 0)
												System.out.println("\nCadastro cancelado.");
											else {
												entrada.nextLine();
												
												System.out.println("\nCPF do cliente (excluindo pontos e hífen):");
												String cpf = entrada.nextLine();
												
												String nome;
												
												Cliente cliente = hotel.procurarCliente(cpf);
												
												if(cliente == null) {
													System.out.println("\nNome do cliente:");
													nome = entrada.nextLine();
												} else
													nome = cliente.getNome();
												
												hotel.inserirReserva(cpf, nome, quartos.get(numQuarto - 1), dataCheckIn, dataCheckOut);
												
												System.out.println("\nCadastro realizado com sucesso!");
											}
										}
									}
									
									System.out.println();
								} else if(opcao == 2) {
									
									// Consultar reservas
									
									System.out.println("Reservas \n");
									
									List<Reserva> reservas = hotel.listarReservas();
									
									if(reservas.isEmpty())
										System.out.println("Nenhuma reserva foi encontrada. \n");
									else {
										for(Reserva reserva : reservas) {
											System.out.println("Cliente: " + reserva.getNomeCliente() + "\n"
												+ "Quarto: " + reserva.getNumQuarto() + " (" + reserva.getCategoriaQuarto() + ") \n"
												+ "Check-in/Check-out: " + reserva.getDataCheckInString() + " - " + reserva.getDataCheckOutString()
												+ "\n");
										}
									}
									
									System.out.println("Insira qualquer tecla para voltar:");
									
									entrada.nextLine();
									entrada.nextLine();
									
									System.out.println();
								} else if(opcao == 3) {
									
									// Consultar reservas por cliente
									
									System.out.println("Reservas por Cliente \n");
									
									List<Cliente> clientes = hotel.listarClientes();
									
									if(clientes.isEmpty()) {
										System.out.println("Não foram encontrados clientes e, portanto, reservas. Cadastre uma reserva primeiro. \n \n"
												+ "Insira qualquer tecla para voltar:");
										
										entrada.nextLine();
										entrada.nextLine();
									} else {
										int opcaoCliente;
										
										do {
											System.out.println("Você gostaria de ver as reservas de qual cliente?");
											
											int i = 1;
											for(Cliente cliente : clientes) {
												System.out.println("(" + i + ") " + cliente.getNome() + " (" + cliente.getCpf() + ")");
												i++;
											}
											System.out.println("(0) Voltar");
											
											opcaoCliente = entrada.nextInt();
											
											invalido = opcaoCliente < 0 || opcaoCliente > clientes.size();
											
											verificaValidade(invalido);
										} while(invalido);
										
										if(opcaoCliente > 0) {
											Cliente cliente = clientes.get(opcaoCliente - 1);
											
											System.out.println("\nReservas de " + cliente.getNome() + " (" + cliente.getCpf() + ") \n");
											
											List<Reserva> reservas = cliente.listarReservasCliente();
											
											if(reservas.isEmpty())
												System.out.println("Nenhuma reserva foi encontrada. \n");
											else {
												for(Reserva reserva : reservas) {
													System.out.println("Quarto: " + reserva.getNumQuarto() + " (" + reserva.getCategoriaQuarto() + ") \n"
														+ "Check-in/Check-out: " + reserva.getDataCheckInString() + " - " + reserva.getDataCheckOutString()
														+ "\n");
												}
											}
											
											System.out.println("Insira qualquer tecla para voltar:");
									
											entrada.nextLine();
											entrada.nextLine();
										}
									}
									
									System.out.println();
								} else if(opcao == 4) {
									
									// Cancelar reserva
									
									System.out.println("Cancelamento de Reserva \n");
									
									List<Cliente> clientes = hotel.listarClientes();
									
									if(clientes.isEmpty()) {
										System.out.println("Não foram encontrados clientes e, portanto, reservas. Cadastre uma reserva primeiro. \n \n"
												+ "Insira qualquer tecla para voltar:");
										
										entrada.nextLine();
										entrada.nextLine();
									} else {
										int opcaoCliente;
										
										do {
											System.out.println("Você gostaria de cancelar a reserva de qual cliente?");
											
											int i = 1;
											for(Cliente cliente : clientes) {
												System.out.println("(" + i + ") " + cliente.getNome() + " (" + cliente.getCpf() + ")");
												i++;
											}
											System.out.println("(0) Voltar");
											
											opcaoCliente = entrada.nextInt();
											
											invalido = opcaoCliente < 0 || opcaoCliente > clientes.size();
											
											verificaValidade(invalido);
										} while(invalido);
										
										System.out.println();
										
										if(opcaoCliente == 0)
											System.out.println("Operação cancelada.");
										else {
											Cliente cliente = clientes.get(opcaoCliente - 1);
											
											List<Reserva> reservas = cliente.listarReservasCliente();
											
											if(reservas.isEmpty()) {
												System.out.println("Nenhuma reserva de " + cliente.getNome() + " foi encontrada. \n \n"
														+ "Insira qualquer tecla para voltar:");
												
												entrada.nextLine();
												entrada.nextLine();
											} else {
												int opcaoReserva;
												
												do {
													System.out.println("Qual reserva você gostaria de cancelar? \n");
													
													int i = 1;
													for(Reserva reserva : reservas) {
														System.out.println("(" + i + ") Quarto " + reserva.getNumQuarto() + " ("
															+ reserva.getCategoriaQuarto() + ") \nCheck-In/Check-Out: "
															+ reserva.getDataCheckInString() + " - " + reserva.getDataCheckOutString() + "\n");
														i++;
													}
													
													opcaoReserva = entrada.nextInt();
													
													invalido = opcaoReserva < 1 || opcaoReserva > reservas.size();
													
													verificaValidade(invalido);
												} while(invalido);
												
												Reserva reserva = reservas.get(opcaoReserva - 1);
												
												hotel.cancelarReserva(cliente.getCpf(), reserva);
												
												System.out.println("\nReserva cancelada com sucesso!");
											}
										}
									}
									
									System.out.println();
								} else if(opcao == 5) {
									
									// Histórico de reservas canceladas
									
									System.out.println("Histórico de Reservas Canceladas \n");
									
									List<Reserva> reservasCanceladas = hotel.listarReservasCanceladas();
									
									if(reservasCanceladas.isEmpty())
										System.out.println("Nenhuma reserva foi cancelada. \n");
									else {
										for(Reserva reserva : reservasCanceladas) {
											System.out.println("Cliente: " + reserva.getNomeCliente() + "\n"
												+ "Quarto: " + reserva.getNumQuarto() + " (" + reserva.getCategoriaQuarto() + ") \n"
												+ "Check-in/Check-out: " + reserva.getDataCheckInString() + " - " + reserva.getDataCheckOutString()
												+ "\n");
										}
									}
									
									System.out.println("Insira qualquer tecla para voltar:");
									
									entrada.nextLine();
									entrada.nextLine();
									
									System.out.println();
								} else if(opcao == 6) {
									
									// Cadastrar quarto
									
									System.out.println("Cadastro de Quarto \n");
									
									entrada.nextLine();
									
									System.out.println("Número do quarto (ou 0 para cancelar):");
									int numero = entrada.nextInt();
									
									if(numero == 0)
										System.out.println("\nCadastro cancelado.");
									else {
										System.out.println();
										
										int opcaoCategoria;
										
										do {
											System.out.println("Categoria do quarto:");
											for(Categoria categoria : Categoria.values())
												System.out.println("("+ categoria.getNumOpcao() +") " + categoria.name());
											
											opcaoCategoria = entrada.nextInt();
											
											invalido = opcaoCategoria < 1 || opcaoCategoria > Categoria.values().length;
											
											verificaValidade(invalido);
										} while(invalido);
										
										hotel.inserirQuarto(numero, opcaoCategoria);
										
										System.out.println("\nCadastro realizado com sucesso!");
									}
									
									System.out.println();
								} else if(opcao == 7) {
									
									// Consultar quartos
									
									System.out.println("Quartos de " + hotel.getNome() + "\n");
									
									List<Quarto> quartos = hotel.listarQuartos();
									
									if(quartos.isEmpty())
										System.out.println("Nenhum quarto foi cadastrado.");
									else {
										for(Quarto quarto : quartos)
											System.out.println("- " + quarto.getNumero() + " (" + quarto.getCategoria() + ")");
									}
									
									System.out.println("\nInsira qualquer tecla para voltar:");
									
									entrada.nextLine();
									entrada.nextLine();
									
									System.out.println();
								} else if(opcao == 8) {
									
									// Consultar disponibilidade de quartos
									
									System.out.println("Disponibilidade de Quartos \n");
									
									List<Quarto> quartos = hotel.listarQuartos();
									
									entrada.nextLine();
									
									if(quartos.isEmpty()) {
										System.out.println("Nenhum quarto foi cadastrado. Primeiro cadastre um quarto. \n \n"
												+ "Insira qualquer tecla para voltar:");
										
										entrada.nextLine();
									} else {
										
										String dataCheckIn;
										
										do {
											System.out.println("Data de Check-in (Dia/Mês/Ano - ou ENTER para cancelar):");
											dataCheckIn = entrada.nextLine();
											
											if(dataCheckIn.equals(""))
												invalido = false;
											else
												invalido = !verificaData(dataCheckIn);
										} while(invalido);
										
										if(!dataCheckIn.equals("")) {
											System.out.println();
											
											String dataCheckOut;
											
											// Se der tempo, adicionar uma verificação
											// que garante que a data de check-out seja igual ou
											// posterior à data de check-in
											
											do {
												System.out.println("Data de Check-Out (Dia/Mês/Ano):");
												dataCheckOut = entrada.nextLine();
														
												invalido = !verificaData(dataCheckOut);
											} while(invalido);
											
											System.out.println();
											
											int opcaoCategoria;
											
											do {
												System.out.println("Categoria do quarto:");
												for(Categoria categoria : Categoria.values())
													System.out.println("("+ categoria.getNumOpcao() +") " + categoria.name());
												
												opcaoCategoria = entrada.nextInt();
												
												invalido = opcaoCategoria < 1 || opcaoCategoria > Categoria.values().length;
												
												verificaValidade(invalido);
											} while(invalido);
											
											System.out.println();
											
											quartos = hotel.listarQuartosDisponiveis(dataCheckIn, dataCheckOut, opcaoCategoria);
											
											if(quartos.isEmpty()) {
												System.out.println("Nenhum quarto da categoria " + Categoria.getCategoria(opcaoCategoria)
													+ " está disponível no período especificado.");
											} else {
												System.out.println("Há " + quartos.size() + " quarto(s) da categoria "
													+ Categoria.getCategoria(opcaoCategoria) + " disponível(is): \n");
												
												for(Quarto quarto : quartos)
													System.out.println("- Quarto " + quarto.getNumero());
											}
											
											System.out.println("\nInsira qualquer tecla para voltar:");
											
											entrada.nextLine();
											entrada.nextLine();
										}
									}
									
									System.out.println();
								} else if(opcao == 9) {
									
									// Relatórios com estatísticas do hotel
									
									while(opcaoRelatorio > 0) {
										System.out.println("Relatórios \n");
										
										do {
											System.out.println("Qual relatório você gostaria de conferir? \n"
												+ "(1) Taxa de ocupação do hotel em um período específico \n(2) Quartos mais e menos reservados \n"
												+ "(3) Quantidade de cancelamentos em um determinado período \n(0) Voltar");
											
											opcaoRelatorio = entrada.nextInt();
											
											invalido = opcaoRelatorio < 0 || opcaoRelatorio > 3;
											
											verificaValidade(invalido);
										} while(invalido);
										
										System.out.println();
										
										if(opcaoRelatorio == 1) {
											
											// Taxa de ocupação em um período específico
											
											System.out.println("Taxa de ocupação em um período específico \n");
											
											entrada.nextLine();
											
											String dataInicio;
											
											do {
												System.out.println("Data inicial (Dia/Mês/Ano - ou ENTER para cancelar):");
												dataInicio = entrada.nextLine();
												
												if(dataInicio.equals(""))
													invalido = false;
												else
													invalido = !verificaData(dataInicio);
											} while(invalido);
											
											if(!dataInicio.equals("")) {
												System.out.println();
												
												String dataFim;
												
												do {
													System.out.println("Data final (Dia/Mês/Ano):");
													dataFim = entrada.nextLine();
															
													invalido = !verificaData(dataFim);
												} while(invalido);
												
												System.out.println();
												
												double taxaOcupacao = hotel.calcularTaxaOcupacao(dataInicio, dataFim);
												
												System.out.println("Taxa de ocupação entre " + dataInicio + " e " + dataFim + ": \n"
														+ taxaOcupacao + "% dos quartos");
											}
											
											System.out.println();
										} else if(opcaoRelatorio == 2) {
											
											// Quartos mais e menos reservados
											
											System.out.println("Quartos mais e menos reservados \n");
											
											List<Quarto> quartos = hotel.listarQuartosPorReservas();
											
											if(quartos.isEmpty())
												System.out.println("Nenhum quarto foi cadastrado. Cadastre um quarto primeiro.");
											else {
												int i = 1;
												for(Quarto quarto : quartos) {
													System.out.println(i + ". Quarto " + quarto.getNumero() + " (" + quarto.getCategoria() + ")"
														+ " - " + quarto.getQuantReservas() + " reserva(s)");
													i++;
												}
											}
											
											System.out.println("\nInsira qualquer tecla para voltar:");
											
											entrada.nextLine();
											entrada.nextLine();
											
											System.out.println();
										} else if(opcaoRelatorio == 3) {
											
											// Quantidade de cancelamentos em um determinado período
											
											System.out.println();
										}
									}
									
									System.out.println();
								}
							}
						}
					}
				}
				
				System.out.println();
			}
		}
		
		System.out.println("Fim da execução.");
		
		entrada.close();
	}
}