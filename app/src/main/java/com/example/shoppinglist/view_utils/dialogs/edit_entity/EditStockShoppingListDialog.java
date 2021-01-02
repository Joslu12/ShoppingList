package com.example.shoppinglist.view_utils.dialogs.edit_entity;

import com.example.shoppinglist.R;

import model.ShoppingList;
import model.StockShoppingList;

public class EditStockShoppingListDialog extends EditListOfProductsDialog<StockShoppingList> {

    //---- Constructor ----
    public EditStockShoppingListDialog(EditListOfProductsDialogListener listener, StockShoppingList shoppingList) {
        super(listener,shoppingList);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String deleteText = getResources().getString(R.string.update);
        String titleText = getResources().getString(R.string.the_stock_shopping_list);
        return deleteText + " " + titleText + ": '" + entity.getName() + "'";
    }
}