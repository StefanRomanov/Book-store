package app.project.gamestart.domain.models.binding;

import app.project.gamestart.web.validators.annotations.EmailValidator;
import app.project.gamestart.util.constants.PublisherConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



public class PublisherAddBindingModel {
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

    @NotEmpty(message = PublisherConstants.COMPANY_NAME_EMPTY_ERROR_MESSAGE)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @NotEmpty(message = PublisherConstants.VAT_EMPTY_ERROR_MESSAGE)
    @Size(min = PublisherConstants.VAT_MINIMUM_SIZE, message = PublisherConstants.VAT_LENGTH_ERROR_MESSAGE)
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @NotEmpty(message = PublisherConstants.BILLING_ADDRESS_EMPTY_MESSAGE)
    @Size(min = PublisherConstants.BILLING_ADDRESS_MINIMUM_SIZE)
    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @NotEmpty(message = PublisherConstants.LEGAL_FORM_EMPTY_ERROR_MESSAGE)
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

    @NotEmpty(message = PublisherConstants.CITY_EMPTY_ERROR_MESSAGE)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty(message = PublisherConstants.POSTAL_CODE_ERROR_MESSAGE)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
