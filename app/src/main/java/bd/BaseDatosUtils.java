package bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.shoppinglist.SingletonMap;

import java.util.Random;

import bd.dao.ShoppingListDao;
import bd.dao.StockDao;
import bd.dao.StockProductDao;
import bd.dao.StockShoppingListDao;
import model.Product;
import model.ShoppingList;
import model.ShoppingListException;
import model.Stock;
import model.StockProduct;
import model.StockShoppingList;

public class BaseDatosUtils {

    //---- Constants and Definitions ----
    private static final String DATA_BASE_NAME = "dataBase.db";
    private static final String DATA_BASE_SINGLETON_KEY = "BASE_DATOS";

    private static boolean hasDataBeenGenerated = false;

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
        SQLiteDatabase bd = getWritableDatabaseConnection(context);

        bd.execSQL(ShoppingListDbHelper.SQL_DELETE_PRODUCT_TABLE);
        bd.execSQL(ShoppingListDbHelper.SQL_DELETE_LIST_TABLE);
        bd.execSQL(ShoppingListDbHelper.SQL_DELETE_LIST_PRODUCT_TABLE);

        bd.execSQL(ShoppingListDbHelper.SQL_CREATE_PRODUCT_TABLE);
        bd.execSQL(ShoppingListDbHelper.SQL_CREATE_LIST_TABLE);
        bd.execSQL(ShoppingListDbHelper.SQL_CREATE_LIST_PRODUCT_TABLE);
    }

    public static void generateDefaultDataForAppTest(Context context) {
        if(!hasDataBeenGenerated) {
            SQLiteDatabase bd = getWritableDatabaseConnection(context);

            try {
                generateDefaultShoppingLists(bd);
                generateDefaultStocks(bd);
            } catch (ShoppingListException ex) {
                // Nunca deberia de ocurrir
            }

            hasDataBeenGenerated = true;
        }
    }

    private static void generateDefaultShoppingLists(SQLiteDatabase bd) throws ShoppingListException {
        ShoppingListDao shoppingListDao = new ShoppingListDao(bd);

        Random rnd = new Random();
        int maxShoppingList = Math.max(5,rnd.nextInt(15));
        for(int i = 0; i<maxShoppingList; ++i) {
            ShoppingList shoppingList = new ShoppingList("Shopping List " + i);
            int maxProducts = rnd.nextInt(25);
            for(int j = 0; j<maxProducts; ++j) {
                Product product = new Product("Product " + j, Math.max(1,rnd.nextInt(25)));
                shoppingList.addProduct(product);
            }
            shoppingListDao.insert(shoppingList);
        }
    }

    private static void generateDefaultStocks(SQLiteDatabase bd) throws ShoppingListException {
        StockDao stockDao = new StockDao(bd);
        StockShoppingListDao stockShoppingListDao = new StockShoppingListDao(bd);
        StockProductDao stockProductDao = new StockProductDao(bd);

        Random rnd = new Random();
        int maxStocks = Math.max(5,rnd.nextInt(15));
        for(int i = 0; i<maxStocks; ++i) {
            Stock stock = new Stock("Stock " + i);
            int maxProducts = rnd.nextInt(25);
            for(int j = 0; j<maxProducts; ++j) {
                int targetAmount = Math.max(1,rnd.nextInt(50));
                StockProduct product = new StockProduct("Product " + j,targetAmount,Math.min(targetAmount,rnd.nextInt(50)));
                stock.addProduct(product);
            }

            stockDao.insert(stock);

            if(rnd.nextBoolean()) {
                StockShoppingList stockShoppingList = stock.generateShoppingList("Stock Shopping List: " + stock.getName());
                stockShoppingListDao.insert(stockShoppingList);
            }
        }
    }
}
