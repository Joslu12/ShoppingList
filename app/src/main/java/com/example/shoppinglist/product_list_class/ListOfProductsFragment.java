package com.example.shoppinglist.product_list_class;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.app_error_handling.AppError;
import com.example.shoppinglist.app_error_handling.AppException;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;

import java.util.List;

import bd.dao.ListTableDao;
import model.Product;
import model.ProductsListClass;
import model.ShoppingList;
import model.Stock;

import static com.example.shoppinglist.app_error_handling.AppErrorHelper.CodeErrors;

public abstract class ListOfProductsFragment<T extends Product> extends Fragment implements CreateEntityDialog.CreateEntityDialogListener, DeleteEntityDialog.DeleteEntityDialogListener {

    //---- Constants and Definitions ----
    protected static final String PRODUCT_LIST = "product-list";

    //---- View Elements ----
    private RecyclerView recyclerView;

    //---- Attributes ----
    protected ProductsListClass<T> productList; //Objeto que contiene el Listado de productos
    private ListTableDao<?> daoProductList; //Dao para actualizar y cargar el listado de productos
    protected List<T> products; //Lista con los productos del listado de productos

    //---- Constructor ----
    public ListOfProductsFragment() {}

    //---- Fragment Methods ----
    protected abstract ListTableDao<?> getDaoProductList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            productList = (ProductsListClass<T>) getArguments().getSerializable(PRODUCT_LIST);
            daoProductList = getDaoProductList();
            products = productList.getProductsInsideAList(); // Cargamos los productos del listado en una lista
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(recyclerView != null) {
            products = productList.getProductsInsideAList();
            recyclerView.setAdapter(getRecyclerView());
            updateFragment();
        }
    }

    protected abstract View getFragmentView(LayoutInflater inflater, ViewGroup container,
                                         Bundle savedInstanceState);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragmento completo. Se devuelve uno u otro segun el tipo de instancia del objeto
        View view = this.getFragmentView(inflater,container,savedInstanceState);

        // Se corresponde con el RecyclerView
        View recyclerViewElement = view.findViewById(R.id.recyclerView);
        // Set the adapter
        if (recyclerViewElement instanceof RecyclerView) {
            recyclerView = (RecyclerView) recyclerViewElement;
            recyclerView.setAdapter(getRecyclerView());
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            updateFragment();
        }

        return view;
    }

    //---- Methods ----
    protected abstract MyListOfItemsProductsRecyclerViewAdapter<T> getRecyclerView();

    protected abstract String getStringTypeOfProduct();

    protected abstract CreateEntityDialog<T> getCreateProductDialog();
    public void openCreateProductDialog() {
        CreateEntityDialog<T> dialog = getCreateProductDialog();
        dialog.show(getParentFragmentManager(), "Create Product");
    }


    /**
     * Metodo usado para actualizar el estado del fragmento, que se realiza despues de cualquier modificacion del fragmento
     */
    protected abstract void updateFragment();

    @Override
    public void onDialogCreateClick(CreateEntityDialog dialog) {
        try {
            T entity = ((CreateEntityDialog<T>) dialog).getEntityToCreate();
            productList.addProduct(entity);
            daoProductList.doAddProduct(productList.getID(),entity);

            // Mostramos el mensaje de exito y cerramos el dialog
            Toast.makeText(getContext(), dialog.getSuccessMsg(entity.getName()), Toast.LENGTH_LONG).show();
            dialog.dismiss();

            // Recargamos la lista de productos
            if(recyclerView != null) {
                products = productList.getProductsInsideAList();
                recyclerView.setAdapter(getRecyclerView());
                updateFragment();
            }

        } catch (AppException ex) {
            // El propio AppErrorHelper se encarga de actuar adecuadamente ante la excepcion lanzada en el getEntityToCreate()
        }
    }

    protected abstract DeleteEntityDialog<T> getDeleteProductDialog(T product);
    public void openDeleteProductDialog(T product) {
        DeleteEntityDialog<T> dialog = getDeleteProductDialog(product);
        dialog.show(getParentFragmentManager(), "Delete a Product");
    }

    @Override
    public void onDialogDeleteClick(DeleteEntityDialog dialog) {
        if (productList instanceof ShoppingList || productList instanceof Stock) {
            // Guardamos el nombre para el mensage mostrado despues
            T entity = (T)dialog.getEntity();
            String name = entity.getName();

            // Eliminamos de la BD el producto
            productList.removeProduct(entity);
            daoProductList.doRemoveProduct(productList.getID(),entity);

            // Mostramos un mensaje informativo de la accion realizada
            String msg = String.format(getResources().getString(R.string.info_msg_entity_deleted), getStringTypeOfProduct(), name);
            Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
            dialog.dismiss();

            // Recargamos la lista de productos
            if(recyclerView != null) {
                products = productList.getProductsInsideAList();
                recyclerView.setAdapter(getRecyclerView());
                updateFragment();
            }
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        }
    }
}
