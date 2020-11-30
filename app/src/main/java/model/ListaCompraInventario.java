package model;

import java.util.Iterator;
import java.util.List;

/**
 * Un objeto de la clase ListaCompraInventario tiene el mismo comportamiento que una ListaCompra. Aunque ademas
 * almacena una referencia al inventario que la genera para poder modificar sus datos tras finalizar una compra
 *
 */

public class ListaCompraInventario extends ListaCompra {

	//---- Atributos ----
	private Inventario inventarioAsociado;
	
	//---- Constructor ----
	/**
	 * Devuelve una nueva ListaCompraInventario
	 * @param nombre de la lista
	 * @param lista de productos a comprar
	 * @param inventario asociado a la lista compra
	 */
	public ListaCompraInventario(final String nombre, final List<Producto> lista, final Inventario inventario) {
		this(-1,nombre,lista,inventario);
	}

	/**
	 * Devuelve una nueva ListaCompraInventario
	 * @param id de la lista
	 * @param nombre de la lista
	 * @param lista de productos a comprar
	 * @param inventario asociado a la lista compra
	 */
	public ListaCompraInventario(final int id, final String nombre, final List<Producto> lista, final Inventario inventario) {
		super(id,nombre,lista);
		this.inventarioAsociado = inventario;
	}
	
	//---- Metodos ----
	public int getIDInventarioAsociado() {
		return inventarioAsociado.getID();
	}
	/**
	 * Redefine el metodo finalizarResumen de la clase padre. En este caso cuando se finaliza el resumen, independientemente
	 * de la operacion especificada siempre se descartara la lista tras haber actualizado las unidades del inventario.
	 * Que se descarte implica que se debe eliminar
	 */
	@Override
	public ListaCompra finalizarResumen(final OpFinalCompra operacion) {
		Iterator<Producto> productosCompradosIt = this.getProductosComprados();
		
		while(productosCompradosIt.hasNext()) {
			Producto p = productosCompradosIt.next();
			try {
				inventarioAsociado.completarUnidadesProducto(p);
			} catch (ShoppingListException e) {}
		}
		
		return null;
	}
}
