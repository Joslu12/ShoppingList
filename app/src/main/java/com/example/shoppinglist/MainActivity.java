package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import bd.BaseDatosUtils;
import bd.ShoppingListContract;
import bd.dao.ProductoDao;
import model.ListaCompra;
import model.Producto;
import model.ShoppingListException;

public class MainActivity extends AppCompatActivity {

    //---- Atributos ----
    private TextView title;

    //---- Metodos ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title= findViewById(R.id.textViewTitle);
    }
}