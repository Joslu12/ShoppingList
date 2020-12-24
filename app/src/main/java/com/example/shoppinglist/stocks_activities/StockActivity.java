package com.example.shoppinglist.stocks_activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shoppinglist.R;

import bd.BaseDatosUtils;
import bd.dao.StockDao;
import model.Stock;

public class StockActivity extends AppCompatActivity {

    //---- Attributes ----
    private SQLiteDatabase bd;
    private Stock stock;

    //---- Methods ----
    private void loadStock(int id) {
        // Cargamos la lista de la compra almacenada en la BD con el id especificado
        StockDao daoSL = new StockDao(bd);
        stock = daoSL.findById(id);
    }

    private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.warning);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                deleteStock();
            }
        });
        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void deleteStock(){
        // Guardamos el nombre para el mensage mostrado despues
        String name = stock.getName();

        // Eliminamos de la BD el inventario
        StockDao dao = new StockDao(bd);
        dao.remove(stock);

        // Finalizamos la actividad
        this.finish();

        // Mostramos un mensaje informativo de la accion realizada
        String msg = String.format(getResources().getString(R.string.info_msg_product_list_deleted),
                getResources().getString(R.string.upCase_the_stock), name);
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtenemos una conexion a la BD con el SingletonMap
        bd = BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext());

        // Cargamos de la base de datos la lista de la compra
        loadStock(getIntent().getIntExtra("ID",-1));

        // Configuramos lo necesario de la barra de herramientas
        getSupportActionBar().setTitle(stock.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products_list ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ok = true;

        switch (item.getItemId()) {
            case R.id.delete:
                deleteDialog();
                break;

            case R.id.action_settings:
                break;

            default:
                ok = super.onOptionsItemSelected(item);
                break;
        }
        return ok;
    }
}