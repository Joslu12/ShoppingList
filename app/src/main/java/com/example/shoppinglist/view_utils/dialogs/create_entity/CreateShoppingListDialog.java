package com.example.shoppinglist.view_utils.dialogs.create_entity;

import com.example.shoppinglist.R;

import model.ShoppingList;

public class CreateShoppingListDialog extends CreateListOfProductsDialog<ShoppingList> {

    //---- Constructor ----
    public CreateShoppingListDialog(CreateEntityDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String titleText = getResources().getString(R.string.the_shopping_list);
        String dialogTitle = String.format(getResources().getString(R.string.enter_the_product_list_name), titleText);
        return dialogTitle;
    }

    @Override
    public String getSuccessMsg(final String itemName) {
        String msg = String.format(getResources().getString(R.string.info_msg_product_list_created),
                getResources().getString(R.string.upCase_the_shopping_list), itemName);
        return msg;
    }

    //TODO: Comprobar que el nombre introducido es valido (No es solo numeros o simbolos raros)
    @Override
    public ShoppingList getEntityToCreate() {
        String name = getTypedName();
        if(name.equals("")) {
            return null;
        } else {
            return new ShoppingList(name);
        }
    }

}