package com.example.shoppinglist.view_utils.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.shoppinglist.R;

import model.ProductsListClass;

public abstract class CreateListOfProductsDialog<T extends ProductsListClass> extends CreateEntityDialog<T> {

    //---- View Elements ----
    private EditText inputName;

    //---- Constructor ----
    public CreateListOfProductsDialog(CreateEntityDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    @Override
    protected View setDialogContent() {
        return getLayoutInflater().inflate(R.layout.content_dialog_create_list_of_products,dialogContent,false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Referenciamos el EditText from el contenido del Dialog
        inputName = this.dialogContent.findViewById(R.id.input_name);

        return dialog;
    }

    final protected String getTypedName() {
        return inputName.getText().toString().trim();
    }

    @Override
    public String getErrorMsg(ProductsListClass entity) {
        if(entity == null) {
            return getResources().getString(R.string.blank_name_input_error);
        } else {
            return getResources().getString(R.string.unexpected_error);
        }
    }

    @Override
    public abstract T getEntityToCreate();
}