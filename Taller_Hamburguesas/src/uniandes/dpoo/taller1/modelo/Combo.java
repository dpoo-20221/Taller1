package uniandes.dpoo.taller1.modelo;

import java.util.ArrayList;

public class Combo implements Producto
{
	
	private double descuento;
	
	private String nombreCombo;
	
	private ArrayList<ProductoMenu> itemsCombo;
	
	private double precio;
	
	public Combo(double pDescuento, String pNombreCombo) 
	{
		descuento = pDescuento;
		nombreCombo = pNombreCombo;
		itemsCombo = new ArrayList<>();
		precio = 0;
	}

	public String getNombre() {
		return nombreCombo;
	}
	public double calcularPrecio() {
		
		if(precio == 0)
		{
			for(ProductoMenu producto: itemsCombo) 
			{
				precio += producto.getPrecio();
			}
			precio = precio*(1-descuento/100);
		}
		
		return precio;
	}
	public void agregarItemACombo(ProductoMenu itemCombo) 
	{
		itemsCombo.add(itemCombo);
	}
	
	public double getPrecio() {
		calcularPrecio();
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}	
	
	public String generarTextoFactura()
	{
		String resp = "combo;"+nombreCombo+";"+calcularPrecio();
		return resp;
	}
	
}
