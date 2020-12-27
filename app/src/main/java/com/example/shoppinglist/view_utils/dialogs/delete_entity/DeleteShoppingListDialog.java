package com.example.shoppinglist.view_utils.dialogs.delete_entity;

import com.example.shoppinglist.R;

import model.ShoppingList;

public class DeleteShoppingListDialog extends DeleteEntityDialog<ShoppingList> {

    //---- Constructor ----
    public DeleteShoppingListDialog(DeleteEntityDialogListener listener, ShoppingList shoppingList) {
        super(listener,shoppingList);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String deleteText = getResources().getString(R.string.delete);
        String titleText = getResources().getString(R.string.the_shopping_list);
        return deleteText + " " + titleText + ": '" + entity.getName() + "'";
    }

    @Override
    protected String getDialogWarningMessage() {
        String typeEntityText = getResources().getString(R.string.the_shopping_list);
        return String.format(getResources().getString(R.string.delete_warning),typeEntityText,entity.getName());
    }
}