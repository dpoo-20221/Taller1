package uniandes.dpoo.taller1.modelo;

public class ProductoMenu implements Producto
{
	
	private String nombre;
	
	private double precioBase;
	
	public ProductoMenu(String pNombre, double pPrecioBase) 
	{
		nombre = pNombre;
		precioBase = pPrecioBase;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecio() {
		return precioBase;
	}

	public String generarTextoFactura() 
	{
		String resp = "productomenu;"+nombre+";"+precioBase;
		return resp;
	}
	
}
