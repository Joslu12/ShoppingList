package bd.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import bd.ShoppingListContract;
import model.Stock;
import model.StockShoppingList;

public class StockShoppingListDao extends ListTableDao<StockShoppingList> {

    //---- Constructor ----
    public StockShoppingListDao(SQLiteDatabase connection) {
        super(connection);
    }

    //---- Metodos ----
    @Override
    protected StockShoppingList loadList(int listID, List productsList, Cursor cursor) {
        StockShoppingList StockShoppingList = null;

        String name = cursor.getString(cursor.getColumnIndex(ShoppingListContract.ListTable.COLUMN_NAME));

        // Cargamos el inventario asociado
        int associatedStockID = cursor.getInt(cursor.getColumnIndex(ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK));
        StockDao dao = new StockDao(getBDConnection());
        Stock stock = dao.findById(associatedStockID);

        StockShoppingList = new StockShoppingList(listID, name, productsList, stock);

        return StockShoppingList;
    }

    @Override
    protected String getWhere() {
        return ShoppingListContract.ListTable.COLUMN_IS_STOCK_SHOPPING_LIST + " = 1";
    }

    @Override
    protected ContentValues generateContentValues(StockShoppingList elem) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ListTable.COLUMN_NAME, elem.getName());
        values.put(ShoppingListContract.ListTable.COLUMN_IS_SHOPPING_LIST, false);
        values.put(ShoppingListContract.ListTable.COLUMN_IS_STOCK_SHOPPING_LIST, true);
        values.put(ShoppingListContract.ListTable.COLUMN_IS_STOCK, false);
        if(elem.getAssociatedStockID() != -1) {
            values.put(ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK,elem.getAssociatedStockID());
        } else {
            values.put(ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK,-1);
        }

        return values;
    }

    @Override
    protected ProductTableDao getProductDao() {
        return new ProductDao(getBDConnection());
    }

}
