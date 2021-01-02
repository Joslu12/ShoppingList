package com.example.shoppinglist.shoppinglists;

import com.example.shoppinglist.ListOfProductsActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteShoppingListDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteStockShoppingListDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditListOfProductsDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditShoppingListDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditStockShoppingListDialog;

import bd.dao.ListTableDao;
import bd.dao.ShoppingListDao;
import bd.dao.StockShoppingListDao;
import model.ShoppingList;
import model.Stock;
import model.StockShoppingList;

public class ShoppingListActivity<T extends ShoppingList> extends ListOfProductsActivity<T> {

    //---- Methods ----
    @Override
    protected ShoppingListFragment getNewInstance(ShoppingList shoppingList) {
        return ShoppingListFragment.newInstance(shoppingList);
    }

    @Override
    protected DeleteEntityDialog<T> generateEntityDeleteDialog() {
        if(productsList instanceof StockShoppingList) {
            return (DeleteEntityDialog<T>) new DeleteStockShoppingListDialog(this, (StockShoppingList) this.productsList);
        } else {
            return (DeleteEntityDialog<T>) new DeleteShoppingListDialog(this, this.productsList);
        }
    }

    @Override
    protected String generateDeleteDialogTag() {
        return "Delete a Shopping List";
    }

    @Override
    protected EditListOfProductsDialog<T> generateEntityEditDialog() {
        if(productsList instanceof StockShoppingList) {
            return (EditListOfProductsDialog<T>) new EditStockShoppingListDialog(this, (StockShoppingList) this.productsList);
        } else {
            return (EditListOfProductsDialog<T>) new EditShoppingListDialog(this, this.productsList);
        }
    }

    @Override
    protected String generateEditDialogTag() {
        return "Edit a Shopping List";
    }

    @Override
    protected ListTableDao<T> getProductListDao() {
        if(this.productListClass.equals(StockShoppingList.class)) {
            return (ListTableDao<T>) new StockShoppingListDao(this.bd);
        } else {
            return (ListTableDao<T>) new ShoppingListDao(this.bd);
        }
    }

    @Override
    protected String getProductListTypeString() {
        return getResources().getString(R.string.upCase_the_shopping_list);
    }

}
