package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bd.ShoppingListContract;
import model.Producto;
import model.ShoppingListException;


public class ProductoDao extends AbstractDao<Producto> {

    //---- Constructor ----
    public ProductoDao(final SQLiteDatabase conexion) {
        super(conexion);
    }

    //---- Metodos ----
    @Override
    public Producto findById(int id) {
        Producto producto = null;

        // Indicamos las columnas que queremos obtener
        String[] columns = {
                ShoppingListContract.ProductoTable.COLUMN_NAME,
                ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT
        };
        String where = ShoppingListContract.ProductoTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(id) };

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getConexionBD().query(ShoppingListContract.ProductoTable.TABLE_NAME,
                columns, where, whereArgs, null, null, null);
        try {
            while (cursor.moveToNext()) {
                String nombre = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_NAME));
                int cantidad = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT));

                try {
                    producto = new Producto((int) id, nombre, cantidad);
                } catch (ShoppingListException ex) {}
            }
        } finally {
            cursor.close();
        }

        return producto;
    }

    @Override
    public List<Producto> findAll() {
        List<Producto> lista = new ArrayList<Producto>();

        // Indicamos las columnas que queremos obtener
        String[] columns = {
                ShoppingListContract.ProductoTable._ID,
                ShoppingListContract.ProductoTable.COLUMN_NAME,
                ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT
        };

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getConexionBD().query(ShoppingListContract.ProductoTable.TABLE_NAME,
                columns, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductoTable._ID));
                String nombre = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_NAME));
                int cantidad = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT));

                try {
                    lista.add(new Producto(id, nombre, cantidad));
                } catch (ShoppingListException ex) {}
            }
        } finally {
            cursor.close();
        }

        return lista;
    }

    @Override
    public int insert(Producto elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ProductoTable.COLUMN_NAME, elem.getNombre()); //NOMBRE
        values.put(ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT, elem.getCantidadObjetivo()); //CANTIDAD OBJETIVO
        //CANTIDAD RESTANTE -> NULL porque no se almacena un ProductoInventario

        return (int) getConexionBD().insert(ShoppingListContract.ProductoTable.TABLE_NAME,null,values);
    }

    @Override
    public long update(Producto elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ProductoTable.COLUMN_NAME, elem.getNombre()); //NOMBRE
        values.put(ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT, elem.getCantidadObjetivo()); //CANTIDAD OBJETIVO
        //CANTIDAD RESTANTE -> NULL porque no se almacena un ProductoInventario

        String where = ShoppingListContract.ProductoTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };
        return getConexionBD().update(ShoppingListContract.ProductoTable.TABLE_NAME, values, where, whereArgs);
    }

    // TODO en Abstract
    @Override
    public long remove(Producto elem) {
        String where = ShoppingListContract.ProductoTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };
        return getConexionBD().delete(ShoppingListContract.ProductoTable.TABLE_NAME, where, whereArgs);
    }

}
