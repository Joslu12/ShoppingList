package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bd.ShoppingListContract;
import model.Product;
import model.ShoppingListException;

public class ProductDao extends ProductTableDao<Product> {

    //---- Constructor ----
    public ProductDao(SQLiteDatabase connection) {
        super(connection);

    }

    //---- Methods ----
    @Override
    public ContentValues generateContentValues(Product elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ProductTable.COLUMN_NAME, elem.getName()); //NOMBRE
        values.put(ShoppingListContract.ProductTable.COLUMN_TARGET_AMOUNT, elem.getTargetAmount()); //CANTIDAD OBJETIVO
        // CANTIDAD RESTANTE -> NULL porque no se almacena un ProductoInventario

        return values;
    }

    @Override
    protected Product loadProduct(int id, Cursor cursor) throws ShoppingListException {
        String name = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ProductTable.COLUMN_NAME));
        int targetAmount = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ProductTable.COLUMN_TARGET_AMOUNT));

        return new Product(id,name,targetAmount);
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ProductTable.COLUMN_CURRENT_AMOUNT + " IS NULL";
    }
}
