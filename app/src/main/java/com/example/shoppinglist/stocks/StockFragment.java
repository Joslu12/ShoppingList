package com.example.shoppinglist.stocks;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateStockProductDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteStockProductDialog;
import com.example.shoppinglist.view_utils.fragments.ListOfProductsFragment;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import bd.dao.StockDao;
import model.ProductsListClass;
import model.Stock;
import model.StockProduct;

public class StockFragment extends ListOfProductsFragment<StockProduct> {

    //---- Constructor ----
    public StockFragment() {}

    @Override
    protected ListTableDao<Stock> getDaoProductList() {
        SQLiteDatabase bd = BaseDatosUtils.getWritableDatabaseConnection(getContext());
        return new StockDao(bd);
    }

    //---- Fragment Methods ----
    public static StockFragment newInstance(final ProductsListClass<StockProduct> productList) {
        StockFragment fragment = new StockFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCT_LIST,productList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_stock,container,false);
    }

    //---- Methods ----
    @Override
    protected void updateFragment() {}

    @Override
    protected Class getClassTypeOfProduct() {
        return StockProduct.class;
    }

    @Override
    protected String getStringTypeOfProduct() {
        return getResources().getString(R.string.the_stock_product);
    }

    @Override
    protected CreateEntityDialog<StockProduct> getCreateProductDialog() {
        return new CreateStockProductDialog(this);
    }

    @Override
    protected DeleteEntityDialog<StockProduct> getDeleteProductDialog(StockProduct product) {
        return new DeleteStockProductDialog(this,product);
    }
}
