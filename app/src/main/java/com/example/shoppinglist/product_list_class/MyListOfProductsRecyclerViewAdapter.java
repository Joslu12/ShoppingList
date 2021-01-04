package com.example.shoppinglist.product_list_class;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;

import java.util.List;

import model.Product;

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
