package com.example.shoppinglist.view_utils.dialogs.delete_entity;

import com.example.shoppinglist.R;

import model.IdentifiedObjectClass;

public class DeleteAllEntitiesDialog extends DeleteEntityDialog<IdentifiedObjectClass> {

    //---- Constructor ----
    public DeleteAllEntitiesDialog(CustomDialogListener listener) {
        super(listener,null);
    }

    //---- Methods ----
    @Override
    protected String getDialogTitle() {
        return getResources().getString(R.string.delete_all_data);
    }

    @Override
    protected String getDialogWarningMessage() {
        return getResources().getString(R.string.delete_all_data_warning);
    }
}
