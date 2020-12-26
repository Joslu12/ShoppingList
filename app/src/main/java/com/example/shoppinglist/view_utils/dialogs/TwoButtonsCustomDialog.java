package com.example.shoppinglist.view_utils.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shoppinglist.R;

public abstract class TwoButtonsCustomDialog extends CustomDialog implements View.OnClickListener {

    //---- Attributes ----
    protected LinearLayout dialogContent;

    //---- Constructor ----
    public TwoButtonsCustomDialog(CustomDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    protected View generateCustomDialogView() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_two_buttons, null);

        // Titulo del Dialogo
        ((TextView) view.findViewById(R.id.txt_dialog_title)).setText(this.getDialogTitle());

        dialogContent = (LinearLayout) view.findViewById(R.id.custom_dialog_content);
        dialogContent.addView(this.generateTwoButtonsDialogContent());

        // Botones del Dialogo
        ((Button) view.findViewById(R.id.btnAffirmative)).setText(this.getTextAffirmativeButton());
        ((Button) view.findViewById(R.id.btnNegative)).setText(this.getTextNegativeButton());
        //TODO: set Style de los botones

        return view;
    }

    @Override
    protected void setListenerToElements(View dialogView) {
        Button btnAffirmative = (Button) dialogView.findViewById(R.id.btnAffirmative);
        btnAffirmative.setOnClickListener(this);

        Button btnNegative = (Button) dialogView.findViewById(R.id.btnNegative);
        btnNegative.setOnClickListener(this);
    }

    @Override
    public final void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnAffirmative:
                actionAffirmativePressed();
                break;

            case R.id.btnNegative:
                actionNegativePressed();
                break;
        }
    }

    protected abstract String getDialogTitle();
    protected abstract View generateTwoButtonsDialogContent();

    protected abstract String getTextAffirmativeButton();
    //TODO: protected abstract Appareance appareanceAffirmativeButton();
    protected abstract void actionAffirmativePressed();

    protected abstract String getTextNegativeButton();
    //TODO: protected abstract Appareance appareanceNegativeButton();
    protected abstract void actionNegativePressed();
}
