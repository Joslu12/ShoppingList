package com.example.shoppinglist.view_utils.dialogs.delete_entity;

import android.view.View;
import android.widget.TextView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.TwoButtonsCustomDialog;

public abstract class DeleteEntityDialog<T> extends TwoButtonsCustomDialog {

    //---- Attributes ----
    protected T entity;

    //---- Constants and Definitions ----
    public interface DeleteEntityDialogListener extends CustomDialogListener {
        void onDialogDeleteClick(DeleteEntityDialog dialog);
    }

    //---- Constructor ----
    public DeleteEntityDialog(CustomDialogListener listener, T entity) {
        super(listener);
        this.entity = entity;
    }

    //---- Methods ----
    public T getEntity() {
        return entity;
    }

    @Override
    protected View generateTwoButtonsDialogContent() {
        View view = getLayoutInflater().inflate(R.layout.content_dialog_delete_entity,dialogContent,false);

        // Establecemos el mesaje de alerta del Dialog
        ((TextView) view.findViewById(R.id.txt_warning_msg)).setText(getDialogWarningMessage());

        return view;
    }

    @Override
    protected String getTextAffirmativeButton() {
        return getResources().getString(R.string.delete);
    }

    @Override
    protected int getStyleAffirmativeButton() {
        return R.drawable.button_negative;
    }

    @Override
    protected void actionAffirmativePressed() {
        ((DeleteEntityDialog.DeleteEntityDialogListener)listener).onDialogDeleteClick(this);
        this.dismiss();
    }

    @Override
    protected String getTextNegativeButton() {
        return getResources().getString(R.string.cancel);
    }

    @Override
    protected void actionNegativePressed() {
        this.dismiss();
    }

    protected abstract String getDialogWarningMessage();
}
