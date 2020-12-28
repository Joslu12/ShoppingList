package com.example.shoppinglist.app_error_handling;

import android.content.Context;

import com.example.shoppinglist.R;

import java.io.Serializable;

public class AppError {

    //---- Attributes ----
    private final int code;
    private final String msg;
    private final Context context;

    //---- Constructor ----
    public AppError(final int code, final String msg, final Context context) {
        // Creamos el error
        this.code = code;
        this.msg = msg;
        this.context = context;

        // Tratamos el error segun su codigo
        AppErrorHelper.handleError(this);
    }

    //---- Methods ----
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public String toString() {
        return context.getResources().getString(R.string.code) + ": " + code + ". " + msg;
    }
}
