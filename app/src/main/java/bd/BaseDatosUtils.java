package bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.shoppinglist.SingletonMap;

public class BaseDatosUtils {

    //---- Constants and Definitions ----
    private static final String DATA_BASE_NAME = "dataBase.db";
    private static final String DATA_BASE_SINGLETON_KEY = "BASE_DATOS";

    //---- Static Methods ----
    public static SQLiteDatabase getWritableDatabaseConnection(Context context) {
        SQLiteDatabase writableBdConnection = (SQLiteDatabase) SingletonMap.getInstance().get(DATA_BASE_SINGLETON_KEY);
        if(writableBdConnection == null) {
            ShoppingListDbHelper dbHelper = new ShoppingListDbHelper(context,DATA_BASE_NAME);
            writableBdConnection = dbHelper.getWritableDatabase();
            SingletonMap.getInstance().put(DATA_BASE_SINGLETON_KEY,writableBdConnection);
        }
        return writableBdConnection;
    }

    public static SQLiteDatabase getReadableDatabaseConnection(Context context) {
        SQLiteDatabase readableBdConnection = (SQLiteDatabase) SingletonMap.getInstance().get(DATA_BASE_SINGLETON_KEY);
        if(readableBdConnection == null) {
            ShoppingListDbHelper dbHelper = new ShoppingListDbHelper(context,DATA_BASE_NAME);
            readableBdConnection = dbHelper.getReadableDatabase();
            SingletonMap.getInstance().put(DATA_BASE_SINGLETON_KEY,readableBdConnection);
        }
        return readableBdConnection;
    }

    public static void deleteAllDataStored(Context context) {
        SQLiteDatabase db = getWritableDatabaseConnection(context);

        db.execSQL(ShoppingListDbHelper.SQL_DELETE_PRODUCT_TABLE);
        db.execSQL(ShoppingListDbHelper.SQL_DELETE_LIST_TABLE);
        db.execSQL(ShoppingListDbHelper.SQL_DELETE_LIST_PRODUCT_TABLE);

        db.execSQL(ShoppingListDbHelper.SQL_CREATE_PRODUCT_TABLE);
        db.execSQL(ShoppingListDbHelper.SQL_CREATE_LIST_TABLE);
        db.execSQL(ShoppingListDbHelper.SQL_CREATE_LIST_PRODUCT_TABLE);
    }
}
