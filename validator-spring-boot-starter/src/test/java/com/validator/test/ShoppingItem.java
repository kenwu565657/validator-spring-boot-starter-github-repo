package com.validator.test;

import com.validator.annotation.FieldName;
import com.validator.annotation.FieldNameRoot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@FieldNameRoot
@FieldName("Shopping Item")
public class ShoppingItem {
    @NotBlank
    @FieldName("Item Name")
    private String itemId;

    @NotNull
    @FieldName("Item Quantity")
    private Integer quantity;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
