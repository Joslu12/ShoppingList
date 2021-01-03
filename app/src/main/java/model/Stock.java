package model;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Inventario ofrece el comportamiento de un inventario llevando un listado de ProductoInventario
 * permitiendo editar las unidades restantes de estos asi como generar una lista de la compra
 *
 */
public class Stock extends ProductsListClass<StockProduct> {

	//---- Attributes ----
	private int associatedListId;

	//---- Constructor ----
	/**
	 * Devuelve un nuevo Inventario con 0 productos
	 * @param name del inventario
	 */
	public Stock(final String name) {
		super(-1,name);
		associatedListId = -1;
	}
	
	/**
	 * Devuelve un nuevo Inventario con los productos especificados como argumentos
	 * @param id del inventario
	 * @param name del inventario
	 * @param productsList de productos
	 */
	public Stock(final int id, final String name, final List<StockProduct> productsList) {
		this(id,name,productsList,-1);
	}

	/**
	 * Devuelve un nuevo Inventario con los productos especificados como argumentos
	 * @param id del inventario
	 * @param name del inventario
	 * @param productsList de productos
	 * @param associatedListId id de la lista asociada
	 */
	public Stock(final int id, final String name, final List<StockProduct> productsList, final int associatedListId) {
		super(id,name,productsList);
		this.associatedListId = associatedListId;
	}
	
	//---- Methods ----

	/**
	 * @return el id de la lista asociada al Stock
	 */
	public int getAssociatedListId() {
		return associatedListId;
	}

	/**
	 * Actualiza el id de la lista asociada al Stock
	 * @param id de la lista asociada al inventario
	 */
	public void setAssociatedListId(final int id) {
		associatedListId = id;
	}

	/**
	 * Incrementa en 1 las unidades del producto p si es posible
	 * @param p Producto al que incrementar las unidades
	 * @return true si se ha podido incrementar en uno las unidades, false en otro caso
	 */
	public boolean addUnitToProduct(final StockProduct p) {
		return this.products.get(products.indexOf(p)).increaseCurrentAmount();
	}
	
	/**
	 * Decrementa en 1 las unidades del producto p si es posible
	 * @param p Producto al que decrementar las unidades
	 * @return true si se ha podido decrementar en uno las unidades, false en otro caso
	 */
	public boolean subtractUnitToProduct(final StockProduct p) {
		return this.products.get(products.indexOf(p)).decreaseCurrentAmount();
	}
	
	/**
	 * Las unidades actuales del producto pasaran a ser igual a las unidades objetivo
	 * @param p Producto al que completar las unidades
	 * @throws ShoppingListException 
	 */
	public void completeUnitsProduct(final Product p) throws ShoppingListException {
		// Hay necesidad de poner ownProduct, porque p es un Producto, y por tanto no tiene currentAmount
		StockProduct ownProduct = this.products.get(products.indexOf(p));
		ownProduct.setCurrentAmount(ownProduct.getTargetAmount());
	}
	
	/**
	 * Genera una ListaCompraInventario con objetos del tipo Producto construida a partir de
	 * los ProductosInventario cuyas unidades actuales es menor que su cantidad objetivo 
	 * @return ListaCompraInventario con los Productos a los que les faltan unidades
	 */
	public StockShoppingList generateShoppingList(String name) {
		List<Product> productsList = new ArrayList<Product>();
		for(StockProduct p : this.products) {
			if(p.getCurrentAmount() < p.getTargetAmount()) {
				try {
					productsList.add(new Product(p.getName(), p.getTargetAmount() - p.getCurrentAmount()));
				} catch (ShoppingListException e) {}
			}
		}
		
		StockShoppingList shoppingList = new StockShoppingList(name,productsList,this);
		return shoppingList;
	}
	
}
