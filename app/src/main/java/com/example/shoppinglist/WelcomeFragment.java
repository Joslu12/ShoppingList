package com.example.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class WelcomeFragment extends Fragment {

    //---- Constructor ----
    public WelcomeFragment() {
        // Constructo publico y vacio requerido
    }

    //---- Methods ----
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
}
