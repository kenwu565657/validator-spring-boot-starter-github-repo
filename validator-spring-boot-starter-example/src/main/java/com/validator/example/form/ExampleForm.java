package com.validator.example.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.validator.example.annotation.ValidateFirstnameAndLastnameSameLanguage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;

// Comment Out Self Define Annotation First
// @ValidateFirstnameAndLastnameSameLanguage
public class ExampleForm {
    private String firstname;
    private String lastname;
    private CollectionMethod collectionMethod;
    @NotBlank(message = "must not be blank.", groups = CollectionMethodIsMailGroup.class)
    private String mailReceiverFullName;
    @ConvertGroup(from = Default.class, to = NoValidationGroup.class)
    @ConvertGroup(from = CollectionMethodIsMailGroup.class, to = Default.class)
    @NotNull(message = "must not be null.", groups = CollectionMethodIsMailGroup.class)
    @Valid
    private Address address;
    public static class Address {
        @NotBlank(message = "must not be blank.")
        private String flat;
        @NotBlank(message = "must not be blank.")
        private String street;
        @NotBlank(message = "must not be blank.")
        private String city;
    }

    interface NoValidationGroup{}
    interface CollectionMethodIsInPersonGroup {}
    interface CollectionMethodIsMailGroup {}

    @JsonIgnore
    @AssertTrue(message = "Firstname And Lastname must in same language.")
    public boolean isFirstnameAndLastnameSameLanguage() {
        if (isBlank(firstname) || isBlank(lastname)) {
            return true;
        }
        if (isContainingEnglishAndSpaceOnly(firstname)) {
            return isContainingEnglishAndSpaceOnly(lastname);
        }
        if (isChinese(firstname)) {
            return isChinese(lastname);
        }
        return false;
    }

    private boolean isBlank(String string) {
        if (null == string || string.isBlank()) {
            return true;
        }
        return false;
    }

    private boolean isContainingEnglishAndSpaceOnly(String string) {
        return string.matches("^[a-zA-Z][a-zA-Z\\s]+$");
    }

    private boolean isChinese(String string) {
        return string.matches("^[\u4E00-\u9FA5]+$");
    }

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

    public CollectionMethod getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(CollectionMethod collectionMethod) {
        this.collectionMethod = collectionMethod;
    }

    public String getMailReceiverFullName() {
        return mailReceiverFullName;
    }

    public void setMailReceiverFullName(String mailReceiverFullName) {
        this.mailReceiverFullName = mailReceiverFullName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public enum CollectionMethod {
        IN_PERSON, MAIL
    }
}
