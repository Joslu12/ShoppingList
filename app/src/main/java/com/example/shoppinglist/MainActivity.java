package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shoppinglist.list_of_products_list.shopping_lists.ListOfShoppingListsFragment;
import com.example.shoppinglist.list_of_products_list.stock_shopping_lists.ListOfStockShoppingListsFragment;
import com.example.shoppinglist.list_of_products_list.stocks.ListOfStocksFragment;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteAllEntitiesDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.list_of_products_list.ListOfProductsListFragment;

import bd.BaseDatosUtils;

public class MainActivity extends AppCompatActivity implements DeleteEntityDialog.DeleteEntityDialogListener {

    //---- View Elements ----
    private Button btnShoppingLists, btnStocks, btnStockShoppingList;
    private Fragment fragmentWelcome, fragmentShoppingLists, fragmentStocks, fragmentStockShoppingLists;
    private Fragment fragmentActive;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Cargamos los elementos de la vista
        btnShoppingLists = findViewById(R.id.btnShoppingLists);
        btnStocks = findViewById(R.id.btnStocks);
        btnStockShoppingList = findViewById(R.id.btnStockShoppingLists);

        // Instanciamos los fragmentos
        fragmentWelcome = new WelcomeFragment();
        fragmentShoppingLists = new ListOfShoppingListsFragment();
        fragmentStocks = new ListOfStocksFragment();
        fragmentStockShoppingLists = new ListOfStockShoppingListsFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragments, fragmentWelcome).commit();
        fragmentActive = fragmentWelcome;

        // METODO QUE GENERA DATOS ARBITRARIOS PARA PODER COMPROBAR EL COMPORTAMIENTO DE LA APLICACION
        //BaseDatosUtils.generateDefaultDataForAppTest(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main ,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ok = true;

        switch (item.getItemId()) {
            case R.id.delete_all_data:
                DeleteAllEntitiesDialog dialog = new DeleteAllEntitiesDialog(this);
                dialog.show(getSupportFragmentManager(), "Delete all data");
                break;

            default:
                ok = super.onOptionsItemSelected(item);
                break;
        }
        return ok;
    }

    //---- Methods ----
    @SuppressLint("NonConstantResourceId")
    public void buttonClicked(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(view.getId()) {
            case R.id.btnShoppingLists:
                transaction.replace(R.id.contenedorFragments, fragmentShoppingLists);
                fragmentActive = fragmentShoppingLists;

                btnShoppingLists.setEnabled(false);
                btnStocks.setEnabled(true);
                btnStockShoppingList.setEnabled(true);
                break;

            case R.id.btnStocks:
                transaction.replace(R.id.contenedorFragments,fragmentStocks);
                fragmentActive = fragmentStocks;

                btnShoppingLists.setEnabled(true);
                btnStocks.setEnabled(false);
                btnStockShoppingList.setEnabled(true);
                break;

            case R.id.btnStockShoppingLists:
                transaction.replace(R.id.contenedorFragments,fragmentStockShoppingLists);
                fragmentActive = fragmentStockShoppingLists;

                btnShoppingLists.setEnabled(true);
                btnStocks.setEnabled(true);
                btnStockShoppingList.setEnabled(false);
                break;

            case R.id.btnAddNewProductsList:
                // Si el fragmento inicio tiene la referencia de uno de los dos de ListOfProductListFragment
                if (fragmentActive == fragmentShoppingLists || fragmentActive == fragmentStocks) {
                    ((ListOfProductsListFragment<?>) fragmentActive).openCreateListOfProductsDialog();
                }
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDialogDeleteClick(DeleteEntityDialog dialog) {
        // Vaciamos la Base de Datos
        BaseDatosUtils.deleteAllDataStored(this);

        // Mostramos un mensaje informativo de la accion realizada
        String msg = getResources().getString(R.string.info_msg_all_data_deleted);
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

        // Volvemos al fragmento de Bienvenida
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentWelcome).commit();
        fragmentActive = fragmentWelcome;

        btnShoppingLists.setEnabled(true);
        btnStocks.setEnabled(true);
        btnStockShoppingList.setEnabled(true);
    }
}