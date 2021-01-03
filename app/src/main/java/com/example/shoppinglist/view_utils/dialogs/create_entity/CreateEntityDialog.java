package com.example.shoppinglist.view_utils.dialogs.create_entity;

import com.example.shoppinglist.R;
import com.example.shoppinglist.app_error_handling.AppException;
import com.example.shoppinglist.view_utils.dialogs.TwoButtonsCustomDialog;

public abstract class CreateEntityDialog<T> extends TwoButtonsCustomDialog {

    //---- Constants and Definitions ----
    public interface CreateEntityDialogListener extends CustomDialogListener {
        void onDialogCreateClick(CreateEntityDialog dialog);
    }

    //---- Constructor ----
    public CreateEntityDialog(CreateEntityDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    @Override
    protected String getTextAffirmativeButton() {
        return getResources().getString(R.string.create);
    }

    @Override
    protected int getStyleAffirmativeButton() {
        return R.drawable.button_positive;
    }

    @Override
    protected void actionAffirmativePressed() {
        ((CreateEntityDialogListener)listener).onDialogCreateClick(this);
    }

    @Override
    protected String getTextNegativeButton() {
        return getResources().getString(R.string.cancel);
    }

    @Override
    protected void actionNegativePressed() {
        this.dismiss();
    }

    public abstract String getSuccessMsg(final String entityName);
    public abstract T getEntityToCreate() throws AppException;
}