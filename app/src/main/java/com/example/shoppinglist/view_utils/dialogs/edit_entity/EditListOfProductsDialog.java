package com.example.shoppinglist.view_utils.dialogs.edit_entity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.TwoButtonsCustomDialog;

import model.ProductsListClass;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class EditListOfProductsDialog<T extends ProductsListClass> extends TwoButtonsCustomDialog {

    //---- View Elements ----
    private EditText inputName;

    //---- Attributes ----
    protected T entity;

    //---- Constants and Definitions ----
    public interface EditListOfProductsDialogListener extends CustomDialogListener {
        void onDialogUpdateClick(EditListOfProductsDialog dialog);
    }

    //---- Constructor ----
    public EditListOfProductsDialog(CustomDialogListener listener, T entity) {
        super(listener);
        this.entity = entity;
    }

    //---- Methods ----
    @Override
    protected View generateTwoButtonsDialogContent() {
        View view = getLayoutInflater().inflate(R.layout.content_dialog_edit_entity,dialogContent,false);

        // Establecemos el placeholder del Dialog
        ((TextView) view.findViewById(R.id.input_name)).setText(entity.getName());

        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Referenciamos el EditText desde el contenido del Dialog
        inputName = this.dialogContent.findViewById(R.id.input_name);

        // Ponemos el foco en el EditText y abrimos el teclado directamente
        inputName.setSelectAllOnFocus(true);
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

    final public String getTypedName() {
        return inputName.getText().toString().trim();
    }

    @Override
    protected String getTextAffirmativeButton() {
        return getResources().getString(R.string.update);
    }

    @Override
    protected void actionAffirmativePressed() {
        ((EditListOfProductsDialogListener)listener).onDialogUpdateClick(this);
    }

    @Override
    protected String getTextNegativeButton() {
        return getResources().getString(R.string.cancel);
    }

    @Override
    protected void actionNegativePressed() {
        this.dismiss();
    }

}
