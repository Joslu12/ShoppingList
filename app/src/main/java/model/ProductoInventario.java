package model;

/**
 * La clase ProductoInventario hereda de la clase Producto. Almacena ademas la cantidad actual del producto. Asi como, ofrece los
 * metodos correspondientes para modelar el comportamiento de este tipo de productos
 */
public class ProductoInventario extends Producto implements Cloneable {

	//---- Atributos ----
	private int cantidadActual;
	
	//---- Constructor ----
	/**
	 * Devuelve una instancia del tipo ProductoInventario con los argumentos especificados
	 * @param nombre del objeto
	 * @param cantObj numero unidades a obtener del producto
	 * @param cantActual numero de unidades restantes del producto
	 * @throws ShoppingListException en el caso de que cantObj <= 0 o cantActual < 0 || cantActual > cantObj
	 */
	public ProductoInventario(final String nombre, final int cantObj, final int cantActual) throws ShoppingListException {
		this(-1,nombre,cantObj,cantActual);
	}
	
	/**
	 * Devuelve una instancia del tipo ProductoInventario con los argumentos especificados
	 * @param id del objeto
	 * @param nombre del objeto
	 * @param cantObj numero unidades a obtener del producto
	 * @param cantActual numero de unidades restantes del producto
	 * @throws ShoppingListException en el caso de que cantObj <= 0 o cantActual < 0 || cantActual > cantObj
	 */
	public ProductoInventario(final int id, final String nombre, final int cantObj, final int cantActual) throws ShoppingListException {
		super(id,nombre,cantObj);
		if(cantActual < 0 || cantActual > cantObj) {
			throw new ShoppingListException("ERROR. La cantidad restante de productos debe ser mayor que 0 y menor o igual que la cantidad objetivo");
		}
		this.cantidadActual = cantActual;
	}
	
	/**
	 * Devuelve una instacia del tipo ProductoInventario con el nombre, la cantidad objetivo y las unidades actuales del producto
	 * recibido como argumento
	 * @param p ProductoInventario del que copiar
	 */
	public ProductoInventario(final ProductoInventario p) {
		super(p);
		this.cantidadActual = p.cantidadActual;
	}
	
	//---- Metodos ----
	/**
	 * @return cantidad actual del producto 
	 */
	public int getCantidadActual() {
		return cantidadActual;
	}
	
	/**
	 * Actualiza el valor de la cantidad actual del producto con el parametro cant
	 * @param cant cantidad actual nueva
	 * @throws ShoppingListException si cant > this.cantidadObjetivo
	 */
	public void setCantidadActual(final int cant) throws ShoppingListException {
		if(cant > this.cantidadObjetivo) {
			throw new ShoppingListException("ERROR. La cantidad indicada es mayor que la cantidad objetivo");
		} else {
			this.cantidadActual = cant;
		}
	}
	
	/**
	 * Incrementa en 1 la cantidad actual del producto
	 * @return false si no se ha podido incrementar por ya estar al m�ximo de unidades
	 */
	public boolean incrementarCantidadActual() {
		if(this.cantidadActual + 1 > this.cantidadObjetivo) {
			return false;
		} else {
			++this.cantidadActual;
			return true;
		}
	}
	
	/**
	 * Decrementa en 1 la cantidad actual del producto
	 * @return false si no quedan unidades del producto por consumir
	 */
	public boolean decrementarCantidadActual() {
		if(this.cantidadActual - 1 < 0) {
			return false;
		} else {
			--this.cantidadActual;
			return true;
		}
	}
	
	/**
	 * @return ProductoInventario con el mismo estado que el actual
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		ProductoInventario p = new ProductoInventario(this);
		return p;
	}
}
