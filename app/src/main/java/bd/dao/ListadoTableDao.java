package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bd.ShoppingListContract;
import model.ListadoProductosClass;
import model.Producto;
import model.ShoppingListException;

public abstract class ListadoTableDao<T extends ListadoProductosClass> extends AbstractDao<T> {

    //---- Constantes ----
    private final String[] columnasListadoTable = {
            ShoppingListContract.ListadoTable._ID,
            ShoppingListContract.ListadoTable.COLUMN_NAME,
            ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA,
            ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA_INVENTARIO,
            ShoppingListContract.ListadoTable.COLUMN_ES_INVENTARIO,
            ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO
    };

    //---- Constructor ----
    public ListadoTableDao(SQLiteDatabase conexion) {
        super(conexion);
    }

    //---- Metodos ----
    protected abstract T cargarListado(int idLista, List listProductos, Cursor cursor);

    protected abstract String getWhere();

    @Override
    public T findById(int id) {
        T listado = null;

        // Cargamos la lista de productos asociados a ese id
        ProductoTableDao productoDao = getProductoDao();
        List listProductos = productoDao.findAllByListId(id);

        // Cargamos los datos del listado
        String[] columns = columnasListadoTable;
        String where = ShoppingListContract.ListadoTable._ID + " = ?" +
                "AND " + getWhere();
        String[] whereArgs = { Integer.toString(id)};
        Cursor cursor = getConexionBD().query(ShoppingListContract.ListadoTable.TABLE_NAME,
                columns, where, whereArgs, null, null, null);
        try {
            while (cursor.moveToNext()) {
                listado = cargarListado(id, listProductos, cursor);
            }
        } finally {
            cursor.close();
        }

        return listado;
    }

    @Override
    public List<T> findAll() {
        List<T> lista = new ArrayList<T>();

        // Indicamos las columnas que queremos obtener
        String[] columns = columnasListadoTable;
        String where = getWhere();

        // Ejecutamos la consulta y la almacenamos en el cursor
        Cursor cursor = getConexionBD().query(ShoppingListContract.ListadoTable.TABLE_NAME,
                columns, where, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ListadoTable._ID));

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

    /**
     * Devuelve el dao correspondiente al tipo de listado
     * @return
     */
    protected abstract ProductoTableDao getProductoDao();

    @Override
    public int insert(T elem) {
        // Insertamos el listado en la tabla ListadoTable
        ContentValues values = generateContentValues(elem);
        int idLista = (int) getConexionBD().insert(ShoppingListContract.ListadoTable.TABLE_NAME,null,values);
        elem.setID(idLista);

        // Insertamos todos sus productos
        ProductoTableDao dao = getProductoDao();
        Iterator<Producto> it = elem.getProductos();
        while (it.hasNext()) {
            int idProducto = dao.insert(it.next());

            // Creamos la relacion entre el listado y el producto
            values = new ContentValues();
            values.put(ShoppingListContract.ListadoProductoTable.COLUMN_LISTADO_ID, idLista);
            values.put(ShoppingListContract.ListadoProductoTable.COLUMN_PRODUCTO_ID, idProducto);
            getConexionBD().insert(ShoppingListContract.ListadoProductoTable.TABLE_NAME, null, values);
        }

        return idLista;
    }

    @Override
    public long update(T elem) {
        // Actualizamos el listado en la tabla ListadoTable
        ContentValues values = generateContentValues(elem);
        String where = ShoppingListContract.ListadoTable._ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };

        int rowsUpdt = getConexionBD().update(ShoppingListContract.ListadoTable.TABLE_NAME, values, where, whereArgs);

        // Actualizamos todos sus productos
        ProductoTableDao dao = getProductoDao();
        Iterator<Producto> it = elem.getProductos();
        while (it.hasNext()) {
            dao.update(it.next());
        }

        return rowsUpdt;
    }

    @Override
    public long remove(T elem) {

        // Eliminamos todos los productos del listado
        Iterator<Producto> it = elem.getProductos();
        ProductoDao productoDao = new ProductoDao(getConexionBD()); //Podemos usar un ProductoDao porque se van a eliminar y no importa la currentAmount
        while(it.hasNext()) {
            productoDao.remove(it.next());
        }

        // Eliminamos todas las referencias al listado en ListadoProductosTable
        String where = ShoppingListContract.ListadoProductoTable.COLUMN_LISTADO_ID + " = ?";
        String[] whereArgs = { Integer.toString(elem.getID()) };
        getConexionBD().delete(ShoppingListContract.ListadoProductoTable.TABLE_NAME, where, whereArgs);

        // Eliminamos el listado de la tabla Listado
        where = ShoppingListContract.ListadoTable._ID + " = ?";

        return getConexionBD().delete(ShoppingListContract.ListadoTable.TABLE_NAME, where, whereArgs);
    }
}
