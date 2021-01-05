package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import bd.ShoppingListContract;
import model.ProductsListClass;
import model.Product;

public abstract class ListTableDao<T extends ProductsListClass> extends AbstractDao<T> {

    //---- Constants and Definitions ----
    private final String[] listTableColumns = {
            ShoppingListContract.ListTable._ID,
            ShoppingListContract.ListTable.COLUMN_NAME,
            ShoppingListContract.ListTable.COLUMN_IS_SHOPPING_LIST,
            ShoppingListContract.ListTable.COLUMN_IS_STOCK_SHOPPING_LIST,
            ShoppingListContract.ListTable.COLUMN_IS_STOCK,
            ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK
    };

    //---- Constructor ----
    public ListTableDao(SQLiteDatabase connection) {
        super(connection);
    }

    //---- Methods ----
    protected abstract T loadList(int listID, List productsList, Cursor cursor);

    protected abstract String getWhere();

    @Override
    public T findById(int id) {
        T list = null;

        // Cargamos la lista de productos asociados a ese id
        ProductTableDao productDao = getProductDao();
        List productsList = productDao.findAllByListId(id);

        // Cargamos los datos del listado
        String[] columns = listTableColumns;
        String where = ShoppingListContract.ListTable._ID + " = ?" +
                "AND " + getWhere();
        String[] whereArgs = { Integer.toString(id)};
        Cursor cursor = getBDConnection().query(ShoppingListContract.ListTable.TABLE_NAME,
                columns, where, whereArgs, null, null, null);
        try {
            while (cursor.moveToNext()) {
                list = loadList(id, productsList, cursor);
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<T>();

        // Indicamos las columnas que queremos obtener
        String[] columns = listTableColumns;
        String where = getWhere();

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getBDConnection().query(ShoppingListContract.ListTable.TABLE_NAME,
                columns, where, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ListTable._ID));

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

    /**
     * Devuelve el dao correspondiente al tipo de listado
     * @return
     */
    protected abstract ProductTableDao getProductDao();

    @Override
    protected int doInsert(T elem) {
        // Insertamos el listado en la tabla ListadoTable
        ContentValues values = generateContentValues(elem);
        int listID = (int) getBDConnection().insert(ShoppingListContract.ListTable.TABLE_NAME,null,values);
        elem.setID(listID);

        // Insertamos todos sus productos
        ProductTableDao dao = getProductDao();
        Iterator<Product> it = elem.getProducts();
        while (it.hasNext()) {
            int productID = dao.doInsert(it.next());

            // Creamos la relacion entre el listado y el producto
            values = new ContentValues();
            values.put(ShoppingListContract.ListProductTable.COLUMN_LIST_ID, listID);
            values.put(ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID, productID);
            getBDConnection().insert(ShoppingListContract.ListProductTable.TABLE_NAME, null, values);
        }

        return listID;
    }

    @Override
    protected long doUpdate(T elem) {
        // Actualizamos el listado en la tabla ListadoTable
        ContentValues values = generateContentValues(elem);
        String where = ShoppingListContract.ListTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };

        int rowsUpdt = getBDConnection().update(ShoppingListContract.ListTable.TABLE_NAME, values, where, whereArgs);

        // Actualizamos todos sus productos
        ProductTableDao dao = getProductDao();
        Iterator<Product> it = elem.getProducts();
        while (it.hasNext()) {
            dao.doUpdate(it.next());
        }

        return rowsUpdt;
    }

    @Override
    protected long doRemove(T elem) {

        // Eliminamos todos los productos del listado
        Iterator<Product> it = elem.getProducts();
        ProductDao productDao = new ProductDao(getBDConnection()); //Podemos usar un ProductoDao porque se van a eliminar y no importa la currentAmount
        while(it.hasNext()) {
            productDao.doRemove(it.next());
        }

        // Eliminamos todas las referencias al listado en ListadoProductosTable
        String where = ShoppingListContract.ListProductTable.COLUMN_LIST_ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };
        getBDConnection().delete(ShoppingListContract.ListProductTable.TABLE_NAME, where, whereArgs);

        // Eliminamos el listado de la tabla Listado
        where = ShoppingListContract.ListTable._ID + " = ?";

        return getBDConnection().delete(ShoppingListContract.ListTable.TABLE_NAME, where, whereArgs);
    }

    public <E extends Product> int doAddProduct(int productListID, E product) {
        // Insertamos el producto
        ProductTableDao dao = getProductDao();
        int productID = dao.insert(product);

        // Creamos la relacion entre el listado y el producto
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ListProductTable.COLUMN_LIST_ID, productListID);
        values.put(ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID, productID);
        return (int) getBDConnection().insert(ShoppingListContract.ListProductTable.TABLE_NAME, null, values);
    }

    public <E extends Product> int doRemoveProduct(int productListID, E product) {
        // Eliminamos el producto
        ProductTableDao dao = getProductDao();
        dao.remove(product);

        // Eliminamos la relacion entre el listado y el producto
        String where = ShoppingListContract.ListProductTable.COLUMN_LIST_ID + " = ?" +
                "AND " + ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID + " = ?";
        String[] whereArgs = { Integer.toString(productListID), Integer.toString(product.getID()) };
        return getBDConnection().delete(ShoppingListContract.ListProductTable.TABLE_NAME, where, whereArgs);
    }
}
