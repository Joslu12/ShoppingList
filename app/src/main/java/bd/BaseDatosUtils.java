package bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.shoppinglist.SingletonMap;

public class BaseDatosUtils {

    //---- Constantes y Definiciones ----
    private static final String DATA_BASE_NAME = "dataBase.db";
    private static final String DATA_BASE_SINGLETON_KEY = "BASE_DATOS";

    //---- Metodos Estaticos ----
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
}
