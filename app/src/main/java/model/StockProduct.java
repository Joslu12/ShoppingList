package model;

/**
 * La clase ProductoInventario hereda de la clase Producto. Almacena ademas la cantidad actual del producto. Asi como, ofrece los
 * metodos correspondientes para modelar el comportamiento de este tipo de productos
 */
public class StockProduct extends Product implements Cloneable {

	//---- Attributes ----
	private int currentAmount;
	
	//---- Constructor ----
	/**
	 * Devuelve una instancia del tipo ProductoInventario con los argumentos especificados
	 * @param name del objeto
	 * @param targetAmount numero unidades a obtener del producto
	 * @param currentAmount numero de unidades restantes del producto
	 * @throws ShoppingListException en el caso de que cantObj <= 0 o cantActual < 0 || cantActual > cantObj
	 */
	public StockProduct(final String name, final int targetAmount, final int currentAmount) throws ShoppingListException {
		this(-1,name,targetAmount,currentAmount);
	}
	
	/**
	 * Devuelve una instancia del tipo ProductoInventario con los argumentos especificados
	 * @param id del objeto
	 * @param name del objeto
	 * @param targetAmount numero unidades a obtener del producto
	 * @param currentAmount numero de unidades restantes del producto
	 * @throws ShoppingListException en el caso de que cantObj <= 0 o cantActual < 0 || cantActual > cantObj
	 */
	public StockProduct(final int id, final String name, final int targetAmount, final int currentAmount) throws ShoppingListException {
		super(id,name,targetAmount);
		if(currentAmount < 0 || currentAmount > targetAmount) {
			throw new ShoppingListException("ERROR. La cantidad restante de productos debe ser mayor que 0 y menor o igual que la cantidad objetivo");
		}
		this.currentAmount = currentAmount;
	}
	
	/**
	 * Devuelve una instacia del tipo ProductoInventario con el nombre, la cantidad objetivo y las unidades actuales del producto
	 * recibido como argumento
	 * @param p ProductoInventario del que copiar
	 */
	public StockProduct(final StockProduct p) {
		super(p);
		this.currentAmount = p.currentAmount;
	}
	
	//---- Methods ----
	/**
	 * @return cantidad actual del producto 
	 */
	public int getCurrentAmount() {
		return currentAmount;
	}
	
	/**
	 * Actualiza el valor de la cantidad actual del producto con el parametro cant
	 * @param amount cantidad actual nueva
	 * @throws ShoppingListException si cant > this.cantidadObjetivo
	 */
	public void setCurrentAmount(final int amount) throws ShoppingListException {
		if(amount > this.targetAmount) {
			throw new ShoppingListException("ERROR. La cantidad indicada es mayor que la cantidad objetivo");
		} else {
			this.currentAmount = amount;
		}
	}
	
	/**
	 * Incrementa en 1 la cantidad actual del producto
	 * @return false si no se ha podido incrementar por ya estar al maximo de unidades
	 */
	public boolean increaseCurrentAmount() {
		if(this.currentAmount + 1 > this.targetAmount) {
			return false;
		} else {
			++this.currentAmount;
			return true;
		}
	}
	
	/**
	 * Decrementa en 1 la cantidad actual del producto
	 * @return false si no quedan unidades del producto por consumir
	 */
	public boolean decreaseCurrentAmount() {
		if(this.currentAmount - 1 < 0) {
			return false;
		} else {
			--this.currentAmount;
			return true;
		}
	}
	
	/**
	 * @return ProductoInventario con el mismo estado que el actual
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		StockProduct p = new StockProduct(this);
		return p;
	}
}
