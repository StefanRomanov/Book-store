package app.project.gamestart.domain.models.binding;

import app.project.gamestart.web.validators.annotations.EmailValidator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PublisherAddBindingModel {

    private static final String COMPANY_NAME_EMPTY_ERROR_MESSAGE = "Please input Your company name !";
    private static final String VAT_EMPTY_ERROR_MESSAGE = "Please input VAT number";
    private static final String VAT_LENGTH_ERROR_MESSAGE = "VAT number should be at least 10 characters long.";
    private static final String BILLING_ADDRESS_EMPTY_MESSAGE = "Please input company full billing address.";
    private static final String LEGAL_FORM_EMPTY_ERROR_MESSAGE = "Please input company legal form stated in the registration papers.";
    private static final String CITY_EMPTY_ERROR_MESSAGE = "Please enter city.";
    private static final String POSTAL_CODE_ERROR_MESSAGE = "Please enter postal code.";

    private String id;
    private String companyName;
    private String vatNumber;
    private String billingAddress;
    private String legalForm;
    private String companyEmail;
    private String country;
    private String city;
    private String postalCode;

    public PublisherAddBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty(message = COMPANY_NAME_EMPTY_ERROR_MESSAGE)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @NotEmpty(message = VAT_EMPTY_ERROR_MESSAGE)
    @Size(min = 10, message = VAT_LENGTH_ERROR_MESSAGE)
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @NotEmpty(message = BILLING_ADDRESS_EMPTY_MESSAGE)
    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @NotEmpty(message = LEGAL_FORM_EMPTY_ERROR_MESSAGE)
    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }


    @EmailValidator
    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @NotNull
    @NotEmpty
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotEmpty(message = CITY_EMPTY_ERROR_MESSAGE)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty(message = POSTAL_CODE_ERROR_MESSAGE)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
