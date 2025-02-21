package com.validator.test;

import com.validator.annotation.FieldName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TestingOnlineShoppingFormNotAnnotated {

    private int id;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    private String emailAddress;

    @NotBlank
    @Email
    private String phoneNumber;

    @Valid
    private List<TestingShoppingItemNotAnnotated> shoppingItemList;

    @Valid
    private List<@Valid ShoppingItem> shoppingItemList2;

    @NotNull
    private ShippingMethod shippingMethod;

    @Valid
    private SelfCollectionForm selfCollectionForm;

    @Valid
    private TestingAddressFormNotAnnotated addressForm;

    private TestingAddressFormNotAnnotated addressForm2;

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

    public List<TestingShoppingItemNotAnnotated> getShoppingItemList() {
        return shoppingItemList;
    }

    public void setShoppingItemList(List<TestingShoppingItemNotAnnotated> shoppingItemList) {
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

    public TestingAddressFormNotAnnotated getAddressForm() {
        return addressForm;
    }

    public void setAddressForm(TestingAddressFormNotAnnotated addressForm) {
        this.addressForm = addressForm;
    }

    public TestingAddressFormNotAnnotated getAddressForm2() {
        return addressForm2;
    }

    public void setAddressForm2(TestingAddressFormNotAnnotated addressForm2) {
        this.addressForm2 = addressForm2;
    }
}
