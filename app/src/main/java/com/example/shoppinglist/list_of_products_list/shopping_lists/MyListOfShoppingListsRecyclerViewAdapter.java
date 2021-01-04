package com.example.shoppinglist.list_of_products_list.shopping_lists;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;
import com.example.shoppinglist.list_of_products_list.MyListOfProductsListRecyclerViewAdapter;
import com.example.shoppinglist.product_list_class.shopping_list.ShoppingListActivity;

import java.util.List;

import model.ShoppingList;

public class MyListOfShoppingListsRecyclerViewAdapter extends MyListOfProductsListRecyclerViewAdapter<ShoppingList> {

    //---- Constructor ----
    public MyListOfShoppingListsRecyclerViewAdapter(List<ShoppingList> objects) {
        super(objects);
    }

    //---- Methods ----
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected ShoppingListViewHolder getCreateViewHolder(ViewGroup parent) {
        // Cargamos la vista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_list_of_products, parent, false);

        // Establecemos el estilo de los botones
        view.findViewById(R.id.btnItemProductList).setBackground(parent.getResources().getDrawable(R.drawable.list_item_button_shopping_list,null));

        return new ShoppingListViewHolder(view);
    }

    //---- ViewHolder ----
    public static class ShoppingListViewHolder extends MyListOfProductsListRecyclerViewAdapter.ViewHolder<ShoppingList> {

        //---- Constructor ----
        public ShoppingListViewHolder(View view) {
            super(view);
        }

        //---- Methods ----
        @Override
        protected Class<?> getIntentActivityClass() {
            return ShoppingListActivity.class;
        }
    }
}
