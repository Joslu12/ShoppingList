package com.example.shoppinglist.shoppinglists_activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.shoppinglist.ProductsListArrayAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.R;

import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.ShoppingListDao;
import bd.dao.StockShoppingListDao;
import model.ShoppingList;
import model.StockShoppingList;

public class ShoppingListListActivity extends AppCompatActivity {

    //---- Constantes y Definiciones ----

    //---- Atributos ----
    private SQLiteDatabase bd;
    private List<ShoppingList> shoppingLists;
    private List<StockShoppingList> stockShoppingLists;

    //---- Elementos de la Vista ----
    private ListView shoppingListsListView, stockShoppingListsListView;
    private FloatingActionButton addNewShoppingListBotton;

    //---- Metodos ----
    private void loadProductsLists() {
        // Cargamos las listas de la compra almacenadas en la BD
        ShoppingListDao daoSL = new ShoppingListDao(bd);
        shoppingLists = daoSL.findAll();

        // Cargamos las listas de la compra asociadas a inventarios almacenadas en la BD
        StockShoppingListDao daoStock = new StockShoppingListDao(bd);
        stockShoppingLists = daoStock.findAll();
    }

    private ListView initListView(final int list_view_id, List<?> list) {
        ListView listView = findViewById(list_view_id);
        ProductsListArrayAdapter<?> productsListArrayAdapter = new ProductsListArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(productsListArrayAdapter);

        // Indicamos la accion a realizar cuando se presiona sobre un elemento de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onShoppingListClick(view);
            }
        });
        return listView;
    }

    private void onShoppingListClick(final View v) {
        int productList_ID = Integer.parseInt(((TextView)v.findViewById(R.id.id_text)).getText().toString());

        Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
        intent.putExtra("ID", productList_ID);
        startActivity(intent);
    }

    //---- Metodos de la Actividad ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtenemos una conexion a la BD con el SingletonMap
        bd = BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext());

        // Cargamos las listas de la compra y las listas de la compra asociadas a inventario
        loadProductsLists();

        // Creamos el ArrayAdapter para las listas de elementos
        shoppingListsListView = initListView(R.id.shopping_lists_list_view,shoppingLists);
        stockShoppingListsListView = initListView(R.id.stock_shopping_lists_list_view,stockShoppingLists);

        ListUtils.setDynamicHeight(shoppingListsListView);
        ListUtils.setDynamicHeight(stockShoppingListsListView);

        // Configuramos lo necesario de la barra de herramientas
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.add_new_shopping_list_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProductsLists();
        shoppingListsListView = initListView(R.id.shopping_lists_list_view,shoppingLists);
        stockShoppingListsListView = initListView(R.id.stock_shopping_lists_list_view,stockShoppingLists);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products_lists ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ok = true;

        switch (item.getItemId()) {
            case R.id.delete_all:
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

    private static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}