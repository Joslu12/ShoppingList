package com.example.shoppinglist.stocks;

import com.example.shoppinglist.ListOfProductsActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteEntityDialog;
import com.example.shoppinglist.view_utils.dialogs.delete_entity.DeleteStockDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditListOfProductsDialog;
import com.example.shoppinglist.view_utils.dialogs.edit_entity.EditStockDialog;

import bd.dao.StockDao;
import model.Stock;

public class StockActivity extends ListOfProductsActivity<Stock> {

    //---- Methods ----
    @Override
    protected StockFragment getNewInstance(Stock stock) {
        return StockFragment.newInstance(stock);
    }

    @Override
    protected DeleteEntityDialog<Stock> generateEntityDeleteDialog() {
        return new DeleteStockDialog(this, this.productsList);
    }

    @Override
    protected String generateDeleteDialogTag() {
        return "Delete a Stock";
    }

    @Override
    protected EditListOfProductsDialog<Stock> generateEntityEditDialog() {
        return new EditStockDialog(this, this.productsList);
    }

    @Override
    protected String generateEditDialogTag() {
        return "Edit a Stock";
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