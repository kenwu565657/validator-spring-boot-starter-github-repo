package com.validator.test;

import com.validator.annotation.FieldName;
import com.validator.annotation.FieldNameRoot;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.function.Function;

@FieldNameRoot
@FieldName("Address Information")
public class AddressForm {
    @FieldName("Address Line 1")
    private String line1;

    @FieldName("Address Line 2")
    private String line2;

    @FieldName("Address Line 3")
    private String line3;

    @NotNull
    @FieldName("Address District")
    private HongKongDistrict district;

    @SuppressWarnings("unused")
    @FieldName("Address Line 1 Or 2 Or 3")
    @AssertTrue
    private boolean isEitherAddressLineProvided() {
        Function<String, Boolean> isBlank = (s) -> {
            if (null == s) {
                return true;
            }
            return s.isEmpty();
        };
        return !isBlank.apply(line1) || !isBlank.apply(line2) || !isBlank.apply(line3);
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public HongKongDistrict getDistrict() {
        return district;
    }

    public void setDistrict(HongKongDistrict district) {
        this.district = district;
    }
}
