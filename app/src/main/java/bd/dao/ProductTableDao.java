package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bd.ShoppingListContract;
import model.Product;
import model.ShoppingListException;


public abstract class ProductTableDao<T extends Product> extends AbstractDao<T> {

    //---- Constants and Definitions ----
    private final String[] productTableColumns = {
            ShoppingListContract.ProductTable._ID,
            ShoppingListContract.ProductTable.COLUMN_NAME,
            ShoppingListContract.ProductTable.COLUMN_TARGET_AMOUNT,
            ShoppingListContract.ProductTable.COLUMN_CURRENT_AMOUNT
    };

    //---- Constructor ----
    protected ProductTableDao(final SQLiteDatabase connection) {
        super(connection);
    }

    //---- Methods ----

    /**
     * A partir de la posicion de un cursor dada, generara un Producto obteniendo los valores
     * de las columnas que mas le interesen en funcion del tipo de Producto que defina la clase
     * @param id del producto a generar
     * @param cursor Objeto de la clase Cursor que se encuentra en la posicion deseada
     * @return un producto generado
     * @throws ShoppingListException
     */
    protected abstract T loadProduct(int id, Cursor cursor) throws ShoppingListException;

    public List<T> findAllByListId(int listID) {
        List<T> list = new ArrayList<T>();

        // Indicamos las columnas que queremos obtener
        String[] columns = {ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID};
        String where = ShoppingListContract.ListProductTable.COLUMN_LIST_ID + " = ? ";
        String[] whereArgs = {Integer.toString(listID)};

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getBDConnection().query(ShoppingListContract.ListProductTable.TABLE_NAME,
                columns, where, whereArgs, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID));

                list.add(findById(id));
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    @Override
    public T findById(int id) {
        T product = null;

        // Indicamos las columnas que queremos obtener
        String[] columns = productTableColumns;
        String where = ShoppingListContract.ProductTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(id) };

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getBDConnection().query(ShoppingListContract.ProductTable.TABLE_NAME,
                columns, where, whereArgs, null, null, null);
        try {
            while (cursor.moveToNext()) {
                product = loadProduct(id,cursor);
            }
        } catch (ShoppingListException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return product;
    }

    protected abstract String getWhere();

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<T>();

        // Indicamos las columnas que queremos obtener
        String[] columns = productTableColumns;
        String where = getWhere();

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getBDConnection().query(ShoppingListContract.ProductTable.TABLE_NAME,
                columns, where, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductTable._ID));

                list.add(findById(id));
            }
        } finally {
            cursor.close();
        }

        Collections.sort(list);
        return list;
    }

    /**
     * Devolvera un ContentValues generado en funcion del tipo de producto de la clase T
     * @param elem
     * @return un objeto ContentValues con los atributos del elem
     */
    protected abstract ContentValues generateContentValues(T elem);

    @Override
    protected int doInsert(T elem) {
        ContentValues values = generateContentValues(elem);

        int elementID = (int) getBDConnection().insert(ShoppingListContract.ProductTable.TABLE_NAME,null,values);
        elem.setID(elementID);

        return elementID;
    }

    @Override
    protected long doUpdate(T elem) {
        ContentValues values = generateContentValues(elem);
        String where = ShoppingListContract.ProductTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };

        return getBDConnection().update(ShoppingListContract.ProductTable.TABLE_NAME, values, where, whereArgs);
    }

    @Override
    protected long doRemove(T elem) {
        String where = ShoppingListContract.ProductTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };

        return getBDConnection().delete(ShoppingListContract.ProductTable.TABLE_NAME, where, whereArgs);
    }

}
