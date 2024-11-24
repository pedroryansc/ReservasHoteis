package enumerado;

public enum Categoria {
	Standard(1),
	Master(2),
	Deluxe(3);
	
	private int numOpcao;
	
	Categoria(int numOpcao){
		this.setNumOpcao(numOpcao);
	}

	public static Categoria getCategoria(int numOpcao) {
		for(Categoria categoria : values()) {
			if(categoria.getNumOpcao() == numOpcao)
				return categoria;
		}
		
		return null;
	}
	
	public int getNumOpcao() {
		return numOpcao;
	}

	public void setNumOpcao(int numOpcao) {
		this.numOpcao = numOpcao;
	}
}
