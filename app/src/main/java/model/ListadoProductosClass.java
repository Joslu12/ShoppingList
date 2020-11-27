package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La clase abstracta ListadoProductosClass modela el comportamiento de un objeto que almacena un listado de productos. Implementando
 * las operaciones add y remove, así como un iterador.
 *
 * @param <E> representa una clase que hereda de la clase Producto
 */

public abstract class ListadoProductosClass<E extends Producto> extends IdentifiedObjectClass {

	//---- Atributos ----
	protected String nombre;
	protected List<E> productos;
	
	//---- Constructor ----
	/**
	 * Devuelve una instancia con un objeto donde la lista de productos está vacía
	 * @param id del objeto
	 * @param nombre del objeto
	 */
	public ListadoProductosClass(final int id, final String nombre) {
		this(id,nombre,new ArrayList<E>());
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Devuleve una instancia con un objeto donde la lista de productos es la copia del parametro recibido
	 * @param id del objeto
	 * @param nombre del objeto
	 * @param lista de productos a copiar
	 */
	public ListadoProductosClass(final int id, final String nombre, final List<E> lista) {
		super(id);
		this.nombre = nombre;
		this.productos = new ArrayList<E>();
		for(E p : lista) {
			try {
				productos.add((E) p.clone());
			} catch (CloneNotSupportedException e) {}
		}
	}
	
	//---- Metodos ----
	/**
	 * @return nombre del ListadoProductos
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Cambia el nombre del objeto
	 * @param nombre nuevo del objeto
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return iterador que permita recorrer los productos del listado
	 */
	public Iterator<E> getProductos() {
		return productos.iterator();
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Añade al listado de productos una copia del Producto p recibido como argumento solo si la lista no contiene
	 * algun producto con el mismo nombre
	 * @param p Producto añadir
	 */
	public boolean addProducto(final E p) {
		try {
			if(!productos.contains(p)) {
				productos.add((E) p.clone());
				return true;
			} else {
				return false;
			}
		} catch (CloneNotSupportedException e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Elimina del listado de productos el Producto que sea igual a p recibido como argumento
	 * @param p Producto a eliminar
	 */
	public boolean removeProducto(final E p) {
		try {
			boolean resultado = productos.remove((E) p.clone());
			return resultado;
		} catch (CloneNotSupportedException e) {
			return false;
		}
	}
}
