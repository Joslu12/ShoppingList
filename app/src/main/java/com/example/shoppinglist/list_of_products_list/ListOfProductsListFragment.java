package com.example.shoppinglist.list_of_products_list;

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
import com.example.shoppinglist.app_error_handling.AppException;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateListOfProductsDialog;

import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import model.ProductsListClass;

public abstract class ListOfProductsListFragment<T extends ProductsListClass<?>> extends Fragment implements CreateEntityDialog.CreateEntityDialogListener {

    //---- View Elements ----
    private RecyclerView recyclerView;

    //---- Attributes ----
    private ListTableDao<T> dao;
    protected List<T> listOfProductList;

    //---- Constructor ----
    public ListOfProductsListFragment() {}

    //---- Fragment Methods ----
    protected abstract ListTableDao<T> getProductListDao(SQLiteDatabase conexion);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenemos el dao adecuado
        SQLiteDatabase bd = BaseDatosUtils.getWritableDatabaseConnection(getContext());
        dao = getProductListDao(bd);

        // Cargamos todos los listados de productos de la base de datos segun el dao
        listOfProductList = dao.findAll();
    }

    protected abstract MyListOfProductsListRecyclerViewAdapter<T> getRecyclerView();
    @Override
    public void onResume() {
        super.onResume();
        if(recyclerView != null) {
            listOfProductList = dao.findAll();
            recyclerView.setAdapter(getRecyclerView());
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
            recyclerView.setAdapter(getRecyclerView());
        }

        return view;
    }

    //---- Methods ----
    public abstract void openCreateListOfProductsDialog();

    protected abstract Class<?> getIntentActivityClass();
    protected abstract Class<?> getIntentEntityClass();
    @Override
    public void onDialogCreateClick(CreateEntityDialog dialog) {
        try {
            // Insertamos en la BD el nuevo ProductList
            T entity = ((CreateListOfProductsDialog<T>) dialog).getEntityToCreate();
            dao.insert(entity);

            // Mostramos el mensaje de exito y cerramos el dialog
            Toast.makeText(getContext(), dialog.getSuccessMsg(entity.getName()), Toast.LENGTH_LONG).show();
            dialog.dismiss();

            // Saltamos a la actividad del nuevo productList
            Intent intent = new Intent(getContext(), getIntentActivityClass());
            intent.putExtra("ID", entity.getID());
            intent.putExtra("CLASS", getIntentEntityClass());
            getContext().startActivity(intent);
        } catch (AppException ex) {
            // El manejo de la excepcion se hara desde el AppErrorHelper
        }
    }
}
