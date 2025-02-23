This repo is for a project which aim to provide more readable validation message for hibernate validator, which include i18n error field name.
Then, the project is packed as a spring-boot starter.
```java
// This is the way to call the api
// construct an instance of ValidationService, then call .validate(Object object, Locale locale)
var validationResult = validationService.validate(object, Locale.ENGLISH);
```
## Example
Below is an example to use the Annotation @FieldName on the DTO object, then get the i18n Field Name. There are 3 example for 3 different locale.
```java
// The example DTO for example
// validator-spring-boot-starter/src/test/java/com/validator/test/TestingOnlineShoppingForm.java
@FieldNameRoot
@FieldName("Online Shopping Form")
@FieldName(value = "網上購物表格", locale = LocaleConstant.TRADITIONAL_CHINESE)
@FieldName(value = "网上购物表格", locale = LocaleConstant.SIMPLIFIED_CHINESE)
public class TestingOnlineShoppingForm {
    ...
    @FieldName("Contact First Name")
    @FieldName(value = "聯絡姓名 (名字)", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系姓名 (名字)", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String firstname;
    ...
    @FieldName("Selected Shipping Method")
    @FieldName(value = "選擇的運輸方式", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "选择的运输方式", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private ShippingMethod shippingMethod;
    ...
    @FieldName("Shopping Cart")
    @FieldName(value = "購物車", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "购物车", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private List<@Valid
    @FieldName("Shopping Cart Item")
    @FieldName(value = "購物車物品", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "购物车物品", locale = LocaleConstant.SIMPLIFIED_CHINESE)
            ShoppingItem> shoppingItemList;
    ...
}
```
### 1. Example for Locale.ENGLISH
Then test for calling the api for Locale.ENGLISH
```java
// validator-spring-boot-starter/src/test/java/com/validator/service/ValidationServiceImplTest.java
@Test
void validateByEnglishLocale() {
    TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
    var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.ENGLISH);
    printConstraintViolationWrapper(validationResult);
}
```
The result:
```text
...
TranslatedErrorFieldName: Online Shopping Form - Contact First Name.
TranslatedMessage: must not be blank.
Locale: en.
...
TranslatedErrorFieldName: Online Shopping Form - Selected Shipping Method.
TranslatedMessage: must not be null.
Locale: en.

TranslatedErrorFieldName: Online Shopping Form - Online Shopping Form - Shopping Cart - Item Number 1 - Shopping Cart Item - Item Name.
TranslatedMessage: must not be blank.
Locale: en.

TranslatedErrorFieldName: Online Shopping Form - Online Shopping Form - Shopping Cart - Item Number 1 - Shopping Cart Item - Item Quantity.
TranslatedMessage: must not be null.
Locale: en.
```
### 2. Example for Locale.TRADITIONAL_CHINESE
Then test for calling the api for Locale.TRADITIONAL_CHINESE
```java
// validator-spring-boot-starter/src/test/java/com/validator/service/ValidationServiceImplTest.java
@Test
void validateByTraditionalChineseLocale() {
    TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
    var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.TRADITIONAL_CHINESE);
    printConstraintViolationWrapper(validationResult);
}
```
The result:
```text
...
TranslatedErrorFieldName: 網上購物表格 - 聯絡姓名 (名字).
TranslatedMessage: 不得空白.
Locale: zh_TW.
...
TranslatedErrorFieldName: 網上購物表格 - 選擇的運輸方式.
TranslatedMessage: 不得是空值.
Locale: zh_TW.

TranslatedErrorFieldName: 網上購物表格 - 網上購物表格 - 購物車 - 第1項 - 購物車物品 - 物品名.
TranslatedMessage: 不得空白.
Locale: zh_TW.

TranslatedErrorFieldName: 網上購物表格 - 網上購物表格 - 購物車 - 第1項 - 購物車物品 - 物品數目.
TranslatedMessage: 不得是空值.
Locale: zh_TW.
```
### 3. Example for Locale.SIMPLIFIED_CHINESE
Then test for calling the api for Locale.SIMPLIFIED_CHINESE
```java
// validator-spring-boot-starter/src/test/java/com/validator/service/ValidationServiceImplTest.java
@Test
void validateBySimplifiedChineseLocale() {
    TestingOnlineShoppingForm testingOnlineShoppingForm = getTestingOnlineShoppingForm();
    var validationResult = validationService.validate(testingOnlineShoppingForm, Locale.SIMPLIFIED_CHINESE);
    printConstraintViolationWrapper(validationResult);
}
```
The result:
```text
...
TranslatedErrorFieldName: 网上购物表格 - 联系姓名 (名字).
TranslatedMessage: 不能为空.
Locale: zh_CN.
...
TranslatedErrorFieldName: 网上购物表格 - 选择的运输方式.
TranslatedMessage: 不能为null.
Locale: zh_CN.

TranslatedErrorFieldName: 网上购物表格 - 网上购物表格 - 购物车 - 第1項 - 购物车物品 - 物品名.
TranslatedMessage: 不能为空.
Locale: zh_CN.

TranslatedErrorFieldName: 网上购物表格 - 网上购物表格 - 购物车 - 第1項 - 购物车物品 - 物品数目.
TranslatedMessage: 不能为null.
Locale: zh_CN.
```

## Steps To Use The Starter
There are 4 Steps. Can follow the example project validator-spring-boot-starter-example.
### 1. Add Maven Dependency In Spring Boot Project
```xml
<dependency>
    <groupId>com.validator</groupId>
    <artifactId>validator-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. Add Annotations In DTO
```java
// validator-spring-boot-starter-example/src/main/java/com/validator/example/form/SimpleForm.java
...
@FieldName(value = "A Simple Form")
@FieldName(value = "簡單表格", locale = LocaleConstant.TRADITIONAL_CHINESE)
@FieldName(value = "简单表格", locale = LocaleConstant.SIMPLIFIED_CHINESE)
public class SimpleForm {
    @NotBlank
    @FieldName("Simple Form Id")
    @FieldName(value = "簡單表格點碼", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "简单表格点码", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String id;

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
    ...
}
```

### 3. Inject ValidationService In Spring Boot Project
```java
// validator-spring-boot-starter-example/src/main/java/com/validator/example/service/FormService.java
@Service
public class FormService {
    private final ValidationService validatorService;

    public FormService(ValidationService validatorService) {
        this.validatorService = validatorService;
    }

    public <T> Set<ConstraintViolationWrapper<T>> validateForm(T object, Locale locale, Class<?>... groups) {
        return validatorService.validate(object, locale, groups);
    }
}
```

### 4. (Optional, Example Only) Use In Restful Service
```java
// validator-spring-boot-starter-example/src/test/java/com/validator/example/controller/FormControllerTest.java
...
@Test
void testValidateBadRequestEmailError() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/validate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                            """
                            {
                                "id": "testingId",
                                "firstname": "testingFirstname",
                                "lastname": "testingLastname",
                                "emailAddress": "testingEmailAddress",
                                "phoneNumber": "testingPhoneNumber"
                            }
                            """
                            )
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(
                            """
                                    {
                                        "valid": false,
                                        "validationErrorList": [
                                            {
                                                "errorField": "簡單表格 - 聯絡電郵",
                                                "errorMessage": "必須是形式完整的電子郵件位址"
                                            }
                                        ]
                                    }
                                    """
                    ));
}
...
```
