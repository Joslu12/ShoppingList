package bd;

import android.provider.BaseColumns;

public final class ShoppingListContract {

    private ShoppingListContract() {}

    //---- PRODUCT TABLE ----
    public static abstract class ProductTable implements BaseColumns {
        public static final String TABLE_NAME = "Producto";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_TARGET_AMOUNT = "cantidadObjetivo";
        public static final String COLUMN_CURRENT_AMOUNT = "cantidadActual";
    }

    //---- LIST TABLE ----
    public static abstract class ListTable implements BaseColumns {
        public static final String TABLE_NAME = "Listado";
        public static final String COLUMN_IS_SHOPPING_LIST = "esListaCompra";
        public static final String COLUMN_IS_STOCK_SHOPPING_LIST = "esListaCompraInventario";
        public static final String COLUMN_IS_STOCK = "esInventario";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_ASSOCIATED_STOCK = "listadoAsociado";
    }

    //---- LIST_PRODUCT TABLE
    public static abstract class ListProductTable implements BaseColumns {
        public static final String TABLE_NAME = "ListadoProducto";
        public static final String COLUMN_LIST_ID = "listado";
        public static final String COLUMN_PRODUCT_ID = "producto";
    }
}
