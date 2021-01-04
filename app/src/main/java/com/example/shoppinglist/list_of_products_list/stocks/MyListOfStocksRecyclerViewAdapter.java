package com.example.shoppinglist.list_of_products_list.stocks;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;
import com.example.shoppinglist.list_of_products_list.MyListOfProductsListRecyclerViewAdapter;
import com.example.shoppinglist.product_list_class.stock.StockActivity;

import java.util.List;

import model.Stock;

public class MyListOfStocksRecyclerViewAdapter extends MyListOfProductsListRecyclerViewAdapter<Stock> {

    //---- Constructor ----
    public MyListOfStocksRecyclerViewAdapter(List<Stock> objects) {
        super(objects);
    }

    //---- Methods ----
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected StockViewHolder getCreateViewHolder(ViewGroup parent) {
        // Cargamos la vista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_list_of_products, parent, false);

        // Establecemos el estilo de los botones
        view.findViewById(R.id.btnItemProductList).setBackground(parent.getResources().getDrawable(R.drawable.list_item_button_stock,null));

        // Creamos el ViewHolder
        return new MyListOfStocksRecyclerViewAdapter.StockViewHolder(view);
    }

    //---- ViewHolder ----
    public static class StockViewHolder extends MyListOfProductsListRecyclerViewAdapter.ViewHolder<Stock> {

        //---- Constructor ----
        public StockViewHolder(View view) {
            super(view);
        }

        //---- Methods ----
        @Override
        protected Class<?> getIntentActivityClass() {
            return StockActivity.class;
        }
    }
}
