package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * La clase ListaCompra ofrece el comportamiento de una lista de la compra llevando un listado de Producto
 * a comprar. Ofrece toda la funcionalidad necesaria para ejecutar el proceso de compra.
 */
public class ShoppingList extends ProductsListClass<Product> {

	//---- Attributes ----
	private List<Product> purchasedProducts; //Productos comprados durante el proceso de compra
	private List<Product> productsToPurchase; //Productos por comprar durante el proceso de compra
	private boolean buying;
	
	//---- Constructor ----
	/**
	 * Devuelve una nueva ListaCompra con 0 productos
	 * @param name de la lista
	 */
	public ShoppingList(final String name) {
		super(-1,name,new ArrayList<Product>());
	}

	/**
	 * Devuelve una nueva ListaCompra con los productos especificados como argumentos
	 * @param name de la lista
	 * @param list de productos
	 */
	public ShoppingList(final String name, final List<Product> list) {
		this(-1,name,list);
	}

	/**
	 * Devuelve una nueva ListaCompra con los productos especificados como argumentos
	 * @param id de la lista
	 * @param name de la lista
	 * @param list de productos
	 */
	public ShoppingList(final int id, final String name, final List<Product> list) {
		super(id,name,list);
		purchasedProducts = null;
		productsToPurchase = null;
		buying = false;
	}
	
	//---- Methods ----
	/**
	 * Inicializa el estado del objeto preparandolo para realizar una compra
	 */
	public void initPurchasing() {
		purchasedProducts = new ArrayList<Product>();
		productsToPurchase = new ArrayList<Product>();
		for(Product p : this.products) {
			productsToPurchase.add(new Product(p));
		}
		buying = true;
	}
	
	/**
	 * Permite indicar que un producto ya se ha comprado
	 * @param p Producto a marcar como comprado
	 * @return true si el Producto p pertenece a la lista de objetos que aun no se han marcado
	 * @throws ShoppingListException si se intenta ejecutar esta operacion cuando no se esta en un estado de
	 * compra
	 */
	public boolean checkProduct(final Product p) throws ShoppingListException {
		if(!buying) {
			throw new ShoppingListException("ERROR. La lista de la compra no se encuentra en estado de compra");
		}
		if(!productsToPurchase.remove(p)) {
			return false;
		} else {
			purchasedProducts.add(new Product(p));
			return true;
		}
	}
	
	/**
	 * Permite indicar que un producto se ha desmarcado
	 * @param p Producto a desmarcar
	 * @return true si el Producto p pertenece a la lista de objetos que ya se han comprado
	 * @throws ShoppingListException si se intenta ejecutar esta operacion cuando no se esta en un estado de
	 * compra
	 */
	public boolean uncheckProduct(final Product p) throws ShoppingListException {
		if(!buying) {
			throw new ShoppingListException("ERROR. La lista de la compra no se encuentra en estado de compra");
		}
		if(!purchasedProducts.remove(p)) {
			return false;
		} else {
			productsToPurchase.add(new Product(p));
			return true;
		}
	}
	
	/**
	 * Se pasa al sistema a un estado seguro indicando que no se esta comprando
	 */
	public void finishPurchasing() {
		buying = false;
	}
	
	/**
	 * @return Iterator<Producto> con los productos que han sido comprados
	 */
	public Iterator<Product> getPurchasedProducts() {
		return purchasedProducts.iterator();
	}
	
	/**
	 * 
	 * @return Iterator<Producto> con los productos que faltan por comprar
	 */
	public Iterator<Product> getProductsToPurchase() {
		return productsToPurchase.iterator();
	}
	
	/**
	 * Tras finalizar una compra se puede indicar que se desea hacer con los resultados:
	 * 		- DESCARTAR: se devuelve una listaCompra igual a null
	 * 		- GUARDAR_LISTA: se devuelve la lista con todos los productos que estaban antes de la compra
	 * 		- ACTUALIZAR_LISTA: se devuelve la lista pero solo con los productos que han faltado por comprar
	 * 		- GENERAR_LISTA_RESTANTES: devuelve una nueva lista con los productos que han faltado por comprar
	 * @param operation a realizar
	 * @return ListaCompra segun la operacion indicada
	 * @throws ShoppingListException en el caso de que la compra este activa
	 */
	public ShoppingList finishSummary(final EndPurchaseOperation operation) throws ShoppingListException {
		if(buying) {
			throw new ShoppingListException("ERROR. El resumen debe darse sobre un estado de compra nulo");
		}
		ShoppingList shoppingList = null;
		switch(operation) {
			case DISCARD:
				break;
				
			case SAVE_LIST:
				shoppingList = this;
				break;
				
			case UPDATE_LIST:
				for(Product p : this.purchasedProducts) {
					this.removeProduct(p);
				}
				shoppingList = this;
				break;
				
			case GENERATE_REMAINING_LIST:
				shoppingList = new ShoppingList(this.name + " x" + (new Random().nextInt(100)*new Random().nextInt(100)), productsToPurchase);
				break;
		}
		return shoppingList;
	}
}
