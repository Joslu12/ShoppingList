package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bd.ShoppingListContract;
import model.Producto;
import model.ProductoInventario;
import model.ShoppingListException;

public class ProductoInventarioDao extends ProductoTableDao<ProductoInventario> {

    //----- Constructor ----
    public ProductoInventarioDao(SQLiteDatabase conexion) {
        super(conexion);
    }

    //---- Metodos ----
    @Override
    protected ProductoInventario cargarProducto(int id, Cursor cursor) throws ShoppingListException {
        String nombre = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_NAME));
        int cantidadObj = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT));
        int cantidadAct = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_CURRENT_AMOUNT));

        return new ProductoInventario(id,nombre,cantidadObj,cantidadAct);
    }

    @Override
    public ContentValues generateContentValues(ProductoInventario elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ProductoTable.COLUMN_NAME, elem.getNombre()); //NOMBRE
        values.put(ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT, elem.getCantidadObjetivo()); //CANTIDAD OBJETIVO
        values.put(ShoppingListContract.ProductoTable.COLUMN_CURRENT_AMOUNT, elem.getCantidadActual()); //CANTIDAD ACTUAL

        return values;
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ProductoTable.COLUMN_CURRENT_AMOUNT + " IS NOT NULL";
    }
}
