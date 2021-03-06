package app.project.gamestart.domain.models.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlyReportServiceModel {
    private int totalBooksSold;
    private BigDecimal totalEarnings;
    private LocalDate date;
    private int totalBuyers;

    public MonthlyReportServiceModel() {
    }

    public int getTotalBooksSold() {
        return totalBooksSold;
    }

    public void setTotalBooksSold(int totalBooksSold) {
        this.totalBooksSold = totalBooksSold;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalBuyers() {
        return totalBuyers;
    }

    public void setTotalBuyers(int totalBuyers) {
        this.totalBuyers = totalBuyers;
    }
}
