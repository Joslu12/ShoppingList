package model;

import java.util.Iterator;
import java.util.List;

/**
 * Un objeto de la clase ListaCompraInventario tiene el mismo comportamiento que una ListaCompra. Aunque ademas
 * almacena una referencia al inventario que la genera para poder modificar sus datos tras finalizar una compra
 *
 */

public class StockShoppingList extends ShoppingList {

	//---- Attributes ----
	private Stock associatedStock;
	
	//---- Constructor ----
	/**
	 * Devuelve una nueva ListaCompraInventario
	 * @param name de la lista
	 * @param list de productos a comprar
	 * @param stock asociado a la lista compra
	 */
	public StockShoppingList(final String name, final List<Product> list, final Stock stock) {
		this(-1,name,list, stock);
	}

	/**
	 * Devuelve una nueva ListaCompraInventario
	 * @param id de la lista
	 * @param name de la lista
	 * @param list de productos a comprar
	 * @param stock asociado a la lista compra
	 */
	public StockShoppingList(final int id, final String name, final List<Product> list, final Stock stock) {
		super(id,name,list);
		this.associatedStock = stock;
	}
	
	//---- Methods ----
	public int getAssociatedStockID() {
		return associatedStock.getID();
	}
	/**
	 * Redefine el metodo finalizarResumen de la clase padre. En este caso cuando se finaliza el resumen, independientemente
	 * de la operacion especificada siempre se descartara la lista tras haber actualizado las unidades del inventario.
	 * Que se descarte implica que se debe eliminar
	 */
	@Override
	public ShoppingList finishSummary(final EndPurchaseOperation operation) {
		Iterator<Product> purchasedProductsIt = this.getPurchasedProducts();
		
		while(purchasedProductsIt.hasNext()) {
			Product p = purchasedProductsIt.next();
			try {
				associatedStock.completeUnitsProduct(p);
			} catch (ShoppingListException e) {
				return null; //TODO: throw exception indicado que algo ha pasado
			}
		}
		
		return null;
	}
}
