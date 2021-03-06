
package com.example.shoppinglist.product_list_class.stock_shopping_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoppinglist.R;
import com.example.shoppinglist.product_list_class.stock.StockActivity;
import com.example.shoppinglist.product_list_class.shopping_list.ShoppingListFragment;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import bd.dao.StockShoppingListDao;
import model.Product;
import model.ProductsListClass;
import model.ShoppingList;
import model.StockShoppingList;

public class StockShoppingListFragment extends ShoppingListFragment {

    //---- Constructor ----
    public StockShoppingListFragment() {}

    @Override
    protected ListTableDao<? extends ShoppingList> getDaoProductList() {
        SQLiteDatabase bd = BaseDatosUtils.getWritableDatabaseConnection(getContext());
        return new StockShoppingListDao(bd);
    }

    //---- Fragment Methods ----
    public static StockShoppingListFragment newInstance(final ProductsListClass<Product> productList) {
        StockShoppingListFragment fragment = new StockShoppingListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCT_LIST,productList);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.getFragmentView(inflater,container,savedInstanceState);

        view.setBackground(getResources().getDrawable(R.drawable.wallpaper_stock_shopping_list,null));

        //---- View Elements ----
        Button btnGoAssociatedStock = (Button) view.findViewById(R.id.btnGoAssociatedStock);
        btnGoAssociatedStock.setVisibility(View.VISIBLE);
        btnGoAssociatedStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StockActivity.class);
                intent.putExtra("ID", ((StockShoppingList)productList).getAssociatedStockID());
                getContext().startActivity(intent);
            }
        });

        return view;
    }
}
