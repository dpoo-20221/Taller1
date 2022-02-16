package uniandes.dpoo.taller1.modelo;

public class Ingrediente 
{
	
	private String nombre;
	
	private int costoAdicional;
	
	public Ingrediente(String pNombre, int pCostoAdicional) 
	{
		nombre = pNombre;
		costoAdicional = pCostoAdicional;
	}
	
	public String getNombre() 
	{
		return nombre;
	}

	public int getCostoAdicional() 
	{
		return costoAdicional;
	}
}
