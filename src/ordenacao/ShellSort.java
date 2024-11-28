package ordenacao;

import java.util.List;

import quarto.Quarto;
import reserva.Reserva;

public class ShellSort {

	public static List<Reserva> ordenarPorCheckIn(List<Reserva> reservas){
		int tamanho = reservas.size();
		int intervalo = 1;
		int j;
		Reserva aux;
		
		while(intervalo < tamanho)
			intervalo = 3 * intervalo + 1;
		
		while(intervalo > 1) {
			intervalo /= 3;
			
			for(int i = intervalo; i < tamanho; i++) {
				aux = reservas.get(i);
				
				j = i - intervalo;
				
				while(j >= 0 && aux.getDataCheckIn().isBefore(reservas.get(j).getDataCheckIn())) {
					reservas.set(j + intervalo, reservas.get(j));
					
					j -= intervalo;
				}
				
				reservas.set(j + intervalo, aux);
			}
		}
		
		return reservas;
	}
	
	public static List<Quarto> ordenarPorQuantReservas(List<Quarto> quartos){
		int tamanho = quartos.size();
		int intervalo = 1;
		int j;
		Quarto aux;
		
		while(intervalo < tamanho)
			intervalo = 3 * intervalo + 1;
		
		while(intervalo > 1) {
			intervalo /= 3;
			
			for(int i = intervalo; i < tamanho; i++) {
				aux = quartos.get(i);
				
				j = i - intervalo;
				
				while(j >= 0 && aux.getQuantReservas() > quartos.get(j).getQuantReservas()) {
					quartos.set(j + intervalo, quartos.get(j));
					
					j -= intervalo;
				}
				
				quartos.set(j + intervalo, aux);
			}
		}
		
		return quartos;
	}
	
}