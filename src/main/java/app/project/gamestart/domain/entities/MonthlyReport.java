package app.project.gamestart.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "monthly_reports")
public class MonthlyReport extends BaseEntity {

    private int totalBooksSold;
    private BigDecimal totalEarnings;
    private LocalDate date;
    private int totalBuyers;

    public MonthlyReport() {
    }

    @Column(nullable = false)
    public int getTotalBooksSold() {
        return totalBooksSold;
    }


    public void setTotalBooksSold(int totalBooksSold) {
        this.totalBooksSold = totalBooksSold;
    }

    @Column(nullable = false)
    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    @Column(nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(nullable = false)
    public int getTotalBuyers() {
        return totalBuyers;
    }

    public void setTotalBuyers(int totalBuyers) {
        this.totalBuyers = totalBuyers;
    }
}
