package com.example.shoppinglist.list_of_products_list.stock_shopping_lists;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;
import com.example.shoppinglist.app_error_handling.AppError;
import com.example.shoppinglist.list_of_products_list.ListOfProductsListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import bd.dao.ListTableDao;
import bd.dao.StockShoppingListDao;
import model.StockShoppingList;

import static com.example.shoppinglist.app_error_handling.AppErrorHelper.CodeErrors;

public class ListOfStockShoppingListsFragment extends ListOfProductsListFragment<StockShoppingList> {

    //---- Constructor ----
    public ListOfStockShoppingListsFragment(){}

    //---- Fragment Methods ----
    @Override
    protected ListTableDao<StockShoppingList> getProductListDao(SQLiteDatabase conexion) {
        return new StockShoppingListDao(conexion);
    }

    @Override
    protected MyListOfStockShoppingListsRecyclerViewAdapter getRecyclerView() {
        return new MyListOfStockShoppingListsRecyclerViewAdapter(this.listOfProductList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Si se trata de un StockShoppingLists Fragment hacemos invisible el FloatingButton
        ((FloatingActionButton) view.findViewById(R.id.btnAddNewProductsList)).setVisibility(View.GONE);

        // Cambiamos el color de fondo
        view.setBackgroundColor(getResources().getColor(R.color.stock_shopping_list_backgroud_color,null));

        return view;
    }

    //---- Methods ----
    @Override
    public void openCreateListOfProductsDialog() {
        // Nunca va a ser llamado
        new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
    }

    @Override
    protected Class<?> getIntentActivityClass() {
        // Nunca va a ser llamado
        new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        return null;
    }

    @Override
    protected Class<?> getIntentEntityClass() {
        // Nunca va a ser llamado
        new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        return null;
    }
}
