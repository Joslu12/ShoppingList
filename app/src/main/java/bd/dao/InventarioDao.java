package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import bd.ShoppingListContract;
import model.Inventario;
import model.ListaCompra;
import model.Producto;
import model.ProductoInventario;

public class InventarioDao extends ListadoTableDao<Inventario> {

    //---- Constructor ----
    public InventarioDao(SQLiteDatabase conexion) {
        super(conexion);
    }

    //---- Metodos ----
    @Override
    protected Inventario cargarListado(int idLista, List listProductos, Cursor cursor) {
        Inventario inventario = null;

        String nombre = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ListadoTable.COLUMN_NAME));
        int idListaAsociada = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO));

        inventario = new Inventario(idLista, nombre, listProductos, idListaAsociada);

        return inventario;
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ListadoTable.COLUMN_ES_INVENTARIO + " = 1";
    }

    @Override
    protected ContentValues generateContentValues(Inventario elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ListadoTable.COLUMN_NAME, elem.getNombre());
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA, false);
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA_INVENTARIO, false);
        values.put(ShoppingListContract.ListadoTable.COLUMN_ES_INVENTARIO, true);
        if(elem.getIdListaAsociada() != -1) {
            values.put(ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO,elem.getIdListaAsociada());
        } else {
            values.put(ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO,-1);
        }

        return values;
    }

    @Override
    protected ProductoTableDao getProductoDao() {
        return new ProductoInventarioDao(getConexionBD());
    }

}
