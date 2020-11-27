package bd;

import android.provider.BaseColumns;

public final class ShoppingListContract {

    private ShoppingListContract() {}

    //---- TABLA PRODUCTO ----
    public static abstract class ProductoTable implements BaseColumns {
        public static final String TABLE_NAME = "Producto";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_TARGET_AMOUNT = "cantidadObjetivo";
        public static final String COLUMN_CURRENT_AMOUNT = "cantidadActual";
    }

    //---- TABLA LISTA COMPRA ----
    public static abstract class ListaCompraTable implements BaseColumns {
        public static final String TABLE_NAME = "ListaCompra";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_INVENTARIO_ASOCIADO = "inventarioAsociado";
    }

    //---- TABLA LISTA COMPRA CON PRODUCTOS -----
    public static abstract class ListaCompraProductosTable implements BaseColumns {
        public static final String TABLE_NAME = "ListaCompraProductos";
        public static final String COLUMN_LISTADO_ID = "listaCompra";
        public static final String COLUMN_PRODUCTO_ID = "producto";
    }

    //---- TABLA INVENTARIO ----
    public static abstract class InventarioTable implements BaseColumns {
        public static final String TABLE_NAME = "Inventario";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_LISTA_ASOCIADA = "listaAsociada";
    }

    //---- TABLA INVENTARIO CON PRODUCTOS -----
    public static abstract class InventarioProductosTable implements BaseColumns {
        public static final String TABLE_NAME = "InventarioProductos";
        public static final String COLUMN_INVENTARIO_ID = "inventario";
        public static final String COLUMN_PRODUCTO_ID = "producto";
    }
}
