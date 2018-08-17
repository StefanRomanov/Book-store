package app.project.gamestart.domain.entities;

import app.project.gamestart.domain.enums.Country;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "publishers")
public class Publisher extends BaseEntity{

    private String companyName;
    private String vatNumber;
    private String billingAddress;
    private String legalForm;
    private String companyEmail;
    private Country country;
    private String city;
    private String postalCode;
    private User user;
    private Set<Author> authors;
    private Boolean isApproved;

    public Publisher() {
    }

    @Column(nullable = false)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(nullable = false)
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Column(nullable = false)
    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @OneToMany
    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Column(nullable = false)
    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean approved) {
        isApproved = approved;
    }

    @OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLegalForm() {
        return legalForm;
    }

    @Column(nullable = false)
    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }

    @Column(nullable = false)
    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @Enumerated(value = EnumType.STRING)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Column(nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(nullable = false)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
