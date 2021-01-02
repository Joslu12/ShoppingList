package com.example.shoppinglist.shoppinglists;

import com.example.shoppinglist.ListOfProductsActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteShoppingListDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditListOfProductsDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditShoppingListDialog;

import bd.dao.ShoppingListDao;
import model.ShoppingList;

public class ShoppingListActivity extends ListOfProductsActivity<ShoppingList> {

    //---- Methods ----
    @Override
    protected ShoppingListFragment getNewInstance(ShoppingList shoppingList) {
        return ShoppingListFragment.newInstance(shoppingList);
    }

    @Override
    protected DeleteEntityDialog<ShoppingList> generateEntityDeleteDialog() {
        return new DeleteShoppingListDialog(this, this.productsList);
    }

    @Override
    protected String generateDeleteDialogTag() {
        return "Delete a Shopping List";
    }

    @Override
    protected EditListOfProductsDialog<ShoppingList> generateEntityEditDialog() {
        return new EditShoppingListDialog(this,this.productsList);
    }

    @Override
    protected String generateEditDialogTag() {
        return "Edit a Shopping List";
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
