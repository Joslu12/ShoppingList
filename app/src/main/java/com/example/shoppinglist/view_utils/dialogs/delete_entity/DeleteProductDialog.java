package com.example.shoppinglist.view_utils.dialogs.delete_entity;

import com.example.shoppinglist.R;

import model.Product;

public class DeleteProductDialog extends DeleteEntityDialog<Product> {
    //---- Constructor ----
    public DeleteProductDialog(DeleteEntityDialogListener listener, Product product) {
        super(listener,product);
    }

    //---- Methods ----
    @Override
    protected String getDialogWarningMessage() {
        String deleteText = getResources().getString(R.string.delete);
        String titleText = getResources().getString(R.string.the_product);
        return deleteText + " " + titleText + ": '" + entity.getName() + "'";
    }

    @Override
    protected String getDialogTitle() {
        String typeEntityText = getResources().getString(R.string.the_product);
        return String.format(getResources().getString(R.string.delete_warning),typeEntityText,entity.getName());
    }
}
