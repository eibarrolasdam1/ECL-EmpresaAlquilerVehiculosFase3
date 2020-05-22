package alquileres.modelo;

/**
 * Un coche es un vehículo que añade un nº de plazas
 * 
 * El coste final de alquiler depende no solo del nº de días de alquiler 
 * sino también del nº de plazas. Si el nº de plazas es > 4 se añaden 5€ más por día
 */
public class Coche extends Vehiculo{
	private int plazas;
	
	public Coche (String matricula, String marca, String modelo, double precioDia, int plazas) {
		super (matricula, marca, modelo, precioDia);
		this.setPlazas(plazas);
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}
	
	public double calcularPrecioAlquiler(int dias) {
		double precioAlquiler = 0;
		if (this.getPlazas() > 4) {
			precioAlquiler = super.calcularPrecioAlquiler(dias) + 5;
		} else {
			precioAlquiler = super.calcularPrecioAlquiler(dias);
		}
		return precioAlquiler;
	}
	
	public String toString() {
		return super.toString() + "\t | \t Plazas: " + this.plazas;
	}
}
