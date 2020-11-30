package com.example.shoppinglist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.StockDao;
import model.Stock;

public class TestActivity extends AppCompatActivity {

    //---- Atributos ----
    private SQLiteDatabase bd;

    //---- Metodos ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        bd = BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext());

        StockDao dao = new StockDao(bd);
        int id = 5;
        List<Stock> stocks = dao.findAll();

        for(Stock inv : stocks) {
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