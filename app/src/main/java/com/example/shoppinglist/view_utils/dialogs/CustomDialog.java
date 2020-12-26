package com.example.shoppinglist.view_utils.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public abstract class CustomDialog extends DialogFragment {

    //---- Constants and Definitions ----
    protected interface CustomDialogListener {}

    //---- Attributes ----
    protected CustomDialogListener listener;

    //---- Constructor ----
    public CustomDialog(CustomDialogListener listener) {
        super();
        this.listener = listener;
    }

    //---- Methods ----
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Generamos la view del CustomDialog
        View dialogView = this.generateCustomDialogView();

        // Añadimos el listener necesario a los elementos de la View
        this.setListenerToElements(dialogView);

        // Añadimos el View al builder y lo creamos
        builder.setView(dialogView);
        return builder.create();
    }

    protected abstract View generateCustomDialogView();
    protected abstract void setListenerToElements(View dialogView);

}
