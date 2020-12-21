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

import com.example.shoppinglist.view_utils.ListOfProductsListFragment;

import model.ShoppingList;
import model.Stock;

public class MainActivity extends AppCompatActivity {

    //---- Elementos de la Vista ----
    private Button shoppingListsBtn, stocksBtn;
    private FragmentTransaction transaction;
    private Fragment fragmentWelcome, fragmentShoppingList, fragmentStocks;
    private Fragment fragmentActive;

    //---- Metodos de la Actividad ----
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
                Toast.makeText(this, "NO SE HA PODIDO BORRAR", Toast.LENGTH_LONG).show();
                break;

            case R.id.action_settings:
                break;

            default:
                ok = super.onOptionsItemSelected(item);
                break;
        }
        return ok;
    }

    //---- Metodos ----
    public void buttonClicked(View v) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch(v.getId()) {
            case R.id.btnShoppingLists:
                fragmentShoppingList = ListOfProductsListFragment.newInstance(ShoppingList.class);
                transaction.replace(R.id.contenedorFragments,fragmentShoppingList);
                fragmentActive = fragmentShoppingList;
                break;

            case R.id.btnStocks:
                fragmentStocks = ListOfProductsListFragment.newInstance(Stock.class);
                transaction.replace(R.id.contenedorFragments,fragmentStocks);
                fragmentActive = fragmentShoppingList;
                break;

            case R.id.addNewProductsList:
                // Si el fragmento inicio tiene la referencia de uno de los dos de ListOfProductListFragment
                if (fragmentActive == fragmentShoppingList || fragmentWelcome == fragmentStocks) {
                    ((ListOfProductsListFragment) fragmentActive).addNewListOfProducts();
                }
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

}