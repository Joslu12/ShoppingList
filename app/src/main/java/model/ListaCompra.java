package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * La clase ListaCompra ofrece el comportamiento de una lista de la compra llevando un listado de Producto
 * a comprar. Ofrece toda la funcionalidad necesaria para ejecutar el proceso de compra.
 */
public class ListaCompra extends ListadoProductosClass<Producto> {

	//---- Atributos ----
	private List<Producto> productosComprados; //Productos comprados durante el proceso de compra
	private List<Producto> productosPorComprar; //Productos por comprar durante el proceso de compra
	private boolean comprando;
	
	//---- Constructor ----
	/**
	 * Devuelve una nueva ListaCompra con 0 productos
	 * @param nombre de la lista
	 */
	public ListaCompra(final String nombre) {
		super(-1,nombre,new ArrayList<Producto>());
	}

	/**
	 * Devuelve una nueva ListaCompra con los productos especificados como argumentos
	 * @param nombre de la lista
	 * @param lista de productos
	 */
	public ListaCompra(final String nombre, final List<Producto> lista) {
		this(-1,nombre,lista);
	}

	/**
	 * Devuelve una nueva ListaCompra con los productos especificados como argumentos
	 * @param id de la lista
	 * @param nombre de la lista
	 * @param lista de productos 
	 */
	public ListaCompra(final int id, final String nombre, final List<Producto> lista) {
		super(id,nombre,lista);
		productosComprados = null;
		productosPorComprar = null;
		comprando = false;
	}
	
	//---- Metodos ----
	/**
	 * Inicializa el estado del objeto preparandolo para realizar una compra
	 */
	public void iniciarCompra() {
		productosComprados = new ArrayList<Producto>();
		productosPorComprar = new ArrayList<Producto>();
		for(Producto p : this.productos) {
			productosPorComprar.add(new Producto(p));
		}
		comprando = true;
	}
	
	/**
	 * Permite indicar que un producto ya se ha comprado
	 * @param p Producto a marcar como comprado
	 * @return true si el Producto p pertenece a la lista de objetos que aun no se han marcado
	 * @throws ShoppingListException si se intenta ejecutar esta operacion cuando no se está en un estado de
	 * compra
	 */
	public boolean marcarProducto(final Producto p) throws ShoppingListException {
		if(!comprando) {
			throw new ShoppingListException("ERROR. La lista de la compra no se encuentra en estado de compra");
		}
		if(!productosPorComprar.remove(p)) {
			return false;
		} else {
			productosComprados.add(new Producto(p));
			return true;
		}
	}
	
	/**
	 * Permite indicar que un producto se ha desmarcado
	 * @param p Producto a desmarcar
	 * @return true si el Producto p pertenece a la lista de objetos que ya se han comprado
	 * @throws ShoppingListException si se intenta ejecutar esta operacion cuando no se está en un estado de
	 * compra
	 */
	public boolean desmarcarProducto(final Producto p) throws ShoppingListException {
		if(!comprando) {
			throw new ShoppingListException("ERROR. La lista de la compra no se encuentra en estado de compra");
		}
		if(!productosComprados.remove(p)) {
			return false;
		} else {
			productosPorComprar.add(new Producto(p));
			return true;
		}
	}
	
	/**
	 * Se pasa al sistema a un estado seguro indicando que no se está comprando
	 */
	public void finalizarCompra() {
		comprando = false;
	}
	
	/**
	 * @return Iterator<Producto> con los productos que han sido comprados
	 */
	public Iterator<Producto> getProductosComprados() {
		return productosComprados.iterator();
	}
	
	/**
	 * 
	 * @return Iterator<Producto> con los productos que faltan por comprar
	 */
	public Iterator<Producto> getProductosPorComprar() {
		return productosPorComprar.iterator();
	}
	
	/**
	 * Tras finalizar una compra se puede indicar que se desea hacer con los resultados:
	 * 		- DESCARTAR: se devuelve una listaCompra igual a null
	 * 		- GUARDAR_LISTA: se devuelve la lista con todos los productos que estaban antes de la compra
	 * 		- ACTUALIZAR_LISTA: se devuelve la lista pero solo con los productos que han faltado por comprar
	 * 		- GENERAR_LISTA_RESTANTES: devuelve una nueva lista con los productos que han faltado por comprar
	 * @param operacion a realizar
	 * @return ListaCompra segun la operacion indicada
	 * @throws ShoppingListException en el caso de que la compra esté activa
	 */
	public ListaCompra finalizarResumen(final OpFinalCompra operacion) throws ShoppingListException {
		if(comprando) {
			throw new ShoppingListException("ERROR. El resumen debe darse sobre un estado de compra nulo");
		}
		ListaCompra listaCompra = null;
		switch(operacion) {
			case DESCARTAR:
				break;
				
			case GUARDAR_LISTA:
				listaCompra = this;
				break;
				
			case ACTUALIZAR_LISTA:
				for(Producto p : this.productosComprados) {
					this.removeProducto(p);
				}
				listaCompra = this;
				break;
				
			case GENERAR_LISTA_RESTANTES:
				listaCompra = new ListaCompra(this.nombre + " x" + (new Random().nextInt(100)*new Random().nextInt(100)), productosPorComprar);
				break;
		}
		return listaCompra;
	}
}
