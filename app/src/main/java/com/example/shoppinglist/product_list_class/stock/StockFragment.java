package com.example.shoppinglist.product_list_class.stock;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateStockProductDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteStockProductDialog;
import com.example.shoppinglist.product_list_class.ListOfProductsFragment;
import com.example.shoppinglist.product_list_class.MyListOfItemsProductsRecyclerViewAdapter;
import com.example.shoppinglist.product_list_class.MyListOfStockProductsRecyclerViewAdapter;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import bd.dao.StockDao;
import bd.dao.StockShoppingListDao;
import model.ProductsListClass;
import model.Stock;
import model.StockProduct;
import model.StockShoppingList;

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
        View view = inflater.inflate(R.layout.content_stock,container,false);

        //---- Attributes ----
        Button btnGenerateAssociatedShoppingList = (Button) view.findViewById(R.id.btnGenerateAssociatedStock);
        btnGenerateAssociatedShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Generamos el shoppinglist associado
                String name = getResources().getString(R.string.stock_shopping_list) + ": " + productList.getName();
                StockShoppingList stockShoppingList = ((Stock)productList).generateShoppingList(name);
                StockShoppingListDao dao = new StockShoppingListDao(getDaoProductList().getBDConnection());
                if(((Stock)productList).getAssociatedListId() != -1) {
                    // Si ya tiene una lista de la compra asociada la eliminamos para crear la nueva
                    stockShoppingList.setID(((Stock) productList).getAssociatedListId());
                    dao.remove(stockShoppingList);
                }
                int shoppingListId = dao.insert(stockShoppingList);

                // Actualizamos el id del stock
                ((Stock) productList).setAssociatedListId(shoppingListId);
                getDaoProductList().update((Stock)productList);

                // Mostramos un mensaje de exito
                Toast.makeText(getContext(),String.format(getResources().getString(R.string.info_msg_stock_shopping_list_created),name),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    //---- Methods ----
    @Override
    protected void updateFragment() {}

    @Override
    protected MyListOfItemsProductsRecyclerViewAdapter<StockProduct> getRecyclerView() {
        return new MyListOfStockProductsRecyclerViewAdapter(this,products);
    }

    @Override
    protected String getStringTypeOfProduct() {
        return getResources().getString(R.string.the_product);
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
