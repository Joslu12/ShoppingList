package bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShoppingListDbHelper extends SQLiteOpenHelper {

    //---- Constants and Definitions ----
    public static final int DATA_BASE_VERSION = 1;

    private static final String SQL_CREATE_PRODUCT_TABLE =
            "CREATE TABLE " + ShoppingListContract.ProductTable.TABLE_NAME + " (" +
            ShoppingListContract.ProductTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.ProductTable.COLUMN_NAME + " TEXT NOT NULL," +
            ShoppingListContract.ProductTable.COLUMN_TARGET_AMOUNT + " INTEGER NOT NULL DEFAULT 1," +
            ShoppingListContract.ProductTable.COLUMN_CURRENT_AMOUNT + " INTEGER" +
            ");";

    private static final String SQL_CREATE_LIST_TABLE =
            "CREATE TABLE " + ShoppingListContract.ListTable.TABLE_NAME + " (" +
            ShoppingListContract.ListTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.ListTable.COLUMN_IS_SHOPPING_LIST + " INTEGER NOT NULL," +
            ShoppingListContract.ListTable.COLUMN_IS_STOCK_SHOPPING_LIST + " INTEGER NOT NULL," +
            ShoppingListContract.ListTable.COLUMN_IS_STOCK + " INTEGER NOT NULL," +
            ShoppingListContract.ListTable.COLUMN_NAME + " TEXT NOT NULL," +
            ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK + ") REFERENCES " +
                    ShoppingListContract.ListTable.TABLE_NAME + " (" + ShoppingListContract.ListTable._ID +")" +
            ");";

    private static final String SQL_CREATE_LIST_PRODUCT_TABLE =
            "CREATE TABLE " + ShoppingListContract.ListProductTable.TABLE_NAME + " (" +
            ShoppingListContract.ListProductTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ShoppingListContract.ListProductTable.COLUMN_LIST_ID + " INTEGER NOT NULL," +
            ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + ShoppingListContract.ListProductTable.COLUMN_LIST_ID + ") REFERENCES " +
                    ShoppingListContract.ListTable.TABLE_NAME + " (" + ShoppingListContract.ListTable._ID +")," +
            "FOREIGN KEY (" + ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID + ") REFERENCES " +
                    ShoppingListContract.ProductTable.TABLE_NAME + " (" + ShoppingListContract.ProductTable._ID +")" +
            ");";

    private static final String SQL_DELETE_PRODUCT_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ProductTable.TABLE_NAME + ";";
    private static final String SQL_DELETE_LIST_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ListTable.TABLE_NAME + ";";
    private static final String SQL_DELETE_LIST_PRODUCT_TABLE =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ListProductTable.TABLE_NAME + ";";

    //---- Constructor ----
    public ShoppingListDbHelper(Context context, String dataBaseName) {
        super(context, dataBaseName, null, DATA_BASE_VERSION);
    }

    //---- Methods ----
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCT_TABLE);
        db.execSQL(SQL_CREATE_LIST_TABLE);
        db.execSQL(SQL_CREATE_LIST_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PRODUCT_TABLE);
        db.execSQL(SQL_DELETE_LIST_TABLE);
        db.execSQL(SQL_DELETE_LIST_PRODUCT_TABLE);
        onCreate(db);
    }

}
