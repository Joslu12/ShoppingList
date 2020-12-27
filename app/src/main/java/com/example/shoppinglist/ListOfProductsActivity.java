package com.example.shoppinglist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditListOfProductsDialog;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import model.ProductsListClass;

public abstract class ListOfProductsActivity<T extends ProductsListClass> extends AppCompatActivity implements DeleteEntityDialog.DeleteEntityDialogListener, EditListOfProductsDialog.EditListOfProductsDialogListener {

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
            case R.id.edit_name:
                EditListOfProductsDialog<T> dialogEdit = generateEntityEditDialog();
                dialogEdit.show(getSupportFragmentManager(), generateEditDialogTag());
                break;

            case R.id.delete:
                DeleteEntityDialog<T> dialogDelete = generateEntityDeleteDialog();
                dialogDelete.show(getSupportFragmentManager(), generateDeleteDialogTag());
                break;

            default:
                ok = super.onOptionsItemSelected(item);
                break;
        }
        return ok;
    }

    //---- Methods ----
    protected abstract DeleteEntityDialog<T> generateEntityDeleteDialog();
    protected abstract String generateDeleteDialogTag();

    protected abstract EditListOfProductsDialog<T> generateEntityEditDialog();
    protected abstract String generateEditDialogTag();

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

    @Override
    public void onDialogUpdateClick(EditListOfProductsDialog dialog) {
        ListTableDao<T> dao = getProductListDao();

        // Actualizamos el nombre de la lista de productos
        String newName = dialog.getTypedName();
        if(newName.equals("")) {
            // Mostramos un mensaje de error
            Toast.makeText(getApplicationContext(),getResources().getText(R.string.blank_name_input_error),Toast.LENGTH_LONG).show();
        } else {
            productsList.setName(dialog.getTypedName());
            dao.update(productsList);

            // Actualizamos la vista y cerramos el Dialog
            getSupportActionBar().setTitle(productsList.getName());
            dialog.dismiss();

            // Mostramos un mensaje informativo de la accion realizada
            String msg = String.format(getResources().getString(R.string.info_msg_product_list_updated), getProductListTypeString(), productsList.getName());
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        }
    }

    protected abstract String getProductListTypeString();
}
