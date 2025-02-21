package com.validator.test;

import com.validator.annotation.FieldName;
import com.validator.annotation.FieldNameRoot;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import java.util.function.Function;

@FieldNameRoot
@FieldName("Self Collection Form")
public class SelfCollectionForm {
    @NotBlank
    @FieldName("Self Collection Point Pin Number")
    private String collectionPointId;

    @FieldName("Contact Phone Number")
    private String phoneNumber;

    @FieldName("Contact Email Address")
    private String emailAddress;

    @SuppressWarnings("unused")
    @FieldName("Phone Number Or Email Address")
    @AssertTrue()
    private boolean isEitherPhoneNumberOrEmailAddressProvided() {
        Function<String, Boolean> isBlank = (s) -> {
            if (null == s) {
                return true;
            }
            return s.isEmpty();
        };
        return !isBlank.apply(phoneNumber) || !isBlank.apply(emailAddress);
    }

    public String getCollectionPointId() {
        return collectionPointId;
    }

    public void setCollectionPointId(String collectionPointId) {
        this.collectionPointId = collectionPointId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
