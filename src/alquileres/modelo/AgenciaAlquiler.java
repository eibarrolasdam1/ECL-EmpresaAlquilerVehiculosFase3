package alquileres.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * La clase guarda en una colección List (un ArrayList) la flota de vehículos
 * que una agencia de alquiler posee
 * 
 * Los vehículos se modelan como un interface List que se instanciará como una
 * colección concreta ArrayList
 */
public class AgenciaAlquiler {
	private static final File FICHERO_ENTRADA = new File("flota.csv");
	private static final File FICHERO_SALIDA = new File("marcasmodelos.txt");
	
	
	private String nombre; // el nombre de la agencia
	private List<Vehiculo> flota; // la lista de vehículos

	/**
	 * Constructor
	 * 
	 * @param nombre el nombre de la agencia
	 */
	public AgenciaAlquiler(String nombre) {
		this.setNombre(nombre);
		this.flota = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * añade un nuevo vehículo solo si no existe
	 * 
	 */
	public void addVehiculo(Vehiculo v) {
		if (!flota.contains(v)) {
			flota.add(v);
		}
	}

	/**
	 * Extrae los datos de una línea, crea y devuelve el vehículo
	 * correspondiente
	 * 
	 * Formato de la línea:
	 * C,matricula,marca,modelo,precio,plazas para coches
	 * F,matricula,marca,modelo,precio,volumen para furgonetas
	 * 
	 * 
	 * Asumimos todos los datos correctos. Puede haber espacios antes y después
	 * de cada dato
	 */
	private Vehiculo obtenerVehiculo(String info) throws NumberFormatException{
		Vehiculo v = null;
		String[] informacionVehiculo = info.split(",");
		for(int i = 0; i < informacionVehiculo.length; i++) {
			informacionVehiculo[i] = informacionVehiculo[i].trim();
		}
		String matricula = informacionVehiculo[1];
		String marca = informacionVehiculo[2];
		String modelo = informacionVehiculo[3];
		double precio = Double.parseDouble(informacionVehiculo[4]);
		if (informacionVehiculo[0].equalsIgnoreCase("F")) {
			double volumen = Double.parseDouble(informacionVehiculo[5]);
			v = new Furgoneta(matricula, marca, modelo, precio, volumen);
		} else {
			int plazas = Integer.parseInt(informacionVehiculo[5]);
			v = new Coche(matricula, marca, modelo, precio, plazas);
		}
		return v;
	}

	/**
	 * La clase Utilidades nos devuelve un array con las líneas de datos
	 * de la flota de vehículos  
	 */
	public int cargarFlota() {
		int errores  = 0;
		BufferedReader entrada = null;
		try {
			entrada = new BufferedReader(new FileReader(FICHERO_ENTRADA));
			String lineaTexto = entrada.readLine();
			while (lineaTexto != null) {
				try {
					this.addVehiculo(this.obtenerVehiculo(lineaTexto));
				} catch (NumberFormatException ex) {
					errores += 1;
				} finally {
					lineaTexto = entrada.readLine();
				}

			}

		} catch (IOException excep) {
			System.out.println("Error al intentar leer el fichero" + FICHERO_ENTRADA.toString());
		}
		return errores;
	}

	/**
	 * Representación textual de la agencia
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Veh�culos en alquiler de la agencia ").append(this.nombre).append("\nTotal veh�culos: ").append(flota.size()).append("\n");
		for (Vehiculo v: flota) {
			sb.append(v.toString()).append("\n ----------------------------------------------------------------------------------------------------------- \n");
		}
		return sb.toString();

	}

	/**
	 * Busca todos los coches de la agencia
	 * Devuelve un String con esta información y lo que
	 * costaría alquilar cada coche el nº de días indicado * 
	 *  
	 */
	public String buscarCoches(int dias) {
		StringBuilder sb = new StringBuilder();
		for (Vehiculo v: flota) {
			if (v instanceof Coche) {
			sb.append(v.toString()).append("\nCoste alquiler " + dias + " d�as: ").append(((Coche) v).calcularPrecioAlquiler(dias)).append(" � \n-----------------------------------------------------\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Obtiene y devuelve una lista de coches con más de 4 plazas ordenada por
	 * matrícula - Hay que usar un iterador
	 * @param <T>
	 * 
	 */
	public <T> List<Coche> cochesOrdenadosMatricula() {
		List<Coche> lista = new ArrayList<>();
		Iterator<Vehiculo> it = flota.iterator();
		while (it.hasNext()) {
			Vehiculo v = it.next();
			if (v instanceof Coche && ((Coche) v).getPlazas() > 4) {
					lista.add((Coche) v);
			}
		}
		lista.sort(Comparator.naturalOrder());
		return lista;
	}

	/**
	 * Devuelve la relación de todas las furgonetas ordenadas de
	 * mayor a menor volumen de carga
	 * 
	 */
	public List<Furgoneta> furgonetasOrdenadasPorVolumen() {
		List<Furgoneta> lista = new ArrayList<>();
		Iterator<Vehiculo> it = flota.iterator();
		while (it.hasNext()) {
			Vehiculo v = it.next();
			if (v instanceof Furgoneta) {
				lista.add((Furgoneta) v);
			}	
		}
		Collections.sort(lista, new Comparator<Furgoneta> () {
			public int compare (Furgoneta furgo1, Furgoneta furgo2) {
				return Double.compare(furgo1.getVolumen(), furgo2.getVolumen());
			}
		});
		Collections.reverse(lista);
		return lista;
	}

	/**
	 * Genera y devuelve un map con las marcas (importa el orden) de todos los
	 * vehículos que hay en la agencia como claves y un conjunto (importa el orden) 
	 * de los modelos en cada marca como valor asociado
	 */
	public Map<String, Set<String>> marcasConModelos() {
		Map<String, Set<String>> marcasYmodelos = new TreeMap<>();
		Iterator<Vehiculo> it = flota.iterator();
		while (it.hasNext()) {
			Vehiculo v = it.next();
			String marca = v.getMarca();
			String modelo = v.getModelo();
			if (!marcasYmodelos.containsKey(marca)) {
				Set<String> modelos = new TreeSet<>();
				modelos.add(modelo);
				marcasYmodelos.put(marca, modelos);
			} else {
				marcasYmodelos.get(marca).add(modelo);
			}
		}
		return marcasYmodelos;
	}
		
	public void guardarMarcasModelos() throws IOException, NullPointerException {
		PrintWriter marcasmodelos = null;
		try {
			marcasmodelos = new PrintWriter(new BufferedWriter (new FileWriter(FICHERO_SALIDA)));
			Set<Map.Entry<String, Set<String>>> conjuntoEntradas = this.marcasConModelos().entrySet();
			Iterator<Map.Entry<String, Set<String>>> it = conjuntoEntradas.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Set<String>> entrada = it.next();
				marcasmodelos.write(entrada.getKey().toString() + "\n\t" + entrada.getValue().toString() + "\n");
			}
		}
		finally
		{
			marcasmodelos.close();
		}
	}

}
