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

public class MyListOfStockProductsRecyclerViewAdapter extends MyListOfItemsProductsRecyclerViewAdapter<StockProduct> {

    //---- Constructor ----
    public MyListOfStockProductsRecyclerViewAdapter(ListOfProductsFragment<StockProduct> fragment, List<StockProduct> objects) {
        super(fragment, objects);
    }

    //---- Methods ----
    @Override
    protected ViewHolder<StockProduct> getCreateViewHolder(ViewGroup parent) {
        return new StockProductViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_stock_product, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.mTargetAmount.setText(String.format(holder.mContext.getResources().getString(R.string.current_target_amount_difference),
                ((StockProduct)holder.mProduct).getCurrentAmount(),holder.mProduct.getTargetAmount()));
        // Actualizamos el estado de los botones
        ((StockProductViewHolder)holder).updateButtonsVisibility();
    }

    //---- ViewHolder ----
    public static class StockProductViewHolder extends MyListOfItemsProductsRecyclerViewAdapter.ViewHolder<StockProduct> {

        //---- Attributes ----
        public final Button mBtnAddUnit, mBtnRemoveUnit;
        private final StockProductDao dao;

        //---- Constructor ----
        public StockProductViewHolder(View view) {
            super(view);

            dao = new StockProductDao(BaseDatosUtils.getWritableDatabaseConnection(mContext));

            mBtnAddUnit = (Button) view.findViewById(R.id.btnAddUnit);
            mBtnAddUnit.setOnClickListener(this);
            mBtnRemoveUnit = (Button) view.findViewById(R.id.btnRemoveUnit);
            mBtnRemoveUnit.setOnClickListener(this);
        }

        //---- Methods ----
        public void updateButtonsVisibility() {
            // Actualizamos el estado del boton Remove
            if(mProduct.getCurrentAmount() == 0) {
                mBtnRemoveUnit.setVisibility(View.INVISIBLE);
            } else if(mProduct.getCurrentAmount() == 1) {
                mBtnRemoveUnit.setVisibility(View.VISIBLE);
            }

            // Actualizamos el estado del boton Add
            if(mProduct.getCurrentAmount() == mProduct.getTargetAmount()) {
                mBtnAddUnit.setVisibility(View.INVISIBLE);
            } else if(mProduct.getCurrentAmount() == (mProduct.getTargetAmount() - 1)) {
                mBtnAddUnit.setVisibility(View.VISIBLE);
            }
        }

        private void updateTextView() {
            mTargetAmount.setText(String.format(mContext.getResources().getString(R.string.current_target_amount_difference),
                    ((StockProduct)mProduct).getCurrentAmount(),mProduct.getTargetAmount()));
        }

        @Override
        public void onClick(View view) {
            // Para el caso de que se pulse sobre Delete
            super.onClick(view);

            switch(view.getId()) {
                case R.id.btnAddUnit:
                    // Actualizamos el estado del producto
                    mProduct.increaseCurrentAmount();
                    dao.update((StockProduct)mProduct);

                    // Actualizamos la vista del ViewHolder
                    updateButtonsVisibility();
                    updateTextView();

                    break;

                case R.id.btnRemoveUnit:
                    // Actualizamos el estado del producto
                    mProduct.decreaseCurrentAmount();
                    dao.update((StockProduct)mProduct);

                    // Actualizamos la vista del ViewHolder
                    updateButtonsVisibility();
                    updateTextView();

                    break;
            }
        }
    }
}
