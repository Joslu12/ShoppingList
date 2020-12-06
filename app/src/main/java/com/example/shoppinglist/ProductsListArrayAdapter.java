package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import model.ProductsListClass;

public class ProductsListArrayAdapter<T extends ProductsListClass> extends ArrayAdapter<T> {

    //---- Constructor ----
    public ProductsListArrayAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    //---- Metodos ----
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Accede a los datos para esta posicion
        ProductsListClass productList = getItem(position);

        // intenta reutilizar la vista, en otro caso, debe crear la nueva vista
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.products_list_array_adapter, parent, false);
        }

        // patron ViewHolder, accede a los componentes de la vista
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.text_nombre = (TextView) view.findViewById(R.id.name_text);
            view.setTag(holder);
        }

        // Actualiza los componentes de la vista
        holder.text_nombre.setText(productList.getName());

        // Devuelve la vista creada con los elementos actualizados
        return view;
    }

    private static class ViewHolder {
        TextView text_nombre;
    }
}
