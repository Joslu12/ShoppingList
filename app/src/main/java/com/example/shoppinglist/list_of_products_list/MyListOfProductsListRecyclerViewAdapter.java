package com.example.shoppinglist.list_of_products_list;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

import java.util.List;

import model.ProductsListClass;

public abstract class MyListOfProductsListRecyclerViewAdapter<T extends ProductsListClass<?>> extends RecyclerView.Adapter<MyListOfProductsListRecyclerViewAdapter.ViewHolder<T>> {

    //---- Attributes ----
    private final List<T> values; // Lista con todos los listados de productos a presentar en el fragmento

    //---- Constructor ----
    public MyListOfProductsListRecyclerViewAdapter(List<T> objects) {
        values = objects;
    }

    //---- Methods ----
    protected abstract ViewHolder<T> getCreateViewHolder(ViewGroup parent);
    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mProductList = values.get(position);
        holder.mProductListName.setText(values.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    /**
     * Esta clase representa lo que se correspondería con una row de la lista, por lo que almacena la informacion necesaria
     */
    public abstract static class ViewHolder<T extends ProductsListClass<?>> extends RecyclerView.ViewHolder implements View.OnClickListener {

        //---- Attributes ----
        public final View mView;
        public final Context mContext;
        public final TextView mProductListName;
        public T mProductList;

        //---- Constructor ----
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContext = mView.getContext();
            mProductListName = (TextView) view.findViewById(R.id.txtProductListName);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        //---- Methods ----
        @Override
        public String toString() {
            return super.toString() + " '" + mProductListName.getText() + "'";
        }

        //Class<ProductListClassActivity<T>>
        protected abstract Class<?> getIntentActivityClass();
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext,getIntentActivityClass());
            intent.putExtra("ID", mProductList.getID());
            mContext.startActivity(intent);
        }
    }
}
