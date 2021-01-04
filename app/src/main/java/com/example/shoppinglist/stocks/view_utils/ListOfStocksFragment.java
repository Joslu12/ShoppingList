package com.example.shoppinglist.stocks.view_utils;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;
import com.example.shoppinglist.stocks.StockActivity;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateStockDialog;
import com.example.shoppinglist.view_utils.fragments.ListOfProductsListFragment;

import bd.dao.ListTableDao;
import bd.dao.StockDao;
import model.Stock;

public class ListOfStocksFragment extends ListOfProductsListFragment<Stock> {

    //---- Constructor ----
    public ListOfStocksFragment(){}

    //---- Fragment Methods ----
    @Override
    protected ListTableDao<Stock> getProductListDao(SQLiteDatabase conexion) {
        return new StockDao(conexion);
    }

    @Override
    protected MyListOfStocksRecyclerViewAdapter getRecyclerView() {
        return new MyListOfStocksRecyclerViewAdapter(this.listOfProductList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        view.setBackgroundColor(getResources().getColor(R.color.stock_backgroud_color,null));

        return view;
    }

    //---- Methods ----
    @Override
    public void openCreateListOfProductsDialog() {
        CreateStockDialog dialog = new CreateStockDialog(this);
        dialog.show(getParentFragmentManager(),"Create Stock");
    }

    @Override
    protected Class<?> getIntentActivityClass() {
        return StockActivity.class;
    }

    @Override
    protected Class<?> getIntentEntityClass() {
        return Stock.class;
    }
}
