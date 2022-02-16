package uniandes.dpoo.taller1.modelo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto 
{
	private ArrayList<Ingrediente> agregados;
	
	private ArrayList<Ingrediente> eliminados;
	
	private ProductoMenu base;
	
	public ProductoAjustado(ProductoMenu pBase)
	{
		agregados = new ArrayList<Ingrediente>();
		eliminados = new ArrayList<Ingrediente>();
		base = pBase;
	}

	public double getPrecio() 
	{	
		double precio = base.getPrecio();
		for(int i=0;i<agregados.size();i++)
		{
			precio+= agregados.get(i).getCostoAdicional();
		}	
		return precio;
	}

	public String getNombre() 
	{		
		return base.getNombre()+" Ajustado";
	}
	
	public void agregarIngredienteAgregados(Ingrediente ingrediente) 
	{
		agregados.add(ingrediente);
	}
	
	public void agregarIngredienteEliminados(Ingrediente ingrediente) 
	{
		eliminados.add(ingrediente);
	}
	
	public String generarTextoFactura() {
		String resp = "productoajustado;"+getNombre()+";"+getPrecio();
		return resp;
	}

}