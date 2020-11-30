package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La clase abstracta ListadoProductosClass modela el comportamiento de un objeto que almacena un listado de productos. Implementando
 * las operaciones add y remove, asi como un iterador.
 *
 * @param <E> representa una clase que hereda de la clase Producto
 */

public abstract class ProductsListClass<E extends Product> extends IdentifiedObjectClass {

	//---- Atributos ----
	protected String name;
	protected List<E> products;
	
	//---- Constructor ----
	/**
	 * Devuelve una instancia con un objeto donde la lista de productos est� vac�a
	 * @param id del objeto
	 * @param name del objeto
	 */
	public ProductsListClass(final int id, final String name) {
		this(id, name,new ArrayList<E>());
	}

	/**
	 * Devuleve una instancia con un objeto donde la lista de productos es la copia del parametro recibido
	 * @param id del objeto
	 * @param name del objeto
	 * @param list de productos a copiar
	 */
	public ProductsListClass(final int id, final String name, final List<E> list) {
		super(id);
		this.name = name;
		this.products = new ArrayList<E>();
		for(E p : list) {
			try {
				products.add((E) p.clone());
			} catch (CloneNotSupportedException e) {}
		}
	}
	
	//---- Metodos ----
	/**
	 * @return nombre del ListadoProductos
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Cambia el nombre del objeto
	 * @param name nuevo del objeto
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * @return iterador que permita recorrer los productos del listado
	 */
	public Iterator<E> getProducts() {
		return products.iterator();
	}

	/**
	 * Anade al listado de productos una copia del Producto p recibido como argumento solo si la lista no contiene
	 * algun producto con el mismo nombre
	 * @param p Producto anadir
	 */
	public boolean addProduct(final E p) {
		try {
			if(!products.contains(p)) {
				products.add((E) p.clone());
				return true;
			} else {
				return false;
			}
		} catch (CloneNotSupportedException e) {
			return false;
		}
	}

	/**
	 * Elimina del listado de productos el Producto que sea igual a p recibido como argumento
	 * @param p Producto a eliminar
	 */
	public boolean removeProduct(final E p) {
		try {
			return products.remove((E) p.clone());
		} catch (CloneNotSupportedException e) {
			return false;
		}
	}
}
