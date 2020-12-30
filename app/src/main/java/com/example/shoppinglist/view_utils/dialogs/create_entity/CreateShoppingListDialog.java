package com.example.shoppinglist.view_utils.dialogs.create_entity;

import com.example.shoppinglist.R;
import com.example.shoppinglist.app_error_handling.AppError;
import static com.example.shoppinglist.app_error_handling.AppErrorHelper.CodeErrors;
import com.example.shoppinglist.app_error_handling.AppException;

import model.ShoppingList;

public class CreateShoppingListDialog extends CreateListOfProductsDialog<ShoppingList> {

    //---- Constructor ----
    public CreateShoppingListDialog(CreateEntityDialogListener listener) {
        super(listener);
    }

    //---- Methods ----
    public String getDialogTitle() {
        String titleText = getResources().getString(R.string.the_shopping_list);
        String dialogTitle = String.format(getResources().getString(R.string.enter_the_product_list_name), titleText);
        return dialogTitle;
    }

    @Override
    public String getSuccessMsg(final String itemName) {
        String msg = String.format(getResources().getString(R.string.info_msg_entity_created),
                getResources().getString(R.string.upCase_the_shopping_list), itemName);
        return msg;
    }

    @Override
    public ShoppingList getEntityToCreate() throws AppException {
        String name = getTypedName();
        if(name.equals("")) {
            throw new AppException(new AppError(CodeErrors.EMPTY_NAME_INPUT, getResources().getString(R.string.blank_name_input_error),getContext()));
        } else {
            return new ShoppingList(name);
        }
    }

}