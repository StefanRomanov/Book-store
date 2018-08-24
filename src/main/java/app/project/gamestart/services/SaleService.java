package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Sale;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {
    void registerSale(String userId, String bookId);
    List<Sale> allSalesAfterDate(LocalDate localDate);
}
