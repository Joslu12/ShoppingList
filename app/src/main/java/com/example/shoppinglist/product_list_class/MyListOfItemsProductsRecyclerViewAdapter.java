package com.example.shoppinglist.product_list_class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

import java.util.List;

import model.Product;

public abstract class MyListOfItemsProductsRecyclerViewAdapter<T extends Product> extends RecyclerView.Adapter<MyListOfItemsProductsRecyclerViewAdapter.ViewHolder<T>> {

    //---- Atributos ----
    private static ListOfProductsFragment parentFragment = null;
    private final List<T> values; // Lista con todos los productos a presentar en el fragmento

    //---- Constructor ----
    public MyListOfItemsProductsRecyclerViewAdapter(ListOfProductsFragment<T> fragment, List<T> objects) {
        parentFragment = fragment;
        values = objects;
    }

    //---- Metodos ----
    protected abstract ViewHolder<T> getCreateViewHolder(ViewGroup parent);
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mProduct = values.get(position);
        holder.mName.setText(holder.mProduct.getName());
        holder.mTargetAmount.setText(Integer.toString(holder.mProduct.getTargetAmount()));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    /**
     * Esta clase representa lo que se correspondería con una row de la lista, por lo que almacena la informacion necesaria
     */
    public static abstract class ViewHolder<T extends Product> extends RecyclerView.ViewHolder implements View.OnClickListener {

        //---- Attributes ----
        public final View mView;
        public final Context mContext;
        public final TextView mName, mTargetAmount;
        public final Button mDelete;
        public T mProduct;

        //---- Constructor ----
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContext = mView.getContext();
            mName = (TextView) view.findViewById(R.id.txtName);
            mTargetAmount = (TextView) view.findViewById(R.id.txtCurrentTargetAmount);
            mDelete = (Button) view.findViewById(R.id.btnDeleteProduct);
            if(parentFragment != null) {
                mDelete.setOnClickListener(this);
            } else {
                mDelete.setVisibility(View.INVISIBLE);
            }
        }

        //---- Methods ----
        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btnDeleteProduct:
                    parentFragment.openDeleteProductDialog(this.mProduct);
                    break;
            }
        }
    }
}
