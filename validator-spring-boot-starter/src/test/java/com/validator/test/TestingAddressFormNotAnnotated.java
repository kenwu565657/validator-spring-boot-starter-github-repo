package com.validator.test;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.function.Function;

public class TestingAddressFormNotAnnotated {
    private String line1;

    private String line2;

    private String line3;

    @NotNull
    private HongKongDistrict district;

    @SuppressWarnings("unused")
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
