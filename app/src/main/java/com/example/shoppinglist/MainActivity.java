package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.shoppinglists_activities.ShoppingListActivity;
import com.example.shoppinglist.shoppinglists_activities.ShoppingListListActivity;
import com.example.shoppinglist.stocks_activities.StockListActivity;

public class MainActivity extends AppCompatActivity {

    //---- Elementos de la Vista ----
    private TextView title;
    private Button stocksBtn, shoppingListsBtn;

    //---- Metodos ----
    public void buttonClicked(View v) {
        switch(v.getId()) {
            case R.id.btnMainShoppingLists:
                startActivity(new Intent(getApplicationContext(), ShoppingListListActivity.class));
                break;

            case R.id.btnMainStocks:
                startActivity(new Intent(getApplicationContext(), StockListActivity.class));
                break;
        }
    }

    //---- Metodos de la Actividad ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Cargamos los elementos de la vista
        title = findViewById(R.id.textViewTitle);
        shoppingListsBtn = findViewById(R.id.btnMainShoppingLists);
        stocksBtn = findViewById(R.id.btnMainStocks);

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
}