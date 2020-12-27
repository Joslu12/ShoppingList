package com.example.shoppinglist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import model.ProductsListClass;

public abstract class ListOfProductsActivity<T extends ProductsListClass> extends AppCompatActivity implements DeleteEntityDialog.DeleteEntityDialogListener {

    //---- Attributes ----
    protected SQLiteDatabase bd;
    protected T productsList;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtenemos una conexion a la BD con el SingletonMap
        bd = BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext());

        // Cargamos de la base de datos la lista de la compra
        loadProductList(getIntent().getIntExtra("ID",-1));

        // Configuramos lo necesario de la barra de herramientas
        getSupportActionBar().setTitle(productsList.getName());
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
                DeleteEntityDialog<T> dialog = generateEntityDialog();
                dialog.show(getSupportFragmentManager(), generateDialogTag());
                break;

            case R.id.action_settings:
                break;

            default:
                ok = super.onOptionsItemSelected(item);
                break;
        }
        return ok;
    }

    //---- Methods ----
    protected abstract DeleteEntityDialog<T> generateEntityDialog();
    protected abstract String generateDialogTag();

    protected abstract ListTableDao<T> getProductListDao();
    private void loadProductList(int id) {
        // Cargamos la lista de productos almacenada en la BD con el id especificado
        ListTableDao<T> daoPL = getProductListDao();
        productsList = daoPL.findById(id);
    }

    @Override
    public void onDialogDeleteClick(DeleteEntityDialog dialog) {
        // Guardamos el nombre para el mensage mostrado despues
        String name = productsList.getName();

        // Eliminamos de la BD la lista de productos
        ListTableDao<T> dao = getProductListDao();
        dao.remove(productsList);

        // Finalizamos la actividad
        this.finish();

        // Mostramos un mensaje informativo de la accion realizada
        String msg = String.format(getResources().getString(R.string.info_msg_product_list_deleted), getProductListTypeString(), name);
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    protected abstract String getProductListTypeString();
}
