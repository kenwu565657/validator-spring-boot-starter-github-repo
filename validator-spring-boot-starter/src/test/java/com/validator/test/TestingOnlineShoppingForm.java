package com.validator.test;

import com.validator.annotation.FieldName;
import com.validator.annotation.FieldNameRoot;
import com.validator.annotation.LocaleConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

@FieldNameRoot
@FieldName("Online Shopping Form")
@FieldName(value = "網上購物表格", locale = LocaleConstant.TRADITIONAL_CHINESE)
@FieldName(value = "网上购物表格", locale = LocaleConstant.SIMPLIFIED_CHINESE)
public class TestingOnlineShoppingForm {
    @FieldName("Shopping Form Id")
    @FieldName(value = "購物表格 ID", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "购物表格 ID", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private int id;

    @NotBlank
    @FieldName("Contact First Name")
    @FieldName(value = "聯絡姓名 (名字)", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系姓名 (名字)", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String firstname;

    @NotBlank
    @FieldName("Contact Last Name")
    @FieldName(value = "聯絡姓名 (姓)", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系姓名 (姓)", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String lastname;

    @NotBlank
    @Email
    @FieldName("Contact Email Address")
    @FieldName(value = "聯絡電郵", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系电邮", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String emailAddress;

    @NotBlank
    @FieldName("Contact Phone Number")
    @FieldName(value = "聯絡電話號碼", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系电话号码", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String phoneNumber;

    @Valid
    @FieldName("Shopping Cart")
    @FieldName(value = "購物車", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "购物车", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private List<@Valid
                 @FieldName("Shopping Cart Item")
                 @FieldName(value = "購物車物品", locale = LocaleConstant.TRADITIONAL_CHINESE)
                 @FieldName(value = "购物车物品", locale = LocaleConstant.SIMPLIFIED_CHINESE)
            ShoppingItem> shoppingItemList;

    @NotNull
    @FieldName("Selected Shipping Method")
    @FieldName(value = "選擇的運輸方式", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "选择的运输方式", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private ShippingMethod shippingMethod;

    @Valid
    @NotNull(groups = {SelfCollectionShippingGroup.class})
    @Null(groups = {ToAddressShippingGroup.class})
    @FieldName("Self Collection Form")
    @FieldName(value = "自取表格", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "自取表格", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private SelfCollectionForm selfCollectionForm;

    @Valid
    @NotNull(groups = {ToAddressShippingGroup.class})
    @Null(groups = {SelfCollectionShippingGroup.class})
    @FieldName("Address Form")
    @FieldName(value = "地址表格", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "地址表格", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private AddressForm addressForm;

    private AddressForm addressForm2;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ShoppingItem> getShoppingItemList() {
        return shoppingItemList;
    }

    public void setShoppingItemList(List<ShoppingItem> shoppingItemList) {
        this.shoppingItemList = shoppingItemList;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public SelfCollectionForm getSelfCollectionForm() {
        return selfCollectionForm;
    }

    public void setSelfCollectionForm(SelfCollectionForm selfCollectionForm) {
        this.selfCollectionForm = selfCollectionForm;
    }

    public AddressForm getAddressForm() {
        return addressForm;
    }

    public void setAddressForm(AddressForm addressForm) {
        this.addressForm = addressForm;
    }

    public AddressForm getAddressForm2() {
        return addressForm2;
    }

    public void setAddressForm2(AddressForm addressForm2) {
        this.addressForm2 = addressForm2;
    }

    public interface SelfCollectionShippingGroup {}

    public interface ToAddressShippingGroup {}
}
