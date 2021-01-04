package com.example.shoppinglist.product_list_class.stock_shopping_list;

import com.example.shoppinglist.product_list_class.ProductListClassActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteStockShoppingListDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditListOfProductsDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditStockShoppingListDialog;
import com.example.shoppinglist.product_list_class.ListOfProductsFragment;

import bd.dao.ListTableDao;
import bd.dao.StockShoppingListDao;
import model.Product;
import model.StockShoppingList;

public class StockShoppingListActivity extends ProductListClassActivity<StockShoppingList> {

    //---- Methods ----
    @Override
    protected ListOfProductsFragment<Product> getNewInstance(StockShoppingList shoppingList) {
        return StockShoppingListFragment.newInstance(shoppingList);
    }

    @Override
    protected DeleteEntityDialog<StockShoppingList> generateEntityDeleteDialog() {
        return new DeleteStockShoppingListDialog(this, (StockShoppingList) this.productsList);
    }

    @Override
    protected String generateDeleteDialogTag() {
        return "Delete a Stock Shopping List";
    }

    @Override
    protected EditListOfProductsDialog<StockShoppingList> generateEntityEditDialog() {
        return new EditStockShoppingListDialog(this, (StockShoppingList) this.productsList);
    }

    @Override
    protected String generateEditDialogTag() {
        return "Edit a Stock Shopping List";
    }

    @Override
    protected ListTableDao<StockShoppingList> getProductListDao() {
        return new StockShoppingListDao(this.bd);
    }

    @Override
    protected String getProductListTypeString() {
        return getResources().getString(R.string.upCase_the_stock_shopping_list);
    }

}
