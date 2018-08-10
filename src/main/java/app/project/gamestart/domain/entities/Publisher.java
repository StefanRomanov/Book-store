package app.project.gamestart.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "publishers")
public class Publisher extends BaseEntity{

    private String companyName;
    private String VATNumber;
    private String billingAddress;
    private User user;
    private Set<Developer> developers;
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
    public String getVATNumber() {
        return VATNumber;
    }

    public void setVATNumber(String VATNumber) {
        this.VATNumber = VATNumber;
    }

    @Column(nullable = false)
    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @OneToMany
    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
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
}
