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
import com.example.shoppinglist.app_error_handling.AppError;
import com.example.shoppinglist.app_error_handling.AppErrorHelper;
import com.example.shoppinglist.app_error_handling.AppException;

import model.Product;
import model.ShoppingListException;
import model.StockProduct;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class CreateProductDialog extends CreateEntityDialog<Product> {
    //---- View Elements ----
    private EditText inputName;
    private EditText inputTargetAmount;

    //---- Constructor ----
    public CreateProductDialog(CreateEntityDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String titleText = getResources().getString(R.string.the_product);
        String dialogTitle = String.format(getResources().getString(R.string.enter_the_product_name), titleText);
        return dialogTitle;
    }

    @Override
    protected View generateTwoButtonsDialogContent() {
        return getLayoutInflater().inflate(R.layout.content_dialog_create_product,dialogContent,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        //Referenciamos el EditText desde el contenido del Dialog
        inputName = this.dialogContent.findViewById(R.id.input_name);
        inputTargetAmount = this.dialogContent.findViewById(R.id.input_target_amount);

        //Ponemos el foco en el EditText y abrimos el teclado directamente
        inputName.requestFocus();
        inputTargetAmount.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        return super.onCreateView(inflater, container,savedInstaceState);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        // Cerramos el teclado
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    public String getSuccessMsg(String entityName) {
        String msg = String.format(getResources().getString(R.string.info_msg_product_created),
                getResources().getString(R.string.upCase_the_product), entityName);
        return msg;
    }

    final protected String getTypedName() {
        return inputName.getText().toString().trim();
    }
    final protected String getTypedCurrentAmount() {
        return inputTargetAmount.getText().toString().trim();
    }

    @Override
    public Product getEntityToCreate() throws AppException {
        String name = getTypedName();
        int currentAmount = Integer.parseInt(getTypedCurrentAmount());
        if(name.equals("")) {
            throw new AppException(new AppError(AppErrorHelper.CodeErrors.EMPTY_NAME_IMPUT, getResources().getString(R.string.blank_name_input_error),getContext()));
        } else {
            try {
                return new Product(name,currentAmount);
            } catch (ShoppingListException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
