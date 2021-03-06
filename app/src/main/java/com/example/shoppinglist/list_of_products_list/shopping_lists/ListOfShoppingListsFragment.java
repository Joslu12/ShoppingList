package com.example.shoppinglist.list_of_products_list.shopping_lists;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppinglist.R;
import com.example.shoppinglist.list_of_products_list.ListOfProductsListFragment;
import com.example.shoppinglist.product_list_class.shopping_list.ShoppingListActivity;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateShoppingListDialog;

import bd.dao.ListTableDao;
import bd.dao.ShoppingListDao;
import model.ShoppingList;

public class ListOfShoppingListsFragment extends ListOfProductsListFragment<ShoppingList> {

    //---- Constructor ----
    public ListOfShoppingListsFragment(){}

    //---- Fragment Methods ----
    @Override
    protected ListTableDao<ShoppingList> getProductListDao(SQLiteDatabase conexion) {
        return new ShoppingListDao(conexion);
    }

    @Override
    protected MyListOfShoppingListsRecyclerViewAdapter getRecyclerView() {
        return new MyListOfShoppingListsRecyclerViewAdapter(this.listOfProductList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        view.setBackgroundColor(getResources().getColor(R.color.shopping_list_backgroud_color,null));

        return view;
    }

    //---- Methods ----
    @Override
    public void openCreateListOfProductsDialog() {
        CreateShoppingListDialog dialog = new CreateShoppingListDialog(this);
        dialog.show(getParentFragmentManager(),"Create Shopping List");
    }

    @Override
    protected Class<?> getIntentActivityClass() {
        return ShoppingListActivity.class;
    }
}
