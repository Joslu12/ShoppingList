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


public abstract class ProductoTableDao<T extends Producto> extends AbstractDao<T> {

    //---- Constantes ----
    private final String[] columnasProductoTable = {
            ShoppingListContract.ProductoTable._ID,
            ShoppingListContract.ProductoTable.COLUMN_NAME,
            ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT,
            ShoppingListContract.ProductoTable.COLUMN_CURRENT_AMOUNT
    };

    //---- Constructor ----
    protected ProductoTableDao(final SQLiteDatabase conexion) {
        super(conexion);
    }

    //---- Metodos ----

    /**
     * A partir de la posicion de un cursor dada, generara un Producto obteniendo los valores
     * de las columnas que mas le interesen en funcion del tipo de Producto que defina la clase
     * @param id del producto a generar
     * @param cursor Objeto de la clase Cursor que se encuentra en la posicion deseada
     * @return un producto generado
     * @throws ShoppingListException
     */
    protected abstract T cargarProducto(int id, Cursor cursor) throws ShoppingListException;

    public List<T> findAllByListId(int listadoId) {
        List<T> lista = new ArrayList<T>();

        // Indicamos las columnas que queremos obtener
        String[] columns = {ShoppingListContract.ListadoProductoTable.COLUMN_PRODUCTO_ID};
        String where = ShoppingListContract.ListadoProductoTable.COLUMN_LISTADO_ID + " = ? ";
        String[] whereArgs = {Integer.toString(listadoId)};

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getConexionBD().query(ShoppingListContract.ListadoProductoTable.TABLE_NAME,
                columns, where, whereArgs, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ListadoProductoTable.COLUMN_PRODUCTO_ID));

                lista.add(findById(id));
            }
        } finally {
            cursor.close();
        }

        return lista;
    }

    @Override
    public T findById(int id) {
        T producto = null;

        // Indicamos las columnas que queremos obtener
        String[] columns = columnasProductoTable;
        String where = ShoppingListContract.ProductoTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(id) };

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getConexionBD().query(ShoppingListContract.ProductoTable.TABLE_NAME,
                columns, where, whereArgs, null, null, null);
        try {
            while (cursor.moveToNext()) {
                producto = cargarProducto(id,cursor);
            }
        } catch (ShoppingListException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return producto;
    }

    protected abstract String getWhere();

    @Override
    public List<T> findAll() {
        List<T> lista = new ArrayList<T>();

        // Indicamos las columnas que queremos obtener
        String[] columns = columnasProductoTable;
        String where = getWhere();

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getConexionBD().query(ShoppingListContract.ProductoTable.TABLE_NAME,
                columns, where, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductoTable._ID));

                lista.add(findById(id));
            }
        } finally {
            cursor.close();
        }

        return lista;
    }

    /**
     * Devolvera un ContentValues generado en funcion del tipo de producto de la clase T
     * @param elem
     * @return un objeto ContentValues con los atributos del elem
     */
    protected abstract ContentValues generateContentValues(T elem);

    @Override
    public int insert(T elem) {
        ContentValues values = generateContentValues(elem);

        int idElem = (int) getConexionBD().insert(ShoppingListContract.ProductoTable.TABLE_NAME,null,values);
        elem.setID(idElem);

        return idElem;
    }

    @Override
    public long update(T elem) {
        ContentValues values = generateContentValues(elem);
        String where = ShoppingListContract.ProductoTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };

        return getConexionBD().update(ShoppingListContract.ProductoTable.TABLE_NAME, values, where, whereArgs);
    }

    @Override
    public long remove(T elem) {
        String where = ShoppingListContract.ProductoTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };

        return getConexionBD().delete(ShoppingListContract.ProductoTable.TABLE_NAME, where, whereArgs);
    }

}
