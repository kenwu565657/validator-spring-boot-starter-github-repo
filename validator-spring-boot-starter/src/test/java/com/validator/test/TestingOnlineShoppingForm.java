package com.validator.test;

import com.validator.annotation.FieldName;
import com.validator.annotation.FieldNameRoot;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

@FieldNameRoot
@FieldName("Online Shopping Form")
public class TestingOnlineShoppingForm {
    @FieldName("Shopping Form Id")
    private int id;

    @NotBlank
    @FieldName("Contact First Name")
    private String firstname;

    @NotBlank
    @FieldName("Contact Last Name")
    private String lastname;

    @NotBlank
    @Email
    @FieldName("Contact Email Address")
    private String emailAddress;

    @NotBlank
    @Email
    @FieldName("Contact Phone Number")
    private String phoneNumber;

    @Valid
    @FieldName("Shopping Cart")
    private List<@Valid @FieldName("Shopping Cart Item") ShoppingItem> shoppingItemList;

    @NotNull
    @FieldName("Selected Shipping Method")
    private ShippingMethod shippingMethod;

    @Valid
    @NotNull(groups = {SelfCollectionShippingGroup.class})
    @Null(groups = {ToAddressShippingGroup.class})
    private SelfCollectionForm selfCollectionForm;

    @Valid
    @NotNull(groups = {ToAddressShippingGroup.class})
    @Null(groups = {SelfCollectionShippingGroup.class})
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
