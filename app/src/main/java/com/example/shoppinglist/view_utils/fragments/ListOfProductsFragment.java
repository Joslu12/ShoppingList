package com.example.shoppinglist.view_utils.fragments;

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
import com.example.shoppinglist.app_error_handling.AppException;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateProductDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateStockProductDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteProductDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteStockProductDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import bd.dao.ShoppingListDao;
import bd.dao.StockDao;
import bd.dao.StockShoppingListDao;
import model.Product;
import model.ProductsListClass;
import model.ShoppingList;
import model.Stock;
import model.StockProduct;
import model.StockShoppingList;

import static com.example.shoppinglist.app_error_handling.AppErrorHelper.CodeErrors;

public class ListOfProductsFragment<T extends Product> extends Fragment implements CreateEntityDialog.CreateEntityDialogListener, DeleteEntityDialog.DeleteEntityDialogListener {

    //---- Constants and Definitions ----
    protected static final String PRODUCT_LIST = "product-list";

    //---- View Elements ----
    private RecyclerView recyclerView;

    //---- Attributes ----
    private ProductsListClass<T> productList; //Objeto que contiene el Listado de productos
    private ListTableDao daoProductList; //Dao para actualizar y cargar el listado de productos
    private List<T> products; //Lista con los productos del listado de productos

    //---- Constructor ----
    public ListOfProductsFragment() {}

    //---- Fragment Methods ----
    public static ListOfProductsFragment newInstance(final ProductsListClass productList) {
        ListOfProductsFragment fragment = new ListOfProductsFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCT_LIST,productList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase bd = BaseDatosUtils.getWritableDatabaseConnection(getContext());

        if (getArguments() != null) {
            productList = (ProductsListClass) getArguments().getSerializable(PRODUCT_LIST);
            if(productList instanceof ShoppingList) {
                daoProductList = new ShoppingListDao(bd);
            } else if (productList instanceof Stock) {
                daoProductList = new StockDao(bd);
            } else if (productList instanceof StockShoppingList) {
                daoProductList = new StockShoppingListDao(bd);
            } else {
                new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
            }
        }

        // Cargamos los productos del listado en una lista
        products = loadProducts();
    }

    private List<T> loadProducts() {
        List<T> result = new ArrayList<T>();

        Iterator<T> iterator = productList.getProducts();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(recyclerView != null) {
            products = loadProducts();
            recyclerView.setAdapter(new MyListOfProductsRecyclerViewAdapter<T>(this,this.getProductClass(),products));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragmento completo
        View view = inflater.inflate(R.layout.fragment_list_of_product, container, false);

        // Se corresponde con el RecyclerView
        View recyclerViewElement = view.findViewById(R.id.recyclerView);
        // Set the adapter
        if (recyclerViewElement instanceof RecyclerView) {
            recyclerView = (RecyclerView) recyclerViewElement;
            recyclerView.setAdapter(new MyListOfProductsRecyclerViewAdapter<T>(this,this.getProductClass(),products));
        }

        return view;
    }

    //---- Methods ----
    private Class getProductClass() {
        if(productList instanceof ShoppingList || productList instanceof StockShoppingList) {
            return Product.class;
        } else {
            return StockProduct.class;
        }
    }

    private String getProductClassString() {
        if(productList instanceof ShoppingList || productList instanceof StockShoppingList) {
            return getResources().getString(R.string.the_product);
        } else {
            return getResources().getString(R.string.the_stock_product);
        }
    }

    public void openCreateProductDialog() {
        CreateEntityDialog<T> dialog = null;
        String tag = "";
        if(productList instanceof ShoppingList || productList instanceof StockShoppingList) {
            dialog = (CreateEntityDialog<T>) new CreateProductDialog(this);
            tag = "Create new Product";
        } else if (productList instanceof Stock) {
            dialog = (CreateEntityDialog<T>) new CreateStockProductDialog(this);
            tag = "Create new StockProduct";
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        }
        dialog.show(getParentFragmentManager(), tag);
    }

    @Override
    public void onDialogCreateClick(CreateEntityDialog dialog) {
        if (productList instanceof ShoppingList || productList instanceof Stock || productList instanceof StockShoppingList) {
            try {
                T entity = ((CreateEntityDialog<T>) dialog).getEntityToCreate();
                productList.addProduct(entity);
                daoProductList.doAddProduct(productList.getID(),entity);

                // Mostramos el mensaje de exito y cerramos el dialog
                Toast.makeText(getContext(), dialog.getSuccessMsg(entity.getName()), Toast.LENGTH_LONG).show();
                dialog.dismiss();

                // Recargamos la lista de productos
                if(recyclerView != null) {
                    products = loadProducts();
                    recyclerView.setAdapter(new MyListOfProductsRecyclerViewAdapter<T>(this,this.getProductClass(),products));
                }

            } catch (AppException ex) { }
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        }
    }

    public void openDeleteProductDialog(T product) {
        DeleteEntityDialog<T> dialog = null;
        String tag = "";
        if(productList instanceof ShoppingList || productList instanceof StockShoppingList) {
            dialog = (DeleteEntityDialog<T>) new DeleteProductDialog(this,product);
            tag = "Delete a Product";
        } else if (productList instanceof Stock) {
            dialog = (DeleteEntityDialog<T>) new DeleteStockProductDialog(this, (StockProduct) product);
            tag = "Delete a StockProduct";
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        }
        dialog.show(getParentFragmentManager(), tag);
    }

    @Override
    public void onDialogDeleteClick(DeleteEntityDialog dialog) {
        if (productList instanceof ShoppingList || productList instanceof Stock || productList instanceof StockShoppingList) {
            // Guardamos el nombre para el mensage mostrado despues
            T entity = (T)dialog.getEntity();
            String name = entity.getName();

            // Eliminamos de la BD el producto
            productList.removeProduct(entity);
            daoProductList.doRemoveProduct(productList.getID(),entity);

            // Mostramos un mensaje informativo de la accion realizada
            String msg = String.format(getResources().getString(R.string.info_msg_entity_deleted), getProductClassString(), name);
            Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
            dialog.dismiss();

            // Recargamos la lista de productos
            if(recyclerView != null) {
                products = loadProducts();
                recyclerView.setAdapter(new MyListOfProductsRecyclerViewAdapter<T>(this,this.getProductClass(),products));
            }
        } else {
            new AppError(CodeErrors.MUST_NOT_HAPPEN, getResources().getString(R.string.unexpected_error),getContext());
        }
    }
}
