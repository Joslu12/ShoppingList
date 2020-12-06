package com.example.shoppinglist.shoppinglists_activities;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shoppinglist.R;

import bd.BaseDatosUtils;
import bd.dao.ShoppingListDao;
import model.ShoppingList;

public class ShoppingListActivity extends AppCompatActivity {

    //---- Constantes y Definiciones ----

    //---- Atributos ----
    private SQLiteDatabase bd;
    private ShoppingList shoppingList;

    //---- Elementos de la Vista ----

    //---- Metodos ----
    private void loadShoppingList(int id) {
        // Cargamos la lista de la compra almacenada en la BD con el id especificado
        ShoppingListDao daoSL = new ShoppingListDao(bd);
        shoppingList = daoSL.findById(id);
    }

    private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.warnig_dialog);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                deleteShoppingList();
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

    private void deleteShoppingList() {
        // Guardamos el nombre para el mensage mostrado despues
        String name = shoppingList.getName();

        // Eliminamos de la BD la lista de la compra
        ShoppingListDao dao = new ShoppingListDao(bd);
        dao.remove(shoppingList);

        // Finalizamos la actividad
        this.finish();

        // Mostramos un mensaje informativo de la accion realizada
        String msg = String.format(getResources().getString(R.string.info_msg_shopping_list_deleted), name);
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    //---- Metodos de la Actividad ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtenemos una conexion a la BD con el SingletonMap
        bd = BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext());

        // Cargamos de la base de datos la lista de la compra
        loadShoppingList(getIntent().getIntExtra("ID",-1));

        // Configuramos lo necesario de la barra de herramientas
        getSupportActionBar().setTitle(shoppingList.getName());
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
