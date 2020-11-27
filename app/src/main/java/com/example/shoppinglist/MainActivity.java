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
    private SQLiteDatabase bd;

    //---- Metodos ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext());

        ProductoDao productoDao = new ProductoDao(bd);
        for(int i = 1; i < 10; ++i) {
            try {
                productoDao.insert(new Producto("Pipas del Indio" + i, i));
            } catch (ShoppingListException e) {
                e.printStackTrace();
            }
        }

        TextView t = (TextView) findViewById (R.id.textView);

        //Producto p = productoDao.findById(3);
        //productoDao.remove(p);
    }
}