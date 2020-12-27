package com.example.shoppinglist.view_utils.dialogs.edit_entity;

import com.example.shoppinglist.R;

import model.Stock;

public class EditStockDialog extends EditListOfProductsDialog<Stock> {

    //---- Constructor ----
    public EditStockDialog(EditListOfProductsDialogListener listener, Stock stock) {
        super(listener,stock);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String deleteText = getResources().getString(R.string.update);
        String titleText = getResources().getString(R.string.the_stock);
        return deleteText + " " + titleText + ": '" + entity.getName() + "'";
    }
}