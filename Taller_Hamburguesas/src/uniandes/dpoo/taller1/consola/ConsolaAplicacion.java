/**
 * 
 */
package uniandes.dpoo.taller1.consola;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import uniandes.dpoo.taller1.modelo.Combo;
import uniandes.dpoo.taller1.modelo.Ingrediente;
import uniandes.dpoo.taller1.modelo.Pedido;
import uniandes.dpoo.taller1.modelo.Producto;
import uniandes.dpoo.taller1.modelo.ProductoAjustado;
import uniandes.dpoo.taller1.modelo.ProductoMenu;
import uniandes.dpoo.taller1.procesamiento.LoaderAplicacion;
   
/**
 * @author Usuario
 *
 */
public class ConsolaAplicacion 
{
	private ArrayList<Ingrediente> ingredientes;
	
	private ArrayList<ProductoMenu> productosMenu;
	
	private ArrayList<Combo> combos;
	
	private ArrayList<Pedido> pedidos;
	
	private Pedido pedidoEnCurso;
	
	public void ejecutarAplicacion()
	{
		cargarArchivo();
		Boolean continuar=true;
		while(continuar)
		{
			try 
			{
				mostrarMenu();
				int opcion = Integer.parseInt(input("Seleccione una opcion"));
				if(opcion == 1)
				{
					System.out.println(" ");
					String nombreCliente = input("Ingrese su nombre ");
					String direccionCliente = input("Ingrese su direccion ");
					if(!nombreCliente.equals("") && !direccionCliente.equals(""))
					{
						iniciarPedido(nombreCliente,direccionCliente);
						cerrarYGuardarPedido();
					}
					else
					{
						System.out.println("\nDebe colocar un nombre y una direccion validas\n");
					}
				}
				else if(opcion == 2)
				{
					System.out.println(" ");
					int id = Integer.parseInt(input("Escriba el ID a consultar"));
					Pedido pedido = consultarPedidoID(id);
					if(pedido!=null)
					{
						System.out.println("\nSe encontro el pedido solicitado, su factura es:\n");
						ArrayList<Producto> items = pedido.getItemsPedido();
						System.out.println("Nombre del cliente: "+pedido.getNombreCliente());
						System.out.println("Direccion del cliente: "+pedido.getDireccionCliente());
						System.out.println("|Items del pedido|");
						for(Producto producto:items)
						{
							System.out.println(centrarCadena(producto.getNombre(), 20)+centrarCadena(Double.toString(producto.getPrecio()), 20));
						}
						System.out.println("|Precio total del pedido|");
						System.out.println(pedido.getPrecioTotalPedido());
					}
					else 
					{
						System.out.println("No existe un pedido con ese ID");
					}
				}
				else if(opcion == 3)
				{
					System.out.println(" ");
					System.out.println("Saliendo de la aplicacion...");
					continuar = false;
				}
				else
				{
					System.out.println("Seleccione una opcion valido");
				}
				
			} catch (Exception e) {
				System.out.println("Por favor ingrese un dato valido");
			}
		}
	}

	private void cargarArchivo() 
	{
		LoaderAplicacion listaCarga = new LoaderAplicacion();
		ingredientes = listaCarga.getIngredientes();
		productosMenu = listaCarga.getProductosMenu();
		combos = listaCarga.getCombos();
		pedidos = listaCarga.getPedidos();
	}

	private void mostrarMenu() 
	{
		System.out.println("*************************************");
		System.out.println("               MENU                  ");
		System.out.println("1. Iniciar un nuevo pedido");
		System.out.println("2. Consultar un pedido");
		System.out.println("3. Salir de la aplicacion\n");
	}
	
	private Pedido consultarPedidoID(int id)
	{
		Pedido buscado = null;
		for(Pedido pedido:pedidos)
		{
			if(pedido.getIdPedido()==id)
			{
				buscado = pedido;
			}
		}
		return buscado;
	}
	
	/**
	 * Este método sirve para imprimir un mensaje en la consola pidiéndole
	 * información al usuario y luego leer lo que escriba el usuario.
	 * 
	 * @param mensaje El mensaje que se le mostrará al usuario
	 * @return La cadena de caracteres que el usuario escriba como respuesta.
	 */
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{
		//SE DEBE VOLVER A INICIAL EL PEDIDO EN CURSO!!!!!!
		pedidoEnCurso = new Pedido(nombreCliente,direccionCliente);
		Boolean continuar=true;
		while(continuar)
		{
			try 
			{
				System.out.println("\n¿Desea pedir un combo o un producto del menu?");
				System.out.println("1.Combo");
				System.out.println("2.Producto del menu\n");
				int opcion = Integer.parseInt(input("Seleccione una opcion"));
				
				if(opcion==1)
				{
					System.out.println(" ");
					System.out.println(centrarCadena("COMBOS", 42));
					System.out.println(" ------------------------------------------ ");
					for(int i=0;i<combos.size();i++)
					{
						Combo combo = combos.get(i);
						int n =i+1;
						String s =centrarCadena(n+"."+combo.getNombre(), 20)+centrarCadena(Double.toString(Math.round(combo.getPrecio()*100.0/100.0)), 20);
						System.out.println(s);
					}
					int opcionCombo = Integer.parseInt(input("Seleccione un combo"));
					if(opcionCombo<=combos.size())
					{
						Combo combo = combos.get(opcionCombo-1);
						pedidoEnCurso.agregarProducto(combo);
						System.out.println("\n¿Desea seguir pidiendo?");
						System.out.println("1.Si, seguir pidiendo");
						System.out.println("2.No, vamos a finalizar el pedido");
						int opcionSeguir = Integer.parseInt(input("Seleccione una opcion"));
						if(opcionSeguir==1)
						{
							//Sigue pidiendo
						}
						else if(opcionSeguir==2)
						{
							continuar=false;
						}
						else
						{
							System.out.println("Seleccione una opcion valida");
						}
					}
					else
					{
						System.out.println("Seleccione una opcion valida");
					}
				}
				else if(opcion==2)
				{
					System.out.println(" ");
					System.out.println(centrarCadena("PRODUCTOS", 62));
					System.out.println(" -------------------------------------------------------------- ");
					for(int i=0;i<productosMenu.size();i++)
					{
						ProductoMenu producto = productosMenu.get(i);
						int n =i+1;
						String s =centrarCadena(n+"."+producto.getNombre(), 30)+centrarCadena(Double.toString(Math.round(producto.getPrecio()*100.0/100.0)), 30);
						System.out.println(s);
					}
					int opcionProducto = Integer.parseInt(input("Seleccione un producto"));
					if(opcionProducto<=productosMenu.size())
					{
						ProductoMenu producto = productosMenu.get(opcionProducto-1);
						System.out.println("\n¿Desea agregarle o quitarle algo al producto?");
						System.out.println("1.Si, modifiquemos el producto");
						System.out.println("2.No, quiero dejarlo asi");
						int opcionAgregar = Integer.parseInt(input("Seleccione una opcion"));
						if(opcionAgregar==1)
						{
							Boolean continuarModificandolo=true;
							while(continuarModificandolo)
							{

								System.out.println(centrarCadena("Ingredientes para modificar", 40));
								for(int i=0;i<ingredientes.size();i++)
								{
									Ingrediente ingrediente = ingredientes.get(i);
									int n =i+1;
									String s =centrarCadena(n+"."+ingrediente.getNombre(), 30)+centrarCadena(Double.toString(Math.round(ingrediente.getCostoAdicional()*100.0/100.0)), 30);
									System.out.println(s);
								}
								int opcionIngrediente = Integer.parseInt(input("Seleccione una opcion"));
								Ingrediente ingrediente = ingredientes.get(opcionIngrediente-1);
								System.out.println("\n¿Desea agregarselo al producto o desea removerlo del pedido?");
								System.out.println("1.Agregemoslo");
								System.out.println("2.Quitemoslo");
								int opcionModificacion = Integer.parseInt(input("Seleccione una opcion"));
								if(opcionModificacion==1)
								{
									ProductoAjustado productoAjustado = new ProductoAjustado(producto);
									productoAjustado.agregarIngredienteAgregados(ingrediente);
									System.out.println("\n¿Desea seguir modificando el pedido?");
									System.out.println("1.Si");
									System.out.println("2.NO");
									int opcionSeguirModificando = Integer.parseInt(input("Seleccione una opcion"));
									if(opcionSeguirModificando==1)
									{
										//Seguir modificandolo
									}
									else if(opcionSeguirModificando==2)
									{
										continuarModificandolo=false;
										pedidoEnCurso.agregarProducto(productoAjustado);
										System.out.println("\n¿Desea seguir pidiendo?");
										System.out.println("1.Si, seguir pidiendo");
										System.out.println("2.No, vamos a finalizar el pedido");
										int opcionSeguir = Integer.parseInt(input("Seleccione una opcion"));
										if(opcionSeguir==1)
										{
											//Sigue pidiendo
										}
										else if(opcionSeguir==2)
										{
											continuar=false;
										}
										else
										{
											System.out.println("Seleccione una opcion valida");
										}
									}
									else 
									{
										System.out.println("Seleccione una opcion valida");
									}
								}
								else if(opcionModificacion==2)
								{

									ProductoAjustado productoAjustado = new ProductoAjustado(producto);
									productoAjustado.agregarIngredienteEliminados(ingrediente);
									System.out.println("\n¿Desea seguir modificando el pedido?");
									System.out.println("1.Si");
									System.out.println("2.NO");
									int opcionSeguirModificando = Integer.parseInt(input("Seleccione una opcion"));
									if(opcionSeguirModificando==1)
									{
										//Seguir modificandolo
									}
									else if(opcionSeguirModificando==2)
									{
										continuarModificandolo=false;
										pedidoEnCurso.agregarProducto(productoAjustado);
										System.out.println("\n¿Desea seguir pidiendo?");
										System.out.println("1.Si, seguir pidiendo");
										System.out.println("2.No, vamos a finalizar el pedido");
										int opcionSeguir1 = Integer.parseInt(input("Seleccione una opcion"));
										if(opcionSeguir1==1)
										{
											//Sigue pidiendo
										}
										else if(opcionSeguir1==2)
										{
											continuar=false;
										}
										else
										{
											System.out.println("Seleccione una opcion valida");
										}
									}
									else 
									{
										System.out.println("Seleccione una opcion valida");
									}
								}
								else
								{
									System.out.println("Seleccione una opcion valida");
								}
							}
						}
						else if (opcionAgregar==2)
						{
							pedidoEnCurso.agregarProducto(producto);
							System.out.println("\n¿Desea seguir pidiendo?");
							System.out.println("1.Si, seguir pidiendo");
							System.out.println("2.No, vamos a finalizar el pedido");
							int opcionSeguir1 = Integer.parseInt(input("Seleccione una opcion"));
							if(opcionSeguir1==1)
							{
								//Sigue pidiendo
							}
							else if(opcionSeguir1==2)
							{
								continuar=false;
							}
							else
							{
								System.out.println("Seleccione una opcion valida");
							}
						}
						else
						{
							System.out.println("Seleccione una opcion valida");
						}
					}
					else
					{
						System.out.println("Seleccione una opcion valida");
					}
				}
				else
				{
					System.out.println("Seleccione una opcion valida");
				}
			} 
			catch (Exception e) 
			{
				System.out.println("Escribe un dato valido");
			}
		}
	}
	
	private String centrarCadena(String s, int ancho)
	{
        int lonText=s.length();
        int tamañoCampo=(ancho/2)+(lonText/2);
        String m= String.format("%" + tamañoCampo + "s", s);
        s = String.format("%" + tamañoCampo + "s", s).replace(" ","*");
        s = String.format("%-" + ancho  + "s", s).replace(" ","*");
        m= String.format("%-" + ancho + "s", m);
        return "|"+m+"|";
	}
	
	public void cerrarYGuardarPedido()
	{
		pedidos.add(pedidoEnCurso);
		System.out.println("\nPedido generado con exito, ID del pedido: "+pedidoEnCurso.getIdPedido()+"\n");
		try {
            String ruta = "./data/pedidos.txt";
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for(Pedido pedido:pedidos)
            {
                bw.write(pedido.generarTextoFactura());
            }
            
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		ConsolaAplicacion consola = new ConsolaAplicacion();
		consola.ejecutarAplicacion();
	}

	public ArrayList<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public ArrayList<ProductoMenu> getItemsPedido() {
		return productosMenu;
	}

	public ArrayList<Combo> getCombos() {
		return combos;
	}

}
