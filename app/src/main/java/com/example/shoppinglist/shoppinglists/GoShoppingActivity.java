package com.example.shoppinglist.shoppinglists;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.MainActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.app_error_handling.AppError;
import com.example.shoppinglist.app_error_handling.AppErrorHelper.CodeErrors;

import model.ShoppingList;

public class GoShoppingActivity<T extends ShoppingList> extends AppCompatActivity {


    //---- Attributes ----
    private T shoppingList;

    //---- View Elements ----
    private RecyclerView recyclerView;

    //--- Activity Methods ----
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_shopping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getSerializableExtra("SHOPPING_LIST") != null) {
            shoppingList = ((T) getIntent().getSerializableExtra("SHOPPING_LIST"));
            shoppingList.initPurchasing(); // Comenzamos la compra
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),this);
        }

        // Se corresponde con el RecyclerView
        View recyclerViewElement = this.findViewById(R.id.recyclerView);
        // Set the adapter
        if (recyclerViewElement instanceof RecyclerView) {
            recyclerView = (RecyclerView) recyclerViewElement;
            recyclerView.setAdapter(new MyGoShoppingRecyclerViewAdapter(shoppingList, shoppingList.getProductsInsideAList()));
        }
    }

    //---- Methods ----
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnFinishShopping:
                // Finalizamos la compra y mostramos un resumen de la compra
                shoppingList.finishPurchasing();
                Intent intent = new Intent(getApplicationContext(), ShoppingSummaryActivity.class);
                intent.putExtra("SHOPPING_LIST",shoppingList);
                getApplicationContext().startActivity(intent);
                break;
        }
    }

}
