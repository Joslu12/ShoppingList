package com.example.shoppinglist.view_utils.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.shoppinglists_activities.ShoppingListActivity;
import com.example.shoppinglist.stocks_activities.StockActivity;

import java.util.List;

import model.ProductsListClass;
import model.ShoppingList;
import model.Stock;
import model.StockShoppingList;

public class MyListOfProductsRecyclerViewAdapter extends RecyclerView.Adapter<MyListOfProductsRecyclerViewAdapter.ViewHolder> {

    //---- Atributos ----
    private final List<ProductsListClass<?>> values; // Lista con todos los listados de productos a presentar en el fragmento

    //---- Constructor ----
    public MyListOfProductsRecyclerViewAdapter(List<ProductsListClass<?>> objects) {
        values = objects;
    }

    //---- Metodos ----
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_list_of_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mProductList = values.get(position);
        holder.mContentView.setText(values.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    /**
     * Esta clase representa lo que se correspondería con una row de la lista, por lo que almacena la informacion necesaria
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //---- Attributes ----
        public final View mView;
        public final Context mContext;
        public final TextView mContentView;
        public ProductsListClass<?> mProductList;

        //---- Constructor ----
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContext = mView.getContext();
            mContentView = (TextView) view.findViewById(R.id.content);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        //---- Methods ----
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            ProductsListClass<?> productList = mProductList;

            Intent intent;
            if(productList instanceof ShoppingList) {
                intent = new Intent(mContext, ShoppingListActivity.class);
            } else if(productList instanceof StockShoppingList) {
                intent = new Intent(view.getContext(), ShoppingListActivity.class);
            } else if(productList instanceof Stock) {
                intent = new Intent(view.getContext(), StockActivity.class);
            } else {
                throw new RuntimeException(); //TODO:
            }

            intent.putExtra("ID", productList.getID());
            mContext.startActivity(intent);
        }
    }
}
