package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteAllEntitiesDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.fragments.ListOfProductsListFragment;

import bd.BaseDatosUtils;
import model.ShoppingList;
import model.Stock;

public class MainActivity extends AppCompatActivity implements DeleteEntityDialog.DeleteEntityDialogListener {

    //---- View Elements ----
    private Button shoppingListsBtn, stocksBtn;
    private FragmentTransaction transaction;
    private Fragment fragmentWelcome, fragmentShoppingList, fragmentStocks;
    private Fragment fragmentActive;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Cargamos los elementos de la vista //TODO: Seran usados para cambios de color
        shoppingListsBtn = findViewById(R.id.btnShoppingLists);
        stocksBtn = findViewById(R.id.btnStocks);

        // Instanciamos los fragmentos
        fragmentWelcome = new WelcomeFragment();
        fragmentShoppingList = new ListOfProductsListFragment();
        fragmentStocks = new ListOfProductsListFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragments, fragmentWelcome).commit();
        fragmentActive = fragmentWelcome;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ok = true;

        switch (item.getItemId()) {
            case R.id.delete_all_data:
                DeleteAllEntitiesDialog dialog = new DeleteAllEntitiesDialog(this);
                dialog.show(getSupportFragmentManager(), "Delete all data");
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
    public void buttonClicked(View view) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch(view.getId()) {
            case R.id.btnShoppingLists:
                fragmentShoppingList = ListOfProductsListFragment.newInstance(ShoppingList.class);
                transaction.replace(R.id.contenedorFragments,fragmentShoppingList);
                fragmentActive = fragmentShoppingList;
                break;

            case R.id.btnStocks:
                fragmentStocks = ListOfProductsListFragment.newInstance(Stock.class);
                transaction.replace(R.id.contenedorFragments,fragmentStocks);
                fragmentActive = fragmentStocks;
                break;

            case R.id.btnAddNewProductsList:
                // Si el fragmento inicio tiene la referencia de uno de los dos de ListOfProductListFragment
                if (fragmentActive == fragmentShoppingList || fragmentActive == fragmentStocks) {
                    ((ListOfProductsListFragment) fragmentActive).openCreateListOfProductsDialog();
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
    }
}