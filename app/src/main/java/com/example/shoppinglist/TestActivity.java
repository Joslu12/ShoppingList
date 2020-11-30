package com.example.shoppinglist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.InventarioDao;
import bd.dao.ListaCompraDao;
import bd.dao.ListaCompraInventarioDao;
import bd.dao.ProductoDao;
import bd.dao.ProductoInventarioDao;
import bd.dao.ProductoTableDao;
import model.Inventario;
import model.ListaCompra;
import model.ListaCompraInventario;
import model.Producto;
import model.ProductoInventario;
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

        InventarioDao dao = new InventarioDao(bd);
        int id = 5;
        List<Inventario> inventarios = dao.findAll();

        for(Inventario inv : inventarios) {
            System.out.println("---------------- id: " + inv.getID());
        }

        /*Inventario inventario = new Inventario("Piscina");
        for(int i = 0; i<5; ++i) {
            try {
                ProductoInventario p = new ProductoInventario("Manguitos " + i, 4, i );
                inventario.addProducto(p);
            } catch (ShoppingListException e) {
                e.printStackTrace();
            }
        }

        dao.insert(inventario);

        ListaCompraInventarioDao dao2 = new ListaCompraInventarioDao(bd);

        inventario.setIdListaAsociada(dao2.insert(inventario.generarListaCompra()));

        dao.update(inventario);*/

        //Producto p = productoDao.findById(3);
        //productoDao.remove(p);
    }
}