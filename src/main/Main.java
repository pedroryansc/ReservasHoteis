package main;

import java.util.List;
import java.util.Scanner;

import enumerado.Categoria;
import hotel.ArvoreHoteis;
import hotel.Hotel;

public class Main {
	
	public static void verificaValidade(boolean invalido) {
		if(invalido)
			System.out.println("\nErro: Opção inválida\n");
	}
	
	public static void main(String[] args) {
		
		Scanner entrada = new Scanner(System.in);
		
		// Criação da árvore de hotéis
		ArvoreHoteis redeHoteis = new ArvoreHoteis();
		
		/*
		redeHoteis.inserir("Hilton Hotel");
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
										+ "(4) Histórico de reservas canceladas \n(5) Cadastrar quarto \n(6) Consultar quartos"
										+ "(0) Voltar");
									
									opcao = entrada.nextInt();
									
									invalido = opcao < 0 || opcao > 6;
									
									verificaValidade(invalido);
								} while(invalido);
								
								System.out.println();
								
								if(opcao == 1) {
								
								} else if(opcao == 5) {
									
									// Cadastrar quarto
									
									System.out.println("Cadastro de Quarto \n");
									
									entrada.nextLine();
									
									System.out.println("Número do quarto (ou ENTER para cancelar):");
									String numero = entrada.nextLine();
									
									if(numero.equals(""))
										System.out.println("Cadastro cancelado.");
									else {
										System.out.println();
										
										int opcaoCategoria;
										
										do {
											System.out.println("Categoria do quarto:");
											for(Categoria categoria : Categoria.values())
												System.out.println("("+ categoria.getNumOpcao() +") " + categoria.name());
											
											opcaoCategoria = entrada.nextInt();
											
											invalido = opcaoCategoria < 1 || opcaoCategoria > Categoria.quantOpcoes();
											
											verificaValidade(invalido);
										} while(invalido);
										
										hotel.inserirQuarto(numero, opcaoCategoria);
										
										System.out.println("\nCadastro realizado com sucesso!");
									}
									
									System.out.println();
								} else if(opcao == 6) {
									
									// Consultar quartos
									
									System.out.println("Quartos de " + hotel.getNome());
									
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