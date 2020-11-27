package model;

/**
 * Clase creada para manejar excepciones propias del modelo del Sistema
 */
public class ShoppingListException extends Exception {

	private static final long serialVersionUID = 1L;

	public ShoppingListException() {
		super();
	}
	
	public ShoppingListException(final String msg) {
		super(msg);
	}
}
