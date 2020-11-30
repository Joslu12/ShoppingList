package bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShoppingListDbHelper extends SQLiteOpenHelper {

    //---- Constantes y Definiciones ----
    public static final int DATA_BASE_VERSION = 1;

    private static final String SQL_CREATE_PRODUCTO_TABLE =
            "CREATE TABLE " + ShoppingListContract.ProductoTable.TABLE_NAME + " (" +
            ShoppingListContract.ProductoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.ProductoTable.COLUMN_NAME + " TEXT NOT NULL," +
            ShoppingListContract.ProductoTable.COLUMN_TARGET_AMOUNT + " INTEGER NOT NULL DEFAULT 1," +
            ShoppingListContract.ProductoTable.COLUMN_CURRENT_AMOUNT + " INTEGER" +
            ");";

    private static final String SQL_CREATE_LISTADO_TABLE =
            "CREATE TABLE " + ShoppingListContract.ListadoTable.TABLE_NAME + " (" +
            ShoppingListContract.ListadoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA + " INTEGER NOT NULL," +
            ShoppingListContract.ListadoTable.COLUMN_ES_LISTA_COMPRA_INVENTARIO + " INTEGER NOT NULL," +
            ShoppingListContract.ListadoTable.COLUMN_ES_INVENTARIO + " INTEGER NOT NULL," +
            ShoppingListContract.ListadoTable.COLUMN_NAME + " TEXT NOT NULL," +
            ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + ShoppingListContract.ListadoTable.COLUMN_LISTADO_ASOCIADO + ") REFERENCES " +
                    ShoppingListContract.ListadoTable.TABLE_NAME + " (" + ShoppingListContract.ListadoTable._ID +")" +
            ");";

    private static final String SQL_CREATE_LISTADO_PRODUCTO_TABLE =
            "CREATE TABLE " + ShoppingListContract.ListadoProductoTable.TABLE_NAME + " (" +
            ShoppingListContract.ListadoProductoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.ListadoProductoTable.COLUMN_LISTADO_ID + " INTEGER NOT NULL," +
            ShoppingListContract.ListadoProductoTable.COLUMN_PRODUCTO_ID + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + ShoppingListContract.ListadoProductoTable.COLUMN_LISTADO_ID + ") REFERENCES " +
                    ShoppingListContract.ListadoTable.TABLE_NAME + " (" + ShoppingListContract.ListadoTable._ID +")," +
            "FOREIGN KEY (" + ShoppingListContract.ListadoProductoTable.COLUMN_PRODUCTO_ID + ") REFERENCES " +
                    ShoppingListContract.ProductoTable.TABLE_NAME + " (" + ShoppingListContract.ProductoTable._ID +")" +
            ");";

    /*
    private static final String SQL_CREATE_LISTA_COMPRA_TABLE =
            "CREATE TABLE " + ShoppingListContract.ListaCompraTable.TABLE_NAME + " (" +
            ShoppingListContract.ListaCompraTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.ListaCompraTable.COLUMN_NAME + " TEXT UNIQUE NOT NULL," +
            ShoppingListContract.ListaCompraTable.COLUMN_INVENTARIO_ASOCIADO + " INTEGER," +
            "FOREIGN KEY (" + ShoppingListContract.ListaCompraTable.COLUMN_INVENTARIO_ASOCIADO + ") REFERENCES " +
                    ShoppingListContract.InventarioTable.TABLE_NAME + " (" + ShoppingListContract.InventarioTable._ID +")" +
            ");";

    private static final String SQL_CREATE_LISTA_COMPRA_PRODUCTOS_TABLE =
            "CREATE TABLE " + ShoppingListContract.ListaCompraProductosTable.TABLE_NAME + " (" +
            ShoppingListContract.ListaCompraProductosTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.ListaCompraProductosTable.COLUMN_LISTADO_ID + " INTEGER NOT NULL," +
            ShoppingListContract.ListaCompraProductosTable.COLUMN_PRODUCTO_ID + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + ShoppingListContract.ListaCompraProductosTable.COLUMN_LISTADO_ID + ") REFERENCES " +
                    ShoppingListContract.ListaCompraTable.TABLE_NAME + " (" + ShoppingListContract.ListaCompraTable._ID +")," +
            "FOREIGN KEY (" + ShoppingListContract.ListaCompraProductosTable.COLUMN_PRODUCTO_ID + ") REFERENCES " +
                    ShoppingListContract.ProductoTable.TABLE_NAME + " (" + ShoppingListContract.ProductoTable._ID +")" +
            ");";

    private static final String SQL_CREATE_INVENTARIO_TABLE =
            "CREATE TABLE " + ShoppingListContract.InventarioTable.TABLE_NAME + " (" +
            ShoppingListContract.InventarioTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.InventarioTable.COLUMN_NAME + " TEXT UNIQUE NOT NULL," +
            ShoppingListContract.InventarioTable.COLUMN_LISTA_ASOCIADA + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + ShoppingListContract.InventarioTable.COLUMN_LISTA_ASOCIADA + ") REFERENCES " +
                    ShoppingListContract.ListaCompraTable.TABLE_NAME + " (" + ShoppingListContract.ListaCompraTable._ID +")" +
            ");";

    private static final String SQL_CREATE_INVENTARIO_PRODUCTOS_TABLE =
            "CREATE TABLE " + ShoppingListContract.InventarioProductosTable.TABLE_NAME + " (" +
            ShoppingListContract.InventarioProductosTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.InventarioProductosTable.COLUMN_INVENTARIO_ID + " INTEGER NOT NULL," +
            ShoppingListContract.InventarioProductosTable.COLUMN_PRODUCTO_ID + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + ShoppingListContract.InventarioProductosTable.COLUMN_INVENTARIO_ID + ") REFERENCES " +
            ShoppingListContract.InventarioTable.TABLE_NAME + " (" + ShoppingListContract.InventarioTable._ID +")," +
            "FOREIGN KEY (" + ShoppingListContract.InventarioProductosTable.COLUMN_PRODUCTO_ID + ") REFERENCES " +
                    ShoppingListContract.ProductoTable.TABLE_NAME + " (" + ShoppingListContract.ProductoTable._ID +")" +
            ");";
     */


    private static final String SQL_DELETE_PRODUCTO_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ProductoTable.TABLE_NAME + ";";
    private static final String SQL_DELETE_LISTADO_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ListadoTable.TABLE_NAME + ";";
    private static final String SQL_DELETE_LISTADO_PRODUCTO_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ListadoProductoTable.TABLE_NAME + ";";

    /*
    private static final String SQL_DELETE_LISTA_COMPRA_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ListaCompraTable.TABLE_NAME + ";";
    private static final String SQL_DELETE_LISTA_COMPRA_PRODUCTOS_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ListaCompraProductosTable.TABLE_NAME + ";";
    private static final String SQL_DELETE_INVENTARIO_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.InventarioTable.TABLE_NAME + ";";
    private static final String SQL_DELETE_INVENTARIO_PRODUCTOS_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.InventarioProductosTable.TABLE_NAME + ";";
     */


    //---- Constructor ----
    public ShoppingListDbHelper(Context context, String dataBaseName) {
        super(context, dataBaseName, null, DATA_BASE_VERSION);
    }

    //---- Metodos ----
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTO_TABLE);
        db.execSQL(SQL_CREATE_LISTADO_TABLE);
        db.execSQL(SQL_CREATE_LISTADO_PRODUCTO_TABLE);
        /*db.execSQL(SQL_CREATE_LISTA_COMPRA_TABLE);
        db.execSQL(SQL_CREATE_LISTA_COMPRA_PRODUCTOS_TABLE);
        db.execSQL(SQL_CREATE_INVENTARIO_TABLE);
        db.execSQL(SQL_CREATE_INVENTARIO_PRODUCTOS_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PRODUCTO_TABLE);
        db.execSQL(SQL_DELETE_LISTADO_TABLE);
        db.execSQL(SQL_DELETE_LISTADO_PRODUCTO_TABLE);
        /*db.execSQL(SQL_DELETE_LISTA_COMPRA_TABLE);
        db.execSQL(SQL_DELETE_LISTA_COMPRA_PRODUCTOS_TABLE);
        db.execSQL(SQL_DELETE_INVENTARIO_TABLE);
        db.execSQL(SQL_DELETE_INVENTARIO_PRODUCTOS_TABLE);*/
        onCreate(db);
    }

}
