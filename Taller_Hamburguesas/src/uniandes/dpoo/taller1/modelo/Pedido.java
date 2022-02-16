package uniandes.dpoo.taller1.modelo;

import java.util.ArrayList;

public class Pedido {

	private int idPedido;
	
	private static int numeroPedidos;
	
	private String nombreCliente;
	
	private String direccionCliente;
	
	private ArrayList<Producto> itemsPedido;
	
	public Pedido(String pNombreCliente, String pDireccionCliente) 
	{
		
		nombreCliente = pNombreCliente;
		
		direccionCliente = pDireccionCliente;
	
		itemsPedido = new ArrayList<>();
		
		idPedido = numeroPedidos+1;
		
		numeroPedidos++;
		
	}
	
	public void setIdPedido(int pId)
	{
		idPedido = pId;
	}
	
	public int getIdPedido() 
	{
	    return idPedido;
	}
	
	
	public void agregarProducto(Producto producto)
	{
		itemsPedido.add(producto);
		
	}
	
	private double getPrecioNetoPedido()
	
	{  
		
		double precio = 0;
		
		for (Producto producto: itemsPedido)
		{
			precio += producto.getPrecio();
		}
		return precio;
	}
	
    public double getPrecioTotalPedido()
	
	{  
		
		double precio = getPrecioNetoPedido()+getPrecioIVAPedido();
		
		return precio;
	}
		
    private double getPrecioIVAPedido()
	
	{  
		
		double precio = getPrecioNetoPedido()*0.19;
		
		return precio;
	}
  
	public String generarTextoFactura() 
	{   String txt = "";
		txt += "id;" + Integer.toString(getIdPedido())+"\n";
	    txt += "nombre;" +(nombreCliente)+"\n";
	    txt += "direccion;"+(direccionCliente)+"\n";
	    txt += "nitems;" + Integer.toString(itemsPedido.size())+"\n";
	    		
		for(int i=0;i<itemsPedido.size();i++) 
		{
		  txt += itemsPedido.get(i).generarTextoFactura()+"\n";
				
		}
		txt += "preciototal;"+getPrecioTotalPedido()+"\n";
		return txt;
	}

	public ArrayList<Producto> getItemsPedido() 
	{
		return itemsPedido;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}
	
    
	}