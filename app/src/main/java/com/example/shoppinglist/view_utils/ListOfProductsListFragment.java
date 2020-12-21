package com.example.shoppinglist.view_utils;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import bd.dao.ShoppingListDao;
import bd.dao.StockDao;
import bd.dao.StockShoppingListDao;
import model.ProductsListClass;
import model.ShoppingList;
import model.Stock;
import model.StockShoppingList;

public class ListOfProductsListFragment extends Fragment {

    //---- Definiciones y Constantes ----
    private static final String PRODUCT_LIST_CLASS = "product-list-class";

    //---- Elementos de la Vista ----
    private RecyclerView recyclerView;

    //---- Atributos ----
    private ListTableDao dao;
    private List<ProductsListClass<?>> list;

    //---- Constructor ----
    public ListOfProductsListFragment() {}

    //---- Metodos del Fragmento ----
    public static ListOfProductsListFragment newInstance(final Class productListClass) {
        ListOfProductsListFragment fragment = new ListOfProductsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCT_LIST_CLASS,productListClass);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase bd = BaseDatosUtils.getWritableDatabaseConnection(getContext());

        if (getArguments() != null) {
            Class productListClass = (Class) getArguments().getSerializable(PRODUCT_LIST_CLASS);
            if(productListClass.equals(ShoppingList.class)) {
                dao = new ShoppingListDao(bd);
            } else if (productListClass.equals(Stock.class)) {
                dao = new StockDao(bd);
            } else if (productListClass.equals(StockShoppingList.class)) {
                dao = new StockShoppingListDao(bd);
            } else {
                throw new RuntimeException(); // TODO:
            }
        }

        // Cargamos todos los listados de productos de la base de datos segun el dao
        list = dao.findAll();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(recyclerView != null) {
            list = dao.findAll();
            recyclerView.setAdapter(new MyListOfProductsRecyclerViewAdapter(list));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragmento completo
        View view = inflater.inflate(R.layout.fragment_list_of_product_list, container, false);

        // Se corresponde con el RecyclerView
        View recyclerViewElement = view.findViewById(R.id.recyclerView);
        // Set the adapter
        if (recyclerViewElement instanceof RecyclerView) {
            recyclerView = (RecyclerView) recyclerViewElement;
            recyclerView.setAdapter(new MyListOfProductsRecyclerViewAdapter(list));
        }

        return view;
    }

    //---- Metodos ----
    public void addNewListOfProducts() {
        //TODO: Add new ProductList con Dialog
    }
}
