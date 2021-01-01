package com.example.shoppinglist.shoppinglists;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateProductDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteProductDialog;
import com.example.shoppinglist.view_utils.fragments.ListOfProductsFragment;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import bd.dao.ShoppingListDao;
import bd.dao.StockDao;
import model.Product;
import model.ProductsListClass;
import model.StockProduct;

public class ShoppingListFragment extends ListOfProductsFragment<Product> {

    //---- Constructor ----
    public ShoppingListFragment() {}

    @Override
    protected ListTableDao getDaoProductList() {
        SQLiteDatabase bd = BaseDatosUtils.getWritableDatabaseConnection(getContext());
        return new ShoppingListDao(bd);
    }

    //---- Fragment Methods ----
    public static ShoppingListFragment newInstance(final ProductsListClass productList) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCT_LIST,productList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_shopping_list,container,false);
    }

    //---- Methods ----
    @Override
    protected Class getClassTypeOfProduct() {
        return Product.class;
    }

    @Override
    protected String getStringTypeOfProduct() {
        return getResources().getString(R.string.the_product);
    }

    @Override
    protected CreateEntityDialog<Product> getCreateProductDialog() {
        return new CreateProductDialog(this);
    }

    @Override
    protected DeleteEntityDialog<Product> getDeleteProductDialog(Product product) {
        return new DeleteProductDialog(this,product);
    }
}
