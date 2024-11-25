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

public class Main {
	
	public static void verificaValidade(boolean invalido) {
		if(invalido)
			System.out.println("\nErro: Opção inválida\n");
	}
	
	public static boolean verificaData(String data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		try {
			LocalDate dataCorreta = LocalDate.parse(data, formato);
			System.out.println(dataCorreta);
			
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
								System.out.println("Menu do " + hotel.getNome() + "\n");
								
								do {
									System.out.println("O que você gostaria de fazer? \n"
										+ "(1) Reservar quarto \n(2) Consultar reservas \n(3) Cancelar reserva \n"
										+ "(4) Histórico de reservas canceladas \n(5) Cadastrar quarto \n(6) Consultar quartos \n"
										+ "(0) Voltar");
									
									opcao = entrada.nextInt();
									
									invalido = opcao < 0 || opcao > 6;
									
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
											String dataCheckOut;
											
											// Se der tempo, adicionar uma verificação
											// que garante que a data de check-out seja igual ou
											// posterior à data de check-in
											
											do {
												System.out.println("Data de Check-Out (Dia/Mês/Ano):");
												dataCheckOut = entrada.nextLine();
														
												invalido = !verificaData(dataCheckIn);
											} while(invalido);
											
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
												
												if(!invalido)
													invalido = hotel.estaOcupado(numQuarto, dataCheckIn, dataCheckOut);
											} while(invalido);
											
											if(numQuarto == 0)
												System.out.println("\nCadastro cancelado.");
											else {
												/*
												if(invalido) {
													System.out.println("O quarto " + quartos.get(numQuarto - 1).getNumero() + " está reservado "
														+ "durante o período especificado.");
												}
												*/
												
												System.out.println("CPF do cliente (excluindo pontos e hífen):");
												String cpf = entrada.nextLine();
												
												String nome;
												
												Cliente cliente = hotel.procurarCliente(cpf);
												
												if(cliente == null) {
													System.out.println("Nome do cliente:");
													nome = entrada.nextLine();
												} else
													nome = cliente.getNome();
												
												hotel.inserirReserva(cpf, nome, quartos.get(numQuarto - 1), dataCheckIn, dataCheckOut);
												
												/*
													Ideia: Ao invés de Hotel conter Clientes e, assim, Clientes conter Reservas,
													Hotel poderia conter Reservas e, depois, Reservas poderia conter Detalhes da Reserva,
													entre os quais estariam as informações do cliente.
													
													Desta forma, as reservas seriam ordenadas pela data de check-in (ou seja,
													um hotel armazenaria uma reserva a partir de sua data de check-in).
												*/
												
												System.out.println("\nCadastro realizado com sucesso!");
											}
										}
									}
									
									System.out.println();
								} else if(opcao == 5) {
									
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
								} else if(opcao == 6) {
									
									// Consultar quartos
									
									List<Quarto> quartos = hotel.listarQuartos();
									
									System.out.println("Quartos de " + hotel.getNome() + "\n");
									
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