package com.example.shoppinglist.view_utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.shoppinglist.R;

public class CreateDialogFragment extends DialogFragment {
    private EditText editStockName;
    private CreateDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (CreateDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_new_dialog,null);
        builder.setMessage(R.string.title_stock_dialog)
                .setPositiveButton(R.string.create_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = editStockName.getText().toString();
                        listener.applyText(name);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        editStockName= view.findViewById(R.id.title_stock_dialog);
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public interface CreateDialogListener{
        void applyText(String name);
    }
}