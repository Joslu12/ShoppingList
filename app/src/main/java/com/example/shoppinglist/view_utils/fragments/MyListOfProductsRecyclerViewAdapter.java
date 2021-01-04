package com.example.shoppinglist.view_utils.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.StockProductDao;
import model.Product;
import model.StockProduct;

public class MyListOfProductsRecyclerViewAdapter extends MyListOfItemsProductsRecyclerViewAdapter<Product> {

    //---- Constructor ----
    public MyListOfProductsRecyclerViewAdapter(ListOfProductsFragment<Product> fragment, List<Product> objects) {
        super(fragment, objects);
    }

    //---- Methods ----
    @Override
    protected ViewHolder<Product> getCreateViewHolder(ViewGroup parent) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_product, parent, false));
    }

    //---- ViewHolder ----
    public static class ProductViewHolder extends MyListOfItemsProductsRecyclerViewAdapter.ViewHolder<Product> {

        public ProductViewHolder(View view) {
            super(view);
        }
    }
}
