package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import bd.ShoppingListContract;
import model.Inventario;
import model.ListaCompraInventario;

public class ListaCompraInventarioDao extends ListadoTableDao<ListaCompraInventario> {

    //---- Constructor ----
    public ListaCompraInventarioDao(SQLiteDatabase conexion) {
        super(conexion);
    }

    //---- Metodos ----
    @Override
    protected ListaCompraInventario cargarListado(int idLista, List listProductos, Cursor cursor) {
        ListaCompraInventario listaCompraInventario = null;

        String nombre = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ListadoTable.COLUMN_NAME));

        // Cargamos el inventario asociado
        int idInventarioAsociado = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO));
        InventarioDao dao = new InventarioDao(getConexionBD());
        Inventario inventario = dao.findById(idInventarioAsociado);

        listaCompraInventario = new ListaCompraInventario(idLista, nombre, listProductos, inventario);

        return listaCompraInventario;
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA_INVENTARIO + " = 1";
    }

    @Override
    protected ContentValues generateContentValues(ListaCompraInventario elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ListadoTable.COLUMN_NAME, elem.getNombre());
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA, false);
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA_INVENTARIO, true);
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_INVENTARIO, false);
        if(elem.getIDInventarioAsociado() != -1) {
            values.put(ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO,elem.getIDInventarioAsociado());
        } else {
            values.put(ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO,-1);
        }

        return values;
    }

    @Override
    protected ProductoTableDao getProductoDao() {
        return new ProductoDao(getConexionBD());
    }

}
