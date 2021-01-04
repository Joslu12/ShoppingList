package model;

import androidx.annotation.NonNull;

/**
 * La clase Producto modela el comportamiento de los objetos producto del sistema. Almacena un nombre y una cantidad a comprar.
 * Implementa el interfaz Cloneable y no es editable. Es decir, el nombre o la cantidad objetivo indicada al inicio son los valores 
 * finales del objeto
 */
public class Product extends IdentifiedObjectClass implements Cloneable {

	//---- Attributes ----
	private final String name;
	protected final int targetAmount;
	
	//---- Constructor ----
	/**
	 * Devuelve una instancia del tipo Producto con el nombre y la cantidad objetivo especificadas
	 * @param name del producto
	 * @param targetAmount numero de unidades a obtener del producto
	 * @throws ShoppingListException en el caso de que cantObj <= 0
	 */
	public Product(final String name, final int targetAmount) throws ShoppingListException {
		this(-1,name,targetAmount);
	}

	/**
	 * Devuelve una instancia del tipo Producto con el nombre y la cantidad objetivo especificadas
	 * @param id del producto
	 * @param name del producto
	 * @param targetAmount numero de unidades a obtener del producto
	 * @throws ShoppingListException en el caso de que cantObj <= 0
	 */
	public Product(final int id, final String name, final int targetAmount) throws ShoppingListException {
		super(id);
		if(targetAmount <= 0) {
			throw new ShoppingListException("ERROR. La cantidad objetivo debe ser mayor que 0");
		}
		this.name = name;
		this.targetAmount = targetAmount;
	}
	
	/**
	 * Devuelve una instacia del tipo Producto con el id, el nombre y la cantidad objetivo del producto
	 * recibido como argumento
	 * @param p Producto del que copiar
	 */
	public Product(final Product p) {
		super(p.getID());
		this.name = p.name;
		this.targetAmount = p.targetAmount;
	}
	
	//---- Methods ----
	/**
	 * @return nombre del Producto
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return cantidad objetivo del Producto
	 */
	public int getTargetAmount() {
		return targetAmount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Dos productos son iguales si tienen el mismo nombre
	 * @return true sii obj es un Producto con el mismo nombre
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (name == null) {
			return other.name == null;
		} else return name.equals(other.name);
	}

	/**
	 * @return Producto con el mismo estado que el actual
	 */
	@NonNull
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Product(this);
	}
}
