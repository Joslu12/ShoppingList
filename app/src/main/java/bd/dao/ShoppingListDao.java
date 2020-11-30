package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import bd.ShoppingListContract;
import model.ShoppingList;

public class ShoppingListDao extends ListTableDao<ShoppingList> {

    //---- Constructor ----
    public ShoppingListDao(SQLiteDatabase connection) {
        super(connection);
    }

    //---- Metodos ----
    @Override
    protected ShoppingList loadList(int listID, List productsList, Cursor cursor) {
        ShoppingList shoppingList = null;

        String name = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ListTable.COLUMN_NAME));

        shoppingList = new ShoppingList(listID, name, productsList);

        return shoppingList;
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ListTable.COLUMN_IS_SHOPPING_LIST + " = 1";
    }

    @Override
    protected ContentValues generateContentValues(ShoppingList elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ListTable.COLUMN_NAME, elem.getName());
        values.put(ShoppingListContract.ListTable.COLUMN_IS_SHOPPING_LIST, true);
        values.put(ShoppingListContract.ListTable.COLUMN_IS_STOCK_SHOPPING_LIST, false);
        values.put(ShoppingListContract.ListTable.COLUMN_IS_STOCK, false);
        values.put(ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK, -1); // Nunca tendra un listado asociado

        return values;
    }

    @Override
    protected ProductTableDao getProductDao() {
        return new ProductDao(getBDConnection());
    }

}
