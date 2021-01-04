
package com.example.shoppinglist.product_list_class.shopping_list;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoppinglist.R;
import com.example.shoppinglist.go_shopping.GoShoppingActivity;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.create_entity.CreateProductDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteProductDialog;
import com.example.shoppinglist.product_list_class.ListOfProductsFragment;
import com.example.shoppinglist.product_list_class.MyListOfItemsProductsRecyclerViewAdapter;
import com.example.shoppinglist.product_list_class.MyListOfProductsRecyclerViewAdapter;

import bd.BaseDatosUtils;
import bd.dao.ListTableDao;
import bd.dao.ShoppingListDao;
import model.Product;
import model.ProductsListClass;
import model.ShoppingList;

public class ShoppingListFragment extends ListOfProductsFragment<Product> {

    //---- View Elements ----
    private Button btnGoShopping;

    //---- Constructor ----
    public ShoppingListFragment() {}

    @Override
    protected ListTableDao<? extends ShoppingList> getDaoProductList() {
        SQLiteDatabase bd = BaseDatosUtils.getWritableDatabaseConnection(getContext());
        return new ShoppingListDao(bd);
    }

    //---- Fragment Methods ----
    public static ShoppingListFragment newInstance(final ProductsListClass<Product> productList) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCT_LIST,productList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_shopping_list,container,false);

        // Establecemos el background del Fragmento
        view.setBackground(getResources().getDrawable(R.drawable.wallpaper_shopping_list,null));

        btnGoShopping = (Button) view.findViewById(R.id.btnGoShopping);
        btnGoShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finishAffinity(); // Eliminamos las actividades del Stack para que no se pueda volver atras
                Intent intent = new Intent(getContext(), GoShoppingActivity.class);
                intent.putExtra("SHOPPING_LIST", productList);
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    //---- Methods ----
    @Override
    protected final void updateFragment() {
        // Si hay productos el boton se muestra disponible
        btnGoShopping.setEnabled(this.productList.getProducts().hasNext());
        if(btnGoShopping.isEnabled()) {
            btnGoShopping.setTextColor(getResources().getColor(R.color.black,null));
        } else {
            btnGoShopping.setTextColor(getResources().getColor(R.color.dark_grey,null));
        }
    }

    @Override
    protected final MyListOfItemsProductsRecyclerViewAdapter<Product> getRecyclerView() {
        return new MyListOfProductsRecyclerViewAdapter(this,products);
    }

    @Override
    protected final String getStringTypeOfProduct() {
        return getResources().getString(R.string.the_product);
    }

    @Override
    protected final CreateEntityDialog<Product> getCreateProductDialog() {
        return new CreateProductDialog(this);
    }

    @Override
    protected final DeleteEntityDialog<Product> getDeleteProductDialog(Product product) {
        return new DeleteProductDialog(this,product);
    }
}
