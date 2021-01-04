package com.example.shoppinglist.shoppinglists.view_utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;
import com.example.shoppinglist.shoppinglists.StockShoppingListActivity;
import com.example.shoppinglist.view_utils.fragments.MyListOfProductsListRecyclerViewAdapter;

import java.util.List;

import model.StockShoppingList;

public class MyListOfStockShoppingListsRecyclerViewAdapter extends MyListOfProductsListRecyclerViewAdapter<StockShoppingList> {

    //---- Constructor ----
    public MyListOfStockShoppingListsRecyclerViewAdapter(List<StockShoppingList> objects) {
        super(objects);
    }

    //---- Methods ----
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected StockShoppingListViewHolder getCreateViewHolder(ViewGroup parent) {
        // Cargamos la vista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_list_of_products, parent, false);

        // Establecemos el estilo de los botones
        view.findViewById(R.id.btnItemProductList).setBackground(parent.getResources().getDrawable(R.drawable.list_item_button_stock_shopping_list,null));

        return new StockShoppingListViewHolder(view);
    }

    //---- ViewHolder ----
    public static class StockShoppingListViewHolder extends MyListOfProductsListRecyclerViewAdapter.ViewHolder<StockShoppingList> {

        //---- Constructor ----
        public StockShoppingListViewHolder(View view) {
            super(view);
        }

        //---- Methods ----
        @Override
        protected Class<?> getIntentActivityClass() {
            return StockShoppingListActivity.class;
        }
    }
}
