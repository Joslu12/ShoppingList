package com.example.shoppinglist.view_utils.dialogs.create_entity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

public class CreateProductDialog extends CreateEntityDialog<Product> {

    //---- View Elements ----
    private EditText inputName;
    private EditText inputAmount;

    //---- Constructor ----
    public CreateProductDialog(CreateEntityDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String titleText = getResources().getString(R.string.the_product);
        return String.format(getResources().getString(R.string.enter_the_entity), titleText);
    }

    @Override
    protected View generateTwoButtonsDialogContent() {
        return getLayoutInflater().inflate(R.layout.content_dialog_create_product,dialogContent,false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        //Referenciamos el EditText desde el contenido del Dialog
        inputName = this.dialogContent.findViewById(R.id.input_name);
        inputAmount = this.dialogContent.findViewById(R.id.input_amount);
        inputAmount.setKeyListener(new DigitsKeyListener());

        //Ponemos el foco en el EditText y abrimos el teclado directamente
        inputName.requestFocus();
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
        return String.format(getResources().getString(R.string.info_msg_entity_created),
                getResources().getString(R.string.upCase_the_product), entityName);
    }

    final protected String getTypedName() {
        return inputName.getText().toString().trim();
    }
    final protected String getTypedAmount() {
        return inputAmount.getText().toString().trim();
    }

    @Override
    public Product getEntityToCreate() throws AppException {
        try {
            String name = getTypedName();
            String amount = getTypedAmount();
            if(name.equals("")) {
                throw new AppException(new AppError(AppErrorHelper.CodeErrors.EMPTY_NAME_INPUT, getResources().getString(R.string.blank_name_input_error),getContext()));
            } else if(amount.equals("")) {
                throw new AppException(new AppError(AppErrorHelper.CodeErrors.EMPTY_AMOUNT_INPUT, getResources().getString(R.string.blank_amount_input_error),getContext()));
            } else {
                return new Product(name,Integer.parseInt(amount));
            }
        } catch (ShoppingListException e) {
            throw new AppException(new AppError(AppErrorHelper.CodeErrors.INVALID_AMOUNT_INPUT, getResources().getString(R.string.invalid_amount_input_error),getContext()));
        } catch (NumberFormatException e) {
            throw new AppException(new AppError(AppErrorHelper.CodeErrors.NOT_A_NUMBER_TARGET_AMOUNT_INPUT, getResources().getString(R.string.not_a_number_input_error),getContext()));
        }
    }

}
