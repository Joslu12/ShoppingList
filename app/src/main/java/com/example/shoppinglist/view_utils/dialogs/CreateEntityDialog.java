package com.example.shoppinglist.view_utils.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.shoppinglist.R;

public abstract class CreateEntityDialog<T> extends DialogFragment implements View.OnClickListener {

    //---- Constants and Definitions ----
    public interface CreateEntityDialogListener {
        public void onDialogCreateClick(CreateEntityDialog dialog);
    }

    //---- Attributes ----
    private CreateEntityDialogListener listener;
    protected LinearLayout dialogContent;

    //---- Constructor ----
    public CreateEntityDialog(CreateEntityDialogListener listener) {
        super();
        this.listener = listener;
    }

    //---- Methods ----
    //TODO: abrir el teclado directamente
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_create_entity, null);

        // Titulo del Dialogo
        ((TextView) view.findViewById(R.id.txt_create_entity_dialog_title)).setText(this.getTitle());

        // Contenido principal del Dialogo
        dialogContent = (LinearLayout) view.findViewById(R.id.create_entity_dialog_content);
        dialogContent.addView(setDialogContent());

        // Botones del Dialogo
        Button btnCreate = (Button) view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //TODO: cerrar teclado si está abierto
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnCreate:
                listener.onDialogCreateClick(this);
                break;

            case R.id.btnCancel:
                this.dismiss();
                break;
        }
    }

    protected abstract View setDialogContent();
    protected abstract String getTitle();
    public abstract String getErrorMsg(final T entity);
    public abstract String getSuccessMsg(final String entityName);
    public abstract T getEntityToCreate();
}