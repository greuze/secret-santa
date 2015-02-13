package es.greuze.sandbox.secretsanta;

public class Participante {

	private String nombre;
	private String correo;
	private Participante objetivoRegalo;

	public Participante(String nombre, String correo) {
		this.nombre = nombre;
		this.correo = correo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setObjetivoRegalo(Participante objetivoRegalo) {
		this.objetivoRegalo = objetivoRegalo;
	}

	public Participante getObjetivoRegalo() {
		return objetivoRegalo;
	}

	@Override
	public boolean equals(Object obj) {
		// Comprueba que el tipo sea correcto
		if (obj == null || !(obj instanceof Participante)) {
			return false;
		}
		// Compara los campos para ver si son iguales
		Participante comparado = (Participante) obj;
		assert this.getCorreo() != null;
		assert this.getNombre() != null;

		return this.getCorreo().equals(comparado.getCorreo()) && this.getNombre().equals(comparado.getNombre()); 
	}

	@Override
	public String toString() {
		return "{" + getNombre() + "=" + getCorreo() + "}";
	}
}
