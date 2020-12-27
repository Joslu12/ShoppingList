package com.example.shoppinglist.view_utils.dialogs.edit_entity;

import com.example.shoppinglist.R;

import model.ShoppingList;

public class EditShoppingListDialog extends EditListOfProductsDialog<ShoppingList> {

    //---- Constructor ----
    public EditShoppingListDialog(EditListOfProductsDialogListener listener, ShoppingList shoppingList) {
        super(listener,shoppingList);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String deleteText = getResources().getString(R.string.update);
        String titleText = getResources().getString(R.string.the_shopping_list);
        return deleteText + " " + titleText + ": '" + entity.getName() + "'";
    }
}