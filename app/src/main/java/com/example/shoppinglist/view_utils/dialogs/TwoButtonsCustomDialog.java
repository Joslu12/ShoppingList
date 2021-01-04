package com.example.shoppinglist.view_utils.dialogs;

import android.annotation.SuppressLint;
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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_two_buttons, null);

        // Titulo del Dialogo
        ((TextView) view.findViewById(R.id.txt_dialog_title)).setText(this.getDialogTitle());

        dialogContent = (LinearLayout) view.findViewById(R.id.custom_dialog_content);
        dialogContent.addView(this.generateTwoButtonsDialogContent());

        // Botones del Dialogo
        Button btnAffirmative = ((Button) view.findViewById(R.id.btnAffirmative));
        btnAffirmative.setText(this.getTextAffirmativeButton());
        btnAffirmative.setBackgroundResource(this.getStyleAffirmativeButton());

        Button btnNegative = ((Button) view.findViewById(R.id.btnNegative));
        btnNegative.setText(this.getTextNegativeButton());
        btnNegative.setBackgroundResource(this.getStyleNegativeButton());

        return view;
    }

    @Override
    protected void setListenerToElements(View dialogView) {
        Button btnAffirmative = (Button) dialogView.findViewById(R.id.btnAffirmative);
        btnAffirmative.setOnClickListener(this);

        Button btnNegative = (Button) dialogView.findViewById(R.id.btnNegative);
        btnNegative.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
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
    protected abstract int getStyleAffirmativeButton();
    protected abstract void actionAffirmativePressed();

    protected abstract String getTextNegativeButton();
    protected int getStyleNegativeButton() {
        return R.drawable.button_cancel;
    }
    protected abstract void actionNegativePressed();
}
