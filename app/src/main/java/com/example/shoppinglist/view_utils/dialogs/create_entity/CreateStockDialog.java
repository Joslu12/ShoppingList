package com.example.shoppinglist.view_utils.dialogs.create_entity;

import com.example.shoppinglist.R;
import com.example.shoppinglist.app_error_handling.AppError;
import static com.example.shoppinglist.app_error_handling.AppErrorHelper.CodeErrors;
import com.example.shoppinglist.app_error_handling.AppException;

import model.Stock;

public class CreateStockDialog extends CreateListOfProductsDialog<Stock> {

    //---- Constructor ----
    public CreateStockDialog(CreateEntityDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String titleText = getResources().getString(R.string.the_stock);
        return String.format(getResources().getString(R.string.enter_the_product_list), titleText);
    }

    @Override
    public String getSuccessMsg(String itemName) {
        return String.format(getResources().getString(R.string.info_msg_entity_created),
                getResources().getString(R.string.upCase_the_stock), itemName);
    }

    @Override
    public Stock getEntityToCreate() throws AppException {
        String name = getTypedName();
        if(name.equals("")) {
            throw new AppException(new AppError(CodeErrors.EMPTY_NAME_INPUT, getResources().getString(R.string.blank_name_input_error),getContext()));
        } else {
            return new Stock(name);
        }
    }

}