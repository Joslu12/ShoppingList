package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bd.ShoppingListContract;
import model.StockProduct;
import model.ShoppingListException;

public class StockProductDao extends ProductTableDao<StockProduct> {

    //----- Constructor ----
    public StockProductDao(SQLiteDatabase connection) {
        super(connection);
    }

    //---- Methods ----
    @Override
    protected StockProduct loadProduct(int id, Cursor cursor) throws ShoppingListException {
        String name = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ProductTable.COLUMN_NAME));
        int targetAmount = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductTable.COLUMN_TARGET_AMOUNT));
        int currentAmount = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductTable.COLUMN_CURRENT_AMOUNT));

        return new StockProduct(id,name,targetAmount,currentAmount);
    }

    @Override
    public ContentValues generateContentValues(StockProduct elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ProductTable.COLUMN_NAME, elem.getName()); //NOMBRE
        values.put(ShoppingListContract.ProductTable.COLUMN_TARGET_AMOUNT, elem.getTargetAmount()); //CANTIDAD OBJETIVO
        values.put(ShoppingListContract.ProductTable.COLUMN_CURRENT_AMOUNT, elem.getCurrentAmount()); //CANTIDAD ACTUAL

        return values;
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ProductTable.COLUMN_CURRENT_AMOUNT + " IS NOT NULL";
    }
}
