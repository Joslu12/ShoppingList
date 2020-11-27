package model;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Inventario ofrece el comportamiento de un inventario llevando un listado de ProductoInventario
 * permitiendo editar las unidades restantes de estos as/í como generar una lista de la compra
 *
 */
public class Inventario extends ListadoProductosClass<ProductoInventario> {

	//---- Atributos ----
	
	//---- Constructor ----
	/**
	 * Devuelve un nuevo Inventario con 0 productos
	 * @param nombre del inventario
	 */
	public Inventario(final String nombre) {
		super(-1,nombre);
	}
	
	/**
	 * Devuelve un nuevo Inventario con los productos especificados como argumentos
	 * @param id del inventario
	 * @param nombre del inventario
	 * @param lista de productos 
	 */
	public Inventario(final int id, final String nombre, final List<ProductoInventario> lista) {
		super(id,nombre,lista);
	}
	
	//---- Metodos ----
	/**
	 * Incrementa en 1 las unidades del producto p si es posible
	 * @param p Producto al que incrementar las unidades
	 * @return true si se ha podido incrementar en uno las unidades, false en otro caso
	 */
	public boolean sumarUnidadProducto(final ProductoInventario p) {
		return this.productos.get(productos.indexOf(p)).incrementarCantidadActual();
	}
	
	/**
	 * Decrementa en 1 las unidades del producto p si es posible
	 * @param p Producto al que decrementar las unidades
	 * @return true si se ha podido decrementar en uno las unidades, false en otro caso
	 */
	public boolean restarUnidadProducto(final ProductoInventario p) {
		return this.productos.get(productos.indexOf(p)).decrementarCantidadActual();
	}
	
	/**
	 * Las unidades actuales del producto pasarán a ser igual a las unidades objetivo
	 * @param p Producto al que completar las unidades
	 * @throws ShoppingListException 
	 */
	public void completarUnidadesProducto(final Producto p) throws ShoppingListException {
		this.productos.get(productos.indexOf(p)).setCantidadActual(p.getCantidadObjetivo());
	}
	
	/**
	 * Genera una ListaCompraInventario con objetos del tipo Producto construida a partir de
	 * los ProductosInventario cuyas unidades actuales es menor que su cantidad objetivo 
	 * @return ListaCompraInventario con los Productos a los que les faltan unidades
	 */
	public ListaCompraInventario generarListaCompra() {
		List<Producto> listaProductos = new ArrayList<Producto>();
		for(ProductoInventario p : this.productos) {
			if(p.getCantidadActual() < p.getCantidadObjetivo()) {
				try {
					listaProductos.add(new Producto(p.getNombre(), p.getCantidadObjetivo() - p.getCantidadActual()));
				} catch (ShoppingListException e) {}
			}
		}
		
		ListaCompraInventario listaCompra = new ListaCompraInventario("Lista Compra Inventario: " + this.nombre,listaProductos,this);
		return listaCompra;
	}
	
}
