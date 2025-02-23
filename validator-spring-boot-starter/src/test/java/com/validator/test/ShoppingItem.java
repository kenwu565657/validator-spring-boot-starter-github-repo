package com.validator.test;

import com.validator.annotation.FieldName;
import com.validator.constant.LocaleConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@FieldName("Shopping Item")
@FieldName(value = "購物物品", locale = LocaleConstant.TRADITIONAL_CHINESE)
@FieldName(value = "购物物品", locale = LocaleConstant.SIMPLIFIED_CHINESE)
public class ShoppingItem {
    @NotBlank
    @FieldName("Item Name")
    @FieldName(value = "物品名", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "物品名", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String itemId;

    @NotNull
    @FieldName("Item Quantity")
    @FieldName(value = "物品數目", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "物品数目", locale = LocaleConstant.SIMPLIFIED_CHINESE)
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
