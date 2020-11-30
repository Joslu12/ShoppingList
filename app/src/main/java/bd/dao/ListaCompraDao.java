package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import bd.ShoppingListContract;
import model.ListaCompra;
import model.Producto;

public class ListaCompraDao extends ListadoTableDao<ListaCompra> {

    //---- Constructor ----
    public ListaCompraDao(SQLiteDatabase conexion) {
        super(conexion);
    }

    //---- Metodos ----
    @Override
    protected ListaCompra cargarListado(int idLista, List listProductos, Cursor cursor) {
        ListaCompra listaCompra = null;

        String nombre = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ListadoTable.COLUMN_NAME));

        listaCompra = new ListaCompra(idLista, nombre, listProductos);

        return listaCompra;
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA + " = 1";
    }

    @Override
    protected ContentValues generateContentValues(ListaCompra elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ListadoTable.COLUMN_NAME, elem.getNombre());
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA, true);
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA_INVENTARIO, false);
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_INVENTARIO, false);
        values.put(ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO, -1); // Nunca tendra un listado asociado

        return values;
    }

    @Override
    protected ProductoTableDao getProductoDao() {
        return new ProductoDao(getConexionBD());
    }

}
