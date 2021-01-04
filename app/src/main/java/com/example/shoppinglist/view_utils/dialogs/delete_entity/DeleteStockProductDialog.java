package com.example.shoppinglist.view_utils.dialogs.delete_entity;

import com.example.shoppinglist.R;

import model.StockProduct;

public class DeleteStockProductDialog extends DeleteEntityDialog<StockProduct> {
    //---- Constructor ----
    public DeleteStockProductDialog(DeleteEntityDialogListener listener, StockProduct stockProduct) {
        super(listener,stockProduct);
    }

    //---- Methods ----
    @Override
    protected String getDialogWarningMessage() {
        String deleteText = getResources().getString(R.string.delete);
        String titleText = getResources().getString(R.string.the_stock_product);
        return deleteText + " " + titleText + ": '" + entity.getName() + "'";
    }

    @Override
    protected String getDialogTitle() {
        String typeEntityText = getResources().getString(R.string.the_stock_product);
        return String.format(getResources().getString(R.string.delete_warning),typeEntityText,entity.getName());
    }
}
