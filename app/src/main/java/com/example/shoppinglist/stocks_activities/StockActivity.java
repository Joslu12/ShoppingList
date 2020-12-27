package com.example.shoppinglist.stocks_activities;

import com.example.shoppinglist.ListOfProductsActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteStockDialog;

import bd.dao.StockDao;
import model.Stock;

public class StockActivity extends ListOfProductsActivity<Stock> {

    //---- Methods ----
    @Override
    protected DeleteEntityDialog<Stock> generateEntityDialog() {
        return new DeleteStockDialog(this, this.productsList);
    }

    @Override
    protected String generateDialogTag() {
        return "Delete a Stock";
    }

    @Override
    protected StockDao getProductListDao() {
        return new StockDao(this.bd);
    }

    @Override
    protected String getProductListTypeString() {
        return getResources().getString(R.string.upCase_the_stock);
    }

}