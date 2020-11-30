package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bd.ShoppingListContract;
import model.Producto;
import model.ProductoInventario;
import model.ShoppingListException;

public class ProductoDao extends ProductoTableDao<Producto>{

    //---- Constructor ----
    public ProductoDao(SQLiteDatabase conexion) {
        super(conexion);

    }

    //---- Metodos ----
    @Override
    public ContentValues generateContentValues(Producto elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ProductoTable.COLUMN_NAME, elem.getNombre()); //NOMBRE
        values.put(ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT, elem.getCantidadObjetivo()); //CANTIDAD OBJETIVO
        // CANTIDAD RESTANTE -> NULL porque no se almacena un ProductoInventario

        return values;
    }

    @Override
    protected Producto cargarProducto(int id, Cursor cursor) throws ShoppingListException {
        String nombre = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_NAME));
        int cantidadObj = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT));

        return new Producto(id,nombre,cantidadObj);
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ProductoTable.COLUMN_CURRENT_AMOUNT + " IS NULL";
    }
}
