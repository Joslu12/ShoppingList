package com.example.shoppinglist.view_utils.dialogs.create_entity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.shoppinglist.R;

import model.ProductsListClass;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class CreateListOfProductsDialog<T extends ProductsListClass> extends CreateEntityDialog<T> {

    //---- View Elements ----
    private EditText inputName;

    //---- Constructor ----
    public CreateListOfProductsDialog(CreateEntityDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    @Override
    protected View generateTwoButtonsDialogContent() {
        return getLayoutInflater().inflate(R.layout.content_dialog_create_list_of_products,dialogContent,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Referenciamos el EditText desde el contenido del Dialog
        inputName = this.dialogContent.findViewById(R.id.input_name);

        // Ponemos el foco en el EditText y abrimos el teclado directamente
        inputName.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        return super.onCreateView(inflater, container,savedInstanceState);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        // Cerramos el teclado
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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