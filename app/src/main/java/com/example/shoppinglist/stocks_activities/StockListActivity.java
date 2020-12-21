package com.example.shoppinglist.stocks_activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.shoppinglist.view_utils.MyListOfProductsRecyclerViewAdapter;
import com.example.shoppinglist.R;
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
import android.widget.Toast;

import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.StockDao;
import com.example.shoppinglist.view_utils.CreateDialogFragment;
import model.Stock;

public class StockListActivity extends AppCompatActivity {
    //---- Constantes y Definiciones ----

    //---- Atributos ----
    private SQLiteDatabase bd;
    private List<Stock> stockLists;

    //---- Elementos de la Vista ----
    private ListView stockListView;
    private FloatingActionButton addNewShoppingListBotton;

    //---- Metodos ----
    private void loadStockLists(){
        //Cargamos las listas de inventario almacenadas en la BD
        StockDao daoSL = new StockDao(bd);
        stockLists = daoSL.findAll();
    }

/*    private ListView initListView(final int list_view_id, List<?> list) {
        ListView listView = findViewById(list_view_id);
        MyListOfProductsRecyclerViewAdapter<?> myListOfProductsRecyclerViewAdapter = new MyListOfProductsRecyclerViewAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(myListOfProductsRecyclerViewAdapter);

        // Indicamos la accion a realizar cuando se presiona sobre un elemento de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stock stock = (Stock) parent.getItemAtPosition(position);
                onStockClick(stock.getID());
            }
        });
        return listView;
    }

    private void onStockClick(final int stockID){
        Intent intent = new Intent(getApplicationContext(), StockActivity.class);
        intent.putExtra("ID",stockID);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtenemos una conexion a la BD con el SingletonMap
        bd = BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext());

        // Cargamos las listas de inventario
        loadStockLists();

        //Creamos el ArrayAdapter para la listas
        stockListView= initListView(R.id.stock_lists_view,stockLists);

        ListUtils.setDynamicHeight(stockListView);

        // Configuramos lo necesario de la barra de herramientas
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.add_new_shopping_list_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }


        });
    }

    public void openDialog() {
        CreateDialogFragment dialog = new CreateDialogFragment();
        dialog.show(getSupportFragmentManager(), "Create a new Stock");
    }

    @Override
    public void applyText(String name) {
        Stock s = new Stock(name);
        stockLists.add(s);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStockLists();
        stockListView = initListView(R.id.stock_lists_view,stockLists);
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




    //TODO: Refactor to another class
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
    }*/
}

