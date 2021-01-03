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

import model.Product;
import model.StockProduct;

public class MyListOfProductsRecyclerViewAdapter<T extends Product> extends RecyclerView.Adapter<MyListOfProductsRecyclerViewAdapter.ViewHolder> {

    //---- Atributos ----
    private static ListOfProductsFragment parentFragment = null;
    private static Class productClass = null;
    private final List<T> values; // Lista con todos los productos a presentar en el fragmento

    //---- Constructor ----
    public MyListOfProductsRecyclerViewAdapter(ListOfProductsFragment<T> fragment, Class productClass, List<T> objects) {
        this.parentFragment = fragment;
        this.productClass = productClass;
        values = objects;
    }

    //---- Metodos ----
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(productClass.equals(Product.class)) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_adapter_product, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_adapter_stock_product, parent, false);
        }
        return new ViewHolder<T>(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mProduct = values.get(position);
        holder.mName.setText(holder.mProduct.getName());
        holder.mTargetAmount.setText(Integer.toString(holder.mProduct.getTargetAmount()));
        if(holder.mProduct instanceof StockProduct) {
            holder.mTargetAmount.setText(String.format(holder.mContext.getResources().getString(R.string.current_target_amount_difference),
                    ((StockProduct)holder.mProduct).getCurrentAmount(),holder.mProduct.getTargetAmount()));
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    /**
     * Esta clase representa lo que se correspondería con una row de la lista, por lo que almacena la informacion necesaria
     */
    public static class ViewHolder<T extends Product> extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        @Override
        public void onClick(View view) {
            parentFragment.openDeleteProductDialog(this.mProduct);
        }
    }
}
