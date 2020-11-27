package model;

/**
 * La clase Producto modela el comportamiento de los objetos producto del sistema. Almacena un nombre y una cantidad a comprar.
 * Implementa el interfaz Cloneable y no es editable. Es decir, el nombre o la cantidad objetivo indicada al inicio son los valores 
 * finales del objeto
 */
public class Producto extends IdentifiedObjectClass implements Cloneable {

	//---- Atributos ----
	private final String nombre;
	protected final int cantidadObjetivo;
	
	//---- Constructor ----
	/**
	 * Devuelve una instancia del tipo Producto con el nombre y la cantidad objetivo especificadas
	 * @param nombre del producto
	 * @param cantObj numero de unidades a obtener del producto
	 * @throws ShoppingListException en el caso de que cantObj <= 0
	 */
	public Producto(final String nombre, final int cantObj) throws ShoppingListException {
		this(-1,nombre,cantObj);
	}

	/**
	 * Devuelve una instancia del tipo Producto con el nombre y la cantidad objetivo especificadas
	 * @param id del producto
	 * @param nombre del producto
	 * @param cantObj numero de unidades a obtener del producto
	 * @throws ShoppingListException en el caso de que cantObj <= 0
	 */
	public Producto(final int id, final String nombre, final int cantObj) throws ShoppingListException {
		super(id);
		if(cantObj <= 0) {
			throw new ShoppingListException("ERROR. La cantidad objetivo debe ser mayor que 0");
		}
		this.nombre = nombre;
		this.cantidadObjetivo = cantObj;
	}
	
	/**
	 * Devuelve una instacia del tipo Producto con el id, el nombre y la cantidad objetivo del producto
	 * recibido como argumento
	 * @param p Producto del que copiar
	 */
	public Producto(final Producto p) {
		super(p.getID());
		this.nombre = p.nombre;
		this.cantidadObjetivo = p.cantidadObjetivo;
	}
	
	//---- Metodos ----
	/**
	 * @return nombre del Producto
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @return cantidad objetivo del Producto
	 */
	public int getCantidadObjetivo() {
		return cantidadObjetivo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		if (!(obj instanceof Producto))
			return false;
		Producto other = (Producto) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	/**
	 * @return Producto con el mismo estado que el actual
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Producto p = new Producto(this);
		return p;
	}
}
