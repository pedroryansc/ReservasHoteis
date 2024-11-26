package reserva;

import java.util.ArrayList;
import java.util.List;

import cliente.Cliente;
import ordenacao.ShellSort;

public class ArvoreReservasCanceladas {

	private Cliente raiz;

	public void inserirReservaCancelada() {
		
	}
	
	public List<Reserva> listarCanceladas(){
		List<Reserva> reservasCanceladas = new ArrayList<Reserva>();
		
		listarCanceladasRecursivo(getRaiz(), reservasCanceladas);
		
		return ShellSort.ordenarPorCheckIn(reservasCanceladas);
	}
	
	private List<Reserva> listarCanceladasRecursivo(Cliente atual, List<Reserva> reservas){
		if(atual != null) {
			listarCanceladasRecursivo(atual.getEsquerdo(), reservas);
			atual.listarReservasRecursivo(atual.getRaiz(), reservas);
			listarCanceladasRecursivo(atual.getDireito(), reservas);
		}
		
		return reservas;
	}
	
	public Cliente getRaiz() {
		return raiz;
	}

	public void setRaiz(Cliente raiz) {
		this.raiz = raiz;
	}
	
}
