package com.example.shoppinglist.shoppinglists_activities;

import com.example.shoppinglist.ListOfProductsActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteShoppingListDialog;

import bd.dao.ShoppingListDao;
import model.ShoppingList;

public class ShoppingListActivity extends ListOfProductsActivity<ShoppingList> {

    //---- Methods ----
    @Override
    protected DeleteEntityDialog<ShoppingList> generateEntityDialog() {
        return new DeleteShoppingListDialog(this, this.productsList);
    }

    @Override
    protected String generateDialogTag() {
        return "Delete a Shopping List";
    }

    @Override
    protected ShoppingListDao getProductListDao() {
        return new ShoppingListDao(this.bd);
    }

    @Override
    protected String getProductListTypeString() {
        return getResources().getString(R.string.upCase_the_shopping_list);
    }

}
