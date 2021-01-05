package com.example.shoppinglist.shopping_summary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.MainActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.app_error_handling.AppError;
import com.example.shoppinglist.app_error_handling.AppErrorHelper.CodeErrors;
import com.example.shoppinglist.product_list_class.MyListOfProductsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import bd.dao.ShoppingListDao;
import bd.dao.StockDao;
import bd.dao.StockShoppingListDao;
import model.EndPurchaseOperation;
import model.Product;
import model.ShoppingList;
import model.ShoppingListException;
import model.Stock;
import model.StockShoppingList;

public class ShoppingSummaryActivity<T extends ShoppingList> extends AppCompatActivity {

    //---- Attributes ----
    private T shoppingList;
    private ListTableDao<T> dao;

    //---- View Elements ----
    private RecyclerView purchasedProductsRecyclerView, productsToPurchaseRecyclerView;

    //--- Activity Methods ----
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getSerializableExtra("SHOPPING_LIST") != null) {
            shoppingList = ((T) getIntent().getSerializableExtra("SHOPPING_LIST"));
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),this);
        }

        if(shoppingList instanceof StockShoppingList) {
            dao = (ListTableDao<T>) new StockShoppingListDao(BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext()));
        } else {
            dao = (ListTableDao<T>) new ShoppingListDao(BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext()));
        }

        // Se corresponde con el productsToPurchaseRecyclerView
        View recyclerViewElement = this.findViewById(R.id.purchasedProductsRecyclerView);
        // Set the adapter
        if (recyclerViewElement instanceof RecyclerView) {
            purchasedProductsRecyclerView = (RecyclerView) recyclerViewElement;
            purchasedProductsRecyclerView.setAdapter(new MyListOfProductsRecyclerViewAdapter(null,listFromIterator(shoppingList.getPurchasedProducts())));
            purchasedProductsRecyclerView.addItemDecoration(new DividerItemDecoration(purchasedProductsRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }

        // Se corresponde con el productsToPurchaseRecyclerView
        recyclerViewElement = this.findViewById(R.id.productsToPurchaseRecyclerView);
        // Set the adapter
        if (recyclerViewElement instanceof RecyclerView) {
            productsToPurchaseRecyclerView = (RecyclerView) recyclerViewElement;
            productsToPurchaseRecyclerView.setAdapter(new MyListOfProductsRecyclerViewAdapter(null,listFromIterator(shoppingList.getProductsToPurchase())));
            productsToPurchaseRecyclerView.addItemDecoration(new DividerItemDecoration(productsToPurchaseRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }

        if(shoppingList instanceof StockShoppingList) {
            showPropertlyButtons();
        }
    }

    //---- Methods ----
    private void showPropertlyButtons() {
        ((Button) findViewById(R.id.btnDiscard)).setVisibility(View.INVISIBLE);
        ((Button) findViewById(R.id.btnSave)).setText(getResources().getString(R.string.update) + " " + getResources().getString(R.string.stock));
        ((Button) findViewById(R.id.btnSave)).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ((Button) findViewById(R.id.btnUpdate)).setVisibility(View.INVISIBLE);
    }

    private <E extends Product> List<E> listFromIterator(Iterator<E> iterator) {
        List<E> result = new ArrayList<E>();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public void onClick(View view) {
        T result = null;

        // Eliminamos la lista de la compra de la bd
        dao.remove(shoppingList);

        try {
            switch (view.getId()) {
                case R.id.btnDiscard:
                    result = (T) shoppingList.finishSummary(EndPurchaseOperation.DISCARD);
                    break;

                case R.id.btnSave:
                    result = (T) shoppingList.finishSummary(EndPurchaseOperation.SAVE_LIST);
                    break;

                case R.id.btnUpdate:
                    result = (T) shoppingList.finishSummary(EndPurchaseOperation.UPDATE_LIST);
                    break;
            }
        } catch (ShoppingListException ex) {
            ex.printStackTrace(System.err);
        }

        // Insertamos una nueva lista si no ha sido eliminada
        if(result != null) {
            dao.insert(result);
        } else if (shoppingList instanceof StockShoppingList) {
            // Si se trata de la lista de la compra de un inventario
            StockDao stockDao = new StockDao(BaseDatosUtils.getWritableDatabaseConnection(getApplicationContext()));
            Stock stock = ((StockShoppingList) shoppingList).getAssociatedStock();

            stockDao.update(stock);
        }

        // Volvemos a la pantalla de inicio
        finishAffinity(); // Eliminamos las actividades del Stack
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        getApplicationContext().startActivity(intent);
    }

}
