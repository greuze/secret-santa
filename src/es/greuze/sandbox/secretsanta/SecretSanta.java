package es.greuze.sandbox.secretsanta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SecretSanta {

	private List<Participante> listaParticipantes;
	private List<Participante> listaObjetivos;
	private BufferedReader reader;

	public SecretSanta(Reader reader) {
		this.reader = new BufferedReader(reader);
		this.listaParticipantes = new ArrayList<Participante>();
	}

	public void cargarParticipantes() throws IOException {
		// Parsea todas las lineas del fichero
		String linea;
		while ((linea = reader.readLine()) != null) {
			String[] lineaSplit = linea.split("=");
			// Cada linea debe tener exactamente dos campos
			assert lineaSplit.length == 2;
		    listaParticipantes.add(new Participante(lineaSplit[0], lineaSplit[1]));
		}
	}

	private void cargarObjetivos() {
		// Crea la lista de participantes y la rellena
		listaObjetivos = new ArrayList<Participante>();
		for (Participante participante: listaParticipantes) {
			listaObjetivos.add(participante);
		}
		// Mezcla la lista hasta que sea valida
		do {
			System.out.println("Se va a mezclar la lista...");
			Collections.shuffle(listaObjetivos);
		} while (!validarObjetivos(listaObjetivos));
	}

	// Comprueba que nadie se tenga a si mismo
	private boolean validarObjetivos(List<Participante> listaObjetivos) {
		Iterator<Participante> i = listaObjetivos.iterator(); 
		for (Participante participanteI: listaParticipantes) {
			Participante objetivoI = i.next();
			// Si hay uno que es su propio objetivo, la lista no vale
			if (participanteI.equals(objetivoI)) {
				return false;
			}
			// Establece el objetivo bueno
			participanteI.setObjetivoRegalo(objetivoI);
		}
		// Si ha llegado hasta aqui, la lista es valida
		return true;
	}

	private void enviarCorreos() {
		for (Participante participanteI: listaParticipantes) {
			// Envia el correo, y si no, para toda la aplicacion
			try {
				SimpleSSLMail.enviarCorreo(participanteI);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void pintarResultados() {
		for (Participante participanteI: listaParticipantes) {
			System.out.println("A " + participanteI.getNombre() + " le ha tocado " + participanteI.getObjetivoRegalo().getNombre());
		}
	}

	// Los nombres de todos los participantes deben ser distintos
	public static void main(String[] args) throws Exception {
		if (args.length == 1) {
			File file = new File(args[0]);
			if (file.exists()) {
				System.out.println("Arrancando programa con el fichero " + file.getAbsolutePath());
				// Crea el objeto principal
				SecretSanta santa = new SecretSanta(new FileReader(file));
				// Carga los participantes del fichero
				santa.cargarParticipantes();
				// Asigna a cada participante un objetivo para regalar 
				santa.cargarObjetivos();
				// Pinta los resultados
				santa.pintarResultados();
//				// Envia los correos
//				santa.enviarCorreos();
			} else {
				System.out.println("El fichero " + file.getAbsolutePath() + " no existe");
			}
		} else {
			System.out.println("Numero de parametros incorrecto, utilice:");
			System.out.println("java SecretSanta <ruta_fichero>");
		}
	}
}
