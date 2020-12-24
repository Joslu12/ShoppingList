package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import bd.ShoppingListContract;
import model.Stock;

public class StockDao extends ListTableDao<Stock> {

    //---- Constructor ----
    public StockDao(SQLiteDatabase connection) {
        super(connection);
    }

    //---- Methods ----
    @Override
    protected Stock loadList(int listID, List productsList, Cursor cursor) {
        Stock stock = null;

        String name = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ListTable.COLUMN_NAME));
        int associatedListID = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK));

        stock = new Stock(listID, name, productsList, associatedListID);

        return stock;
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ListTable.COLUMN_IS_STOCK + " = 1";
    }

    @Override
    protected ContentValues generateContentValues(Stock elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ListTable.COLUMN_NAME, elem.getName());
        values.put(ShoppingListContract.ListTable.COLUMN_IS_SHOPPING_LIST, false);
        values.put(ShoppingListContract.ListTable.COLUMN_IS_STOCK_SHOPPING_LIST, false);
        values.put(ShoppingListContract.ListTable.COLUMN_IS_STOCK, true);
        if(elem.getAssociatedListId() != -1) {
            values.put(ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK,elem.getAssociatedListId());
        } else {
            values.put(ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK,-1);
        }

        return values;
    }

    @Override
    protected ProductTableDao getProductDao() {
        return new StockProductDao(getBDConnection());
    }

}
