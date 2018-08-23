package app.project.gamestart.domain.models.binding;

import app.project.gamestart.validators.annotations.EmailValidator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PublisherAddBindingModel {
    private String companyName;
    private String vatNumber;
    private String billingAddress;
    private String legalForm;
    private String companyEmail;
    private boolean sameEmail;
    private String country;
    private String city;
    private String postalCode;

    public PublisherAddBindingModel() {
    }

    @NotEmpty(message = "Please input Your company name !")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @NotEmpty(message = "Please input VAT number")
    @Size(min = 10, message = "VAT number should be at least 10 characters long.")
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @NotEmpty(message = "Please input company full billing address.")
    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @NotEmpty(message = "Please input company legal form stated in the registration papers.")
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

    public boolean getSameEmail() {
        return sameEmail;
    }

    public void setSameEmail(boolean sameEmail) {
        this.sameEmail = sameEmail;
    }

    @NotEmpty(message = "Please enter country of registration.")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotEmpty(message = "Please enter city.")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty(message = "Please enter postal code.")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
