package com.example.shoppinglist.view_utils;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.shoppinglists_activities.ShoppingListActivity;
import com.example.shoppinglist.stocks_activities.StockActivity;
import com.example.shoppinglist.view_utils.dialogs.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.CreateListOfProductsDialog;
import com.example.shoppinglist.view_utils.dialogs.CreateShoppingListDialog;
import com.example.shoppinglist.view_utils.dialogs.CreateStockDialog;

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

public class ListOfProductsListFragment extends Fragment implements CreateEntityDialog.CreateEntityDialogListener {

    //---- Constants and Definitions ----
    protected static final String PRODUCT_LIST_CLASS = "product-list-class";

    //---- View Elements ----
    private RecyclerView recyclerView;

    //---- Attributes ----
    private Class productListClass;
    private ListTableDao dao;
    private List<ProductsListClass<?>> list;

    //---- Constructor ----
    public ListOfProductsListFragment() {}

    //---- Fragment Methods ----
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
            productListClass = (Class) getArguments().getSerializable(PRODUCT_LIST_CLASS);
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

    //---- Methods ----
    public void openCreateListOfProductsDialog() {
        CreateListOfProductsDialog dialog;
        String tag = "";
        if(productListClass.equals(ShoppingList.class)) {
            dialog = new CreateShoppingListDialog(this);
            tag = "Create new Shopping List";
        } else if (productListClass.equals(Stock.class)) {
            dialog = new CreateStockDialog(this);
            tag = "Create new Stock";
        } else {
            throw new RuntimeException(); // TODO:
        }
        dialog.show(getParentFragmentManager(), tag);
    }

    @Override
    public void onDialogCreateClick(CreateEntityDialog dialog) {
        if (productListClass.equals(ShoppingList.class) || productListClass.equals(Stock.class)) {
            ProductsListClass entity = ((CreateListOfProductsDialog) dialog).getEntityToCreate();
            int opResult = dao.insert(entity);
            if(opResult == -1) {
                Toast.makeText(getContext(),dialog.getErrorMsg(entity),Toast.LENGTH_LONG).show();
            } else {
                // Mostramos el mensaje de exito y cerramos el dialog
                Toast.makeText(getContext(), dialog.getSuccessMsg(entity.getName()), Toast.LENGTH_LONG).show();
                dialog.dismiss();

                // Saltamos a la actividad del nuevo productList
                Class activityClass = null;
                if(entity.getClass().equals(Stock.class)) {
                    activityClass = StockActivity.class;
                } else if (entity.getClass().equals(ShoppingList.class)) {
                    activityClass = ShoppingListActivity.class;
                } else {
                    //TODO:
                    throw new RuntimeException();
                }
                Intent intent = new Intent(getContext(), activityClass);
                intent.putExtra("ID", entity.getID());
                getContext().startActivity(intent);
            }
        } else {
            throw new RuntimeException(); // TODO:
        }
    }
}
