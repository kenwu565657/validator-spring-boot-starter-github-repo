package com.validator.test;

import com.validator.annotation.FieldName;
import com.validator.constant.LocaleConstant;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.function.Function;

@FieldName("Address Information")
@FieldName(value = "地址資訊", locale = LocaleConstant.TRADITIONAL_CHINESE)
@FieldName(value = "地址信息", locale = LocaleConstant.SIMPLIFIED_CHINESE)
public class AddressForm {
    @FieldName("Address Line 1")
    @FieldName(value = "地址第一行", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "地址第一行", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String line1;

    @FieldName("Address Line 2")
    @FieldName(value = "地址第二行", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "地址第二行", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String line2;

    @FieldName("Address Line 3")
    @FieldName(value = "地址第三行", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "地址第三行", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String line3;

    @NotNull
    @FieldName("Address District")
    @FieldName(value = "地區", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "地区", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private HongKongDistrict district;

    @SuppressWarnings("unused")
    @FieldName("Address Line 1 Or 2 Or 3")
    @FieldName(value = "任意地址行", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "任意地址行", locale = LocaleConstant.SIMPLIFIED_CHINESE)
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
