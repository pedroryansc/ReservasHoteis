package enumerado;

public enum Categoria {
	Econômico(1),
	Luxo(2);
	
	Adicionar mais categorias
	
	private int numOpcao;
	
	Categoria(int numOpcao){
		this.setNumOpcao(numOpcao);
	}
	
	public static int quantOpcoes() {
		int cont = 0;
		
		for(Categoria categoria : values())
			cont++;
		
		return cont;
	}

	public int getNumOpcao() {
		return numOpcao;
	}

	public void setNumOpcao(int numOpcao) {
		this.numOpcao = numOpcao;
	}
}
