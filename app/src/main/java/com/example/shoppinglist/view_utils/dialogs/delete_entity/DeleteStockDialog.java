package com.example.shoppinglist.view_utils.dialogs.delete_entity;

import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateListOfProductsDialog;

import model.Stock;

public class DeleteStockDialog extends DeleteEntityDialog<Stock> {

    //---- Constructor ----
    public DeleteStockDialog(DeleteEntityDialogListener listener, Stock stock) {
        super(listener,stock);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String deleteText = getResources().getString(R.string.delete);
        String titleText = getResources().getString(R.string.the_stock);
        return deleteText + " " + titleText + ": '" + entity.getName() + "'";
    }

    @Override
    protected String getDialogWarningMessage() {
        String typeEntityText = getResources().getString(R.string.the_stock);
        return String.format(getResources().getString(R.string.delete_warning),typeEntityText,entity.getName());
    }
}