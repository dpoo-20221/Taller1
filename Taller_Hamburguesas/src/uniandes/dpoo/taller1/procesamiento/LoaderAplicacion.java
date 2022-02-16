package uniandes.dpoo.taller1.procesamiento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import uniandes.dpoo.taller1.modelo.Combo;
import uniandes.dpoo.taller1.modelo.Ingrediente;
import uniandes.dpoo.taller1.modelo.Pedido;
import uniandes.dpoo.taller1.modelo.ProductoAjustado;
import uniandes.dpoo.taller1.modelo.ProductoMenu;

public class LoaderAplicacion 
{
	
	ArrayList<Ingrediente> ingredientes = new ArrayList<>();
	ArrayList<Combo> combos = new ArrayList<>();
	ArrayList<ProductoMenu> productosMenu = new ArrayList<>();
	ArrayList<Pedido> pedidos = new ArrayList<>();
	
	public LoaderAplicacion() 
	{
		cargarArchivo();
	}
	
	public void cargarArchivo() 
	{
		cargarIngredientes();
		cargarMenu();
		cargarCombos();
		cargarPedidos();
	}
	
	private void cargarPedidos() 
	{
		int id = 0;
		String nombre = null;
		String direccion = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("./data/pedidos.txt"));
			String linea = br.readLine();
			while(linea != null) 
			{
				String[] partes = linea.split(";");
				if(partes[0].equals("id"))
				{
					id = Integer.parseInt(partes[1]);
				}
				else if(partes[0].equals("nombre"))
				{
					nombre = partes[1];
				}
				else if(partes[0].equals("direccion"))
				{
					direccion = partes[1];
				}
				else if(partes[0].equals("nitems"))
				{
					int n = Integer.parseInt(partes[1]);
					Pedido pedido = new Pedido(nombre,direccion);
					pedido.setIdPedido(id);
					for(int i=0;i<=n;i++)
					{
						String[] partesAux = linea.split(";");
						if(partesAux[0].equals("combo"))
						{
							Combo combo = new Combo(0, partesAux[1]);
							combo.setPrecio(Double.parseDouble(partesAux[2]));
							pedido.agregarProducto(combo);
						}
						else if(partesAux[0].equals("productomenu"))
						{
							ProductoMenu productoMenu = new ProductoMenu(partesAux[1], Integer.parseInt(partesAux[2]));
							pedido.agregarProducto(productoMenu);
						}
						else if(partesAux[0].equals("productoajustado"))
						{
							ProductoMenu productoMenu = new ProductoMenu(partesAux[1].substring(0, partesAux[1].length()-8), Double.parseDouble(partesAux[2]));
							ProductoAjustado productoAjustado = new ProductoAjustado(productoMenu);
							pedido.agregarProducto(productoAjustado);
						}
						linea = br.readLine();
					}
					pedidos.add(pedido);
				}
				linea = br.readLine();
			}
			br.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}

	private void cargarMenu() 
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader("./data/menu.txt"));
			String linea = br.readLine();
			while(linea != null) 
			{
				String[] partes = linea.split(";");
				String nombre = partes[0];
				int precioBase = Integer.parseInt(partes[1]);
				ProductoMenu productoMenu = new ProductoMenu(nombre, precioBase);
				productosMenu.add(productoMenu);
				linea = br.readLine();
			}
			br.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void cargarIngredientes() 
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader("./data/ingredientes.txt"));
			String linea = br.readLine();
			while(linea != null) 
			{
				String[] partes = linea.split(";");
				String nombre = partes[0];
				int costoAdicional = Integer.parseInt(partes[1]);
				Ingrediente ingrediente = new Ingrediente(nombre, costoAdicional);
				ingredientes.add(ingrediente);
				linea = br.readLine();
			}
			br.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void cargarCombos() 
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader("./data/combos.txt"));
			String linea = br.readLine();
			while(linea != null) 
			{
				String[] partes = linea.split(";");
				String nombre = partes[0];
				String[] parteDescuento = partes[1].split("%");
				double descuento = Integer.parseInt(parteDescuento[0]);
				Combo combo = new Combo(descuento, nombre);
				for(int i=2;i<partes.length-1;i++) 
				{
					for(ProductoMenu producto:productosMenu) 
					{
						if(partes[i].equals(producto.getNombre())) 
						{
							combo.agregarItemACombo(producto);
							break;
						}
					}
				}				
				combos.add(combo);
				linea = br.readLine();
			}
			br.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public ArrayList<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public ArrayList<Combo> getCombos() {
		return combos;
	}

	public ArrayList<ProductoMenu> getProductosMenu() {
		return productosMenu;
	}

	public ArrayList<Pedido> getPedidos() {
		//System.out.println(pedidos.get(0).generarTextoFactura());
		return pedidos;
	}
}
