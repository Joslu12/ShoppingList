package com.example.shoppinglist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import model.Product;

public class Utils {

    private Utils(){}

    //---- Methods ----

    /**
     *
     * @param iterator
     * @param <E>
     * @return una lista ordenada con los productos que tiene el iterdor
     */
    public static <E extends Product> List<E> listOfProductsFromIterator(Iterator<E> iterator) {
        List<E> result = new ArrayList<E>();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        Collections.sort(result);
        return result;
    }
}
