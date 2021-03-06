package com.example.shoppinglist.product_list_class.shopping_list;

import com.example.shoppinglist.product_list_class.ProductListClassActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteShoppingListDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditListOfProductsDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditShoppingListDialog;
import com.example.shoppinglist.product_list_class.ListOfProductsFragment;

import bd.dao.ListTableDao;
import bd.dao.ShoppingListDao;
import model.Product;
import model.ShoppingList;

public class ShoppingListActivity extends ProductListClassActivity<ShoppingList> {

    //---- Methods ----
    @Override
    protected ListOfProductsFragment<Product> getNewInstance(ShoppingList shoppingList) {
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
        return new EditShoppingListDialog(this, this.productsList);
    }

    @Override
    protected String generateEditDialogTag() {
        return "Edit a Shopping List";
    }

    @Override
    protected ListTableDao<ShoppingList> getProductListDao() {
        return new ShoppingListDao(this.bd);
    }

    @Override
    protected String getProductListTypeString() {
        return getResources().getString(R.string.upCase_the_shopping_list);
    }

}
