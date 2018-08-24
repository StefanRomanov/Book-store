package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Sale;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.repositories.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final BookService bookService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, BookService bookService, UserService userService, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerSale(String userId, String bookId) {
        User customer = this.userService.getUserById(userId);
        Book book = this.modelMapper.map(this.bookService.getOneById(bookId), Book.class);


        Sale sale = new Sale();
        sale.setBook(book);
        sale.setCustomer(customer);
        sale.setDate(LocalDate.now());

        customer.getBooks().add(book);
        book.getUsers().add(customer);
        customer.getSales().add(sale);

        this.saleRepository.saveAndFlush(sale);

    }

    @Override
    public List<Sale> allSalesAfterDate(LocalDate localDate) {
        return this.saleRepository.findAllByDateAfter(localDate);
    }
}
