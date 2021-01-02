package com.example.shoppinglist.shoppinglists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

import java.util.List;

import model.Product;
import model.ShoppingList;
import model.ShoppingListException;

public class MyGoShoppingRecyclerViewAdapter extends RecyclerView.Adapter<MyGoShoppingRecyclerViewAdapter.ViewHolder> {

    //---- Attributes ----
    private static ShoppingList shoppingList = null;
    private final List<Product> values;

    //---- Constructor ----
    public MyGoShoppingRecyclerViewAdapter(ShoppingList shoppingList, List<Product> values) {
        this.shoppingList = shoppingList;
        this.values = values;
    }

    //---- Methods ----
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_view_adapter_go_shopping, parent, false);
        return new ViewHolder(view);
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
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //---- Attributes ----
        public final TextView mName, mTargetAmount;
        private final CheckBox mCheckBox;
        public Product mProduct;

        //---- Constructor ----
        public ViewHolder(View view) {
            super(view);
            mName = (TextView) view.findViewById(R.id.txtName);
            mTargetAmount = (TextView) view.findViewById(R.id.txtTargetAmount);
            mCheckBox = (CheckBox) view.findViewById(R.id.checkBox);
            mCheckBox.setOnClickListener(this);
        }

        //---- Methods ----
        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            // Marcamos o desmarcamos el producto de la lista de la compra
            try {
                if (mCheckBox.isChecked()) {
                    shoppingList.checkProduct(mProduct);
                } else {
                    shoppingList.uncheckProduct(mProduct);
                }
            } catch (ShoppingListException ex) {}
        }
    }
}
