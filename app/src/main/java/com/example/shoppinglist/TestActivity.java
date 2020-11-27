package com.example.shoppinglist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import bd.BaseDatosUtils;
import bd.dao.ProductoDao;
import model.Producto;
import model.ShoppingListException;

public class TestActivity extends AppCompatActivity {

    //---- Atributos ----
    private SQLiteDatabase bd;

    //---- Metodos ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

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