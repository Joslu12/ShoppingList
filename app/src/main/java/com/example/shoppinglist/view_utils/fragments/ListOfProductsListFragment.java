package com.example.shoppinglist.view_utils.fragments;

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
import com.example.shoppinglist.app_error_handling.AppError;
import static com.example.shoppinglist.app_error_handling.AppErrorHelper.CodeErrors;
import com.example.shoppinglist.app_error_handling.AppException;
import com.example.shoppinglist.shoppinglists.ShoppingListActivity;
import com.example.shoppinglist.stocks.StockActivity;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateListOfProductsDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateShoppingListDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateStockDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
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
                new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
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
            recyclerView.setAdapter(new MyListOfProductsListRecyclerViewAdapter(list));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragmento completo
        View view = inflater.inflate(R.layout.fragment_list_of_product_list, container, false);

        // Si se trata de un StockShoppingLists Fragment hacemos invisible el FloatingButton
        if(productListClass.equals(StockShoppingList.class))
            ((FloatingActionButton) view.findViewById(R.id.btnAddNewProductsList)).setVisibility(View.INVISIBLE);

        // Se corresponde con el RecyclerView
        View recyclerViewElement = view.findViewById(R.id.recyclerView);
        // Set the adapter
        if (recyclerViewElement instanceof RecyclerView) {
            recyclerView = (RecyclerView) recyclerViewElement;
            recyclerView.setAdapter(new MyListOfProductsListRecyclerViewAdapter(list));
        }

        return view;
    }

    //---- Methods ----
    public void openCreateListOfProductsDialog() {
        CreateListOfProductsDialog dialog = null;
        String tag = "";
        if(productListClass.equals(ShoppingList.class)) {
            dialog = new CreateShoppingListDialog(this);
            tag = "Create new Shopping List";
        } else if (productListClass.equals(Stock.class)) {
            dialog = new CreateStockDialog(this);
            tag = "Create new Stock";
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        }
        dialog.show(getParentFragmentManager(), tag);
    }

    @Override
    public void onDialogCreateClick(CreateEntityDialog dialog) {
        try {
            ProductsListClass entity = ((CreateListOfProductsDialog) dialog).getEntityToCreate();
            dao.insert(entity);

            // Mostramos el mensaje de exito y cerramos el dialog
            Toast.makeText(getContext(), dialog.getSuccessMsg(entity.getName()), Toast.LENGTH_LONG).show();
            dialog.dismiss();

            // Saltamos a la actividad del nuevo productList
            Class activityClass = null;
            if (entity.getClass().equals(Stock.class)) {
                activityClass = StockActivity.class;

            } else if (entity.getClass().equals(ShoppingList.class)) {
                activityClass = ShoppingListActivity.class;
            } else {
                new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
            }
            Intent intent = new Intent(getContext(), activityClass);
            intent.putExtra("ID", entity.getID());
            getContext().startActivity(intent);
        } catch (AppException ex) { }
    }
}
