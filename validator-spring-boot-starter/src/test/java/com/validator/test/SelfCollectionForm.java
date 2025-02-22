package com.validator.test;

import com.validator.annotation.FieldName;
import com.validator.annotation.FieldNameRoot;
import com.validator.annotation.LocaleConstant;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import java.util.function.Function;

@FieldNameRoot
@FieldName("Self Collection Form")
@FieldName(value = "自取表格", locale = LocaleConstant.TRADITIONAL_CHINESE)
@FieldName(value = "自取表格", locale = LocaleConstant.SIMPLIFIED_CHINESE)
public class SelfCollectionForm {
    @NotBlank
    @FieldName("Self Collection Point Pin Number")
    @FieldName(value = "自取點碼", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "自取点码", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String collectionPointId;

    @FieldName("Contact Phone Number")
    @FieldName(value = "聯絡電話號碼", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系电话号码", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String phoneNumber;

    @FieldName("Contact Email Address")
    @FieldName(value = "聯絡電郵", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系电邮", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String emailAddress;

    @SuppressWarnings("unused")
    @FieldName("Phone Number Or Email Address")
    @FieldName(value = "聯絡電話號碼或聯絡電郵", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系电话号码或联系电邮", locale = LocaleConstant.SIMPLIFIED_CHINESE)
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
